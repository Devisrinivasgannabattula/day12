package com.nit.springcore;

public class Car 
{
	private Engine engine;
	public void engine(Engine engine)
	{
		this.engine=engine;
	}
	public void display()
	{
		engine.start();
		System.out.println("Car is running..");
	}
}
