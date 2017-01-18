package domain;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

@Entity
@Access(AccessType.PROPERTY)
public class PublicSession extends DomainEntity{
	
	// Attributes -----------------------------------------------------------
	
	private Date startMoment;
	private Date endMoment;
	private Integer capacity;
	
	// Getters and Setters --------------------------------------------------
	
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndMoment() {
		return endMoment;
	}
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	
	@NotNull
	@Min(1)
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	// Relationships --------------------------------------------------------
	
	private Organiser organiser;
	private Contest contest;
	private Collection<Essay> essays;
	
	@NotNull
	@ManyToOne(optional = false)
	public Organiser getOrganiser() {
		return organiser;
	}
	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}
	
	@NotNull
	@ManyToOne(optional = false)
	public Contest getContest() {
		return contest;
	}
	public void setContest(Contest contest) {
		this.contest = contest;
	}

	@OneToMany(mappedBy = "publicSession")
	public Collection<Essay> getEssays() {
		return essays;
	}
	public void setEssays(Collection<Essay> essays) {
		this.essays = essays;
	}
	
	
	public void conditionsOk(PublicSession publicSession) {
		Assert.notNull(publicSession.getStartMoment());
		Assert.notNull(publicSession.getEndMoment());
		Assert.isTrue(publicSession.getStartMoment().before(publicSession.getEndMoment()));
		Assert.isTrue(publicSession.getStartMoment().after(publicSession.getContest().getHoldingDate()));
		Assert.isTrue(publicSession.getStartMoment().after(new Date()));
	}
}