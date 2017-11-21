package com.tongwii.controller;

import com.tongwii.domain.User;
import com.tongwii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by admin on 2017/10/17.
 */
@RestController
@RequestMapping("/api/user_role")
public class UserRoleController {
    @Autowired
    private UserService userService;

    /**
     * 查询用户角色接口
     */
    @GetMapping("/findRoleByAccount/{account}")
    public ResponseEntity findRoleByAccount(@PathVariable String account){
        User user = userService.findByAccount(account);
        return ResponseEntity.ok(user.getRoles());
    }

    /**
     * 添加用户角色
     */
    @PostMapping("/addUserRole")
    public void addUserRole(){
        // TODO 需要重写
        /*// 首先获取传来的userId与roleId
        // 然后通过userId查询其绑定的所有角色信息，并且与传来的roleid作对比排除操作
        List<UserRole> userRoleEntities = userRoleService.findByUserId(userRole.getUserId());
        try{
            for(UserRole u: userRoleEntities){
                if(u.getRoleId().equals(userRole.getRoleId())){
                    return ResponseEntity.badRequest().body("该用户已有该角色!");
                }
            }
            // 倘若用户没有传来的角色，就进行添加操作
            userRoleService.register(userRole);
            return ResponseEntity.ok("角色添加成功!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("用户不存在!");
        }*/
    }

    /**
     * 删除用户角色
     */
    @PutMapping("/deleteUserRole")
    public void deleteUserRole(){
        // TODO 需要重写
    }

}
