package com.github.kkrull.jbehave.steps;

import java.lang.annotation.*;

/**
 * A class containing JBehave Given/When/Then steps that should be included in JBehave's configuration when running
 * stories.
 *
 * Note: The class must have a public, no-arg constructor.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Steps { /* empty */ }
