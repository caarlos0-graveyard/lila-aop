package com.github.lila.aop;

import org.junit.Test;

import com.github.lila.HasPermission;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class AppTest {

	public static interface Permission {
		public static final int READ = 1;
		public static final int WRITE = 2;
		public static final int EXECUTE = 4;
		public static final int ANOTHER = 8;
	}

	public static class User implements HasPermission {

		private int permission;

		public int getPermission() {
			return permission;
		}

		public void setPermission(int permission) {
			this.permission = permission;
		}

	}

	public static class PermTest {

		@RequiresPermission(permission = Permission.WRITE)
		public void needsWrite(User u) {
			System.out.println("I write something :)");
		}

		@RequiresPermission(permission = Permission.WRITE)
		public void needsWrite() {
			System.out.println("I write something :)");
		}

	}

	private Injector i = Guice.createInjector(new LilaAOPModule(),
			new AbstractModule() {
				@Override
				protected void configure() {
					bind(PermTest.class);
				}
			});

	private PermTest pt = i.getInstance(PermTest.class);

	@Test(expected = InsufientPermissionException.class)
	public void testNoPermission() {
		User u = new User();
		u.setPermission(Permission.EXECUTE | Permission.READ);

		pt.needsWrite(u);
	}

	@Test
	public void testPermissionOK() {
		User u = new User();
		u.setPermission(Permission.WRITE);
		pt.needsWrite(u);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNoUserParam() {

		pt.needsWrite();

	}
}
