package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Contest;
import domain.Organiser;
import domain.PublicSession;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PublicSessionServiceTest extends AbstractTest{
	// ------------------- Supporting services -------------------
	
	@Autowired
	private PublicSessionService publicSessionService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private OrganiserService organiserService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is not authenticated must be able to:
	//List the public sessions that the system stores.
	//Without any logged user, we will try to obtain all public sessions
	@Test
	public void testListAll(){
		Collection<PublicSession> all=publicSessionService.findAll();
		Assert.isTrue(all.size()==1);
	}
	
	//A user who is authenticated as Organiser must be able to:
	//List the public sessions that the system stores.
	//With an existing organiser we will try to obtain all public sessions
	@Test
	public void testListOrganiser(){
		authenticate("organiser1");
		Collection<PublicSession> all=publicSessionService.findAll();
		Assert.isTrue(all.size()==1);
		unauthenticate();
	}
	
	//A user who is authenticated as Administrator must be able to:
	//List the public sessions that the system stores.
	//With an existing administrator we will try to obtain all public sessions
	@Test
	public void testListAdministrator(){
		authenticate("admin1");
		Collection<PublicSession> all=publicSessionService.findAll();
		Assert.isTrue(all.size()==1);
		unauthenticate();
	}

//	A user who is authenticated as an Organiser must able to:
//	Create new public sessions. StartMoment must be placed before the 
//	endMoment. And StartMoment must be later that the contest holdingDate
//	With an existing Organiser we will try to create a public session.
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateAndSave(){
		authenticate("organiser2");
		UserAccount currentUser=LoginService.getPrincipal();
		
		PublicSession session = publicSessionService.create();
		
		Contest contest = contestService.findOne(14);
		Organiser organiser=organiserService.findByUserAccount(currentUser);
		
		session.setContest(contest);
		session.setOrganiser(organiser);
		
		Date startMoment = new Date();
		//23/11/2015 11:30
		startMoment.setDate(23);
		startMoment.setMonth(10);
		//1900
		startMoment.setYear(115);
		startMoment.setHours(11);
		startMoment.setMinutes(30);
		
		Date endMoment = new Date();
		//23/12/2015 11:30
		endMoment.setDate(23);
		endMoment.setMonth(11);
		//1900
		endMoment.setYear(115);
		endMoment.setHours(11);
		endMoment.setMinutes(30);
		
		session.setStartMoment(startMoment);
		session.setEndMoment(endMoment);
		session.setCapacity(4);
		
		publicSessionService.save(session);
		unauthenticate();
	}

	//A user who is authenticated as an Administrator must be able to:
	//Display a dashboard with the following information:
	//The list of public sessions in descending order of capacity.
	//With an existing administrator we will check that the result is the expected.	
	@Test
	public void testFindDescOrderCapacity(){
		authenticate("admin1");
		
		List<PublicSession>orderedList= new ArrayList<PublicSession>();
		Collection<PublicSession> all= publicSessionService.findDescOrderCapacity();
		for(PublicSession c:all){
			orderedList.add(c);
		}
		
		Assert.isTrue(orderedList.get(0).getId()==18);
		unauthenticate();
	}
	

	@Test
	public void testEditAndSave(){
		authenticate("organiser1");
		
		PublicSession publicSession = publicSessionService.findOne(18);
		publicSession.setCapacity(30);
		
		publicSessionService.save(publicSession);
		
		System.out.println(publicSessionService.findOne(18).getCapacity());
		
		unauthenticate();
	}
	
	
	
	//A user who is authenticated as an Organiser must be able to:
	//List the public sessions that he or she has created.
	//With an existing organiser we will try to find all his public sessions.
	@Test
	public void testListMyPublicSessionOrganiser(){
		authenticate("organiser2");
		
		Collection<PublicSession> all = publicSessionService.findByOrganiserId(12);
		Assert.isTrue(all.size()==1);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Administrator must able to:
	//Display the list of public sessions by their respective contest
	//in their dashboard.
	//This use case works in the corresponding view, not as a simple service method. So
	//we can't test it in our JUnit	

	//A user who is authenticated as an Author must able to:
	//Get the public session from an essayId given
	@Test 
	public void testGetByEssayId(){
		authenticate("author2");
		Boolean res = false;
		PublicSession ps = publicSessionService.findByEssayId(20);
		if(ps!=null){
			res=true;
		}
		Assert.isTrue(res);
		unauthenticate();
	}
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
		
	//A user who is not authenticated must be able to:
	//List the public sessions that the system stores.
	//Without any logged user, we will expect a wrong result and catch the exception.	
	@Test(expected=IllegalArgumentException.class)
	public void testListAllException(){
		Collection<PublicSession> all = publicSessionService.findAll();
		Assert.isTrue(all.size()==0);
	}
	
	//A user who is authenticated  as Organiser must be able to:
	//List the public sessions that the system stores.
	//Without any logged user, we will expect a wrong result and catch the exception.	
	@Test(expected=IllegalArgumentException.class)
	public void testListOrganiserException(){
		authenticate("organiser1");
		Collection<PublicSession> all = publicSessionService.findAll();
		Assert.isTrue(all.size()==0);
		unauthenticate();
	}
	
	//A user who is authenticated  as Administrator must be able to:
	//List the public sessions that the system stores.
	//Without any logged user, we will expect a wrong result and catch the exception.	
	@Test(expected=IllegalArgumentException.class)
	public void testListAdministratorException(){
		authenticate("admin1");
		Collection<PublicSession> all = publicSessionService.findAll();
		Assert.isTrue(all.size()==0);
		unauthenticate();
	}
	
	//A user who is authenticated as an Administrator must be able to:
	//Display a dashboard with the following information:
	//The list of public sessions in descending order of capacity.
	//With an existing author we will catch the exception.	
	@Test(expected=IllegalArgumentException.class)
	public void testFindDescOrderCapacityException(){
		authenticate("author1");
		
		List<PublicSession>orderedList= new ArrayList<PublicSession>();
		Collection<PublicSession> all= publicSessionService.findDescOrderCapacity();
		for(PublicSession c:all){
			orderedList.add(c);
		}
		System.out.println(orderedList.get(0).getId());
		Assert.isTrue(orderedList.get(0).getId()==18);
		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//List the public sessions that he or she has created.
	//With an existing Author we will catch the exception.	
	@Test(expected=IllegalArgumentException.class)
	public void testListMyPublicSessionOrganiserException(){
		authenticate("author2");
		
		Collection<PublicSession> all = publicSessionService.findByOrganiserId(12);
		Assert.isTrue(all.size()==1);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Administrator must able to:
	//Display the list of public sessions by their respective contest
	//in their dashboard.
	//This use case works in the corresponding view, not as a simple service method. So
	//we can't test it in our JUnit

	
	//A user who is authenticated as an Author must able to:
	//Get the public session from an essayId given
	//With an existing Administrator we will catch the exception.
	@Test(expected=IllegalArgumentException.class) 
	public void testGetByEssayIdException(){
		authenticate("admin1");
		Boolean res = false;
		PublicSession ps = publicSessionService.findByEssayId(19);
		if(ps!=null){
			res=true;
		}
		Assert.isTrue(res);
		unauthenticate();
	}
}
