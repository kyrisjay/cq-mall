package club.banyuan.demo.authentication.config;


import club.banyuan.demo.authentication.security.AuthenticationFailHandler;
import club.banyuan.demo.authentication.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/admin/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //自定义异常回复
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationFailHandler());

        http.authorizeRequests().anyRequest().authenticated();
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter();
        beanFactory.autowireBean(jwtAuthenticationFilter);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }

}
