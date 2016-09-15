/* Autor: Igo Coelho
 * Data: 06/06/2006
 * Requisito: RFA013 */
package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.ChaveDaAuditoria;
import com.fortes.security.auditoria.NaoAudita;
import com.fortes.thumb.GeradorDeThumbnailUtils;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="candidato_sequence", allocationSize=1)
public class Candidato extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=60)
	private String nome;
	@Column(length=30)
	private String senha;//variavel de senha de candidato para acesso as vagas.
	@Transient
	private String novaSenha;//variavel de senha de candidato para acesso as vagas.
	@Transient
	private String confNovaSenha;//variavel de senha de candidato para acesso as vagas.
	private File foto;
	@Embedded
	private Endereco endereco = new Endereco();
	@Embedded
	private Contato contato = new Contato();
	@Embedded
	private Pessoal pessoal = new Pessoal();
	@Embedded
	private SocioEconomica socioEconomica = new SocioEconomica();
	@Embedded
	private Habilitacao habilitacao = new Habilitacao();
	@Column(length=1)
	private String colocacao = "E";
	private Double pretencaoSalarial = 0.0;
	private boolean disponivel = true;
	@NaoAudita
	private boolean blackList = false;
    private boolean contratado = false;
    @Lob
	private String observacao;
    @NaoAudita
    @Lob
    private String observacaoBlackList;
    @Lob
    private String cursos;
    @Temporal(TemporalType.DATE)
	private Date dataCadastro;
    @Temporal(TemporalType.DATE)
    private Date dataAtualizacao = new Date();
	private char origem = OrigemCandidato.CADASTRADO;

	@ManyToMany(fetch=FetchType.LAZY, targetEntity=Cargo.class)
	private Collection<Cargo> cargos;
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=AreaInteresse.class)
	private Collection<AreaInteresse> areasInteresse;
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=Conhecimento.class)
	private Collection<Conhecimento> conhecimentos;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidato", cascade=CascadeType.ALL)
	private Collection<Formacao> formacao;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidato", cascade=CascadeType.ALL)
	private Collection<Experiencia> experiencias;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidato", cascade=CascadeType.ALL)
	private Collection<CandidatoIdioma> candidatoIdiomas;

	@ManyToOne
	private Empresa empresa;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidato", cascade=CascadeType.ALL)
	private Collection<CandidatoCurriculo> candidatoCurriculos;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidato", cascade=CascadeType.ALL)
	private Collection<CandidatoSolicitacao> candidatoSolicitacaos;
	
	@OneToOne (mappedBy="candidato", fetch = FetchType.LAZY)
	private Colaborador colaborador;

	@Lob
	private String ocrTexto;
	@Lob
	private String observacaoRH;
	@Lob
	private String examePalografico;
	
	@Transient
	private int qtdCurriculosCadastrados;
	@Transient
	private double percentualCompatibilidade;
	@Transient
	private int tempoExperiencia;
	@Transient
	private Boolean inscritoSolicitacao;
	@Transient
	private boolean jaFoiColaborador;

	@ManyToOne
	private ComoFicouSabendoVaga comoFicouSabendoVaga;
	
	private String comoFicouSabendoVagaQual;
	
	@OneToOne(fetch=FetchType.LAZY)
	private CamposExtras camposExtras;
	
	public Candidato() {}
	
	public Candidato(String empresaNome, char origem, int qtdPorOrigem) 
	{
		setEmpresaNome(empresaNome);
		this.origem = origem;
		this.qtdCurriculosCadastrados = qtdPorOrigem;
	}
	
	public Candidato(Long id, File foto) {
		setId(id);
		this.foto = foto;
	}
	
	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	//Usado no iReport
	@NaoAudita
	public String getColocacaoDic()
	{
		Vinculo vinculoMap = new Vinculo();
		return (String) vinculoMap.get(this.colocacao);
	}

	@NaoAudita
	public String getCargosDesc()
	{
		String cargosDesc = "";
		boolean pri = true;

		if(!(this.cargos == null) && !this.cargos.isEmpty())
		{
			for (Cargo cargoAux : this.cargos)
			{
				if (this.cargos.size() == 1)
					cargosDesc = cargoAux.getNomeMercado();
				else
				{
					if (pri)
					{
						cargosDesc = cargoAux.getNomeMercado();
						pri = false;
					}
					else
						cargosDesc += ", " + cargoAux.getNomeMercado();
				}
			}
		}

		return cargosDesc;
	}



