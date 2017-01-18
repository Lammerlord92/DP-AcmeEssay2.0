package repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import security.UserAccount;
import domain.Organiser;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser,Integer>{
	@Query("select o from Organiser o where o.userAccount=?1")
	Organiser findByUserAccount(UserAccount userAccount);
	
	@Query("select o.name,o.publicSessions.size from Organiser o where o.publicSessions.size>0")
	Collection<Object[]> findChairmans();

//	@Query("select o from Organiser o where contest member of o.contests")
//	Collection<Organiser> findByContest(Contest contest);
}
