/**
 * 
 */
package com.ba.users.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Preeti Pandey
 *
 */
@Data
@Entity
@Table(name = "auction_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4989761052557792533L;

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_code"))
	private Set<Role> roles = new HashSet<>();

	@Column(nullable = false, unique = true)
	private String username;

	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column
	private String email;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "zipcode")
	private String zipcode;

	@Column
	private boolean active;

	@CreationTimestamp
	@Column(name = "created_at")
	protected LocalDateTime createdAt;


}
