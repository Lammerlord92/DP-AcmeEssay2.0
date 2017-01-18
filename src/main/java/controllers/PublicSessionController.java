package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PublicSessionService;

import domain.Essay;
import domain.PublicSession;

@Controller
@RequestMapping("/publicSession")
public class PublicSessionController {
	//Services --------------------------------------------------
	@Autowired
	private PublicSessionService sessionService;
	
	//Listing------------------------
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	public ModelAndView listAll(){
		ModelAndView result;
	    Collection<PublicSession> sessions=sessionService.findAll();
	    String requestURI="publicSession/list.do";
	    
	    result = new ModelAndView("publicSession/list");
	    result.addObject("sessions",sessions);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
	@RequestMapping(value= "/listByContest", method = RequestMethod.GET)
	public ModelAndView listByContest(@RequestParam int contestId){
		ModelAndView result;
	    Collection<PublicSession> sessions=sessionService.findByContestId(contestId);
	    String requestURI="publicSession/listByContest.do";
	    
	    result = new ModelAndView("publicSession/listByContest");
	    result.addObject("sessions",sessions);
	    result.addObject("requestURI",requestURI);
		return result;
	}
}
