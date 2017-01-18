package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer>{
	@Query("select a from Author a where a.userAccount=?1")
	Author findByUserAccount(UserAccount userAccount);
	@Query("select a from Author a where a.essays.size=(select max(a.essays.size) from Author a) group by a.name")
	Collection<Author> authorsMoreEssaysSubmitted();
	@Query("select a from Author a where (select count(e) from Essay e where e.published is true and e.author.id=a.id)>=all (select count(e) from Essay e where e.published is true group by e.author)")
	Collection<Author> authorsMoreEssaysPublished();
	@Query("select a from Author a where (select count(e) from Essay e where e.published is true and e.author.id=a.id)= (select min(a.essays.size) from Author a join a.essays e where e.published is true)")
	Collection<Author> authorsLessEssaysPublished();
}
