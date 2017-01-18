package controllers.administrator;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;
import security.UserAccountRepository;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorRegisterController extends AbstractController{
	
	//	Services --------------------------------------------------------------
	
	@Autowired 
	private AdministratorService administratorService ;
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	// Constructors -----------------------------------------------------------
	public AdministratorRegisterController(){
		super();
	}
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		AdministratorForm administratorForm;
		administratorForm=new AdministratorForm();
		
		result= createEditModelAndView(administratorForm);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid AdministratorForm administratorForm, BindingResult binding) {
		
		ModelAndView result;
		Administrator administrator;
		if(binding.hasErrors()){
			result=createEditModelAndView(administratorForm);
		}else{
			try{
				administrator=administratorService.reconstruct(administratorForm);
				administratorService.save(administrator);
				result=new ModelAndView("redirect:../");
			}catch (Throwable oops) {
				if(userAccountRepository.findByUsername(administratorForm.getUserName())!=null)
					result = createEditModelAndView(administratorForm,"administrator.duplicated");
				else if(!administratorForm.isAccepConditions())
					result = createEditModelAndView(administratorForm,"administrator.conditions");
				else
					result = createEditModelAndView(administratorForm,"administrator.commit.error");
				
			}
		}
		
		return result;
		
	}
	
	// Ancillary methods
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm){
		ModelAndView result;
		result=createEditModelAndView(administratorForm,null);
		return result;
	}
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm, String message){
		ModelAndView result;
		
		result=new ModelAndView("administrator/register");
		result.addObject("administratorForm",administratorForm);
		result.addObject("message",message);
		return result;
	}
}
