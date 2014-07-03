package com.fortes.rh.model.portalcolaborador;

import javax.xml.bind.DatatypeConverter;

import com.fortes.rh.model.geral.Colaborador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ArquivoPC {

	private String bytes; 
	@SerializedName("arquivo_content_type")
	private String contentType; 
	@SerializedName("arquivo_file_size")
	private Long size; 

	public ArquivoPC() {

	}

	public ArquivoPC(Colaborador colaborador) 
	{
		this.bytes 			= DatatypeConverter.printBase64Binary(colaborador.getFoto().getBytes());
		this.contentType 	= colaborador.getFoto().getContentType();
		this.size 			= colaborador.getFoto().getSize();
	}
	
	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
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
	
	public String toString()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("arquivo", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}
