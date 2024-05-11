package co.edu.eci.cvds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicAuthInterceptor basicAuthInterceptor;

    @Autowired
    // Constructor que inyecta el interceptor BasicAuthInterceptor
    public WebConfig(BasicAuthInterceptor basicAuthInterceptor) {
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    // Método para agregar interceptores al registro
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/login/protected/**")  // Rutas protegidas por el interceptor
                .excludePathPatterns("/static/**");       // Excluir rutas estáticas
    }

    @Override
    // Método para agregar manejadores de recursos estáticos
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")       // Ruta base para recursos estáticos
                .addResourceLocations("classpath:/static/");  // Ubicación de los recursos estáticos en el classpath
    }
}
