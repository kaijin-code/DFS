package com.neo.server;

import com.neo.service.ListenerService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class StartService implements ApplicationRunner {


    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private ListenerService listenerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
// 非常重要！！！Start the client. Most mutator methods will not work until the client is started
        curatorFramework.start();
        System.out.println("zookeeper client start");
        // 初始化监听方法
        listenerService.listener();
    }
}
