package play.board1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import play.board1.common.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login/signIn",
                        "/login/signUp",
                        "/post/postList",
                        "/login/checkId",
                        "/post/selectPostList",
                        "/post/selectComment",
                        "/post/postView/**",
                        "/login/logout",
                        "/css/**",
                        "/js/**");

    }
}
