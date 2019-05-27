package com.sky.blind.service;

import com.github.pagehelper.PageHelper;
import com.sky.blind.dao.AdminMapper;
import com.sky.blind.dao.UserMapper;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import com.sky.blind.service.exception.SMSException;
import com.sky.blind.service.exception.UsernameAlreadyExistsException;
import com.sky.blind.utils.OnLineRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;

    @Autowired
    public UserService(UserMapper userMapper, AdminMapper adminMapper) {
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有记录
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 根据ID查询记录
     */
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 修改记录
     */
    public void update(User user) {
        userMapper.update(user);
    }

    /**
     * 添加记录
     */
    public void save(User user) {
        Admin admin = adminMapper.findAdminById(user.getAdminId());
        Date date = new Date();
        String username = admin.getUsername();
        user.setWeixin(admin.getWeixin());
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        user.setCreatedUser(username);
        user.setModifiedUser(username);
        userMapper.insert(user);
    }

    /**
     * 删除记录
     */
    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页查询
     *
     * @return List<User>
     */
    public List<User> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        Collections.shuffle(users);
        return users;
    }

    /**
     * 虚拟在线人数
     */
    public Integer findOnline() {
        return OnLineRunner.onLineNumber;
    }

    /**
     * 发送验证码
     */
    public void sendSms(String mobile) {
        String checkCokd = (String) redisTemplate.opsForValue().get("userSmsCode" + mobile);
        if (checkCokd != null) {
            throw new SMSException("请两分钟后再试");
        }
        checkCokd = RandomStringUtils.randomNumeric(6);
//        SMSUtil.mobileQuery(mobile, checkCokd, "160984");
        System.out.println(checkCokd);
        redisTemplate.opsForValue().set("userSmsCode" + mobile, checkCokd, 2, TimeUnit.MINUTES);
    }

    /**
     * user register
     */
    public void register(Admin admin, String code) {
        Admin findUser = adminMapper.findByUsername(admin.getUsername());
        if (findUser != null) {
            throw new UsernameAlreadyExistsException("用户已存在");
        }
        String checkCokd = (String) redisTemplate.opsForValue().get("userSmsCode" + admin.getMobile());
        if (code == null) {
            throw new SMSException("请点击获取验证码");
        }
        if (checkCokd == null) {
            throw new SMSException("验证码已过期,请重新获取");
        }
        if (!code.equals(checkCokd)) {
            throw new SMSException("验证码输入不正确");
        }
        Date date = new Date();
        admin.setIsAdmin(0);
        admin.setModifiedTime(date);
        String username = admin.getUsername();
        admin.setModifiedUser(username);
        admin.setCreatedTime(date);
        admin.setCreatedUser(username);
        adminMapper.insert(admin);
        //保存用户详情
        Admin data = adminMapper.findByUsername(admin.getUsername());
        User user = new User();
        user.setWeixin(data.getWeixin());
        user.setAdminId(data.getId());
        user.setModifiedUser(username);
        user.setNickname(username);
        user.setAge(23);
        user.setUserFace("");
        user.setModifiedTime(date);
        user.setCreatedTime(date);
        user.setCreatedUser(username);
        userMapper.insert(user);
    }

    /**
     * check username
     */
    public void checkUsername(String username) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null) {
            throw new UsernameAlreadyExistsException("用户名已存在");
        }
    }
}
