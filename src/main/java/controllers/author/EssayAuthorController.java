package controllers.author;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.EssayService;

import controllers.AbstractController;
import domain.Essay;

@Controller
@RequestMapping("/essay/author")
public class EssayAuthorController extends AbstractController{
	// ----------------------- Managed service -----------------------
	@Autowired
	private EssayService essayService;
	// ------------------------- Constructor -------------------------
	// -------------------------   UseCases  -------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listByAuthor(){
		ModelAndView result;
		Collection<Essay> essays=essayService.findByAuthor();
		String requestURI="essay/author/list.do";
		
		result = new ModelAndView("essay/list");
		result.addObject("essays",essays);
		result.addObject("currentDate",new Date());
		result.addObject("requestURI",requestURI);
		return result;
	}
	@RequestMapping(value= "/listMyEssays", method = RequestMethod.GET)
	public ModelAndView listMyEssays(@RequestParam int contestId){
		ModelAndView result;
	    Collection<Essay> essays=essayService.submittedEssaysByContest(contestId);
	    System.out.println(essays);
	    String requestURI="essay/author/listMyEssays.do";
	    
	    result = new ModelAndView("essay/listMyEssays");
	    result.addObject("essays",essays);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	@RequestMapping(value= "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int contestId){
		ModelAndView result;
		Essay essay=essayService.create(contestId);
		Assert.notNull(essay);
		result=createEditModelAndView(essay);
		boolean create=true;
		result.addObject("create", create);
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
	@RequestMapping(value = "/details", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Essay essay, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = createEditModelAndView(essay);
		else{
			try{
				essayService.save(essay);
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(essay, "essay.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Essay essay, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = createEditModelAndView(essay);
		else{
			try{
				essayService.save(essay);
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(essay, "essay.commit.error");
			}
		}
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
		String replace="essay/author/list.do";
		
		result = new ModelAndView("essay/author/details");
		result.addObject("essay", essay);
		result.addObject("message", message);
		result.addObject("isExpired",isExpired);
		result.addObject("replace",replace);
		return result;
	}
}
