package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="historicocolaboradorbeneficio_sequence", allocationSize=1)
public class HistoricoColaboradorBeneficio extends AbstractModel implements Serializable
{
	@ManyToOne
	private Colaborador colaborador;

	@ManyToMany
	private Collection<Beneficio> beneficios;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Temporal(TemporalType.DATE)
	private Date dataAte;

	@Transient
	private String dataMesAno;

    public String getDataMesAno()
	{
		return dataMesAno;
	}
	public void setDataMesAno(String dataMesAno)
	{
		this.dataMesAno = dataMesAno;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public Collection<Beneficio> getBeneficios()
	{
		return beneficios;
	}
	public void setBeneficios(Collection<Beneficio> beneficios)
	{
		this.beneficios = beneficios;
	}
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorId(Long id)
	{
		if (colaborador == null)
			colaborador = new Colaborador();
		colaborador.setId(id);
	}

	public void setColaboradorAreaOrganizacionalId(Long id)
	{
		if (colaborador == null)
			colaborador = new Colaborador();

		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setId(id);
		colaborador.setAreaOrganizacional(areaOrganizacional);
	}

	public Date getDataAte()
	{
		return dataAte;
	}

	public void setDataAte(Date dataAte)
	{
		this.dataAte = dataAte;
	}
}