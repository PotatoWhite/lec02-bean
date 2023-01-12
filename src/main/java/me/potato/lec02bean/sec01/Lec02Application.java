package me.potato.lec02bean.sec01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Lec02Application {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        // get bean by type - name
        var name = context.getBean(String.class);
        System.out.println("name : " + name);

        // get bean by type - age
        var age = context.getBean(int.class);
        System.out.println("age : " + age);

        // get bean by type - person
        var person1 = context.getBean("person1", Person.class);
        var person2 = context.getBean("person2", Person.class);
        var person3 = context.getBean("person3parameters", Person.class);

        System.out.println("person1 : " + person1);
        System.out.println("person2 : " + person2);
        System.out.println("person3 : " + person3);
    }

}
