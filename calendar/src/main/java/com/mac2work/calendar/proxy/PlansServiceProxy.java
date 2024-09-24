package com.mac2work.calendar.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.PlanResponse;

@FeignClient(name="PLANS", configuration = {PlansServiceFeignConfig.class})
public interface PlansServiceProxy {
	@DeleteMapping("/api/plans/{id}")
	void deletePlan(Long id);

	@GetMapping("/api/plans/{doneStatus}")
	List<PlanResponse> getPlansByDoneStatus(@PathVariable boolean doneStatus);

	@PostMapping("/api/plans")
	PlanResponse saveNewPlan(@RequestBody PlanRequest planRequest);

}
