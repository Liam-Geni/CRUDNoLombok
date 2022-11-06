package com.sparta.boardprac.Exception;


import java.util.function.Supplier;

public class GlobalException extends RuntimeException implements Supplier<ErrorCode> {

    private final ErrorCode errorCode;

    //의존 주입
    public GlobalException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
    @Override
    public ErrorCode get() {
        return errorCode;
    }

    //Getter
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
