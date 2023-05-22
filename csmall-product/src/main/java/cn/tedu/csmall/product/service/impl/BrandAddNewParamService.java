package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.service.IBrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BrandAddNewParamService implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void addNew(BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌模板】的业务，参数:{}", brandAddNewParam);
        //检查品牌名称是否被占用 如果被占用 则抛出异常
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", brandAddNewParam.getName());
        int countByName = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌名称统计匹配的属性模板数量，结果:{}", countByName);
        if (countByName>0){
            String message = "添加品牌失败，品牌名称已被占用";
            throw new ServiceException(message);
        }

        //将品牌名称写入到数据库中
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddNewParam, brand);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());

        brandMapper.insert(brand);
        log.debug("将品牌名称写入到数据库中，完成!");
    }
}
