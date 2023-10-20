package com.example.demo.prime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrimeController {

	@Autowired
	private PrimeService primeService;
	
	@GetMapping("/prime")
	public String getPrime() {
		return "prime/input";
	}
	
	@PostMapping("/prime")
	public String postPrime(Model model,@RequestParam("num") String input) {
		
		String ans = primeService.exec(input);
		model.addAttribute("ans", ans);
		return "prime/result";
	}
}
