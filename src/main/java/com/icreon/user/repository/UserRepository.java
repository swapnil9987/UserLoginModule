package com.icreon.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icreon.user.model.User;
/**
 * @author swapnil.mane
 * 
 * This is Repository class to handle
 * operations regarding db
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	//User findByEmail(String email);
	
	//public static final String password = "user";
	
	Optional<User> findByEmail(String email);
	
	public boolean existsByEmail(String email);
	
	User findById(Long id);
	
	//Optional<User> findByPassword(String password);
	
	User findByPassword(String password2);
	
}
