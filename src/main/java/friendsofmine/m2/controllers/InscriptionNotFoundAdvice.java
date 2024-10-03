package friendsofmine.m2.controllers;
import friendsofmine.m2.exceptions.InscriptionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InscriptionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InscriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String inscriptionNotFoundHandler(InscriptionNotFoundException e) {
        return e.getMessage();
    }
}