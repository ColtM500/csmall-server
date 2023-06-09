package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.pojo.vo.PageData;
import cn.tedu.csmall.commons.util.PageInfoToPageDataConvert;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.BrandCategoryMapper;
import cn.tedu.csmall.product.mapper.CategoryAttributeTemplateMapper;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.tedu.csmall.product.pojo.vo.CategoryTreeItemVO;
import cn.tedu.csmall.product.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryAttributeTemplateMapper categoryAttributeTemplateMapper;

    @Autowired
    private BrandCategoryMapper brandCategoryMapper;

    public CategoryServiceImpl() {
        log.debug("正在创建Category业务层对象……");
    }

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) {
        //判断类别名称是否重复
        log.debug("正在执行【添加类别】业务……");
        int countByNameAndParentId = categoryMapper.countByNameAndParentId(
                categoryAddNewParam.getName(), categoryAddNewParam.getParentId()
        );
        if (countByNameAndParentId > 0) {
            String message = "新增类别失败，类别名称已被占用!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //检查父类级别 如果parentId不为0 判断父类类别数据是否存在 这里判断是为了更改depth
        Integer depth = 1;  //预设深度为1
        Long parentId = categoryAddNewParam.getParentId();//下面要用到parentId 故也要提取出来
        CategoryStandardVO parentCategory = null;//因为下面都要用到父类级别的数据故直接提取出来
        //如果当前类别的父类ID不为0
        if (parentId != 0) {
            //根据父类ID查询父类信息
            parentCategory = categoryMapper.getStandardById(categoryAddNewParam.getParentId());
            if (parentCategory == null) {//如果查不到父类信息 抛出异常
                String message = "新增类别失败，父类级别不存在！";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            } else {//如果有父类信息 在父类的深度基础上再+1
                depth = parentCategory.getDepth() + 1;
            }
        }

        //将创建的新类别信息添加进数据库
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam, category);
        category.setDepth(depth);
        category.setIsParent(0);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        int rows = categoryMapper.insert(category);
        if (rows != 1) {
            String message = "添加类别失败！服务器忙，请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        //判断parentId是否不为0 并判断父类级别是不是isParent=0
        if (parentId != 0 && parentCategory.getIsParent() == 0) {
            //如果是 则 将父级ID修改为父级ID（因为得传ID） 同时将父级的isParent判断改为1
            Category category1 = new Category();
            category1.setId(parentId);
            category1.setIsParent(1);
            rows = categoryMapper.update(category1);
            if (rows != 1) {
                String message = "添加类别失败！服务器忙，请稍后再试!";
                throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
            }
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据ID删除类别】的业务，参数：{}", id);
        // 调用Mapper对象的getStandardById()执行查询
        CategoryStandardVO currentCategory = categoryMapper.getStandardById(id);
        // 判断查询结果是否为null，如果是，则抛出异常
        if (currentCategory == null) {
            // 是：数据不存在，抛出异常
            String message = "删除类别失败，尝试删除的类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 判断查询结果中的isParent是否为1，如果是，则抛出异常
        if (currentCategory.getIsParent() == 1) {
            String message = "删除类别失败，该类别仍包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 检查此类别是否关联了SPU
        {
            int count = spuMapper.countByCategoryId(id);
            if (count > 0) {
                String message = "删除品牌失败！当前品牌仍关联了商品！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        //检查此类别是否关联了属性
        {
            int count = categoryAttributeTemplateMapper.countByCategoryId(id);
            if (count > 0) {
                String message = "删除类别失败！当前类别仍关联了属性模板！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        //检查此类别是否关联了品牌
        {
            int count = brandCategoryMapper.countByCategoryId(id);
            if (count > 0) {
                String message = "删除类别失败！当前类别仍关联了品牌模板!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }


        // 调用Mapper对象的deleteById()方法执行删除
        int rows = categoryMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除类别失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }

        // 调用Mapper对象的countByParentId方法，根据以上查询结果中的parentId，执行统计
        Long parentId = currentCategory.getParentId();
        int count = categoryMapper.countByParentId(parentId);
        // 判断统计结果为0，则将父级类别的isParent更新为0
        if (count == 0) {
            Category parentCategory = new Category();
            parentCategory.setId(parentId);
            parentCategory.setIsParent(0);
            rows = categoryMapper.update(parentCategory);
            if (rows != 1) {
                String message = "删除类别失败，服务器忙，请稍后再尝试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
            }
        }
    }

    @Override
    public void setEnable(Long id) {
        updateEnableById(id, 1);
    }

    @Override
    public void setDisable(Long id) {
        updateEnableById(id, 0);
    }

    @Override
    public void setDisplay(Long id) {
        updateDisplayById(id, 1);
    }

    @Override
    public void setHidden(Long id) {
        updateDisplayById(id, 0);
    }

    @Override
    public CategoryStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询类别详情】的业务，参数:{}", id);
        CategoryStandardVO queryResult = categoryMapper.getStandardById(id);
        if (queryResult==null){
            String message = "查询类别详情失败，类别数据不存在！";
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public void updateStandardById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam) {
        log.debug("开始处理【修改类别详情】的业务，ID：{}，新数据：{}",id,categoryUpdateInfoParam);
        //检查查询结果是否为空
        CategoryStandardVO queryResult = categoryMapper.getStandardById(id);
        if (queryResult==null){
            String message = "修改类别失败，类别信息不存在!";
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //调用countByNameAndNotId(统计)
        int count = categoryMapper.countByNameAndNotId(id, categoryUpdateInfoParam.getName());
        if (count>0){
            String message = "修改类别详情失败，类别名称已被占用!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //调用update执行修改
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateInfoParam, category);
        category.setId(id);
        int rows = categoryMapper.updateById(category);
        if (rows!=1){
            String message = "修改类别详情失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public List<CategoryTreeItemVO> listTree() {
        log.debug("开始处理【获取类别树】的业务，参数:无");
        //创建树的集合
        List<CategoryTreeItemVO> categoryTreeItemList = new ArrayList<>();
        //创建list的集合
        List<CategoryListItemVO> categoryList = categoryMapper.list();
        //将CategoryListItemVO的List转为Map型
        Map<Long, CategoryListItemVO> categoryMap = transformListToMap(categoryList);
        System.out.println("此时categoryList的类型为： "+categoryList.getClass());
        for (Long key : categoryMap.keySet()) {//遍历所有的转换成Map类型的对象的key
            CategoryListItemVO mapItem = categoryMap.get(key);//获取他们的key值
            System.out.println("此时mapItem的类型为： "+mapItem.getClass());
            if (mapItem.getParentId() == 0) {//如果他们为父级类 则需要将CategoryListItemVO的相应值转为categoryTreeItemList
                CategoryTreeItemVO categoryTreeItem = transformListItemToTreeItem(mapItem);
                categoryTreeItemList.add(categoryTreeItem);//将转换成TreeItem型的数据add进TreeList中

                //处理children子类的数据部分
                //查看elementUI 需要key值 转换成的TreeList 子类的Map集合
                fillChildren(mapItem, categoryTreeItem, categoryMap);
            }
        }
        return categoryTreeItemList;
    }

    private Map<Long, CategoryListItemVO> transformListToMap(List<CategoryListItemVO> categoryList) {
        Map<Long, CategoryListItemVO> categoryMap = new LinkedHashMap<>();
        for (CategoryListItemVO categoryListItemVO : categoryList) {
            if (categoryListItemVO.getEnable() == 0) {
                continue;
            }
            categoryMap.put(categoryListItemVO.getId(), categoryListItemVO);
        }
        return categoryMap;
    }

    private CategoryTreeItemVO transformListItemToTreeItem(CategoryListItemVO listItem) {
        return new CategoryTreeItemVO()
                .setValue(listItem.getId())
                .setLabel(listItem.getName());

    }

    private void fillChildren(CategoryListItemVO listItem,//listItem当前操作的分类项 treeItem要添加子节点的分类树节点
                                                          // categoryMap 所有分类项的映射表
                              CategoryTreeItemVO treeItem, Map<Long, CategoryListItemVO> categoryMap) {

        if (listItem.getIsParent() == 1) {//判断是否为父节点
            treeItem.setChildren(new ArrayList<>());//是 创建一个空的子节点列表
            for (Long key : categoryMap.keySet()) {//遍历整个Map 找当前操作的分类项的子项
                                                   // 拿到子类所有的map键值 放到一个Long型的Set集合中
                CategoryListItemVO mapItem = categoryMap.get(key);
                //如果list中某个mapItem的父级类id为某个list项中的id
                if (mapItem.getParentId() == listItem.getId()) {//找到后 将其转换为分类树节点并添加到分类树中
                    CategoryTreeItemVO SubCategoryTreeItem = transformListItemToTreeItem(mapItem);
                    treeItem.getChildren().add(SubCategoryTreeItem);//将转换成TreeItem型的数据add进TreeList中

                    if (mapItem.getIsParent() == 1) {//如果子分类项也是父节点 则递归fillChildren方法继续添加其子类项
                        //处理children子类的数据部分
                        //查看elementUI 需要key值 转换成的TreeList 子类的Map集合
                        fillChildren(mapItem, SubCategoryTreeItem, categoryMap);
                    }
                }
            }
        }
    }

    private void updateEnableById(Long id, Integer enable) {
        //检查类别是否存在
        CategoryStandardVO categoryStandard = categoryMapper.getStandardById(id);
        if (categoryStandard == null) {
            String message = Enable_Text[enable] + "类别失败，类别数据不存在!";
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //检查当前类别的启用状态 相同则抛出异常
        if (categoryStandard.getEnable() == enable) {
            String message = Enable_Text[enable] + "类别失败，此类别已处于" + Enable_Text[enable] + "状态!";
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //调用mapper 更新mapper状态
        Category category = new Category();
        category.setId(id);
        category.setEnable(enable);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        int rows = categoryMapper.updateById(category);
        if (rows != 1) {
            String message = Enable_Text[enable] + "类被失败，服务器繁忙，请稍后再试!";
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }

    }

    private void updateDisplayById(Long id, Integer isDisplay){
        //检查类别是否存在
        CategoryStandardVO currentCategory = categoryMapper.getStandardById(id);
        if (currentCategory==null){
            String message = Display_Text[isDisplay] + "类别失败！类别数据不存在!";
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //检查启用状态
        if (currentCategory.getIsDisplay()==isDisplay){
            String message = Display_Text[isDisplay]+"数据失败！该类别已处于"+Display_Text[isDisplay]+"状态!";
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //修改启用状态
        Category category = new Category();
        int rows = categoryMapper.updateById(category);
        if (rows!=1){
            String message = Display_Text[isDisplay]+"类别失败！服务器繁忙，请稍后再试!";
            throw new ServiceException(ServiceCode.ERROR_UNKNOWN, message);
        }
    }

    @Override
    public PageData<CategoryListItemVO> listByParentId(Long parentId, Integer pageNum) {
        log.debug("开始处理【根据父级类别查询子级类别列表】的业务，父级类别：{}, 页码：{}", parentId, pageNum);
        return listByParentId(parentId, pageNum, 5);
    }

    @Override
    public PageData<CategoryListItemVO> listByParentId(Long parentId, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【根据父级类别查询子级类别列表】的业务，父级类别：{}, 页码：{}，每页记录数：{}", parentId, pageNum, pageSize);
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper进行数据库处理的分页查询
        List<CategoryListItemVO> list = categoryMapper.listByParentId(parentId);
        //将所查信息用pageInfo装起来
        PageInfo<CategoryListItemVO> pageInfo = new PageInfo<>(list);
        //用Convert的api将pageInfo转程pageData
        PageData<CategoryListItemVO> pageData = PageInfoToPageDataConvert.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
