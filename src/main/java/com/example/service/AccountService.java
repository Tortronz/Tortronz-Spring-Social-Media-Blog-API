package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public ResponseEntity<Account> registerAccount(Account account) { 
		// Check username isn't blank 
		if(account.getUsername() == "") { 
			return ResponseEntity.status(400).body(null);
		} 
		// Check password is more than 4 characters 
		if(account.getPassword().length() < 4) { 
			return ResponseEntity.status(400).body(null); 
		}

		// Check there isn't an account already with the specified username
		if(this.accountRepository.findByUsername(account.getUsername()) != null) {
			return ResponseEntity.status(409).body(null);
		}
 

		Account addedAccount = this.accountRepository.save(account);

		if (addedAccount != null) { // Account successfully registered
			return ResponseEntity.status(200).body(addedAccount);

		} else { // Registration failed due to other error
			return ResponseEntity.status(400).body(null);
		}
	} 
 
	/** 
	 * This authorizes/logs in an account.
	 *  
	 * @param account   The account credientials attempting to log in 
	 *  
	 * @return	Account if it was successfully logged in, or "null" if it
	 *          wasn't successfully logged in 
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<Account> loginAccount(Account account) {
		Account loggedInAccount = this.accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());

		if (loggedInAccount != null) { // Account successfully logged in
			return ResponseEntity.status(200).body(loggedInAccount);

		} else { // Login failed / unauthorized
			return ResponseEntity.status(401).body(null);
		}
	} 
}