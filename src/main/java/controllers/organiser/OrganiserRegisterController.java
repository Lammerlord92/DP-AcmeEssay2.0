package controllers.organiser;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccountRepository;
import services.OrganiserService;
import controllers.AbstractController;
import domain.Organiser;
import forms.OrganiserForm;

@Controller
@RequestMapping("/organiser")
public class OrganiserRegisterController extends AbstractController{
	//	Services --------------------------------------------------------------
	@Autowired 
	private OrganiserService organiserService;
	@Autowired
	private UserAccountRepository userAccountRepository;
	//	Create ----------------------------------------------------------------
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		OrganiserForm organiserForm;
		organiserForm=new OrganiserForm();
		
		result= createEditModelAndView(organiserForm);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid OrganiserForm organiserForm, BindingResult binding) {
		ModelAndView result;
		Organiser organiser;
		if(binding.hasErrors()){
			System.out.println("dasfsfa");
			result=createEditModelAndView(organiserForm);
		}else{
			try{
				organiser=organiserService.reconstruct(organiserForm);
				organiserService.save(organiser);
				result=new ModelAndView("redirect:../security/login.do");
			}catch (Throwable oops) {
				if(userAccountRepository.findByUsername(organiserForm.getUserName())!=null)
					result = createEditModelAndView(organiserForm,"administrator.duplicated");
				else if(!organiserForm.isAccepConditions()){
					result = createEditModelAndView(organiserForm,"administrator.conditions");
				}else if(oops.getCause()==null){
					result = createEditModelAndView(organiserForm,"administrator.duplicated");
				}else{
					result= createEditModelAndView(organiserForm,"organiser.commit.error");
				}
			}
		}
		
		return result;
		
	}
	
	// Ancillary methods
	protected ModelAndView createEditModelAndView(OrganiserForm organiserForm){
		ModelAndView result;
		result=createEditModelAndView(organiserForm,null);
		return result;
	}
	protected ModelAndView createEditModelAndView(OrganiserForm organiserForm, String message){
		ModelAndView result;
		
		result=new ModelAndView("organiser/register");
		result.addObject("organiserForm",organiserForm);
		result.addObject("message",message);
		return result;
	}
}
