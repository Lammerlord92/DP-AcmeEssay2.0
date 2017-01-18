package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Essay;
@Repository
public interface EssayRepository extends JpaRepository<Essay,Integer>{
	@Query("select a.essays from Author a where a.id=?1")
	Collection<Essay> findByAuthor(int authorId);
	@Query("select a.name,avg(a.essays.size)/count(e) from Author a, Essay e group by a")
	Collection<Object[]> avgNumberEssaysSubmittedByAuthorId();
	@Query("select e from Contest c join c.essays e where c.id=?1 and e.author.id=?2")
	Collection<Essay> submittedEssaysByContest(int contestId, int authorId);
	@Query("select e from Contest c join c.essays e where e.published is true and e.contest.id=?1")
	Collection<Essay> publishedEssaysByContest(int contestId);
	@Query("select e from Contest c join c.essays e where c.id=?1")
	Collection<Essay> submittedEssaysByContestOrganiser(int contestId);
	
	@Query("select e from PublicSession pS join pS.essays e where pS.id=?1")
	Collection<Essay> essaysByPublicSession(int publicSessionId);
}
