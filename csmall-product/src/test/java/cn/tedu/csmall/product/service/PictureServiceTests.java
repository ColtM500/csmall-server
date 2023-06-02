package cn.tedu.csmall.product.service;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.product.pojo.param.PictureAddNewParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class PictureServiceTests {

    @Autowired
    private IPictureService service;

    @Test
    void addNew() {
        PictureAddNewParam pictureAddNewDTO = new PictureAddNewParam();
        pictureAddNewDTO.setUrl("http://www.baidu.com/test.jpg");

        try {
            service.addNew(pictureAddNewDTO);
            log.debug("添加图片成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void delete() {
        Long id = 3L;

        try {
            service.delete(id);
            log.debug("根据id【{}】删除图片成功！", id);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void setCover(){
        try {
            service.setCover(13L);
            log.debug("根据id【{}】修改图片成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }

    }
}
