package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PublicSession;

@Repository
public interface PublicSessionRepository extends JpaRepository<PublicSession,Integer>{
	@Query("select pS from PublicSession pS where pS.organiser.id=?1")
	Collection<PublicSession> findByOrganiserId(int organiserId);
	
	@Query("select pS from PublicSession pS where pS.contest.id=?1")
	Collection<PublicSession> findByContestId(int contestId);
	
	@Query("select e.publicSession from Essay e where e.id=?1")
	PublicSession findByEssayId(int essayId);
	
	@Query("select pS from PublicSession pS order by pS.capacity desc")
	Collection<PublicSession> findDescOrderCapacity();
}
