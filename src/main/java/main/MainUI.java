package main;

import ConstField.SharedInfo;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.swing.*;

public class MainUI extends JFrame {

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        SharedInfo.AC = new ClassPathXmlApplicationContext("applicationContext.xml");

    }
}
