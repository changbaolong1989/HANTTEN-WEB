package application;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;

/**
 * 核心主类
 *
 * @author JLF
 * Created in 2019/3/6 10:28
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.hd.*.dao")
@ComponentScan("com.hd")
@EnableCaching
public class Application extends SpringBootServletInitializer {

    /**
     * 启动Boot项目
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
    /**
     * 打包用
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

}
