package makeBlog.cjblog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import makeBlog.cjblog.springbootdeveloper.domain.Article;
import makeBlog.cjblog.springbootdeveloper.dto.ArticleResponse;
import makeBlog.cjblog.springbootdeveloper.dto.ArticleViewResponse;
import makeBlog.cjblog.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleViewResponse> articles = blogService.findAll().stream()
                .map(ArticleViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(Model model, @PathVariable Long id) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
