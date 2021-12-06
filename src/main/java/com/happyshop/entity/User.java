package com.happyshop.entity;

import java.util.Set;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
public class User {
	@Id
	@NotEmpty
	String id;
	@NotEmpty
	@Length(min=6)
	String password;
	@NotEmpty
	String fullname;
	@NotEmpty
	String telephone;
	@NotEmpty
	@Email
	String email;
	String photo;
	Boolean activated;
	Boolean admin;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Comment> comments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", fullname=" + fullname + ", telephone=" + telephone
				+ ", email=" + email + ", photo=" + photo + ", activated=" + activated + ", admin=" + admin
				+ "]";
	}
	
	

}