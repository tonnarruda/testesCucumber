package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.util.SalarioUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "reajustecolaborador_sequence", allocationSize = 1)
public class ReajusteColaborador extends AbstractModel implements Serializable
{
	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne
	private FaixaSalarial faixaSalarialAtual;
	@ManyToOne
	private FaixaSalarial faixaSalarialProposta;
	private Double salarioAtual;
	private Double salarioProposto;
	@ManyToOne
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	@ManyToOne
	private AreaOrganizacional areaOrganizacionalAtual;
	@ManyToOne
	private AreaOrganizacional areaOrganizacionalProposta;
	@ManyToOne
	private Funcao funcaoAtual;
	@ManyToOne
	private Funcao funcaoProposta;
	@ManyToOne
	private Ambiente ambienteAtual;
	@ManyToOne
	private Ambiente ambienteProposto;
	@Transient
	private String descricaoAreaOrganizacional;
	@ManyToOne
	private Estabelecimento estabelecimentoAtual;
	@ManyToOne
	private Estabelecimento estabelecimentoProposto;
	@ManyToOne
	private Indice indiceAtual;
	private Double quantidadeIndiceAtual;
	private int tipoSalarioAtual;
	@ManyToOne
	private Indice indiceProposto;
	private Double quantidadeIndiceProposto;
	private int tipoSalarioProposto;
	
	@Lob
	private String observacao;

	@Transient
	private String descricaoTipoSalarioAtual;
	@Transient
	private String descricaoTipoSalarioProposto;

	public ReajusteColaborador()
	{
	}

	public ReajusteColaborador(Long id, Long areaAtualId, String faixaSalarialAtualNome, String cargoAtualNome, Long faixaSalarialPropostaId, String faixaSalarialPropostaNome,
			String faixaSalarialPropostaCodigoAC, String cargoPropostoNome, Integer tipoSalarioAtual, Integer tipoSalarioProposto, Double salarioAtual, Double salarioProposto, String observacao,
			Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, String colaboradorCodigoAC, boolean colaboradorNaoIntegraAC, boolean colaboradorDesligado, Date colaboradorDataDesligamento, Long areaColaboradorId,
			String areaAtualNome, Long areaPropostaId, String areaPropostaNome, String areaPropostaCodigoAC, Long tabelaReajusteColaboradorId, Date tabelaReajusteColaboradorData, Double faixaSalarialHistoricoAtualValor, Integer faixaSalarialHistoricoAtualTipo,
			Double faixaSalarialHistoricoAtualQuantidade, Double faixaSalarialHistoricoPropostoValor, Integer faixaSalarialHistoricoPropostoTipo, Double faixaSalarialHistoricoPropostoQuantidade,
			Double indiceHistoricoAtualValor, Double quantidadeIndiceAtual, Double indiceHistoricoPropostoValor, Double quantidadeIndiceProposto,
			Double indiceHistoricoFaixaSalarialAtualValor, Double indiceHistoricoFaixaSalarialPropostaValor, Long indiceHistoricoFaixaSalarialPropostaId,
			Long grupoOcupacionalId, String grupoOcupacionalNome, Long estabelecimentoId, String estabelecimentoPropostoNome, String estabelecimentoCodigoAC, String estabelecimentoAtualNome ,Long faixaSalarialHistoricoPropostoId,
			Long projectionIndicePropostoId, String indicePropostoNome, String projectionIndicePropostoCodigoAC, Long indiceFaixaSalarialHistoricoPropostoId, Long ambientePropostoId, Long funcaoPropostaId, String indiceAtualNome)
	{
		this.setId(id);
		this.setTipoSalarioAtual(tipoSalarioAtual);
		this.setTipoSalarioProposto(tipoSalarioProposto);
		this.setSalarioAtual(salarioAtual);
		this.setSalarioProposto(salarioProposto);
		this.setObservacao(observacao);
		this.setAreaOrganizacionalPropostaId(areaPropostaId);
		this.setAreaOrganizacionalPropostaNome(areaPropostaNome);
		this.setAreaOrganizacionalPropostaCodigoAC(areaPropostaCodigoAC);
		this.setProjectionAreaAtualNome(areaAtualNome);
		this.setTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
		this.setTabelaReajusteColaboradorData(tabelaReajusteColaboradorData);
		this.setIdColaborador(colaboradorId);
		this.setNomeColaborador(colaboradorNome);
		this.setNomeComercialProjection(colaboradorNomeComercial);
		this.setColaboradorCodigoACProjection(colaboradorCodigoAC);
		this.setProjectionColaboradorNaoIntegraAC(colaboradorNaoIntegraAC);
		this.setProjectionColaboradorDesligado(colaboradorDesligado);
		this.setProjectionColaboradorDataDesligamento(colaboradorDataDesligamento);
		this.setAreaOrganizacionalId(areaColaboradorId);
		this.setProjectionAreaAtualId(areaAtualId);
		this.setProjectionFaixaSalarialAtualNome(faixaSalarialAtualNome);
		this.setProjectionFaixaSalarialPropostaId(faixaSalarialPropostaId);
		this.setProjectionFaixaSalarialPropostaNome(faixaSalarialPropostaNome);
		this.setProjectionFaixaSalarialPropostaCodigoAC(faixaSalarialPropostaCodigoAC);
		this.setProjectionCargoAtualNome(cargoAtualNome);
		this.setProjectionCargoPropostoNome(cargoPropostoNome);

		this.setFaixaSalarialHistoricoAtualValor(faixaSalarialHistoricoAtualValor);
		this.setFaixaSalarialHistoricoAtualTipo(faixaSalarialHistoricoAtualTipo);
		this.setFaixaSalarialHistoricoAtualQuantidade(faixaSalarialHistoricoAtualQuantidade);

		this.setFaixaSalarialHistoricoPropostoId(faixaSalarialHistoricoPropostoId);
		this.setFaixaSalarialHistoricoPropostoValor(faixaSalarialHistoricoPropostoValor);
		this.setFaixaSalarialHistoricoPropostoTipo(faixaSalarialHistoricoPropostoTipo);
		this.setFaixaSalarialHistoricoPropostoQuantidade(faixaSalarialHistoricoPropostoQuantidade);

		this.setIndiceHistoricoAtualValor(indiceHistoricoAtualValor);
		this.setQuantidadeIndiceAtual(quantidadeIndiceAtual);
		this.setIndiceHistoricoPropostoValor(indiceHistoricoPropostoValor);
		this.setQuantidadeIndiceProposto(quantidadeIndiceProposto);

		this.setIndiceHistoricoFaixaSalarialAtualValor(indiceHistoricoFaixaSalarialAtualValor);
		this.setIndiceHistoricoFaixaSalarialPropostaValor(indiceHistoricoFaixaSalarialPropostaValor);
		this.setIndiceHistoricoFaixaSalarialPropostaId(indiceHistoricoFaixaSalarialPropostaId);

		this.setProjectionGrupoOcupacionalPropostoId(grupoOcupacionalId);
		this.setProjectionGrupoOcupacionalPropostoNome(grupoOcupacionalNome);

		this.setProjectionEstabelecimentoPropostoId(estabelecimentoId);
		this.setProjectionEstabelecimentoPropostoNome(estabelecimentoPropostoNome);
		this.setProjectionEstabelecimentoPropostoCodigoAC(estabelecimentoCodigoAC);
		
		this.setProjectionEstabelecimentoAtualNome(estabelecimentoAtualNome);

		this.setProjectionIndicePropostoId(projectionIndicePropostoId);
		this.setProjectionIndicePropostoNome(indicePropostoNome);
		this.setProjectionIndicePropostoCodigoAC(projectionIndicePropostoCodigoAC);
		this.setIndiceFaixaSalarialHistoricoPropostoId(indiceFaixaSalarialHistoricoPropostoId);

		this.setProjectionAmbientePropostoId(ambientePropostoId);
		this.setProjectionFuncaoPropostaId(funcaoPropostaId);
		this.setProjectionIndiceAtualNome(indiceAtualNome);
	}

