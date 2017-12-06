package main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.transportationService.TransportationService;

public class Main {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
