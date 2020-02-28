package com.example.demo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ConditionVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 数据库字段名
     */
    private String column;
    /**
     * 字段值
     */
    private String value;
    /**
     * 连接类型，如llike,equals,gt,ge,lt,le
     */
    private String type;
}
