package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AuthorService;
import services.ContestService;
import services.OrganiserService;
import controllers.AbstractController;
import domain.Author;
import domain.Contest;
import domain.Organiser;

@Controller
@RequestMapping("/customer/administrator")
public class CustomerAdministratorController extends AbstractController {
	
	// ---------------------- Managed services------------------------
	
	@Autowired 
	private AuthorService authorService;
	@Autowired 
	private OrganiserService organiserService;
	@Autowired
	private ContestService contestService;
	
	// ------------------------- Constructor -------------------------
	
	// --------------------------- Profile ---------------------------
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam int customerId) {
		ModelAndView result;
		String requestURI;
		Author author = authorService.findOne(customerId);
		Organiser organiser = organiserService.findOne(customerId);
		
		if(organiser!=null){
			requestURI="organiser/profile.do";
			Collection<Contest> contests=contestService.findByOrganiserId(customerId);
			
			result = new ModelAndView("organiser/profile");
			result.addObject("organiser",organiser);
			result.addObject("contests", contests);
		}else{
			Assert.notNull(author);
			requestURI="author/profile.do";
			
			result = new ModelAndView("author/profile");
			result.addObject("author",author);
		}
		
		result.addObject("requestURI",requestURI);
		return result;
	}
}