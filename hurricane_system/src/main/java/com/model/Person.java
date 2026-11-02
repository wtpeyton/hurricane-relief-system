package com.model;

/**
 * Represents a person included in a relief request.
 * @author Olivia Casper
 */
public class Person {
    private String name;
    private int age;

    /**
     * Creates a person with a name and age.
     * @param name the person's name
     * @param age the person's age
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}