package com.example.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncTask {

    @Async
    public void task1(Long begin) throws InterruptedException {
        Long b = System.currentTimeMillis();
        Thread.sleep(5000);
  //      Long error = b / 0;
        Long e = System.currentTimeMillis();
        log.info("task1 Async task time: {}", e - b);
    }

    @Async
    public void task2(Long begin) throws InterruptedException {
        Long b = System.currentTimeMillis();
        Thread.sleep(5000);
        //      Long error = b / 0;
        Long e = System.currentTimeMillis();
        log.info("task2 Async task time: {}", e - b);
    }

    @Async
    public void asyncTaskAfterCommit1() throws InterruptedException {
        Thread.sleep(5000);
        log.info("asyncTaskAfterCommit1");
    }

    @Async
    public void asyncTaskAfterCommit2() throws InterruptedException {
        Thread.sleep(5000);
        log.info("asyncTaskAfterCommit2");

    }


}
