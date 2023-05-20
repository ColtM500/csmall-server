package cn.tedu.csmall.product.ex.handler;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleServiceException(ServiceException e){
        log.warn("程序运行过程中出现了ServiceException，将统一处理！");
        log.warn("异常的信息:{}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    public String handleBindException(BindException e){
        log.warn("程序运行过程中出现了BindException,将统一处理!");
        log.warn("异常信息:{}",e.getMessage());
        String message =  e.getFieldError().getDefaultMessage();
        return message;
    }

    //【注意】在项目正式上线时，不允许使用e.printStackTrace();
    @ExceptionHandler
    public String handleThrowable(Throwable e){//避免服务端出现500的错误 所以用Throwable
        log.warn("程序运行过程中出现了ServiceException，将统一处理！");
        log.warn("异常的信息:{}", e.getMessage());
        String message = "服务器忙，请稍后再试![在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在全局异常处理器中补充处理对应异常的方法]";
        return message;
    }

}
