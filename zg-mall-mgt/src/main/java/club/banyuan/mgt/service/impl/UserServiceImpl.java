package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.service.UserService;
import club.banyuan.mgt.user.JacksonUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserService userService;

    @Override
    @Cacheable(value = "user", key = "#username")
    public JacksonUser getUser(String username) {
        return new JacksonUser(username, ((int) (Math.random() * 100000)) + "");
    }

    @Override
    @CachePut(value = "user", key = "#username")
    public JacksonUser updateUser(String username, String password) {
        JacksonUser rlt = getUser(username);
        rlt.setPassword(password);
        return rlt;
    }

    @Override
    @CacheEvict(value = "user", key = "#username")
    public boolean deleteUser(String username, String password) {
        // 如果直接调用getUser 注解将失效，因为直接调用，不会走动态代理类的方法
        return userService.getUser(username).getPassword().equals(password);
    }
}