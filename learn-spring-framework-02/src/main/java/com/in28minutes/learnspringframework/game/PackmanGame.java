package com.in28minutes.learnspringframework.game;

import org.springframework.stereotype.Component;

@Component
public class PackmanGame implements GamingConsole{

    @Override
    public void up() {
        System.out.println("Packman Up!");
    }

    @Override
    public void down() {
        System.out.println("Packman Down!");
    }

    @Override
    public void left() {
        System.out.println("Packman move Left!");
    }

    @Override
    public void right() {
        System.out.println("Packman move Right!");
    }
}
