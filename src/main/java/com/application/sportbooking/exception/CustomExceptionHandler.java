package com.application.sportbooking.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof EntityNotFoundException) {
            return createError(ErrorType.NOT_FOUND, ex.getMessage(), env);
        } else if (ex instanceof RegistrationException) {
            return createError(ErrorType.BAD_REQUEST, ex.getMessage(), env);
        } else if (ex instanceof UnauthorizedException) {
            return createError(ErrorType.UNAUTHORIZED, ex.getMessage(), env);
        } else if (ex instanceof RequestTokenException) {
            return createError(ErrorType.BAD_REQUEST, ex.getMessage(), env);
        } else {
            return createError(ErrorType.INTERNAL_ERROR,
                    "An unexpected error occurred", env);
        }
    }

    private GraphQLError createError(ErrorType errorType, String message,
                                     DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
