package com.shopping.exception;

// 재고 부족시 발생시킬 사용자 정의 예외 클래스
public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message){
        super(message);
    }
}
