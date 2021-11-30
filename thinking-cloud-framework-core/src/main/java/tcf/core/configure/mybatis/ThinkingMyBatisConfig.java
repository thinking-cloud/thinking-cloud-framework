package tcf.core.configure.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tcf.core.handler.MybatisLimitInterceptor;


/**
 * MyBatis的配置对象
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Configuration
public class ThinkingMyBatisConfig {
    /**
     * 添加mybatis分页拦截器
     * @return
     */    
    @Bean
    public MybatisLimitInterceptor limitInterceptor(){
        return new MybatisLimitInterceptor();
    }
}
