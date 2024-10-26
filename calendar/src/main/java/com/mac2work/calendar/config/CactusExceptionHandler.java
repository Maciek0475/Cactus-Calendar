package com.mac2work.calendar.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.mac2work.cactus_library.exception.ResourceNotFoundException;

@ControllerAdvice
public class CactusExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ModelAndView handleResourceNotFoundException(ResourceNotFoundException exc) {
		ModelAndView errorPage = new ModelAndView();
		errorPage.addObject("exception", exc);
		errorPage.setViewName("error");
		return errorPage;
	}
}
