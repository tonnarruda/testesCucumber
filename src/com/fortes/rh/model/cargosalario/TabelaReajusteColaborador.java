package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="tabelareajustecolaborador_sequence", allocationSize=1)
public class TabelaReajusteColaborador extends AbstractModel implements Serializable
{
	//TODO CUIDADO ao alterar os atributos desse classe verificar o update na action(foi feito na mão por causa do EAGER)
	
    @OneToMany(mappedBy="tabelaReajusteColaborador")
    private Collection<ReajusteColaborador> reajusteColaboradors;
    @Column(length=100)
    private String nome;
    @Temporal(TemporalType.DATE)
    private Date data;
    
    private String observacao;
    private boolean aprovada;
    @ManyToOne
    private Empresa empresa;

    private Character tipoReajuste;
    private boolean dissidio;

    //TODO CUIDADO ao alterar os atributos desse classe verificar o update na action(foi feito na mão por causa do EAGER)
    
    @Transient
    private boolean ehUltimo;

    public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public Collection<ReajusteColaborador> getReajusteColaboradors()
	{
		return reajusteColaboradors;
	}
	public void setReajusteColaboradors(
			Collection<ReajusteColaborador> reajusteColaboradors)
	{
		this.reajusteColaboradors = reajusteColaboradors;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id", this.getId()).append("nome", this.nome).append("data", this.data).toString();
	}
	public boolean isAprovada()
	{
		return aprovada;
	}
	public void setAprovada(boolean aprovada)
	{
		this.aprovada = aprovada;
	}

    public boolean isEhUltimo()
	{
		return ehUltimo;
	}

    public void setEhUltimo(boolean ehUltimo)
	{
		this.ehUltimo = ehUltimo;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	public boolean isDissidio() 
	{
		return dissidio;
	}
	
	public void setDissidio(boolean dissidio) 
	{
		this.dissidio = dissidio;
	}
	
	public Character getTipoReajuste() 
	{
		return tipoReajuste;
	}
	
	public String getTipoReajusteDescricao() 
	{
		return TipoReajuste.getReajusteDescricao(tipoReajuste);
	}
	
	public void setTipoReajuste(Character tipoReajuste) 
	{
		this.tipoReajuste = tipoReajuste;
	}
}