//	Habilitacao
	public void setHabilitacaoNumeroHab(String habilitacaoNumeroHab)
	{
		if (habilitacao == null)
			habilitacao = new Habilitacao();
		habilitacao.setNumeroHab(habilitacaoNumeroHab);
	}
	public void setHabilitacaoRegistro(String habilitacaoRegistro)
	{
		if (habilitacao == null)
			habilitacao = new Habilitacao();
		habilitacao.setRegistro(habilitacaoRegistro);
	}
	public void setHabilitacaoEmissao(Date habilitacaoEmissao)
	{
		if (habilitacao == null)
			habilitacao = new Habilitacao();
		habilitacao.setEmissao(habilitacaoEmissao);
	}
	public void setHabilitacaoVencimento(Date habilitacaoVencimento)
	{
		if (habilitacao == null)
			habilitacao = new Habilitacao();
		habilitacao.setVencimento(habilitacaoVencimento);
	}
	public void setHabilitacaoCategoria(String habilitacaoCategoria)
	{
		if (habilitacao == null)
			habilitacao = new Habilitacao();
		habilitacao.setCategoria(habilitacaoCategoria);
	}

	//SocioEconomica
	public void setSocioEconomicaPagaPensao(boolean socioEconomicaPagaPensao)
	{
		if (socioEconomica == null)
			socioEconomica = new SocioEconomica();
		socioEconomica.setPagaPensao(socioEconomicaPagaPensao);
	}
	public void setSocioEconomicaQuantidade(int socioEconomicaQuantidade)
	{
		if (socioEconomica == null)
			socioEconomica = new SocioEconomica();
		socioEconomica.setQuantidade(socioEconomicaQuantidade);
	}
	public void setSocioEconomicaValor(Double socioEconomicaValor)
	{
		if (socioEconomica == null)
			socioEconomica = new SocioEconomica();
		socioEconomica.setValor(socioEconomicaValor);
	}
	public void setSocioEconomicaPossuiVeiculo(boolean socioEconomicaPossuiVeiculo)
	{
		if (socioEconomica == null)
			socioEconomica = new SocioEconomica();
		socioEconomica.setPossuiVeiculo(socioEconomicaPossuiVeiculo);
	}

	//Contato
	public void setContatoDdd(String contatoDdd)
	{
		if (contato == null)
			contato = new Contato();
		contato.setDdd(contatoDdd);
	}
	public void setContatoFoneFixo(String contatoFoneFixo)
	{
		if (contato == null)
			contato = new Contato();
		contato.setFoneFixo(contatoFoneFixo);
	}
	public void setContatoFoneCelular(String contatoFoneCelular)
	{
		if (contato == null)
			contato = new Contato();
		contato.setFoneCelular(contatoFoneCelular);
	}
	public void setContatoEmail(String contatoEmail)
	{
		if (contato == null)
			contato = new Contato();
		contato.setEmail(contatoEmail);
	}
	public void setNomeContato(String nomeContato)
	{
		if (contato == null)
			contato = new Contato();
		contato.setNomeContato(nomeContato);
	}

	//Pessoal
	public void setPessoalCpf(String pessoalCpf)
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setCpf(pessoalCpf);
	}
	public void setPessoalRg(String pessoalRg )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setRg(pessoalRg);
	}
	public void setPessoalRgOrgaoEmissor(String pessoalRgOrgaoEmissor)
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setRgOrgaoEmissor(pessoalRgOrgaoEmissor);
	}
	public void setPessoalRgUfId(Long pessoalRgUfId)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if(pessoal.getRgUf() == null)
			pessoal.setRgUf(new Estado());

		pessoal.getRgUf().setId(pessoalRgUfId);
	}
	public void setPessoalRgDataExpedicao(Date pessoalRgDataExpedicao)
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setRgDataExpedicao(pessoalRgDataExpedicao);
	}
	public void setPessoalNaturalidade(String pessoalNaturalidade )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setNaturalidade(pessoalNaturalidade);
	}
	public void setPessoalPai(String pessoalPai )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setPai(pessoalPai);
	}
	public void setPessoalMae(String pessoalMae )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setMae(pessoalMae);
	}
	public void setPessoalConjuge(String pessoalConjuge )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setConjuge(pessoalConjuge);
	}
	public void setPessoalProfissaoPai(String pessoalProfissaoPai )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setProfissaoPai(pessoalProfissaoPai);
	}
	public void setPessoalProfissaoMae(String pessoalProfissaoMae )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setProfissaoMae(pessoalProfissaoMae);
	}
	public void setPessoalProfissaoConjuge(String pessoalProfissaoConjuge )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setProfissaoConjuge(pessoalProfissaoConjuge);
	}
	public void setPessoalConjugeTrabalha(boolean pessoalConjugeTrabalha )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setConjugeTrabalha(pessoalConjugeTrabalha);
	}
	public void setPessoalIndicadoPor(String pessoalIndicadoPor )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setIndicadoPor(pessoalIndicadoPor);
	}
	public void setPessoalParentesAmigos(String pessoalParentesAmigos )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setParentesAmigos(pessoalParentesAmigos);
	}
	public void setPessoalQtdFilhos(int pessoalQtdFilhos )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setQtdFilhos(pessoalQtdFilhos);
	}
	public void setPessoalSexo(char pessoalSexo )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setSexo(pessoalSexo);
	}
	public void setPessoalDeficiencia(char pessoalDeficiencia)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		pessoal.setDeficiencia(pessoalDeficiencia);
	}
	public void setPessoalDataNascimento(Date pessoalDataNascimento )
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setDataNascimento(pessoalDataNascimento);
	}

	public void setPessoalEstadoCivil(String pessoalEstadoCivil)
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setEstadoCivil(pessoalEstadoCivil);
	}
	public void setPessoalTitEleitNumero(String pessoalTitEleitNumero)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getTituloEleitoral() == null)
			pessoal.setTituloEleitoral(new TituloEleitoral());

		pessoal.getTituloEleitoral().setTitEleitNumero(pessoalTitEleitNumero);
	}
	public void setPessoalTitEleitZona(String pessoalTitEleitZona)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getTituloEleitoral() == null)
			pessoal.setTituloEleitoral(new TituloEleitoral());

		pessoal.getTituloEleitoral().setTitEleitZona(pessoalTitEleitZona);
	}
	public void setPessoalTitEleitSecao(String pessoalTitEleitSecao)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getTituloEleitoral() == null)
			pessoal.setTituloEleitoral(new TituloEleitoral());

		pessoal.getTituloEleitoral().setTitEleitSecao(pessoalTitEleitSecao);
	}
	public void setPessoalCertMilNumero(String pessoalCertMilNumero)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCertificadoMilitar() == null)
			pessoal.setCertificadoMilitar(new CertificadoMilitar());

		pessoal.getCertificadoMilitar().setCertMilNumero(pessoalCertMilNumero);
	}
	public void setPessoalCertMilTipo(String pessoalCertMilTipo)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCertificadoMilitar() == null)
			pessoal.setCertificadoMilitar(new CertificadoMilitar());

		pessoal.getCertificadoMilitar().setCertMilTipo(pessoalCertMilTipo);
	}
	public void setPessoalCertMilSerie(String pessoalCertMilSerie)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCertificadoMilitar() == null)
			pessoal.setCertificadoMilitar(new CertificadoMilitar());

		pessoal.getCertificadoMilitar().setCertMilSerie(pessoalCertMilSerie);
	}
	public void setPessoalCtpsNumero(String pessoalCtpsNumero)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCtps() == null)
			pessoal.setCtps(new Ctps());

		pessoal.getCtps().setCtpsNumero(pessoalCtpsNumero);
	}
	public void setPessoalCtpsSerie(String pessoalCtpsSerie)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCtps() == null)
			pessoal.setCtps(new Ctps());

		pessoal.getCtps().setCtpsSerie(pessoalCtpsSerie);
	}
	public void setPessoalCtpsDv(Character pessoalCtpsDv)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCtps() == null)
			pessoal.setCtps(new Ctps());

		pessoal.getCtps().setCtpsDv(pessoalCtpsDv);
	}
	public void setPessoalCtpsUfId(Long pessoalCtpsUfId)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCtps() == null)
			pessoal.setCtps(new Ctps());

		if (pessoal.getCtps().getCtpsUf() == null)
			pessoal.getCtps().setCtpsUf(new Estado());

		pessoal.getCtps().getCtpsUf().setId(pessoalCtpsUfId);
	}
	public void setPessoalCtpsDataExpedicao(Date pessoalCtpsDataExpedicao)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		if (pessoal.getCtps() == null)
			pessoal.setCtps(new Ctps());

		pessoal.getCtps().setCtpsDataExpedicao(pessoalCtpsDataExpedicao);
	}
	public void setPessoalPis(String pis)
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		
		pessoal.setPis(pis);
	}

	//Endereço
	public void setEnderecoLogradouro(String enderecoLogradouro)
	{
		if (endereco == null)
			endereco = new Endereco();
		endereco.setLogradouro(enderecoLogradouro);
	}
	public void setEnderecoNumero(String enderecoNumero)
	{
		if (endereco == null)
			endereco = new Endereco();
		endereco.setNumero(enderecoNumero);
	}
	public void setEnderecoComplemento(String enderecoComplemento)
	{
		if (endereco == null)
			endereco = new Endereco();
		endereco.setComplemento(enderecoComplemento);
	}
	public void setEnderecoBairro(String enderecoBairro)
	{
		if (endereco == null)
			endereco = new Endereco();
		endereco.setBairro(enderecoBairro);
	}
	public void setEnderecoUfId(Long enderecoUfId)
	{
		if (endereco == null)
			endereco = new Endereco();

		if(endereco.getUf() == null)
			endereco.setUf(new Estado());

		endereco.getUf().setId(enderecoUfId);
	}
	public void setEnderecoUfSigla(String enderecoUfSigla)
	{
		if (endereco == null)
			endereco = new Endereco();
		
		if(endereco.getUf() == null)
			endereco.setUf(new Estado());
		
		endereco.getUf().setSigla(enderecoUfSigla);
	}
	public void setEnderecoCidadeId(Long enderecoCidadeId)
	{
		if (endereco == null)
			endereco = new Endereco();

		if(endereco.getCidade() == null)
			endereco.setCidade(new Cidade());

		endereco.getCidade().setId(enderecoCidadeId);
	}
	public void setEnderecoCidadeNome(String enderecoCidadeNome)
	{
		if (endereco == null)
			endereco = new Endereco();

		if(endereco.getCidade() == null)
			endereco.setCidade(new Cidade());

		endereco.getCidade().setNome(enderecoCidadeNome);
	}
	public void setEnderecoCep(String enderecoCep)
	{
		if (endereco == null)
			endereco = new Endereco();
		endereco.setCep(enderecoCep);
	}

	public void setCandidatoCidade(String candidatoCidade)
	{
		if (endereco == null)
			endereco = new Endereco();
		if (endereco.getCidade() == null)
			endereco.setCidade(new Cidade());
		endereco.getCidade().setNome(candidatoCidade);
	}

	public void setPessoalEscolaridade(String pessoalEscolaridade)
	{
		if (pessoal == null)
			pessoal = new Pessoal();

		pessoal.setEscolaridade(pessoalEscolaridade);
	}

	public void setSigla(String sigla)
	{
		if (endereco == null)
			endereco = new Endereco();
		if (endereco.getUf() == null)
			endereco.setUf(new Estado());
		endereco.getUf().setSigla(sigla);
	}

	@ChaveDaAuditoria
	public String getNome()
	{
		return nome;
	}
	@NaoAudita
	public String getNomeCpf()
	{
		String retorno;

		retorno = nome;

		if(pessoal != null && pessoal.getCpf() != null && !pessoal.getCpf().equals(""))
			retorno += " (CPF " + pessoal.getCpf() + ") ";

		return retorno;
	}
	@NaoAudita
	public String getNomeECpf()
	{
		String nomeCpf = "";
		if(nome != null)
			nomeCpf = nome;
		if(pessoal != null && StringUtils.isNotBlank(pessoal.getCpf()))
			nomeCpf += " - " + pessoal.getCpfFormatado();

		return nomeCpf;
	}
	
	@NaoAudita
	public String getNomeCpfEmpresa()
	{
		String nomeEmpresaCpf = "";
		
		if (empresa != null)
			nomeEmpresaCpf = empresa.getNome() + " - ";
		if(nome != null)
			nomeEmpresaCpf += nome;
		if(pessoal != null && StringUtils.isNotBlank(pessoal.getCpf()))
			nomeEmpresaCpf += " - " + pessoal.getCpfFormatado();
		
		return nomeEmpresaCpf;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}
	@NaoAudita
	public File getFoto() {
		return foto;
	}

	public void setFoto(File foto) {
		this.foto = foto;
	}

	public Collection<AreaInteresse> getAreasInteresse()
	{
		return areasInteresse;
	}

	public void setAreasInteresse(Collection<AreaInteresse> areasInteresse)
	{
		this.areasInteresse = areasInteresse;
	}
	
	public String getConhecimentosDescricao()
	{
		CollectionUtil<Conhecimento> util = new CollectionUtil<Conhecimento>();
		return StringUtil.converteArrayToString(util.convertCollectionToArrayString(conhecimentos, "getNome"));
	}

	public Collection<Conhecimento> getConhecimentos()
	{
		return conhecimentos;
	}

	public void setConhecimentos(Collection<Conhecimento> conhecimentos)
	{
		this.conhecimentos = conhecimentos;
	}

	public Contato getContato()
	{
		return contato;
	}

	public void setContato(Contato contato)
	{
		this.contato = contato;
	}

	public boolean isContratado()
	{
		return contratado;
	}

	public void setContratado(boolean contratado)
	{
		this.contratado = contratado;
	}

	public String getCursos()
	{
		return cursos;
	}

	public void setCursos(String cursos)
	{
		this.cursos = cursos;
	}

	public boolean isDisponivel()
	{
		return disponivel;
	}

	public void setDisponivel(boolean disponivel)
	{
		this.disponivel = disponivel;
	}

	public Endereco getEndereco()
	{
		return endereco;
	}

	public void setEndereco(Endereco endereco)
	{
		this.endereco = endereco;
	}

	public Collection<Experiencia> getExperiencias()
	{
		return experiencias;
	}

	public void setExperiencias(Collection<Experiencia> experiencias)
	{
		this.experiencias = experiencias;
	}

	public Collection<Formacao> getFormacao()
	{
		return formacao;
	}

	public void setFormacao(Collection<Formacao> formacao)
	{
		this.formacao = formacao;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public Pessoal getPessoal()
	{
		return pessoal;
	}

	public void setPessoal(Pessoal pessoal)
	{
		this.pessoal = pessoal;
	}

	public Double getPretencaoSalarial()
	{
		return pretencaoSalarial;
	}

	public void setPretencaoSalarial(Double pretencaoSalarial)
	{
		this.pretencaoSalarial = pretencaoSalarial;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos)
	{
		this.cargos = cargos;
	}

	public Collection<CandidatoIdioma> getCandidatoIdiomas()
	{
		return candidatoIdiomas;
	}

	public void setCandidatoIdiomas(Collection<CandidatoIdioma> candidatoIdiomas)
	{
		this.candidatoIdiomas = candidatoIdiomas;
	}

	public Date getDataAtualizacao()
	{
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao)
	{
		this.dataAtualizacao = dataAtualizacao;
	}
	@NaoAudita
	public char getOrigem()
	{
		return origem;
	}

	public String getOrigemDescricao()
	{
		return (String) (new OrigemCandidato().get(getOrigem()));
	}

	public void setOrigem(char origem)
	{
		this.origem = origem;
	}

	public boolean isBlackList()
	{
		return blackList;
	}

	public void setBlackList(boolean blackList)
	{
		this.blackList = blackList;
	}

	public String getObservacaoBlackList()
	{
		return observacaoBlackList;
	}

	public void setObservacaoBlackList(String observacaoBlackList)
	{
		this.observacaoBlackList = observacaoBlackList;
	}

	public Habilitacao getHabilitacao() {
		return habilitacao;
	}

	public void setHabilitacao(Habilitacao habilitacao) {
		this.habilitacao = habilitacao;
	}

	public void setCpf(String cpf)
	{
		if (pessoal == null)
			pessoal = new Pessoal();
		pessoal.setCpf(cpf);
	}

	public void setFoneFixo(String foneFixo)
	{
		if (contato == null)
			contato = new Contato();
		contato.setFoneFixo(foneFixo);
	}

	public void setFoneCelular(String foneCelular)
	{
		if (contato == null)
			contato = new Contato();
		contato.setFoneCelular(foneCelular);
	}
	
	public void setEmail (String email)
	{
		if (contato == null)
			contato = new Contato();
		contato.setEmail(email);
	}

	public void setDdd(String ddd)
	{
		if (contato == null)
			contato = new Contato();
		contato.setDdd(ddd);
	}

	public void setEmpresaId(Long empresaId)
	{
		prepareProjectionEmpresa();
		empresa.setId(empresaId);
	}

	public void setEmpresaNome(String empresaNome)
	{
		prepareProjectionEmpresa();
		empresa.setNome(empresaNome);
	}
	
	public void setProjectionEmpresaNome(String empresaNome)
	{
		prepareProjectionEmpresa();
		empresa.setNome(empresaNome);
	}

	private void prepareProjectionEmpresa()
	{
		if (empresa == null)
			empresa = new Empresa();
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public String getNovaSenha()
	{
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha)
	{
		this.novaSenha = novaSenha;
	}

	public String getConfNovaSenha()
	{
		return confNovaSenha;
	}

	public void setConfNovaSenha(String confNovaSenha)
	{
		this.confNovaSenha = confNovaSenha;
	}


	public SocioEconomica getSocioEconomica()
	{
		return socioEconomica;
	}

	public void setSocioEconomica(SocioEconomica socioEconomica)
	{
		this.socioEconomica = socioEconomica;
	}
	public String getOcrTexto()
	{
		return ocrTexto;
	}

	public void setOcrTexto(String ocrTexto)
	{
		this.ocrTexto = ocrTexto;
	}

	public Collection<CandidatoCurriculo> getCandidatoCurriculos()
	{
		return candidatoCurriculos;
	}

	public void setCandidatoCurriculos(Collection<CandidatoCurriculo> candidatoCurriculos)
	{
		this.candidatoCurriculos = candidatoCurriculos;
	}

	public String getObservacaoRH()
	{
		return observacaoRH;
	}

	public void setObservacaoRH(String observacaoRH)
	{
		this.observacaoRH = observacaoRH;
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append("nome", this.nome);
		string.append("contato", this.contato);
		string.append("pretencaoSalarial", this.pretencaoSalarial);
		string.append("habilitacao", this.habilitacao);
		string.append("endereco", this.endereco);
		string.append("disponivel", this.disponivel);
		string.append("pessoal", this.pessoal);
		string.append("socioEconomica", this.socioEconomica);
		string.append("colocacao", this.colocacao);
		string.append("blackList", this.blackList);
		string.append("dataAtualizacao", this.dataAtualizacao);
		string.append("origem", this.origem);
		string.append("contratado", this.contratado);

		return string.toString();
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos()
	{
		return candidatoSolicitacaos;
	}

	public void setCandidatoSolicitacaos(Collection<CandidatoSolicitacao> candidatoSolicitacaos)
	{
		this.candidatoSolicitacaos = candidatoSolicitacaos;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	@NaoAudita
	public String getDataCadastroFormatada() {
		if (this.dataCadastro != null)
			return DateUtil.formataDate(this.dataCadastro, "dd/MM/yyyy");
		else
			return "";
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * Converte foto original do candidato para thumbnail (miniatura).
	 */
	public void converteFotoParaThumbnail() {
		boolean possuiFoto = possuiFoto();
		if (possuiFoto) {
			GeradorDeThumbnailUtils thumbnailUtils = new GeradorDeThumbnailUtils();
			java.io.File thumb = thumbnailUtils.gera(getCaminhoDaFoto());
			foto.setBytes(FileUtil.getFileBytes(thumb));
			foto.setSize(thumb.length());
			foto.setContentType(thumbnailUtils.getContentType());
		}
	}

	private boolean possuiFoto() {
		return getFoto() != null;
	}

	private String getCaminhoDaFoto() {
		return this.getFoto()
			.getFileArchive().getAbsolutePath()
			.replace("\\", "/").replace("%20", " ");
	}

	public void setNomeDaFoto(String nomeDafoto) {
		instanciaFotoSeNecessario();
		foto.setName(nomeDafoto);
	}
	public void setContentTypeDaFoto(String contentType) {
		instanciaFotoSeNecessario();
		foto.setContentType(contentType);
	}
	public void setSizeDaFoto(Long size) {
		instanciaFotoSeNecessario();
		foto.setSize(size);
	}
	public void setBytesDaFoto(byte[] bytes) {
		instanciaFotoSeNecessario();
		foto.setBytes(bytes);
	}
	

	private void instanciaFotoSeNecessario() {
		if (foto == null)
			foto = new File();
	}

	public int getQtdCurriculosCadastrados() {
		return qtdCurriculosCadastrados;
	}

	public String getColocacao() {
		return colocacao;
	}

	public void setColocacao(String colocacao) {
		this.colocacao = colocacao;
	}

	public ComoFicouSabendoVaga getComoFicouSabendoVaga() {
		return comoFicouSabendoVaga;
	}

	public void setComoFicouSabendoVaga(ComoFicouSabendoVaga comoFicouSabendoVaga) {
		this.comoFicouSabendoVaga = comoFicouSabendoVaga;
	}

	public String getComoFicouSabendoVagaQual() {
		return comoFicouSabendoVagaQual;
	}

	public void setComoFicouSabendoVagaQual(String comoFicouSabendoVagaQual) {
		this.comoFicouSabendoVagaQual = comoFicouSabendoVagaQual;
	}

	public CamposExtras getCamposExtras() {
		return camposExtras;
	}

	public void setCamposExtras(CamposExtras camposExtras) {
		this.camposExtras = camposExtras;
	}

	public void setExamePalografico(String examePalografico) {
		if (examePalografico != null)
			examePalografico = examePalografico.replaceAll("[iI]","|").replaceAll("[-_~¯ˉ˗─]","-");
		
		this.examePalografico = examePalografico;
	}

	public String getExamePalografico() {
		return examePalografico;
	}

	public String getResultadoExamePalografico() {
		if (examePalografico != null)
		{
			StringBuffer buffer = new StringBuffer();
			
			String[] partes =  examePalografico.replaceAll("[\n\r]","").replaceAll("[iI]","|").split("[-_~¯ˉ˗─]");
			
			for (int i = 0; i < partes.length; i++)
			{
				buffer.append((i+1) + "ª sequência: " + partes[i].length() + " caracteres - " + partes[i] + "\n");
			}
			
			return buffer.toString().trim();
		}
		
		return null;
	}

	public double getPercentualCompatibilidade() {
		return percentualCompatibilidade;
	}

	public void setPercentualCompatibilidade(double percentualCompatibilidade) {
		this.percentualCompatibilidade = percentualCompatibilidade;
	}

	public int getTempoExperiencia() {
		return tempoExperiencia;
	}

	public void setTempoExperiencia(int tempoExperiencia) {
		this.tempoExperiencia = tempoExperiencia;
	}

	public Boolean getInscritoSolicitacao() {
		return inscritoSolicitacao;
	}

	public void setInscritoSolicitacao(Boolean inscritoSolicitacao) {
		this.inscritoSolicitacao = inscritoSolicitacao;
	}

	public boolean isJaFoiColaborador() {
		return jaFoiColaborador;
	}

	public void setJaFoiColaborador(boolean jaFoiColaborador) {
		this.jaFoiColaborador = jaFoiColaborador;
	}
	
	public void setCamposExtrasId(Long camposExtrasId) {
		if(camposExtras == null)
			camposExtras = new CamposExtras();
		
		this.camposExtras.setId(camposExtrasId);
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
}