package org.gmjm.slack.core.message;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.gmjm.slack.api.message.ActionBuilder.DataSource;
import org.gmjm.slack.api.message.ActionBuilder.Style;
import org.junit.Test;

public class SlackMessageBuilderTest {

    @Test
    public void testSlackMessageToJson() throws IOException {

        String message = new SlackMessageBuilderJsonImpl()

            .setText("So many commits!")
            .setChannel("engineering")
            .setIconEmoji("smiley")
            .setResponseType("ephemeral")
            .setIconUrl("https://picsum.photos/200/300")

            .addAttachment(
                new AttachmentBuilderJsonImpl()
                    .setTitle("Update Catalan translation to e38cb41", "http://example.com/mike/")
                    .setText("Modified *18* files"))

            .addAttachment(
                new AttachmentBuilderJsonImpl()
                    .setTitle("mmk", "http://example.com/mike/")
                    .setText("Modified *13* files"))

            .addAttachment(
                new AttachmentBuilderJsonImpl()
                    .setTitle("nowerk", "http://example.com/mike/")
                    .setText("Modified *5* files")
                    .addAction(new ActionBuilderJsonImpl()
                        .setName("Choose a channel.")
                        .setStyle(Style.PRIMARY)
                        .setText("Pick from this list.")
                        .setDataSource(DataSource.CHANNELS))
                    .addField(
                        new FieldBuilderJsonImpl()
                            .setShort(true)
                            .setTitle("Example Field 1")
                            .setValue("Value 1"))
                    .addField(
                        new FieldBuilderJsonImpl()
                            .setShort(false)
                            .setTitle("Example Field 2")
                            .setValue("Value 2 extra long value")))
            .build()
            .toString();

        assertEquals(IOUtils.toString(this.getClass().getResourceAsStream("message.json")), message);

    }

}
