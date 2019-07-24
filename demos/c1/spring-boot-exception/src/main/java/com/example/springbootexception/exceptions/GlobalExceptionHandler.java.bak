package com.example.springbootexception.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value={java.lang.ArithmeticException.class})
    public ModelAndView handlerArithmeticException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.toString());
        modelAndView.setViewName("mathError");
        return modelAndView;
    }

    @ExceptionHandler(value={java.lang.NullPointerException.class})
    public ModelAndView handlerNullPointerException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.toString());
        modelAndView.setViewName("nullPointerError");
        return modelAndView;
    }
}
