package com.atomist.play;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
class DemoController {

    @RequestMapping(method = GET, path = "/")
    public String root() {
        return "Spring Boot REST service running: Served by " + getClass().getName();
    }

    @RequestMapping(method = GET, path = "hello/{name}")
    public String person(@PathVariable String name) {
        int random = (int) (Math.random() * 4 + 1);
        
        if (random > 3) {
            throw new RuntimeException("Unable to connect to backend system");
        }

        return "Hello " + name + "!";
    }

}
