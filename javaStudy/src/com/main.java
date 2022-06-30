package com;

/**
 * Author: Tenno
 * Date: 2022-05-30 11:49
 */
//https://zhuanlan.zhihu.com/p/64147696
public class main {

    public static void main(String[] args) {
        String x = "通话";
        String y = "重地";
        System.out.println(String.format("%d,%d",x.hashCode(),y.hashCode()));
        System.out.println(x.equals(y));
    }
}
