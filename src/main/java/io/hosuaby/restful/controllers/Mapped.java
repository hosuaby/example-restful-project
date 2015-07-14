package io.hosuaby.restful.controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates controller method's return or parameter to be passed throw the
 * mapper.<br/>
 * If this annotation added on method's return with type of domain object than
 * returned domain object is converted to corresponding mapping.<br/>
 * If this annotation added on method's parameter with type of domain object
 * than this object is obtained by conversion from corresponding mapping.
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapped {}