	public ReajusteColaborador(Long id, String observacao, Double salarioAtual, Double salarioProposto, Integer tipoSalarioAtual, Integer tipoSalarioProposto, Double quantidadeIndiceProposto, Double quantidadeIndiceAtual, Long tabelaReajusteColaboradorId,
		    Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, Long indicePropostoId, String indicePropostoNome, Double indiceHistoricoPropostoValor, Long indiceAtualId, String indiceAtualNome, Double indiceHistoricoAtualValor,
		    Long areaOrganizacionalPropostaId, String areaOrganizacionalPropostaNome, Long areaOrganizacionalAtualId, String areaOrganizacionalAtualNome, Long ambientePropostoId, String ambientePropostoNome, Long ambienteAtualId,
		    String ambienteAtualNome, Long funcaoPropostaId, String funcaoPropostaNome, Long funcaoAtualId, String funcaoAtualNome, Long faixaSalarialPropostaId, String faixaSalarialPropostaNome, Long faixaSalarialAtualId,
		    String faixaSalarialAtualNome, Long cargoPropostoId, String cargoPropostoNomeMercado, String cargoPropostoNome, Long cargoAtualId, String cargoAtualNomeMercado, String cargoAtualNome, Long grupoOcupacionalPropostoId,
		    String grupoOcupacionalPropostoNome, Long grupoOcupacionalAtualId, String grupoOcupacionalAtualNome, Long estabelecimentoPropostoId, String estabelecimentoPropostoNome, Long estabelecimentoAtualId,
		    String estabelecimentoAtualNome, Double faixaSalarialHistoricoPropostoValor, Double faixaSalarialHistoricoAtualValor, Integer faixaSalarialHistoricoPropostoTipo, Integer faixaSalarialHistoricoAtualTipo,
		    Double faixaSalarialHistoricoPropostoQuantidade, Double faixaSalarialHistoricoAtualQuantidade, Double indiceFaixaSalarialHistoricoPropostoValor, Double indiceFaixaSalarialHistoricoAtualValor)
	{
		this.setId(id);
		this.observacao = observacao;
		this.setSalarioAtual(salarioAtual);
		this.setSalarioProposto(salarioProposto);
		this.setTipoSalarioAtual(tipoSalarioAtual);
		this.setTipoSalarioProposto(tipoSalarioProposto);
		this.setQuantidadeIndiceProposto(quantidadeIndiceProposto);
		this.setQuantidadeIndiceAtual(quantidadeIndiceAtual);
		this.setTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
		this.setIdColaborador(colaboradorId);
		this.setNomeColaborador(colaboradorNome);
		this.setNomeComercialProjection(colaboradorNomeComercial);
		this.setProjectionIndicePropostoId(indicePropostoId);
		this.setProjectionIndicePropostoNome(indicePropostoNome);
		this.setProjectionIndiceHistoricoAtualValor(indiceHistoricoAtualValor);
		this.setProjectionIndiceAtualId(indiceAtualId);
		this.setProjectionIndiceAtualNome(indiceAtualNome);
		this.setAreaOrganizacionalPropostaId(areaOrganizacionalPropostaId);
		this.setAreaOrganizacionalPropostaNome(areaOrganizacionalPropostaNome);
		this.setProjectionAreaAtualId(areaOrganizacionalAtualId);
		this.setProjectionAreaAtualNome(areaOrganizacionalAtualNome);
		this.setProjectionAmbientePropostoId(ambientePropostoId);
		this.setProjectionAmbienteAtualId(ambienteAtualId);
		this.setProjectionAmbienteAtualNome(ambienteAtualNome);
		this.setProjectionFuncaoPropostaId(funcaoPropostaId);
		this.setProjectionFuncaoAtualId(funcaoAtualId);
		this.setProjectionFuncaoAtualNome(funcaoAtualNome);
		this.setProjectionFaixaSalarialPropostaId(faixaSalarialPropostaId);
		this.setProjectionFaixaSalarialPropostaNome(faixaSalarialPropostaNome);
		this.setProjectionFaixaSalarialAtualId(faixaSalarialAtualId);
		this.setProjectionFaixaSalarialAtualNome(faixaSalarialAtualNome);
		this.setProjectionCargoPropostoNome(cargoPropostoNome);
		this.setProjectionCargoAtualNome(cargoAtualNome);
		this.setProjectionGrupoOcupacionalPropostoId(grupoOcupacionalPropostoId);
		this.setProjectionGrupoOcupacionalPropostoNome(grupoOcupacionalPropostoNome);
		this.setProjectionGrupoOcupacionalAtualId(grupoOcupacionalAtualId);
		this.setProjectionGrupoOcupacionalAtualNome(grupoOcupacionalAtualNome);
		this.setProjectionEstabelecimentoPropostoId(estabelecimentoPropostoId);
		this.setProjectionEstabelecimentoPropostoNome(estabelecimentoPropostoNome);
		this.setProjectionEstabelecimentoAtualId(estabelecimentoAtualId);
		this.setProjectionEstabelecimentoAtualNome(estabelecimentoAtualNome);
		this.setFaixaSalarialHistoricoPropostoValor(faixaSalarialHistoricoPropostoValor);
		this.setFaixaSalarialHistoricoAtualValor(faixaSalarialHistoricoAtualValor);
		this.setFaixaSalarialHistoricoPropostoTipo(faixaSalarialHistoricoPropostoTipo);
		this.setFaixaSalarialHistoricoAtualTipo(faixaSalarialHistoricoAtualTipo);
		this.setFaixaSalarialHistoricoPropostoQuantidade(faixaSalarialHistoricoPropostoQuantidade);
		this.setFaixaSalarialHistoricoAtualQuantidade(faixaSalarialHistoricoAtualQuantidade);
		this.setProjectionFaixaHistoricoAtualIndiceValor(indiceFaixaSalarialHistoricoAtualValor);
	}

