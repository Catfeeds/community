package com.tongwii.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-26
 */
@Data
public class RoleDTO implements Serializable {
    private String id;
    private String name;
    private String code;
    private String des;
}
