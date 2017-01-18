package services;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Organiser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrganiserServiceTest extends AbstractTest{
	@Autowired
	private OrganiserService organiserService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is not authenticated must be able to:
	//Register to the system as an organiser.
	//We will try to register as a organiser
	@SuppressWarnings("deprecation")
	@Test
	public void testRegister(){
		Organiser organiser=organiserService.create();
		Date fechaProb=new Date();
		fechaProb.setDate(fechaProb.getDate()-7500);
		
		organiser.setName("name");
		organiser.setSurname("surname");
		organiser.setNationality("nationality");
		organiser.setBirthDate(fechaProb);
		organiser.setContactPhone("954954954");
		organiser.setEmailAddress("Correo@gmail.com");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(345);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		
		organiser.setCreditCard(credit);
		
		organiserService.save(organiser);
	}

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------

	//A user who is not authenticated must be able to:
	//Register to the system as an organiser.
	//We will try to register as a organiser
	//And introduce a wrong Date to induce a fail.
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void testRegisterException(){
		Organiser organiser=organiserService.create();
		Date fechaProb=new Date("");
		
		organiser.setName("name");
		organiser.setSurname("surname");
		organiser.setNationality("nationality");
		organiser.setBirthDate(fechaProb);
		organiser.setContactPhone("954954954");
		organiser.setEmailAddress("Correo@gmail.com");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(111);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		
		organiser.setCreditCard(credit);
		
		organiserService.save(organiser);
	}
}