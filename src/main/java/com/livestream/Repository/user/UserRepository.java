package com.livestream.Repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.livestream.Entity.user.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	boolean existsByUsername(String username);

	Optional<Users> findByUsername(String username);

	@Query("SELECT u FROM Users u WHERE u.status = 1")
	List<Users> findAllUserActive();

	@Query("SELECT u FROM Users u WHERE" +
			"(:username IS NULL OR u.username LIKE %:username%) AND" +
			"(:role IS NULL OR u.role.name LIKE %:role%)")
	Page<Users> findByName
			(String name, String username, String role, Pageable pageable);
}
