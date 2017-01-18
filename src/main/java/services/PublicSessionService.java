package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.PublicSessionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Essay;
import domain.Organiser;
import domain.PublicSession;

@Transactional
@Service
public class PublicSessionService {
	
	// Managed repository -----------------------------------------
	
	@Autowired
	private PublicSessionRepository sessionRepository;
	
	// Supporting services ----------------------------------------

	@Autowired
	private OrganiserService organiserService;
	
	// Constructor ------------------------------------------------
	
	// Simple CRUD methods ----------------------------------------
	
	public PublicSession create(){
		isOrganiser();
		PublicSession result=new PublicSession();
		Collection<Essay> essays=new ArrayList<Essay>();
		result.setEssays(essays);
		
		return result;
	}
	
	public PublicSession findOne(int publicSessionId){
		PublicSession result=sessionRepository.findOne(publicSessionId);
		return result;
	}
	
	public Collection<PublicSession> findAll(){
		Collection<PublicSession> result=sessionRepository.findAll();
		return filterByActive(result);
	}
	
	public Collection<PublicSession> findInactive(int organiserId){
//		Collection<PublicSession> result = sessionRepository.findByOrganiserId(organiserId);
		Collection<PublicSession> result = sessionRepository.findAll();
		result.removeAll(filterByActive(result));
		
		return filterByPrincipal(result);
	}
	
	public PublicSession save(PublicSession publicSession){
		isOrganiser();
		Assert.notNull(publicSession);
		
		PublicSession result = sessionRepository.save(publicSession);
		return result;
	}
	
	public void delete (int publicSessionId){
		PublicSession publicSessionDB = findOne(publicSessionId);
		Assert.notNull(publicSessionDB);
		Assert.isTrue(isPrincipal(publicSessionDB));
		
		if(publicSessionDB.getEssays().isEmpty())
			sessionRepository.delete(publicSessionDB);
		else
			Assert.isTrue(false, "No puedes borrar un publicSession si no esta inactivo");
	}

	// Other business methods -------------------------------------
	
	public Collection<PublicSession> findDescOrderCapacity(){
		isAdmin();
		Collection<PublicSession> result=sessionRepository.findDescOrderCapacity();
		return filterByActive(result);
	}
	public Collection<PublicSession> findByOrganiserId(int organiserId){
		isOrganiserOrAuthor();
		Collection<PublicSession> result = filterByActive(sessionRepository.findAll());
		return filterByPrincipal(result);
	}
	
	public Collection<PublicSession> findByContestId(int contestId){
		Collection<PublicSession> result=sessionRepository.findByContestId(contestId);
		return filterByActive(result);
	}
	
	public PublicSession findByEssayId(int essayId){
		PublicSession result=sessionRepository.findByEssayId(essayId);
		return result;
	}
	
	private void isOrganiser() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ORGANISER")) res=true;
		Assert.isTrue(res);
	}
	private void isAdmin() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ADMIN")) res=true;
		Assert.isTrue(res);
	}
	private void isOrganiserOrAuthor() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ORGANISER") || a.getAuthority().equals("AUTHOR")) res=true;
		Assert.isTrue(res);
	}
	
	private boolean isPrincipal(PublicSession publicSession) {
		Boolean result = false;
		isOrganiser();

		Organiser organiser = organiserService.findByUserAccount(LoginService.getPrincipal());
		PublicSession sessionDB = findOne(publicSession.getId());
		
		if(sessionDB.getContest().getContestOrganisers().contains(organiser))
			result = true;
		
		return result;
	}
	
	private Collection<PublicSession> filterByActive(Collection<PublicSession> publicSessions){
		Collection<PublicSession> result = new ArrayList<PublicSession>();
		
		for(PublicSession aux : publicSessions)
			if(aux.getEssays().isEmpty()==false)
				result.add(aux);
		return result;
	}
	
	private Collection<PublicSession> filterByPrincipal(Collection<PublicSession> publicSessions){
		Collection<PublicSession> result = new ArrayList<PublicSession>();
		
		for(PublicSession aux : publicSessions)
			if(isPrincipal(aux))
				result.add(aux);
		return result;
	}
}