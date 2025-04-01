package com.example.onaffair.online_chat.job;

import com.example.onaffair.online_chat.service.ActivityStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ActivityStatusUpdateJob {

    @Autowired
    ActivityStatusService activityStatusService;

    @Scheduled(cron = "0 * * * * ?")
    public void executeStatusUpdate() {
        activityStatusService.updateActivityStatus();
    }
}
