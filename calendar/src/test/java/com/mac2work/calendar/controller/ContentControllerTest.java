package com.mac2work.calendar.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mac2work.cactus_library.exception.ResourceNotFoundException;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.service.ContentService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ContentController.class)
class ContentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContentService contentService;

	private UserResponse userResponse;
	private PlanResponse planResponse;
	private ResourceNotFoundException exception;
	private Long invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		invalidId = -1L;
		exception = new ResourceNotFoundException("user", "id", invalidId);
		
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
	void contentController_getContentPage_CheckModelAttributesAndReturnFileName() throws Exception {
		Map<String, Object> attrs = Map.of("user", userResponse, "undonePlansCount", 1L);
		when(contentService.getPlansCount(Mockito.any(), Mockito.anyBoolean())).thenReturn("content.html");
		
		ResultActions result = mockMvc.perform(get("/calendar/content").flashAttrs(attrs)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("content.html"))
			.andExpect(model().attribute("user", userResponse))
			.andExpect(model().attribute("undonePlansCount",1L));
		}
	@Test
	void contentController_getContentPage_ReturnExceptionView() throws Exception {
		when(contentService.getPlansCount(Mockito.any(), Mockito.anyBoolean())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(get("/calendar/content")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));
		}

	@Test
	void contentController_getGuestPage_ReturnFileName() throws Exception {
		ResultActions result = mockMvc.perform(get("/calendar/content/guest")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("guest-content.html"));
		}

}
