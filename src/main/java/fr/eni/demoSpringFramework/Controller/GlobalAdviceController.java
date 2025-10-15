package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Response.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice()
public class GlobalAdviceController {

    /**
     * Handle Not Resource Found
     *
     * @return The Response.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Payload.create(null, "Page Not Found", HttpStatus.NOT_FOUND));
    }

}
