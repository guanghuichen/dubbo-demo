package com.imooc.springboot.dubbo.demo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imooc.springboot.dubbo.demo.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoConsumerController {
	
//@Reference 用在消费端，表明使用的是服务端的什么服务
	@Reference
	private DemoService demoService;

	@RequestMapping(value = "/sayHello",method = RequestMethod.GET)
	public String sayHello(@RequestParam String name) {
		System.out.println("this is the consumer  welcome !!!");
		return demoService.sayHello(name);
	}

}