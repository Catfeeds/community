package com.tongwii.service;

import com.tongwii.constant.AuthoritiesConstants;
import com.tongwii.constant.UserConstants;
import com.tongwii.dao.IDeviceDao;
import com.tongwii.dao.IUserDao;
import com.tongwii.domain.Device;
import com.tongwii.domain.File;
import com.tongwii.domain.Role;
import com.tongwii.domain.User;
import com.tongwii.dto.UserDTO;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.security.SecurityUtils;
import com.tongwii.vm.LoginVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户Service
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
@Service
@Transactional
public class UserService {
    private final IUserDao userDao;
    private final FileService fileService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final IDeviceDao deviceDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDao userDao, FileService fileService, UserMapper userMapper, RoleService roleService,
                       IDeviceDao deviceDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.deviceDao = deviceDao;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(UserDTO userDTO, String password) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setAccount(userDTO.getAccount());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        // 新用户默认激活
        newUser.setActivated(true);
        if (newUser.getLangKey() == null) {
            newUser.setLangKey(UserConstants.DEFAULT_LANGUAGE); // 默认语言
        } else {
            newUser.setLangKey(userDTO.getLangKey());
        }
        Role role = roleService.findRoleByCode(AuthoritiesConstants.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);
        newUser.setAddTime(new Date());
        userDao.save(newUser);
        return userMapper.userToUserDTO(newUser);
    }


    public User findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    public String updateUserAvatarById(String userId, MultipartFile multipartFile) {
        File file = fileService.saveAndUploadFileToFTP(userId, multipartFile);
        userDao.updateAvatarById(userId, file.getFilePath());
        return file.getFilePath();
    }

    @Transactional(readOnly = true)
    public User findById(String createUserId) {
        return userDao.findOne(createUserId);
    }

    @Transactional(readOnly = true)
    public User getUserWithDevicesById(String id) {
        return userDao.findOneWithDevicesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithRolesByAccount(String account) {
        return userDao.findOneWithRolesByAccount(account);
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneByAccount(String account) {
        return userDao.findOneByAccount(account);
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    public void update(User user) {
        userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userDao.findAll(pageable).map(userMapper::userToUserDTO);
    }


    /**
     * Update basic information (account, nickname, name, language) for the current user.
     *
     * @param account account of user
     * @param nickName nickname of user
     * @param name real name of user
     * @param langKey lang key of user
     */
    public void updateUser(String account, String nickName, String name, String langKey) {
        userDao.findOneByAccount(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setAccount(account);
            user.setNickName(nickName);
            user.setName(name);
            user.setLangKey(langKey);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userDao
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setAccount(userDTO.getAccount());
                user.setNickName(userDTO.getNickName());
                user.setName(userDTO.getName());
                user.setPhone(userDTO.getPhone());
                user.setSex(userDTO.getSex());
                user.setIdCard(userDTO.getIdCard());
                user.setBirthday(userDTO.getBirthday());
                user.setActivated(userDTO.isActivated());
                user.setSignature(userDTO.getSignature());
                user.setLangKey(userDTO.getLangKey());
                Set<Role> managedAuthorities = user.getRoles();
                managedAuthorities.clear();
                userDTO.getRoles().stream()
                    .map(roleService::findRoleByCode)
                    .forEach(managedAuthorities::add);
                return user;
            })
            .map(userMapper::userToUserDTO);
    }

    /**
     * 根据roleCode添加用户角色
     *
     * @param userId 用户id
     * @param roleCode 角色code
     */
    public void addUserRole(String userId, String roleCode) {
        Optional.of(userDao.findOne(userId)).ifPresent(user -> {
            Set<Role> managedAuthorities = user.getRoles();
            managedAuthorities.add(roleService.findRoleByCode(roleCode));
        });
    }

    /**
     * 根据roleCode删除用户角色
     *
     * @param userId 用户id
     * @param roleCode 角色code
     */
    public void removeUserRole(String userId, String roleCode) {
        Optional.of(userDao.findOne(userId)).ifPresent(user -> {
            Set<Role> managedAuthorities = user.getRoles();
            managedAuthorities.remove(roleService.findRoleByCode(roleCode));
        });
    }

    public void deleteUser(String account) {
        userDao.findOneByAccount(account).ifPresent(userDao::delete);
    }

    public void changePassword(String password) {
        userDao.findOneByAccount(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
        });
    }

    public User findByAccountAndUpdateDeviceId(LoginVM loginVM) {
        User user = userDao.findOneWithDevicesByAccount(loginVM.getAccount());
        if(CollectionUtils.isEmpty(user.getDevices()) && Objects.nonNull(loginVM.getDeviceId())) {
            Device device = new Device(loginVM.getDeviceId(), user);
            deviceDao.save(device);
        } else if (!StringUtils.isEmpty(loginVM.getDeviceId())) {
            if(!user.getDevices().stream().map(Device::getDeviceCode).collect(Collectors.toList()).contains(loginVM.getDeviceId())) {
                Device device = new Device(loginVM.getDeviceId(), user);
                deviceDao.save(device);
            }
        }
        return user;
    }

    public void updateUserDevices(String deviceId) {
        User user = userDao.findOneWithDevicesById(SecurityUtils.getCurrentUserId());
        if(user.getDevices().stream().map(Device::getDeviceCode).collect(Collectors.toList()).contains(deviceId)) {
            Optional<Device> device = user.getDevices().stream().filter(device1 -> device1.getDeviceCode().equals(deviceId)).findFirst();
            device.ifPresent(deviceDao::delete);
        }
    }
}
