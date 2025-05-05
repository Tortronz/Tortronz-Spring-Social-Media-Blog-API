package com.example.repository;

import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

/** 
 * This is a data access repository that manages the interactions Java objects
 * of the class "Account" and the SQL database table "account". 
 *  
 * The database table "account" has the columns: 
 * account_id   int             Primary key, and unique identifiers for
 *                              accounts
 * username     varchar(255)	Name for account, is unique 
 * password     varchar(255)	Login for account 
 */ 
public interface AccountRepository extends JpaRepository<Account, Long>{
    /**
     * Returns account with the specified username and password.
     * @param username
     * @param password
     * @return  account with the specified username and password
     */
    Account findByUsernameAndPassword(String username, String password);
}
