package com.and.SchoolMgmt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.and.SchoolMgmt.entities.User;
import com.and.SchoolMgmt.enums.UserRole;





@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByRole(UserRole userRole);

	Optional<User> findFirstByEmail(String email);

	 
	
}
