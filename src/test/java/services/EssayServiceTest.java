package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;


import domain.Essay;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EssayServiceTest extends AbstractTest{
	@Autowired
	private EssayService essayService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	//	A user who is authenticated as an author must be able to
	//	Submit an essay, which he or she must have written, to a contest.
	@Test
	public void testSubmit(){
		authenticate("author1");
		Essay essay=essayService.create(11);
		essay.setContents("asnjashaosidhaoid");
		essay.setAbstractEss("agkshgagdsiuagdlakd");
		essay.setTitle("gwuguiqgiwgiagwdiqgad");
		essayService.save(essay);
		authenticate(null);
	}	
	// A user who is authenticated as an author must be able to:
	// Edit an essay as long as the deadline of the corresponding contest has not expired.
	@Test
	public void testEdit(){
		authenticate("author1");
		Essay essay=essayService.findOne(19);
		essay.setContents("asnjashaosidhaoid");
		essay.setAbstractEss("agkshgagdsiuagdlakd");
		essay.setTitle("gwuguiqgiwgiagwdiqgad");
		essayService.save(essay);
		authenticate(null);
	}	
	// A user who is authenticated as an author must be able to:
	// List the essays that he or she's submitted to a given contest.
	@Test
	public void testFindByContest(){
		authenticate("author1");
		Collection<Essay> essays=essayService.submittedEssaysByContest(14);
		Assert.isTrue(essays.size()==1);
		authenticate(null);
	}
	// All users (registered and unregistered)
	// See the list of essays that have been published in a contest and see their details.
	@Test
	public void testFindPublishedContestAuthor(){
		authenticate("organiser1");
		Collection<Essay> essays=essayService.publishedEssaysByContest(14);
		Assert.isTrue(essays.size()==1);
		authenticate(null);
	}
	// All users (registered and unregistered)
	// See the list of essays that have been published in a contest and see their details.
	@Test
	public void testFindPublishedContestAdmin(){
		authenticate("admin1");
		Collection<Essay> essays=essayService.publishedEssaysByContest(14);
		
		Assert.isTrue(essays.size()==1);
		authenticate(null);
	}
	//A user who is authenticated as an organiser must be able to:
	//Decide on whether an essay must be published or not. 
	//The decision can be made only after the hold date of 
	//the corresponding contest has expired.
	@Test
	public void testPublishEssay(){
		authenticate("organiser2");
		essayService.publish(21);
		authenticate(null);
	}
	//	A user who is authenticated as an administrator must be able to:
	//		o Display a dashboard with the following information:
	//			The average number of essays submitted by an author.
	@Test
	public void testAvgNumberEssay(){
		authenticate("admin1");
		Collection<Object[]> all=essayService.avgNumberEssaysSubmittedByAuthorId();
		List<Double> afterList=new ArrayList<Double>();
		for(Object[] obj:all){
			Double aux=(Double) obj[1];
			afterList.add(aux);
		}
		Assert.isTrue(afterList.get(1)== 0.25);
		authenticate(null);
	}
	//----------------------------------------------------
	// Negative TEST CASES
	//----------------------------------------------------
	//	A user who is authenticated as an author must be able to
	//	Submit an essay, which he or she must have written, to a contest.
	//	Tryng whith an admin
	@Test(expected=NullPointerException.class)
	public void testSubmitAsAdmin(){
		authenticate("admin1");
		Essay essay1=essayService.create(17);
		essay1.setContents("asnjashaosidhaoid");
		essay1.setAbstractEss("agkshgagdsiuagdlakd");
		essay1.setTitle("gwuguiqgiwgiagwdiqgad");
		essayService.save(essay1);
		authenticate(null);
	}
	// A user who is authenticated as an author must be able to:
	// Edit an essay as long as the deadline of the corresponding contest has not expired.
	// Trying whith an admin
	@Test(expected=NullPointerException.class)
	public void testEditAdmin(){
		authenticate("admin1");
		Essay essay=essayService.findOne(18);
		essay.setContents("asnjashaosidhaoid");
		essay.setAbstractEss("agkshgagdsiuagdlakd");
		essay.setTitle("gwuguiqgiwgiagwdiqgad");
		essayService.save(essay);
		authenticate(null);
	}
	// A user who is authenticated as an author must be able to:
	// List the essays that he or she's submitted to a given contest.
	// Trying whith an organiser
	@Test(expected=NullPointerException.class)
	public void testFindByContestOrganiser(){
		authenticate("organiser1");
		Collection<Essay> essays=essayService.submittedEssaysByContest(13);
		Assert.isTrue(essays.size()==1);
		authenticate(null);
	}
	//A user who is authenticated as an organiser must be able to:
	//Decide on whether an essay must be published or not. 
	//The decision can be made only after the hold date of 
	//the corresponding contest has expired.
	@Test(expected=IllegalArgumentException.class)
	public void testPublishEssayNotExpired(){
		authenticate("author1");
		essayService.publish(21);
		authenticate(null);
	}
	//	A user who is authenticated as an administrator must be able to:
	//		o Display a dashboard with the following information:
	//			The average number of essays submitted by an author. Trying whith an author
	@Test(expected=IllegalArgumentException.class)
	public void testAvgNumberEssayAuthor(){
		authenticate("author1");
		Collection<Object[]> all=essayService.avgNumberEssaysSubmittedByAuthorId();
		List<Double> afterList=new ArrayList<Double>();
		for(Object[] obj:all){
			Double aux=(Double) obj[1];
			afterList.add(aux);
		}
		Assert.isTrue(afterList.get(1)== 0.25);
		authenticate(null);
	}
}
