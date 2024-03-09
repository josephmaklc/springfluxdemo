package com.optimal.solutions.springfluxdemo.r2dbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "person")
public class Person {


	@Id
	@Column("id")
    private int id;

    @Column("lastname")
    private String lastname;

    @Column("firstname")
    private String firstname;

    @Column("email")
    private String email;
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return "You are "+firstname+" "+lastname+" and your email is "+email;
	}

}
