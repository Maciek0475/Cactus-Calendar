package com.mac2work.myfirstproject.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mac2work.myfirstproject.webapp.model.Plan;

public interface PlanRepository extends CrudRepository<Plan, Long>{
	
	@Query("SELECT u FROM plans u WHERE u.isDone = '0'")
	public List<Plan> findAllUndone();
	
	@Query("SELECT u FROM plans u WHERE u.isDone = '1' ")
	public List<Plan> findAllDone();
	
	@Modifying
	@Query("UPDATE plans p SET p.isDone = :isDone WHERE p.id = :id ")
	public void updateIsDoneById(@Param(value = "id") Long id, @Param(value = "isDone") boolean isDone);

	@Modifying
	@Query("UPDATE plans u SET u.successPropability = :successPropability WHERE id = :id ")
	public void updateSuccessPropabilityById(@Param(value= "id") Long id, @Param(value="successPropability") double successPropability);

}
