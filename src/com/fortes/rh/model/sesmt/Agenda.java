package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="agenda_sequence", allocationSize=1)
public class Agenda extends AbstractModel implements Serializable, Cloneable
{
	@Temporal(TemporalType.DATE)
    private Date data;
	@ManyToOne
	private Estabelecimento estabelecimento;
	@ManyToOne
	private Evento evento;
	
	//projection
	public void setProjectionEmpresaId(Long estabelecimentoId)
	{
		criaEstabelecimento();
		if(this.estabelecimento.getEmpresa() == null)
			this.estabelecimento.setEmpresa(new Empresa());
		this.estabelecimento.getEmpresa().setId(estabelecimentoId);
	}
	public void setProjectionEstabelecimentoId(Long estabelecimentoId)
	{
		criaEstabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
	}
	private void criaEstabelecimento()
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
	}
	public void setProjectionEventoId(Long enventoId)
	{
		criaEvento();
		this.evento.setId(enventoId);
	}
	public void setProjectionEventoNome(String enventoNome)
	{
		criaEvento();
		this.evento.setNome(enventoNome);
	}
	
	private void criaEvento()
	{
		if(this.evento == null)
			this.evento = new Evento();
	}
	
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}

	public Evento getEvento()
	{
		return evento;
	}
	public void setEvento(Evento evento)
	{
		this.evento = evento;
	}

	public void setDataMesAno(String dataMesAno)
	{
		this.data = DateUtil.criarDataMesAno(dataMesAno);
	}
	
	public String getMesAno()
	{
		return DateUtil.formataMesAno(this.data);
	}
	
	public String getMesAnoFormatado()
	{
		return DateUtil.formataMesExtensoAno(this.data);
	}
	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", this.getId())
				.append("Data", this.data)
				.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	public void setNovaData(Date dataAntiga, Integer periodicidade, Integer tipoPeriodo)
	{
		if(tipoPeriodo.equals(0))
			this.setData(DateUtil.incrementaMes(dataAntiga, periodicidade));
		else if(tipoPeriodo.equals(1))
			this.setData(DateUtil.incrementaAno(dataAntiga, periodicidade));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((evento == null) ? 0 : evento.hashCode());
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		return result;
	}
	
	// mesmos mês/ano e evento para o mesmo estabelecimento
	@Override
	public boolean equals(Object obj) {
		
		if (getClass() != obj.getClass())
				return false;
		
		Agenda agenda = ((Agenda)obj);
		
		if (getId() != null && getId().equals(agenda.getId()))
			return true;
		
		if (data == null || evento == null || evento.getId() == null || estabelecimento == null || estabelecimento.getId() == null
				|| agenda.getData() == null || agenda.getEvento() == null || agenda.getEstabelecimento() == null)
			return false;
		
		if (DateUtil.equalsMesAno(this.data, agenda.getData()) && this.evento.getId().equals(agenda.getEvento().getId()) 
				&& estabelecimento.getId().equals(agenda.getEstabelecimento().getId()))
			return true;
		
		return false;
	}
}