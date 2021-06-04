package jp.co.exbbsgit.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.exbbsgit.domain.Comment;

/**
 * Comments テーブルを操作するリポジトリ.
 * 
 * @author daiki.takayama
 *
 */
@Repository
public class CommentRepository {
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 投稿IDを受け取り、投稿に対して送られたコメントのリストを返す.
	 * 
	 * @param articleId 検索する投稿ID
	 * @return 該当するコメントリスト
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "select id,name,content,article_id from comments where article_id=:articleId order by id";

		SqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

	/**
	 * comments テーブルに列を追加する.
	 * 
	 * @param comment コメント情報を格納したオブジェクト
	 */
	public void insert(Comment comment) {
		String sql = "insert into comments(name,content,article_id) values(:name,:content,:articleId)";
		// Bean... に変更
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}
}
