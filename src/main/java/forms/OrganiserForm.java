package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.CreditCard;

@Embeddable
@Access(AccessType.PROPERTY)
public class OrganiserForm {
	private String userName;
	private String password;
	private String confirmPassword;
	private String name;
	private String surname;
	private String emailAddress;
	private String contactPhone;
	private String url;
	private CreditCard creditCard;
	private Date birthDate;
	private String nationality;

	private boolean accepConditions;

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@Size(min = 5, max = 32)
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@Email
	@NotBlank
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@URL
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public boolean isAccepConditions() {
		return accepConditions;
	}
	public void setAccepConditions(boolean accepConditions) {
		this.accepConditions = accepConditions;
	}
	
	
}
