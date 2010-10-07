/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.model.acesso;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="papel_sequence", allocationSize=1)
public class Papel extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=50)
	private String codigo;
	@Column(length=100)
	private String nome;
	@Column(length=120)
	private String url;
	@ManyToOne
	private Papel papelMae;
	private int ordem;
	private boolean menu;

	@Column(length=1)
	private String accesskey;
	@Transient
	private String idExibir;//usado para lista de checkbox

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Papel getPapelMae()
	{
		return papelMae;
	}

	public void setPapelMaeId(Long papelMaeId)
	{
		if (this.papelMae == null)
			this.papelMae = new Papel();
		
		this.papelMae.setId(papelMaeId);
	}
	public void setPapelMae(Papel papelMae)
	{
		this.papelMae = papelMae;
	}

	public int getOrdem()
	{
		return ordem;
	}

	public void setOrdem(int ordem)
	{
		this.ordem = ordem;
	}

	public boolean isMenu()
	{
		return menu;
	}

	public void setMenu(boolean menu)
	{
		this.menu = menu;
	}

	public String getIdExibir()
	{
		return idExibir;
	}

	public void setIdExibir(String idExibir)
	{
		this.idExibir = idExibir;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("nome", this.nome).append("id",
				this.getId()).append("papelMae", this.papelMae).append("url",
				this.url).append("codigo", this.codigo).append("ordem", this.ordem).toString();
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

	public String getAccesskey()
	{
		return accesskey;
	}

	public void setAccesskey(String accesskey)
	{
		this.accesskey = accesskey;
	}
}