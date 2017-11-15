package com.tongwii.controller;

import com.tongwii.domain.Role;
import com.tongwii.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/17.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取角色信息
     */
    @GetMapping("/getRoleInfo")
    public ResponseEntity getRoleInfo(){
        List<Role> roleEntities = roleService.findAll();
        List<Map> roleArray = new ArrayList();
        for(Role r: roleEntities){
            Map<String, Object> role = new HashMap<>();
            role.put("roleName", r.getName());
            role.put("roleCode", r.getCode());
            role.put("roleId", r.getId());
            role.put("roleDesc", r.getName());
            roleArray.add(role);
        }
        return ResponseEntity.ok(roleArray);
    }
}
