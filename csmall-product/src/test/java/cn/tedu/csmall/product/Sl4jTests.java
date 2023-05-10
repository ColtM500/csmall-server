package cn.tedu.csmall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Sl4jTests {

    @Test
    void test(){
        int x = 1;
        int y = 2;
        System.out.println("x = " + x + " , " + "y = " + y + ", x + y = " + (x+y));
        log.info("hello!");
    }
}
