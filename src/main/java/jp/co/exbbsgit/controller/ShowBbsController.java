package jp.co.exbbsgit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.exbbsgit.domain.Article;
import jp.co.exbbsgit.form.ArticleForm;
import jp.co.exbbsgit.form.CommentForm;
import jp.co.exbbsgit.repository.ArticleRepository;

@Controller
@RequestMapping("ex-bbs-git")
@Transactional
public class ShowBbsController {
	@Autowired
	private ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	private CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	@Autowired
	private ArticleRepository articleRepository;


	/**
	 * 書き込み内容とコメントを出力.
	 * 
	 * @param model 書き込み情報を格納するリクエストスコープ
	 * @return 掲示板画面を出力
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList",articleList);
		return "index-bbs";
		
	}
}
