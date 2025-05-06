package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    /** 
     * Returns account with the specified ID. 
     * @param accountId 
     * @return  account with the specified ID 
     */ 
     Account findByAccountId(int accountId); 

     /** 
      * Returns account with the specified username. 
      * @param username 
      * @return  account with the specified username 
      */ 
     Account findByUsername(String username); 

     /** 
      * Returns account with the specified username and password. 
      * @param username 
      * @param password 
      * @return  account with the specified username and password 
      */ 
     Account findByUsernameAndPassword(String username, String password);
}
