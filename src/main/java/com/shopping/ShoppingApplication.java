package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@으로 시작하는 항목들을 어노테이션(Annotation)이라고 합니다.
클래스나 메소드, 변수등에 작성하여 어드바이스를 해주는 고급 주석
*/

@SpringBootApplication
public class ShoppingApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}
}
