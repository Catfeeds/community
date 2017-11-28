package com.tongwii.controller;

import com.tongwii.domain.Role;
import com.tongwii.domain.User;
import com.tongwii.service.UserService;
import com.tongwii.vm.UserRoleVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by admin on 2017/10/17.
 */
@RestController
@RequestMapping("/api/user_role")
public class UserRoleController {

    private final UserService userService;

    public UserRoleController(UserService userService) {this.userService = userService;}

    /**
     * 查询用户角色接口
     */
    @GetMapping("/findRoleByAccount/{account}")
    public ResponseEntity findRoleByAccount(@PathVariable String account){
        // 首先需要通过这个account查询user表，获取到userId
        User user = userService.findByAccount(account);
        // 接着通过获取的userId查询user_role表，获取用户的所有角色信息
        Set<Role> roles = user.getRoles();
        try{
            List<Map> roleArray = new ArrayList<>();
            for(Role role: roles){
                Map<String, Object> roleMap = new HashMap<>();
                roleMap.put("roleName", role.getName());
                roleMap.put("roleCode", role.getCode());
                roleMap.put("roleDesc", role.getDes());
                roleMap.put("roleUser", user.getId());
                roleArray.add(roleMap);
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
    public ResponseEntity addUserRole(@Valid @RequestBody UserRoleVM userRole){
        // 倘若用户没有传来的角色，就进行添加操作
        userService.addUserRole(userRole.getUserId(), userRole.getRoleCode());
        return ResponseEntity.ok("角色添加成功!");
    }

    /**
     * 删除用户角色
     */
    @PutMapping("/deleteUserRole")
    public ResponseEntity deleteUserRole(@Valid @RequestBody UserRoleVM userRole){
        // 倘若用户没有传来的角色，就进行添加操作
        userService.removeUserRole(userRole.getUserId(), userRole.getRoleCode());
        return ResponseEntity.ok("角色删除成功!");
    }

}
