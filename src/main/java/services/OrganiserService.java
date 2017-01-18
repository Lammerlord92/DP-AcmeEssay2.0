package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.OrganiserRepository;
import security.Authority;
import security.UserAccount;
import domain.Contest;
import domain.Organiser;
import domain.PublicSession;
import forms.OrganiserForm;

@Transactional
@Service
public class OrganiserService {
	// Managed repository -----------------------------------------	
	@Autowired
	private OrganiserRepository organiserRepository;
	// Supporting services ----------------------------------------
	// Constructor ------------------------------------------------
	// Simple CRUD methods ----------------------------------------
	public Organiser create(){
		Organiser result=new Organiser();
		
		UserAccount userAccount=new UserAccount();
		List<Authority> authorities=new ArrayList<Authority>();
		Authority a=new Authority();
		a.setAuthority(Authority.ORGANISER);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		
		Collection<Contest> contests=new ArrayList<Contest>();
		Collection<PublicSession> publicSessions=new ArrayList<PublicSession>();
		
		result.setUserAccount(userAccount);
		result.setContests(contests);
		result.setPublicSessions(publicSessions);
		
		return result;
	}
	public Organiser findOne(int organiserId){
		Organiser result;
		result=organiserRepository.findOne(organiserId);
		return result;
	}
	public Collection<Organiser> findAll(){
		Collection<Organiser> result;
		result=organiserRepository.findAll();
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public void save(Organiser organiser){
		Assert.notNull(organiser);
		Date date=new Date();
		if(organiser.getCreditCard().getExpirationYear()==(date.getYear()+1900)){
			Assert.isTrue(organiser.getCreditCard().getExpirationMonth()>(date.getMonth()+1));
		}
		else{
			Assert.isTrue(organiser.getCreditCard().getExpirationYear()>date.getYear()+1900);
		}
		organiserRepository.save(organiser);
	}
	public void delete(Organiser organiser){
		Assert.notNull(organiser);
		organiserRepository.delete(organiser);
	}
	
	// Other business methods -------------------------------------
	
	public Collection<Object[]> findChairmans(){
		Collection<Object[]> result;
		result=organiserRepository.findChairmans();
		return result;
	}
	
//	public Collection<Organiser> findByContest(Contest contest) {
//		Collection<Organiser> result = organiserRepository.findByContest(contest);
//		return result;
//	}
	
	public Organiser reconstruct(OrganiserForm organiserForm) {
		Assert.isTrue(organiserForm.getPassword().equals(organiserForm.getConfirmPassword()));
		Assert.isTrue(organiserForm.isAccepConditions());
		Organiser result;
		result=create();
		UserAccount userAccount=result.getUserAccount();
		userAccount.setUsername(organiserForm.getUserName());
		
		Md5PasswordEncoder encoder;		
		encoder= new Md5PasswordEncoder();
		String password=encoder.encodePassword(organiserForm.getPassword(), null);
		userAccount.setPassword(password);
		
		result.setUserAccount(userAccount);
		
		result.setName(organiserForm.getName());
		result.setSurname(organiserForm.getSurname());
		result.setEmailAddress(organiserForm.getEmailAddress());
		result.setContactPhone(organiserForm.getContactPhone());
		result.setUrl(organiserForm.getUrl());
		result.setBirthDate(organiserForm.getBirthDate());
		result.setNationality(organiserForm.getNationality());
		result.setCreditCard(organiserForm.getCreditCard());
		
		return result;
	}
	public Organiser findByUserAccount(UserAccount userAccount) {
		Organiser result;
		result=organiserRepository.findByUserAccount(userAccount);
		return result;
	}
}
