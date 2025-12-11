package com.livestream.Repository.role;

import com.livestream.Entity.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);

    @Query("SELECT r FROM Role r WHERE" +
            "(:name IS NULL OR r.name LIKE %:name%)")
    Page<Role> findByName
            (String name, Pageable pageable);
}
