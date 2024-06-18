package com.mac2work.plans.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mac2work.plans.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	public List<Plan> findAllByUserId(Long userId);

	@Modifying
	@Query("UPDATE plans p SET p.isDone = :isDone WHERE p.id = :id ")
	public Plan updateIsDoneById(@Param(value = "id") Long id, @Param(value = "isDone") boolean isDone);

	@Modifying
	@Query("UPDATE plans u SET u.successPropability = :successPropability WHERE id = :id ")
	public Plan updateSuccessPropabilityById(@Param(value= "id") Long id, @Param(value="successPropability") double successPropability);
}
