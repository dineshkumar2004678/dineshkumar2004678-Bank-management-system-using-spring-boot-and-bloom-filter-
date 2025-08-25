package com.example.database.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Account {
	@Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Account_number;
	@Column(name = "account_holder") 
	private String accountHolder;
	@Column
	private double balance;
	public Long getAccount_number() {
		return Account_number;
	}
	public void setAccount_number(Long account_number) {
		Account_number = account_number;
	}
	public String getAccount_holder() {
		return accountHolder;
	}
	public void setAccount_holder(String account_holder) {
		accountHolder = account_holder;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Account(String account_holder, double balance) {
		super();
		accountHolder = account_holder;
		this.balance = balance;
	}
	public Account() {
		super();
		
	}
	
	

}
