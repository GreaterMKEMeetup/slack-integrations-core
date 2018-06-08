package org.gmjm.slack.core.file;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

import org.gmjm.slack.api.common.Channel;
import org.gmjm.slack.api.file.FileType;
import org.gmjm.slack.api.file.FileUpload;
import org.gmjm.slack.api.file.FileUploadBuilder;

class FileUploadBuilderImpl implements FileUploadBuilder {

	Supplier<InputStream> inputStreamSupplier = null;

	private String fileType;
	private Supplier<InputStream> InputStreamSupplier;
	private String title;
	private String fileName;
	private String content;
	private String initialComment;
	private HashSet<Channel> channels = new HashSet<>();

	@Override
	public FileUploadBuilder setChannels(Channel... channels) {
		this.channels = new HashSet<Channel>(Arrays.asList(channels));
		return this;
	}

	@Override
	public FileUploadBuilder setContent(InputStream contentInputStream) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileUploadBuilder setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public FileUploadBuilder setInputStreamSupplier(Supplier<InputStream> inputStreamSupplier) {
		this.inputStreamSupplier = inputStreamSupplier;
		return this;
	}

	@Override
	public FileUploadBuilder setFilename(String filename) {
		this.fileName = filename;
		return this;
	}

	@Override
	public FileUploadBuilder setFiletype(FileType fileType) {
		this.fileType = fileType != null ? fileType.type : null;
		return this;
	}

	@Override
	public FileUploadBuilder setFiletype(String fileType) {
		this.fileType = fileType;
		return this;
	}

	@Override
	public FileUploadBuilder setInitialComment(String initialComment) {
		this.initialComment = initialComment;
		return this;
	}

	@Override
	public FileUploadBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public FileUpload build() {
		return new FileUploadImpl(
			title,
			fileType,
			inputStreamSupplier,
			fileName,
			content,
			initialComment,
			channels);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("FileUploadBuilderImpl{");
		sb.append("inputStreamSupplier=").append(inputStreamSupplier);
		sb.append(", fileType='").append(fileType).append('\'');
		sb.append(", InputStreamSupplier=").append(InputStreamSupplier);
		sb.append(", title='").append(title).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", initialComment='").append(initialComment).append('\'');
		sb.append(", channels=").append(channels);
		sb.append('}');
		return sb.toString();
	}
}
