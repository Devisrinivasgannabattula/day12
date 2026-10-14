package com.nit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Welcome {
	@GetMapping("/vasu")
	public String welcome()
	{
		return "index";
	}
}
