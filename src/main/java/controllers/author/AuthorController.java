package controllers.author;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccountRepository;
import services.AuthorService;
import controllers.AbstractController;
import domain.Author;
import forms.AuthorForm;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController{
	
	// ----------------------- Managed service -----------------------
	
	@Autowired 
	private AuthorService authorService;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	// ------------------------- Constructor -------------------------
	
	// --------------------------- Profile ---------------------------
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam int authorId) {
		ModelAndView result;
		
		Author author=authorService.findOne(authorId);
		Assert.notNull(author);
		String requestURI="author/profile.do";
		
		result = new ModelAndView("author/profile");
		result.addObject("author",author);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
	// --------------------------- Register --------------------------
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		AuthorForm authorForm = new AuthorForm();
		result= createEditModelAndView(authorForm);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid AuthorForm authorForm, BindingResult binding) {
		
		ModelAndView result;
		Author author;
		if(binding.hasErrors()){
			System.out.println("dasfsfa");
			result=createEditModelAndView(authorForm);
		}else{
			try{
				author=authorService.reconstruct(authorForm);
				authorService.save(author);
				result=new ModelAndView("redirect:../security/login.do");
			}catch (Throwable oops) {
				if(userAccountRepository.findByUsername(authorForm.getUserName())!=null)
					result = createEditModelAndView(authorForm,"administrator.duplicated");
				else if(!authorForm.isAccepConditions()){
					result = createEditModelAndView(authorForm,"administrator.conditions");
				}else{
					result= createEditModelAndView(authorForm,"author.commit.error");
				}
			}
		}
		
		return result;
	}
	
	// ---------------------- Ancillary methods ----------------------
	
	protected ModelAndView createEditModelAndView(AuthorForm authorForm){
		ModelAndView result;
		result=createEditModelAndView(authorForm,null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(AuthorForm authorForm, String message){
		ModelAndView result;
		
		result=new ModelAndView("author/register");
		result.addObject("authorForm",authorForm);
		result.addObject("message",message);
		return result;
	}
}