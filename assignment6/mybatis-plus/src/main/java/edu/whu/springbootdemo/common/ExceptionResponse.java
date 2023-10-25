package edu.whu.springbootdemo.common;

import lombok.Data;

@Data
public class ExceptionResponse {

    private Integer code;

    private String message;
}
