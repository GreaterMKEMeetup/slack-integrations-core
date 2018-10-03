package org.gmjm.slack.core.message;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.gmjm.slack.api.message.Action;
import org.gmjm.slack.api.message.ActionBuilder;
import org.gmjm.slack.api.message.Button;
import org.gmjm.slack.api.message.Option;
import org.gmjm.slack.api.message.OptionGroup;
import org.gmjm.slack.api.message.Options;

public class ActionBuilderJsonImpl extends JsonBuilder implements ActionBuilder {

  private static class ActionMap extends LinkedHashMap<String, Object> implements Action {

    public ActionMap(Map<? extends String, ?> m) {
      super(m);
    }

    private static final JsonWriter writer = new JsonWriter();

    @Override
    public String toString() {
      return writer.toJson(this);
    }
  }

  private static class Confirm {

    public final String title;
    public final String text;
    public final String ok_text;
    public final String dismiss_text;

    Confirm(String title, String text, String ok_text, String dismiss_text) {
      this.title = title;
      this.text = text;
      this.ok_text = ok_text;
      this.dismiss_text = dismiss_text;
    }
  }

  private static class Opts extends LinkedList<Option> implements Options {

    @Override
    public List<Option> getOptions() {
      return this;
    }

  }

  private static class Opt implements Option {

    public final String text;
    public final String value;
    public final String description;

    Opt(Option o) {
      this(o.getText(), o.getValue(), o.getDescription().orElse(null));
    }

    Opt(String text, String value, String description) {
      this.text = text;
      this.value = value;
      this.description = description;
    }

    @Override
    public String getText() {
      return text;
    }

    @Override
    public String getValue() {
      return value;
    }

    @Override
    public Optional<String> getDescription() {
      return Optional.ofNullable(description);
    }
  }

  private Opts opts = new Opts();

  public ActionBuilderJsonImpl() {
    super(false);
  }

  public ActionBuilderJsonImpl(Button button) {
    super(false);
    setName(button.getName());
    setText(button.getText());
    setType(ActionType.BUTTON);
    setValue(button.getValue());

    button.getOConfirm().ifPresent(confirm -> {
      setConfirm(confirm.getTitle(), confirm.getText(), confirm.getOkText(), confirm.getDismissText());
    });
  }

  @Override
  public ActionBuilder setName(String name) {
    setField("name", name);
    return this;
  }

  @Override
  public ActionBuilder setStyle(Style style) {
    setField("style", style.name().toLowerCase());
    return this;
  }

  @Override
  public ActionBuilder setStyle(String style) {
    setField("style", style);
    return this;
  }

  @Override
  public ActionBuilder setText(String text) {
    setField("text", text);
    return this;
  }

  @Override
  public ActionBuilder setType(ActionType type) {
    setField("type", type.name().toLowerCase());
    return this;
  }

  @Override
  public ActionBuilder setValue(String value) {
    setField("value", value);
    return this;
  }

  @Override
  public ActionBuilder setConfirm(String title, String text, String okText, String dismissText) {
    setField("confirm", new Confirm(title, text, okText, dismissText));
    return this;
  }

  @Override
  public ActionBuilder addOption(Option option) {
    opts.add(option);
    return this;
  }

  @Override
  public ActionBuilder addOptions(Option... options) {
    Collections.addAll(opts, options);
    return this;
  }

  @Override
  public ActionBuilder addOptions(List<Option> options) {
    opts.addAll(options);
    return this;
  }

  @Override
  public ActionBuilder setOptions(List<Option> options) {
    opts = new Opts();
    opts.addAll(options);
    return this;
  }

  @Override
  public ActionBuilder setOptions(Options options) {
    opts = new Opts();
    opts.addAll(options.getOptions());
    return this;
  }

  @Override
  public ActionBuilder setSelectedOptions(Options options) {
    setField("selected_options", options);
    return this;
  }

  @Override
  public ActionBuilder setOptionGroups(List<OptionGroup> optionGroups) {
    setField("group_options", optionGroups);
    return this;
  }

  @Override
  public ActionBuilder setDataSource(DataSource dataSource) {
    setField("data_source", dataSource.name().toLowerCase());
    return this;
  }

  @Override
  public ActionBuilder setDataSource(String dataSource) {
    setField("data_source", dataSource);
    return this;
  }

  @Override
  public ActionBuilder setMinQueryLength(int minQueryLength) {
    setField("min_query_length", minQueryLength);
    return this;
  }

  @Override
  public ActionBuilder setAttribute(String key, Object value) {
    setField(key, value);
    return this;
  }

  @Override
  public Action build() {

    ActionMap actionMap = new ActionMap(jsonFields);

    if(opts.size() > 0) {
      actionMap.put("options", opts);
    }
    return actionMap;
  }
}
