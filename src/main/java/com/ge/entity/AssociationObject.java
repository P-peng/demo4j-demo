package com.ge.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 外键对象简单封装
 * @author dengzhipeng
 * @date 2019/06/14
 */
@Setter
@Getter
public class AssociationObject {
    private String property;
    private String columnPrefix;
    private String javaType;
}

