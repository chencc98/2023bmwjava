package com.example.pipeline.entity;

import java.util.Date;

//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.scheduling.support.SimpleTriggerContext;

import com.example.pipeline.inbound.PullMethod;
import com.example.pipeline.inbound.ScheduleHandler;
import com.example.pipeline.utils.Constants;
import com.example.pipeline.utils.MyUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class FeedInbound {
    private String uuid;
    private String feedUuid;
    private String feedName;
    private String vendorCode;
    private String inboundType;
    private String pullMethodCode;
    private String pullMethodWorker;
    private String scheduleCode;
    private String scheduleValue;
    private String pullAttr;
    private String pushMethodCode;
    private String pushMethodWorker;
    private String pushAttr;
    private Date previousRunTs;

    public boolean needToRun(long currentMs) {
        if (!InboundType.PULL.name().equalsIgnoreCase(this.inboundType)) {
            return false;
        }
        Date nextRunTime = ScheduleHandler.nextRunTime(this, currentMs);
        log.info("nextruntime :" + nextRunTime.toString());
        Date previousRunTime = this.getPreviousRunTs();
        if (previousRunTime == null) {
            if (nextRunTime.getTime()<= currentMs) {
                //never run before, and earlier than current
                return true;
            }
            if (MyUtils.isDateAroundMin(MyUtils.longToDate(currentMs), nextRunTime, Constants.NEXT_RUN_TIME_INTERNAL_MIN)) {
                return true;
            }
        } else {
            if (MyUtils.isDateAroundMin(MyUtils.longToDate(currentMs), nextRunTime, Constants.NEXT_RUN_TIME_INTERNAL_MIN)) {
                //above 1min is our schedule time for now
                return true;
            }
        }
        return false;
    }

    public PullMethod getPullWorker() {
        String clz = this.getPullMethodWorker();
        try {
            Class<PullMethod> c = (Class<PullMethod>)Class.forName(clz);
            PullMethod pull = c.newInstance();
            return pull;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
