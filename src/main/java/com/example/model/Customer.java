package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity      
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Customer {

	@Id //primary key for the table
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	    private int id;
	    private String name;
	    private String address;
	    private String email;
	    private String username;
	    private String password;
	    private int age;
	    private String ssn;
	    private String phoneNumber;
	    private float balance;
}
