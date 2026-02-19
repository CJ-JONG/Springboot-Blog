package makeBlog.cjblog.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import makeBlog.cjblog.springbootdeveloper.domain.Article;
import makeBlog.cjblog.springbootdeveloper.dto.AddArticleRequest;
import makeBlog.cjblog.springbootdeveloper.dto.UpdateArticleRequest;
import makeBlog.cjblog.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {

        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+id));
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;

    }
}
