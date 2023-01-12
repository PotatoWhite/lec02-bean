package me.potato.lec02bean.sec02.person;

import me.potato.lec02bean.sec01.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private NameService nameService;

    @Autowired
    private AgeService ageService;

    public Person getPerson01() {
        return new Person(nameService.getNameFromDatabase1(), ageService.getAgeFromDatabase1());
    }

    public Person getPerson02() {
        return new Person(nameService.getNameFromDatabase2(), ageService.getAgeFromDatabase2());
    }
}
