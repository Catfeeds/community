package com.tongwii.core.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

/**
 * Simple exception with a message, that returns an Internal Server Error code.
 */
public class InternalServerErrorException extends AbstractThrowableProblem {

    public InternalServerErrorException(String message) {
        super(Problem.DEFAULT_TYPE, message, Status.INTERNAL_SERVER_ERROR);
    }
}
