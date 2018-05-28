package com.atomist.spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
class SpringController {

    @RequestMapping(method = GET, path = "/")
    public String root() {
        return "Spring Boot REST service running: Served by " + getClass().getName();
    }

    @RequestMapping(method = GET, path = "version")
    public String version() {
        return getClass().getPackage().getImplementationVersion();
    }

    @RequestMapping(method = GET, path = "hello/{name}")
    public String person(@PathVariable String name) {
        return "Hello " + name + "!";
    }

}
