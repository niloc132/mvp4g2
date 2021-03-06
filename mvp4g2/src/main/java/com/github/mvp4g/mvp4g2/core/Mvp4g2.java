package com.github.mvp4g.mvp4g2.core;

import elemental2.dom.DomGlobal;

import static java.util.Objects.isNull;

public class Mvp4g2 {

  private Mvp4g2() {
  }

  public static void log(String message) {
    String logging = System.getProperty("mvp4g2.logging");
    if (!isNull(logging)) {
      if ("true".equals(logging.toLowerCase())) {
        DomGlobal.window.console.log(message);
      }
    }
  }

}
