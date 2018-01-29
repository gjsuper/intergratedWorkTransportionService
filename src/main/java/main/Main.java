package main;

import ConstField.SharedInfo;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
       SharedInfo.AC = new ClassPathXmlApplicationContext("applicationContext.xml");

    }
}
