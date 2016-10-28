package com.fortes.validation.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * not empty constraint
 *
 * @author Marlus Saraiva
 */
@Documented
//@ValidatorClass(NotEmptyValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface NotEmpty
{
	String message() default "{validator.notEmpty}";
}
