package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Essay;

import services.EssayService;

@Controller
@RequestMapping("/essay")
public class EssayController extends AbstractController{
	//Services --------------------------------------------------
	@Autowired
	private EssayService essayService;
	//Listing------------------------
	@RequestMapping(value= "/listPublished", method = RequestMethod.GET)
	public ModelAndView listMyEssays(@RequestParam int contestId){
		ModelAndView result;
	    Collection<Essay> essays=essayService.publishedEssaysByContest(contestId);
	    String requestURI="essay/listPublished.do";
	    
	    result = new ModelAndView("essay/listPublished");
	    result.addObject("essays",essays);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
	
	
	@RequestMapping(value= "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int essayId){
		ModelAndView result;
		Essay essay=essayService.findOne(essayId);
		Assert.notNull(essay);
		result=createEditModelAndView(essay);
		boolean create=false;
		result.addObject("create", create);
		return result;
	}
	
	// ---------------------- Ancillary methods ----------------------
		protected ModelAndView createEditModelAndView(Essay essay){
			ModelAndView result;
			result = createEditModelAndView(essay, null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Essay essay, String message){
			ModelAndView result;
			boolean isExpired=essayService.isExpired(essay);
			String replace="contest/list.do";
			
			result = new ModelAndView("essay/details");
			result.addObject("essay", essay);
			result.addObject("message", message);
			result.addObject("isExpired",isExpired);
			result.addObject("replace",replace);
			return result;
		}
}
