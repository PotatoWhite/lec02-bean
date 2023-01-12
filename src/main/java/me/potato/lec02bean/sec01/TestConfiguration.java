package me.potato.lec02bean.sec01;

import org.springframework.context.annotation.Bean;

public class TestConfiguration {

    @Bean
    public String name() {
        return "Potato";
    }

    @Bean
    public int age() {
        return 40;
    }

    @Bean
    public Person person1() {
        return new Person("carrot", 30);
    }

    @Bean
    public Person person2() {
        return new Person(name(), age());
    }

    @Bean
    public Person person3parameters(String a, int b) {
        return new Person(a, b - 10);
    }

//    @Bean
//    public Person person4parameters(@Qualifier("name2") String name, int age) {
//        return new Person(name, age - 10);
//    }
//
//    @Bean
//    public String name2() {
//        return "tomato";
//    }
}
