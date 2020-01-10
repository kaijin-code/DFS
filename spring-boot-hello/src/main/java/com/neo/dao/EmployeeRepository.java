package com.neo.dao;

import com.neo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class EmployeeRepository {

    private Map<String,Object> map = new LinkedHashMap<>();

    public void save(Employee en){

        map.put("name", en.getName());
        map.put("age", en.getAge());
        map.put("sex", en.isSex());

        for(Map.Entry<String,Object> map2: map.entrySet())
        {
            System.out.println( map2.getKey()+"-------"+map2.getValue());

        }

    }
}
