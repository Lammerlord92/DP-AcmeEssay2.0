package services;

import java.util.Collection;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import domain.Author;
import domain.Contest;
import domain.Essay;
import domain.PublicSession;
import repositories.EssayRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class EssayService {
	// Managed repository -----------------------------------------	
	
	@Autowired
	private EssayRepository essayRepository;
	
	// Supporting services ----------------------------------------
	
	@Autowired
	private AuthorService authorService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private PublicSessionService sessionService;
	
	// Constructor ------------------------------------------------
	
	// Simple CRUD methods ----------------------------------------
	
	public Essay create(int contestId){
		Essay result=new Essay();
		Contest contest=contestService.findOne(contestId);
		UserAccount userAccount=LoginService.getPrincipal();
		Author author=authorService.findByUserAccount(userAccount);
		
		result.setAuthor(author);
		result.setPublished(false);
		result.setContest(contest);
		result.setSubmissionDate(new Date());
		return result;
	}
	public Essay findOne(int essayId){
		Essay result;
		result=essayRepository.findOne(essayId);
		return result;
	}
	public Collection<Essay> findAll(){
		Collection<Essay> result;
		result=essayRepository.findAll();
		return result;
	}
	public void save(Essay essay){
		Assert.notNull(essay);
		long milisec=new Date().getTime();
		Date submisionDate= new Date(milisec-1);
		essay.setSubmissionDate(submisionDate);
		checkPrincipal(essay);
		essayRepository.save(essay);
	}

	public void delete(Essay essay){
		Assert.notNull(essay);
		essayRepository.delete(essay);
	}
	
	// Other business methods -------------------------------------
	
	public Collection<Essay> findByAuthorId(int authorId){
		Collection<Essay> result;
		result=essayRepository.findByAuthor(authorId);
		return result;
	}
	public Collection<Essay> findByAuthor() {
		UserAccount userAccount=LoginService.getPrincipal();
		Author author=authorService.findByUserAccount(userAccount);
		Collection<Essay> result=essayRepository.findByAuthor(author.getId());
		return result;
	}
	
	public Collection<Object[]> avgNumberEssaysSubmittedByAuthorId(){
		isAdmin();
		Collection<Object[]> result;
		result=essayRepository.avgNumberEssaysSubmittedByAuthorId();
		
		return result;
	}
	
	private void checkPrincipal(Essay essay) {
		Contest contest=essay.getContest();	
		UserAccount userAccount=LoginService.getPrincipal();
		Author author=authorService.findByUserAccount(userAccount);
		boolean cond1=contest.getDeadline().after(essay.getSubmissionDate());
		System.out.println(cond1);
		System.out.println(contest.getDeadline());
		System.out.println(essay.getSubmissionDate());
		Assert.isTrue(cond1);
		Assert.isTrue(author.getId()==essay.getAuthor().getId());
	}
	
	public boolean isExpired(Essay essay) {
		Contest contest=essay.getContest();
		boolean result=false;
		if(contest.getDeadline().before(new Date())) result=true;
		return result;
	}
	
	public void publish(int essayId) {
		isOrganiser();
		Essay essay = findOne(essayId);
		Assert.notNull(essay);
		
		Date holdingDate = essay.getContest().getHoldingDate();
		Assert.isTrue(holdingDate.before(new Date()));
		System.out.println(holdingDate.before(new Date()));
		essay.setPublished(true);
		
		essayRepository.save(essay);
		System.out.println(essay.getPublished());
	}
	
	public Collection<Essay> submittedEssaysByContest(int contestId){
		Collection<Essay> result;
		UserAccount userAccount=LoginService.getPrincipal();
		Author author=authorService.findByUserAccount(userAccount);
		int authorId = author.getId();
		System.out.println(authorId);
		result = essayRepository.submittedEssaysByContest(contestId, authorId);
		return result;
	}
	
	public Collection<Essay> submittedEssaysByContestOrganiser(int contestId){
		isOrganiser();
		Collection<Essay> result;
		result = essayRepository.submittedEssaysByContestOrganiser(contestId);
		return result;
	}
	
	public Collection<Essay> publishedEssaysByContest(int contestId){
		Collection<Essay> result;
		result = essayRepository.publishedEssaysByContest(contestId);
		return result;
	}
	
	private void isAdmin() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ADMIN")) res=true;
		Assert.isTrue(res);
	}
	
	private void isOrganiser() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("ORGANISER")) res=true;
		Assert.isTrue(res);
	}
	
	public void addToSession(int essayId, int sessionId) {
		Essay essay = essayRepository.findOne(essayId);
		PublicSession session = sessionService.findOne(sessionId);
		
		
		
		Collection<Essay> essays = session.getEssays();
		essays.add(essay);
		session.setEssays(essays);
		
		essay.setPublicSession(session);
		
		essayRepository.save(essay);
		sessionService.save(session);
	}
	
	public void deleteFromSession(int essayId, int sessionId) {
		Essay essay = essayRepository.findOne(essayId);
		PublicSession session = sessionService.findOne(sessionId);
		
		
		
		Collection<Essay> essays = session.getEssays();
		essays.remove(essay);
		session.setEssays(essays);
		
		essay.setPublicSession(null);
		
		essayRepository.save(essay);
		sessionService.save(session);
	}
}
