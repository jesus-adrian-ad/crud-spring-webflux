package asl.development.configuration;

import asl.development.domain.response.ErrorResponse;
import asl.development.exception.CustomException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webflux.autoconfigure.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer codecConfigurer){
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
        this.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes){
        return RouterFunctions.route(RequestPredicates.all(), this::handleError);
    }

    private Mono<ServerResponse> handleError(ServerRequest request) {

        Throwable error = getError(request);

        if (error instanceof CustomException ex) {
            ErrorResponse response = new ErrorResponse(
                    ex.getMessage(),
                    ex.getHttpStatus().value(),
                    Instant.now(),
                    request.path()
            );

            return ServerResponse
                    .status(ex.getHttpStatus())
                    .bodyValue(response);
        }

        error.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Instant.now(),
                request.path()
        );

        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(response);
    }
}
