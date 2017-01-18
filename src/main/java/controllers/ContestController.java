package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Contest;

import services.ContestService;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController {
	//Services --------------------------------------------------
	@Autowired
	private ContestService contestService;
	
	//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView listAll() {
		ModelAndView result;
		
		Collection<Contest> contests;
		
		contests=contestService.findAll();
		String requestURI="contest/list.do";
			
		result = new ModelAndView("contest/list");
		result.addObject("contests",contests);
		result.addObject("requestURI",requestURI);
		return result;
	}
}
