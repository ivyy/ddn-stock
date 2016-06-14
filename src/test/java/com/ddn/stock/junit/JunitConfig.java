package com.ddn.stock.junit;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.ddn.stock.util",
    "com.ddn.stock.service",
    "com.ddn.stock.indicator"
})
public class JunitConfig {
}
