package com.tongwii.core.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class InvalidPasswordException extends AbstractThrowableProblem {

    public InvalidPasswordException() {
        super(Problem.DEFAULT_TYPE, "Incorrect password", Status.BAD_REQUEST);
    }
}
