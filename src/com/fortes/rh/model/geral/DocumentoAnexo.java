package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.EtapaSeletiva;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="documentoanexo_sequence", allocationSize=1)
public class DocumentoAnexo extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String descricao;
    @ManyToOne
	private EtapaSeletiva etapaSeletiva;
    @Temporal(TemporalType.DATE)
    private Date data;
    @ManyToOne
    private TipoDocumento tipoDocumento;
	@Lob
	private String observacao;
	@Column(length=120)
	private String url;
	//Dicionario OrigemAnexo
	private char origem;
	private Long origemId;
	
	public void setProjectionEtapaSeletivaId(Long etapaSeletivaId)
	{
		if (this.etapaSeletiva == null)
			this.etapaSeletiva = new EtapaSeletiva();
		
		this.etapaSeletiva.setId(etapaSeletivaId);
	}

   public void setProjectionEtapaSeletivaNome(String etapaSeletivaNome)
   {
	   if (this.etapaSeletiva == null)
		   this.etapaSeletiva = new EtapaSeletiva();

	   this.etapaSeletiva.setNome(etapaSeletivaNome);
   }

   public void setProjectionTipoDocumentoDescricao(String tipoDocumentoDescricao)
   {
	   if (this.tipoDocumento == null)
		   this.tipoDocumento = new TipoDocumento();
	   if (tipoDocumentoDescricao == null) 
		   tipoDocumentoDescricao = "";
	   
	   this.tipoDocumento.setDescricao(tipoDocumentoDescricao);
   }
   
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public EtapaSeletiva getEtapaSeletiva()
	{
		return etapaSeletiva;
	}
	public void setEtapaSeletiva(EtapaSeletiva etapaSeletiva)
	{
		this.etapaSeletiva = etapaSeletiva;
	}
	public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}
	public char getOrigem()
	{
		return origem;
	}
	public void setOrigem(char origem)
	{
		this.origem = origem;
	}
	public Long getOrigemId()
	{
		return origemId;
	}
	public void setOrigemId(Long origemId)
	{
		this.origemId = origemId;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId())
		.append("descricao", this.descricao)
		.append("url", this.url).toString();
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}