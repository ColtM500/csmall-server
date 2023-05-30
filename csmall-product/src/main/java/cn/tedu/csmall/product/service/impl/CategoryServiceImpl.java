package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.tedu.csmall.product.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SpuMapper spuMapper;

    public CategoryServiceImpl (){
        log.debug("正在创建Category业务层对象……");
    }

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) {
        //判断类别名称是否重复
        log.debug("正在执行【添加类别】业务……");
        int countByNameAndParentId = categoryMapper.countByNameAndParentId(
                categoryAddNewParam.getName(), categoryAddNewParam.getParentId()
        );
        if (countByNameAndParentId>0){
            String message = "新增类别失败，类别名称已被占用!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //检查父类级别 如果parentId不为0 判断父类类别数据是否存在 这里判断是为了更改depth
        Integer depth = 1;  //预设深度为1
        Long parentId = categoryAddNewParam.getParentId();//下面要用到parentId 故也要提取出来
        CategoryStandardVO parentCategory = null;//因为下面都要用到父类级别的数据故直接提取出来
            //如果当前类别的父类ID不为0
        if (parentId!=0){
            //根据父类ID查询父类信息
            parentCategory = categoryMapper.getStandardById(categoryAddNewParam.getParentId());
           if (parentCategory==null){//如果查不到父类信息 抛出异常
               String message = "新增类别失败，父类级别不存在！";
               log.debug(message);
               throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
           } else {//如果有父类信息 在父类的深度基础上再+1
               depth = parentCategory.getDepth()+1;
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
        if (rows!=1){
            String message = "添加类别失败！服务器忙，请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        //判断parentId是否不为0 并判断父类级别是不是isParent=0
        if (parentId!=0 && parentCategory.getIsParent()==0){
            //如果是 则 将父级ID修改为父级ID（因为得传ID） 同时将父级的isParent判断改为1
            Category category1 = new Category();
            category1.setId(parentId);
            category1.setIsParent(1);
            rows = categoryMapper.update(category1);
            if (rows!=1){
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
}
