package controllers.organiser;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.EssayService;
import services.PublicSessionService;

import controllers.AbstractController;
import domain.Contest;
import domain.Essay;
import domain.PublicSession;

@Controller
@RequestMapping("essay/organiser")
public class EssayOrganiserController extends AbstractController{
	
	// ----------------------- Managed service -----------------------
	
	@Autowired
	private EssayService essayService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private PublicSessionService sessionService;
	
	// ------------------------- Constructor -------------------------
	
	// -------------------------   UseCases  -------------------------
	
	@RequestMapping(value= "/publish", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int essayId){
		ModelAndView result;
		
		try{
			essayService.publish(essayId);
			result= new ModelAndView("redirect:/contest/organiser/list.do");
		}catch(Throwable oops){
			result= new ModelAndView("redirect:/contest/organiser/list.do");
			result.addObject("message", "essay.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value= "/listAll", method = RequestMethod.GET)
	public ModelAndView listAllEssays(@RequestParam int contestId){
		ModelAndView result;
		Boolean isPublishable=false;
	    Collection<Essay> essays=essayService.submittedEssaysByContestOrganiser(contestId);
	    System.out.println(essays);
	    String requestURI="essay/organiser/listAll.do";
	    
	    Contest c=contestService.findOne(contestId);
	    if(c.getHoldingDate().before(new Date())){
	    	isPublishable=true;
	    }
	    
	    result = new ModelAndView("essay/organiser/listAll");
	    result.addObject("essays",essays);
	    result.addObject("isPublishable",isPublishable);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
	@RequestMapping(value= "/listBySession", method = RequestMethod.GET)
	public ModelAndView listBySession(@RequestParam int sessionId){
		Collection<Essay> essaysUsed = new LinkedList<Essay>();
		ModelAndView result;
		
	    PublicSession publicSession = sessionService.findOne(sessionId);
	    Assert.notNull(publicSession);
	    
	    Collection<Essay> essaysSel = publicSession.getEssays();
	    Collection<Essay> essaysRem = publicSession.getContest().getEssays();
	    
	    essaysRem.removeAll(essaysSel);
	    for(Essay aux : essaysRem)
	    	if(aux.getPublicSession()!= null)
	    		essaysUsed.add(aux);
	    essaysRem.removeAll(essaysUsed);
	    
	    String requestURI = "essay/organiser/listBySession.do";
	    
	    result = new ModelAndView("essay/organiser/listBySession");
	    result.addObject("essaysSelected", essaysSel);
	    result.addObject("essaysRemaining", essaysRem);
	    result.addObject("sessionId", sessionId);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	// ---------------------- Extra methods ----------------------
	
	@RequestMapping(value= "/addFromSession", method = RequestMethod.GET)
	public ModelAndView addFromSession(@RequestParam int essayId, @RequestParam int sessionId) {
		ModelAndView result;
		
		try{
			essayService.addToSession(essayId, sessionId);
			result= new ModelAndView("redirect:listBySession.do?sessionId="+sessionId);
		}catch(Throwable oops){
			result= new ModelAndView("redirect:listBySession.do?sessionId="+sessionId);
			result.addObject("message", "essay.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value= "/deleteFromSession", method = RequestMethod.GET)
	public ModelAndView deleteFromSession(@RequestParam int essayId, @RequestParam int sessionId) {
		ModelAndView result;
		
		try{
			essayService.deleteFromSession(essayId, sessionId);
			result= new ModelAndView("redirect:listBySession.do?sessionId="+sessionId);
		}catch(Throwable oops){
			result= new ModelAndView("redirect:listBySession.do?sessionId="+sessionId);
			result.addObject("message", "essay.commit.error");
		}
		return result;
	}
}