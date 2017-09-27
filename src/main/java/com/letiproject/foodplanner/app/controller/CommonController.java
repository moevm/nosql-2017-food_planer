package com.letiproject.foodplanner.app.controller;

import com.letiproject.foodplanner.app.domain.TestObject;
import com.letiproject.foodplanner.app.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CommonController {

    @Autowired
    private DomainRepository repository;

    @RequestMapping(value = "/")
    public String returnHelloWorldMsg(Map<String, Object> model) {
        repository.insert(new TestObject("Some data"));
        List<TestObject> all = repository.findAll();
        model.put("objects", all);

        model.put("welcome", "Hello User. It's test page!");
        return "welcome";
    }
}
