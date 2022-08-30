package com.meta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class RouteController {

	@RequestMapping(value = "/{path:[^\\.]*}")
	public String redirect(WebRequest request) {
		return "forward:/";       
    }
	
	@RequestMapping(value = "/*/{path:[^\\.]*}")
	public String redirect2(WebRequest request) {
		return "forward:/";  
	}	
	
	@RequestMapping(value = "/*/*/{path:[^\\.]*}")
	public String redirect3(WebRequest request) {
		return "forward:/";  
	}
	
	@RequestMapping(value = "/*/*/*/{path:[^\\.]*}")
	public String redirect4(WebRequest request) {
		return "forward:/";  
	}	
	
}
