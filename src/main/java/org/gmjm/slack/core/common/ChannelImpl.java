package org.gmjm.slack.core.common;

import java.util.Objects;
import java.util.Optional;

import org.gmjm.slack.api.common.Channel;

import static java.lang.String.*;

public class ChannelImpl implements Channel {
    
    private final String name;
    private final String id;

    public static Channel withName(String name) {
        if(name == null){
            throw new IllegalArgumentException("Channel name cannot be null.");
        }
        return new ChannelImpl(name, null);
    }

    public static Channel withId(String id) {
        if(id == null){
            throw new IllegalArgumentException("Channel id cannot be null.");
        }
        return new ChannelImpl(null, id);
    }

    public static Channel with(String name, String id) {
        if(name == null && id == null){
            throw new IllegalArgumentException(
                format("Channel name and id cannot both be null. name: %s id: %s", name, id));
        }
        return new ChannelImpl(name, id);
    }
    
    private ChannelImpl(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public Optional<String> getOName() {
        return Optional.ofNullable(name);
    }

    @Override
    public Optional<String> getOId() {
        return Optional.ofNullable(id);
    }

    @Override
    public String getIdentifier() {
        return id != null ? id : name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChannelImpl channel = (ChannelImpl) o;
        return Objects.equals(name, channel.name) &&
            Objects.equals(id, channel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
