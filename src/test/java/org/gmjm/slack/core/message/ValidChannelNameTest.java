package org.gmjm.slack.core.message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidChannelNameTest
{

	@Test
	public void testCleanChannelName() {
		String badName = "D'Jayz Repository#! ok-d sto@p";
		String expected = "d-jayz-repository-ok";
		ValidChannelName cn = new ValidChannelName(badName);

		assertEquals(expected,cn.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCleanChannelName_emptyResult_IllegalArg() {
		String badName = "--";
		ValidChannelName cn = new ValidChannelName(badName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCleanChannelName_emptyResult_IllegalArg2() {
		String badName = "-";
		ValidChannelName cn = new ValidChannelName(badName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCleanChannelName_emptyResult_IllegalArg3() {
		String badName = "";
		ValidChannelName cn = new ValidChannelName(badName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCleanChannelName_emptyResult_IllegalArg4() {
		String badName = "- 3@$%*(!)@($*#@ -";
		ValidChannelName cn = new ValidChannelName(badName);
	}

}

