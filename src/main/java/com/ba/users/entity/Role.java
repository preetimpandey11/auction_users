/**
 * 
 */
package com.ba.users.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Preeti Pandey
 *
 */

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4021795818452913592L;

	@Id
	@Column(name = "code", updatable = false, nullable = false)
	private String code;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "role_privilege", joinColumns = @JoinColumn(name = "role_code"), inverseJoinColumns = @JoinColumn(name = "privilege_code"))
	private Set<Privilege> privileges = new HashSet<>();

}
