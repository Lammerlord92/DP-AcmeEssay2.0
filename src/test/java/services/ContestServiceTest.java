package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Contest;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@SuppressWarnings("unused")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ContestServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private ContestService contestService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is not authenticated must be able to:
	//List the contests that the system stores.
	//Without any logged user, we will try to obtain all contests.
	@Test
	public void testListAll(){
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==3);
	}
	
	//A user who is authenticated as Author must be able to:
	//List the contests that the system stores.
	//With an existing author, we will try to obtain all contests.
	@Test
	public void testListAuthor(){
		authenticate("author1");
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==3);
		unauthenticate();
	}
	
	//A user who is authenticated as Organiser must be able to:
	//List the contests that the system stores.
	//With an existing organiser we will try to obtain all contests.
	@Test
	public void testListOrganiser(){
		authenticate("organiser1");
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==3);
		unauthenticate();
	}
		
	//A user who is authenticated as an Organiser must be able to:
	//Create contests. Deadlines and holding dates may be delayed when editing
	//a contest, but they can only be set ahead the original dates.
	//With an existing Organiser we will try to create a contest.
	@Test
	public void testCreateAndSave(){
		authenticate("organiser1");
		
		Contest contest = contestService.create();
		
		contest.setName("testing");
		contest.setDescription("testing");
		contest.setHoldingDate(new Date(System.currentTimeMillis()+90000));
		contest.setDeadline(new Date());
		contest.setResult("testing");

		contestService.saveCreate(contest);
		
		unauthenticate();
	}
	//A user who is authenticated as an Organiser must be able to:
	//Edit contests. Deadlines and holding dates may be delayed when editing
	//a contest, but they can only be set ahead the original dates.
	//With an existing Organiser we will try to edit a contest.
	@Test
	public void testEditAndSave(){
		authenticate("organiser1");
		
		Contest contest = contestService.findOne(11);
		Date newDeadLine=DateUtils.addDays(contest.getDeadline(), 17);
		contest.setDeadline(newDeadLine);
		Date newHoldingDate=DateUtils.addDays(contest.getDeadline(), 17);
		contest.setHoldingDate(newHoldingDate);
		
		contestService.save(contest);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//Delete one of the contests he or she organises, as long as no author has already
	//submitted any essays to it.
	//With an existing Author we will try to delete an existing contest.
	@Test
	public void testDelete(){
		authenticate("organiser1");
		
		Contest contest = contestService.findOne(11);
		contestService.delete(contest);

		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//Register the results of contests. The results are written as a piece of text in 
	//plain natural language. The results can be registered as soon as the holding date 
	//has expired.
	//With an existing organiser we will try to add a new result
	@Test
	public void testAddResult(){
		authenticate("organiser2");
		
		Contest contest = contestService.findOne(13);
		contest.setResult("result");
		contestService.save(contest);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//List the contests that he or she is created.
	//With an existing organiser we will try to find all his contests.
	@Test
	public void testListMyContests(){
		authenticate("organiser2");
		
		Collection<Contest> all=contestService.findByOrganiser();
		Assert.isTrue(all.size()==2);
		
		unauthenticate();
	}
	
	//A user who is authenticated as Administrator must be able to:
	//List the contests that the system stores.
	//With an existing administrator we will try to obtain all contests.
	@Test
	public void testListAdministrator(){
		authenticate("admin1");
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==3);
		unauthenticate();
	}
	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of contests in descending order of number submitted essays.
	//With an existing administrator we will check that the result is the expected.
	@Test
	public void testContestDescOrderSubmittedEssays(){
		authenticate("admin1");
		
		List<Contest> orderedList=new ArrayList<Contest>();
		Collection<Contest> all=contestService.orderByNumberOfEssaysSubmittedDesc();
		for(Contest c:all){
			orderedList.add(c);
		}
		Assert.isTrue(orderedList.get(0).getId()==14);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The average number of contests organised by an organiser.
	//With an existing administrator we will check that the result is the expected.
	@Test
	public void testAvgNumberOfContest(){
		authenticate("admin1");
		
		Collection<Object[]> all=contestService.avgContestOrganisedByOrganiser();
		List<Double> afterList=new ArrayList<Double>();
		for(Object[] obj:all){
			Double aux=(Double) obj[1];
			afterList.add(aux);
		}
		Assert.isTrue(afterList.get(1)== 0.66666667);
		unauthenticate();
	}
	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of contests that were held during the last month.
	//With an existing administrator we will check that the result is the expected.
	@Test
	public void testContestLastMonth(){
		authenticate("admin1");
		
		Collection<Contest>all=contestService.contestsHeldLastMonth();
		Assert.isTrue(all.size()==0);
		
		unauthenticate();
	}
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is not authenticated must be able to:
	//List the contests that the system stores.
	//Without any logged user, we will expect a wrong result and catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testListAllException(){
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==2);
	}
	
	//A user who is authenticated as Author must be able to:
	//List the contests that the system stores.
	//With an existing author, we will expect a wrong result and catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testListAuthorException(){
		authenticate("author1");
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==2);
		unauthenticate();
	}
	
	//A user who is authenticated as Organiser must be able to:
	//List the contests that the system stores.
	//With an existing organiser we will expect a wrong result and catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testListOrganiserException(){
		authenticate("organiser1");
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==2);
		unauthenticate();
	}
		
	//A user who is authenticated as an Organiser must be able to:
	//Create contests. Deadlines and holding dates may be delayed when editing
	//a contest, but they can only be set ahead the original dates.
	//With an existing Author we will try to create a contest and 
	//catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		authenticate("author1");
		
		Contest contest = contestService.create();
		
		contest.setName("testing");
		contest.setDescription("testing");
		contest.setHoldingDate(new Date(System.currentTimeMillis()+90000));
		contest.setDeadline(new Date());
		contest.setResult("testing");

		contestService.saveCreate(contest);
		
		unauthenticate();
	}
	//A user who is authenticated as an Organiser must be able to:
	//Edit contests. Deadlines and holding dates may be delayed when editing
	//a contest, but they can only be set ahead the original dates.
	//With an existing author we will try to edit a contest and 
	//catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testEditAndSaveException(){
		authenticate("author1");
		
		Contest contest = contestService.findOne(13);
		Date newDeadLine=DateUtils.addDays(contest.getDeadline(), 17);
		contest.setDeadline(new Date());

		contestService.save(contest);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//Delete one of the contests he or she organises, as long as no author has already
	//submitted any essays to it.
	//With an existing Author we will try to delete an existing contest and 
	//catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteException(){
		authenticate("author1");
		
		Contest contest = contestService.findOne(17);
		contestService.delete(contest);

		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//Register the results of contests. The results are written as a piece of text in 
	//plain natural language. The results can be registered as soon as the holding date 
	//has expired.
	//With an existing author we will try to add a new result and catch the exception
	@Test(expected=IllegalArgumentException.class)
	public void testAddResultException(){
		authenticate("author1");
		
		Contest contest = contestService.findOne(11);
		contest.setResult("result");
		contestService.save(contest);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Organiser must be able to:
	//List the contests that he or she is created.
	//With an existing author we will try to find all his contests and catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testListMyContestsException(){
		authenticate("author1");
		
		Collection<Contest> all=contestService.findByOrganiser();
		Assert.isTrue(all.size()==3);
		
		unauthenticate();
	}
	
	//A user who is authenticated as Administrator must be able to:
	//List the contests that the system stores.
	//With an existing administrator we will expect a wrong result and catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testListAdministratorException(){
		authenticate("admin1");
		Collection<Contest> all=contestService.findAll();
		Assert.isTrue(all.size()==2);
		unauthenticate();
	}
	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of contests in descending order of number submitted essays.
	//With an existing organiser we will check that the result is the expected and catch the 
	//exception.
	@Test(expected=IllegalArgumentException.class)
	public void testContestDescOrderSubmittedEssaysException(){
		authenticate("organiser1");
		
		List<Contest> orderedList=new ArrayList<Contest>();
		Collection<Contest> all=contestService.orderByNumberOfEssaysSubmittedDesc();
		for(Contest c:all){
			orderedList.add(c);
		}
		Assert.isTrue(orderedList.get(0).getId()==16);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The average number of contests organised by an organiser.
	//With an existing organiser we will check that the result is the expected and catch the
	//exception.
	@Test(expected=IllegalArgumentException.class)
	public void testAvgNumberOfContestException(){
		authenticate("organiser");
		
		Collection<Object[]> all=contestService.avgContestOrganisedByOrganiser();
		List<Long> afterList=new ArrayList<Long>();
		for(Object[] obj:all){
			Long aux=(Long) obj[1];
			afterList.add(aux);
		}
		Assert.isTrue(afterList.get(1)==1);
		unauthenticate();
	}
	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of contests that were held during the last month.
	//With an existing organiser we will check that the result is the expected and catch the
	//exception.
	@Test(expected=IllegalArgumentException.class)
	public void testContestLastMonthException(){
		authenticate("organiser1");
		
		Collection<Contest>all=contestService.contestsHeldLastMonth();
		Assert.isTrue(all.size()==1);
		
		unauthenticate();
	}
}