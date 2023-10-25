package edu.whu.springbootdemo.common;

import lombok.Data;

@Data
public class CustomException extends Exception {

    public final static int INPUT_ERROR = 100;  //定义各种错误代码常量

    int code; //自定义的错误代码

    public CustomException(int code,String message){
        super(message);
        this.code=code;
    }
}
