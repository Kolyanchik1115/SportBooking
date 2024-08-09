package com.application.sportbooking.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof EntityNotFoundException) {
            return createError(ErrorType.NOT_FOUND, List.of(ex.getMessage()), env);
        } else if (ex instanceof RegistrationException) {
            return createError(ErrorType.BAD_REQUEST,List.of(ex.getMessage()), env);
        } else if (ex instanceof UnauthorizedException) {
            return createError(ErrorType.UNAUTHORIZED, List.of(ex.getMessage()), env);
        } else if (ex instanceof RequestTokenException) {
            return createError(ErrorType.BAD_REQUEST, List.of(ex.getMessage()), env);
        } else if (ex instanceof ConstraintViolationException) {
            List<String> messages = ((ConstraintViolationException) ex)
                    .getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            return createError(ErrorType.BAD_REQUEST, messages, env);
        } else {
            return createError(ErrorType.INTERNAL_ERROR,
                    List.of("An unexpected error occurred"), env);
        }
    }

    private GraphQLError createError(ErrorType errorType, List<String> messages,
                                     DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message("Failure")
                .extensions(Map.of("errors", messages))
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }
}
