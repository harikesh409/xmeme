package com.harikesh.XMeme.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class Meme.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
/**
 * Instantiates a new meme.
 */
@NoArgsConstructor

/**
 * Instantiates a new meme.
 *
 * @param id the id
 * @param name the name
 * @param url the url
 * @param caption the caption
 */
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "url", "caption"}))
@Entity
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE meme SET deleted=true WHERE id=?", check = ResultCheckStyle.COUNT)
public class Meme extends BaseEntity {

	/** The id. */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	/** The name. */
	@Column(nullable = false, updatable = false)
	private String name;

	/** The url. */
	@Column(nullable = false, length = 700)
	private String url;

	/** The caption. */
	@Column(nullable = false)
	private String caption;
}
