package jp.co.exbbsgit.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.exbbsgit.domain.Article;
import jp.co.exbbsgit.form.ArticleForm;
import jp.co.exbbsgit.repository.ArticleRepository;

@Controller
@RequestMapping("/ex-bbs-git")
public class InsertArticleController {
	@Autowired
	private ArticleRepository articleRepository;

	@RequestMapping("/insert")
	public void insert(ArticleForm articleForm) {
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
	}
}
