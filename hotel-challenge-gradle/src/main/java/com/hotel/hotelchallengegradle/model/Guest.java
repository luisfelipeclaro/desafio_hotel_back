package com.hotel.hotelchallengegradle.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "GUEST")
public class Guest extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotBlank
	@Size(min=3, max=255)
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@NotBlank
	@Size(min=3, max=255)
	@Column(name = "SOCIAL_IDENTIFICATION")
	private String socialIdentification;
	
	@NotBlank
	@Size(min=3, max=255)
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSocialIdentification() {
		return socialIdentification;
	}

	public void setSocialIdentification(String socialIdentification) {
		socialIdentification = socialIdentification.replace(".", "");
		socialIdentification = socialIdentification.replace("-", "");
		socialIdentification = socialIdentification.replace("/", "");
		this.socialIdentification = socialIdentification;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		phoneNumber = phoneNumber.replace("(", "");
		phoneNumber = phoneNumber.replace(")", "");
		phoneNumber = phoneNumber.replace("-", "");
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
