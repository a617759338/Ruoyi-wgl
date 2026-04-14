package com.ruoyi.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import com.ruoyi.gateway.handler.SentinelFallbackHandler;

/**
 * 网关限流配置
 * 
 * @author ruoyi
 */
@Configuration
public class GatewayConfig
{

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)  // Sets the highest precedence for this exception handler
    public SentinelFallbackHandler sentinelGatewayExceptionHandler()
    {
        return new SentinelFallbackHandler();  // Creates and returns a new instance of SentinelFallbackHandler
    }
}