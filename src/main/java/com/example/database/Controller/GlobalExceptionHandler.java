package com.example.database.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, RedirectAttributes redirectAttributes) {
        String parameterName = ex.getParameter().getParameterName();
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";
        
        logger.warn("Parameter type mismatch for parameter '{}' with value '{}'", parameterName, value);
        
        if ("accountNumber".equals(parameterName)) {
            // Check if this is a favicon request
            if ("favicon.ico".equals(value)) {
                // Silently ignore favicon requests
                return "redirect:/";
            }
            
            redirectAttributes.addFlashAttribute("error", 
                "Invalid account number format. Please enter a numeric value.");
            return "redirect:/";
        }
        
        redirectAttributes.addFlashAttribute("error", "Invalid parameter format");
        return "redirect:/";
    }
    
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, RedirectAttributes redirectAttributes) {
        logger.error("An unexpected error occurred", ex);
        redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        return "redirect:/";
    }
}