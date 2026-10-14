package com.task3;

import java.util.Map;

public class RechargePlan 
{
	private Map<String ,Integer> value;

	public Map<String, Integer> getValue() {
		return value;
	}

	public void setValue(Map<String, Integer> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RechargePlan [value=" + value + "]";
	}
	
	
}
