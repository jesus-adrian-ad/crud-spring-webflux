package asl.development.configuration;

import asl.development.domain.response.ErrorResponse;
import asl.development.exception.CustomException;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webflux.autoconfigure.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;

@NullMarked
@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
        this.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::handleError);
    }

    private Mono<ServerResponse> handleError(ServerRequest request) {
        Throwable error = Objects.requireNonNull(getError(request));

        Instant now = Instant.now();
        String path = request.path();

        ErrorResponse response;
        HttpStatus status;

        switch (error) {

            case CustomException ex -> {
                response = new ErrorResponse(
                        ex.getMessage(),
                        ex.getHttpStatus().value(),
                        now,
                        path
                );
                status = ex.getHttpStatus();
            }

            case NoResourceFoundException ignored -> {
                response = new ErrorResponse(
                        "Resource not found, please validate again",
                        HttpStatus.NOT_FOUND.value(),
                        now,
                        path
                );
                status = HttpStatus.NOT_FOUND;
            }

            case BadSqlGrammarException ignored -> {
                response = new ErrorResponse(
                        "Bad SQL Exception, please validate if database or table exists",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        now,
                        path
                );
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            default -> {
                response = new ErrorResponse(
                        "Internal Server Error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        now,
                        path
                );
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        return ServerResponse.status(status).bodyValue(response);
    }
}