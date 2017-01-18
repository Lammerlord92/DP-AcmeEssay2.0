package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;
import domain.Organiser;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Integer>{
	@Query("select o.contests from Organiser o where o.id=?1")
	Collection<Contest> findByOrganiser(int organiserId);
//	@Query("select c from Contest c join c.contestOrganisers o where ?1 member of o")
//	Collection<Contest> findByOrganiser(Organiser organiser);
	@Query("select c from Contest c order by c.essays.size desc")
	Collection<Contest> orderByNumberOfEssaysSubmittedDesc();
	@Query("select o.name,avg(o.contests.size)/count(c) from Organiser o,Contest c group by o")
	Collection<Object[]> avgContestOrganisedByOrganiser();
}
