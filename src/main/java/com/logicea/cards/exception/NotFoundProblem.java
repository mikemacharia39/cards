package com.logicea.cards.exception;

import org.zalando.problem.Status;

import java.net.URI;
import java.util.Map;

public class NotFoundProblem extends BaseProblem {
    public NotFoundProblem(String errorKey, Map<String, Object> details) {
        super(URI.create("https://cards.com/not-found"), null, Status.NOT_FOUND, errorKey, details);
    }

    public NotFoundProblem(String errorKey) {
        super(URI.create("https://cards.com/not-found"), null, Status.NOT_FOUND, errorKey, null);
    }
}