	public String getDescricaoTipoSalarioAtual()
	{
		return montaDescricaoTipoSalario(this.tipoSalarioAtual, this.quantidadeIndiceAtual, this.indiceAtual.getNome());
	}

	public String getDescricaoTipoSalarioProposto()
	{
		return montaDescricaoTipoSalario(this.tipoSalarioProposto, this.quantidadeIndiceProposto, this.indiceProposto.getNome());
	}

	private String montaDescricaoTipoSalario(int tipoSalario, Double quantidade, String indiceNome)
	{
		if(tipoSalario == TipoAplicacaoIndice.INDICE)
			return "Índice (" + quantidade + "x " + indiceNome + " )";
		else
			return TipoAplicacaoIndice.getDescricao(tipoSalario);
	}

	public Indice getIndiceProposto()
	{
		return indiceProposto;
	}

	public void setIndiceProposto(Indice indiceProposto)
	{
		this.indiceProposto = indiceProposto;
	}

	public Double getQuantidadeIndiceProposto()
	{
		return quantidadeIndiceProposto;
	}

	public void setQuantidadeIndiceProposto(Double quantidadeIndiceProposto)
	{
		this.quantidadeIndiceProposto = quantidadeIndiceProposto;
	}

	public Integer getTipoSalarioProposto()
	{
		return tipoSalarioProposto;
	}

	public void setTipoSalarioProposto(Integer tipoSalarioProposto)
	{
		this.tipoSalarioProposto = tipoSalarioProposto;
	}

