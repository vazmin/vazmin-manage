package com.github.vazmin.manage.support.util;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.Map;

public final class DefaultProblem extends AbstractThrowableProblem {

    // TODO needed for jackson
    DefaultProblem(@Nullable final URI type,
                   @Nullable final String title,
                   @Nullable final StatusType status,
                   @Nullable final String detail,
                   @Nullable final URI instance,
                   @Nullable final ThrowableProblem cause) {
        super(type, title, status, detail, instance, cause);
    }

    DefaultProblem(@Nullable final URI type,
                   @Nullable final String title,
                   @Nullable final StatusType status,
                   @Nullable final String detail,
                   @Nullable final URI instance,
                   @Nullable final ThrowableProblem cause,
                   @Nullable final Map<String, Object> parameters) {
        super(type, title, status, detail, instance, cause, parameters);
    }
}
