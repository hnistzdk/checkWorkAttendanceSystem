package com.zdk.task;

import com.zdk.service.CheckInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zdk
 * @Date 2021/12/28 14:03
 * 定时任务
 */
@Component
public class ScheduledTask {

    @Autowired
    private CheckInfoService checkInfoService;

    /**
     * 从左至右：秒 分 小时 月份中的日期 月份 星期中的日期 年份
     * 字段	允许值	允许的特殊字符
     * 秒（Seconds）	0~59的整数	, - * /    四个字符
     * 分（Minutes）	0~59的整数	, - * /    四个字符
     * 小时（Hours）	0~23的整数	, - * /    四个字符
     * 日期（Day of Month）	1~31的整数（但是你需要考虑你月的天数）	,- * ? / L W C     八个字符
     * 月份（Month）	1~12的整数或者 JAN-DEC	, - * /    四个字符
     * 星期（Day of Week）	1~7的整数或者 SUN-SAT （1=SUN）	, - * ? / L C #     八个字符
     * 年(可选，留空)（Year）	1970~2099	, - * /    四个字符
     */
    /**
     * 每天0点 执行生成考勤初始信息
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void generateCheckInfo(){
        checkInfoService.generateCheckInfo();
    }

    /**
     * 每天0点5分确认缺勤状态
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void checkAbsent(){
        checkInfoService.checkAbsent();
    }
}
