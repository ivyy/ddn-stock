package com.ddn.stock.util;

public class MathUtil {
  public static double round(double number, int precision) {
    //get_double = (double)(Math.round(result_value*100)/100.0)
    int n = 1;
    for (int i = 0; i < precision; i++) {
      n *= 10;
    }
    return Math.round(number * n)/n;
  }


}
