package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ContestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Contest;
import domain.Essay;
import domain.Organiser;
import domain.PublicSession;
import forms.ContestAddOrgganiserForm;

@Transactional
@Service
public class ContestService {
	// Managed repository -----------------------------------------	
	@Autowired
	private ContestRepository contestRepository;
	// Supporting services ----------------------------------------
	@Autowired
	private OrganiserService organiserService;
	// Constructor ------------------------------------------------
	// Simple CRUD methods ----------------------------------------
	public Contest create(){
		isOrganiser();
		Contest result=new Contest();
		UserAccount userAccount=LoginService.getPrincipal();
		Organiser organiser=organiserService.findByUserAccount(userAccount);
		Collection<Organiser> organisers=new ArrayList<Organiser>();
		organisers.add(organiser);
		Collection<Essay> essays=new ArrayList<Essay>();
		Collection<PublicSession> publicSessions=new ArrayList<PublicSession>();
		
		result.setEssays(essays);
		result.setPublicSessions(publicSessions);
		result.setContestOrganisers(organisers);
		return result;
	}
	public Contest findOne(int contestId){
		Contest result;
		result=contestRepository.findOne(contestId);
		return result;
	}
	public Collection<Contest> findAll(){
		Collection<Contest> result;
		result=contestRepository.findAll();
		return result;
	}
	@SuppressWarnings("deprecation")
	public void save(Contest contest){
		isOrganiser(contest);
		Assert.notNull(contest);
		
		Contest previous=contestRepository.findOne(contest.getId());
		
		Assert.isTrue(contest.getDeadline().before(contest.getHoldingDate()));
		
		if(contest.getDeadline().getYear()!=previous.getDeadline().getYear() || 
				contest.getDeadline().getMonth()!=previous.getDeadline().getMonth() ||
				contest.getDeadline().getDate()!=previous.getDeadline().getDate())
			Assert.isTrue(contest.getDeadline().after(previous.getDeadline()));
		if(contest.getHoldingDate().getYear()!=previous.getHoldingDate().getYear() ||
				contest.getHoldingDate().getMonth()!=previous.getHoldingDate().getMonth() ||
				contest.getHoldingDate().getDate()!=previous.getHoldingDate().getDate())
			Assert.isTrue(contest.getHoldingDate().after(previous.getHoldingDate()));
		
		contestRepository.save(contest);
	}
	public void saveCreate(Contest contest) {
		isOrganiser();
		Assert.notNull(contest);
		Assert.isTrue(contest.getDeadline().before(contest.getHoldingDate()));
		Assert.isTrue(contest.getDeadline().after(new Date()));
		contestRepository.save(contest);
	}
	
	public void delete(Contest contest){
		isOrganiser(contest);
		Assert.notNull(contest);
		Assert.isTrue(contest.getEssays().size()==0);
		contestRepository.delete(contest);
	}
	// Other business methods -------------------------------------
	public Collection<Contest> findByOrganiserId(int organiserId){
		Collection<Contest> result;
		result=contestRepository.findByOrganiser(organiserId);
		return result;
	}
	public Collection<Contest> findByOrganiser() {
		isOrganiser();
		UserAccount user=LoginService.getPrincipal();
		Organiser currentActor=organiserService.findByUserAccount(user);
		Collection<Contest> all=contestRepository.findAll();
		List<Contest> result=new ArrayList<Contest>();
		for(Contest caux:all){
			Collection<Organiser> orgs=caux.getContestOrganisers();
			Boolean isValid=false;
			for(Organiser orgaux:orgs){
				if(orgaux.getId()==currentActor.getId()) isValid=true;
			}
			if(isValid) result.add(caux);
		}
		return result;
	}
	public Collection<Contest> orderByNumberOfEssaysSubmittedDesc(){
		isAdmin();
		Collection<Contest> result;
		result = contestRepository.orderByNumberOfEssaysSubmittedDesc();
		return result;
	}
	
	public Collection<Object[]> avgContestOrganisedByOrganiser(){
		isAdmin();
		Collection<Object[]> result;
		result = contestRepository.avgContestOrganisedByOrganiser();
		return result;
	}
	
	public void addOrganiser(ContestAddOrgganiserForm contestAddOrgganiserForm){
		isOrganiser();
		Organiser organiser=contestAddOrgganiserForm.getOrganiser();
		Contest contest=contestRepository.findOne(contestAddOrgganiserForm.getContestId());
		contest.getContestOrganisers().add(organiser);
		contestRepository.save(contest);
	}
	
	public Collection<Contest> contestsHeldLastMonth(){
		isAdmin();
		Collection<Contest> result = findAll();
		Collection<Contest> res = new ArrayList<Contest>();
		
		Date today = new Date();
		Integer mes = today.getMonth()-1;
		
		for(Contest c:result){
			Date deadLine = c.getDeadline();
			if(deadLine.compareTo(today)<0){
				if((deadLine.getYear()==today.getYear()) && (deadLine.getMonth()==mes)){
					res.add(c);
					System.out.println(res);
				}
			}
		}
		return res;
	}
	
	private void isOrganiser() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ORGANISER")) res=true;
		Assert.isTrue(res);
	}
	private void isOrganiser(Contest contest) {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ORGANISER")){
				for(Organiser aux:contest.getContestOrganisers()){
					if(aux.getUserAccount().getId()==account.getId()) res=true;
				}
			}
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

}
