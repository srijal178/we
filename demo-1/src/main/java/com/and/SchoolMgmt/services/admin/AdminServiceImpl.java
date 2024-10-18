package com.and.SchoolMgmt.services.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.and.SchoolMgmt.entities.User;
import com.and.SchoolMgmt.enums.UserRole;
import com.and.SchoolMgmt.repositories.UserRepository;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.Enumerated;

@Service
public class AdminServiceImpl {

	private final UserRepository userRepository;
	
	
	public AdminServiceImpl(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}


	@PostConstruct
	@Enumerated
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(adminAccount == null) {
		User admin = new User();
		admin.setEmail("admin@test.com");
		admin.setName("admin");
		admin.setRole(UserRole.ADMIN);
	
		admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
		userRepository.save(admin);
		}
	}
}
