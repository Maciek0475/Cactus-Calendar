package com.mac2work.myfirstproject.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mac2work.myfirstproject.webapp.model.City;

public interface CityRepository extends JpaRepository<City, Long>{

}
