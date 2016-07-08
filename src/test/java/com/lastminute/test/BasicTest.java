package com.lastminute.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

public abstract class BasicTest {
	protected void assertNotEmpty(String message, Collection<?> collection) {
		assertNotNull(message, collection);
		assertTrue(message, collection.size()>0);
	}
}
