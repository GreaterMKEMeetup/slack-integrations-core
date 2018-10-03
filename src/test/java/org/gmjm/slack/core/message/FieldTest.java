package org.gmjm.slack.core.message;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FieldTest
{

	@Test
	public void testJsonField() throws IOException
	{
		String field = new FieldBuilderJsonImpl()
			.setShort(true)
			.setTitle("Test Field")
			.setValue("Field Value")
			.build()
			.toString();

		assertEquals(
			IOUtils.toString(this.getClass().getResourceAsStream("field.json")),
			field);
	}

}
