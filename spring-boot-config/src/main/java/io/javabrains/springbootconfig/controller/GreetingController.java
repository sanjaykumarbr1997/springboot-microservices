package io.javabrains.springbootconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GreetingController {
	
	@Value("${my.greeting}")
	private String greetingMessage;
	
	@GetMapping("/greeting")
	public String greeting() {
		return greetingMessage;
	}

}
