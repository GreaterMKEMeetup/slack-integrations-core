package org.gmjm.slack.core.message;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest
{

	@Test
	public void testJsonField() throws IOException
	{
		String field = new FieldBuilderJsonImpl()
			.setShort(true)
			.setTitle("Test Field")
			.setValue("Field Value")
			.build();

		assertEquals(
			IOUtils.toString(this.getClass().getResourceAsStream("field.json")),
			field);
	}

}
