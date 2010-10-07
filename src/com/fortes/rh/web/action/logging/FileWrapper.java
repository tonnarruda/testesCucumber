package com.fortes.rh.web.action.logging;

import java.io.File;
import java.util.Date;

public class FileWrapper {

	File file;
	
	public FileWrapper(File file) {
		this.file = file;
	}
	
	public String getName() {
		return file.getName();
	}
	public Long getSize() {
		return file.length();
	}
	public Date getLastModified() {
		return new Date(file.lastModified());
	}
	
}
