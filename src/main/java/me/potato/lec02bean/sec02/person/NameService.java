package me.potato.lec02bean.sec02.person;

import org.springframework.stereotype.Component;

@Component
public class NameService {
    public String getNameFromDatabase1() {
        return "potato";
    }

    public String getNameFromDatabase2() {
        return "carrot";
    }
}
