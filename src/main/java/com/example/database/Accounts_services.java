package com.example.database;

import com.example.database.entity.Account;
import java.util.List;

public interface Accounts_services {
	  Account insertAccount(Account account);
	    Account getAccountDetails(long accountNumber);
	    Account closeAccount(long accountNumber);
	    Account withdraw(long accountNumber, double amount);
	    Account deposit(long accountNumber, double amount);
	}

