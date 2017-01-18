package services;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Author;
import domain.Essay;
import forms.AuthorForm;

@Transactional
@Service
public class AuthorService {
	// Managed repository -----------------------------------------	
	@Autowired
	private AuthorRepository authorRepository;
	// Supporting services ----------------------------------------
	// Constructor ------------------------------------------------
	// Simple CRUD methods ----------------------------------------
	public Author create(){
		Author result=new Author();
		
		UserAccount userAccount=new UserAccount();
		List<Authority> authorities=new ArrayList<Authority>();
		Authority a=new Authority();
		a.setAuthority(Authority.AUTHOR);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		
		Collection<Essay> essays=new ArrayList<Essay>();
		
		result.setUserAccount(userAccount);
		result.setEssays(essays);
		
		return result;
	}
	public Author findOne(int authorId){
		Author result;
		result=authorRepository.findOne(authorId);
		return result;
	}
	public Collection<Author> findAll(){
		isAdmin();
		Collection<Author> result;
		result=authorRepository.findAll();
		return result;
	}
	public void save(Author author){
		Assert.notNull(author);
		Date date=new Date();
		if(author.getCreditCard().getExpirationYear()==(date.getYear()+1900)){
			Assert.isTrue(author.getCreditCard().getExpirationMonth()>(date.getMonth()+1));
		}
		else{
			Assert.isTrue(author.getCreditCard().getExpirationYear()>date.getYear()+1900);
		}
		authorRepository.save(author);
	}
	public void delete(Author author){
		Assert.notNull(author);
		authorRepository.delete(author);
	}
	// Other business methods -------------------------------------
	public Author reconstruct(AuthorForm authorForm) {
		Assert.isTrue(authorForm.getPassword().equals(authorForm.getConfirmPassword()));
		Assert.isTrue(authorForm.isAccepConditions());
		Author result;
		result=create();
		UserAccount userAccount=result.getUserAccount();
		userAccount.setUsername(authorForm.getUserName());
		
		Md5PasswordEncoder encoder;		
		encoder= new Md5PasswordEncoder();
		String password=encoder.encodePassword(authorForm.getPassword(), null);
		userAccount.setPassword(password);
		
		result.setUserAccount(userAccount);
		
		result.setName(authorForm.getName());
		result.setSurname(authorForm.getSurname());
		result.setEmailAddress(authorForm.getEmailAddress());
		result.setContactPhone(authorForm.getContactPhone());
		result.setUrl(authorForm.getUrl());
		result.setBirthDate(authorForm.getBirthDate());
		result.setNationality(authorForm.getNationality());
		result.setCreditCard(authorForm.getCreditCard());
		
		return result;
	}
	public Author findByUserAccount(UserAccount userAccount) {
		Author result;
		result=authorRepository.findByUserAccount(userAccount);
		return result;
	}
	public Collection<Author> authorsMoreEssaysSubmitted(){
		isAdmin();
		Collection<Author> result;
		result=authorRepository.authorsMoreEssaysSubmitted();
		return result;
	}
	public Collection<Author> authorsMoreEssaysPublished(){
		isAdmin();
		Collection<Author> result;
		result=authorRepository.authorsMoreEssaysPublished();
		return result;
	}
	public Collection<Author> authorsLessEssaysPublished(){
		isAdmin();
		Collection<Author> result;
		result=authorRepository.authorsLessEssaysPublished();
		return result;
	}
	
	
	private void isAdmin() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities){
			if(a.getAuthority().equals("ADMIN")) res=true;
		}
		Assert.isTrue(res);
	}
}