	public Indice getIndiceAtual()
	{
		return indiceAtual;
	}

	public void setIndiceAtual(Indice indiceAtual)
	{
		this.indiceAtual = indiceAtual;
	}

	public Double getQuantidadeIndiceAtual()
	{
		return quantidadeIndiceAtual;
	}

	public void setQuantidadeIndiceAtual(Double quantidadeIndiceAtual)
	{
		this.quantidadeIndiceAtual = quantidadeIndiceAtual;
	}

	public int getTipoSalarioAtual()
	{
		return tipoSalarioAtual;
	}

	public void setNomeColaborador(String nome)
	{
		initColaborador();
		colaborador.setNome(nome);
	}

	public void setNomeComercialProjection(String nomeComercialProjection)
	{
		initColaborador();
		colaborador.setNomeComercial(nomeComercialProjection);
	}

	public void setColaboradorCodigoACProjection(String codigoACProjection)
	{
		initColaborador();
		colaborador.setCodigoAC(codigoACProjection);
	}
	
	public void setProjectionColaboradorNaoIntegraAC(boolean colaboradorNaoIntegraAC)
	{
		initColaborador();
		colaborador.setNaoIntegraAc(colaboradorNaoIntegraAC);
	}
	
	public void setProjectionColaboradorDesligado(boolean colaboradorDesligado)
	{
		initColaborador();
		colaborador.setDesligado(colaboradorDesligado);
	}
	
	public void setProjectionColaboradorDataDesligamento(Date colaboradorDataDesligamento)
	{
		initColaborador();
		colaborador.setDataDesligamento(colaboradorDataDesligamento);
	}

	public void setIdColaborador(Long id)
	{
		initColaborador();
		colaborador.setId(id);
	}

	private void initColaborador() {
		if (colaborador == null)
			colaborador = new Colaborador();
	}

	public void setTabelaReajusteColaboradorId(Long id)
	{
		if (tabelaReajusteColaborador == null)
			tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador.setId(id);
	}

	public void setTabelaReajusteColaboradorData(Date tabelaReajusteColaboradorData)
	{
		if (tabelaReajusteColaborador == null)
			tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador.setData(tabelaReajusteColaboradorData);
	}

	public void setAreaOrganizacionalId(Long id)
	{
		initColaborador();

		if (colaborador.getAreaOrganizacional() == null)
			colaborador.setAreaOrganizacional(new AreaOrganizacional());

		colaborador.getAreaOrganizacional().setId(id);
	}

	public void setAreaOrganizacionalNome(String nome)
	{
		initColaborador();

		if (colaborador.getAreaOrganizacional() == null)
			colaborador.setAreaOrganizacional(new AreaOrganizacional());

		colaborador.getAreaOrganizacional().setNome(nome);
	}

	public void setAreaOrganizacionalPropostaNome(String nome)
	{
		if (areaOrganizacionalProposta == null)
			areaOrganizacionalProposta = new AreaOrganizacional();

		areaOrganizacionalProposta.setNome(nome);
	}

	public void setAreaOrganizacionalPropostaCodigoAC(String areaPropostaCodigoAC)
	{
		if (areaOrganizacionalProposta == null)
			areaOrganizacionalProposta = new AreaOrganizacional();

		areaOrganizacionalProposta.setCodigoAC(areaPropostaCodigoAC);
	}

	public void setAreaOrganizacionalPropostaId(Long areaPropostaId)
	{
		if (areaOrganizacionalProposta == null)
			areaOrganizacionalProposta = new AreaOrganizacional();

		areaOrganizacionalProposta.setId(areaPropostaId);
	}

	public void setAreaOrganizacionalMae(AreaOrganizacional mae)
	{
		initColaborador();

		if (colaborador.getAreaOrganizacional() == null)
			colaborador.setAreaOrganizacional(new AreaOrganizacional());

		colaborador.getAreaOrganizacional().setAreaMae(mae);
	}

	public void setProjectionFuncaoAtualId(Long projectionFuncaoAtualId)
	{
		if (this.funcaoAtual == null)
			this.funcaoAtual = new Funcao();

		this.funcaoAtual.setId(projectionFuncaoAtualId);
	}

	public void setProjectionFuncaoAtualNome(String projectionFuncaoAtualNome)
	{
		if (this.funcaoAtual == null)
			this.funcaoAtual = new Funcao();

		this.funcaoAtual.setNome(projectionFuncaoAtualNome);
	}

	public void setProjectionAmbienteAtualId(Long projectionAmbienteAtualId)
	{
		if (this.ambienteAtual == null)
			this.ambienteAtual = new Ambiente();

		this.ambienteAtual.setId(projectionAmbienteAtualId);
	}

	public void setProjectionAmbienteAtualNome(String projectionAmbienteAtualNome)
	{
		if (this.ambienteAtual == null)
			this.ambienteAtual = new Ambiente();

		this.ambienteAtual.setNome(projectionAmbienteAtualNome);
	}

	public void setProjectionAreaAtualId(Long projectionAreaAtualId)
	{
		if (this.areaOrganizacionalAtual == null)
			this.areaOrganizacionalAtual = new AreaOrganizacional();

		this.areaOrganizacionalAtual.setId(projectionAreaAtualId);
	}

