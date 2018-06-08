package org.gmjm.slack.core.file;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.gmjm.slack.api.common.Channel;
import org.gmjm.slack.api.file.FileUpload;

public class FileUploadImpl implements FileUpload {

    private final String title;
    private final String fileType;
    private final Supplier<InputStream> inputStreamSupplier;
    private final String fileName;
    private final String content;
    private final String initialComment;
    private final HashSet<Channel> channels;

    FileUploadImpl(
        String title,
        String fileType,
        Supplier<InputStream> inputStreamSupplier,
        String fileName,
        String content,
        String initialComment,
        Collection<Channel> channels
    ) {
        this.title = title;
        this.fileType = fileType;
        this.inputStreamSupplier = inputStreamSupplier;
        this.fileName = fileName;
        this.content = content;
        this.initialComment = initialComment;
        this.channels = new HashSet<>(channels);
    }

    @Override
    public Optional<String> getOTitle() {
        return Optional.ofNullable(title);
    }

    @Override
    public Optional<String> getOFileType() {
        return Optional.ofNullable(fileType);
    }

    @Override
    public Optional<String> getOFileName() {
        return Optional.ofNullable(fileName);
    }

    @Override
    public Optional<Supplier<InputStream>> getOInputStreamSupplier() {
        return Optional.ofNullable(inputStreamSupplier);
    }

    @Override
    public Optional<String> getOContent() {
        return Optional.ofNullable(content);
    }

    @Override
    public Optional<String> getOInitialComment() {
        return Optional.ofNullable(initialComment);
    }

    @Override
    public Set<Channel> getChannels() {
        return (HashSet<Channel>)channels.clone();
    }
}
