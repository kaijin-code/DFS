package com.neo.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class TimerTaskService {

    @Autowired
    private CuratorFramework curatorFramework;

    @Value("${server.port}")
    private String port;

    @Scheduled(cron = "0/5 * * * * *")
    public void task() {
        LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, "/timerTask");
        try {
            leaderLatch.start();
            // Leader选举需要一些时间，等待2秒
            Thread.sleep(2000);
            // 判断是否为主节点
            if (leaderLatch.hasLeadership()) {
                System.out.println(new Date() + "    port=" + port + " 是领导");
                // 定时任务的业务逻辑代码
            } else {
                System.out.println(new Date() + "    port=" + port + " 是从属");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                leaderLatch.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
