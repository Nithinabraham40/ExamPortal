package com.tutorial.exam.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	@Builder
	@Table(name="tbl_token")
	public class Authentication {

		
		
		@Id
		@SequenceGenerator(name = "token_sequence",sequenceName = "token_sequence0",allocationSize = 1,initialValue = 6000)
		@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "token_sequence")
		private Long authId;
		
		private String token;
		private LocalDate creationDate;
		
		@OneToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "fk_userId")

		private User user;
		
		
		public Authentication(User user) {
			
			this.user=user;
			this.creationDate=LocalDate.now();
			this.token=UUID.randomUUID().toString();
			
		}
		
		
		
	}

