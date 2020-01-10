package com.neo.controller;

import com.neo.base.CacheLock;
import com.neo.base.CacheParam;
import com.neo.dao.EmployeeRepository;
import com.neo.model.Employee;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    public Map<String,Object> map = new HashMap<>();
    public int i =0;

    @RequestMapping("/")
    public String index() {


        return "Hello Spring Boot 2.0!";
    }

    @CacheLock(prefix = "test", expire = 30)
    @GetMapping("/test")
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }




    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CuratorFramework curatorFramework;

    @GetMapping("/emp/save")
    public Employee save(String name) {

        // 获取锁
        InterProcessSemaphoreMutex balanceLock = new InterProcessSemaphoreMutex(curatorFramework, "/zktest" + name);
        Employee employee = new Employee();
        try {
            // 执行加锁操作
            balanceLock.acquire();
            System.out.println("已经加锁了, name=" + name);
            employee.setName(name);
            if ("abc".equals(name)) {
                Thread.sleep(30000);
            }
            employee.setAge((int) (Math.random() * 100));
            employee.setSex(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放锁资源
                balanceLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        employeeRepository.save(employee);

        return employee;
    }
}