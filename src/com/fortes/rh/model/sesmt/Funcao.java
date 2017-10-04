package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.security.auditoria.ChaveDaAuditoria;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="funcao_sequence", allocationSize=1)
public class Funcao extends AbstractModel implements Serializable
{
	@Column(length=100)
    private String nome;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="funcao")
	private Collection<HistoricoFuncao> historicoFuncaos;
	
	@OneToMany(mappedBy="funcao")
	private Collection<MedicaoRisco> medicaoRiscos;
	
	@ManyToOne
	private Empresa empresa;
	
	@Column(length=6)
	private String codigoCbo;
	
	@Transient
	private HistoricoFuncao historicoAtual;
	
	@Transient
	private String exameNome;
	
	public Funcao() {
	}
	
	public Funcao(Long id, String nome, String descricao) 
	{
		setId(id);
		this.nome = nome;
		this.historicoAtual = new HistoricoFuncao();
		historicoAtual.setDescricao(descricao);
	}
	
	public Funcao(Long id, String nome, Long historicoAtualId, String descricao) 
	{
		setId(id);
		this.nome = nome;
		this.historicoAtual = new HistoricoFuncao();
		historicoAtual.setDescricao(descricao);
		historicoAtual.setId(historicoAtualId);
	}
	
	public Funcao(Long id, String nome) 
	{
		setId(id);
		this.nome = nome;
	}

	@ChaveDaAuditoria
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<HistoricoFuncao> getHistoricoFuncaos()
	{
		return historicoFuncaos;
	}
	public void setHistoricoFuncaos(Collection<HistoricoFuncao> historicoFuncaos)
	{
		this.historicoFuncaos = historicoFuncaos;
	}

	/**
	 * Variável <i>Transiente</i>.
	 * Preenchida por uma consulta com o histórico atual
	 * @see ppraDao.getFuncoesDoAmbiente()
	 */
	public HistoricoFuncao getHistoricoAtual() {
		return historicoAtual;
	}

	public void setHistoricoAtual(HistoricoFuncao historicoAtual) {
		this.historicoAtual = historicoAtual;
	}
	
	public void setHistoricoAtualId(Long historicoAtualId) {
		inicializaHistoricoAtual();
		historicoAtual.setId(historicoAtualId);
	}
	
	public void setHistoricoAtualDescricao(String historicoAtualDescricao) {
		inicializaHistoricoAtual();
		historicoAtual.setDescricao(historicoAtualDescricao);
	}

	private void inicializaHistoricoAtual() {
		if(historicoAtual == null)
			this.historicoAtual = new HistoricoFuncao();
	}

	public Collection<MedicaoRisco> getMedicaoRiscos() {
		return medicaoRiscos;
	}

	public void setMedicaoRiscos(Collection<MedicaoRisco> medicaoRiscos) {
		this.medicaoRiscos = medicaoRiscos;
	}

	public String getExameNome() {
		return exameNome;
	}

	public void setExameNome(String exameNome) {
		this.exameNome = exameNome;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaId){
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}

	public String getCodigoCbo() {
		return codigoCbo;
	}

	public void setCodigoCbo(String codigoCbo) {
		this.codigoCbo = codigoCbo;
	}
}