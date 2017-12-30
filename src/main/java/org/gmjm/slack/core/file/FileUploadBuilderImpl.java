package org.gmjm.slack.core.file;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.gmjm.slack.api.file.FileType;
import org.gmjm.slack.api.file.FileUploadBuilder;

class FileUploadBuilderImpl implements FileUploadBuilder {

	InputStream fileInputStream = null;

	Map<String,String> params = new HashMap<>();

	@Override
	public FileUploadBuilder setChannels(String... channels) {
		params.put("channels",String.join(",", channels));
		return this;
	}

	@Override
	public FileUploadBuilder setContent(InputStream contentInputStream) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileUploadBuilder setContent(String content) {
		params.put("content", content);
		return this;
	}

	@Override
	public FileUploadBuilder setFile(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
		return this;
	}

	@Override
	public FileUploadBuilder setFilename(String filename) {
		params.put("filename", filename);
		return this;
	}

	@Override
	public FileUploadBuilder setFiletype(FileType filetype) {
		if(filetype != null) {
			params.put("filetype", filetype.type);
		}
		return this;
	}

	@Override
	public FileUploadBuilder setFiletype(String filetype) {
		params.put("filetype", filetype);
		return this;
	}

	@Override
	public FileUploadBuilder setInitialComment(String initialComment) {
		params.put("initial_comment", initialComment);
		return this;
	}

	@Override
	public FileUploadBuilder setTitle(String title) {
		params.put("title", title);
		return this;
	}

}
