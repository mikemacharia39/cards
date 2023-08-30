package com.logicea.cards.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.Map;

abstract class BaseProblem extends AbstractThrowableProblem {

    BaseProblem(URI uri, String title, Status status, String errorKey, Map<String, Object> details){
    }
}

