# dubbo-demo

**本项目是springboot整合dubbo-Zookeper的demo项目,仿照GitHub上另外一个项目自己写出来的，留作笔记**

1：使用Zookeper作为注册中心，

Zookeper安装核心点如下[Windows]:

​	1.1:解压后，复制 conf目录下的zoo_sample.cfg文件，命名为zoo.cfg.修改如下配置：

```c++
	tickTime=2000
	dataDir=D:/zookeeper-3.4.6/data 
	dataLogDir=D:/zookeeper-3.4.6/log
	clientPort=2181
```

​	1.2：进入bin目录下启动zkServer.cmd与zkCli.cmd



2：整合dubbo

​	2.1：创建一个project的maven父工程，接着创建api，provider，consumer，三个工程都是module。

​	2.2 ：API工程为接口层只提供接口。编写完成后直接运行mvn install.以供其他模块引入

​	2.3：provider层是服务层，提供接口的具体逻辑实现，用来被接口调用

```java
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
```

​	provider的配置文件为：

```properties
## Dubbo 服务提供者配置
 #服务名称
spring.dubbo.application.name=provider
 ## 注册中心地址
spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
# dubbo 协议
spring.dubbo.protocol.name=dubbo
spring.dubbo.protocol.port=20880
# 声明需要暴露的服务接口
spring.dubbo.scan=com.imooc.springboot.dubbo.demo.provider

```



​	2.4 :consumer 层是提供接口供前端调用，通过注入API层的接口。直接调用API层的忌口，然后api再调用provider的具体实现

```java
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
```

consumer的配置文件

```properties
server.port=8080
## Dubbo 服务消费者配置
spring.dubbo.application.name=consumer
#这里使用广播的注册方式，
#如果有Can't assign address异常需要加vm参数：
#-Djava.net.preferIPv4Stack=true
spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
#--声明需要暴露的服务接口com.imooc.springboot.dubbo.demo.consumer
spring.dubbo.scan=com.imooc.springboot.dubbo.demo.consumer
```

