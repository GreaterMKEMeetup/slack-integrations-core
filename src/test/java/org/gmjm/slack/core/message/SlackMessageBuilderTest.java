package org.gmjm.slack.core.message;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
            .build();

        assertEquals(IOUtils.toString(this.getClass().getResourceAsStream("message.json")), message);

    }

}
