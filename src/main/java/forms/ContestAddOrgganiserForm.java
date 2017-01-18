package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import domain.Organiser;

@Embeddable
@Access(AccessType.PROPERTY)
public class ContestAddOrgganiserForm {
	private int contestId;
	private Organiser organiser;
	
	public int getContestId() {
		return contestId;
	}
	public void setContestId(int contestId) {
		this.contestId = contestId;
	}
	@NotNull
	public Organiser getOrganiser() {
		return organiser;
	}
	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}
	
	
}
