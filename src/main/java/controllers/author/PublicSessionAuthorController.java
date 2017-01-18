package controllers.author;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.PublicSession;

import security.LoginService;
import security.UserAccount;
import services.AuthorService;
import services.PublicSessionService;

@Controller
@RequestMapping("publicSession/author")
public class PublicSessionAuthorController {
	//Services --------------------------------------------------
	@Autowired
	private PublicSessionService sessionService;
	@Autowired
	private AuthorService authorService;
	
	//Listing----------------------------------------------------
	@RequestMapping(value= "/listByEssay", method = RequestMethod.GET)
	public ModelAndView listByEssay(@RequestParam int essayId){
		ModelAndView result;
		UserAccount currentUser=LoginService.getPrincipal();
	    PublicSession session=sessionService.findByEssayId(essayId);
	    
	    String requestURI="publicSession/author/listByEssay.do";
	    Collection<PublicSession> sessions=new ArrayList<PublicSession>();
	    if(session!=null) sessions.add(session);
	    		
	    result = new ModelAndView("publicSession/author/listByEssay");
	    result.addObject("sessions",sessions);
	    result.addObject("requestURI",requestURI);
		return result;
	}
}
