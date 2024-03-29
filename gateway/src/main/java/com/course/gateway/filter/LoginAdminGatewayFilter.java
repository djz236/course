package com.course.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class LoginAdminGatewayFilter implements
        GatewayFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        //请求地址中不包含/admin/的，不是控台请求，不需要拦截
        if (!path.contains("/admin/")) {
            return chain.filter(exchange);
        }
        if (path.contains("/system/admin/user/login")
                || path.contains("/system/admin/user/logout")
                || path.contains("/system/admin/kaptcha")) {
            return chain.filter(exchange);
        }
        //获取header的token参数
        String token = exchange.getRequest().getHeaders()
                .getFirst("token");
        if (token == null || token.isEmpty()) {
            log.info("token为空，请求被拦截");
            exchange.getResponse().setStatusCode(
                    HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        Object object = redisTemplate.opsForValue().get(token);
        if (object == null) {
            log.warn("token无效");
            exchange.getResponse().setStatusCode(
                    HttpStatus.UNAUTHORIZED
            );
            return exchange.getResponse().setComplete();
        } else {
            log.info("已登录");
            //增加权限校验，gateway里没有LoginuserDto  所以全部用json操作
            boolean exist = false;
            JSONObject loginUserDto = JSON.parseObject(String.valueOf(object));
            JSONArray requests = loginUserDto.getJSONArray("requests");
            //遍历所有 权限请求 判断当前请求的地址是否在【权限请求】里
            for (int i = 0, l = requests.size(); i < l; i++) {
                String request = (String) requests.get(i);
                if (path.contains(request)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                log.info("权限校验通过");
            } else {
                log.info("权限校验未通过:"+path);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
