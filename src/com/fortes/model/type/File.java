package com.fortes.model.type;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @author Marlus Saraiva
 *
 */
@Embeddable
public class File
{
	private String name;
	private String contentType;
	private byte[] bytes;
	private Long size;
	//@Transient
	//private FileStoreType fileStoreType;
	//@Transient
	//private String location;
	@Transient
	private boolean ignored = false;

	public File()
	{
		super();
	}
/*
	public File(String name, String contentType, byte[] bytes)
	{
		super();
		this.name = name;
		this.contentType = contentType;
		this.bytes = bytes;
	}
*/
	public byte[] getBytes()
	{
		return bytes;
	}
	public void setBytes(byte[] bytes)
	{
		this.bytes = bytes;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isIgnored()
	{
		return ignored;
	}

	public void setIgnored(boolean ignored)
	{
		this.ignored = ignored;
	}

	public Long getSize()
	{
		return size;
	}

	public void setSize(Long size)
	{
		this.size = size;
	}

	/*
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public FileStoreType getFileStoreType()
	{
		return fileStoreType;
	}

	public void setFileStoreType(FileStoreType fileStoreType)
	{
		this.fileStoreType = fileStoreType;
	}
	*/

	public java.io.File getFileArchive()
	{
		return FileUtil.bytesToFile(bytes, System.getProperty("java.io.tmpdir") + "/tmpFotoCandidato" + FileUtil.getFileExtension(contentType));
	}
}