package club.banyuan.demo.redis.service.impl;

import club.banyuan.demo.redis.RedisCacheServiceImpl;
import club.banyuan.demo.redis.UserService;
import club.banyuan.demo.redis.user.JacksonUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCacheTest {
    @Autowired
    private JacksonUser jacksonUser;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCacheServiceImpl cacheService;

    @Test
    public void cacheUser() throws ClassNotFoundException {
        JacksonUser user = new JacksonUser("admin", "12345");

        cacheService.set("user", user);

        JacksonUser cachedUser = cacheService.get("user");
        Assert.assertEquals(user, cachedUser);

    }

    @Test
    public void cacheAble() {
        JacksonUser user1 = jacksonUser.jacksonUser();
        JacksonUser user2 = jacksonUser.jacksonUser();
        Assert.assertEquals(user1, user2);
    }

    @Before
    public void clearRedis() {
        cacheService.flushdb();
    }

    @Test
    public void cachePut() {
        JacksonUser admin = userService.getUser("admin");
        Assert.assertNotEquals("123456", admin.getPassword());
        Assert.assertEquals(admin, userService.getUser("admin"));
        JacksonUser other = userService.getUser("other");
        Assert.assertNotEquals(admin, other);
        userService.updateUser("admin", "123456");
        JacksonUser adminUpdate = userService.getUser("admin");
        Assert.assertNotEquals(adminUpdate, admin);
        Assert.assertEquals(adminUpdate.getPassword(), "123456");
    }

    @Test
    public void cacheEvict() {
        JacksonUser admin = userService.getUser("admin");
        Assert.assertNotEquals("123456", admin.getPassword());
        Assert.assertEquals(admin, userService.getUser("admin"));
        JacksonUser other = userService.getUser("other");
        Assert.assertNotEquals(admin, other);
        userService.updateUser("admin", "123456");
        JacksonUser adminUpdate = userService.getUser("admin");
        Assert.assertNotEquals(adminUpdate, admin);
        Assert.assertEquals(adminUpdate.getPassword(), "123456");

        Assert.assertTrue(userService.deleteUser("admin", "123456"));
        Assert.assertFalse(userService.deleteUser("admin", "123456"));

    }
}
