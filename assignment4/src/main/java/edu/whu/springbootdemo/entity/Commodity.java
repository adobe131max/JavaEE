package edu.whu.springbootdemo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Commodity implements Serializable {

    private Long id;

    private Integer number;

    private BigDecimal price;

    private String name;

    private String description;

    private Integer status;

    private Integer sort;
}
