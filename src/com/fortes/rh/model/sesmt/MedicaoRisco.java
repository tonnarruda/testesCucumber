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

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.LocalAmbiente;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="medicaorisco_sequence", allocationSize=1)
public class MedicaoRisco extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
    private Date data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Ambiente ambiente;

	@ManyToOne
	private Funcao funcao;
    
	@OneToMany(mappedBy="medicaoRisco", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
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
		
		this.ambiente.setProjectionEstabelecimentoNome(estabelecimentoNome);
	}
	
	public void setEstabelecimentoNomeOrSetEstabalecimentoDeTerceiro(String estabelecimentoNome)
	{
		if (ambiente == null)
			ambiente = new Ambiente();
		
		if(StringUtils.isBlank(estabelecimentoNome))
			this.ambiente.setProjectionEstabelecimentoNome(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getDescricao());
		else
			this.ambiente.setProjectionEstabelecimentoNome(estabelecimentoNome);
	}
	
	public void setProjectionFuncaoId(Long funcaoId)
	{
		incializaFuncao();
		
		funcao.setId(funcaoId);
	}
	
	public void setProjectionFuncaoNome(String funcaoNome)
	{
		incializaFuncao();
		
		funcao.setNome(funcaoNome);
	}
	
	public void setProjectionEmpresaId(Long empresaId)
	{
		incializaFuncao();
		funcao.setEmpresaId(empresaId);
	}

	private void incializaFuncao() {
		if (funcao == null)
			funcao = new Funcao();
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

	public void setRiscoMedicaoRiscos(Collection<RiscoMedicaoRisco> riscoMedicaoRiscos) {
		this.riscoMedicaoRiscos = riscoMedicaoRiscos;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
}
