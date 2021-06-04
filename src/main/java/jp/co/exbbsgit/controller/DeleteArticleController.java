package jp.co.exbbsgit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.exbbsgit.repository.ArticleRepository;

@Controller
@RequestMapping("/ex-bbs-git")
@Transactional
public class DeleteArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	@RequestMapping("/delete")
	public String delete(int articleId) {
		articleRepository.deleteById(articleId);
		return "redirect:/ex-bbs-git";
	}
}
