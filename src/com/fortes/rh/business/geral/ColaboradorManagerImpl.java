package com.fortes.rh.business.geral;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import remprot.RPClient;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.DuracaoPreenchimentoVagaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.security.AuditoriaManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CertificadoMilitar;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.TituloEleitoral;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.dicionario.Entidade;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.EscolaridadeACPessoal;
import com.fortes.rh.model.dicionario.FormulaTurnover;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.relatorio.CartaoAcompanhamentoExperienciaVO;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.geral.relatorio.TaxaDemissao;
import com.fortes.rh.model.geral.relatorio.TaxaDemissaoCollection;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.MathUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;
import com.fortes.web.tags.CheckBox;

@SuppressWarnings("unchecked")
public class ColaboradorManagerImpl extends GenericManagerImpl<Colaborador, ColaboradorDao> implements ColaboradorManager
{
	private FormacaoManager formacaoManager;
	private ExperienciaManager experienciaManager;
	private ColaboradorIdiomaManager colaboradorIdiomaManager;
	private CandidatoManager candidatoManager;
	private MensagemManager mensagemManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private Mail mail;
	private PlatformTransactionManager transactionManager;
	private DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AcPessoalClientColaborador acPessoalClientColaborador;
	private EmpresaManager empresaManager;
	private EstabelecimentoManager estabelecimentoManager;
	private BairroManager bairroManager;
	private CidadeManager cidadeManager;
	private IndiceManager indiceManager;
	private FaixaSalarialManager faixaSalarialManager;
	private EstadoManager estadoManager;
	private CamposExtrasManager camposExtrasManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private SolicitacaoManager solicitacaoManager;
	private AuditoriaManager auditoriaManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private SolicitacaoExameManager solicitacaoExameManager;
	
	private static final String RETIRAFOTO = "S";
	private static final String NAORETIRAFOTO = "N";

	public void enviaEmailAniversariantes(Collection<Empresa> empresas) throws Exception
	{
		Date data = new Date();
		int dia = DateUtil.getDia(data);
		int mes = DateUtil.getMes(data);

		char barra = java.io.File.separatorChar;
		String path = ArquivoUtil.getSystemConf().getProperty("sys.path").trim();
		path = path + barra + "WEB-INF" + barra + "report" + barra; 

		Map<String,Object> parametros = new HashMap<String, Object>();
		parametros.put("SUBREPORT_DIR", path);

		for (Empresa empresa : empresas) 
		{
			String pathBackGroundRelatorio = "";

			String pathLogo = ArquivoUtil.getPathLogoEmpresa() + empresa.getImgAniversarianteUrl();
			java.io.File logo = new java.io.File(pathLogo);
			if(logo.exists())
				pathBackGroundRelatorio = pathLogo;

			parametros.put("BACKGROUND", pathBackGroundRelatorio);

			Collection<Colaborador> aniversariantes = getDao().findAniversariantesByEmpresa(empresa.getId(), dia, mes);
			for (Colaborador aniversariante : aniversariantes)
			{
				parametros.put("MSG", empresa.getMensagemCartaoAniversariante().replaceAll("#NOMECOLABORADOR#", aniversariante.getNome()));					
				String subject = "Feliz Aniversário " + aniversariante.getNome();
				String body = "Cartão em anexo, feliz aniversário!";

				Collection<Colaborador> colaboradores = Arrays.asList(new Colaborador());
				DataSource[] files = ArquivoUtil.montaRelatorio(parametros, colaboradores, "cartaoAniversariante.jasper");

				if(StringUtils.isNotEmpty(aniversariante.getContato().getEmail())){
					mail.send(empresa, subject, files, body, aniversariante.getContato().getEmail());		
				}
			}
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<Colaborador> findByAreasOrganizacionalIds(Long[] idsLong)
	{
		return getDao().findByAreaOrganizacionalIds(idsLong);
	}

	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario)
	{
		return getDao().findSemUsuarios(empresaId, usuario);
	}

	@SuppressWarnings("rawtypes")
	public Integer getCount(Map parametros)
	{
		return getDao().getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	}

	@SuppressWarnings("rawtypes")
	public Collection<Colaborador> findList(int page, int pagingSize, Map parametros)
	{
		return getDao().findList(page, pagingSize, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	}

	public Collection<Colaborador> findByAreasOrganizacionalIds(Integer page, Integer pagingSize, Long[] areasIds, Long[] cargosIds, Long[] estabelecimentosIds, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean comHistColaboradorFuturo, boolean somenteDesligados)
	{
		return getDao().findByAreaOrganizacionalIds(page, pagingSize, areasIds, cargosIds, estabelecimentosIds, colaborador, dataAdmissaoIni, dataAdmissaoFim, empresaId, comHistColaboradorFuturo, somenteDesligados);
	}

	public Colaborador findColaboradorPesquisa(Long id, Long empresaId)
	{
		return getDao().findColaboradorPesquisa(id, empresaId);
	}

	public boolean insert(Colaborador colaborador, Double salarioColaborador, Long idCandidato, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Solicitacao solicitacao, Empresa empresa) throws Exception
	{
		colaborador.setUsuario(null);

		if (colaborador.getPessoal().getCtps().getCtpsUf().getId() == null)
			colaborador.getPessoal().getCtps().setCtpsUf(null);

		if (colaborador.getPessoal().getRgUf().getId() == null)
			colaborador.getPessoal().setRgUf(null);

		if (colaborador.getSolicitacao() != null && colaborador.getSolicitacao().getId() == null)
			colaborador.setSolicitacao(null);

		colaborador.setEmpresa(empresa);

		if(idCandidato != null)
			getDao().setCandidatoNull(idCandidato);
			
		save(colaborador);

		// Inicia historico do colaborador
		HistoricoColaborador historico = new HistoricoColaborador();
		historico.setColaborador(colaborador);
		historico.setMotivo(MotivoHistoricoColaborador.CONTRATADO);

		if (!colaborador.isNaoIntegraAc() && empresa.isAcIntegra())
			historico.setStatus(StatusRetornoAC.AGUARDANDO);
		else
			historico.setStatus(StatusRetornoAC.CONFIRMADO);

		setDadosHistoricoColaborador(historico, colaborador);

		historico = historicoColaboradorManager.ajustaTipoSalario(historico, colaborador.getHistoricoColaborador().getTipoSalario(), colaborador
				.getHistoricoColaborador().getIndice(), colaborador.getHistoricoColaborador().getQuantidadeIndice(), salarioColaborador);

		historico = historicoColaboradorManager.save(historico);

		// Caso seja a contratação de um candidato
		if (idCandidato != null)
		{
			candidatoManager.updateSetContratado(idCandidato, empresa.getId());
			experienciaManager.removeCandidato(new Candidato(idCandidato, null));//vai ser salvo logo abaixo com id de candidato e colaborador, fazendo compartilhamento das experiências.

			if (solicitacao.getId() != null)
			{
				colaborador.setSolicitacao(solicitacao);
				configuracaoNivelCompetenciaManager.criaCNCColaboradorByCNCCnadidato(colaborador, idCandidato, solicitacao, historico);
			}
		}

		formacaoManager.removeColaborador(colaborador);
		colaboradorIdiomaManager.removeColaborador(colaborador);
		experienciaManager.removeColaborador(colaborador);

		saveDetalhes(colaborador, formacaos, idiomas, experiencias);

		salvarBairro(colaborador);

		// Flush necessário quando houver uma operação com banco/sistema externo.
		// garante que erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if (!colaborador.isNaoIntegraAc() && empresa.isAcIntegra())
			contratarColaboradorNoAC(colaborador, historico, empresa, true);

		gerenciadorComunicacaoManager.enviaAvisoContratacao(historico);

		return true;
	}

	private TEmpregado bindEmpregado(Colaborador colaborador, String empresaCodigoAC)
	{
		TEmpregado empregado = new TEmpregado();
		empregado.setId(colaborador.getId().intValue());
		empregado.setMatricula(colaborador.getMatricula());
		empregado.setEmpresaCodigoAC(empresaCodigoAC);
		empregado.setNome(colaborador.getNome());
		empregado.setNomeComercial(colaborador.getNomeComercial());
		empregado.setCodigoAC(colaborador.getCodigoAC());

		bindEnderecoEmpregado(colaborador, empregado);

		empregado.setCpf(colaborador.getPessoal().getCpf());
		empregado.setPis(colaborador.getPessoal().getPis());
		empregado.setSexo(String.valueOf(colaborador.getPessoal().getSexo()));
		if (colaborador.getPessoal().getDataNascimento() != null)
			empregado.setDataNascimento(DateUtil.formataDiaMesAno(colaborador.getPessoal().getDataNascimento()));

		bindEscolaridadeEmpregado(colaborador, empregado);

		empregado.setEstadoCivil(colaborador.getPessoal().getEstadoCivil());
		if (colaborador.getDataAdmissao() != null)
			empregado.setDataAdmissao(DateUtil.formataDiaMesAno(colaborador.getDataAdmissao()));

		empregado.setConjuge(colaborador.getPessoal().getConjuge());
		empregado.setPai(colaborador.getPessoal().getPai());
		empregado.setMae(colaborador.getPessoal().getMae());
		empregado.setDeficiencia(Character.toString(colaborador.getPessoal().getDeficiencia()));

		empregado.setDdd(colaborador.getContato().getDdd());
		empregado.setFoneFixo(colaborador.getContato().getFoneFixo());
		empregado.setFoneCelular(colaborador.getContato().getFoneCelular());
		empregado.setEmail(colaborador.getContato().getEmail());
		
		if(colaborador.getFoto() != null && colaborador.getFoto().getBytes() != null && !"".equals(colaborador.getFoto())){
			byte[] encode = Base64.encodeBase64(colaborador.getFoto().getBytes());
			empregado.setFoto(new String(encode));
			empregado.setRetiraFoto(NAORETIRAFOTO);
		}else if(!colaborador.isManterFoto())
			empregado.setRetiraFoto(RETIRAFOTO);

		bindIdentidadeEmpregado(colaborador, empregado);
		bindTituloEleitoralEmpregado(colaborador, empregado);
		bindCertificadoMilitarEmpregado(colaborador, empregado);
		bindHabilitacaoEmpregado(colaborador, empregado);
		bindCtpsEmpregado(colaborador, empregado);
		bindVinculoEmpregado(colaborador, empregado);

		return empregado;
	}

	private void bindEnderecoEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		empregado.setLogradouro(colaborador.getEndereco().getLogradouro());
		empregado.setNumero(colaborador.getEndereco().getNumero());
		empregado.setComplemento(colaborador.getEndereco().getComplemento());
		empregado.setBairro(colaborador.getEndereco().getBairro());
		empregado.setCep(colaborador.getEndereco().getCep().equals("") ? "" : colaborador.getEndereco().getCep());
		
		if (colaborador.getEndereco().getCidade().getId() != null)
		{
			Cidade cidade = cidadeManager.findByIdProjection(colaborador.getEndereco().getCidade().getId());
			empregado.setCidadeCodigoAC(cidade.getCodigoAC());
			empregado.setUfSigla(cidade.getUf().getSigla());
		}
	}
	
	private void bindEscolaridadeEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		if (colaborador.getPessoal() != null && colaborador.getPessoal().getEscolaridade() != null)
		{
			if (colaborador.getPessoal().getEscolaridade().equals("08") || colaborador.getPessoal().getEscolaridade().equals("09"))//tecnico no rh
				empregado.setEscolaridade("07");//colegial completo no ac
			else if (colaborador.getPessoal().getEscolaridade().equals("10"))//superior em andamento no rh
				empregado.setEscolaridade("08");//superior em andamento no ac
			else if (colaborador.getPessoal().getEscolaridade().equals("11") || colaborador.getPessoal().getEscolaridade().equals("12"))// superior completo / especializacao no rh
				empregado.setEscolaridade("09");// superior completo no ac
			else if (colaborador.getPessoal().getEscolaridade().equals("13"))// mestrado no rh
				empregado.setEscolaridade("10");// mestrado no ac
			else if (colaborador.getPessoal().getEscolaridade().equals("14"))//doutorrado no rh
				empregado.setEscolaridade("11");//doutorrado no ac
			else
				empregado.setEscolaridade(colaborador.getPessoal().getEscolaridade());
		}
	}
	
	private void bindIdentidadeEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		empregado.setIdentidadeNumero(colaborador.getPessoal().getRg());
		empregado.setIdentidadeOrgao(colaborador.getPessoal().getRgOrgaoEmissor());
		
		if (colaborador.getPessoal().getRgDataExpedicao() != null)
			empregado.setIdentidadeDataExpedicao(DateUtil.formataDiaMesAno(colaborador.getPessoal().getRgDataExpedicao()));

