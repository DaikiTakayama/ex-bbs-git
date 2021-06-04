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

import jp.co.exbbsgit.domain.Article;

/**
 * 記事用リポジトリ.
 * 
 * @author ryotaro.seya
 *
 */
@Repository
public class ArticleRepository {
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);
	private static String TABLE_NAME = "articles";

	@Autowired
	private NamedParameterJdbcTemplate template;

	public List<Article> findAll() {
		String sql = "select id, name, content from " + TABLE_NAME + " order by id desc";
		return template.query(sql, ARTICLE_ROW_MAPPER);
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
