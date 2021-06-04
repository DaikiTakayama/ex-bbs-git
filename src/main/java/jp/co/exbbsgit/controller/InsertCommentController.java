package jp.co.exbbsgit.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Transactional
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
	public String insertComment(CommentForm commentForm,Model model) {

		System.out.println(commentForm.getName());
		
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
//		System.out.println(comment.getArticleId());

		commentRepository.insert(comment);
		return "redirect:/ex-bbs-git/index";
	}
}
