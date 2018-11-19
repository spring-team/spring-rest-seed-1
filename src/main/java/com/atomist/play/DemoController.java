package com.atomist.play;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

	@GetMapping("/")
	public String root() {
		return "Hello demo on Wed! I'm served by " + getClass().getName();
	}

	@GetMapping("hello/{name}")
	public String person(@PathVariable String name) {
		return "Hello " + name + "!";
	}

}
