package com.github.lila.aop;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * The Lila's AOP module.
 * 
 * @author Carlos A Becker
 * 
 */
public class LilaAOPModule extends AbstractModule {

	@Override
	protected void configure() {
		bindInterceptor(Matchers.any(),
				Matchers.annotatedWith(RequiresPermission.class),
				new RequiresPermissionInterceptor());
	}

}
