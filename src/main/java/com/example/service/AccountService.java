package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

/** 
 * This is a Service class that acts between the endpoints (controller) and the 
 * database (repository) of the "Account" Java class, validating input. 
 */ 
@Service
public class AccountService {
    AccountRepository accountRepository;

    // CONSTRUCTORS //
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // CREATE OPERATIONS //
    /** 
	 * This registers a new account into the "account" database table. 
	 *  
	 * @param account   The new account to be registered 
	 *  
	 * @return	Account if it was successfully persisted, or "null" if it 
	 *          wasn't successfully persisted or if username or password were
	 *          invalid 
	 */ 
	public Account registerAccount(Account account) { 
		// Check username isn't blank 
		if(account.getUsername() == "") { 
			return null; 
		} 
		// Check password is more than 4 characters 
		if(account.getPassword().length() < 4) { 
			return null; 
		} 
 
		return this.accountRepository.save(account); 
	} 
 
	// READ OPERATIONS // 
	/** 
	 * This authorizes/logs in an account. 
	 *  
	 * @param account   The account credientials attempting to log in 
	 *  
	 * @return	Account if it was successfully logged in, or "null" if it
	 *          wasn't successfully logged in 
	 */ 
	public Account loginAccount(Account account) { 
		return this.accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
	} 
}