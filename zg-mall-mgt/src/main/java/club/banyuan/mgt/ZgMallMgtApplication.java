package club.banyuan.mgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "club.banyuan.mgt")
public class ZgMallMgtApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZgMallMgtApplication.class, args);
    }
}