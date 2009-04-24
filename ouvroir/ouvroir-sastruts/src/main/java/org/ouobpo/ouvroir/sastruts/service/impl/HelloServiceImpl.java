package org.ouobpo.ouvroir.sastruts.service.impl;

import java.text.SimpleDateFormat;

import org.ouobpo.ouvroir.sastruts.service.HelloService;

import com.domainlanguage.timeutil.SystemClock;

public class HelloServiceImpl implements HelloService {
  public String say() {
    return String.format(
        "こんにちは、今 %s です。",
        new SimpleDateFormat("HH時mm分ss秒").format(SystemClock.now().asJavaUtilDate()));
  }
}
