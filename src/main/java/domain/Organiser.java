package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Organiser extends Customer{
	// Attributes -----------------------------------------------------------
	// Getters and Setters --------------------------------------------------
	// Relationships --------------------------------------------------------
	private Collection<Contest> contests;
	private Collection<PublicSession> publicSessions;

	@ManyToMany(cascade={CascadeType.ALL})
	public Collection<Contest> getContests() {
		return contests;
	}
	public void setContests(Collection<Contest> contests) {
		this.contests = contests;
	}
	
	@OneToMany(mappedBy = "organiser")
	public Collection<PublicSession> getPublicSessions() {
		return publicSessions;
	}
	public void setPublicSessions(Collection<PublicSession> publicSessions) {
		this.publicSessions = publicSessions;
	}
}
