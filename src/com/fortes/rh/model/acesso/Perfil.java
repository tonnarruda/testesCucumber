package com.fortes.rh.model.acesso;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="perfil_sequence", allocationSize=1)
public class Perfil extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=100)
	private String nome;
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=Papel.class)
	@OrderBy("ordem")
	private Collection<Papel> papeis;
	private boolean acessoRestrito;
	@Transient
	private String arvorePapeis;
	
	public Perfil() {
	}
	
	public Perfil(Long id, String nome) {
		setId(id);
		this.nome = nome;
	}


	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<Papel> getPapeis()
	{
		return papeis;
	}

	public void setPapeis(Collection<Papel> papeis)
	{
		this.papeis = papeis;
	}

	@Override
	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this);
		string.append("id",	this.getId());
		string.append("nome", this.nome);

		return string.toString();
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public String getArvorePapeis() {
		return arvorePapeis;
	}

	public void setArvorePapeis(String arvorePapeis) {
		this.arvorePapeis = arvorePapeis;
	}

	public boolean getAcessoRestrito()
	{
		return acessoRestrito;
	}

	public void setAcessoRestrito(boolean acessoRestrito)
	{
		this.acessoRestrito = acessoRestrito;
	}
}