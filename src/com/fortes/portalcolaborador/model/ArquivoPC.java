package com.fortes.portalcolaborador.model;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;

import com.fortes.rh.model.geral.Colaborador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ArquivoPC extends AbstractAdapterPC 
{
	private String bytes; 
	private String name; 
	@SerializedName("content_type")
	private String contentType; 
	private Long size;
	private String checksum;

	public ArquivoPC() {

	}

	public ArquivoPC(Colaborador colaborador) 
	{
		this.bytes 			= DatatypeConverter.printBase64Binary(colaborador.getFoto().getBytes());
		this.name 			= colaborador.getFoto().getName();
		this.contentType 	= colaborador.getFoto().getContentType();
		this.size 			= colaborador.getFoto().getSize();
		this.checksum 		= DigestUtils.md5Hex(colaborador.getFoto().getBytes());
	}
	
	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("arquivo", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}
