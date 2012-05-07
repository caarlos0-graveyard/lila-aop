package com.github.lila.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.github.lila.HasPermission;
import com.github.lila.LilaPermissionAdapter;

/**
 * A interceptor for {@link RequiresPermission} aspect.
 * 
 * @author Carlos A Becker
 * 
 */
public final class RequiresPermissionInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		int permission = invocation.getMethod()
				.getAnnotation(RequiresPermission.class).permission();

		HasPermission obj = null;
		for (Object arg : invocation.getArguments()) {
			if (arg instanceof HasPermission) {
				obj = (HasPermission) arg;
				break;
			}
		}

		if (obj == null) {
			throw new IllegalArgumentException(
					"The method you're trying to validate haven't any 'HasPermission' object in arguments.");
		}

		if (!new LilaPermissionAdapter(obj).hasPermission(permission)) {
			throw new InsufientPermissionException();
		}

		return invocation.proceed();
	}
}
