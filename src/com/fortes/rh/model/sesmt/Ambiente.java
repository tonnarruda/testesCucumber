package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = false)
@SequenceGenerator(name="sequence", sequenceName="ambiente_sequence", allocationSize=1)
public class Ambiente extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String nome;
	@ManyToOne
	private Empresa empresa;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ambiente", cascade=CascadeType.REMOVE)
	private Collection<HistoricoAmbiente> historicoAmbientes;
	
	@OneToMany(mappedBy="ambiente")
	private Collection<MedicaoRisco> medicaoRiscos;

	@Transient
	private HistoricoAmbiente historicoAtual;
	
	public Ambiente() {
	}
	
	public Ambiente(String nome, Empresa empresa) {
		setNome(nome);
		setEmpresa(empresa);
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
	
	public Collection<HistoricoAmbiente> getHistoricoAmbientes()
	{
		return historicoAmbientes;
	}
	public void setHistoricoAmbientes(Collection<HistoricoAmbiente> historicoAmbientes)
	{
		this.historicoAmbientes = historicoAmbientes;
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

	private void iniciaHistoricoAmbineteAtual() {
		if(this.historicoAtual == null)
			this.historicoAtual = new HistoricoAmbiente();
	}
	
	public void setHistoricoAmbienteNomeAmbiente(String nomeAmbiente){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setNomeAmbiente(nomeAmbiente);
	}
	
	public void setHistoricoAmbienteAtualDescricao(String historicoAmbienteAtualDescricao){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setDescricao(historicoAmbienteAtualDescricao);
	}
	
	public void setHistoricoAmbienteAtualData(Date historicoAmbienteAtualData){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setData(historicoAmbienteAtualData);
	}

	public void setHistoricoAmbienteAtualTempoExposicao(String historicoAmbienteAtualTempoExposicao){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setTempoExposicao(historicoAmbienteAtualTempoExposicao);
	}
	
	public void setHistoricoAmbienteAtualCnpjEstabTerceiros(String cnpjEstabTerceiros){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setCnpjEstabelecimentoDeTerceiros(cnpjEstabTerceiros);
	}
	
	public void setProjectionEstabelecimentoId(Long estabelecimentoId){
		iniciaHistoricoAmbineteAtual();
		historicoAtual.setEstabelecimentoId(estabelecimentoId);
	}
	
	public void setProjectionEstabelecimentoNome(String estabelecimentoNome){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setEstabelecimentoNome(estabelecimentoNome);
	}
	
	public void setProjectionLocalAmbiente(Integer localAmbiente){
		iniciaHistoricoAmbineteAtual();
		this.historicoAtual.setLocalAmbiente(localAmbiente);
	}
	
	public void setEstabelecimentoNomeOrEstabalecimentoDeTerceiro(String estabelecimentoNome){
		iniciaHistoricoAmbineteAtual();
		
		if(StringUtils.isBlank(estabelecimentoNome))
			this.historicoAtual.setEstabelecimentoNome(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getDescricao());
		else
			this.historicoAtual.setEstabelecimentoNome(estabelecimentoNome);
	}
	
	@NaoAudita
	public String getNomeComEstabelecimento()
	{
		String nomeComEstabelecimento = nome;
		if (historicoAtual!= null && historicoAtual.getEstabelecimento() != null && historicoAtual.getEstabelecimento().getNome() != null)
			nomeComEstabelecimento = historicoAtual.getEstabelecimento().getNome() + " - " + nome;
		return nomeComEstabelecimento;
	}
}