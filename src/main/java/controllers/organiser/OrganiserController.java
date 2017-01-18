package controllers.organiser;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.OrganiserService;

import controllers.AbstractController;
import domain.Contest;
import domain.Organiser;
@Controller
@RequestMapping("/organiser")
public class OrganiserController extends AbstractController{
	//	Services --------------------------------------------------------------
	@Autowired 
	private OrganiserService organiserService;
	@Autowired
	private ContestService contestService;
	
	//	View Profile ---------------------------------------------------------------
	@RequestMapping(value="/administrator/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam int organiserId) {
		ModelAndView result;
		Organiser organiser=organiserService.findOne(organiserId);
		Collection<Contest> contests=contestService.findByOrganiserId(organiserId);
		String requestURI="organiser/profile.do";
		
		result = new ModelAndView("organiser/profile");
		result.addObject("organiser",organiser);
		result.addObject("contests", contests);
		result.addObject("requestURI",requestURI);
		return result;
		
	}
}
