import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 모든 오리진 허용, 실제 사용 시 필요에 따라 변경
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용되는 요청 메서드 설정
                .allowCredentials(true); // 필요에 따라 인증 정보를 허용할지 여부 설정
    }
}
