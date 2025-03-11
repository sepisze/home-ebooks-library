package com.demo.sample.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.sample.domain.Book;
import com.demo.sample.exceptions.RuntimeExceptionRest;
import com.demo.sample.services.BooksLibraryService;
import com.demo.sample.utils.JsonUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;

/*
 * http://127.0.0.1:8080/swagger-ui/index.html
 * http://127.0.0.1:8080/h2-console
 */

@RestController
public class BooksController {

	private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

	@Autowired
	private JsonUtils jsonUtils;

	@Autowired
	private BooksLibraryService booksLibraryService;

	/*
	@Operation(summary = "Get book information",
		description = "",
		parameters = {
			@Parameter(in = ParameterIn.HEADER, name = "X-Request-ID", description = "Request ID", required = false, schema = @Schema(implementation = String.class)),
			@Parameter(in = ParameterIn.QUERY, name = "isbn", description = "Book ISBN", required = true, schema = @Schema(implementation = String.class))
		},
		responses = {
			@ApiResponse(responseCode = "200", description = "Book information", content = @Content(schema = @Schema(implementation = String.class)))
		}
	)
	@PostMapping(value = "/get-book-info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BookInfo> getBookInfo(@RequestBody String request, HttpServletRequest httpServletRequest) {
		long ts = System.currentTimeMillis();
		String requestId = httpServletRequest.getHeader("X-Request-ID");
		String instanceId = UUID.randomUUID().toString();

		logger.debug("BooksController.getBookInfo(" + requestId + "): start\n"
			+ "Request:\n"
			+ "    InstanceId: " + instanceId + "\n"
			+ "    RequestID:   " + requestId + "\n"
			+ "    URI:         " + httpServletRequest.getMethod() + " " +httpServletRequest.getRequestURI() + "\n"
			+ "    Body:        " + jsonUtils.stringify(request));

		BookInfo response = new BookInfo();
		response.title = "Sample book";
	
		logger.debug("BooksController.getBookInfo(" + requestId + "): end time=[" + (System.currentTimeMillis() - ts) + "ms]\n"
			+ "Response:\n"
			+ "    InstanceId: " + instanceId + "\n"
			+ "    RequestID:   " + requestId + "\n"
			+ "    Body:        " + jsonUtils.stringify(response));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	*/

	@Operation(
		summary = "Get book information",
		description = "",
		parameters = {
			@Parameter(in = ParameterIn.HEADER, name = "X-Request-ID", description = "Request ID", required = false, schema = @Schema(implementation = String.class)),
			@Parameter(in = ParameterIn.QUERY, name = "uid", description = "Book UID", required = true, schema = @Schema(implementation = String.class))
		},
		responses = {
			@ApiResponse(responseCode = "200", description = "Book information", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
		}
	)
	@GetMapping(value = "/get-book-info", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Book> getBookInfoTest(@RequestParam(name = "uid") String bookUid, HttpServletRequest httpServletRequest) {
		long ts = System.currentTimeMillis();
		String requestId = httpServletRequest.getHeader("X-Request-ID");
		String instanceId = UUID.randomUUID().toString();

		logger.debug("BooksController.getBookInfoTest(" + requestId + "): start\n"
			+ "Request:\n"
			+ "    InstanceId: " + instanceId + "\n"
			+ "    RequestID:  " + requestId + "\n"
			+ "    URI:        " + httpServletRequest.getMethod() + " " +httpServletRequest.getRequestURI() + "\n"
			+ "    Body:       " + "uid=" + bookUid);

		Book response = null;
		try {
			response = booksLibraryService.getBookByUid(instanceId, bookUid);
		}
		catch (RuntimeException e) {
			throw new RuntimeExceptionRest(e, instanceId, requestId);
		}

		logger.debug("BooksController.getBookInfo(" + requestId + "): end time=[" + (System.currentTimeMillis() - ts) + "ms]\n"
			+ "Response:\n"
			+ "    InstanceId: " + instanceId + "\n"
			+ "    RequestID:  " + requestId + "\n"
			+ "    Body:       " + jsonUtils.stringify(response));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ExceptionHandler({
		RuntimeException.class,
		RuntimeExceptionRest.class })
	public ResponseEntity<HttpErrorResponse> handleErrorRuntimeException(RuntimeException e) {
		logger.debug("BooksController.handleErrorRuntimeException()");

		String instanceId = (e instanceof RuntimeExceptionRest) ? ((RuntimeExceptionRest)e).getInstanceId() : null;
		String requestId  = (e instanceof RuntimeExceptionRest) ? ((RuntimeExceptionRest)e).getRequestId() : null;

		// https://datatracker.ietf.org/doc/html/rfc9457

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		HttpErrorResponse errorResponse = new HttpErrorResponse();
		errorResponse.type = "about:blank"; // TODO: tu powinien być adres URI wyjaśniający ten rodzaj błędu
		errorResponse.status = httpStatus.value();
		errorResponse.title = "Internal server error";
		errorResponse.detail = "Internal server error";
		errorResponse.instance = null; // TODO: tu powinien być adres URI wyjaśniający to konkrente wystąpienie błędu (na podstawie instanceId)

		logger.debug("BooksController.handleErrorRuntimeException(): end\n"
			+ "Response:\n"
			+ "    InstanceId: " + instanceId + "\n"
			+ "    RequestID:  " + requestId + "\n"
			+ "    Body:       " + jsonUtils.stringify(errorResponse));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		return new ResponseEntity<HttpErrorResponse>(errorResponse, httpHeaders, httpStatus);
	}

}
