package com.harikesh.XMeme.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * The common properties for all entities are present in this class.
 * 
 * @author harikesh.pallantla
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

	/** The creation time. */
	@CreationTimestamp
	@Column(updatable = false)
	private ZonedDateTime createdOn;

	/** The last updated time. */
	@UpdateTimestamp
	private ZonedDateTime lastUpdatedOn;

	/** The deleted flag. */
	private boolean deleted = false;

}
