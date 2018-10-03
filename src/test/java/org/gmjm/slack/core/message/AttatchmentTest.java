package org.gmjm.slack.core.message;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.gmjm.slack.api.message.ActionBuilder.DataSource;
import org.gmjm.slack.api.message.ActionBuilder.Style;
import org.junit.Test;

public class AttatchmentTest
{

	@Test
	public void testJsonAttachment() throws IOException
	{
		AttachmentBuilderJsonImpl attachmentBuilder = new AttachmentBuilderJsonImpl();

		attachmentBuilder
				.setFallbackText("Required plain-text summary of the attachment.")
				.setColor("#2eb886")
				.setPreText("Optional text that appears above the attachment block", false)
				.setAuthorName("Bobby Tables")
				.setAuthorLink("http://flickr.com/bobby/")
				.setAuthorIcon("http://flickr.com/icons/bobby.jpg")
				.setTitle("Slack API Documentation", "https://api.slack.com/")
				.setText("Optional text that appears within the attachment")
				.setImageUrl("http://my-website.com/path/to/image.jp")
				.setThumbUrl("http://example.com/path/to/thumb.png")
				.setFooter("Slack API")
				.setFooterIcon("https://platform.slack-edge.com/img/default_application_icon.png")
				.setTimestamp(123456789)
				.addField(new FieldBuilderJsonImpl()
						.setShort(true)
						.setTitle("Test Field")
						.setValue("Field Value"))
				.addAction(new ActionBuilderJsonImpl()
						.setName("Choose a channel.")
						.setStyle(Style.PRIMARY)
						.setText("Pick from this list.")
						.setDataSource(DataSource.CHANNELS));

		assertEquals(
				IOUtils.toString(this.getClass().getResourceAsStream("attachment.json")),
				attachmentBuilder.build().toString());
	}

}
