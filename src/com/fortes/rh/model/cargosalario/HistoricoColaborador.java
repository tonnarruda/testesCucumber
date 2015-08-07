/* Autor: Igo Coelho
 * Data: 16/08/2006
 * Requisito: ? */
package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SalarioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"data", "colaborador_id"}))
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicocolaborador_sequence", allocationSize=1)
public class HistoricoColaborador extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;

	@ManyToOne(fetch=FetchType.LAZY)
	private Colaborador colaborador;

	private Double salario;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Transient
	private Date dataProximoHistorico;
	@Column(length=5)
	private String motivo;
	@ManyToOne
	private AreaOrganizacional areaOrganizacional;
	@ManyToOne
	private Funcao funcao;
	@Column(length=2)
    private String gfip;
	@ManyToOne
	private Ambiente ambiente;
	@ManyToOne
	private Estabelecimento estabelecimento;
	@OneToOne
	private CandidatoSolicitacao candidatoSolicitacao;

	@ManyToOne(fetch=FetchType.LAZY)
	private ReajusteColaborador reajusteColaborador;

	@ManyToOne
	private Indice indice;
	private Double quantidadeIndice;
	private int tipoSalario;
	private int status;
	
	private Integer movimentoSalarialId;
	
	@Transient private Collection<Ambiente> ambientes;
	@Transient private Collection<Funcao> funcoes;
	
	@Transient private Double salarioVariavel;
	@Transient private Double diferencaSalarialEmPorcentam;
	@Transient private String obsACPessoal;
	@Transient private Date dataSolicitacaoDesligamento;
	
	public HistoricoColaborador(String colaborador, String areaOrganizacional) {
		
		this.colaborador = new Colaborador(colaborador);
		this.areaOrganizacional = new AreaOrganizacional(areaOrganizacional);
	}
	
	public HistoricoColaborador(Long id, String colaborador, String areaOrganizacional) {
		this.setId(id);
		this.colaborador = new Colaborador(colaborador);
		this.areaOrganizacional = new AreaOrganizacional(areaOrganizacional);
	}

	public HistoricoColaborador(Long id, Double salario, Date data, String gfip, String motivo, Double quantidadeIndice, int tipoSalario, int status,
			Long empresaId, Long colaboradorId, String colaboradorNomeComercial, String colaboradorNome, String colaboradorCodigoAC, boolean colaboradorNaoIntegraAc,
			Long indiceId, String indiceNome, Double indiceHistoricoValor, Long areaId, String areaNome, Long ambienteId,
			String ambienteNome, Long funcaoId, String funcaoNome, Long faixaSalarialId, String faixaSalarialNome,
			Long cargoId, String cargoNomeMercado, String cargoNome, Long grupoId, String grupoNome, Long estabelecimentoId, String estabelecimentoNome,
			Double faixaHistoricoValor, Integer faixaHistoricoTipo, Double faixaHistoricoQuantidade, Double faixaHistoricoIndiceValor,
			String faixaCodigoAc, String areaOrganizacionalCodigoAc, String estabelecimentoCodigoAc, Integer faixaSalarialHistoricoStatus )
	{
		setId(id);
		setSalario(salario);
		setData(data);
		setGfip(gfip);
		setMotivo(motivo);
		setQuantidadeIndice(quantidadeIndice);
		setTipoSalario(tipoSalario);
		setStatus(status);
		setEmpresaId(empresaId);
		setEstabelecimentoCodigoAC(estabelecimentoCodigoAc);
		setColaboradorId(colaboradorId);
		setColaboradorNomeComercial(colaboradorNomeComercial);
		setColaboradorNome(colaboradorNome);
		setColaboradorCodigoAC(colaboradorCodigoAC);
		setProjectionColaboradorNaoIntegraAc(colaboradorNaoIntegraAc);
		setProjectionIndiceId(indiceId);
		setProjectionIndiceNome(indiceNome);
		setProjectionIndiceHistoricoValor(indiceHistoricoValor);
		setAreaId(areaId);
		setAreaOrganizacionalNome(areaNome);
		setAreaOrganizacionalCodigoAC(areaOrganizacionalCodigoAc);
		setAmbienteId(ambienteId);
		setAmbienteNome(ambienteNome);
		setFuncaoId(funcaoId);
		setFuncaoNome(funcaoNome);
		setFaixaSalarialId(faixaSalarialId);
		setFaixaSalarialNome(faixaSalarialNome);
		setFaixaCodigoAC(faixaCodigoAc);
		setCargoId(cargoId);
		setCargoNomeMercado(cargoNomeMercado);
		setCargoNome(cargoNome);
		setGrupoOcupacionalId(grupoId);
		setGrupoOcupacionalNome(grupoNome);
		setProjectionEstabelecimentoId(estabelecimentoId);
		setProjectionEstabelecimentoNome(estabelecimentoNome);
		setProjectionFaixaHistoricoValor(faixaHistoricoValor);
		setProjectionFaixaHistoricoTipo(faixaHistoricoTipo);
		setProjectionFaixaHistoricoQuantidade(faixaHistoricoQuantidade);
		setProjectionFaixaHistoricoIndiceValor(faixaHistoricoIndiceValor);
		setProjectionFaixaSalarialHistoricoStatus(faixaSalarialHistoricoStatus);
	}

	public HistoricoColaborador(Long id, Double salario, Long colaboradorId, Long areaOrganizacionalId, String areaOrganizacionalNome, Long funcaoId, Long ambienteId, Long estabelecimentoId, String colaboradorNome, String cargoNomeMercado, Long faixaSalarialId, int tipoSalario)
	{
		this.setId(id);
		this.salario = salario;
		this.tipoSalario = tipoSalario;
		
		this.setFaixaSalarialId(faixaSalarialId);
		
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);

		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaOrganizacionalId);
		this.areaOrganizacional.setNome(areaOrganizacionalNome);

		if(this.funcao == null)
			this.funcao = new Funcao();
		this.funcao.setId(funcaoId);

		if(this.ambiente == null)
			this.ambiente = new Ambiente();
		this.ambiente.setId(ambienteId);

		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
	}
	
	public HistoricoColaborador(Date dataAdmissao, Date data)
	{
		this.data = data;
		this.colaborador = new Colaborador();
		this.colaborador.setDataAdmissao(dataAdmissao);
	}
	
	//usado em relatorioColaboradorCargo
	public HistoricoColaborador(Long historicoId, Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, Date colaboradorDataAdmissao, String colaboradorCodigoAC, String vinculo, Long cargoId, String cargoNome, Long faixaId, String faixaNome, Long estabelecimentoId, String estabelecimentoNome, Long empresaId, String empresaNome, Double salario, Boolean acIntegra, int tipoSalario, Double quantidadeIndice, Indice indice, FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, IndiceHistorico indiceHistorico, Indice faixaIndice, IndiceHistorico faixaHistoricoIndice, Long areaOrganizacionalId, String areaOrganizacionaNome)
	{
		this.setId(historicoId);
		
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
		this.colaborador.setDataAdmissao(colaboradorDataAdmissao);
		this.colaborador.setCodigoAC(colaboradorCodigoAC);
		this.colaborador.getEmpresa().setAcIntegra(acIntegra);
		this.colaborador.getEmpresa().setId(empresaId);
		
		if(vinculo == null || vinculo.equals(""))
			vinculo = "E";
		Vinculo vinc = new Vinculo();
		this.colaborador.setVinculo((String) vinc.get(vinculo));
		
		this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setId(faixaId);
		this.faixaSalarial.setNome(faixaNome);

		this.faixaSalarial.setCargo(new Cargo());
		this.faixaSalarial.getCargo().setId(cargoId);
		this.faixaSalarial.getCargo().setNome(cargoNome);

		this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
		this.estabelecimento.setNome(estabelecimentoNome);
		
		this.salario = salario;
		this.tipoSalario = tipoSalario;
		this.quantidadeIndice = quantidadeIndice;
		
		this.indice = indice != null ? indice : new Indice();
		this.indice.setIndiceHistoricoAtual(indiceHistorico);

		this.faixaSalarial = faixaSalarial != null ? faixaSalarial : new FaixaSalarial();
		this.faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		if (faixaSalarialHistorico != null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().setIndice(faixaIndice);
		if(faixaIndice != null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(faixaHistoricoIndice);
		
		this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaOrganizacionalId);
		this.areaOrganizacional.setNome(areaOrganizacionaNome);
	}
	
	// usado em findByPeriodo
	public HistoricoColaborador(Long historicoId, Date historicoData, String historicoMotivo, Long colaboradorId, String colaboradorNome, String cargoNome, String faixaNome, Long estabelecimentoId, String estabelecimentoNome,
			Long areaId, String areaNome, Long areaMaeId, String areaMaeNome, int tipoSalario, Double salario, Double historicoQuantidadeIndice, Double indiceHistoricoValor, Integer faixaHistoricoTipo, Double faixaHistoricoValor, Double faixaHistoricoQuantidade, Double historicoIndiceValor, String indiceNome)
	{
		this.setId(historicoId);
		this.data = historicoData;
		this.motivo = historicoMotivo;
		
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		
		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setNome(faixaNome);

		if(this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());
		this.faixaSalarial.getCargo().setNome(cargoNome);

		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
		this.estabelecimento.setNome(estabelecimentoNome);
		
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setNome(areaNome);

		if(this.areaOrganizacional.getAreaMae() == null)
			this.areaOrganizacional.setAreaMae(new AreaOrganizacional());
		this.areaOrganizacional.getAreaMae().setId(areaMaeId);
		this.areaOrganizacional.getAreaMae().setNome(areaMaeNome);
		
		this.salario = salario;
		this.tipoSalario = tipoSalario;
		this.quantidadeIndice = historicoQuantidadeIndice;
		this.setProjectionIndiceHistoricoValor(indiceHistoricoValor);
		this.setProjectionFaixaHistoricoTipo(faixaHistoricoTipo);
		this.setProjectionFaixaHistoricoValor(faixaHistoricoValor);
		this.setProjectionFaixaHistoricoQuantidade(faixaHistoricoQuantidade);
		this.getFaixaSalarial().setHistoricoIndiceValor(historicoIndiceValor);
		
		this.setProjectionIndiceNome(indiceNome);
		
		this.colaborador.setHistoricoColaborador(this);
	}

	public HistoricoColaborador(int tipoSalario, Double salario, Double historicoQuantidadeIndice, 
			Long faixaSalarialId, Integer faixaHistoricoTipo, Double faixaHistoricoValor, Double faixaHistoricoQuantidade, Double historicoIndiceValor,
			Long indiceId, Double indiceHistoricoValor)
	{
		this.tipoSalario = tipoSalario;
		this.salario = salario;
		this.quantidadeIndice = historicoQuantidadeIndice;
		
		this.setFaixaSalarialId(faixaSalarialId);
		this.setProjectionFaixaHistoricoTipo(faixaHistoricoTipo);
		this.setProjectionFaixaHistoricoValor(faixaHistoricoValor);
		this.setProjectionFaixaHistoricoQuantidade(faixaHistoricoQuantidade);
		this.getFaixaSalarial().setHistoricoIndiceValor(historicoIndiceValor);

		this.setProjectionIndiceId(indiceId);
		this.setProjectionIndiceHistoricoValor(indiceHistoricoValor);
	}
	
	// Utilizado em findByAreaGrupoCargo
	public HistoricoColaborador(Long historicoId, Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, Date colaboradorDataAdmissao, String vinculo, Long areaId, String areaNome, Long areaMaeId, Long cargoId, String cargoNome, Long faixaId, String faixaNome, Long grupoOcupacionalId, String grupoOcupacionalNome)
	{
		this.setId(historicoId);
		
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
		this.colaborador.setDataAdmissao(colaboradorDataAdmissao);
		this.colaborador.setVinculo(vinculo);
		
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setNome(areaNome);
		
		if(this.areaOrganizacional.getAreaMae() == null)
			this.areaOrganizacional.setAreaMae(new AreaOrganizacional());
		this.areaOrganizacional.getAreaMae().setId(areaMaeId);
		
		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setId(faixaId);
		this.faixaSalarial.setNome(faixaNome);

		if(this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());
		this.faixaSalarial.getCargo().setId(cargoId);
		this.faixaSalarial.getCargo().setNome(cargoNome);

		if(this.faixaSalarial.getCargo().getGrupoOcupacional() == null)
			this.faixaSalarial.getCargo().setGrupoOcupacional(new GrupoOcupacional());
		this.faixaSalarial.getCargo().getGrupoOcupacional().setId(grupoOcupacionalId);
		this.faixaSalarial.getCargo().getGrupoOcupacional().setNome(grupoOcupacionalNome);
		
		this.colaborador.setHistoricoColaborador(this);
	}
	
	
	public HistoricoColaborador()
	{
	}
	
	@NaoAudita
	public String getCnpjFormatado()
	{
		String cnpj = this.getCnpjDaEmpresa();
		String complementoCnpj = getComplementoDoCnpj();
		
		return StringUtil.formataCnpj(cnpj, complementoCnpj);
	}

	@NaoAudita
	private String getComplementoDoCnpj() {
		if (estabelecimento != null)
			return estabelecimento.getComplementoCnpj();
		return "";
	}

	@NaoAudita
	private String getCnpjDaEmpresa() {
		if (this.colaborador != null)
			return this.colaborador.getCnpjDaEmpresa();
		return "";
	}

	public int getTipoSalario()
	{
		return tipoSalario;
	}

	public String getTipoSalarioDescricao()
	{
		return TipoAplicacaoIndice.getDescricao(tipoSalario);
	}

	public void setTipoSalario(int tipoSalario)
	{
		this.tipoSalario = tipoSalario;
	}

	public Indice getIndice()
	{
		return indice;
	}

	public void setIndice(Indice indice)
	{
		this.indice = indice;
	}

	public void setProjectionIndiceCodigoAC(String codigoAC)
	{
		if(this.indice == null)
			this.indice = new Indice();
		
		this.indice.setCodigoAC(codigoAC);
	}
	
	public Double getQuantidadeIndice()
	{
		return quantidadeIndice;
	}

	public void setQuantidadeIndice(Double quantidadeIndice)
	{
		this.quantidadeIndice = quantidadeIndice;
	}
	
	public void setAreaId(Long areaId)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setId(areaId);
	}
	
	public void setAreaOrganizacionalCodigoAC(String areaOrganizacionalCodigoAC)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		
		areaOrganizacional.setCodigoAC(areaOrganizacionalCodigoAC);
	}

	public void setProjectionIndiceId(Long projectionIndiceId)
	{
		if (this.indice == null)
			this.indice = new Indice();

		this.indice.setId(projectionIndiceId);
	}

	public void setProjectionReajusteColaboradorId(Long projectionReajusteColaboradorId)
	{
		if (this.reajusteColaborador == null)
			this.reajusteColaborador = new ReajusteColaborador();
		
		this.reajusteColaborador.setId(projectionReajusteColaboradorId);
	}
	
	public void setProjectionIndiceNome(String projectionIndiceNome)
	{
		if (this.indice == null)
			this.indice = new Indice();

		this.indice.setNome(projectionIndiceNome);
	}

	public void setProjectionIndiceHistoricoValor(Double indiceHistoricoValor)
	{
		if (this.indice == null)
			this.indice = new Indice();

		if(this.indice.getIndiceHistoricoAtual() == null)
			this.indice.setIndiceHistoricoAtual(new IndiceHistorico());

		this.indice.getIndiceHistoricoAtual().setValor(indiceHistoricoValor);
	}

	public void setProjectionEstabelecimentoNome(String projectionEstabelecimentoNome)
	{
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setNome(projectionEstabelecimentoNome);
	}

	public void setProjectionEstabelecimentoId(Long projectionEstabelecimentoId)
	{
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setId(projectionEstabelecimentoId);
	}

	public void setAreaOrganizacionalNome(String areaOrganizacionalNome)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setNome(areaOrganizacionalNome);
	}

	public void setColaboradorArea(Long areaId)
	{
		inicializaColaborador();
		if(colaborador.getAreaOrganizacional() == null)
			colaborador.setAreaOrganizacional(new AreaOrganizacional());
		colaborador.getAreaOrganizacional().setId(areaId);
	}
	
	public void setColaboradorDataAdmissao(Date colaboradorDataAdmissao)
	{
		inicializaColaborador();
		
		colaborador.setDataAdmissao(colaboradorDataAdmissao);
	}
	public void setColaboradorNomeComercial(String colaboradorNomeComercial)
	{
		inicializaColaborador();

		colaborador.setNomeComercial(colaboradorNomeComercial);
	}

	public void setColaboradorNome(String colaboradorNome)
	{
		inicializaColaborador();

		colaborador.setNome(colaboradorNome);
	}

	public void setColaboradorCodigoAC(String colaboradorCodigoAC)
	{
		inicializaColaborador();

		colaborador.setCodigoAC(colaboradorCodigoAC);
	}
	
	public void setColaboradorMatricula(String colaboradorMatricula)
	{
		inicializaColaborador();
		colaborador.setMatricula(colaboradorMatricula);
	}
	
	public void setColaboradorDesligado(Boolean colaboradorDesligado)
	{
		inicializaColaborador();
		colaborador.setDesligado(colaboradorDesligado);
	}

	private void inicializaColaborador() 
	{
		if (colaborador == null)
			colaborador = new Colaborador();
	}

	public void setProjectionColaboradorNaoIntegraAc(boolean naoIntegraAc)
	{
		inicializaColaborador();

		colaborador.setNaoIntegraAc(naoIntegraAc);
	}

	public void setColaboradorId(Long id)
	{
		inicializaColaborador();

		colaborador.setId(id);
	}

	public void setEmpresaId(Long id)
	{
		inicializaColaborador();

		if (colaborador.getEmpresa() == null)
			colaborador.setEmpresa(new Empresa());

		colaborador.getEmpresa().setId(id);
	}

	public void setEmpresaNome(String nomeEmpresa)
	{
		inicializaColaborador();
		
		if (colaborador.getEmpresa() == null)
			colaborador.setEmpresa(new Empresa());
		
		colaborador.getEmpresa().setNome(nomeEmpresa);
	}
	
	public void setAmbienteId(Long id)
	{
		if (ambiente == null)
			ambiente = new Ambiente();

		ambiente.setId(id);
	}

	public void setAmbienteNome(String nome)
	{
		if (ambiente == null)
			ambiente = new Ambiente();

		ambiente.setNome(nome);
	}

	public void setFuncaoId(Long id)
	{
		if (funcao == null)
			funcao = new Funcao();

		funcao.setId(id);
	}

	public void setFuncaoNome(String nome)
	{
		if (funcao == null)
			funcao = new Funcao();

		funcao.setNome(nome);
	}

	public void setFaixaSalarialId(Long faixaSalarialId)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		this.faixaSalarial.setId(faixaSalarialId);
	}

	public void setFaixaSalarialNome(String faixaSalarialNome)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		this.faixaSalarial.setNome(faixaSalarialNome);
	}

	public void setFaixaCodigoAC(String faixaSalarialCodigoAC)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		
		this.faixaSalarial.setCodigoAC(faixaSalarialCodigoAC);
	}

	public void setProjectionFaixaHistoricoValor(Double faixaHistoricoValor)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarial.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.faixaSalarial.getFaixaSalarialHistoricoAtual().setValor(faixaHistoricoValor);
	}

	public void setProjectionFaixaHistoricoTipo(Integer faixaHistoricoTipo)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarial.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(faixaHistoricoTipo != null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().setTipo(faixaHistoricoTipo);
	}
	
	public void setProjectionFaixaSalarialHistoricoStatus(Integer faixaSalarialHistoricoStatus)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarial.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(faixaSalarialHistoricoStatus != null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().setStatus(faixaSalarialHistoricoStatus);
	}

	public void setProjectionFaixaHistoricoQuantidade(Double faixaHistoricoQuantidade)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarial.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(faixaHistoricoQuantidade != null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().setQuantidade(faixaHistoricoQuantidade);
	}

	public void setProjectionFaixaHistoricoIndiceValor(Double faixaHistoricoIndiceValor)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getFaixaSalarialHistoricoAtual() == null)
			this.faixaSalarial.setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if (this.faixaSalarial.getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		if(this.faixaSalarial.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual() == null)
			this.faixaSalarial.getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.faixaSalarial.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().setValor(faixaHistoricoIndiceValor);
	}

	public void setFaixaSalarialDescricao(String faixaSalarialDescricao)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		this.faixaSalarial.setNome(faixaSalarialDescricao);
	}

	public void setCargoNomeMercado(String cargoNomeMercado)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());

		this.faixaSalarial.getCargo().setNomeMercado(cargoNomeMercado);
	}

	public void setCargoNome(String cargoNome)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());

		this.faixaSalarial.getCargo().setNome(cargoNome);
	}

	public void setCargoId(Long cargoId)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());

		this.faixaSalarial.getCargo().setId(cargoId);
	}

	public void setGrupoOcupacionalId(Long grupoOcupacionalId)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());

		if (this.faixaSalarial.getCargo().getGrupoOcupacional() == null)
			this.faixaSalarial.getCargo().setGrupoOcupacional(new GrupoOcupacional());

		this.faixaSalarial.getCargo().getGrupoOcupacional().setId(grupoOcupacionalId);
	}

	public void setGrupoOcupacionalNome(String grupoOcupacionalNome)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		if (this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());

		if (this.faixaSalarial.getCargo().getGrupoOcupacional() == null)
			this.faixaSalarial.getCargo().setGrupoOcupacional(new GrupoOcupacional());

		this.faixaSalarial.getCargo().getGrupoOcupacional().setNome(grupoOcupacionalNome);
	}

	public void setAreaMaeNome(String areaMaeNome)
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		if(areaOrganizacional.getAreaMae() == null)
			areaOrganizacional.setAreaMae(new AreaOrganizacional());

		areaOrganizacional.getAreaMae().setNome(areaMaeNome);
	}

	@NaoAudita
	public Double getSalarioCalculado()
	{
		return SalarioUtil.getValor(this.getTipoSalario(), this.getFaixaSalarial(), this.getIndice(), this.getQuantidadeIndice(), this.salario);
	}

	public String getDescricaoTipoSalario()
	{
		String retorno = "";

		switch (tipoSalario)
		{
			case TipoAplicacaoIndice.CARGO:
				retorno = "Por cargo";
				break;
			case TipoAplicacaoIndice.INDICE:
				retorno = "Por índice (" + this.quantidadeIndice + "x " + this.indice.getNome() + " )";
				break;
			case TipoAplicacaoIndice.VALOR:
				retorno = "Por valor";
				break;
		}

		return retorno;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Date getData()
	{
		return data;
	}
	
	@NaoAudita
	public String getDataFormatada()
	{
		String dataFormatada = "";
		if(data != null)
			dataFormatada = DateUtil.formataDiaMesAno(data);
		return dataFormatada;
	}

	@NaoAudita
	public String getMesAno()
	{
		String dataFormatada = "";
		if(data != null)
			dataFormatada = DateUtil.formataMesExtensoAno(data);
		
		return dataFormatada;
	}
	

	public void setData(Date data)
	{
		this.data = data;
	}

	public String getMotivo()
	{
		return motivo;
	}

	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}

	public void setSalario(Double salario)
	{
		this.salario = salario;
	}
	
	public void setColaboradorSolicitacaoId(Long solicitacaoId){
		inicializaColaborador();
		colaborador.setSolicitacaoId(solicitacaoId);
	}
	
	public void setColaboradorCandidatoId(Long candidatoId){
		inicializaColaborador();
		colaborador.setCandidatoId(candidatoId);
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", this.getId())
			.append("colaborador", this.colaborador)
			.append("motivo", this.motivo)
			.append("salario", this.salario)
			.append("faixaSalarial", this.faixaSalarial)
			.append("data", this.data)
			.append("funcao", this.funcao)
			.append("ambiente", this.ambiente)
			.toString();
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public String getMotivoDescricao()
	{
		if(this.motivo != null && !this.motivo.equals("") && this.motivo.length() > 3)//Coloquei o tamanho do campo para aproveitar, tela de progressão (Francisco)
			return this.motivo;

		MotivoHistoricoColaborador motivoHistoricoColaborador = new MotivoHistoricoColaborador();

		if(motivo != null && !motivo.equals(""))
			return (String) motivoHistoricoColaborador.get(motivo);

		return "Motivo Desconhecido";
	}
	
	public String getOrigemDaSituacao()
	{
		String origem = "-";
		if (StringUtils.isNotBlank(this.motivo))
		{
			if (this.motivo.equals(MotivoHistoricoColaborador.IMPORTADO))
					origem = "AC Pessoal";
			else
				origem = "Fortes RH";
		}
		
		return origem;
	}

	public Funcao getFuncao()
	{
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public String getGfip()
	{
		return gfip;
	}

	public void setGfip(String gfip)
	{
		this.gfip = gfip;
	}


	public Ambiente getAmbiente()
	{
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente)
	{
		this.ambiente = ambiente;
	}

	public Date getDataProximoHistorico()
	{
		return dataProximoHistorico;
	}

	public void setDataProximoHistorico(Date dataProximoHistorico)
	{
		this.dataProximoHistorico = dataProximoHistorico;
	}
	
	@NaoAudita
	public String getPeriodo()
	{
		return DateUtil.formataDiaMesAno(this.data) + " a " + (this.dataProximoHistorico != null ? DateUtil.formataDiaMesAno(this.dataProximoHistorico) : "__/__/___");
	}
	
	public Object clone()
	{
		try
		{
			HistoricoColaborador clone = (HistoricoColaborador) super.clone();
			clone.setFaixaSalarial((FaixaSalarial) this.faixaSalarial.clone());
			clone.setIndice((Indice) this.indice.clone());

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public void setEstabelecimentoCodigoAC(String estabelecimentoCodigoAC)
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setCodigoAC(estabelecimentoCodigoAC);
	}
	
	public void setEstabelecimentoNome(String estabelecimentoNome)
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setNome(estabelecimentoNome);
	}

	public FaixaSalarial getFaixaSalarial()
	{
		return faixaSalarial;
	}
	
	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}

	public ReajusteColaborador getReajusteColaborador()
	{
		return reajusteColaborador;
	}

	public void setReajusteColaborador(ReajusteColaborador reajusteColaborador)
	{
		this.reajusteColaborador = reajusteColaborador;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Double getSalario()
	{
		return this.salario;
	}

	public void setProjectionColaboradorCodigoAC(String projectionColaboradorCodigoAC)
	{
		inicializaColaborador();
		colaborador.setCodigoAC(projectionColaboradorCodigoAC);
	}

	public void setProjectionColaboradorDesligado(boolean desligado)
	{
		inicializaColaborador();
		colaborador.setDesligado(desligado);
	}
	
	public Collection<Ambiente> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes) {
		this.ambientes = ambientes;
	}

	public Collection<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Collection<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public Integer getMovimentoSalarialId()
	{
		return movimentoSalarialId;
	}

	public void setMovimentoSalarialId(Integer movimentoSalarialId)
	{
		this.movimentoSalarialId = movimentoSalarialId;
	}
	
	public Double getSalarioVariavel() 
	{
		if (salarioVariavel == null)
			return 0.0;
		
		return salarioVariavel;
	}

	public void setSalarioVariavel(Double salarioVariavel) 
	{
		this.salarioVariavel = salarioVariavel;
	}

	public Double getSalarioCalculadoMaisSalarioVariavel() 
	{
		return getSalarioCalculado() + getSalarioVariavel();
	}
 
	public String getObsACPessoal() 
	{
		if(obsACPessoal == null)
			return "";

		return obsACPessoal;
	}

	public void setObsACPessoal(String obsACPessoal) {
		this.obsACPessoal = obsACPessoal;
	}

	public Double getDiferencaSalarialEmPorcentam() {
		if(diferencaSalarialEmPorcentam != null)
		{
			DecimalFormat df = new DecimalFormat("0.00");
			return Double.parseDouble(df.format(diferencaSalarialEmPorcentam).replace(",", "."));
		}
		
		return diferencaSalarialEmPorcentam;
	}

	public void setDiferencaSalarialEmPorcentam(Double diferencaSalarialEmPorcentam) {
		this.diferencaSalarialEmPorcentam = diferencaSalarialEmPorcentam;
	}

	public CandidatoSolicitacao getCandidatoSolicitacao() {
		return candidatoSolicitacao;
	}

	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao) {
		this.candidatoSolicitacao = candidatoSolicitacao;
	}

	public Date getDataSolicitacaoDesligamento() {
		return dataSolicitacaoDesligamento;
	}

	public void setDataSolicitacaoDesligamento(Date dataSolicitacaoDesligamento) {
		this.dataSolicitacaoDesligamento = dataSolicitacaoDesligamento;
	}

}