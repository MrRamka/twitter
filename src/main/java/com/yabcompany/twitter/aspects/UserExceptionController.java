package com.yabcompany.twitter.aspects;

import com.yabcompany.twitter.exception.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * Advice to catch Exceptions
 *
 * @see ConstraintViolationException
 * @see EntityNotFoundException
 * @see UsernameNotFoundException
 */
@ControllerAdvice
class ExceptionHandlerController {
    /**
     * Handle ConstraintViolationException
     *
     * @param exception
     * @return Response with message
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> exception(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getCause().getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handle Not Found Exception
     *
     * @param request
     * @param exception
     * @return Error page
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundException(HttpServletRequest request, Exception exception) {
        ModelAndView model = new ModelAndView();
        model.addObject("exception", exception.getMessage());
        model.addObject("url", request.getRequestURI());
        model.setViewName("errors/404");
        return model;
    }

    /**
     * Handle UsernameNotFoundException
     *
     * @param request
     * @param exception
     * @return ErrorPage
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView usernameNotFoundException(HttpServletRequest request, Exception exception) {
        ModelAndView model = new ModelAndView();
        model.addObject("exception", exception.getMessage());
        model.addObject("url", request.getRequestURI());
        model.setViewName("errors/404");
        return model;
    }


    //@ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView notFound2(HttpServletRequest req, Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("errors/default");
        return mav;
    }
}
