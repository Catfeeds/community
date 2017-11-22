package com.tongwii.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *用户角色VM
 *
 * @author Zeral
 * @date 2017-11-22
 */
@Data
public class UserRoleVM {
    // 用户id
    @NotNull
    private String userId;
    // 角色id
    @NotNull
    private String roleCode;
    // 角色描述
    private String roleDes;
}
