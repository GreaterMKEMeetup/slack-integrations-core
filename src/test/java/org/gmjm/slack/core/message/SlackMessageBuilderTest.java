package org.gmjm.slack.core.message;


import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SlackMessageBuilderTest
{


	@Test
	public void testSlackMessageToJson() throws IOException
	{


		SlackMessageBuilderJsonImpl sm = new SlackMessageBuilderJsonImpl();

		sm.setText("So many commits!");

		{
			AttachmentBuilderJsonImpl attachmentBuilder1 = new AttachmentBuilderJsonImpl();
			attachmentBuilder1.setTitle("Update Catalan translation to e38cb41", "http://example.com/mike/");
			attachmentBuilder1.setText("Modified *18* files");
			sm.addAttachment(attachmentBuilder1);
		}

		{
			AttachmentBuilderJsonImpl attachmentBuilder1 = new AttachmentBuilderJsonImpl();
			attachmentBuilder1.setTitle("mmk", "http://example.com/mike/");
			attachmentBuilder1.setText("Modified *13* files");
			sm.addAttachment(attachmentBuilder1);
		}

		{
			AttachmentBuilderJsonImpl attachmentBuilder1 = new AttachmentBuilderJsonImpl();
			attachmentBuilder1.setTitle("nowerk", "http://example.com/mike/");
			attachmentBuilder1.setText("Modified *5* files");
			sm.addAttachment(attachmentBuilder1);
		}

		assertEquals(IOUtils.toString(this.getClass().getResourceAsStream("message.json")),sm.buildJsonString());

	}

}
