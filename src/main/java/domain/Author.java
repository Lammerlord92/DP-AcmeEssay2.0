package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Author extends Customer{
	// Attributes -----------------------------------------------------------
	// Getters and Setters --------------------------------------------------
	// Relationships --------------------------------------------------------
	private Collection<Essay> essays;

	@OneToMany(mappedBy = "author")
	public Collection<Essay> getEssays() {
		return essays;
	}
	public void setEssays(Collection<Essay> essays) {
		this.essays = essays;
	}
}
