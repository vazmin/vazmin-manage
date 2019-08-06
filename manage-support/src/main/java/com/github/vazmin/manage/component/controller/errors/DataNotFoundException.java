package com.github.vazmin.manage.component.controller.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class DataNotFoundException extends AbstractThrowableProblem {

    public DataNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, "Data not found", Status.NOT_FOUND);
    }
}
