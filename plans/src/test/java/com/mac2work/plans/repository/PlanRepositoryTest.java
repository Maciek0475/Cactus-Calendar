package com.mac2work.plans.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import com.mac2work.plans.model.Plan;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PlanRepositoryTest {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private PlanRepository planRepository;
	
	private Plan plan;
	private Plan plan2;

	@BeforeEach
	void setUp() throws Exception {
		plan = Plan.builder()
				.id(1L)
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.cityId(1L)
				.isDone(false)
				.userId(1L)
				.build();
		plan2 = Plan.builder()
				.id(2L)
				.date(LocalDate.parse("2024-10-06"))
				.note("50 - 50")
				.successPropability(50.0)
				.cityId(1L)
				.isDone(false)
				.userId(1L)
				.build();
		planRepository.save(plan);		
		planRepository.save(plan2);
	}

	@Test
	void planRepository_findAllByUserId_ReturnListOfPlan() {
		List<Plan> plans = List.of(plan, plan2);
		
		List<Plan> actualPlans = planRepository.findAllByUserId(1L);
		
		assertThat(actualPlans).isEqualTo(plans);
	}

	@Test
	void planRepository_updateIsDoneById_CheckIfDoneIsUpdated() {	
		boolean newDoneValue = true;
		
		planRepository.updateIsDoneById(plan.getId(), newDoneValue);
		entityManager.clear();
		Plan actualPlan = planRepository.findById(plan.getId()).get();
		
		assertThat(actualPlan.isDone()).isEqualTo(newDoneValue);
	}

	@Test
	void planRepository_updateSuccessPropabilityById_CheckIfSuccessIsUpdated() {
		double newSuccessPropability = 85.0;
		
		planRepository.updateSuccessPropabilityById(plan.getId(), newSuccessPropability);
		entityManager.clear();
		Plan actualPlan = planRepository.findById(plan.getId()).get();
		
		assertThat(actualPlan.getSuccessPropability()).isEqualTo(newSuccessPropability);
	}

}
