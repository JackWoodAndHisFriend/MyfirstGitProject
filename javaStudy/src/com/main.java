package com;

/**
 * Author: Tenno
 * Date: 2022-05-30 11:49
 */
//https://zhuanlan.zhihu.com/p/64147696
//https://javaguide.cn/java/basis/java-basic-questions-01.html#java-%E8%AF%AD%E8%A8%80%E6%9C%89%E5%93%AA%E4%BA%9B%E7%89%B9%E7%82%B9
public class main {

    public static void main(String[] args) {
        String x = "通话";
        String y = "重地";
        System.out.println(String.format("%d,%d",x.hashCode(),y.hashCode()));
        System.out.println(x.equals(y));
    }
}




