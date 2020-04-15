package club.banyuan.demo.redis;

public interface CacheService {
    void set(String key, Object value);

    <T> T get(String key);

    Boolean expire(String key, long sec);

    Long getExpireSec(String key);


    void flushdb();
}
