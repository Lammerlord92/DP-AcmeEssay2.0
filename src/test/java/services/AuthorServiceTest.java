package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;

import domain.Author;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AuthorServiceTest extends AbstractTest{
	@Autowired
	private AuthorService authorService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	//A user who is not authenticated must be able to:
	//Register to the system as an author.
	//We will try to register and save a new author
	@Test
	public void testRegister(){
		Author author=authorService.create();
		Date fechaProb=new Date();
		fechaProb.setDate(fechaProb.getDate()-7500);
		
		author.setName("name");
		author.setSurname("surname");
		author.setEmailAddress("Correo@gmail.com");
		author.setContactPhone("954954954");
		author.setBirthDate(fechaProb);
		author.setNationality("nationality");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(345);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		author.setCreditCard(credit);
		
		UserAccount user=author.getUserAccount();
		user.setUsername("nameSurname");
		user.setPassword("nameSurname");
		author.setUserAccount(user);
		
		authorService.save(author);
	}
	//A user who is authenticated as an administrator must be able to:
	//List the authors and organisers and see their details.
	//With an existing administrator, we will try to find all authors
	@Test
	public void testFindAllAuthor(){
		authenticate("admin1");
		Collection<Author> all=authorService.findAll();
		Assert.isTrue(all.size()==3);
		authenticate(null);
	}
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of authors who have submitted more essays.
	//With an existing administrator, we will check that he can find the expected result
	@Test
	public void testFindOrganiserMoreEssaysSubmitted(){
		authenticate("admin1");
		Collection<Author> all=authorService.authorsMoreEssaysSubmitted();
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of authors who have got more essays published.
	//With an existing administrator, we will check that he can find the expected result
	@Test
	public void testFindOrganiserMoreEssaysPublished(){
		authenticate("admin1");
		Collection<Author> all=authorService.authorsMoreEssaysPublished();
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of authors who have got less essays published.
	//With an existing administrator, we will check that he can find the expected result
	@Test
	public void testFindOrganiserLessEssaysPublished(){
		authenticate("admin1");
		Collection<Author> all=authorService.authorsLessEssaysPublished();
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}
	
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	//A user who is not authenticated must be able to:
	//Register to the system as an author.
	//We will try to register as a new author with wrong attributes and capture the exception
	@Test(expected=IllegalArgumentException.class)
	public void testRegisterException(){
		authenticate("admin1");
		Author author=authorService.create();
		Date fechaProb=new Date("badAttribute");
		fechaProb.setDate(fechaProb.getDate()-7500);
		
		author.setName("name");
		author.setSurname("surname");
		author.setEmailAddress("Correo@gmail.com");
		author.setContactPhone("954954954");
		author.setBirthDate(fechaProb);
		author.setNationality("nationality");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(345);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		author.setCreditCard(credit);
		
		UserAccount user=author.getUserAccount();
		user.setUsername("nameSurname");
		user.setPassword("nameSurname");
		author.setUserAccount(user);
		
		authorService.save(author);
		authenticate(null);
	}
	//A user who is authenticated as an administrator must be able to:
	//List the authors and organisers and see their details.
	//With an existing organiser, we will try to find all organisers
	@Test(expected=IllegalArgumentException.class)
	public void testFindAllOrganiserException(){
		authenticate("organiser1");
		Collection<Author> all=authorService.findAll();
		Assert.isTrue(all.size()==2);
		authenticate(null);
	}
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of authors who have submitted more essays.
	//With an existing organiser, we will check that he can find the expected result
	@Test(expected=IllegalArgumentException.class)
	public void testFindOrganiserMoreEssaysSubmittedException(){
		authenticate("organiser1");
		Collection<Author> all=authorService.authorsMoreEssaysSubmitted();
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of authors who have got more essays published.
	//With an existing organiser, we will check that he can find the expected result
	@Test(expected=IllegalArgumentException.class)
	public void testFindOrganiserMoreEssaysPublishedException(){
		authenticate("organiser1");
		Collection<Author> all=authorService.authorsMoreEssaysPublished();
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}	
	//A user who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:
	//The list of authors who have got less essays published.
	//With an existing organiser, we will check that he can find the expected result
	@Test(expected=IllegalArgumentException.class)
	public void testFindOrganiserLessEssaysPublishedException(){
		authenticate("organiser1");
		Collection<Author> all=authorService.authorsLessEssaysPublished();
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}
}
