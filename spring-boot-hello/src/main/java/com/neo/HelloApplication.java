package com.neo;

import com.neo.service.CacheKeyGenerator;
import com.neo.service.LockKeyGenerator;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

    @Bean
	public CacheKeyGenerator cacheKeyGenerator(){
		return  new LockKeyGenerator();
	}

	@Bean
	public CuratorFramework curatorFramework(){
		return CuratorFrameworkFactory.newClient("112.124.12.100:2181", new RetryNTimes(5, 1000));
	}
}
