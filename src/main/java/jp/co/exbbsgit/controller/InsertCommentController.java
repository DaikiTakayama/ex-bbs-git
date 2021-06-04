package jp.co.exbbsgit.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.exbbsgit.domain.Comment;
import jp.co.exbbsgit.form.CommentForm;
import jp.co.exbbsgit.repository.CommentRepository;

/**
 * コメントを投稿するコントローラ 
 * 
 * @author daiki.takayama
 *
 */
@Controller
@RequestMapping("/ex-bbs-git")
public class InsertCommentController {
	

	@ModelAttribute
	private CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * コメントを追加登録する処理を実行する.
	 * 
	 * @param commentForm        コメントのリクエストパラメータを格納するオブジェクト
	 * @param result             エラーメッセージを格納するオブジェクト
	 * @param redirectAttributes flashスコープを使用するため準備
	 * @param model              リクエストスコープ(joinIndex()を呼び出す際に必要)
	 * 
	 * @return 掲示板画面を表示
	 */
	@RequestMapping("/insertComment")
	public String insertComment(@Validated CommentForm commentForm, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {

		System.out.println(commentForm.getName());
//		if (result.hasErrors()) {
//			model.addAttribute("errorArticleId",commentForm.getArticleId());
//
//			return joinIndex(model);
//		}
		
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
//		System.out.println(comment.getArticleId());

		commentRepository.insert(comment);
		return "redirect:/ex-bbs-git/index";
	}
}
