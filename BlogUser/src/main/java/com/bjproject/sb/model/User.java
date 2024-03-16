package com.bjproject.sb.model;




import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@Column
	@NotNull(message="Name cannot be null")
	@NotBlank(message="Name cannot be blank")
	@Length(min=2,max=10)
	String name;
	@Column
	@NotNull(message="Email cannot be null")
	@NotBlank(message="Email cannot be blank")
	@Email(message="Email format is wrong")
	String email;
	@Column
	@NotNull(message="Password cannot be null")
	@NotBlank(message="Password cannot be blank")
	@Size(min=6,max=12)
	String password;
}
