package com.example.async.contoller;


import com.example.async.service.AsyncTask;
import com.example.async.service.DomainExample;
import com.example.async.service.DomainExampleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final AsyncTask asyncTask;
    private final DomainExampleRepository repository;
    @GetMapping("/task")
    public Long doAsync() throws InterruptedException {
       Long begin = System.currentTimeMillis();
       asyncTask.task1(begin);
       asyncTask.task2(begin);
       Thread.sleep(12000);
       Long end = System.currentTimeMillis();
       log.info("main task");
       return end - begin;
    }

   @GetMapping("/afterCommit")
   @Transactional
   public String afterTransaction() throws InterruptedException {
        repository.save( DomainExample.builder().name("Test").build());
        executeAfterTransactionCommits(() -> {
            try {
                asyncTask.asyncTaskAfterCommit1();
                asyncTask.asyncTaskAfterCommit2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(10000);
        log.info("executou a principal");
        return "Executou";

   }
    private void executeAfterTransactionCommits(Runnable task) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                task.run();
            }
        });
    }

}
