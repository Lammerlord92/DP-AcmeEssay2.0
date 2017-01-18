package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AdministratorServiceTest extends AbstractTest{
	@Autowired
	private AdministratorService administratorService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is not authenticated must be able to:
	//Register to the system as an administrator.
	//We will try to register as a administrator
	@Test
	public void testRegister(){
		authenticate("admin1");
		
		Administrator administrator=administratorService.create();
		
		administrator.setName("name");
		administrator.setSurname("surname");
		administrator.setContactPhone("954954954");
		administrator.setEmailAddress("Correo@gmail.com");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(345);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		
		administratorService.save(administrator);
		
		unauthenticate();
	}

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------

	//A user who is not authenticated must be able to:
	//Register to the system as an administrator.
	//We will try to register as a administrator
	//And introduce an incorrect Name to induce a fail.
	@Test(expected=ConstraintViolationException.class)
	public void testRegisterException(){
		authenticate("admin1");
		
		Administrator administrator=administratorService.create();
		
		administrator.setName("");
		administrator.setSurname("surname");
		administrator.setContactPhone("954954954");
		administrator.setEmailAddress("Correo@gmail.com");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(111);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		
		administratorService.save(administrator);
		
		authenticate("admin1");
	}
}