package com.mac2work.cactus_user_panel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mac2work.cactus_user_panel.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	@Modifying
	@Query("UPDATE users u SET u.cityId = :cityId WHERE u.id = :id")
	public User UpdateCityIdById(@Param(value = "cityId") Long cityId, @Param(value = "id") Long id);
}