	public void setProjectionAreaAtualNome(String projectionAreaAtualNome)
	{
		if (this.areaOrganizacionalAtual == null)
			this.areaOrganizacionalAtual = new AreaOrganizacional();

		this.areaOrganizacionalAtual.setNome(projectionAreaAtualNome);
	}

	public void setProjectionEstabelecimentoAtualId(Long projectionEstabelecimentoAtualId)
	{
		if (this.estabelecimentoAtual == null)
			this.estabelecimentoAtual = new Estabelecimento();

		this.estabelecimentoAtual.setId(projectionEstabelecimentoAtualId);
	}

	public void setProjectionEstabelecimentoAtualNome(String estabelecimentoAtualNome)
	{
		if (this.estabelecimentoAtual == null)
			this.estabelecimentoAtual = new Estabelecimento();

		this.estabelecimentoAtual.setNome(estabelecimentoAtualNome);
	}

	public void setProjectionEstabelecimentoPropostoId(Long estabelecimentoPropostoId)
	{
		if (this.estabelecimentoProposto == null)
			this.estabelecimentoProposto = new Estabelecimento();

		this.estabelecimentoProposto.setId(estabelecimentoPropostoId);
	}

	public void setProjectionEstabelecimentoPropostoNome(String estabelecimentoPropostoNome)
	{
		if (this.estabelecimentoProposto == null)
			this.estabelecimentoProposto = new Estabelecimento();

		this.estabelecimentoProposto.setNome(estabelecimentoPropostoNome);
	}

	public void setProjectionEstabelecimentoPropostoCodigoAC(String estabelecimentoPropostoCodigoAC)
	{
		if (this.estabelecimentoProposto == null)
			this.estabelecimentoProposto = new Estabelecimento();

		this.estabelecimentoProposto.setCodigoAC(estabelecimentoPropostoCodigoAC);
	}

	public void setProjectionFaixaSalarialAtualId(Long faixaSalarialAtualId)
	{
		initFaixaSalarialAtual();

		this.faixaSalarialAtual.setId(faixaSalarialAtualId);
	}

	public void setProjectionFaixaSalarialAtualNome(String faixaSalarialAtualNome)
	{
		initFaixaSalarialAtual();

		this.faixaSalarialAtual.setNome(faixaSalarialAtualNome);
	}

	public void setProjectionFaixaSalarialPropostaId(Long projectionFaixaSalarialPropostaId)
	{
		initFaixaSalarialProposta();

		this.faixaSalarialProposta.setId(projectionFaixaSalarialPropostaId);
	}

	public void setProjectionFaixaSalarialPropostaNome(String projectionFaixaSalarialPropostaNome)
	{
		initFaixaSalarialProposta();

		this.faixaSalarialProposta.setNome(projectionFaixaSalarialPropostaNome);
	}

	public void setProjectionFaixaSalarialPropostaCodigoAC(String projectionFaixaSalarialPropostaCodigoAC)
	{
		initFaixaSalarialProposta();

		this.faixaSalarialProposta.setCodigoAC(projectionFaixaSalarialPropostaCodigoAC);
	}

	public void setProjectionCargoAtualNome(String projectionCargoAtualNome)
	{
		initFaixaSalarialAtual();

		initFaixaSalarialAtualCargo();

		this.faixaSalarialAtual.getCargo().setNome(projectionCargoAtualNome);
	}

	public void setFaixaSalarialHistoricoAtualValor(Double faixaSalarialHistoricoAtualValor)
	{
		initFaixaSalarialAtual();

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialAtual.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().setValor(faixaSalarialHistoricoAtualValor);
	}

	public void setFaixaSalarialHistoricoAtualTipo(Integer faixaSalarialHistoricoAtualTipo)
	{
		initFaixaSalarialAtual();

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialAtual.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (faixaSalarialHistoricoAtualTipo != null)
			this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().setTipo(faixaSalarialHistoricoAtualTipo);
	}

	public void setFaixaSalarialHistoricoAtualQuantidade(Double faixaSalarialHistoricoAtualQuantidade)
	{
		initFaixaSalarialAtual();

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialAtual.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(faixaSalarialHistoricoAtualQuantidade != null)
			this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().setQuantidade(faixaSalarialHistoricoAtualQuantidade);
	}

