package com.ddn.stock.com.ddn.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenzi on 5/31/2016.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
  @RequestMapping("/world")
  public String helloWorld() {
    return "<h1>hello world</h1>";
  }
}
