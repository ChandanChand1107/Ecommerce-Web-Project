package com.zosh.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String mobile;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();
    
    //@JoinColumn
    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name = "user"))
    private List<PaymentInformation> paymentinformation = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> rating = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> review = new ArrayList<>();
    
    private LocalDateTime createdAt;
    
    public User() {
		// TODO Auto-generated constructor stub
	}
    
    

	public User(Long id, String firstName, String lastName, String password, String email, String mobile,
			List<Address> address, List<PaymentInformation> paymentinformation, List<Rating> rating,
			List<Review> review, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.paymentinformation = paymentinformation;
		this.rating = rating;
		this.review = review;
		this.createdAt = createdAt;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<PaymentInformation> getPaymentinformation() {
		return paymentinformation;
	}

	public void setPaymentinformation(List<PaymentInformation> paymentinformation) {
		this.paymentinformation = paymentinformation;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    

}
