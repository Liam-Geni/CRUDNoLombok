package com.sparta.boardprac.dto;


import com.sparta.boardprac.dto.response.MemberResponseDto;

public class ResponseDto<T> {

    private boolean success;
    private T data;
    private Error error;

    //생성자
    public ResponseDto(boolean success, T data, Error error){
        this.success = success;
        this.data = data;
        this.error = error;
    }
    //getter
    public boolean getSuccess(){
        return this.success;
    }
    public T getData(){
        return this.data;
    }
    public Error getError(){
        return this.error;
    }

    //static success 메서드
    public static <T> ResponseDto <T> success(T data){
        return new ResponseDto<>(true, data, null);
    }

    //static fail 메서드
    public static <T> ResponseDto<T> fail(Integer httpStatus, String message, String detail){
        return new ResponseDto<>(false, null, new Error(httpStatus, message, detail));
    }

    static class Error{
        private Integer httpStatus;
        private String message;
        private String detail;


        //생성자
        public Error(Integer httpStatus, String message, String detail){
            this.httpStatus = httpStatus;
            this.message = message;
            this.detail = detail;
        }

        //Getter
        public Integer getHttpStatus(){
            return this.httpStatus;
        }
        public String getMessage(){
            return this.message;
        }
        public String getDetail(){
            return this.detail;
        }
    }

}
