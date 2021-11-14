package com.api.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //30 초마다 실행
    @Scheduled(cron = "0/30 * * * * *")
    public void testSchedule() throws Exception {
        logger.info("[Batch Scheduler] 만기 도래 계약 안내장 발송 준비");
        Thread.sleep(2000);
        logger.info("[Batch Scheduler] 만기 도래 계약 조회 완료");
        Thread.sleep(2000);
        logger.info("[Batch Scheduler] 만기 도래 계약 SAM 파일 생성 완료");
        Thread.sleep(2000);
        logger.info("[Batch Scheduler] 만기 도래 계약 안내장 발송 완료 (총 : 227건)");
    }

}
