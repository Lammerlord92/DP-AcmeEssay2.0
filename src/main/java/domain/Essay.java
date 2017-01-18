package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints=
		@UniqueConstraint(
				columnNames={"title","contest_id"}
				))
public class Essay extends DomainEntity{
	// Attributes -----------------------------------------------------------
	private String title;
	private String abstractEss;
	private String contents;
	private Date submissionDate;
	private boolean published;
	
	// Getters and Setters --------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getAbstractEss() {
		return abstractEss;
	}
	public void setAbstractEss(String abstractEss) {
		this.abstractEss = abstractEss;
	}
	
	@NotBlank
	@SafeHtml
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@Past
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	
	public boolean getPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	
	// Relationships --------------------------------------------------------
	private Author author;
	private Contest contest;
	private PublicSession publicSession;
	
	@ManyToOne(optional = false)
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@ManyToOne(optional = false)
	public Contest getContest() {
		return contest;
	}
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	@ManyToOne(optional = true)
	public PublicSession getPublicSession() {
		return publicSession;
	}
	public void setPublicSession(PublicSession publicSession) {
		this.publicSession = publicSession;
	}
	
	
}
