package com.github.vazmin.manage.support.util;

import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.*;

public final class ProblemBuilder {

    private static final Set<String> RESERVED_PROPERTIES = new HashSet<>(Arrays.asList(
            "type", "title", "status", "detail", "instance", "cause"
    ));

    private URI type;
    private String title;
    private StatusType status;
    private String detail;
    private URI instance;
    private ThrowableProblem cause;
    private final Map<String, Object> parameters = new LinkedHashMap<>();

    /**
     * @see Problem#builder()
     */
    ProblemBuilder() {

    }

    public ProblemBuilder withType(@Nullable final URI type) {
        this.type = type;
        return this;
    }

    public ProblemBuilder withTitle(@Nullable final String title) {
        this.title = title;
        return this;
    }

    public ProblemBuilder withStatus(@Nullable final StatusType status) {
        this.status = status;
        return this;
    }

    public ProblemBuilder withDetail(@Nullable final String detail) {
        this.detail = detail;
        return this;
    }

    public ProblemBuilder withInstance(@Nullable final URI instance) {
        this.instance = instance;
        return this;
    }

    public ProblemBuilder withCause(@Nullable final ThrowableProblem cause) {
        this.cause = cause;
        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     * @throws IllegalArgumentException if key is any of type, title, status, detail or instance
     */
    public ProblemBuilder with(final String key, final Object value) throws IllegalArgumentException {
        if (RESERVED_PROPERTIES.contains(key)) {
            throw new IllegalArgumentException("Property " + key + " is reserved");
        }
        parameters.put(key, value);
        return this;
    }

    public ThrowableProblem build() {
        return new DefaultProblem(type, title, status, detail, instance, cause, new LinkedHashMap<>(parameters));
    }

}
