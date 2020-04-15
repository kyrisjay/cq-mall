package club.banyuan.demo.redis.config;

import club.banyuan.demo.redis.user.JacksonUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public JacksonUser jacksonUser() {
        return new JacksonUser();
    }

}
