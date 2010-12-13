package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="candidatosolicitacao_sequence", allocationSize=1)
public class CandidatoSolicitacao extends AbstractModel implements Serializable
{
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private Solicitacao solicitacao;
	private boolean triagem;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidatoSolicitacao")
	private Collection<HistoricoCandidato> historicoCandidatos;

	/* Estes campos são utilizado para exibição dos candidatos da
	 * solicitação na listagem da solicitação */
	@Transient
	private Long colaboradorId;
	@Transient
	private boolean apto = true;
	@Transient
	private EtapaSeletiva etapaSeletiva;
	@Transient
	private Date data;
	@Transient
	private String responsavel;
	@Transient
	private String observacao;
	@Transient
	private boolean existeColaborador;

	@Transient
	private int qtdCandidatos;
	@Transient
	private int qtdAptos;
	@Transient
	private Long colaboradorQuestionarioId;

	public CandidatoSolicitacao()
	{

	}

	public CandidatoSolicitacao(Long csId, Long cId, String cNome, Boolean cContratado, String indicadoPor, Long eId, String eNome, String hResponsavel, Date hData, String hObservacao, Boolean hApto, Long colaboradorId)
	{
		setId(csId);
		setCandidatoId(cId);
		setCandidatoNome(cNome);
		setCandidatoContratado(cContratado);
		setCandidatoIndicadoPor(indicadoPor);

		setResponsavel(hResponsavel);
		setData(hData);
		setObservacao(hObservacao);
		if(hApto != null)
			setApto(hApto);

		setColaboradorId(colaboradorId);

		etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(eId);
		etapaSeletiva.setNome(eNome);
	}

	public CandidatoSolicitacao(Long csId, Long cId, String cNome, Boolean cContratado, Long eId, String eNome, String hResponsavel, Date hData, String hObservacao, Boolean hApto, Long colaboradorId)
	{
		setId(csId);
		setCandidatoId(cId);
		setCandidatoNome(cNome);
		setCandidatoContratado(cContratado);

		setResponsavel(hResponsavel);
		setData(hData);
		setObservacao(hObservacao);
		if(hApto != null)
			setApto(hApto);

		setColaboradorId(colaboradorId);

		etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(eId);
		etapaSeletiva.setNome(eNome);
	}

