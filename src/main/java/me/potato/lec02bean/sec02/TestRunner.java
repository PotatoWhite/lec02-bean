package me.potato.lec02bean.sec02;

import me.potato.lec02bean.sec02.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements ApplicationRunner {
    @Autowired
    private PersonService personService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(personService.getPerson01());
        System.out.println(personService.getPerson02());
    }
}
