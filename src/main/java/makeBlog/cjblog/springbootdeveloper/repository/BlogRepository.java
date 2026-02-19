package makeBlog.cjblog.springbootdeveloper.repository;

import makeBlog.cjblog.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