	//utilizado no daoHibernate
	public void setCandidatoId(Long candidatoId)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setId(candidatoId);
	}

	public void setCandidatoNome(String candidatoNome)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setNome(candidatoNome);
	}

	public void setCandidatoContratado(boolean candidatoContratado)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setContratado(candidatoContratado);
	}

	public void setCandidatoIndicadoPor(String candidatoIndicadoPor)
	{
		if (candidato == null)
			candidato = new Candidato();
		
		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());
		
		candidato.getPessoal().setIndicadoPor(candidatoIndicadoPor);
	}

	public void setProjectionCandidatoDataAtualizacao(Date projectionCandidatoDataAtualizacao)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setDataAtualizacao(projectionCandidatoDataAtualizacao);
	}

	public void setProjectionCandidatoEmail(String projectionCandidatoEmail)
	{
		if (candidato == null)
			candidato = new Candidato();
		if(candidato.getContato() == null)
			candidato.setContato(new Contato());

		candidato.getContato().setEmail(projectionCandidatoEmail);
	}

	public void setProjectionCandidatoOrigem(char projectionCandidatoOrigem)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setOrigem(projectionCandidatoOrigem);
	}
	
	public void setProjectionEtapaId(Long projectionEtapaId)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();
		
		etapaSeletiva.setId(projectionEtapaId);
	}
	
	public void setProjectionEtapaNome(String projectionEtapaNome)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();
		
		etapaSeletiva.setNome(projectionEtapaNome);
	}

	public void setProjectionCandidatoBlackList(boolean projectionCandidatoBlackList)
	{
		if (candidato == null)
			candidato = new Candidato();

		candidato.setBlackList(projectionCandidatoBlackList);
	}

	public void setProjectionCidadeNome(String projectionCidadeNome)
	{
		if (candidato == null)
			candidato = new Candidato();
		if (candidato.getEndereco() == null)
			candidato.setEndereco(new Endereco());
		if (candidato.getEndereco().getCidade() == null)
			candidato.getEndereco().setCidade(new Cidade());

		candidato.getEndereco().getCidade().setNome(projectionCidadeNome);
	}

	public void setProjectionSolicitacaoDescricao(String descricao)
	{
		solicitacao.setDescricao(descricao);
	}
	
	public void setProjectionUfSigla(String projectionUfSigla)
	{
		if (candidato == null)
			candidato = new Candidato();
		if (candidato.getEndereco() == null)
			candidato.setEndereco(new Endereco());
		if (candidato.getEndereco().getUf() == null)
			candidato.getEndereco().setUf(new Estado());

		candidato.getEndereco().getUf().setSigla(projectionUfSigla);
	}

	public void setSolicitacaoId(Long solicitacaoId)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();

		solicitacao.setId(solicitacaoId);
	}

	public void setCandidatoPessoalCpf(String cpf)
	{
		if(candidato == null)
			candidato = new Candidato();

		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());

		candidato.getPessoal().setCpf(cpf);

	}

	public void setProjectionCandidatoEscolaridade(String projectionCandidatoEscolaridade)
	{
		if(candidato == null)
			candidato = new Candidato();

		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());

		candidato.getPessoal().setEscolaridade(projectionCandidatoEscolaridade);

	}

		public void setSolicitacaoQtd(int qtd)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		solicitacao.setQuantidade(qtd);
	}
	public void setSolicitanteId(Long id)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getSolicitante() == null)
			solicitacao.setSolicitante(new Usuario());
		solicitacao.getSolicitante().setId(id);
	}
	public void setSolicitanteNome(String nome)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getSolicitante() == null)
			solicitacao.setSolicitante(new Usuario());
		solicitacao.getSolicitante().setNome(nome);
	}
	public void setAreaOrganizacionalId(Long id)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getAreaOrganizacional() == null)
			solicitacao.setAreaOrganizacional(new AreaOrganizacional());
		solicitacao.getAreaOrganizacional().setId(id);
	}
	public void setAreaOrganizacionalNome(String nome)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getAreaOrganizacional() == null)
			solicitacao.setAreaOrganizacional(new AreaOrganizacional());
		solicitacao.getAreaOrganizacional().setNome(nome);
	}
	public void setFaixaId(Long id)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getFaixaSalarial() == null)
			solicitacao.setFaixaSalarial(new FaixaSalarial());

		solicitacao.getFaixaSalarial().setId(id);
	}
	public void setFaixaNome(String nome)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getFaixaSalarial() == null)
			solicitacao.setFaixaSalarial(new FaixaSalarial());

		solicitacao.getFaixaSalarial().setNome(nome);
	}
	public void setCargoId(Long id)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getFaixaSalarial() == null)
			solicitacao.setFaixaSalarial(new FaixaSalarial());
		if(solicitacao.getFaixaSalarial().getCargo() == null)
			solicitacao.getFaixaSalarial().setCargo(new Cargo());

		solicitacao.getFaixaSalarial().getCargo().setId(id);
	}
	public void setCargoNome(String nome)
	{
		if (solicitacao == null)
			solicitacao = new Solicitacao();
		if (solicitacao.getFaixaSalarial() == null)
			solicitacao.setFaixaSalarial(new FaixaSalarial());
		if(solicitacao.getFaixaSalarial().getCargo() == null)
			solicitacao.getFaixaSalarial().setCargo(new Cargo());

		solicitacao.getFaixaSalarial().getCargo().setNome(nome);
	}

	//get usado pelo populaCheckListBox
	public String getCandidatoNome()
	{
		String label = "";
		if(candidato != null && candidato.getNome() != null)
			label = candidato.getNome();

		if(etapaSeletiva != null && etapaSeletiva.getNome() != null && !etapaSeletiva.getNome().equals(""))
			label += " (" + etapaSeletiva.getNome() + ")";
			
		return label;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}
	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}
	public boolean isApto()
	{
		return apto;
	}
	public void setApto(boolean apto)
	{
		this.apto = apto;
	}
	
	public void setProjectionApto(Boolean apto)
	{
		if(apto != null)
			this.apto = apto;
	}
	
	public boolean isTriagem()
	{
		return triagem;
	}
	public void setTriagem(boolean triagem)
	{
		this.triagem = triagem;
	}

	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}

	public String getDataString()
	{
		return DateUtil.formataDiaMesAno(data);
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
	public String getResponsavel()
	{
		return responsavel;
	}
	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("etapaSeletiva", this.etapaSeletiva).append(
						"solicitacao", this.solicitacao).append("id", this.getId())
				.append("apto", this.apto).append("responsavel",
						this.responsavel).append("observacao", this.observacao)
				.append("data", this.data).append("candidato", this.candidato)
				.append("triagem", this.triagem).toString();
	}

	public boolean isExisteColaborador()
	{
		return existeColaborador;
	}

	public void setExisteColaborador(boolean existeColaborador)
	{
		this.existeColaborador = existeColaborador;
	}

	public int getQtdCandidatos()
	{
		return qtdCandidatos;
	}

	public void setQtdCandidatos(int qtdCandidatos)
	{
		this.qtdCandidatos = qtdCandidatos;
	}

	public int getQtdAptos()
	{
		return qtdAptos;
	}

	public void setQtdAptos(int qtdAptos)
	{
		this.qtdAptos = qtdAptos;
	}

	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		this.colaboradorId = colaboradorId;
	}

	public Collection<HistoricoCandidato> getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}
	
	public Long getColaboradorQuestionarioId() {
		return colaboradorQuestionarioId;
	}

	public void setColaboradorQuestionarioId(Long colaboradorQuestionarioId) {
		this.colaboradorQuestionarioId = colaboradorQuestionarioId;
	}

}