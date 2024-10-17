package com.mac2work.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.proxy.UserPanelProxy;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {
	@Mock
	private UserPanelProxy userPanelProxy;
	
	@InjectMocks
	private ContentService contentService;

	private UserResponse userResponse;
	private PlanResponse planResponse;
	
	@BeforeEach
	void setUp() throws Exception {
		planResponse = PlanResponse.builder()
				.id(1L)
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.isDone(false)
				.userId(1L)
				.build();
		List<PlanResponse> planResponses = List.of(planResponse);
		userResponse = UserResponse.builder()
				.username("mac2work@gmail.com")
				.planResponses(planResponses)
				.build();
	}

	@Test
	void contentService_getPlansCount_CheckIfModelIsCorrectAndReturnHtmlFileName() {
		when(userPanelProxy.getAccountInfo()).thenReturn(userResponse);
		Model model = new ExtendedModelMap();
		
		String response = contentService.getPlansCount(model, false);
		
		assertThat(response).isNotEmpty();
		userResponse.setUsername("mac2work");
		assertThat(model.getAttribute("user")).isEqualTo(userResponse);
		assertThat(model.getAttribute("undonePlansCount")).isEqualTo(1L);
	}

}
