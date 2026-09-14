package com.todoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.util.Scanner;

@Configuration
public class Appconfig {
    @Bean
    public Scanner Scanner(){
        return new Scanner(System.in);
    }

    @Bean
    public PrintStream out(){
        return System.out;
    }
}
