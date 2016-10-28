package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Estabelecimento;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="medicaorisco_sequence", allocationSize=1)
public class MedicaoRisco extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
    private Date data;
	
	@ManyToOne
	private Ambiente ambiente;

	@ManyToOne
	private Funcao funcao;
    
	@OneToMany(mappedBy="medicaoRisco", cascade=CascadeType.ALL)
	private Collection<RiscoMedicaoRisco> riscoMedicaoRiscos;

	public MedicaoRisco() {}

	public MedicaoRisco(Long id, RiscoMedicaoRisco rmr, boolean epcEficaz, Date data) 
	{
		setId(id);
		
		if (riscoMedicaoRiscos == null)
			riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		
		riscoMedicaoRiscos.add(rmr);
		
		this.data = data;
	}
	
	public void setProjectionAmbienteId(Long ambienteId)
	{
		if (ambiente == null)
			ambiente = new Ambiente();
		
		ambiente.setId(ambienteId);
	}
	
	public void setProjectionAmbienteNome(String ambienteNome)
	{
		if (ambiente == null)
			ambiente = new Ambiente();
		
		ambiente.setNome(ambienteNome);
	}
	
	public void setProjectionEstabelecimentoNome(String estabelecimentoNome)
	{
		if (ambiente == null)
			ambiente = new Ambiente();
		
		this.ambiente.setEstabelecimento(new Estabelecimento());
		this.ambiente.getEstabelecimento().setNome(estabelecimentoNome);
	}
	
	public void setProjectionFuncaoId(Long funcaoId)
	{
		if (funcao == null)
			funcao = new Funcao();
		
		funcao.setId(funcaoId);
	}
	
	public void setProjectionFuncaoNome(String funcaoNome)
	{
		if (funcao == null)
			funcao = new Funcao();
		
		funcao.setNome(funcaoNome);
	}
	
	public void setProjectionCargoNome(String cargoNome)
	{
		if (funcao == null)
			funcao = new Funcao();
		
		if(funcao.getCargo() == null)
			this.funcao.setCargo(new Cargo());
		
		this.funcao.getCargo().setNome(cargoNome);
	}
	
	
	public void setProjectionCargoId(Long cargoId)
	{
		if (funcao == null)
			funcao = new Funcao();
		
		if(funcao.getCargo() == null)
			this.funcao.setCargo(new Cargo());
		
		this.funcao.getCargo().setId(cargoId);
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Collection<RiscoMedicaoRisco> getRiscoMedicaoRiscos() {
		return riscoMedicaoRiscos;
	}

	public void setRiscoMedicaoRiscos(
			Collection<RiscoMedicaoRisco> riscoMedicaoRiscos) {
		this.riscoMedicaoRiscos = riscoMedicaoRiscos;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
}