	public void setIndiceFaixaSalarialHistoricoPropostoId(Long indiceFaixaSalarialHistoricoPropostoId)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().setId(indiceFaixaSalarialHistoricoPropostoId);
	}

	public void setFaixaSalarialHistoricoPropostoId(Long faixaSalarialHistoricoPropostoId)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setId(faixaSalarialHistoricoPropostoId);
	}

	public void setFaixaSalarialHistoricoPropostoValor(Double faixaSalarialHistoricoPropostoValor)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setValor(faixaSalarialHistoricoPropostoValor);
	}

	public void setFaixaSalarialHistoricoPropostoTipo(Integer faixaSalarialHistoricoPropostoTipo)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(faixaSalarialHistoricoPropostoTipo != null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setTipo(faixaSalarialHistoricoPropostoTipo);
	}

	public void setFaixaSalarialHistoricoPropostoQuantidade(Double faixaSalarialHistoricoPropostoQuantidade)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(faixaSalarialHistoricoPropostoQuantidade != null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setQuantidade(faixaSalarialHistoricoPropostoQuantidade);
	}

	public void setIndiceHistoricoAtualValor(Double indiceHistoricoAtualValor)
	{
		initIndiceAtual();

		initIndiceAtualHistorico();

		this.indiceAtual.getIndiceHistoricoAtual().setValor(indiceHistoricoAtualValor);
	}

	private void initIndiceAtualHistorico() {
		if (this.indiceAtual.getIndiceHistoricoAtual() == null)
			this.indiceAtual.setIndiceHistoricoAtual(new IndiceHistorico());
	}

	private void initIndiceAtual() {
		if (this.indiceAtual == null)
			this.indiceAtual = new Indice();
	}

	public void setIndiceHistoricoPropostoValor(Double indiceHistoricoPropostoValor)
	{
		initIndiceProposto();

		if (this.indiceProposto.getIndiceHistoricoAtual() == null)
			this.indiceProposto.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indiceProposto.getIndiceHistoricoAtual().setValor(indiceHistoricoPropostoValor);
	}

	public void setProjectionCargoPropostoNome(String projectionCargoPropostoNome)
	{
		initFaixaSalarialProposta();

		initFaixaSalarialPropostaCargo();

		this.faixaSalarialProposta.getCargo().setNome(projectionCargoPropostoNome);
	}

	private void initFaixaSalarialPropostaCargo() {
		if (this.faixaSalarialProposta.getCargo() == null)
			this.faixaSalarialProposta.setCargo(new Cargo());
	}

	private void initFaixaSalarialProposta() {
		if (this.faixaSalarialProposta == null)
			this.faixaSalarialProposta = new FaixaSalarial();
	}

	public void setProjectionGrupoOcupacionalPropostoId(Long projectionGrupoOcupacionalPropostoId)
	{
		initFaixaSalarialProposta();

		initFaixaSalarialPropostaCargo();

		initFaixaSalarialPropostaGrupoOcupacional();

		this.faixaSalarialProposta.getCargo().getGrupoOcupacional().setId(projectionGrupoOcupacionalPropostoId);
	}

	private void initFaixaSalarialPropostaGrupoOcupacional() {
		if (this.faixaSalarialProposta.getCargo().getGrupoOcupacional() == null)
			this.faixaSalarialProposta.getCargo().setGrupoOcupacional(new GrupoOcupacional());
	}

	public void setProjectionGrupoOcupacionalPropostoNome(String projectionGrupoOcupacionalPropostoNome)
	{
		initFaixaSalarialProposta();

		initFaixaSalarialPropostaCargo();

		initFaixaSalarialPropostaGrupoOcupacional();

		this.faixaSalarialProposta.getCargo().getGrupoOcupacional().setNome(projectionGrupoOcupacionalPropostoNome);
	}

	public void setProjectionGrupoOcupacionalAtualId(Long projectionGrupoOcupacionalAtualId)
	{
		initFaixaSalarialAtual();

		initFaixaSalarialAtualCargo();

		initFaixaSalarialAtualGrupoOcupacional();

		this.faixaSalarialAtual.getCargo().getGrupoOcupacional().setId(projectionGrupoOcupacionalAtualId);
	}

	private void initFaixaSalarialAtualGrupoOcupacional() {
		if (this.faixaSalarialAtual.getCargo().getGrupoOcupacional() == null)
			this.faixaSalarialAtual.getCargo().setGrupoOcupacional(new GrupoOcupacional());
	}

	private void initFaixaSalarialAtualCargo() {
		if (this.faixaSalarialAtual.getCargo() == null)
			this.faixaSalarialAtual.setCargo(new Cargo());
	}

	private void initFaixaSalarialAtual() {
		if (this.faixaSalarialAtual == null)
			this.faixaSalarialAtual = new FaixaSalarial();
	}

	public void setProjectionGrupoOcupacionalAtualNome(String projectionGrupoOcupacionalAtualNome)
	{
		initFaixaSalarialAtual();

		initFaixaSalarialAtualCargo();

		initFaixaSalarialAtualGrupoOcupacional();

		this.faixaSalarialAtual.getCargo().getGrupoOcupacional().setNome(projectionGrupoOcupacionalAtualNome);
	}

	public void setProjectionIndicePropostoId(Long projectionIndicePropostoId)
	{
		initIndiceProposto();

		this.indiceProposto.setId(projectionIndicePropostoId);
	}

	public void setProjectionIndicePropostoCodigoAC(String projectionIndicePropostoCodigoAC)
	{
		initIndiceProposto();

		this.indiceProposto.setCodigoAC(projectionIndicePropostoCodigoAC);
	}

	private void initIndiceProposto() {
		if (this.indiceProposto == null)
			this.indiceProposto = new Indice();
	}

	public void setProjectionIndicePropostoNome(String projectionIndicePropostoNome)
	{
		initIndiceProposto();

		this.indiceProposto.setNome(projectionIndicePropostoNome);
	}

	public void setProjectionIndiceAtualId(Long projectionIndiceAtualId)
	{
		initIndiceAtual();

		this.indiceAtual.setId(projectionIndiceAtualId);
	}

	public void setProjectionIndiceAtualNome(String projectionIndiceAtualNome)
	{
		initIndiceAtual();

		this.indiceAtual.setNome(projectionIndiceAtualNome);
	}

	public void setProjectionIndiceAtualCodigoAC(String projectionIndiceAtualcodigoAC)
	{
		initIndiceAtual();

		this.indiceAtual.setCodigoAC(projectionIndiceAtualcodigoAC);
	}

	public void setProjectionIndiceHistoricoAtualValor(Double projectionIndiceHistoricoAtualValor)
	{
		initIndiceAtual();

		initIndiceAtualHistorico();

		this.indiceAtual.getIndiceHistoricoAtual().setValor(projectionIndiceHistoricoAtualValor);
	}

	private void setIndiceHistoricoFaixaSalarialAtualValor(Double indiceHistoricoFaixaSalarialAtualValor)
	{
		initFaixaSalarialAtual();

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialAtual.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		if(this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().setValor(indiceHistoricoFaixaSalarialAtualValor);

	}

	private void setIndiceHistoricoFaixaSalarialPropostaValor(Double indiceHistoricoFaixaSalarialPropostaValor)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		if(this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().setValor(indiceHistoricoFaixaSalarialPropostaValor);
	}

	private void setIndiceHistoricoFaixaSalarialPropostaId(Long indiceHistoricoFaixaSalarialPropostaId)
	{
		initFaixaSalarialProposta();

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialProposta.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		if(this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialProposta.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().setId(indiceHistoricoFaixaSalarialPropostaId);
	}

	public void setAreaMaeId(Long areaMaeId)
	{
		if (colaborador == null)
		{
			colaborador = new Colaborador();
		}
		colaborador.setAreaOrganizacional(new AreaOrganizacional());
		colaborador.getAreaOrganizacional().setId(areaMaeId);
	}

	public Long getAreaMaeId()
	{
		if (colaborador == null)
		{
			colaborador = new Colaborador();
		}
		colaborador.setAreaOrganizacional(new AreaOrganizacional());
		return colaborador.getAreaOrganizacional().getId();
	}

	public String getAreaMaeNome()
	{
		if (colaborador == null)
		{
			colaborador = new Colaborador();
		}
		colaborador.setAreaOrganizacional(new AreaOrganizacional());
		return colaborador.getAreaOrganizacional().getNome();
	}

	public void setAreaMaeNome(String areaMaeNome)
	{
		if (colaborador == null)
		{
			colaborador = new Colaborador();
		}
		colaborador.setAreaOrganizacional(new AreaOrganizacional());
		colaborador.getAreaOrganizacional().setNome(areaMaeNome);
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("faixaSalarialAtual", this.faixaSalarialAtual).append("id", this.getId())
				.append("faixaSalarialProposta", this.faixaSalarialProposta).append("colaborador", this.colaborador).append("tabelaReajusteColaborador",
						this.tabelaReajusteColaborador).append("salarioAtual", this.salarioAtual).append("salarioProposto", this.salarioProposto).toString();
	}

	public Ambiente getAmbienteAtual()
	{
		return ambienteAtual;
	}

	public void setAmbienteAtual(Ambiente ambienteAtual)
	{
		this.ambienteAtual = ambienteAtual;
	}

	public Ambiente getAmbienteProposto()
	{
		return ambienteProposto;
	}

	public void setAmbienteProposto(Ambiente ambienteProposto)
	{
		this.ambienteProposto = ambienteProposto;
	}

	public void setProjectionAmbientePropostoId(Long ambientePropostoId)
	{
		if(this.ambienteProposto == null)
			this.ambienteProposto = new Ambiente();

		this.ambienteProposto.setId(ambientePropostoId);
	}

	public void setProjectionFuncaoPropostaId(Long funcaoPropostaId)
	{
		if(this.funcaoProposta == null)
			this.funcaoProposta = new Funcao();

		this.funcaoProposta.setId(funcaoPropostaId);
	}

	public AreaOrganizacional getAreaOrganizacionalProposta()
	{
		return areaOrganizacionalProposta;
	}

	public void setAreaOrganizacionalProposta(AreaOrganizacional areaOrganizacionalProposta)
	{
		this.areaOrganizacionalProposta = areaOrganizacionalProposta;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public String getDescricaoAreaOrganizacional()
	{
		return descricaoAreaOrganizacional;
	}

	public String getDescricaoEstabelecimentoAreaOrganizacionalPropostos()
	{
		String nome = this.getNomeDoEstabelecimentoProposto();
		String descricao = this.getDescricaoDaAreaOrganizacionalProposta();
		
		return nome + " - " + descricao;
	}

	private String getDescricaoDaAreaOrganizacionalProposta() {
		AreaOrganizacional area = this.areaOrganizacionalProposta;
		if (area != null)
			return area.getDescricao();
		return "";
	}

	private String getNomeDoEstabelecimentoProposto() {
		Estabelecimento estabelecimento = this.getEstabelecimentoProposto();
		if (estabelecimento != null)
			return estabelecimento.getNome();
		return "";
	}

	public void setDescricaoAreaOrganizacional(String descricaoAreaOrganizacional)
	{
		this.descricaoAreaOrganizacional = descricaoAreaOrganizacional;
	}

	public Funcao getFuncaoAtual()
	{
		return funcaoAtual;
	}

	public void setFuncaoAtual(Funcao funcaoAtual)
	{
		this.funcaoAtual = funcaoAtual;
	}

	public Funcao getFuncaoProposta()
	{
		return funcaoProposta;
	}

	public void setFuncaoProposta(Funcao funcaoProposta)
	{
		this.funcaoProposta = funcaoProposta;
	}

	public Double getSalarioAtual()
	{
		return SalarioUtil.getValor(this.getTipoSalarioAtual(), this.getFaixaSalarialAtual(), this.getIndiceAtual(), this.getQuantidadeIndiceAtual(),this.salarioAtual);
	}

	public String getSalarioAtualFormatado()
	{
		Double valor = SalarioUtil.getValor(this.getTipoSalarioAtual(), this.getFaixaSalarialAtual(), this.getIndiceAtual(), this.getQuantidadeIndiceAtual(),this.salarioAtual);
		NumberFormat formata = new DecimalFormat("#0.00");
		
		return formata.format(valor);
	}
	
	@NaoAudita
	public Double getSalarioProposto()
	{
		return SalarioUtil.getValor(this.getTipoSalarioProposto(), this.getFaixaSalarialProposta(), this.getIndiceProposto(), this.getQuantidadeIndiceProposto(), this.salarioProposto);
	}

	@NaoAudita
	public String getSalarioPropostoFormatado()
	{
		Double valor = SalarioUtil.getValor(this.getTipoSalarioProposto(), this.getFaixaSalarialProposta(), this.getIndiceProposto(), this.getQuantidadeIndiceProposto(), this.salarioProposto);
		NumberFormat formata = new DecimalFormat("#0.00");
		
		return formata.format(valor);
	}

	public void setSalarioAtual(Double salarioAtual)
	{
		this.salarioAtual = salarioAtual;
	}

	public void setSalarioProposto(Double salarioProposto)
	{
		this.salarioProposto = salarioProposto;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador()
	{
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public AreaOrganizacional getAreaOrganizacionalAtual()
	{
		return areaOrganizacionalAtual;
	}

	public void setAreaOrganizacionalAtual(AreaOrganizacional areaOrganizacionalAtual)
	{
		this.areaOrganizacionalAtual = areaOrganizacionalAtual;
	}

	public Estabelecimento getEstabelecimentoAtual()
	{
		return estabelecimentoAtual;
	}

	public void setEstabelecimentoAtual(Estabelecimento estabelecimentoAtual)
	{
		this.estabelecimentoAtual = estabelecimentoAtual;
	}

	public Estabelecimento getEstabelecimentoProposto()
	{
		return estabelecimentoProposto;
	}

	public void setEstabelecimentoProposto(Estabelecimento estabelecimentoProposto)
	{
		this.estabelecimentoProposto = estabelecimentoProposto;
	}
	@NaoAudita
	public String getPorcentagemDiferencaSalarial()
	{
		Double salarioProposto = this.getSalarioProposto();
		if (salarioProposto == null)
			salarioProposto =  new Double(0);
		
		Double salarioAtual = this.getSalarioAtual();
		if (salarioAtual == null)
			salarioAtual =  new Double(0);
		
		Double valor = ((salarioProposto - salarioAtual) * 100) / salarioAtual;

		NumberFormat formata = new DecimalFormat("#0.00");

		if (valor.isInfinite() || valor.isNaN())
			return "100%";

		return formata.format(valor) + "%";
	}

	@NaoAudita
	public String getDiferencaSalarial()
	{
		Double salarioProposto = this.getSalarioProposto();
		if (salarioProposto == null)
			salarioProposto =  new Double(0);
		
		Double salarioAtual = this.getSalarioAtual();
		if (salarioAtual == null)
			salarioAtual =  new Double(0);
		
		Double valor = (salarioProposto - salarioAtual);
		NumberFormat formata = new DecimalFormat("#0.00");
		
		return formata.format(valor);
	}

	public FaixaSalarial getFaixaSalarialAtual()
	{
		return faixaSalarialAtual;
	}

	public void setFaixaSalarialAtual(FaixaSalarial faixaSalarialAtual)
	{
		this.faixaSalarialAtual = faixaSalarialAtual;
	}

	public FaixaSalarial getFaixaSalarialProposta()
	{
		return faixaSalarialProposta;
	}

	public void setFaixaSalarialProposta(FaixaSalarial faixaSalarialProposta)
	{
		this.faixaSalarialProposta = faixaSalarialProposta;
	}

	public void setTipoSalarioAtual(int tipoSalarioAtual)
	{
		this.tipoSalarioAtual = tipoSalarioAtual;
	}

	public void setProjectionFaixaHistoricoAtualIndiceValor(Double faixaHistoricoAtualIndiceValor)
	{
		initFaixaSalarialAtual();

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarialAtual.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		if(this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarialAtual.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().setValor(faixaHistoricoAtualIndiceValor);
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}