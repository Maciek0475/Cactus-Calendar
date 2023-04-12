package com.mac2work.myfirstproject.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mac2work.myfirstproject.webapp.model.City;
import com.mac2work.myfirstproject.webapp.model.User;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	@Modifying
	@Query("UPDATE users u SET u.city = :city WHERE u.username = :username")
	public void UpdateCityIdByName(@Param(value = "city") City city, @Param(value = "username") String username);
}
