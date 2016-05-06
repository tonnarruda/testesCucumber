package com.fortes.rh.model.ws;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "token_sequence", allocationSize = 1)
public class Token extends AbstractModel implements Serializable
{
	private String hash;
	
	public Token()
	{
	}
	
	public Token(String hash)
	{
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}