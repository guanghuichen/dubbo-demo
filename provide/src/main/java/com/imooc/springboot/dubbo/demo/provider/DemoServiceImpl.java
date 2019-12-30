package com.imooc.springboot.dubbo.demo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.imooc.springboot.dubbo.demo.DemoService;

@Service // dubbo 的注解了，不是springboot的注解
public class DemoServiceImpl implements DemoService {
	public String sayHello(String name) {
		System.out.println("this is provide ~~~~~~~~");
		return "Hello, " + name + " (from Spring Boot)";
	}
}