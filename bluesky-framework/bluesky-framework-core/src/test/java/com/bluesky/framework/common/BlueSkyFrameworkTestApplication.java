package com.bluesky.framework.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author liyang
 */
@EnableAsync
@SpringBootApplication
@ImportResource("/bluesky/framework/appContext-domain.xml")
@PropertySource("classpath:application.properties")
@MapperScan("com.bluesky.framework.domain.infrastructure")
public class BlueSkyFrameworkTestApplication {

}