		if (colaborador.getPessoal().getRgUf() != null && colaborador.getPessoal().getRgUf().getId() != null)
		{
			Estado estado = estadoManager.findById(colaborador.getPessoal().getRgUf().getId());
			empregado.setIdentidadeUF(estado.getSigla());
		}
	}
	
	private void bindTituloEleitoralEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		if (colaborador.getPessoal().getTituloEleitoral() != null) 
		{
			if (colaborador.getPessoal().getTituloEleitoral().getTitEleitNumero() != null)
				empregado.setTituloNumero(colaborador.getPessoal().getTituloEleitoral().getTitEleitNumero());
			if (colaborador.getPessoal().getTituloEleitoral().getTitEleitSecao() != null)
				empregado.setTituloSecao(colaborador.getPessoal().getTituloEleitoral().getTitEleitSecao());
			if (colaborador.getPessoal().getTituloEleitoral().getTitEleitZona() != null)
				empregado.setTituloZona(colaborador.getPessoal().getTituloEleitoral().getTitEleitZona());
		}
	}
	
	private void bindCertificadoMilitarEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		if (colaborador.getPessoal().getCertificadoMilitar() != null) 
		{
			if (colaborador.getPessoal().getCertificadoMilitar().getCertMilNumero() != null)
				empregado.setCertificadoMilitarNumero(colaborador.getPessoal().getCertificadoMilitar().getCertMilNumero());
			if (colaborador.getPessoal().getCertificadoMilitar().getCertMilSerie() != null)
				empregado.setCertificadoMilitarSerie(colaborador.getPessoal().getCertificadoMilitar().getCertMilSerie());
			if (colaborador.getPessoal().getCertificadoMilitar().getCertMilTipo() != null)
				empregado.setCertificadoMilitarTipo(colaborador.getPessoal().getCertificadoMilitar().getCertMilTipo());
		}
	}
	
	private void bindHabilitacaoEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		if (colaborador.getHabilitacao() != null) 
		{
			if (colaborador.getHabilitacao().getNumeroHab() != null)
				empregado.setHabilitacaoNumero(colaborador.getHabilitacao().getNumeroHab());
			if (colaborador.getHabilitacao().getEmissao() != null)
				empregado.setHabilitacaoEmissao(DateUtil.formataDiaMesAno(colaborador.getHabilitacao().getEmissao()));
			if (colaborador.getHabilitacao().getVencimento() != null)
				empregado.setHabilitacaoVencimento(DateUtil.formataDiaMesAno(colaborador.getHabilitacao().getVencimento()));
			if (colaborador.getHabilitacao().getCategoria() != null)
				empregado.setHabilitacaoCategoria(colaborador.getHabilitacao().getCategoria());
		}
	}
	
	private void bindCtpsEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		if (colaborador.getPessoal().getCtps() != null) 
		{
			if (colaborador.getPessoal().getCtps().getCtpsNumero() != null)
				empregado.setCtpsNumero(colaborador.getPessoal().getCtps().getCtpsNumero());
			if (colaborador.getPessoal().getCtps().getCtpsSerie() != null)
				empregado.setCtpsSerie(colaborador.getPessoal().getCtps().getCtpsSerie());
			if (colaborador.getPessoal().getCtps().getCtpsDv() != null)
				empregado.setCtpsDV(Character.toString(colaborador.getPessoal().getCtps().getCtpsDv()));
			if (colaborador.getPessoal().getCtps().getCtpsDataExpedicao() != null)
				empregado.setCtpsDataExpedicao(DateUtil.formataDiaMesAno(colaborador.getPessoal().getCtps().getCtpsDataExpedicao()));
			if (colaborador.getPessoal().getCtps().getCtpsUf() != null && colaborador.getPessoal().getCtps().getCtpsUf().getId() != null)
			{
				Estado estado = estadoManager.findById(colaborador.getPessoal().getCtps().getCtpsUf().getId());
				empregado.setCtpsUFSigla(estado.getSigla());
			}
		}
	}
	
	private void bindVinculoEmpregado(Colaborador colaborador, TEmpregado empregado) 
	{
		if (colaborador.getVinculo().equals(Vinculo.ESTAGIO))
			empregado.setTipoAdmissao("00");
		else 
		{
			if (colaborador.getVinculo().equals(Vinculo.TEMPORARIO))
				empregado.setVinculo(50);
			else
			{
				if (colaborador.getVinculo().equals(Vinculo.APRENDIZ)){
					empregado.setVinculo(55);
					empregado.setCategoria(07);
				}
				else {
					empregado.setTipoAdmissao("20");
					empregado.setVinculo(10);
				}
			}
		}
	}

	public String getVinculo(String admissaoTipo, Integer admissaoVinculo, Integer admissaoCategoria) {
		String colocacao;
		
		if (admissaoTipo!=null && admissaoTipo.equals("00"))
			colocacao = Vinculo.ESTAGIO;
		else if (admissaoVinculo!=null && (admissaoVinculo == 50 || admissaoVinculo == 60 || admissaoVinculo == 65 || admissaoVinculo == 70 || admissaoVinculo == 75 || admissaoVinculo == 90))
			colocacao = Vinculo.TEMPORARIO;
		else {
				if (admissaoCategoria!=null && admissaoCategoria == 7) {
					colocacao = Vinculo.APRENDIZ;
				} else {
					colocacao = Vinculo.EMPREGO;
				}
			}
		
		return colocacao;
	}

	public void contratarColaboradorNoAC(Colaborador colaborador, HistoricoColaborador historico, Empresa empresa, boolean enviarEmailContratacao) throws AddressException, MessagingException,Exception
	{
		historico.setAreaOrganizacional(areaOrganizacionalManager.findAreaOrganizacionalCodigoAc(historico.getAreaOrganizacional().getId()));
		historico.setEstabelecimento(estabelecimentoManager.findEstabelecimentoCodigoAc(historico.getEstabelecimento().getId()));
		historico.setFaixaSalarial(faixaSalarialManager.findCodigoACById(historico.getFaixaSalarial().getId()));
		colaborador.getEndereco().setCidade(cidadeManager.findById(colaborador.getEndereco().getCidade().getId()));

		// busca codigoAC
		if (historico.getTipoSalario() == TipoAplicacaoIndice.INDICE)
			historico.setIndice(indiceManager.getCodigoAc(historico.getIndice()));

		boolean contratou = acPessoalClientColaborador.contratar(bindEmpregado(colaborador, empresa.getCodigoAC()), historicoColaboradorManager.bindSituacao(historico, empresa.getCodigoAC()), empresa);
		if (contratou)
		{
			try
			{
				if(enviarEmailContratacao)
					gerenciadorComunicacaoManager.enviarEmailContratacaoColaborador(colaborador.getNome(), empresa);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//ta mó GAMBI, futrique muito não (chico barroso)
			String strException = "Não foi possível contratar este colaborador no Fortes Pessoal.";
			Throwable cause = new Throwable(strException);
			Exception exception = new Exception(strException, cause );
			throw exception;
		}
	}

	private void setDadosHistoricoColaborador(HistoricoColaborador historico, Colaborador colaborador)
	{
		historico.setObsACPessoal(colaborador.getHistoricoColaborador().getObsACPessoal());
		historico.setData(colaborador.getHistoricoColaborador().getData());
		historico.setAreaOrganizacional(colaborador.getHistoricoColaborador().getAreaOrganizacional());
		historico.setFaixaSalarial(colaborador.getHistoricoColaborador().getFaixaSalarial());
		historico.setEstabelecimento(colaborador.getHistoricoColaborador().getEstabelecimento());
		historico.setGfip(colaborador.getHistoricoColaborador().getGfip());

		historico.setAmbiente(colaborador.getHistoricoColaborador().getAmbiente());
		historico.setFuncao(colaborador.getHistoricoColaborador().getFuncao());

		if (colaborador.getHistoricoColaborador().getAmbiente() != null && (colaborador.getHistoricoColaborador().getAmbiente().getId() == null
				|| colaborador.getHistoricoColaborador().getAmbiente().getId() == -1))
			historico.setAmbiente(null);

		if (colaborador.getHistoricoColaborador().getFuncao() != null && (colaborador.getHistoricoColaborador().getFuncao().getId() == null
				|| colaborador.getHistoricoColaborador().getFuncao().getId() == -1))
			historico.setFuncao(null);

	}

	public void update(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias,	Empresa empresa, boolean editarHistorico, Double salarioColaborador) throws Exception
	{
		verificaEntidadeIdNulo(colaborador);
		colaborador.setEmpresa(empresa);
		
		update(colaborador);
		salvarBairro(colaborador);

		formacaoManager.removeColaborador(colaborador);
		colaboradorIdiomaManager.removeColaborador(colaborador);
		experienciaManager.removeColaborador(colaborador);

		saveDetalhes(colaborador, formacaos, idiomas, experiencias);

		HistoricoColaborador historicoColaborador = null;
		if (editarHistorico)
		{
			historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId());
			setDadosHistoricoColaborador(historicoColaborador, colaborador);
			historicoColaborador = historicoColaboradorManager.ajustaTipoSalario(historicoColaborador, colaborador.getHistoricoColaborador()
					.getTipoSalario(), colaborador.getHistoricoColaborador().getIndice(), colaborador.getHistoricoColaborador().getQuantidadeIndice(),
					salarioColaborador);

			historicoColaboradorManager.update(historicoColaborador);
			colaborador.setHistoricoColaborador(historicoColaborador);
		}

		// Flush necessário quando houver uma operação com banco/sistema externo.
		// garante que erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if (!colaborador.isNaoIntegraAc() && empresa.isAcIntegra())
		{
			if (editarHistorico)// deleta o registro na CTT do AC e cria um novo
				contratarColaboradorNoAC(colaborador, historicoColaborador, empresa, true);
			else
				acPessoalClientColaborador.atualizar(bindEmpregado(colaborador, empresa.getCodigoAC()), empresa);
		}
		
		replicaUpdateCandidato(findAllRelacionamentos(colaborador.getId()), idiomas);
	}

	/**Replica as alterações do colaborador no candidato */
	private void replicaUpdateCandidato(Colaborador colaborador, Collection<CandidatoIdioma> idiomas) 
	{
		if (colaborador.getCandidato() != null && colaborador.getCandidato().getId() != null && colaborador.getCandidato().getId() > 0) {
			
			if (idiomas != null && !idiomas.isEmpty()) {
				List<ColaboradorIdioma> colaboradorIdiomas = new ArrayList<ColaboradorIdioma>();
				for (CandidatoIdioma candidatoIdioma : idiomas) {
					ColaboradorIdioma candidatoIdiomaTemp = new ColaboradorIdioma();
					candidatoIdiomaTemp.setId(null);
					candidatoIdiomaTemp.setColaborador(colaborador);
					candidatoIdiomaTemp.setIdioma(candidatoIdioma.getIdioma());
					candidatoIdiomaTemp.setNivel(candidatoIdioma.getNivel());
					colaboradorIdiomas.add(candidatoIdiomaTemp);
				}
				colaborador.setColaboradorIdiomas(colaboradorIdiomas);
			}
			candidatoManager.saveOrUpdateCandidatoByColaborador(colaborador);
		}
	}

	private void verificaEntidadeIdNulo(Colaborador colaborador) 
	{
		if (colaborador.getUsuario() != null && colaborador.getUsuario().getId() == null)
			colaborador.setUsuario(null);

		if (colaborador.getPessoal().getRgUf() != null && colaborador.getPessoal().getRgUf().getId() == null)
			colaborador.getPessoal().setRgUf(null);

		if (colaborador.getPessoal().getCtps() != null && colaborador.getPessoal().getCtps().getCtpsUf() != null
				&& colaborador.getPessoal().getCtps().getCtpsUf().getId() == null)
			colaborador.getPessoal().getCtps().setCtpsUf(null);

		if(colaborador.getSolicitacao() != null && colaborador.getSolicitacao().getId() == null)
			colaborador.setSolicitacao(null);
	}

	private void salvarBairro(Colaborador colaborador)
	{
		Bairro bairro = new Bairro();
		bairro.setNome(colaborador.getEndereco().getBairro());
		bairro.setCidade(colaborador.getEndereco().getCidade());

		if (bairro.getNome() != null && !bairro.getNome().trim().equals("") && bairro.getCidade().getId() != null)
		{
			if (!bairroManager.existeBairro(bairro))
				bairroManager.save(bairro);
		}
	}

	public void saveDetalhes(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias)
	{
		if (formacaos != null && !formacaos.isEmpty())
			for (Formacao f : formacaos){
				f.setId(null);
				f.atualizaColaboradorECandidato(colaborador);

				formacaoManager.save(f);
			}

		if (idiomas != null && !idiomas.isEmpty())
			for (CandidatoIdioma c : idiomas){
				ColaboradorIdioma ci = new ColaboradorIdioma();
				ci.setId(null);
				ci.setColaborador(colaborador);
				ci.setIdioma(c.getIdioma());
				ci.setNivel(c.getNivel());

				colaboradorIdiomaManager.save(ci);
			}

		if (experiencias != null && !experiencias.isEmpty())
			for (Experiencia e : experiencias){
				e.setId(null);
				e.atualizaColaboradorECandidato(colaborador);

				if (!e.possuiCargo())
					e.setCargo(null);

				experienciaManager.save(e);
			}
	}

	public void setColaboradorIdiomaManager(ColaboradorIdiomaManager colaboradorIdiomaManager)
	{
		this.colaboradorIdiomaManager = colaboradorIdiomaManager;
	}

	public void setExperienciaManager(ExperienciaManager experienciaManager)
	{
		this.experienciaManager = experienciaManager;
	}

	public void setFormacaoManager(FormacaoManager formacaoManager)
	{
		this.formacaoManager = formacaoManager;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public ParametrosDoSistemaManager getParametrosDoSistemaManager()
	{
		return parametrosDoSistemaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Colaborador findByUsuario(Usuario usuario, Long empresaId)
	{
		return getDao().findByUsuario(usuario, empresaId);
	}

	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId)
	{
		return getDao().findColaboradorUsuarioByCpf(cpf, empresaId);
	}

	public void enviaEmailEsqueciMinhaSenha(Colaborador colaborador, Empresa empresa)
	{

		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findAll().toArray()[0];
		String link = parametrosDoSistema.getAppUrl();
		String subject = "Reenvio de senha.";
		String senha = StringUtil.decodeString(colaborador.getUsuario().getSenha());
		String nomeUsuario = colaborador.getNomeComercial();

		StringBuilder body = new StringBuilder();
		body.append("Sr(a) " + nomeUsuario + ", <br>");
		body.append("sua senha do sistema RH é: " + senha + "<br>");
		body.append("Acesse o RH em:<br>");
		body.append("<a href='" + link + "'>RH</a>");

		try
		{
			mail.send(empresa, subject, body.toString(), null, colaborador.getContato().getEmail());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String recuperaSenha(String cpf, Empresa empresa)
	{
		String mensagem;
		Colaborador colaborador = findColaboradorUsuarioByCpf(cpf, empresa.getId());

		// VERIFICA SE JA EXISTE UM USUARIO CADASTRADO PARA O COLABORADOR.
		// O USUARIO DO COLABORADOR É CRIADO QUANDO ELE RESPONDE O EMAIL DE CADASTRO.
		if (colaborador != null && colaborador.getUsuario() != null && StringUtils.isNotBlank(colaborador.getUsuario().getSenha())
				&& StringUtils.isNotBlank(colaborador.getContato().getEmail()))
		{
			empresa = empresaManager.findById(empresa.getId());
			enviaEmailEsqueciMinhaSenha(colaborador, empresa);
			mensagem = "Sua senha foi enviada para seu E-mail.";
		}
		else if (colaborador == null || StringUtils.isBlank(colaborador.getContato().getEmail()))
		{
			mensagem = "Caro(a) Sr(a), não identificamos um endereço de e-mail associado ao seu usuário.";
		}
		else
		{
			mensagem = "Caro(a) Sr(a), não identificamos uma senha associada ao seu cpf na empresa selecionada.";
		}

		return mensagem;
	}

	public boolean candidatoEhColaborador(Long candidatoId, Long empresaId)
	{
		return findByCandidato(candidatoId, empresaId) != null;
	}

	public Colaborador findByCandidato(Long candidatoId, Long empresaId)
	{
		return getDao().findbyCandidato(candidatoId, empresaId);
	}

	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds)
	{
		return getDao().findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentoIds);
	}

	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds)
	{
		return getDao().findByAreasOrganizacionaisEstabelecimentos(areasOrganizacionaisIds, estabelecimentoIds, null, null);
	}

	public Colaborador findByCodigoAC(String codigoAC, Empresa empresa)
	{
		return getDao().findByCodigoAC(codigoAC, empresa);
	}

	public Colaborador findColaboradorById(Long id)
	{
		return getDao().findColaboradorById(id);
	}

	public Colaborador findAllRelacionamentos(Long id){
		return getDao().findColaboradorComTodosOsDados(id);
	}
	
	public boolean setCodigoColaboradorAC(String codigo, Long id, Empresa empresa)
	{
		return getDao().setCodigoColaboradorAC(codigo, id);
	}

	public Collection<Colaborador> findByArea(AreaOrganizacional areaFiltro)
	{
		return getDao().findByArea(areaFiltro);
	}

	public DuracaoPreenchimentoVagaManager getDuracaoPreenchimentoVagaManager()
	{
		return duracaoPreenchimentoVagaManager;
	}

	public void setDuracaoPreenchimentoVagaManager(DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager)
	{
		this.duracaoPreenchimentoVagaManager = duracaoPreenchimentoVagaManager;
	}

	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId)
	{
		return getDao().findByFuncaoAmbiente(funcaoId, ambienteId);
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setAcPessoalClientColaborador(AcPessoalClientColaborador acPessoalClientColaborador)
	{
		this.acPessoalClientColaborador = acPessoalClientColaborador;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId)
	{
		return getDao().findByAreaEstabelecimento(areaOrganizacionalId, estabelecimentoId);
	}

	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargosIds, Collection<Long> estabelecimentosIds)
	{
		return getDao().findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds, null, null);
	}

	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds)
	{
		return getDao().findByEstabelecimento(estabelecimentoIds);
	}

	public void setBairroManager(BairroManager bairroManager)
	{
		this.bairroManager = bairroManager;
	}

	public Colaborador findByIdProjectionUsuario(Long colaboradorId)
	{
		return getDao().findByIdProjectionUsuario(colaboradorId);
	}

	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, CamposExtras camposExtras, String order, Date dataAdmissaoIni, Date dataAdmissaoFim, String sexo, String deficiencia, Integer[] tempoServicoIni, Integer[] tempoServicoFim, String situacao, Character enviadoParaAC, Long... empresasIds)
	{
		return getDao().findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentosIds, areasIds, cargosIds, camposExtras, order, dataAdmissaoIni, dataAdmissaoFim, sexo, deficiencia, tempoServicoIni, tempoServicoFim, situacao, enviadoParaAC, empresasIds);
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public Colaborador findColaboradorByIdProjection(Long colaboradorId)
	{
		return getDao().findColaboradorByIdProjection(colaboradorId);
	}

	public void atualizarUsuario(Long colaboradorId, Long usuarioId) throws Exception
	{
		Long colaboradorIdAntigo = findByUsuario(usuarioId);
		if (colaboradorId == null)
		{
			if (colaboradorIdAntigo != null)
				getDao().atualizarUsuario(colaboradorIdAntigo, null);
		}
		else if (!colaboradorId.equals(colaboradorIdAntigo))
		{
			if (colaboradorIdAntigo != null)
				getDao().atualizarUsuario(colaboradorIdAntigo, null);

			if (colaboradorId != null)
				getDao().atualizarUsuario(colaboradorId, usuarioId);
		}
	}

	public Colaborador findByIdProjectionEmpresa(Long colaboradorId)
	{
		return getDao().findByIdProjectionEmpresa(colaboradorId);
	}

	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String agruparPor, String vinculo) throws Exception
	{
		Collection<Colaborador> colaboradors = getDao().findColaboradoresMotivoDemissao(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, agruparPor, vinculo);
		if (colaboradors == null || colaboradors.isEmpty())
			throw new Exception("Não existem dados para o filtro informado.");

		return colaboradors;
	}

	public Collection<MotivoDemissaoQuantidade> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds,
			Date dataIni, Date dataFim, String vinculo) throws Exception
			{
		List<Object[]> lista = getDao().findColaboradoresMotivoDemissaoQuantidade(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, vinculo);

		if (lista == null || lista.isEmpty())
			throw new Exception("Não existem dados para o filtro informado.");

		Collection<MotivoDemissaoQuantidade> motivos = new ArrayList<MotivoDemissaoQuantidade>();
		for (Object[] item : lista)
		{
			if(item[0] == null)
				motivos.add(new MotivoDemissaoQuantidade("[não informado]", descricaoTurnover(false), getDao().countSemMotivos(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, vinculo)));
			else
				motivos.add(new MotivoDemissaoQuantidade((String) item[0], descricaoTurnover((Boolean) item[1]), (Integer) item[2]));
		}

		CollectionUtil<MotivoDemissaoQuantidade> util = new CollectionUtil<MotivoDemissaoQuantidade>();

		// Ordena por quantidade 
		motivos = util.sortCollectionDesc(motivos, "quantidade");

		return motivos;
			}

	private String descricaoTurnover(Boolean motivo){
		if (motivo != null && motivo) {
			return "Sim";
		} else {
			return "Não";
		}
	}
	public Collection<Colaborador> getColaboradoresByEstabelecimentoAreaGrupo(char filtrarPor, Collection<Long> estabelecimentosIds, Collection<Long> areasIds,
			Collection<Long> cargosIds, String colaboradorNome, Long empresaId)
			{
		Collection<Colaborador> colaboradores = null;
		if (filtrarPor == '1')// filtrar por Area Organizacional
			colaboradores = getDao().findByAreasOrganizacionaisEstabelecimentos(areasIds, estabelecimentosIds, colaboradorNome, empresaId);
		else
			colaboradores = getDao().findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds, colaboradorNome, empresaId);

		return colaboradores;
			}

	public void solicitacaoDesligamentoAc(Date dataSolicitacaoDesligamento, String observacaoDemissao, Long motivoId, Character gerouSubstituicao, Long colaboradorId, Empresa empresa) throws Exception, IntegraACException 
	{
		Collection<HistoricoColaborador> historicosColaborador = new ArrayList<HistoricoColaborador>();
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaboradorId);
		historicoColaborador.setDataSolicitacaoDesligamento(dataSolicitacaoDesligamento);
		historicoColaborador.setObsACPessoal(observacaoDemissao);
		historicosColaborador.add(historicoColaborador);

		TFeedbackPessoalWebService tFeedbackPessoalWebService = acPessoalClientColaborador.solicitacaoDesligamentoAc(historicosColaborador, empresa); 
		
		if(tFeedbackPessoalWebService != null && tFeedbackPessoalWebService.getSucesso())
		{
			getDao().atualizaSolicitacaoDesligamento(null, dataSolicitacaoDesligamento, null, null, null, null, colaboradorId);
			desligaColaborador(null, null, observacaoDemissao, motivoId, gerouSubstituicao, false, empresa.isAcIntegra(), colaboradorId);
		}else{
			throw new IntegraACException("Colaborador não encontrado no Fortes Pessoal");
		}
	}

	public void solicitacaoDesligamento(Date dataSolicitacaoDesligamento, String observacaoDemissao, Long motivoId, Character gerouSubstituicao, Long solicitanteDemissaoId, Long colaboradorId) throws Exception 
	{
		getDao().atualizaSolicitacaoDesligamento(dataSolicitacaoDesligamento, null, observacaoDemissao, motivoId, gerouSubstituicao, solicitanteDemissaoId, colaboradorId);
	}
	
	public Collection<Colaborador> listColaboradorComDataSolDesligamentoAC(Long empresaId){
		return getDao().listColaboradorComDataSolDesligamentoAC(empresaId);
	}

	public void desligaColaborador(Boolean desligado, Date dataDesligamento, String observacaoDemissao, Long motivoDemissaoId, Character gerouSubstituicao, boolean desligaByAC, boolean integradoAC, Long... colaboradoresIds) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			@SuppressWarnings("deprecation")
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
			if(integradoAC)
				usuarioManager.desativaAcessoSistema(colaboradoresIds);
			else
				usuarioManager.removeAcessoSistema(colaboradoresIds);
			
			candidatoManager.updateDisponivelAndContratadoByColaborador(true, false, colaboradoresIds);
			candidatoSolicitacaoManager.setStatusByColaborador(StatusCandidatoSolicitacao.INDIFERENTE, colaboradoresIds);

			if(desligaByAC)
				historicoColaboradorManager.deleteHistoricosAguardandoConfirmacaoByColaborador(colaboradoresIds);
			else {
				if(!integradoAC)
					removeVinculos(colaboradoresIds);
				getDao().desligaColaborador(desligado, dataDesligamento, observacaoDemissao, motivoDemissaoId, gerouSubstituicao, colaboradoresIds);
			}
			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}

	private void removeVinculos(Long... colaboradoresIds){
		try {
			areaOrganizacionalManager.desvinculaResponsaveis(colaboradoresIds);
			mensagemManager.removerMensagensViculadasByColaborador(colaboradoresIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void religaColaborador(Long colaboradorId) throws Exception
	{
		UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
		usuarioManager.reativaAcessoSistema(colaboradorId);
		candidatoManager.updateDisponivelAndContratadoByColaborador(false, true, colaboradorId);
		candidatoSolicitacaoManager.setStatusByColaborador(StatusCandidatoSolicitacao.APROMOVER, colaboradorId);
		getDao().religaColaborador(colaboradorId);
	}

	public Collection<Colaborador> findColaboradoresByCodigoAC(Long empresaId, boolean joinComHistorico, String... codigosACColaboradores)
	{
		return getDao().findColaboradoresByCodigoAC(empresaId, joinComHistorico, codigosACColaboradores);
	}

	public boolean desligaColaboradorAC(Empresa empresa, Date dataDesligamento, String... codigosACColaboradores) throws FortesException, Exception
	{
		Collection<Colaborador> colaboradores = getDao().findColaboradoresByCodigoAC(empresa.getId(), false, codigosACColaboradores);
		
		try {
			if(codigosACColaboradores.length != colaboradores.size())
				throw new FortesException("Desligar Empregado: Existe(m) empregado(s) que não se encontra(m) no sistema RH.");
			
			Long[] colaboradoresIds = new CollectionUtil<Colaborador>().convertCollectionToArrayIds(colaboradores);
			desligaColaborador(true, dataDesligamento, "", null, null, true, true, colaboradoresIds);
			removeVinculos(colaboradoresIds);
			auditoriaManager.auditaConfirmacaoDesligamentoNoAC(colaboradores, dataDesligamento, empresa);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return getDao().desligaByCodigo(empresa, dataDesligamento, codigosACColaboradores);
	}

	public Long religaColaboradorAC(String codigoAC, String empresaCodigo, String grupoAC)
	{
		Long colaboradorId = getDao().findByCodigoAC(codigoAC, empresaCodigo, grupoAC).getId();

		candidatoManager.updateDisponivelAndContratadoByColaborador(false, true, colaboradorId);
		mensagemManager.removeMensagensColaborador(colaboradorId, TipoMensagem.INFO_FUNCIONAIS);
		getDao().religaColaborador(colaboradorId);

		return colaboradorId;
	}


	public Collection<Colaborador> findProjecaoSalarial(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds,
			Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId) throws Exception
			{
		Collection<Colaborador> colaboradorByHistoricoColaboradors = getDao().findProjecaoSalarialByHistoricoColaborador(data, estabelecimentoIds, areaIds,
				grupoIds, cargoIds, filtro, empresaId);

		if (tabelaReajusteColaboradorId != null)
		{
			Collection<Colaborador> colaboradorByTabelaReajusteColaboradors = getDao().findProjecaoSalarialByTabelaReajusteColaborador(
					tabelaReajusteColaboradorId, data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresaId);

			Map<Object, Object> mapColaboradorByTabelaReajusteColaboradors = CollectionUtil.convertCollectionToMap(colaboradorByTabelaReajusteColaboradors,
					"getId");

			if (colaboradorByTabelaReajusteColaboradors != null && !colaboradorByTabelaReajusteColaboradors.isEmpty())
			{
				for (Colaborador colaborador : colaboradorByHistoricoColaboradors)
				{
					Colaborador colaboradorByMap = (Colaborador) mapColaboradorByTabelaReajusteColaboradors.get(colaborador.getId());

					if (colaboradorByMap != null)
					{
						colaborador.setEstabelecimento(colaboradorByMap.getEstabelecimento());
						colaborador.setAreaOrganizacional(colaboradorByMap.getAreaOrganizacional());
						colaborador.setFaixaSalarial(colaboradorByMap.getFaixaSalarial());
						colaborador.setHistoricoColaborador(colaboradorByMap.getHistoricoColaborador());
						colaborador.setEhProjecao(true);
					}
				}
			}
		}

		colaboradorByHistoricoColaboradors = setFamiliaAreasOrder(empresaId, colaboradorByHistoricoColaboradors);

		return colaboradorByHistoricoColaboradors;
	}

	private Collection<Colaborador> setFamiliaAreasOrder(Long empresaId, Collection<Colaborador> colaboradorByHistoricoColaboradors) throws Exception
	{
		// monta familia das areas
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
		areas = areaOrganizacionalManager.montaFamilia(areas);

		for (Colaborador colaborador : colaboradorByHistoricoColaboradors)
		{
			colaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areas, colaborador.getAreaOrganizacional().getId()));
		}

		CollectionUtil<Colaborador> cutil = new CollectionUtil<Colaborador>();
		colaboradorByHistoricoColaboradors = cutil.sortCollectionStringIgnoreCase(colaboradorByHistoricoColaboradors, "descricaoEstabelecimentoAreaOrganizacional");

		return colaboradorByHistoricoColaboradors;
	}

	public Collection<Colaborador> ordenaPorEstabelecimentoArea(Collection<Colaborador> colaboradors, Long... empresasIds) throws Exception
	{
		// monta familia das areas
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresasIds);
		areas = areaOrganizacionalManager.montaFamilia(areas);
		
		for (Colaborador colaboradorTmp : colaboradors) {
			colaboradorTmp.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areas, colaboradorTmp.getAreaOrganizacional().getId()));
		}
		
		CollectionUtil<Colaborador> cu1 = new CollectionUtil<Colaborador>();
		colaboradors = cu1.sortCollectionStringIgnoreCase(colaboradors, "descricaoEmpresaEstabelecimentoAreaOrganizacional");
		
		return colaboradors;
	}
	
	public void getRemuneracaoVariavelFromAcPessoalByColaboradores(Collection<Colaborador> colaboradores) throws Exception 
	{
		ArrayList<String> colaboradoresIdsList = new ArrayList<String>();
		ArrayList<Long> empresasIdsList = new ArrayList<Long>();
		
		for (Colaborador colaborador : colaboradores) 
		{			
			if (colaborador.getEmpresa().isAcIntegra() && colaborador.getCodigoAC() != null && !colaborador.getCodigoAC().equals("") && !colaborador.isNaoIntegraAc())
			{
				colaboradoresIdsList.remove(colaborador.getCodigoAC().toString());
				colaboradoresIdsList.add(colaborador.getCodigoAC().toString());
				
				Long empresaId = colaborador.getEmpresa().getId();
				if (empresaId != null && !empresasIdsList.contains(empresaId))
					empresasIdsList.add(empresaId);
			}
		}
		
		String[] colaboradoresIds = new String[colaboradoresIdsList.size()];
		colaboradoresIds = colaboradoresIdsList.toArray(colaboradoresIds);
		
		if (colaboradoresIdsList.size() != 0)
		{
			List<TRemuneracaoVariavel> remuneracoesVariaveisList = new ArrayList<TRemuneracaoVariavel>();
			Empresa empresa;
			TRemuneracaoVariavel[] remuneracoesVariaveisTemp;

			for (Long empresaId : empresasIdsList)
			{
				empresa = empresaManager.findById(empresaId);
				remuneracoesVariaveisTemp = acPessoalClientColaborador.getRemuneracoesVariaveis(empresa, colaboradoresIds, DateUtil.formataAnoMes(new Date()), DateUtil.formataAnoMes(new Date())); 
				CollectionUtil<TRemuneracaoVariavel> util = new CollectionUtil<TRemuneracaoVariavel>();
				remuneracoesVariaveisList.addAll(util.convertArrayToCollection(remuneracoesVariaveisTemp));
			}
						
			for (TRemuneracaoVariavel remuneracaoVariavel : remuneracoesVariaveisList)
			{
				for (Colaborador colaborador : colaboradores) 
				{
					if (colaborador.getCodigoAC() != null && !colaborador.getCodigoAC().equals(""))
					{
						if (colaborador.getCodigoAC().equals(remuneracaoVariavel.getCodigoEmpregado()))
						{
							colaborador.getHistoricoColaborador().setSalarioVariavel(remuneracaoVariavel.getValor());
							colaborador.getHistoricoColaborador().setMensalidade(remuneracaoVariavel.getMensalidade());
							break;
						}
					}
				}
			}
		}
	}

	public void verificaColaboradoresSemCodigoAC(Collection<ReajusteColaborador> reajustes) throws Exception
	{
		StringBuilder colaboradoresSemCodigoAC = new StringBuilder();

		for (ReajusteColaborador reajusteColaborador : reajustes)
		{
			if (!reajusteColaborador.getColaborador().isNaoIntegraAc()
					&& StringUtils.isBlank(reajusteColaborador.getColaborador().getCodigoAC()))
			{
				colaboradoresSemCodigoAC.append(reajusteColaborador.getColaborador().getNomeComercial() + ", ");
			}
		}

		if (colaboradoresSemCodigoAC.length() > 0)
		{
			colaboradoresSemCodigoAC = colaboradoresSemCodigoAC.replace(colaboradoresSemCodigoAC.length() - 2, colaboradoresSemCodigoAC.length(), ".");
			throw new ColecaoVaziaException("O reajuste não pode ser aplicado enquanto os cadastros destes colaboradores não for concluído no Fortes Pessoal:<br>"
					+ colaboradoresSemCodigoAC);
		}
	}

	public void verificaColaboradoresDesligados(Collection<ReajusteColaborador> reajustes) throws Exception
	{
		StringBuilder colaboradoresDesligados = new StringBuilder();

		for (ReajusteColaborador reajusteColaborador : reajustes)
		{
			if (reajusteColaborador.getColaborador().getDataDesligamento() != null && reajusteColaborador.getColaborador().getDataDesligamento().before(reajusteColaborador.getTabelaReajusteColaborador().getData()))
			{
				colaboradoresDesligados.append(reajusteColaborador.getColaborador().getNomeComercial() + ", ");
			}
		}

		if (colaboradoresDesligados.length() > 0)
		{
			colaboradoresDesligados = colaboradoresDesligados.replace(colaboradoresDesligados.length() - 2, colaboradoresDesligados.length(), ".");
			throw new ColecaoVaziaException("O reajuste não pode ser aplicado pois os seguintes colaboradores estão desligados da empresa:<br>" + colaboradoresDesligados);
		}
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void respondeuEntrevista(Long colaboradorId)
	{
		getDao().setRespondeuEntrevista(colaboradorId);
	}

	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula)
	{
		return getDao().setMatriculaColaborador(empresaId, codigoAC, matricula);
	}

	public boolean setMatriculaColaborador(Long colaboradorId, String matricula)
	{
		return getDao().setMatriculaColaborador(colaboradorId, matricula);
	}

	@SuppressWarnings("rawtypes")
	public Collection<Colaborador> findListComHistoricoFuturo(int page, int pagingSize, Map parametros)
	{
		return getDao().findList(page, pagingSize, parametros, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);
	}

	@SuppressWarnings("rawtypes")
	public Integer getCountComHistoricoFuturo(Map parametros)
	{
		return getDao().getCount(parametros, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);
	}

	public Colaborador findByIdComHistoricoConfirmados(Long colaboradorId)
	{
		return getDao().findByIdComHistorico(colaboradorId, StatusRetornoAC.CONFIRMADO);
	}

	public Colaborador findByIdComHistorico(Long colaboradorId)
	{
		return getDao().findByIdComHistorico(colaboradorId, null);
	}

	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor)
	{
		return getDao().findAllSelect(empresaId, ordenarPor);
	}

	public Collection<Colaborador> findAllSelect(String situacao, Long... empresaIds)
	{
		return getDao().findAllSelect(situacao, empresaIds);
	}

	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado)
	{
		return getDao().findAllSelect(colaboradorIds, colabDesligado);
	}
	
	public Collection<Colaborador> findComNotaDoCurso(Collection<Long> colaboradorIds, Long turmaId)
	{
		return getDao().findComNotaDoCurso(colaboradorIds, turmaId);
	}

	public void updateInfoPessoais(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas,
			Collection<Experiencia> experiencias, Empresa empresa) throws Exception
			{
		try
		{
			Colaborador colaboradorOriginal = findColaboradorById(colaborador.getId());

			getDao().updateInfoPessoais(colaborador);
			salvarBairro(colaborador);

			formacaoManager.removeColaborador(colaborador);
			colaboradorIdiomaManager.removeColaborador(colaborador);
			experienciaManager.removeColaborador(colaborador);

			saveDetalhes(colaborador, formacaos, idiomas, experiencias);

			if(empresa.isAcIntegra() && verifyExists(new String[]{"id", "naoIntegraAc", "codigoAC"}, new Object[]{colaborador.getId(), false, colaborador.getCodigoAC()}))
			{
				colaborador = findColaboradorById(colaborador.getId());
				acPessoalClientColaborador.atualizar(bindEmpregado(colaborador, empresa.getCodigoAC()), empresa);
			}

			Colaborador colaboradorAtualizado = findColaboradorById(colaborador.getId());
			replicaUpdateCandidato(findAllRelacionamentos(colaborador.getId()), idiomas);
			
			gerenciadorComunicacaoManager.enviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível editar o colaborador.");
		}
			}

	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId)
	{
		return getDao().updateInfoPessoaisByCpf(colaborador, empresaId);
	}

	public Colaborador updateEmpregado(TEmpregado empregado) throws Exception
	{
		Colaborador colaborador = null;
		if(empregado.getId() != null && empregado.getId() != 0)
			colaborador = findByIdComHistorico(empregado.getId().longValue());
		else
			colaborador = getDao().findByCodigoACEmpresaCodigoAC(empregado.getCodigoAC(), empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());

		if(colaborador != null)//Colaborador não existe na base do rh, pode ser um empregado com data de demissao (não importado)
		{
			colaborador = bindColaborador(colaborador, empregado);
			getDao().update(colaborador);
		}

		return colaborador;
	}

	public void saveEmpregadosESituacoes(TEmpregado[] tEmpregados, TSituacao[] tSituacoes, Empresa empresa) throws Exception
	{
		for(TEmpregado tEmpregado: tEmpregados)
		{
			Colaborador colaborador = new Colaborador();
			bindColaborador(colaborador, tEmpregado);
			colaborador.setCodigoAC(tEmpregado.getCodigoACDestino());
			
			colaborador.setEmpresa(empresa);
			getDao().save(colaborador);
			
			TSituacao tSituacao = new TSituacao();
			for (TSituacao tSituacaoTmp : tSituacoes) {
				if (tSituacaoTmp.getEmpregadoCodigoACDestino() != null && tSituacaoTmp.getEmpregadoCodigoACDestino().equals(tEmpregado.getCodigoACDestino()))
					tSituacao = tSituacaoTmp;
			}
			
			if(tSituacao.getEmpregadoCodigoAC() == null)
				throw new Exception("O empregado " + colaborador.getNome() + " está sem situação.");
			
			HistoricoColaborador historicoColaborador = new HistoricoColaborador();
			historicoColaborador.setColaborador(colaborador);
			historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
			historicoColaborador.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
			historicoColaboradorManager.bindSituacao(tSituacao, historicoColaborador);
			historicoColaboradorManager.save(historicoColaborador);
		}
	}

	private Colaborador bindColaborador(Colaborador colaborador, TEmpregado empregado) throws Exception
	{
		colaborador.setCodigoAC(empregado.getCodigoAC());
		colaborador.setNome(empregado.getNome());
		if(empregado.getNomeComercial() != null && !empregado.getNomeComercial().equals(""))
			colaborador.setNomeComercial(empregado.getNomeComercial());

		colaborador.setDataAdmissao(empregado.getDataAdmissaoFormatada());
		colaborador.setMatricula(empregado.getMatricula());
		
		if(colaborador.getEndereco() == null)
			colaborador.setEndereco(new Endereco());

		colaborador.getEndereco().setLogradouro(empregado.getLogradouro());
		colaborador.getEndereco().setNumero(empregado.getNumero());
		colaborador.getEndereco().setComplemento(empregado.getComplemento());
		colaborador.getEndereco().setBairro(empregado.getBairro());
		colaborador.getEndereco().setCep(empregado.getCep());

		if (StringUtils.isNotBlank(empregado.getCidadeCodigoAC()) && StringUtils.isNotBlank(empregado.getUfSigla()))
		{
			Cidade cidade = cidadeManager.findByCodigoAC(empregado.getCidadeCodigoAC(), empregado.getUfSigla());
			if(cidade == null)
				throw new Exception("A cidade com o código " + empregado.getCidadeCodigoAC() + " não está cadastrada no RH para o estado " + empregado.getUfSigla() + ".");
			
			colaborador.getEndereco().setCidade(cidade);
			colaborador.getEndereco().setUf(colaborador.getEndereco().getCidade().getUf());
		}

		if(colaborador.getPessoal() == null)
			colaborador.setPessoal(new Pessoal());

		colaborador.getPessoal().setCpf(empregado.getCpf());
		colaborador.getPessoal().setPis(empregado.getPis());

		if (StringUtils.isNotBlank(empregado.getSexo()))
			colaborador.getPessoal().setSexo(empregado.getSexo().charAt(0));

		colaborador.getPessoal().setDataNascimento(empregado.getDataNascimentoFormatada());

		populaEscolaridade(colaborador, empregado);
		
		colaborador.getPessoal().setEstadoCivil(empregado.getEstadoCivil());
		colaborador.getPessoal().setConjuge(empregado.getConjuge());
		colaborador.getPessoal().setPai(empregado.getPai());
		colaborador.getPessoal().setMae(empregado.getMae());

		if (StringUtils.isNotBlank(empregado.getDeficiencia()))
			colaborador.getPessoal().setDeficiencia(empregado.getDeficiencia().charAt(0));

		if(colaborador.getContato() == null)
			colaborador.setContato(new Contato());
		colaborador.getContato().setDdd(empregado.getDdd());
		colaborador.getContato().setFoneFixo(empregado.getFoneFixo());
		colaborador.getContato().setFoneCelular(empregado.getFoneCelular());
		colaborador.getContato().setEmail(empregado.getEmail());

		colaborador.getPessoal().setRg(empregado.getIdentidadeNumero());
		colaborador.getPessoal().setRgOrgaoEmissor(empregado.getIdentidadeOrgao());
		colaborador.getPessoal().setRgDataExpedicao(empregado.getIdentidadeDataExpedicaoFormatada());

		if (StringUtils.isNotBlank(empregado.getIdentidadeUF()))
			colaborador.getPessoal().setRgUf(estadoManager.findBySigla(empregado.getIdentidadeUF()));

		if(colaborador.getPessoal().getTituloEleitoral() == null)
			colaborador.getPessoal().setTituloEleitoral(new TituloEleitoral());
		colaborador.getPessoal().getTituloEleitoral().setTitEleitNumero(empregado.getTituloNumero());
		colaborador.getPessoal().getTituloEleitoral().setTitEleitSecao(empregado.getTituloSecao());
		colaborador.getPessoal().getTituloEleitoral().setTitEleitZona(empregado.getTituloZona());

		if(colaborador.getPessoal().getCertificadoMilitar() == null)
			colaborador.getPessoal().setCertificadoMilitar(new CertificadoMilitar());
		colaborador.getPessoal().getCertificadoMilitar().setCertMilNumero(empregado.getCertificadoMilitarNumero());
		colaborador.getPessoal().getCertificadoMilitar().setCertMilTipo(empregado.getCertificadoMilitarTipo());
		colaborador.getPessoal().getCertificadoMilitar().setCertMilSerie(empregado.getCertificadoMilitarSerie());

		if(colaborador.getHabilitacao() == null)
			colaborador.setHabilitacao(new Habilitacao());
		colaborador.getHabilitacao().setNumeroHab(empregado.getHabilitacaoNumero());
		colaborador.getHabilitacao().setEmissao(empregado.getHabilitacaoEmissaoFormatada());
		colaborador.getHabilitacao().setVencimento(empregado.getHabilitacaoVencimentoFormatada());
		colaborador.getHabilitacao().setCategoria(empregado.getHabilitacaoCategoria());

		if(colaborador.getPessoal().getCtps() == null)
			colaborador.getPessoal().setCtps(new Ctps());
		colaborador.getPessoal().getCtps().setCtpsNumero(empregado.getCtpsNumero());
		colaborador.getPessoal().getCtps().setCtpsSerie(empregado.getCtpsSerie());
		colaborador.getPessoal().getCtps().setCtpsDataExpedicao(empregado.getCtpsDataExpedicaoFormatada());

		if (StringUtils.isNotBlank(empregado.getCtpsUFSigla()))
			colaborador.getPessoal().getCtps().setCtpsUf(estadoManager.findBySigla(empregado.getCtpsUFSigla()));

		if (StringUtils.isNotBlank(empregado.getCtpsDV()))
			colaborador.getPessoal().getCtps().setCtpsDv(empregado.getCtpsDV().charAt(0));

		String vinculo = getVinculo(empregado.getTipoAdmissao(), empregado.getVinculo(), empregado.getCategoria());
		colaborador.setVinculo(vinculo);
		
		bindFoto(colaborador, empregado);

		return colaborador;
	}

	private void bindFoto(Colaborador colaborador, TEmpregado empregado) 
	{
		if(empregado.getRetiraFoto() != null && RETIRAFOTO.equals(empregado.getRetiraFoto()))
			colaborador.setFoto(null);
		else if (StringUtils.isNotBlank(empregado.getFoto()))
			colaborador.setFoto(montaImagemBYFortesPessoal(empregado.getFoto()));
	}
	
	private File montaImagemBYFortesPessoal(String foto)
	{
		byte[] decoded = Base64.decodeBase64(foto.getBytes());
		
		File file = new File();
		file.setName("Pessoal_" + new Date().getTime());
		file.setContentType("image/jpeg"); 
		file.setBytes(decoded);
		file.setSize(1000L);
		
		return file;
	}
	
	private void defineEscolaridade(Colaborador colaborador, TEmpregado empregado) 
	{
		if(empregado.getEscolaridade() == null && colaborador.getPessoal().getEscolaridade() == null)
			colaborador.getPessoal().setEscolaridade(Escolaridade.ANALFABETO);
		else if(empregado.getEscolaridade().equals(EscolaridadeACPessoal.SUPERIOR_EM_ANDAMENTO))
			colaborador.getPessoal().setEscolaridade(Escolaridade.SUPERIOR_EM_ANDAMENTO);
		else if(empregado.getEscolaridade().equals(EscolaridadeACPessoal.SUPERIOR_COMPLETO))
			colaborador.getPessoal().setEscolaridade(Escolaridade.SUPERIOR_COMPLETO);
		else if(empregado.getEscolaridade().equals(EscolaridadeACPessoal.MESTRADO))
			colaborador.getPessoal().setEscolaridade(Escolaridade.MESTRADO);
		else if(empregado.getEscolaridade().equals(EscolaridadeACPessoal.DOUTORADO))
			colaborador.getPessoal().setEscolaridade(Escolaridade.DOUTORADO);
		else 
			colaborador.getPessoal().setEscolaridade(empregado.getEscolaridade());
	}

	//obs: se alterar esse método tem que alterar no importador ac rh
	public void populaEscolaridade(Colaborador colaborador, TEmpregado empregado) 
	{
		if(colaborador.getPessoal().getEscolaridade() != null && ((empregado.getEscolaridade() == null)
					|| ((colaborador.getPessoal().getEscolaridade().equals(Escolaridade.TECNICO_EM_ANDAMENTO) || colaborador.getPessoal().getEscolaridade().equals(Escolaridade.TECNICO_COMPLETO)) && empregado.getEscolaridade().equals(EscolaridadeACPessoal.COLEGIAL_COMPLETO))
					|| (colaborador.getPessoal().getEscolaridade().equals(Escolaridade.ESPECIALIZACAO)) && empregado.getEscolaridade().equals(EscolaridadeACPessoal.SUPERIOR_COMPLETO)))
		{
			return;
		}
		
		defineEscolaridade(colaborador, empregado);
	}

	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		return getDao().findByCodigoAC(empregadoCodigoAC, empresaCodigoAC, grupoAC);
	}

	public Long findByUsuario(Long usuarioId)
	{
		return getDao().findByUsuario(usuarioId);
	}

	public Integer getCountAtivosQualquerStatus(Date dataBase, Long[] empresaIds, Long[] areasIds, Long[] estabelecimentosIds)
	{
		return getDao().getCountAtivosQualquerStatus(dataBase, empresaIds, areasIds, estabelecimentosIds);
	}

	public Integer getCountAtivosEstabelecimento(Long estabelecimentoId)
	{
		return getDao().getCountAtivosByEstabelecimento(estabelecimentoId);
	}

	public File getFoto(Long id) throws Exception
	{
		return getDao().getFile("foto", id);
	}

	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds) throws Exception
	{
		Collection<Colaborador> resultado = getDao().findAniversariantes(empresaIds, mes, areaIds, estabelecimentoIds);

		if (resultado == null || resultado.isEmpty())
			throw new ColecaoVaziaException("Não existem colaboradores para o filtro informado.");

		return setFamiliaAreas(resultado, empresaIds);
	}

	public Collection<Colaborador> setFamiliaAreas(Collection<Colaborador> colaboradores, Long... empresaIds) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findByEmpresasIds(empresaIds, AreaOrganizacional.TODAS);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

		for (Colaborador colaborador: colaboradores)
		{
			if(colaborador.getAreaOrganizacional() != null && colaborador.getAreaOrganizacional().getId() != null)
				colaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colaborador.getAreaOrganizacional().getId()));
		}

		return colaboradores;
	}

	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Boolean somenteAtivos, String[] colabsNaoHomonimoHa, Integer statusRetornoAC, Long... empresaIds)
	{
		return getDao().findByNomeCpfMatricula(colaborador, somenteAtivos, colabsNaoHomonimoHa, statusRetornoAC, empresaIds);
	}

	public String getNome(Long id)
	{
		String nome = "";
		try
		{
			Colaborador colaborador = getDao().findColaboradorByIdProjection(id);
			nome = colaborador.getNome();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return nome;
	}

	public void remove(Colaborador colaborador, Empresa empresa) throws Exception
	{
		String[] dependenciasDaTabelaColaborador = verificaDependencias(colaborador);		
		
		if(dependenciasDaTabelaColaborador.length > 0){
			StringBuffer msg = new StringBuffer("Este colaborador não pode ser removido pois possui dependência com: <br />");
		
			for (String table : dependenciasDaTabelaColaborador)
				msg.append("&bull; ").append(Entidade.getDescricao(table)).append("<br />");
			
			throw new FortesException(msg.toString());
		}else{
			Colaborador colaboradorTmp = removeColaboradorDependencias(colaborador);
			if(empresa.isAcIntegra())
			{
				if( ! acPessoalClientColaborador.remove(colaboradorTmp, empresa))
					throw new IntegraACException("Não foi possível remover o colaborador no Fortes Pessoal.");
			}
		}
	}

	public void cancelarContratacaoNoAC(Colaborador colaborador, HistoricoColaborador historicoColaborador, String mensagem) throws Exception
	{
		solicitacaoManager.atualizaStatusSolicitacaoByColaborador(colaborador, StatusCandidatoSolicitacao.INDIFERENTE, true);
		historicoColaboradorManager.remove(historicoColaborador);
		
		String[] dependenciasDaTabelaColaborador = verificaDependencias(colaborador);
		if(dependenciasDaTabelaColaborador.length > 0){
			StringBuffer msg = new StringBuffer("Erro ao cancelar contratação do empregado. Este empregado possui dependências no RH com: ");
		
			for (String table : dependenciasDaTabelaColaborador)
				msg.append(Entidade.getDescricao(table)).append("; ");
			
			throw new FortesException(msg.toString());
		}else{
			removeColaboradorDependencias(colaborador);
		}
		
		gerenciadorComunicacaoManager.enviaMensagemCancelamentoContratacao(colaborador, mensagem);
		auditoriaManager.auditaCancelarContratacaoNoAC(colaborador, mensagem);
	}

	private String[] verificaDependencias(Colaborador colaborador) throws Exception {
		String[] dependenciasDaTabelaColaborador = getDao().findDependentTables(colaborador.getId());
		
		String[] dependenciasDesconsideradasNaRemocao = colaborador.getDependenciasDesconsideradasNaRemocao();
		
		for (String dependenciaDesconsideradaNaRemocao : dependenciasDesconsideradasNaRemocao) 
			dependenciasDaTabelaColaborador = (String[]) ArrayUtils.removeElement(dependenciasDaTabelaColaborador, dependenciaDesconsideradaNaRemocao);			
		
		return dependenciasDaTabelaColaborador;
	}
	
	public Colaborador removeColaboradorDependencias(Colaborador colaborador) 
	{
		formacaoManager.removeColaborador(colaborador);
		colaboradorIdiomaManager.removeColaborador(colaborador);
		experienciaManager.removeColaborador(colaborador);
		configuracaoNivelCompetenciaManager.removeColaborador(colaborador);
		configuracaoNivelCompetenciaColaboradorManager.removeColaborador(colaborador);
		colaboradorPeriodoExperienciaAvaliacaoManager.removeConfiguracaoAvaliacaoPeriodoExperiencia(colaborador.getId());
		mensagemManager.removeMensagensColaborador(colaborador.getId(), null);

		if(colaborador.getEmpresa() != null && colaborador.getCandidato() != null)
			solicitacaoExameManager.transferirColaboradorToCandidato(colaborador.getEmpresa().getId(), colaborador.getCandidato().getId(), colaborador.getId());

		solicitacaoExameManager.removeByColaborador(colaborador.getId());
		
		Colaborador	colaboradorTmp = getDao().findColaboradorByIdProjection(colaborador.getId());

		candidatoManager.updateDisponivelAndContratadoByColaborador(true, false, colaborador.getId());
		candidatoSolicitacaoManager.setStatusByColaborador(StatusCandidatoSolicitacao.INDIFERENTE, colaborador.getId());

		historicoColaboradorManager.removeColaborador(colaborador.getId());
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{
			remove(colaborador.getId());
			mensagemManager.removerMensagensViculadasByColaborador(new Long[]{colaborador.getId()});
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
		}

		if(colaboradorTmp.getCamposExtras() != null && colaboradorTmp.getCamposExtras().getId() != null && !candidatoManager.existeCamposExtras(colaboradorTmp.getCamposExtras().getId()))
			camposExtrasManager.remove(colaboradorTmp.getCamposExtras().getId());

		
		return colaboradorTmp;
	}

	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<Colaborador> colaboradoresTmp = getDao().findAllSelect(empresaId, "nomeComercial");
			return CheckListBoxUtil.populaCheckListBox(colaboradoresTmp, "getId", "getNomeMaisNomeComercial");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public Colaborador findByIdHistoricoProjection(Long id)
	{
		return getDao().findByIdHistoricoProjection(id);
	}

	public Colaborador findByIdDadosBasicos(Long id, Integer statusRetornoAC)
	{
		return getDao().findByIdDadosBasicos(id, statusRetornoAC);
	}

	public Collection<Colaborador> findByAreaOrganizacionalIdsNome(Collection<Long> areasIds, Colaborador colaborador)
	{
		return getDao().findByAreaOrganizacionalIds(areasIds, null, null, null, null, colaborador, null, null, null, false, false);
	}

	public Colaborador findByIdHistoricoAtual(Long colaboradorId, boolean exibirSomenteAtivos)
	{
		return getDao().findByIdHistoricoAtual(colaboradorId, exibirSomenteAtivos);
	}
	
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds)
	{
		return getDao().findByIdHistoricoAtual(colaboradorIds);
	}

	public Colaborador findColaboradorByDataHistorico(Long colaboradorId, Date dataHistorico)
	{
		return getDao().findColaboradorByDataHistorico(colaboradorId, dataHistorico);
	}
	
	public void migrarBairro(String bairro, String bairroDestino)
	{
		getDao().migrarBairro(bairro, bairroDestino);
	}

	public Collection<Colaborador> findByAreaOrganizacionalEstabelecimento(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentoIds, String situacao)
	{
		return getDao().findByAreaOrganizacionalEstabelecimento(areaOrganizacionalIds, estabelecimentoIds, situacao);
	}

	public void validaQtdCadastros() throws Exception
	{
		//TODO remprot
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		if (parametrosDoSistema.verificaRemprot()) {

			int qtdColaboradorNoBanco = getDao().countColaboradoresComHistoricos();

			RPClient remprot = Autenticador.getRemprot();
			if (Autenticador.isRegistrado())
			{
				if (remprot.getUserCount() > 0 && qtdColaboradorNoBanco >= remprot.getUserCount())
					throw new FortesException("Sua licença só permite manter " + remprot.getUserCount() + " colaboradores ativos.<br>Atualmente o sistema possui " + qtdColaboradorNoBanco +" colaboradores ativos.");			
			}	
			else
				if (qtdColaboradorNoBanco >= Autenticador.getQtdCadastrosVersaoDemo())
					throw new FortesException("Versão demonstração, só é permitido cadastrar " + Autenticador.getQtdCadastrosVersaoDemo() + " Colaboradores");
		}
	}

	public String avisoQtdCadastros() throws Exception
	{
//		TODO remprot
		int qtdColaboradorNoBanco = getDao().getCount(new String[]{"desligado"}, new Object[]{false});
		
		RPClient remprot = Autenticador.getRemprot();
		if(Autenticador.isRegistrado()) {
			if (remprot.getUserCount() > 0 && (remprot.getUserCount() - (remprot.getUserCount() * 0.05)) <= qtdColaboradorNoBanco)
				return "Atualmente existem " + qtdColaboradorNoBanco + " colaboradores cadastrados no sistema.<br>Sua licença permite cadastrar " + remprot.getUserCount() + " colaboradores.";			
		}	
		
		return null;
	}

	public Collection<String> findEmailsDeColaboradoresByPerfis(Collection<Perfil> perfis, Long empresaId)
	{
		if (perfis == null || perfis.isEmpty())
			return new ArrayList<String>();

		Long[] perfilIds = new CollectionUtil<Perfil>().convertCollectionToArrayIds(perfis);

		return getDao().findEmailsDeColaboradoresByPerfis(perfilIds, empresaId);
	}

	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa, Long periodoExperienciaId) throws Exception {
		Collection<Colaborador> admitidosHaDias = getDao().findAdmitidosHaDias(dias, empresa, periodoExperienciaId);

		admitidosHaDias = setFamiliaAreas(admitidosHaDias, empresa.getId());

		return admitidosHaDias;
	}

	public Collection<Colaborador> findAdmitidos(Long[] empresaIds, String vinculo, Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos) throws Exception {

		Collection<Colaborador> admitidos = getDao().findAdmitidos(vinculo, dataIni, dataFim, areasIds, estabelecimentosIds, exibirSomenteAtivos);

		if (admitidos == null || admitidos.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		admitidos = setFamiliaAreas(admitidos, empresaIds);

		return admitidos;
	}

	public Collection<Colaborador> findByNomeCpfMatriculaComHistoricoComfirmado(Colaborador colaborador, Long empresaId, Long[] areasIds)
	{
		return getDao().findByNomeCpfMatriculaComHistoricoComfirmado(colaborador, empresaId, areasIds);
	}

	public Collection<Colaborador> findByCpf(String cpf, Long empresaId, Long colaboradorId, Boolean desligado) 
	{
		return getDao().findByCpf(cpf, empresaId, colaboradorId, desligado);
	}

	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida) 
	{
		return getDao().findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenhoId, isAvaliado, respondida);
	}

	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados, Long empresaId, Long[] areasIds, Long[] cargosIds) 
	{
		return getDao().findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, isAvaliados, empresaId, areasIds, cargosIds);
	}

	public Collection<Colaborador> findColaboradorDeAvaliacaoDesempenhoNaoRespondida() 
	{
		return getDao().findColaboradorDeAvaliacaoDesempenhoNaoRespondida();
	}

	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula,Long empresaId, String nomeComercial) 
	{
		return getDao().findColaboradoresByArea(areaIds, nome, matricula, empresaId, nomeComercial);
	}

	public void setCamposExtrasManager(CamposExtrasManager camposExtrasManager)
	{
		this.camposExtrasManager = camposExtrasManager;
	}
	public Integer qtdTotalDiasDaTurmaVezesColaboradoresInscritos(Date dataPrevIni, Date dataPrevFim, Long[] EmpresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds) 
	{
		return getDao().qtdTotalDiasDaTurmaVezesColaboradoresInscritos(dataPrevIni, dataPrevFim, EmpresaIds, cursoIds, areasIds, estabelecimentosIds);
	}

	@SuppressWarnings("rawtypes")
	public Integer getCountComHistoricoFuturoSQL(Map parametros)
	{
		return getDao().findComHistoricoFuturoSQL(parametros, 0, 0).size();
	}

	@SuppressWarnings("rawtypes")
	public Collection<Colaborador> findComHistoricoFuturoSQL(int page, int pagingSize, Map parametros) throws Exception
	{
		Collection<Colaborador> result = new LinkedList<Colaborador>();

		Collection lista = getDao().findComHistoricoFuturoSQL(parametros, pagingSize, page);

		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Colaborador colaborador = new Colaborador();

			if(array[0] != null)
				colaborador.setId(((BigInteger)array[0]).longValue());

			colaborador.setNome((String) array[1]);
			colaborador.setNomeComercial((String) array[2]);
			colaborador.setMatricula((String) array[3]);
			colaborador.setDesligado((Boolean) array[4]);
			colaborador.setDataAdmissao((Date) array[5]);
			colaborador.setPessoal(new Pessoal());
			colaborador.getPessoal().setCpf((String) array[6]);
			colaborador.setUsuario(new Usuario());

			if(array[7] != null)
				colaborador.getUsuario().setId(((BigInteger)array[7]).longValue());

			colaborador.setDataDesligamento((Date) array[8]);
			colaborador.setMotivoDemissaoMotivo((String) array[9]);

			if (colaborador.isDesligado() == true)
				colaborador.setNome((String) array[1] + " (Desligado em " + DateUtil.formataDiaMesAno(colaborador.getDataDesligamento()) + ")");

			if(array[10] != null)
				colaborador.setRespondeuEntrevista((Boolean) array[10]);
			else
				colaborador.setRespondeuEntrevista(false);

			colaborador.setCandidato(new Candidato());

			if(array[11] != null)
				colaborador.getCandidato().setId(((BigInteger)array[11]).longValue());

			colaborador.setNaoIntegraAc((Boolean) array[12]);
			
			if(array[13] != null)
				colaborador.setDataSolicitacaoDesligamento((Date) array[13]);

			if(array[14] != null)
				colaborador.setDataSolicitacaoDesligamentoAc((Date) array[14]);

			if(array[15] != null)
				colaborador.setAreaOrganizacionalId(((BigInteger)array[15]).longValue());
			
			if(array[16] != null)
				colaborador.setStatusAcPessoal(((Integer)array[16]).intValue());
				
			colaborador.setCodigoAC((String)array[17]);
			
			result.add(colaborador);
		}

		return result;

	}

	public Collection<Colaborador> getAvaliacoesExperienciaPendentes(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Integer tempoDeEmpresa, Integer diasDeAcompanhamento, Collection<PeriodoExperiencia> periodoExperiencias) throws Exception 
	{
		int gordura = 0;
		if(diasDeAcompanhamento != null)
			gordura = diasDeAcompanhamento;

		int menorPeriodo = 0;
		if(!periodoExperiencias.isEmpty())
			menorPeriodo = ((PeriodoExperiencia)periodoExperiencias.toArray()[0]).getDias();

		menorPeriodo = menorPeriodo - gordura;

		Collection<Colaborador> colaboradores = getDao().findAdmitidosNoPeriodo(periodoIni, periodoFim, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa);
		Collection<Colaborador> colaboradoresComAvaliacoes = getDao().findComAvaliacoesExperiencias(periodoIni, periodoFim, empresa, areasCheck, estabelecimentoCheck);

		//autorizado essa ruma de forIf, aprovado
		StringBuilder datasAvaliacao;
		StringBuilder statusAvaliacao;
		for (Colaborador colaborador : colaboradores)
		{
			datasAvaliacao = new StringBuilder();
			statusAvaliacao = new StringBuilder();

			for (PeriodoExperiencia periodoExperiencia : periodoExperiencias)
			{
				String dataSugerida = DateUtil.formataDiaMesAno(DateUtil.incrementaDias(colaborador.getDataAdmissao(), periodoExperiencia.getDias()-1));
				String msg = periodoExperiencia.getDias() + " dias, não respondida";

				if(colaborador.getDiasDeEmpresa() >= (periodoExperiencia.getDias() - gordura))
				{
					for (Colaborador colaboradorRespondidas : colaboradoresComAvaliacoes)
					{
						if(colaborador.getId().equals(colaboradorRespondidas.getId()))
						{
							if(periodoExperiencia.getId().equals(colaboradorRespondidas.getPeriodoExperienciaId()))
								msg = periodoExperiencia.getDias() + " dias, respondida (" + colaboradorRespondidas.getQtdDiasRespondeuAvExperiencia() + " dias)";
						}
					}

					datasAvaliacao.append(dataSugerida + "\n");
					statusAvaliacao.append(msg + "\n");
				}
			}

			if(statusAvaliacao.length() != 0)
				statusAvaliacao.replace(statusAvaliacao.length()-1, statusAvaliacao.length(), "");
			if(datasAvaliacao.length() != 0)
				datasAvaliacao.replace(datasAvaliacao.length()-1, datasAvaliacao.length(), "");

			colaborador.setStatusAvaliacao(statusAvaliacao.toString());
			colaborador.setDatasDeAvaliacao(datasAvaliacao.toString());
		}

		if(colaboradores.isEmpty())
			throw new Exception ("Não existem colaboradores com os filtros selecionados" ); 

		return colaboradores;
	}

	public List<AcompanhamentoExperienciaColaborador> getAvaliacoesExperienciaPendentesPeriodo(Date periodoIni, Date periodoFim, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Collection<PeriodoExperiencia> periodoExperiencias) throws Exception 
	{
		List<AcompanhamentoExperienciaColaborador> acompanhamentos = new ArrayList<AcompanhamentoExperienciaColaborador>();

		Collection<Colaborador> colaboradores = getDao().findAdmitidosNoPeriodo(null, null, empresa, areasCheck, estabelecimentoCheck, null);
		Collection<Colaborador> colaboradoresRespostas = getDao().findComAvaliacoesExperiencias(null, null, empresa, areasCheck, estabelecimentoCheck);

		Date data;
	 	String performance;
	 	boolean temPeriodoExperiencia;

		for (Colaborador colab : colaboradores)
		{
			temPeriodoExperiencia = false;
			AcompanhamentoExperienciaColaborador experienciaColaborador = new AcompanhamentoExperienciaColaborador(colab.getMatricula(), colab.getNome(), colab.getCargoFaixa(), colab.getAreaOrganizacional(), colab.getDataAdmissao());
			for (PeriodoExperiencia periodoExperiencia : periodoExperiencias)
			{
				data = null;
				performance = null;
				for (Colaborador colaboradorResposta : colaboradoresRespostas)
				{
					if(colab.getId().equals(colaboradorResposta.getId()) && periodoExperiencia.getId().equals(colaboradorResposta.getPeriodoExperienciaId()))
					{
						performance = colaboradorResposta.getPerformance();
						data = colaboradorResposta.getAvaliacaoRespondidaEm();
					}
				}

				Date dataDoPeriodoDeExperiencia = DateUtil.incrementaDias(colab.getDataAdmissao(), periodoExperiencia.getDias()-1);

				if(dataDoPeriodoDeExperiencia.getTime() <= periodoFim.getTime() && dataDoPeriodoDeExperiencia.getTime() >= periodoIni.getTime())
				{
					String DataPeriodoExperienciaPrevista = DateUtil.formataDiaMesAno(DateUtil.incrementaDias(colab.getDataAdmissao(), periodoExperiencia.getDias()-1));
					experienciaColaborador.addPeriodo(data, performance, DataPeriodoExperienciaPrevista);
					temPeriodoExperiencia = true;
				}else
					experienciaColaborador.addPeriodo(null, null, null);

			}

			if (temPeriodoExperiencia)
				acompanhamentos.add(experienciaColaborador);
		}

		if(acompanhamentos.isEmpty())
			throw new Exception ("Não existem colaboradores com os filtros selecionados" ); 

		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findByEmpresasIds(new Long[]{empresa.getId()}, AreaOrganizacional.TODAS);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

		for (AcompanhamentoExperienciaColaborador acompanhamento: acompanhamentos)
			acompanhamento.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, acompanhamento.getAreaOrganizacionalId()));

		Collections.sort(acompanhamentos);
		return acompanhamentos;
	}

	public Collection<Colaborador> findColabPeriodoExperiencia(Date periodoIni, Date periodoFim, String[] avaliacaoCheck, String[] areasCheck, String[] estabelecimentoCheck, String[] colaboradorsCheck, boolean agruparPorArea, Long... empresasIds) throws Exception 
	{
		Collection<Colaborador> retorno = new ArrayList<Colaborador>();
		retorno = getDao().findColabPeriodoExperiencia(periodoIni, periodoFim, StringUtil.stringToLong(avaliacaoCheck), StringUtil.stringToLong(areasCheck), StringUtil.stringToLong(estabelecimentoCheck), StringUtil.stringToLong(colaboradorsCheck), true, agruparPorArea, empresasIds);

		if(retorno.isEmpty())
			throw new Exception("Não existem informações para os filtros selecionados");


		return retorno;
	}

	public Collection<Colaborador> findColabPeriodoExperienciaAgrupadoPorModelo(Long empresaId, Date periodoIni, Date periodoFim, Long avaliacaoId, String[] areasCheck, String[] estabelecimentoCheck, String[] colaboradorsCheck, boolean considerarAutoAvaliacao) throws Exception 
	{
		AvaliacaoDesempenhoManager avaliacaoDesempenhoManager = (AvaliacaoDesempenhoManager) SpringUtil.getBean("avaliacaoDesempenhoManager");
		Collection<AvaliacaoDesempenho> avaliacaoIds = avaliacaoDesempenhoManager.findIdsAvaliacaoDesempenho(avaliacaoId);
		CollectionUtil<AvaliacaoDesempenho> clu = new CollectionUtil<AvaliacaoDesempenho>();
		Collection<Colaborador> retorno = getDao().findColabPeriodoExperiencia(periodoIni, periodoFim, clu.convertCollectionToArrayIds(avaliacaoIds), StringUtil.stringToLong(areasCheck), StringUtil.stringToLong(estabelecimentoCheck), StringUtil.stringToLong(colaboradorsCheck), considerarAutoAvaliacao, false, empresaId);

		if(retorno.isEmpty())
			throw new Exception("Não existem informações para os filtros selecionados");

		return retorno;
	}

	public Collection<Colaborador> montaTempoServico(Collection<Colaborador> colaboradores, Integer[] tempoServicoIni, Integer[] tempoServicoFim, String orderAgrupadoPor) 
	{
		if (tempoServicoIni != null && tempoServicoFim != null && tempoServicoIni.length == tempoServicoFim.length)
		{
			for (Colaborador colaborador : colaboradores)
			{				
				for (int i = 0; i < tempoServicoIni.length; i++) 
				{
					if (colaborador.getTempoServico() >= tempoServicoIni[i] && colaborador.getTempoServico() <= tempoServicoFim[i])
					{
						colaborador.setTempoServicoString("De " + tempoServicoIni[i] + " até " + tempoServicoFim[i] + " meses");
					}
				}
			}
		}

		colaboradores = new CollectionUtil<Colaborador>().sortCollectionStringIgnoreCase(colaboradores, orderAgrupadoPor);
		colaboradores = new CollectionUtil<Colaborador>().sortCollectionStringIgnoreCase(colaboradores, "tempoServicoString");

		return colaboradores;
	}
	
	public Collection<Colaborador> findDemitidosTurnoverTempoServico(Integer[] tempoServicoIni, Integer[] tempoServicoFim, Long empresaId, Date dataIni, Date dataFim, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, int filtrarPor) 
	{
		if (filtrarPor == 1)
			cargosIds = null;
		else if (filtrarPor == 2)
			areasIds = null;
		
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		Collection<Colaborador> colaboradores = getDao().findDemitidosTurnover(empresa, dataIni, dataFim, tempoServicoIni, tempoServicoFim, estabelecimentosIds, areasIds, cargosIds, vinculos);
		
		return this.montaTempoServico(colaboradores, tempoServicoIni, tempoServicoFim, "nome");
	}

	public Collection<Colaborador> insereGrupoPorTempoServico(Collection<Colaborador> colaboradores, Integer[] tempoServicoIni, Integer[] tempoServicoFim)
	{
		for (Colaborador colaborador : colaboradores) {
			if (tempoServicoIni != null && tempoServicoFim != null && tempoServicoIni.length == tempoServicoFim.length){
				for (int i = 0; i < tempoServicoIni.length; i++) 
				{
					if (colaborador.getTempoServico() >= tempoServicoIni[i] && colaborador.getTempoServico() <= tempoServicoFim[i])
					{
						colaborador.setIntervaloTempoServico("De " + tempoServicoIni[i] + " até " + tempoServicoFim[i] + " meses");
						break;
					}
				}
			}
		}

		return colaboradores;
	}

	public Colaborador findByUsuarioProjection(Long usuarioId, Boolean ativo) 
	{
		return getDao().findByUsuarioProjection(usuarioId, ativo);
	}

	public String[] findEmailsByUsuarios(Collection<Long> usuarioEmpresaIds)
	{
		if(usuarioEmpresaIds.isEmpty())
			return new String[0];

		return getDao().findEmailsByPapel(usuarioEmpresaIds);
	}

	public Collection<DataGrafico> countSexo(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		return getDao().countSexo(data, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculos);
	}

	public Collection<DataGrafico> countEstadoCivil(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		return getDao().countEstadoCivil(data, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculos);
	}

	public Collection<DataGrafico> countFormacaoEscolar(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		return  getDao().countFormacaoEscolar(data, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculos);
	}

	public Collection<DataGrafico> countFaixaEtaria(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos)
	{
		return  getDao().countFaixaEtaria(data, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculos);
	}

	public Collection<DataGrafico> countDeficiencia(Date data, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		return getDao().countDeficiencia(data, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculos);
	}

	public Collection<DataGrafico> countMotivoDesligamento(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens) 
	{
		return getDao().countMotivoDesligamento(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, qtdItens);
	}

	public Collection<DataGrafico> countColocacao(Date dataBase, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, String[] vinculos) 
	{
		return getDao().countColocacao(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculos);
	}

	public Collection<DataGrafico> countOcorrencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long[] ocorrenciasIds, int qtdItens) 
	{
		return getDao().countOcorrencia(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasIds, qtdItens);
	}
	
	public Collection<Ocorrencia> getOcorrenciasByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens) 
	{
		Collection<Ocorrencia> ocorrenciasRetorno = getDao().getOcorrenciasByPeriodo(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, qtdItens); 
		return new CollectionUtil<Ocorrencia>().sortCollectionStringIgnoreCase(ocorrenciasRetorno, "descricaoComEmpresa");
	}

	public Collection<DataGrafico> countProvidencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long[] ocorrenciasIds, int qtdItens) 
	{
		return getDao().countProvidencia(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasIds, qtdItens);
	}

	public int getCountAtivos(Date dataBase, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds) 
	{
		return getDao().getCountAtivos(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds);
	}

	public Integer countAdmitidosDemitidosTurnover(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, boolean isAdmitidos) 
	{
		Integer totalPorEmpresas = 0;
		if(empresaIds != null)
		{
			for (Long empresaId : empresaIds)
			{	
				Empresa empresaTmp = empresaManager.findByIdProjection(empresaId);
				totalPorEmpresas += getDao().countAdmitidosDemitidosTurnover(dataIni, dataFim, empresaTmp, estabelecimentosIds, areasIds, cargosIds, isAdmitidos);
			}			
		}

		return totalPorEmpresas;
	}

	public TurnOverCollection montaTurnOver(Date dataIni, Date dataFim, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, int filtrarPor) throws Exception 
	{
		if(filtrarPor == 1)
			cargosIds = null;
		else if(filtrarPor == 2)
			areasIds = null;

		int ate = DateUtil.mesesEntreDatas(dataIni, dataFim);
		Date dataTmp = DateUtil.getInicioMesData(dataIni);
		Collection<TurnOver> admitidos = new ArrayList<TurnOver>();
		Collection<TurnOver> demitidos = new ArrayList<TurnOver>();
		Collection<TurnOver> turnOvers = new LinkedList<TurnOver>();
		double qtdAtivosInicioMes;
		double qtdAtivosFinalMes;
		double qtdAdmitidos;
		double qtdDemitidos;
		double totalAdmitidos = 0;
		double totalDemitidos = 0;

		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		
		for (int i = 0; i <= ate; i++)
		{
			qtdAtivosInicioMes = 0;
			qtdAtivosFinalMes = 0;
			qtdAdmitidos = 0;
			qtdDemitidos = 0;
			dataFim = DateUtil.getUltimoDiaMes(dataTmp);

			admitidos = getDao().countAdmitidosDemitidosPeriodoTurnover(dataTmp, dataFim, empresa, estabelecimentosIds, areasIds, cargosIds, vinculos, true);
			if(admitidos != null && admitidos.size() > 0)
			{
				qtdAdmitidos = ((TurnOver) admitidos.toArray()[0]).getQtdAdmitidos();
				totalAdmitidos += qtdAdmitidos;
			}

			demitidos = getDao().countAdmitidosDemitidosPeriodoTurnover(dataTmp, dataFim, empresa, estabelecimentosIds, areasIds, cargosIds, vinculos, false);
			if(demitidos != null && demitidos.size() > 0)
			{
				qtdDemitidos = ((TurnOver) demitidos.toArray()[0]).getQtdDemitidos();
				totalDemitidos += qtdDemitidos;
			}

			qtdAtivosInicioMes = getDao().countAtivosPeriodo(DateUtil.getUltimoDiaMesAnterior(dataTmp), Arrays.asList(empresaId), estabelecimentosIds, areasIds, cargosIds, vinculos, null, false, null, false);
			qtdAtivosFinalMes = getDao().countAtivosPeriodo(DateUtil.getUltimoDiaMes(dataTmp), Arrays.asList(empresaId), estabelecimentosIds, areasIds, cargosIds, vinculos, null, false, null, false);
			
			TurnOver turnOverTmp = new TurnOver();
			turnOverTmp.setMesAno(dataTmp);
			turnOverTmp.setQtdAdmitidos(qtdAdmitidos);
			turnOverTmp.setQtdDemitidos(qtdDemitidos);
			turnOverTmp.setQtdAtivosInicioMes(qtdAtivosInicioMes);
			turnOverTmp.setQtdAtivosFinalMes(qtdAtivosFinalMes);

			if (empresa.getFormulaTurnover() == FormulaTurnover.MEDIA_ATIVOS_MES)
				turnOverTmp.setTurnOver((((qtdAdmitidos + qtdDemitidos) / 2) / ((qtdAtivosInicioMes + qtdAtivosFinalMes) / 2)) * 100);
			else
				turnOverTmp.setTurnOver((((qtdAdmitidos + qtdDemitidos) / 2) / qtdAtivosInicioMes) * 100);
			
			turnOvers.add(turnOverTmp);
			
			dataTmp = DateUtil.setaMesPosterior(dataTmp);
		}
		
		if (turnOvers == null || turnOvers.isEmpty())
			throw new ColecaoVaziaException();
		
		TurnOverCollection turnOverCollection = new TurnOverCollection();
		turnOverCollection.setEmpresaId(empresa.getId());
		turnOverCollection.setEmpresaNome(empresa.getNome());
		turnOverCollection.setFormula(empresa.getFormulaTurnoverDescricao());
		turnOverCollection.setTurnOvers(turnOvers);
		turnOverCollection.setQtdAdmitidos(totalAdmitidos);
		turnOverCollection.setQtdDemitidos(totalDemitidos);

		return turnOverCollection;
	}
	
	public TaxaDemissaoCollection montaTaxaDemissao(Date dataIni, Date dataFim, Long empresaId, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos, int filtrarPor) throws Exception 
	{
		if(filtrarPor == 1)
			cargosIds = null;
		else if(filtrarPor == 2)
			areasIds = null;
		
		int ate = DateUtil.mesesEntreDatas(dataIni, dataFim);
		Date dataIniTmp = DateUtil.getInicioMesData(dataIni);
		Collection<TaxaDemissao> taxaDemissoes = new LinkedList<TaxaDemissao>();

		for (int i = 0; i <= ate; i++)
		{
			dataFim = DateUtil.getUltimoDiaMes(dataIniTmp);
			
			TaxaDemissao taxaDeDemissaoTmp = new TaxaDemissao();
			taxaDeDemissaoTmp.setMesAno(dataIniTmp);
			taxaDeDemissaoTmp.setQtdDemitidosReducaoQuadro(getDao().countDemitidosPeriodo(dataIniTmp, dataFim, empresaId, estabelecimentosIds, areasIds, cargosIds, vinculos, true));
			taxaDeDemissaoTmp.setQtdDemitidos(getDao().countDemitidosPeriodo(dataIniTmp, dataFim, empresaId, estabelecimentosIds, areasIds, cargosIds, vinculos, false));
			taxaDeDemissaoTmp.setQtdAtivosInicioMes(getDao().countAtivosPeriodo(DateUtil.getUltimoDiaMesAnterior(dataIniTmp), Arrays.asList(empresaId), estabelecimentosIds, areasIds, cargosIds, vinculos, null, false, null, false));
			taxaDeDemissaoTmp.setQtdAtivosFinalMes(getDao().countAtivosPeriodo(dataFim, Arrays.asList(empresaId), estabelecimentosIds, areasIds, cargosIds, vinculos, null, false, null, false));
			taxaDemissoes.add(taxaDeDemissaoTmp);
			
			dataIniTmp = DateUtil.setaMesPosterior(dataIniTmp);
		}
		
		if (taxaDemissoes == null || taxaDemissoes.isEmpty())
			throw new ColecaoVaziaException();
		
		TaxaDemissaoCollection taxaDemissaoCollection = new TaxaDemissaoCollection();
		taxaDemissaoCollection.setTaxaDemissoes(taxaDemissoes);

		return taxaDemissaoCollection;
	}

	public Collection<DataGrafico> montaSalarioPorArea(Date dataBase, Long empresaId, AreaOrganizacional areaOrganizacional) 
	{
		Collection<Colaborador> colaboradores = getDao().findProjecaoSalarialByHistoricoColaborador(dataBase, null, null, null, null, "99", empresaId);
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByEmpresa(empresaId);

		HashMap<AreaOrganizacional, Double> areaSalario = new HashMap<AreaOrganizacional, Double>();
		for (Colaborador colaborador : colaboradores) 
		{
			AreaOrganizacional matriarca = areaOrganizacionalManager.getMatriarca(areas, colaborador.getAreaOrganizacional(), areaOrganizacional.getId());

			if(areaOrganizacional.getId() != null)
			{
				if(matriarca != null && matriarca.getAreaMae() != null && matriarca.getAreaMae().getId() != null && matriarca.getAreaMae().getId().equals(areaOrganizacional.getId()))
				{
					if (!areaSalario.containsKey(matriarca))
						areaSalario.put(matriarca, 0.0);

					areaSalario.put(matriarca, areaSalario.get(matriarca) + (colaborador.getSalarioCalculado()== null?0:colaborador.getSalarioCalculado()));																		
				}
			}
			else
			{
				if (!areaSalario.containsKey(matriarca))
					areaSalario.put(matriarca, 0.0);

				areaSalario.put(matriarca, areaSalario.get(matriarca) + (colaborador.getSalarioCalculado()== null?0:colaborador.getSalarioCalculado()));				
			}
		}

		Collection<DataGrafico> dados = new ArrayList<DataGrafico>();
		if(!areaSalario.isEmpty())
		{
			for (AreaOrganizacional area : areaSalario.keySet()) 
				dados.add(new DataGrafico(area.getId(), area.getNome(), areaSalario.get(area), areaOrganizacional.getDescricao()));			
		}

		return dados;
	}

	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC, String grupoAC) {
		return getDao().findByCodigoACEmpresaCodigoAC(codigoAC, empresaCodigoAC, grupoAC);
	}

	public Collection<Object[]> montaGraficoEvolucaoFolha(Date dataIni, Date dataFim, Long empresaId, Long[] areasIds)
	{
		Collection<Object[]>  graficoEvolucaoFolha = new ArrayList<Object[]>();
		dataFim = DateUtil.getUltimoDiaMes(dataFim);
		while (!dataIni.after(dataFim))
		{
			dataIni = DateUtil.getUltimoDiaMes(dataIni);
			double valor = totalFolhaDia (dataIni, empresaId, areasIds);
			graficoEvolucaoFolha.add(new Object[]{dataIni.getTime(), valor}); 
			dataIni = DateUtil.incrementaMes(dataIni, 1);
		}

		return graficoEvolucaoFolha;
	}

	private Double totalFolhaDia (Date dataBase, Long empresaId, Long[] areasIds)
	{
		Collection<Long> areasOrganizacionais = new CollectionUtil<Long>().convertArrayToCollection(areasIds);
		Collection<Colaborador> colaboradores = getDao().findProjecaoSalarialByHistoricoColaborador(dataBase, null, areasOrganizacionais, null, null, "1", empresaId);

		double valor = 0.0;
		for (Colaborador colaborador : colaboradores)
		{
			Double salarioCalculadoTmp = colaborador.getSalarioCalculado();
			valor += (salarioCalculadoTmp == null ? 0 : salarioCalculadoTmp);
		}
		return valor;
	}

	public int countAtivosPeriodo(Date dataIni, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> ocorrenciasId, boolean considerarDataAdmissao, Long colaboradorId, boolean isAbsenteismo) {
		return getDao().countAtivosPeriodo(dataIni, empresaIds, estabelecimentosIds, areasIds, cargosIds, null, ocorrenciasId, considerarDataAdmissao, colaboradorId, isAbsenteismo);
	}

	public String montaGraficoTurnover(Collection<TurnOverCollection> turnOverCollections, Collection<Empresa> empresas) 
	{
		Collection<Map<String, Object>> retorno = new ArrayList<Map<String, Object>>();
		Map<Long, String> empresasMap = new CollectionUtil<Empresa>().convertCollectionToMap(empresas, "getId", "getNome");
		
		for (TurnOverCollection turnOverCollection : turnOverCollections) 
		{
			Collection<Object[]>  graficoEvolucaoTurnover = new ArrayList<Object[]>();
			for (TurnOver turnOver : turnOverCollection.getTurnOvers()) 
				graficoEvolucaoTurnover.add(new Object[]{DateUtil.getInicioMesData(turnOver.getMesAno()).getTime(), turnOver.getTurnOver()});
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", empresasMap.get(turnOverCollection.getEmpresaId()));
			map.put("data", graficoEvolucaoTurnover);
			
			retorno.add(map);
		}

		return StringUtil.toJSON(retorno, null);
	}
	
	public Collection<DataGrafico> montaGraficoTurnoverTempoServico(Integer[] tempoServicoIni, Integer[] tempoServicoFim, Date dataIni, Date dataFim, Collection<Long> empresasIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos) 
	{
		Map<String, Integer> qtdDeColaboradoresPorPeriodoDeMeses = new HashMap<String, Integer>();
		Empresa empresa;
		String chave;
		
		for (Long empresaId : empresasIds)
		{
			empresa = empresaManager.findByIdProjection(empresaId);
			
			Collection<TurnOver> turnOvers = getDao().countDemitidosTempoServico(empresa, dataIni, dataFim, estabelecimentosIds, areasIds, cargosIds, vinculos);
			
			for (TurnOver turnOver : turnOvers)
			{				
				for (int i = 0; i < tempoServicoIni.length; i++) 
				{
					if (turnOver.getTempoServico() >= tempoServicoIni[i] && turnOver.getTempoServico() <= tempoServicoFim[i])
					{
						chave = StringUtils.leftPad(tempoServicoIni[i].toString(), 2, '0') + " a " + StringUtils.leftPad(tempoServicoFim[i].toString(), 2, '0') + " meses";
						if (!qtdDeColaboradoresPorPeriodoDeMeses.containsKey(chave))
							qtdDeColaboradoresPorPeriodoDeMeses.put(chave, 0);
						
						qtdDeColaboradoresPorPeriodoDeMeses.put(chave, qtdDeColaboradoresPorPeriodoDeMeses.get(chave) + turnOver.getQtdColaboradores());
					}
				}
			}
		}

		Collection<DataGrafico> dados = new ArrayList<DataGrafico>();
		
		for (Map.Entry<String, Integer> entry : qtdDeColaboradoresPorPeriodoDeMeses.entrySet()) 
			dados.add(new DataGrafico(null, entry.getKey(), entry.getValue(), null));

		return new CollectionUtil<DataGrafico>().sortCollectionStringIgnoreCase(dados, "label");
	}
	
	public Collection<DataGrafico> montaGraficoColaboradoresTempoServico(Integer[] tempoServicoIni, Integer[] tempoServicoFim, Integer[] mesesParaMultiplicar, Collection<Long> empresasIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<String> vinculos) 
	{
		Map<String, Integer> qtds = new HashMap<String, Integer>();
		Empresa empresa;
		String chave;
		
		Integer tempoServicoIniEmMeses;
		Integer tempoServicoFimEmMeses;
		String tempo;
		
		if (tempoServicoIni != null)
			for (Long empresaId : empresasIds)
			{
				empresa = empresaManager.findByIdProjection(empresaId);
	
				for (int i = 0; i < tempoServicoIni.length; i++) 
				{
					tempoServicoIniEmMeses = tempoServicoIni[i] * mesesParaMultiplicar[i];
					tempoServicoFimEmMeses = tempoServicoFim[i] * mesesParaMultiplicar[i];
					tempo = mesesParaMultiplicar[i] == 12 ? "anos" : "meses";
					
					Integer qtdColaboradoresPorTempo = getDao().countColaboradoresPorTempoServico(empresa, tempoServicoIniEmMeses, tempoServicoFimEmMeses, estabelecimentosIds, areasIds, cargosIds, vinculos);
					
					chave = StringUtils.leftPad(tempoServicoIni[i].toString(), 2, '0') + " a " + StringUtils.leftPad(tempoServicoFim[i].toString(), 2, '0') + " " + tempo;
					if (!qtds.containsKey(chave))
						qtds.put(chave, 0);
					
					qtds.put(chave, qtds.get(chave) + qtdColaboradoresPorTempo);
				}
			}
		
		Collection<DataGrafico> dados = new ArrayList<DataGrafico>();
		
		for (Map.Entry<String, Integer> entry : qtds.entrySet()) 
			dados.add(new DataGrafico(null, entry.getKey(), entry.getValue(), null));
		
		return new CollectionUtil<DataGrafico>().sortCollectionStringIgnoreCase(dados, "label");
	}

	public Collection<Colaborador> findByAvaliacoes(Long... avaliacaoIds) 
	{
		return getDao().findByAvaliacoes(avaliacaoIds);
	}

	public Collection<Colaborador> findByEstabelecimentoDataAdmissao(Long estabelecimentoId, Date dataAdmissao, Long empresaId) 
	{
		return getDao().findByEstabelecimentoDataAdmissao(estabelecimentoId, dataAdmissao, empresaId);
	}

	public Collection<CartaoAcompanhamentoExperienciaVO> montaCartoesPeriodoExperiencia(Long[] colaboradoresIds, Long[] dias, String observacao) throws Exception 
	{
		Collection<Colaborador> colaboradores = getDao().findColaboradoresByIds(colaboradoresIds);
		Collection<CartaoAcompanhamentoExperienciaVO> vos = new ArrayList<CartaoAcompanhamentoExperienciaVO>();

		if (colaboradores.isEmpty())
			throw new Exception("Não existem dados para o filtro informado");

		Collection<PeriodoExperiencia> periodos = new ArrayList<PeriodoExperiencia>();
		PeriodoExperiencia periodo;

		for (Colaborador colaborador : colaboradores) 
		{
			CartaoAcompanhamentoExperienciaVO vo = new CartaoAcompanhamentoExperienciaVO();
			vo.setColaborador(colaborador);
			vo.setObservacao(observacao);

			periodos = new ArrayList<PeriodoExperiencia>();
			for (Long qtdDias : dias) {
				periodo = new PeriodoExperiencia();
				periodo.setDias(qtdDias.intValue());
				periodo.setDataFim(DateUtil.incrementaDias(colaborador.getDataAdmissao(), qtdDias.intValue()));
				periodos.add(periodo);
			}

			vo.setPeriodosExperiencias(periodos);
			vos.add(vo);
		}

		return vos;
	}

	public int findQtdVagasPreenchidas(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		return getDao().findQtdVagasPreenchidas(empresaId, estabelecimentoIds, areaIds, solicitacaoIds, dataIni, dataFim);
	}

	public Collection<Colaborador> findSemCodigoAC(Long empresaId) {
		return getDao().findSemCodigoAC(empresaId);
	}

	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId) {
		return getDao().getAutoComplete(descricao, empresaId);
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public void setMensagemManager(MensagemManager mensagemManager) {
		this.mensagemManager = mensagemManager;
	}

	public Collection<Colaborador> findByQuestionarioNaoRespondido( Long questionarioId) { 
		return getDao().findByQuestionarioNaoRespondido(questionarioId);
	}

	public double calculaIndiceProcessoSeletivo(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Date dataAte) 
	{
		//(demitidos em até 90 dias / admitidos no periodo * 100)
		double qtdDemitidosEm90Dias = getDao().qtdDemitidosEm90Dias(empresaId, estabelecimentoIds, areaIds, dataAte);
		double qtdAdmitidosPeriodo = getDao().qtdAdmitidosPeriodoEm90Dias(empresaId, estabelecimentoIds, areaIds, dataAte);

		if(qtdDemitidosEm90Dias == 0.0)
			return 100.0;
		if(qtdAdmitidosPeriodo == 0.0)
			return 0.0;

		return MathUtil.round(100 - ((qtdDemitidosEm90Dias / qtdAdmitidosPeriodo) * 100.00) , 2);
	}

	public void deleteColaborador(Long[] colaboradorIds) throws Exception {
		if (colaboradorIds != null && colaboradorIds.length > 0) {
			historicoColaboradorManager.deleteHistoricoColaborador(colaboradorIds);
			candidatoManager.updateDisponivelAndContratadoByColaborador(true, false, colaboradorIds);
			candidatoSolicitacaoManager.setStatusByColaborador(StatusCandidatoSolicitacao.INDIFERENTE, colaboradorIds);
			getDao().remove(colaboradorIds);
		}
	}

	public Collection<Object> montaParentesByNome(Collection<Colaborador> colaboradores) {
		Collection<Object> retorno = new ArrayList<Object>();
		Map<String, Object> dados;

		for (Colaborador colaborador : colaboradores)
		{
			dados = new HashMap<String, Object>();
			dados.put("id", 			colaborador.getId());
			dados.put("nome", 			colaborador.getNome());
			dados.put("pai", 			StringUtils.defaultIfEmpty(colaborador.getPessoal().getPai(), ""));
			dados.put("mae", 			StringUtils.defaultIfEmpty(colaborador.getPessoal().getMae(), ""));
			dados.put("conjuge",		StringUtils.defaultIfEmpty(colaborador.getPessoal().getConjuge(), ""));
			dados.put("endereco",		StringUtils.defaultIfEmpty(colaborador.getEndereco().getEnderecoFormatado(), ""));
			dados.put("cidade",			StringUtils.defaultIfEmpty(colaborador.getEndereco().getCidade().getNome(), ""));
			dados.put("uf",				StringUtils.defaultIfEmpty(colaborador.getEndereco().getUf().getSigla(), ""));
			dados.put("fone",			StringUtils.defaultIfEmpty(colaborador.getContato().getFoneContatoFormatado(), ""));
			dados.put("empresaNome",	StringUtils.defaultIfEmpty(colaborador.getEmpresaNome(), ""));

			retorno.add(dados);
		}

		return retorno;
	}

	public Colaborador findFuncaoAmbiente(Long colaboradorId)
	{
		return getDao().findFuncaoAmbiente(colaboradorId);
	}

	public Collection<PendenciaAC> findPendenciasSolicitacaoDesligamento(Long empresaId) {
		Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();

		Collection<Colaborador> colaboradors = getDao().findPendenciasSolicitacaoDesligamentoAC(empresaId);

		for (Colaborador colaborador : colaboradors) {
			PendenciaAC pendenciaAC = new PendenciaAC();

			pendenciaAC.setPendencia("Solicitação de Desligamento");
			pendenciaAC.setDetalhes("Solicitação de desligamento do colaborador "+colaborador.getNome());
			pendenciaAC.setStatus(StatusRetornoAC.AGUARDANDO);
			pendenciaAC.setRole("ROLE_COLAB_LIST_DESLIGAR");
			pendenciaAC.setMsg("Confirma exclusão da solicitação de desligamento?");
			pendenciaAC.setAction("removePendenciaACSolicitacaoDesligamento.action?colaboradorId="+ colaborador.getId());

			pendenciaACs.add(pendenciaAC);
		}

		return pendenciaACs;
	}

	public void cancelarSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem, String empresaCodigoAC, String grupoAC) throws Exception
	{
		this.religaColaborador(colaborador.getId());
		gerenciadorComunicacaoManager.enviaMensagemCancelamentoSolicitacaoDesligamentoAC(colaborador, mensagem, empresaCodigoAC, grupoAC);
		auditoriaManager.auditaCancelamentoSolicitacoNoAC(colaborador, mensagem);
	}

	public Collection<Colaborador> findAdmitidosHaDiasSemEpi(Collection<Integer> dias, Long empresaId)
	{
		return getDao().findAdmitidosHaDiasSemEpi(dias, empresaId);
	}

	public Collection<Colaborador> findAguardandoEntregaEpi(Collection<Integer> diasLembrete, Long empresaId)
	{
		return getDao().findAguardandoEntregaEpi(diasLembrete, empresaId);
	}

	public void setCandidatoNull(Long candidatoId) 
	{
		getDao().setCandidatoNull(candidatoId);
	}

	public Collection<Colaborador> findParentesByNome(Long colaboradorId, Long empresaId, String... nomes) 
	{
		Collection<Colaborador> parentes = new HashSet<Colaborador>();
		
		for (String nome : nomes)
		{
			if (nome != null && nome.length() >= 4)
			{
				parentes.addAll(getDao().findParentesByNome(colaboradorId, nome, empresaId));
			}
		}
		
		return parentes;
	}

	public boolean pertenceEmpresa(Long colaboradorId, Long empresaId) 
	{
		Colaborador colaborador = findByIdProjectionEmpresa(colaboradorId);
		return colaborador.getEmpresa().getId().equals(empresaId);
	}

	public Collection<Colaborador> triar(Long solicitacaoId, String escolaridade, String sexo, String idadeMin, String idadeMax, String[] faixasCheck, String[] areasCheck, boolean exibeCompatibilidade, Integer percentualMinimo, boolean opcaoTodasEmpresas, Long... empresaIds) throws Exception 
	{
		Date dataNascIni = null;
		Date dataNascFim = null;
		Date hoje = new Date();

		if( isNotBlank(idadeMin) && !idadeMin.equals("0"))
			dataNascIni = DateUtil.incrementaAno(hoje, (-1)*(Integer.parseInt(idadeMin)));

		if( isNotBlank(idadeMax) && !idadeMax.equals("0"))
			dataNascFim = DateUtil.incrementaAno(hoje, (-1)*Integer.parseInt(idadeMax));

		Solicitacao solicitacao = solicitacaoManager.findByIdProjection(solicitacaoId);
		Long faixaSolicitacaoId = solicitacao.getFaixaSalarial().getId();

		Long[] competenciasIdsFaixaSolicitacao = configuracaoNivelCompetenciaManager.findCompetenciasIdsConfiguradasByFaixaSolicitacao(faixaSolicitacaoId);
		Integer pontuacaoMaxima = configuracaoNivelCompetenciaManager.somaConfiguracoesByFaixa(faixaSolicitacaoId);

		if(exibeCompatibilidade && pontuacaoMaxima == null)
			throw new Exception("Não existe configuração de nível de competência para a faixa salarial desta solictação.");

		Collection<Colaborador> colaboradores = getDao().triar(empresaIds, escolaridade, sexo, dataNascIni, dataNascFim, faixasCheck, LongUtil.arrayStringToArrayLong(areasCheck), competenciasIdsFaixaSolicitacao, exibeCompatibilidade, opcaoTodasEmpresas);
		double compatibilidade;

		if (exibeCompatibilidade && pontuacaoMaxima > 0)
		{
			Collection<Colaborador> colabs = new ArrayList<Colaborador>();
			for (Colaborador colaborador : colaboradores) 
			{
				compatibilidade = (Double.valueOf(colaborador.getSomaCompetencias()) / pontuacaoMaxima) * 100.0;
				compatibilidade = (compatibilidade > 100.0) ? 100.0 : compatibilidade;

				if (compatibilidade >= percentualMinimo) 
				{
					colaborador.setPercentualCompatibilidade(compatibilidade);
					colabs.add(colaborador);
				}
			}

			return colabs;

		} else {
			return colaboradores;
		}
	}

	public void insertColaboradoresSolicitacao(Long[] colaboradoresIds, Solicitacao solicitacao, char statusCandidatoSolicitacao) throws Exception
	{
		Colaborador colaborador = null;
		Candidato candidato = null;
		Collection<String> candidatosIds = new ArrayList<String>();

		// Atualiza candidato do colaborador
		for (Long colaboradorId : colaboradoresIds) {

			colaborador = (Colaborador) findByIdComHistoricoConfirmados(colaboradorId);

			colaborador.setColaboradorIdiomas(colaboradorIdiomaManager.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()}));
			colaborador.setExperiencias(experienciaManager.findByColaborador(colaborador.getId()));
			colaborador.setFormacao(formacaoManager.findByColaborador(colaborador.getId()));

			candidato = candidatoManager.saveOrUpdateCandidatoByColaborador(colaborador);
			candidatosIds.add(candidato.getId().toString());

			colaborador.setCandidato(candidato);

			update(colaborador);
		}

		// Grava colaboradores na solicitação
		candidatoSolicitacaoManager.insertCandidatos(candidatosIds.toArray(new String[candidatosIds.size()]), solicitacao, statusCandidatoSolicitacao);
	}

	//sou feio mais tenho teste, heheheh
	public Collection<Colaborador> ordenaByMediaPerformance(Collection<Colaborador> colaboradores)
	{
		HashMap<Long, Double> performaces = new HashMap<Long, Double>();
		HashMap<Long, Double> qtdColaboradores = new HashMap<Long, Double>();

		for (Colaborador colaborador : colaboradores) 
		{

			Double mediaColab = performaces.get(colaborador.getId()) == null ? 0.0: performaces.get(colaborador.getId());
			Double qtdColab = qtdColaboradores.get(colaborador.getId()) == null ? 0.0: qtdColaboradores.get(colaborador.getId());

			performaces.put(colaborador.getId(), mediaColab + colaborador.getPerformanceDouble());
			qtdColaboradores.put(colaborador.getId(), qtdColab + 1.0);
		}

		for (Colaborador colaborador : colaboradores)
			colaborador.setMediaPerformance(performaces.get(colaborador.getId()) / qtdColaboradores.get(colaborador.getId()) );

		CollectionUtil<Colaborador> cul = new CollectionUtil<Colaborador>();
		return cul.sortCollectionDouble(colaboradores, "mediaPerformance");
	}

	public Collection<Colaborador> findParaLembreteTerminoContratoTemporario(Collection<Integer> diasLembretes, Long empresaId)
	{
		return getDao().findParaLembreteTerminoContratoTemporario(diasLembretes, empresaId);
	}
	
	public Collection<Colaborador> findHabilitacaAVencer(Collection<Integer> diasLembrete, Long empresaId)
	{
		return getDao().findHabilitacaAVencer(diasLembrete, empresaId);
	}
	
	public String getReciboPagamento(Colaborador colaborador, Date mesAno) throws Exception 
	{
		return acPessoalClientColaborador.getReciboPagamento(colaborador, mesAno);
	}
	
	public String getReciboDeDecimoTerceiro(Colaborador colaborador, String dataCalculo) throws Exception 
	{
		return acPessoalClientColaborador.getReciboDeDecimoTerceiro(colaborador, dataCalculo);
	}
	
	public String getDeclaracaoRendimentos(Colaborador colaborador, String ano) throws Exception 
	{
		return acPessoalClientColaborador.getDeclaracaoRendimentos(colaborador, ano);
	}
	
	public String[] getDatasDecimoTerceiroPorEmpregado(Colaborador colaborador) throws Exception 
	{
		return acPessoalClientColaborador.getDatasDecimoTerceiroPorEmpregado(colaborador);
	}
	
	public Collection<Colaborador> findFormacaoEscolar(Long empresaId, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> cargoIds) throws Exception
	{
		Collection<Colaborador> colaboradores = findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaIds, cargoIds, null, null, null, null, null, null, null, null, SituacaoColaborador.ATIVO, null, empresaId);
		Collection<Formacao> formacoes;
		Collection<ColaboradorIdioma> colaboradorIdiomas;
		
		for (Colaborador colaborador : colaboradores) {

			formacoes = formacaoManager.findByColaborador(colaborador.getId());
			if (formacoes != null && !formacoes.isEmpty())
				colaborador.setFormacao(formacoes);
			
			colaboradorIdiomas = colaboradorIdiomaManager.findByColaborador(colaborador.getId()); 
			if (colaboradorIdiomas != null && !colaboradorIdiomas.isEmpty())
				colaborador.setColaboradorIdiomas(colaboradorIdiomas);
		}
		
		return colaboradores;
	}

	public Collection<Colaborador> findByEmpresaAndStatusAC(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, int statusAC, boolean semcodigoAc, boolean comNaoIntegraAC, String situacaoColaborador, boolean primeiroHistorico, String... order)
	{
		return getDao().findByEmpresaAndStatusAC(empresaId, estabelecimentosIds, areasIds, statusAC, semcodigoAc, comNaoIntegraAC, situacaoColaborador, primeiroHistorico, order);
	}

	public void desvinculaCandidato(Long candidatoId) 
	{
		getDao().desvinculaCandidato(candidatoId);
	}

	public Collection<Colaborador> findAguardandoDesligamento(Long empresaId, Long[] areasIdsPorResponsavel) 
	{
		return getDao().findAguardandoDesligamento(empresaId, areasIdsPorResponsavel);
	}

	public void removeComDependencias(Long id) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			getDao().removeComDependencias(id);
			mensagemManager.removerMensagensViculadasByColaborador(new Long[]{id});
		}
		catch (Exception e)	{
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	public void insereNonoDigitoCelular(Long[] ufId) throws Exception {
		
		Collection<Colaborador> colaboradores = getDao().findByEstadosCelularOitoDigitos(ufId);
		
		for (Colaborador colaborador : colaboradores) {
			colaborador.getContato().setFoneCelular("9" + colaborador.getContato().getFoneCelular());
			
			if (!colaborador.isNaoIntegraAc() && colaborador.getEmpresa().isAcIntegra()){
				acPessoalClientColaborador.atualizar(bindEmpregado(colaborador, colaborador.getEmpresa().getCodigoAC()), colaborador.getEmpresa());
			}
			
			getDao().update(colaborador);
			
		}
		
	}
	
	public void reprovaSolicitacaoDesligamento(Long colaboradorId) throws Exception{
		getDao().atualizaSolicitacaoDesligamento(null, null, null, null, null, null, colaboradorId);
	}
	
	public void setDataSolicitacaoDesligamentoACByDataDesligamento(Long empresaId){
		getDao().setDataSolicitacaoDesligamentoACByDataDesligamento(empresaId);
	}
	
	public void setSolicitacao(Long colaboradorId, Long solicitacaoId) 
	{
		getDao().setSolicitacao(colaboradorId, solicitacaoId);
	}

	public Collection<Usuario> findUsuarioByAreaEstabelecimento(Long[] areasIds, Long[] estabelecimentosIds)
	{
		return getDao().findUsuarioByAreaEstabelecimento(areasIds, estabelecimentosIds);
	}

	public void reenviaAguardandoContratacao(Empresa empresa) throws Exception 
	{
		Collection<Colaborador> colaboradores = getDao().findByEmpresaAndStatusAC(empresa.getId(), null, null, StatusRetornoAC.AGUARDANDO, true, false, SituacaoColaborador.ATIVO, true, "c.nome");

		for (Colaborador colaborador : colaboradores){
			try {

				colaborador.getHistoricoColaborador().setAreaOrganizacional(colaborador.getAreaOrganizacional());
				colaborador.getHistoricoColaborador().setEstabelecimento(colaborador.getEstabelecimento());
				colaborador.getHistoricoColaborador().setFaixaSalarial(colaborador.getFaixaSalarial());
				colaborador.getHistoricoColaborador().setIndice(colaborador.getIndice());
				acPessoalClientColaborador.contratar(bindEmpregado(colaborador, empresa.getCodigoAC()), historicoColaboradorManager.bindSituacao(colaborador.getHistoricoColaborador(), empresa.getCodigoAC()), empresa);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Erro ao tentar enciar colaborador: " + colaborador.getNome() + " (Código AC: " + colaborador.getCodigoAC() + ").");
			}
		}
	}

	public void reenviaSolicitacaoDesligamento(Empresa empresa) throws Exception 
	{
		Collection<HistoricoColaborador> historicosColaboradores = historicoColaboradorManager.findByEmpresaComHistorico(empresa.getId(), StatusRetornoAC.AGUARDANDO);
		acPessoalClientColaborador.solicitacaoDesligamentoAc(historicosColaboradores, empresa);
	}

	public void confirmaReenvios(TFeedbackPessoalWebService tFeedbackPessoalWebService, Empresa empresa) throws Exception 
	{
		acPessoalClientColaborador.confirmarReenvio(tFeedbackPessoalWebService, empresa);
	}
	
	public void setColaboradorPeriodoExperienciaAvaliacaoManager(ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager) 
	{
		this.colaboradorPeriodoExperienciaAvaliacaoManager = colaboradorPeriodoExperienciaAvaliacaoManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) 
	{
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) 
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public void setAuditoriaManager(AuditoriaManager auditoriaManager) 
	{
		this.auditoriaManager = auditoriaManager;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public CandidatoIdiomaManager getCandidatoIdiomaManager() {
		return candidatoIdiomaManager;
	}

	public void setCandidatoIdiomaManager(CandidatoIdiomaManager candidatoIdiomaManager) {
		this.candidatoIdiomaManager = candidatoIdiomaManager;
	}

	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager) {
		this.solicitacaoExameManager = solicitacaoExameManager;
	}
}