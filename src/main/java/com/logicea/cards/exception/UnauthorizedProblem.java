package com.logicea.cards.exception;

import org.zalando.problem.Status;

import java.net.URI;
import java.util.Map;

public class UnauthorizedProblem extends BaseProblem {
    public UnauthorizedProblem(String errorKey, Map<String, Object> details) {
        super(URI.create("https://cards.com/not-found"), null, Status.UNAUTHORIZED, errorKey, details);
    }

    public UnauthorizedProblem(String errorKey) {
        super(URI.create("https://cards.com/not-found"), null, Status.UNAUTHORIZED, errorKey, null);
    }
}