package com.example.pipeline.inbound;

import java.util.Date;

import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.utils.MyUtils;

public class ScheduleHandler {
    public static Date nextRunTime(FeedInbound inbound, long currentMs) {
        //TODO cron only for now. will add more
        CronTrigger cron = new CronTrigger(inbound.getScheduleValue());
        SimpleTriggerContext context = null;
        if (inbound.getPreviousRunTs() == null) {
            context = new SimpleTriggerContext();
        } else {
            Date date = inbound.getPreviousRunTs();
            context = new SimpleTriggerContext(date, date, MyUtils.datePlusBySecond(date, 10));
        }
        Date next =  cron.nextExecutionTime(context);
        if (next.getTime() >= currentMs) {
            return next;
        }
        if (MyUtils.isDateEarlyMin(MyUtils.longToDate(currentMs), next, 1)) {
            return next;
        }
        context = new SimpleTriggerContext();
        return cron.nextExecutionTime(context);
    }
}
