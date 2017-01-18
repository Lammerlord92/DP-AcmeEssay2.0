package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Contest extends DomainEntity{
	// Attributes -----------------------------------------------------------
	private String name;
	private String description;
	private Date holdingDate;
	private Date deadline;
	private String result;
	
	// Getters and Setters --------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getHoldingDate() {
		return holdingDate;
	}
	public void setHoldingDate(Date holdingDate) {
		this.holdingDate = holdingDate;
	}
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	@SafeHtml
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	// Relationships --------------------------------------------------------
	private Collection<Organiser> contestOrganisers;
	private Collection<Essay> essays;
	private Collection<PublicSession> publicSessions;

	@ManyToMany(cascade={CascadeType.PERSIST})
	public Collection<Organiser> getContestOrganisers() {
		return contestOrganisers;
	}
	public void setContestOrganisers(Collection<Organiser> contestOrganisers) {
		this.contestOrganisers = contestOrganisers;
	}
	
	@OneToMany(mappedBy = "contest")
	public Collection<Essay> getEssays() {
		return essays;
	}
	public void setEssays(Collection<Essay> essays) {
		this.essays = essays;
	}
	
	@OneToMany(mappedBy = "contest")
	public Collection<PublicSession> getPublicSessions() {
		return publicSessions;
	}
	public void setPublicSessions(Collection<PublicSession> publicSessions) {
		this.publicSessions = publicSessions;
	}
}
