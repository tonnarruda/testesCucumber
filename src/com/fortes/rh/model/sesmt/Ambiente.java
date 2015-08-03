package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="ambiente_sequence", allocationSize=1)
public class Ambiente extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String nome;
	@ManyToOne
	private Empresa empresa;
	
	@ManyToOne
	private Estabelecimento estabelecimento;

	@OneToMany(fetch=FetchType.EAGER, mappedBy="ambiente", cascade=CascadeType.REMOVE)
	private Collection<HistoricoAmbiente> historicoAmbientes;
	
	@OneToMany(mappedBy="ambiente")
	private Collection<MedicaoRisco> medicaoRiscos;
	
	@Transient
	private HistoricoAmbiente historicoAtual;
	
	public Ambiente() {
	}
	
	public Ambiente(Long ambienteId, String ambienteNome, String ambienteDescricao, String ambienteTempoExposicao) 
	{
		setId(ambienteId);
		this.nome = ambienteNome;
		historicoAtual = new HistoricoAmbiente();
		historicoAtual.setDescricao(ambienteDescricao);
		historicoAtual.setTempoExposicao(ambienteTempoExposicao);
	}

	//Projection
	public void setProjectionEmpresaId(Long empresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}
	public void setProjectionEstabelecimentoId(Long estabelecimentoId)
	{
		this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
	}
	public void setProjectionEstabelecimentoNome(String estabelecimentoNome)
	{
		this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setNome(estabelecimentoNome);
	}
	
    public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getNomeComEstabelecimento()
	{
		String nomeComEstabelecimento = nome;
		if (estabelecimento != null && estabelecimento.getNome() != null)
			nomeComEstabelecimento = estabelecimento.getNome() + " - " + nome;
		return nomeComEstabelecimento;
	}
	
	public Collection<HistoricoAmbiente> getHistoricoAmbientes()
	{
		return historicoAmbientes;
	}
	public void setHistoricoAmbientes(Collection<HistoricoAmbiente> historicoAmbientes)
	{
		this.historicoAmbientes = historicoAmbientes;
	}


	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}


	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	
	/**
	 * Variável <i>Transiente</i>.
	 * Preenchido por uma consulta com o histórico atual
	 * @see ambienteDao.findByIds()
	 */
	public HistoricoAmbiente getHistoricoAtual() {
		return historicoAtual;
	}
	public void setHistoricoAtual(HistoricoAmbiente historicoAtual) {
		this.historicoAtual = historicoAtual;
	}

	public Collection<MedicaoRisco> getMedicaoRiscos() {
		return medicaoRiscos;
	}

	public void setMedicaoRiscos(Collection<MedicaoRisco> medicaoRiscos) {
		this.medicaoRiscos = medicaoRiscos;
	}
}