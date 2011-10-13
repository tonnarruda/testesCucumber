package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "solicitacao_sequence", allocationSize = 1)
public class Solicitacao extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=100)
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Temporal(TemporalType.DATE)
	private Date dataEncerramento;
	private int quantidade;
	@Column(length=5)
	private String vinculo;
	@Column(length=5)
	private String escolaridade;
	private Double remuneracao;
	private Integer idadeMinima;
	private Integer idadeMaxima;
	private String horarioComercial = "";
	@Column(length=5)
	private String sexo;
	@Lob
	private String infoComplementares;
	private boolean encerrada;
	private boolean liberada;
	@ManyToOne
	private MotivoSolicitacao motivoSolicitacao;
	@ManyToOne
	private AreaOrganizacional areaOrganizacional;
	@ManyToOne
	private Estabelecimento estabelecimento;
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private Usuario solicitante;
	@ManyToOne
	private Usuario liberador;
	@OneToOne (mappedBy="solicitacao", fetch = FetchType.LAZY)
	private Anuncio anuncio;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Bairro> bairros;
	
	@ManyToOne
	public Cidade cidade;

	@ManyToOne
	private Empresa empresa;
	
	@Transient
	private String valorPromocao;

	private boolean suspensa;
	
	@Lob
	private String obsSuspensao;
	
	@ManyToOne
	private Avaliacao avaliacao;

	public Solicitacao()
	{

	}

	public Solicitacao(Long id, int quantidade, Date data, boolean encerrada, Double valorDoHistoricoDaFaixaSalarial, Long avaliacaoId, Long faixaSalarialId, Long idCargo, String nomeCargo, String nomeAreaOrganizacional, String nomeSolicitante)
	{
		setId(id);
		setQuantidade(quantidade);
		setData(data);
		setNomeArea(nomeAreaOrganizacional);
		setSolicitanteNome(nomeSolicitante);
		this.encerrada = encerrada;

		setAvaliacao(new Avaliacao());
		getAvaliacao().setId(avaliacaoId);
		
		setProjectionFaixaSalarialId(faixaSalarialId);
		getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());
		getFaixaSalarial().getFaixaSalarialHistoricoAtual().setValor(valorDoHistoricoDaFaixaSalarial);
		getFaixaSalarial().setCargo(new Cargo());
		getFaixaSalarial().getCargo().setId(idCargo);
		getFaixaSalarial().getCargo().setNome(nomeCargo);
	}

	public String getObsSuspensao()
	{
		return obsSuspensao;
	}

	public void setObsSuspensao(String obsSuspensao)
	{
		this.obsSuspensao = obsSuspensao;
	}

	public boolean isSuspensa()
	{
		return suspensa;
	}

	public void setSuspensa(boolean suspensa)
	{
		this.suspensa = suspensa;
	}

	public void setProjectionCidadeId(Long projectionCidadeId)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();

		this.cidade.setId(projectionCidadeId);
	}

	public void setProjectionCidadeNome(String projectionCidadeNome)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();
		
		this.cidade.setNome(projectionCidadeNome);
	}

	public void setProjectionCidadeUf(Long projectionCidadeUf)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();

		if(this.cidade.getUf() == null)
			this.getCidade().setUf(new Estado());

		this.getCidade().getUf().setId(projectionCidadeUf);
	}

	public void setProjectionCidadeUfSigla(String projectionCidadeUfSigla)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();
		
		if(this.cidade.getUf() == null)
			this.getCidade().setUf(new Estado());
		
		this.getCidade().getUf().setSigla(projectionCidadeUfSigla);
	}

	public void setProjectionMotivoSolicitacaoId(Long projectionMotivoSolicitacaoId)
	{
		if(this.motivoSolicitacao == null)
			motivoSolicitacao = new MotivoSolicitacao();

		motivoSolicitacao.setId(projectionMotivoSolicitacaoId);
	}

	public void setProjectionEstabelecimentoId(Long projectionEstabelecimentoId)
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setId(projectionEstabelecimentoId);
	}

	public void setProjectionEstabelecimentoNome(String projectionEstabelecimentoNome)
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setNome(projectionEstabelecimentoNome);
	}

	public void setProjectionMotivoSolicitacaoDescricao(String projectionMotivoSolicitacaoDescricao)
	{
		if(this.motivoSolicitacao == null)
			motivoSolicitacao = new MotivoSolicitacao();

		motivoSolicitacao.setDescricao(projectionMotivoSolicitacaoDescricao);
	}

	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(this.empresa == null)
			empresa = new Empresa();

		empresa.setId(projectionEmpresaId);
	}

	public void setProjectionEmpresaNome(String projectionEmpresaNome)
	{
		if(this.empresa == null)
			empresa = new Empresa();

		empresa.setNome(projectionEmpresaNome);
	}
	
	public void setNomeCargo(String nomeCargo)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		if(faixaSalarial.getCargo() == null)
			getFaixaSalarial().setCargo(new Cargo());

		getFaixaSalarial().getCargo().setNome(nomeCargo);
	}

	public void setProjectionFaixaSalarialCargoId(Long cargoId)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		if(faixaSalarial.getCargo() == null)
			getFaixaSalarial().setCargo(new Cargo());
		
		getFaixaSalarial().getCargo().setId(cargoId);
	}

	public void setNomeArea(String nomeArea)
	{
		if(this.areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setNome(nomeArea);
	}

	public void setCargoId(Long cargoId)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		if(faixaSalarial.getCargo() == null)
			getFaixaSalarial().setCargo(new Cargo());
		faixaSalarial.getCargo().setId(cargoId);
	}

	public void setProjectionFaixaSalarialId(Long projectionFaixaSalarialId)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		faixaSalarial.setId(projectionFaixaSalarialId);
	}

	public void setProjectionFaixaSalarialNome(String projectionFaixaSalarialNome)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		faixaSalarial.setNome(projectionFaixaSalarialNome);
	}

	public void setProjectionAreaId(Long projectionAreaId)
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setId(projectionAreaId);
	}

	public void setProjectionSolicitanteId(Long projectionSolicitanteId)
	{
		if(solicitante == null)
			solicitante = new Usuario();

		solicitante.setId(projectionSolicitanteId);
	}
	public void setSolicitanteLogin(String solicitanteLogin)
	{
		if(solicitante == null)
			solicitante = new Usuario();
		
		solicitante.setLogin(solicitanteLogin);
	}

	public void setSolicitanteNome(String solicitanteNome)
	{
		if(solicitante == null)
			solicitante = new Usuario();

		solicitante.setNome(solicitanteNome);
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public String getDataFormatada()
	{
		return DateUtil.formataDiaMesAno(this.data);
	}

	public boolean isEncerrada()
	{
		return encerrada;
	}

	public void setEncerrada(boolean encerrada)
	{
		this.encerrada = encerrada;
	}

	public String getEscolaridade()
	{
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade)
	{
		this.escolaridade = escolaridade;
	}


	public String getInfoComplementares()
	{
		return infoComplementares;
	}

	public void setInfoComplementares(String infoComplementares)
	{
		this.infoComplementares = infoComplementares;
	}

	public int getQuantidade()
	{
		return quantidade;
	}

	public void setQuantidade(int quantidade)
	{
		this.quantidade = quantidade;
	}

	public Double getRemuneracao()
	{
		return remuneracao;
	}

	public void setRemuneracao(Double remuneracao)
	{
		this.remuneracao = remuneracao;
	}

	public String getSexo()
	{
		return sexo;
	}

	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}

	public Usuario getSolicitante()
	{
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante)
	{
		this.solicitante = solicitante;
	}

	public String getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(String vinculo)
	{
		this.vinculo = vinculo;
	}
	
	public Integer getIdadeMinima() {
		return idadeMinima;
	}

	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}

	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}

	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}

	@Override
	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto.");
	   }
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append("solicitante", this.solicitante);
		string.append("data", this.data);
		string.append("areaOrganizacional", this.areaOrganizacional);
		string.append("estabelecimento", this.estabelecimento);
		string.append("idadeMinima", this.idadeMinima);
		string.append("idadeMaxima", this.idadeMaxima);
		string.append("escolaridade", this.escolaridade);
		string.append("sexo", this.sexo);
		string.append("vinculo", this.vinculo);
		string.append("remuneracao", this.remuneracao);
		string.append("quantidade", this.quantidade);
		string.append("infoComplementares", this.infoComplementares);
		string.append("encerrada", this.encerrada);

		 return string.toString();
	}

	public boolean isLiberada()
	{
		return liberada;
	}

	public void setLiberada(boolean liberada)
	{
		this.liberada = liberada;
	}

	public MotivoSolicitacao getMotivoSolicitacao()
	{
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao)
	{
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public String getDescricaoFormatada()
	{
		String nomeDoCargo = this.getNomeDoCargoDaFaixaSalarial();
		String data = DateUtil.formataDate(this.getData(),"dd/MM/yyyy");
		String descricaoDaAreaOrg = this.getDescricaoDaAreaOrganizacional();
		
		return this.descricao + " - " + nomeDoCargo +" - "+ data +" - " + descricaoDaAreaOrg;
	}

	public String getValorPromocao()
	{
		return valorPromocao;
	}

	public void setValorPromocao(String valorPromocao)
	{
		this.valorPromocao = valorPromocao;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Date getDataEncerramento()
	{
		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento)
	{
		this.dataEncerramento = dataEncerramento;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<Bairro> getBairros()
	{
		return bairros;
	}

	public void setBairros(Collection<Bairro> bairros)
	{
		this.bairros = bairros;
	}

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}

	public FaixaSalarial getFaixaSalarial()
	{
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getNomeDoCargoDaFaixaSalarial() {
		FaixaSalarial faixa = this.getFaixaSalarial();
		if (faixa != null)
			return faixa.getNomeDoCargo();
		return "";
	}
	
	private String getDescricaoDaAreaOrganizacional() {
		AreaOrganizacional area = getAreaOrganizacional();
		if (area != null)
			return area.getDescricao();
		return "";
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public void setProjectionAnuncioExibirModuloExterno(Boolean exibirModuloExterno)
	{
		if (this.anuncio == null)
			this.anuncio = new Anuncio();

		if(exibirModuloExterno != null)
			this.anuncio.setExibirModuloExterno(exibirModuloExterno);
	}

	public String getIdadeMinimaDesc() {
		return StringUtil.valueOf(this.idadeMinima);
	}

	public String getIdadeMaximaDesc() {
		return StringUtil.valueOf(this.idadeMaxima);
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public void setProjectionAvaliacaoId(Long avaliacaoId) 
	{
		inicializaAvaliacao();
		avaliacao.setId(avaliacaoId);
	}

	public void setProjectionAvaliacaoTitulo(String avaliacaoTitulo)
	{
		inicializaAvaliacao();
		avaliacao.setTitulo(avaliacaoTitulo);
	}

	private void inicializaAvaliacao() {
		if(this.avaliacao == null)
			avaliacao = new Avaliacao();
	}

	public Usuario getLiberador() {
		return liberador;
	}

	public void setLiberador(Usuario liberador) {
		this.liberador = liberador;
	}

	public String getHorarioComercial() {
		return horarioComercial;
	}

	public void setHorarioComercial(String horarioComercial) {
		this.horarioComercial = horarioComercial;
	}

}