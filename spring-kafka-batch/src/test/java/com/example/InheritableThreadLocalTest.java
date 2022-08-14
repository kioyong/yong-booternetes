package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

@Slf4j
public class InheritableThreadLocalTest {

    //refer ExcutorConfig
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static final ThreadLocal<String> strThreadLocal = new ThreadLocal<>();

    @Test
    public void test() throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 100; i++) {
            String str = sdf.format(cal.getTime());
            //api call
            executorService.submit(() -> {
                try {
                    run(str);
                }
                catch (InterruptedException e) {
                    log.error("error");
                }
            });
            Thread.sleep(1000L);
            cal.add(Calendar.MINUTE, 1);
        }
    }

    public void run(String str) throws InterruptedException {
        String traceId = UUID.randomUUID().toString().replace("-", "")
            .substring(0, 10);

        log.error("start            " + str + " token : " + str);
        //  setHeader
        strThreadLocal.set(str);


        // ShareManager subtask
        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(() -> {
            log.error("process sub task " + str + " token : " + strThreadLocal.get());
            return str + "";
        });
        taskList.add(() -> {
            log.error("process sub task " + str + " token : " + strThreadLocal.get());
            return str + "";
        });

        executorService.invokeAll(taskList);
        List<Future<String>> futures = executorService.invokeAll(taskList);
        futures.stream().map(future -> {
            try {
                String s = future.get();
                log.error("done task        " + s + " token : " + strThreadLocal.get());
                
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            catch (ExecutionException e) {
                throw new RuntimeException(e);
            } return null;
        }).collect(Collectors.toList());
//        strThreadLocal.remove();
    }
    
    @Test
    public void test1(){
        LocalTime now = LocalTime.now();
        long l = 60L * 60L - now.getMinute() * 60L - now.getSecond();
        System.out.println(l);
    }
    
    
    @Test
    public void StringTest() throws IOException {
        String file = "test.sql";
        ClassPathResource cp = new ClassPathResource(file);
        
        byte[] bytes = FileCopyUtils.copyToByteArray(cp.getInputStream());
        System.out.println(new String(bytes));
        System.out.println(String.valueOf(bytes));
        String str = FileCopyUtils.copyToString(new InputStreamReader(cp.getInputStream()));
        System.out.println(str);
    }
}
