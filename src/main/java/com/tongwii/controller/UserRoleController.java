package com.tongwii.controller;

import com.tongwii.domain.Role;
import com.tongwii.domain.User;
import com.tongwii.domain.UserRole;
import com.tongwii.service.RoleService;
import com.tongwii.service.UserRoleService;
import com.tongwii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/17.
 */
@RestController
@RequestMapping("/user_role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 查询用户角色接口
     */
    @GetMapping("/findRoleByAccount/{account}")
    public ResponseEntity findRoleByAccount(@PathVariable String account){
        // 首先需要通过这个account查询user表，获取到userId
        User user = userService.findByAccount(account);
        // 接着通过获取的userId查询user_role表，获取用户的所有角色信息
        List<UserRole> userRoleEntities = userRoleService.findByUserId(user.getId());
        try{
            List<Map> roleArray = new ArrayList<>();
            for(UserRole u: userRoleEntities){
                Role roleEntity = roleService.findById(u.getRoleId());
                Map<String, Object> role = new HashMap<>();
                role.put("roleName", roleEntity.getName());
                role.put("roleCode", roleEntity.getCode());
                role.put("roleDesc", roleEntity.getDes());
                role.put("roleUser", user.getId());
                roleArray.add(role);
            }
            return ResponseEntity.ok(roleArray);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("用户角色信息有误!");
        }
    }

    /**
     * 添加用户角色
     */
    @PostMapping("/addUserRole")
    public ResponseEntity addUserRole(@RequestBody UserRole userRole){
        // 首先获取传来的userId与roleId
        // 然后通过userId查询其绑定的所有角色信息，并且与传来的roleid作对比排除操作
        List<UserRole> userRoleEntities = userRoleService.findByUserId(userRole.getUserId());
        try{
            for(UserRole u: userRoleEntities){
                if(u.getRoleId().equals(userRole.getRoleId())){
                    return ResponseEntity.badRequest().body("该用户已有该角色!");
                }
            }
            // 倘若用户没有传来的角色，就进行添加操作
            userRoleService.save(userRole);
            return ResponseEntity.ok("角色添加成功!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("用户不存在!");
        }
    }

    /**
     * 删除用户角色
     */
    @PutMapping("/deleteUserRole")
    public ResponseEntity deleteUserRole(@RequestBody UserRole userRole){
        // 首先得获取传来的用户的所有角色信息
        // 然后看看这些角色中是否包括传来的角色信息，如果包括，则可删除，否则不可删除
        List<UserRole> userRoleEntities = userRoleService.findByUserId(userRole.getUserId());
        try{
            for(UserRole u: userRoleEntities){
                if(u.getRoleId().equals(userRole.getRoleId())){
                    userRole.setId(u.getId());
                    userRoleService.delete(userRole);
                    return ResponseEntity.ok("角色删除成功!");
                }
            }
            // 倘若用户没有传来的角色，就报错误信息
            return ResponseEntity.badRequest().body("该用户无法删除此角色!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("用户不存在!");
        }
    }

}
