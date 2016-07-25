package org.gmjm.slack.core.message;


import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class AttatchmentTest
{

	@Test
	public void testJsonAttachment() throws IOException
	{

		AttachmentBuilderJsonImpl attachmentBuilder = new AttachmentBuilderJsonImpl();

		attachmentBuilder.setTitle("Update Catalan translation to e38cb41","http://example.com/mike/");
		attachmentBuilder.setText("Modified *18* files");

		assertEquals(
			IOUtils.toString(this.getClass().getResourceAsStream("attachment.json")),
			attachmentBuilder.buildJsonString());

	}

}
