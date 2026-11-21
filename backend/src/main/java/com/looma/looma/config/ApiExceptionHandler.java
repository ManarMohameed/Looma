package com.looma.looma.config;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Map<String, Object>> handleStatus(ResponseStatusException ex) {
	        return build(ex.getStatusCode(), ex.getReason());
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
	        String message = ex.getBindingResult().getFieldErrors().stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.joining(", "));
	        return build(HttpStatus.BAD_REQUEST, message);
	    }

	    @ExceptionHandler(MissingServletRequestParameterException.class)
	    public ResponseEntity<Map<String, Object>> handleMissingParam(MissingServletRequestParameterException ex) {
	        return build(HttpStatus.BAD_REQUEST, "Missing parameter: " + ex.getParameterName());
	    }

	    @ExceptionHandler(MissingServletRequestPartException.class)
	    public ResponseEntity<Map<String, Object>> handleMissingPart(MissingServletRequestPartException ex) {
	        return build(HttpStatus.BAD_REQUEST, "Missing file field: " + ex.getRequestPartName());
	    }

	    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
	        return build(HttpStatus.BAD_REQUEST, "Invalid value for " + ex.getName());
	    }

	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
	        return build(HttpStatus.BAD_REQUEST, "Invalid or missing request body");
	    }

	    @ExceptionHandler(MaxUploadSizeExceededException.class)
	    public ResponseEntity<Map<String, Object>> handleTooLarge(MaxUploadSizeExceededException ex) {
	        return build(HttpStatus.PAYLOAD_TOO_LARGE, "Image is too large (max 5MB)");
	    }

	    private ResponseEntity<Map<String, Object>> build(HttpStatusCode status, String message) {
	        HttpStatus resolved = HttpStatus.resolve(status.value());
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("status", status.value());
	        body.put("error", resolved != null ? resolved.getReasonPhrase() : "Error");
	        body.put("message", message);
	        return ResponseEntity.status(status).body(body);
	    }
	}

