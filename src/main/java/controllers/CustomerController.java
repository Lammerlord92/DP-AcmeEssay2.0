/* CustomerController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Author;
import domain.Organiser;

import services.AuthorService;
import services.OrganiserService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {
	
	//Services --------------------------------------------------
	@Autowired
	private AuthorService authorService;
	@Autowired
	private OrganiserService organiserService;
	
	//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView listAll() {
		ModelAndView result;
		
		Collection<Organiser> organisers;
		Collection<Author> authors;
		
		organisers=organiserService.findAll();
		authors=authorService.findAll();
		String requestURI="customer/list.do";
			
		result = new ModelAndView("customer/list");
		result.addObject("organisers",organisers);
		result.addObject("authors", authors);
		result.addObject("requestURI",requestURI);
		return result;
	}
}