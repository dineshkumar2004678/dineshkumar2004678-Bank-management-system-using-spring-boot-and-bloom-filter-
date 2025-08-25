package com.example.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.database.entity.*;
	@Repository
	public interface Account_repo extends JpaRepository<Account, Long> {
	    boolean existsByAccountHolder(String accountHolder);
	}