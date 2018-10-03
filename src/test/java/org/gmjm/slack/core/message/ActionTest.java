package org.gmjm.slack.core.message;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.gmjm.slack.api.message.ActionBuilder.DataSource;
import org.gmjm.slack.api.message.ActionBuilder.Style;
import org.junit.Test;

public class ActionTest {
  @Test
  public void testJsonAction() throws IOException
  {
    String actionJson = new ActionBuilderJsonImpl()
        .setName("Choose a channel.")
        .setStyle(Style.PRIMARY)
        .setText("Pick from this list.")
        .setDataSource(DataSource.CHANNELS)
        .setConfirm("confirm this", "are you sure?" , "do it", "no don't!")
        .build()
        .toString();

    assertEquals(
        IOUtils.toString(this.getClass().getResourceAsStream("action_dataSource.json")),
        actionJson);
  }
}
