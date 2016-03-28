package org.gmjm.slack.core.message;


import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.testng.Assert.assertEquals;


public class AttatchmentTest
{

	@Test
	public void testJsonAttachment() throws JsonProcessingException
	{

		AttachmentBuilderJsonImpl attachmentBuilder = new AttachmentBuilderJsonImpl();

		attachmentBuilder.setTitle("Update Catalan translation to e38cb41","http://example.com/mike/");
		attachmentBuilder.setText("Modified *18* files");

		assertEquals(
			"{\"mrkdwn_in\":[\"text\",\"title\"],\"title_link\":\"http://example.com/mike/\",\"text\":\"Modified *18* files\",\"title\":\"Update Catalan translation to e38cb41\"}",
			attachmentBuilder.buildJsonString());
	}

}
