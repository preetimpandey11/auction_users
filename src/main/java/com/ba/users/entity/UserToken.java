/**
 * 
 */
package com.ba.users.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Preeti Pandey
 *
 */
@Data
@Entity
@Table(name = "user_token")
public class UserToken {

	@Id
	@Column(name = "user_id")
	private String id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "token_key")
	private String tokenKey;

	@Column
	private boolean active;

	@Column(name = "expired_at")
	private Timestamp expiredAt;

}
