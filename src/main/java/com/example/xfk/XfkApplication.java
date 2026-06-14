package com.example.xfk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.xfk.mapper")
public class XfkApplication {

	public static void main(String[] args) {
		SpringApplication.run(XfkApplication.class, args);
	}

}

//  http://127.0.0.1:8080/








