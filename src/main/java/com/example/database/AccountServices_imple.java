package com.example.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.database.bloom.AccountBloomFilter;
import com.example.database.entity.Account;
import com.example.database.repo.Account_repo;
@Service
public class AccountServices_imple implements Accounts_services  {
	@Autowired
	Account_repo accountRepository;

	 @Autowired
	    private AccountBloomFilter bloomFilter;
	    
	    @Override
	    public Account insertAccount(Account account) {
	        if (accountRepository.existsByAccountHolder(account.getAccount_holder())) {
	            throw new RuntimeException("Account with this name already exists!");
	        }
	        
	        Account savedAccount = accountRepository.save(account);
	        bloomFilter.addName(savedAccount.getAccount_holder());
	        return savedAccount;
	    }
	    
	    @Override 
	    public Account getAccountDetails(long accountNumber) { 
	        return accountRepository.findById(accountNumber).orElse(null);
	    }
	    
	    @Override
	    public Account closeAccount(long accountNumber) {
	        Account account = accountRepository.findById(accountNumber).orElse(null);
	        if (account != null) {
	            accountRepository.delete(account);
	        }
	        return account;
	    }
	    
	    @Override
	    public Account withdraw(long accountNumber, double amount) {
	        Optional<Account> account = accountRepository.findById(accountNumber);
	        
	        if (account.isEmpty()) {
	            throw new RuntimeException("Account not found");
	        }
	        
	        if (account.get().getBalance() - amount < 0) {
	            throw new RuntimeException("Insufficient funds");
	        }
	        
	        account.get().setBalance(account.get().getBalance() - amount);
	        return accountRepository.save(account.get());
	    }
	    
	    @Override
	    public Account deposit(long accountNumber, double amount) {
	        Optional<Account> account = accountRepository.findById(accountNumber);
	        
	        if (account.isEmpty()) {
	            throw new RuntimeException("Account not found");
	        }
	        
	        account.get().setBalance(account.get().getBalance() + amount);
	        return accountRepository.save(account.get());
	    }

			}