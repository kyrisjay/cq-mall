package club.banyuan.demo.redis;

import club.banyuan.demo.redis.user.JacksonUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface UserService {


    @Cacheable(value = "user", key = "#username")
    JacksonUser getUser(String username);

    @CachePut(value = "user", key = "#username")
    JacksonUser updateUser(String username, String password);

    @CacheEvict(value = "user", key = "#username")
    boolean deleteUser(String username, String password);
}
