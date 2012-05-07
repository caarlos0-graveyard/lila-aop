package com.github.lila.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.lila.HasPermission;

/**
 * Define a method that the {@link HasPermission} argument has to have the
 * permission.
 * 
 * @author Carlos A Becker
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {

	/**
	 * Permission that the {@link HasPermission} needs to have.
	 * 
	 * @return
	 */
	int permission();

}
