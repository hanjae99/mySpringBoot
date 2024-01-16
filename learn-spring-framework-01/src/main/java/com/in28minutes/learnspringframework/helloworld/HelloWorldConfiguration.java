package com.in28minutes.learnspringframework.helloworld;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

record Person(String name, int age, Address address){};

record Address(String firstLine, String city){};

@Configuration
public class HelloWorldConfiguration {

    @Bean
    public String name(){
        return "kildong";
    }

    @Bean
    public int age(){
        return 25;
    }

    @Bean
    public Person person(){
        var person = new Person("hanjae", 26, new Address("Haewoondae", "Busan"));
        return person;
    }

    @Bean
    public Person person2MethodCall(){
        return new Person(name(), age(), address());
    }

    @Bean
    public Person person3Parameters(String name, int age, Address customAddress2){
        return new Person(name, age, customAddress2);
    }

    @Bean
    @Primary
    public Person person4Parameters(String name, int age, Address address){
        return new Person(name, age, address);
    }

    @Bean
    public Person person5Qualifier(String name, int age, @Qualifier("customAddress2Qualifier") Address address){
        return new Person(name, age, address);
    }

    @Bean(name = "customAddress")
    @Primary //Spring 에게 빈이 여러개일 경우 기본값으로 참조하도록 알려줌
    public Address address(){
        return new Address("Enpyeong", "Seoul");
    }

    @Bean(name = "customAddress2")
    @Qualifier("customAddress2Qualifier")
    public Address address2(){
        return new Address("Gangreung", "Gangwon-do");
    }
}
