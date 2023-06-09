package cn.tedu.csmall.product.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheSchedule {

    // fixedRate：执行频率，以【上一次执行开始时的时间】来计算下次的执行时间，以毫秒为单位
    // fixedDelay：执行间隔，从【上一次执行结束时的时间】来计算下次的执行时间，以毫秒为单位
    // cron：取值是一个字符串，此字符串是一个表达式，包含6~7个值，各值之间使用空格分隔
    // -- 在cron表达式中，各值从左至右分别是：秒 分 时 日 月 周 [年]
    // -- 各值均可使用通配符
    // -- 使用星号（*）表示任意值
    // -- 使用问号（?）表示不关心此值，此通配符仅可以用于“日”和“周”
    // -- 各值可以使用 x/y 格式的值，其中，x表示起始值，y表示间隔周期，以“分”位置取值为1/5为例，表示“分”的值为1时开始执行，且每间隔5分钟执行一次
    // 示例：
    // "56 34 12 13 2 ? 2023"表示：2023年2月13日12:34:56执行任务，不关心当天星期几
    // "0/30 * * * * ?"表示：每分钟的0秒时执行，且每30秒执行一次
    // 更多内容参考：
    // https://segmentfault.com/a/1190000021574315
    // https://blog.csdn.net/study_665/article/details/123506946
    @Scheduled(fixedDelay = 5000)
    public void xxx(){
        log.debug("CacheSchedule.xxx()");
    }
}
