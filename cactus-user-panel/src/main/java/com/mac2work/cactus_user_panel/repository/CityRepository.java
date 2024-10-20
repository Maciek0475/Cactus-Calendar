package com.mac2work.cactus_user_panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.cactus_user_panel.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

}
