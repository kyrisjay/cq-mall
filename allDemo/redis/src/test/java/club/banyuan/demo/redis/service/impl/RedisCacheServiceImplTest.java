package club.banyuan.demo.redis.service.impl;

import club.banyuan.demo.redis.RedisCacheServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheServiceImplTest {
    @Autowired
    private RedisCacheServiceImpl cacheService;

    @Test
    public void getSetTest() {
        cacheService.set("test", "admin");
        Assert.assertEquals("admin", cacheService.get("test"));
    }

    @Test
    public void expire() throws InterruptedException {
        String key = "test";
        String value = "testval";

        cacheService.set(key, value);
        cacheService.expire(key, 3);
        Assert.assertTrue(cacheService.getExpireSec(key) < 3);
        Assert.assertEquals(value, cacheService.get(key));

        Thread.sleep(3000);
        Assert.assertNull(cacheService.get(key));
    }
}
