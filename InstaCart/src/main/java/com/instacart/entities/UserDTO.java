package com.instacart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String userType;
	private String name;
	private String email;
	private Long contactNumber;
	private Integer age;
	private String password;
	private String city;
	private Long aadharNumber;
	private Long panNumber;
	private Long gstNumber;
	private long adminPassword;
	
	

}
