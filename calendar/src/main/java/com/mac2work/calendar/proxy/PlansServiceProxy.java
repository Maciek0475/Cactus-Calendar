package com.mac2work.calendar.proxy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mac2work.calendar.response.PlanResponse;

@FeignClient(name="PLANS-SERVICE")
public interface PlansServiceProxy {
	@DeleteMapping("/api/plans/{id}")
	void deletePlan(Long id);

	@GetMapping("/api/plans/{status}")
	List<PlanResponse> getPlansByDoneStatus(boolean doneStatus);

	@PostMapping("/api/plans/{date}/{note}/{successPropability}")
	PlanResponse saveNewPlan(LocalDate date, String note, Double successPropability);

}
