package com.itstaredu.scala06;

/**
 * @author: lzm
 * @date: 2019/3/6
 * @description:
 * @version: 1.0
 */
public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        return this.age > o.age ? 1 : -1;
    }
}
