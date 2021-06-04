package jp.co.exbbsgit.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.exbbsgit.domain.Article;
import jp.co.exbbsgit.domain.Comment;

/**
 * 記事用リポジトリ.
 * 
 * @author ryotaro.seya
 *
 */
@Repository
public class ArticleRepository {
	private final static String ARTICLE_TABLE_NAME = "articles";
	private final static String COMMENT_TABLE_NAME = "comments";
	private static String TABLE_NAME = "articles";

	private static Comment getCommentFromResultSet(ResultSet rs) throws SQLException {
		Comment comment = new Comment();
		comment.setId(rs.getInt("c_id"));
		comment.setName(rs.getString("c_name"));
		comment.setContent(rs.getString("c_content"));
		comment.setArticleId(rs.getInt("c_article_id"));
		return comment;
	}

	// TODO: 演習6 で使うクラス
	private final static ResultSetExtractor<List<Article>> ARTICLE_SET_EXTRACTOR = (rs) -> {
		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = null;
		int beforeArticleId = 0;
		Article article = null;

		while (rs.next()) {
			// 新しい記事
			if (rs.getInt("a_id") != beforeArticleId) {
				// 記事情報セット
				article = new Article();
				article.setId(rs.getInt("a_id"));
				article.setName(rs.getString("a_name"));
				article.setContent(rs.getString("a_content"));

				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				articleList.add(article);
			}
			// コメント追加
			if (rs.getInt("c_id") != 0) {
				commentList.add(getCommentFromResultSet(rs));
			}
			beforeArticleId = rs.getInt("a_id");
		}

		return articleList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public List<Article> findAll() {
		String sql = "select a.id a_id, a.name a_name, a.content a_content, c.id c_id, c.name c_name, c.content c_content, c.article_id c_article_id from "
				+ ARTICLE_TABLE_NAME + " as a left outer join " + COMMENT_TABLE_NAME
				+ " as c on a.id = c.article_id order by a.id desc, c_id desc";
		return template.query(sql, ARTICLE_SET_EXTRACTOR);
	}

	public void insert(Article article) {
		String sql = "insert into " + TABLE_NAME + "(name, content) values (:name, :content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}

	public void deleteById(int id) {
		String sql = "delete from " + TABLE_NAME + " where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
