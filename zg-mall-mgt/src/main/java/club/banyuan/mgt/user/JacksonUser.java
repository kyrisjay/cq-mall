package club.banyuan.mgt.user;

import org.springframework.cache.annotation.Cacheable;

import java.util.Objects;

public class JacksonUser {
    private String username;
    private String password;
    private static int count;
    private static final String KEY = "database";

    public JacksonUser() {
    }

    public JacksonUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Cacheable(value = KEY, key = "'user'")
    public JacksonUser jacksonUser() {
        count++;
        return new JacksonUser("user" + count, "password" + count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JacksonUser user = (JacksonUser) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "JacksonUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
