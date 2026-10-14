package com.task3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
  public static void main(String[] args) {
    //System.out.println("Hello World!");
	  ApplicationContext context=new ClassPathXmlApplicationContext("com/task3/application-context.xml");
	  context.getBean(RechargePlan.class);
  }
}
