package cn.tedu.csmall.product.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class TimerAspect {

    @Around("execution(* cn.tedu.csmall.product.service.*.*(..))")
    public Object xxx(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();

        //获取匹配的方法的相关信息
        String targetClassName = pjp.getTarget().getClass().getName();// 获取匹配的方法所在的类
        String signatureName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs(); // 方法的参数列表
        System.out.println("类型：" + targetClassName);
        System.out.println("方法名：" + signatureName);
        System.out.println("参数列表：" + Arrays.toString(args));

        // 执行以上表达式匹配的方法，即某个Service的某个方法
        // 注意-1：必须获取调用proceed()方法返回的结果，作为当前切面方法的返回值
        // -- 如果没有获取，或没有作为当前切面方法的返回值，相当于执行了连接点方法，却没有获取返回值
        // 注意-2：调用proceed()时的异常必须抛出，否则，Controller将无法知晓Service抛出过异常，就无法向客户端响应错误信息
        // -- 前提：本例的切面是作用于Service的
        // -- 其实，你也可以使用try...catch捕获调用proceed()时的异常，但必须在catch中也抛出异常
        Object result = pjp.proceed();

        long end = System.currentTimeMillis();
        System.out.println("执行耗时：" + (end - start) + "毫秒");

        // 返回调用proceed()得到的结果
        return result;

    }
}
