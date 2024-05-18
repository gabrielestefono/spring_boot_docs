package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {
	@GetMapping(produces = "application/json")
	public String getMethodName() {
		return "Olá, mundo!";
	}
	
}
