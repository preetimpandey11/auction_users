/**
 * 
 */
package com.ba.users.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Preeti Pandey
 *
 */

@Data
@Entity
@Table(name = "privilege")
public class Privilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4290862313801052388L;

	@Id
	@Column(name = "code", updatable = false, nullable = false)
	private String code;

}
