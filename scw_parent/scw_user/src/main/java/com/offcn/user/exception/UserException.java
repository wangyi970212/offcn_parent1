package com.offcn.user.exception;

import com.offcn.user.enums.UserExceptionEnum;

public class UserException extends RuntimeException{
    /**
     * 单表用户异常
     */
    public UserException(UserExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
    }
}
