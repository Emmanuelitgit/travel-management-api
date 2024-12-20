package travel_management_system.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import travel_management_system.Interceptors.Interceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // interceptors need to be explicitly registered with WebMvcConfigurer to be recognized by spring.
    // this can be done by calling the addInterceptors method from the  WebMvcConfigurer and add the configured interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor()).addPathPatterns("/**");
    }
}