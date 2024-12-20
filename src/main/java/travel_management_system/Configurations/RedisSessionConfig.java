//package travel_management_system.Configurations;
//
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.session.web.http.CookieHttpSessionIdResolver;
//import org.springframework.session.web.http.HttpSessionIdResolver;
//
//@Configuration
//@EnableRedisHttpSession
//public class RedisSessionConfig {
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        // Configure your Redis connection factory (Lettuce or Jedis)
//        return new LettuceConnectionFactory("localhost", 6379);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        return template;
//    }
//}
