package com.tongwii.controller;

import com.gexin.fastjson.JSONObject;
import com.tongwii.domain.RoleEntity;
import com.tongwii.service.RoleService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by admin on 2017/10/17.
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取角色信息
     */
    @GetMapping("/getRoleInfo")
    public ResponseEntity getRoleInfo(){
        List<RoleEntity> roleEntities = roleService.findAll();
        JSONArray roleArray = new JSONArray();
        for(RoleEntity r: roleEntities){
            JSONObject role = new JSONObject();
            role.put("roleName", r.getName());
            role.put("roleCode", r.getCode());
            role.put("roleId", r.getId());
            role.put("roleDesc", r.getName());
            roleArray.add(role);
        }
        return ResponseEntity.ok(roleArray);
    }
}
