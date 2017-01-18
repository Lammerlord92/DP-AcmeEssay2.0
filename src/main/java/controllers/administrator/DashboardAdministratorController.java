package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Author;
import domain.Contest;
import domain.Organiser;
import domain.PublicSession;

import services.AuthorService;
import services.ContestService;
import services.EssayService;
import services.OrganiserService;
import services.PublicSessionService;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController {
	//Services-----------------------
	@Autowired
	private ContestService contestService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private EssayService essayService;
	@Autowired
	private PublicSessionService publicSessionService;
	@Autowired
	private OrganiserService organiserService;
	//Listing------------------------
	@RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView result;
		//A
		Collection<Contest> contestDesc;
		contestDesc=contestService.orderByNumberOfEssaysSubmittedDesc();
		Collection<Author> authorsMoreEssSubmit;
		authorsMoreEssSubmit=authorService.authorsMoreEssaysSubmitted();
		Collection<Author> authorsMoreEssPublished;
		authorsMoreEssPublished=authorService.authorsMoreEssaysPublished();
		Collection<Author> authorsLessEssPublished;
		authorsLessEssPublished=authorService.authorsLessEssaysPublished();
		Collection<Object[]> avgNumberEssaysSubmitted ;
		avgNumberEssaysSubmitted =essayService.avgNumberEssaysSubmittedByAuthorId();
		Collection<Object[]> avgContestOrganisedByOrganiser;
		avgContestOrganisedByOrganiser = contestService.avgContestOrganisedByOrganiser();
		Collection<Contest> contestsHeldLastMonth = contestService.contestsHeldLastMonth();

		result=new ModelAndView("administrator/dashboard");
		
		//A
		result.addObject("contestDesc", contestDesc);
		result.addObject("authorsMoreEssSubmit", authorsMoreEssSubmit);
		result.addObject("authorsMoreEssPublished", authorsMoreEssPublished);
		result.addObject("authorsLessEssPublished", authorsLessEssPublished);
		result.addObject("avgNumberEssaysSubmitted", avgNumberEssaysSubmitted);
		result.addObject("avgContestOrganisedByOrganiser", avgContestOrganisedByOrganiser);
		result.addObject("contestsHeldLastMonth", contestsHeldLastMonth);

		
		//2.0
		Collection<PublicSession> publicSessions=publicSessionService.findDescOrderCapacity();
		Collection<Object[]> chairmans=organiserService.findChairmans();
		Collection<Contest> contests=contestService.orderByNumberOfEssaysSubmittedDesc();
		
		result.addObject("contests",contests);
		result.addObject("publicSessions",publicSessions);
		result.addObject("chairmans",chairmans);
		
		return result;
	}
}
