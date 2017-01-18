package controllers.organiser;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import security.UserAccount;
import services.ContestService;
import services.OrganiserService;
import services.PublicSessionService;
import domain.Contest;
import domain.Essay;
import domain.Organiser;
import domain.PublicSession;

@Controller
@RequestMapping("publicSession/organiser")
public class PublicSessionOrganiserController {
	
	//Services --------------------------------------------------
	
	@Autowired
	private PublicSessionService sessionService;
	@Autowired
	private OrganiserService organiserService;
	@Autowired
	private ContestService contestService;
	
	//Listing----------------------------------------------------
	
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	public ModelAndView listAllPublicSessions(){
		ModelAndView result;
		UserAccount account=LoginService.getPrincipal();
		Organiser organiser=organiserService.findByUserAccount(account);
		
	    Collection<PublicSession> sessions=sessionService.findByOrganiserId(organiser.getId());
	    String requestURI="publicSession/organiser/list.do";
	    
	    result = new ModelAndView("publicSession/organiser/list");
	    result.addObject("sessions",sessions);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
	@RequestMapping(value= "/listInactive", method = RequestMethod.GET)
	public ModelAndView listInactiveSessions(){
		ModelAndView result;
		UserAccount account=LoginService.getPrincipal();
		Organiser organiser=organiserService.findByUserAccount(account);
		
	    Collection<PublicSession> sessions=sessionService.findInactive(organiser.getId());
	    String requestURI="publicSession/organiser/listInactive.do";
	    
	    result = new ModelAndView("publicSession/organiser/listInactive");
	    result.addObject("sessions",sessions);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
	@RequestMapping(value= "/listByContest", method = RequestMethod.GET)
	public ModelAndView listByContest(@RequestParam int contestId){
		ModelAndView result;
	    Collection<PublicSession> sessions=sessionService.findByContestId(contestId);
	    
	    String requestURI="publicSession/organiser/listByContest.do";
	    
	    result = new ModelAndView("publicSession/organiser/listByContest");
	    result.addObject("sessions",sessions);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
	// ---------------------------- Create ---------------------------
	
	@RequestMapping(value= "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int contestId) {
		ModelAndView result;
		
		PublicSession publicSession = new PublicSession();
		Contest contest = contestService.findOne(contestId);
		publicSession.setContest(contest);
		Collection<Essay> essays = new LinkedList<Essay>();
		publicSession.setEssays(essays);
		
		
		result = createEditModelAndView(publicSession);
		return result;
	}
	
	// ----------------------------- Edit ----------------------------
	
	@RequestMapping(value= "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int publicSessionId) {
		ModelAndView result;

		PublicSession publicSession = sessionService.findOne(publicSessionId);
		Assert.notNull(publicSession);
		
		result = createEditModelAndView(publicSession);
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PublicSession publicSession, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = createEditModelAndView(publicSession);
		else{
			try{
				publicSession.conditionsOk(publicSession);
				PublicSession retrievedFromDB = sessionService.save(publicSession);
				
				String url = "redirect:/essay/organiser/listBySession.do?sessionId="+retrievedFromDB.getId();
				result = new ModelAndView(url);
			}catch(Throwable oops){
				result = createEditModelAndView(publicSession, "publicSession.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value= "/delete", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam int sessionId) {
		ModelAndView result;
		
		try{
			sessionService.delete(sessionId);
			result= new ModelAndView("redirect:listInactive.do");
		}catch(Throwable oops){
			result= new ModelAndView("redirect:listInactive.do");
			result.addObject("message", "publicSession.commit.error");
		}
		return result;
	}
	
	// ---------------------- Ancillary methods ----------------------
		
	private ModelAndView createEditModelAndView(PublicSession publicSession) {
		ModelAndView result;
		result = createEditModelAndView(publicSession, null);
		return result;
	}
	private ModelAndView createEditModelAndView(PublicSession publicSession, String message) {
		ModelAndView result;
		
		Collection<Organiser> organisers = publicSession.getContest().getContestOrganisers();
		Assert.notNull(organisers);
		
		result = new ModelAndView("publicSession/organiser/edit");
		result.addObject("publicSession", publicSession);
		result.addObject("organisers", organisers);
		result.addObject("message", message);
		return result;
	}
}