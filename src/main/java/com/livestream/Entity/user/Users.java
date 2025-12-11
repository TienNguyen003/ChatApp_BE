package com.livestream.Entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.livestream.Entity.role.Role;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String username;

	String email;

	String name;

	String dob;

	String phone;

	String address;

	@JsonIgnore
	String password;

	String urlImage;

	int status;

	@ManyToOne
	Role role;
}
