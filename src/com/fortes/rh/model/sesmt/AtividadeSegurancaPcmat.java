package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="atividadeSegurancaPcmat_sequence", allocationSize=1)
public class AtividadeSegurancaPcmat extends AbstractModel implements Serializable
{
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private Integer cargaHoraria;
	@ManyToOne
	private Pcmat pcmat;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public Integer getCargaHoraria() {
		return cargaHoraria;
	}
	
	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public Pcmat getPcmat() {
		return pcmat;
	}
	
	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}
	
	public void setDataMesAno(String dataMesAno)
	{
		this.data = DateUtil.criarDataMesAno(dataMesAno);
	}

	public String getDataMesAno()
	{
		return DateUtil.formataMesAno(this.data);
	}
	
	public void setCargaHorariaMinutos(String cargaHorariaMinutos) 
	{
		if (!cargaHorariaMinutos.equals("    :  ") && !cargaHorariaMinutos.equals("0000:00")) {
			Integer hora = Integer.parseInt(cargaHorariaMinutos.substring(0, (cargaHorariaMinutos.length() - 3)));
			Integer minutos = Integer.parseInt(cargaHorariaMinutos.substring(cargaHorariaMinutos.length() - 2, cargaHorariaMinutos.length()));
			setCargaHoraria(hora * 60 + minutos);
		}
	}
	
	public String getCargaHorariaMinutos()
	{
		if (cargaHoraria == null)
			return "";
		
		Integer hora = cargaHoraria/60;
		Integer minutos = cargaHoraria%60;
		
		return (StringUtils.leftPad(hora.toString(), 4, ' ') + ":" +StringUtils.leftPad(minutos.toString(), 2, "0")).trim();		
	}

	public String getCargaHorariaEmHora() 
	{
		if (cargaHoraria != null && !cargaHoraria.equals("")) {
			int hora = cargaHoraria / 60;   
		    int minutos = cargaHoraria % 60;  
		           
		    return (String.valueOf(hora) + ":" +StringUtils.leftPad(String.valueOf(minutos), 2, "0")).trim();	
		}
		
		return "";
	}

	public void setPcmatId(Long pcmatId) 
	{
		if (this.pcmat == null)
			this.pcmat = new Pcmat();
		
		this.pcmat.setId(pcmatId);
	}
}
