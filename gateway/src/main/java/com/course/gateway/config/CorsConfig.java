package com.course.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 冬生草
 * @Date: 2020/12/10 21:11
 * @Description: 跨域问题
 */
/*@Configuration*/
public class CorsConfig {
   /* @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        CorsConfiguration configuration = new CorsConfiguration();
//       配置跨域
        configuration.setAllowCredentials(true);
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add(CorsConfiguration.ALL);
        configuration.setAllowedOriginPatterns(allowedOriginPatterns);

        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);

        configurationSource.registerCorsConfiguration("/**", configuration);
        return new CorsWebFilter(configurationSource);
    }*/
}
