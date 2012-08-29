package com.fortes.rh.business.geral;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
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
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CertificadoMilitar;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.TituloEleitoral;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
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
import com.fortes.rh.model.geral.DynaRecord;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.relatorio.CartaoAcompanhamentoExperienciaVO;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
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
import com.opensymphony.util.BeanUtils;
import com.opensymphony.xwork.ActionContext;

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
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private UsuarioMensagemManager usuarioMensagemManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private SolicitacaoManager solicitacaoManager;
	
	public void enviaEmailAniversariantes() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		if(parametrosDoSistema.getEnviarEmail())
		{
			Collection<Empresa> empresas = empresaManager.findByCartaoAniversario();
			Date data = new Date();
			int dia = DateUtil.getDia(data);
			int mes = DateUtil.getMes(data);
			
			char barra = java.io.File.separatorChar;
			String path = ArquivoUtil.getSystemConf().getProperty("sys.path");
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
					String body = "Cartão em anexo, feliz aniversário.";

					Collection<Colaborador> colaboradores = Arrays.asList(new Colaborador());
					DataSource[] files = ArquivoUtil.montaRelatorio(parametros, colaboradores, "cartaoAniversariante.jasper");

					if(StringUtils.isNotEmpty(aniversariante.getContato().getEmail())){
						mail.send(empresa, subject, files, body, aniversariante.getContato().getEmail());		
					}

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

	public Integer getCount(Map parametros)
	{
		return getDao().getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	}

	public Collection findList(int page, int pagingSize, Map parametros)
	{
		return getDao().findList(page, pagingSize, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	}

	public Collection<Colaborador> findByAreasOrganizacionalIds(Integer page, Integer pagingSize, Long[] areasIds, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId, boolean comHistColaboradorFuturo)
	{
		return getDao().findByAreaOrganizacionalIds(page, pagingSize, areasIds, colaborador, dataAdmissaoIni, dataAdmissaoFim, empresaId, comHistColaboradorFuturo);
	}

	public Colaborador findColaboradorPesquisa(Long id, Long empresaId)
	{
		return getDao().findColaboradorPesquisa(id, empresaId);
	}

	public boolean insert(Colaborador colaborador, Double salarioColaborador, Long idCandidato, Collection<Formacao> formacaos,
			Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Solicitacao solicitacao, Empresa empresa) throws Exception
	{
		colaborador.setUsuario(null);

		if (colaborador.getPessoal().getCtps().getCtpsUf().getId() == null)
			colaborador.getPessoal().getCtps().setCtpsUf(null);

		if (colaborador.getPessoal().getRgUf().getId() == null)
			colaborador.getPessoal().setRgUf(null);
		
		if(colaborador.getSolicitacao() != null && colaborador.getSolicitacao().getId() == null)
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
			candidatoManager.updateSetContratado(idCandidato);
			
			// Transfere as competências configuradas no candidato
			Collection<ConfiguracaoNivelCompetencia> configuracoesNiveisCompetencias = configuracaoNivelCompetenciaManager.findByCandidato(idCandidato);
			if (configuracoesNiveisCompetencias != null && configuracoesNiveisCompetencias.size() > 0)
			{
				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracoesNiveisCompetencias)
					configuracaoNivelCompetencia.setCandidato(null);
				
				ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
				configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
				configuracaoNivelCompetenciaColaborador.setFaixaSalarial(historico.getFaixaSalarial());
				configuracaoNivelCompetenciaColaborador.setData(historico.getData());
				
				configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(configuracoesNiveisCompetencias, configuracaoNivelCompetenciaColaborador);
			}

			// Contratação COM solicitação
			if (solicitacao.getId() != null)
			{
				colaborador.setSolicitacao(solicitacao);
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
			contratarColaborador(colaborador, historico, empresa);

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

		empregado.setLogradouro(colaborador.getEndereco().getLogradouro());
		empregado.setNumero(colaborador.getEndereco().getNumero());
		empregado.setComplemento(colaborador.getEndereco().getComplemento());
		empregado.setBairro(colaborador.getEndereco().getBairro());
		empregado.setCep(colaborador.getEndereco().getCep().equals("") ? "" : colaborador.getEndereco().getCep());

		if(colaborador.getEndereco().getCidade().getId() != null)
		{
			Cidade cidade = cidadeManager.findByIdProjection(colaborador.getEndereco().getCidade().getId());
			empregado.setCidadeCodigoAC(cidade.getCodigoAC());
			empregado.setUfSigla(cidade.getUf().getSigla());
		}

		empregado.setCpf(colaborador.getPessoal().getCpf());
		empregado.setPis(colaborador.getPessoal().getPis());
		empregado.setSexo(String.valueOf(colaborador.getPessoal().getSexo()));
		if (colaborador.getPessoal().getDataNascimento() != null)
			empregado.setDataNascimento(DateUtil.formataDiaMesAno(colaborador.getPessoal().getDataNascimento()));
		empregado.setEscolaridade(colaborador.getPessoal().getEscolaridade());
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

		empregado.setIdentidadeNumero(colaborador.getPessoal().getRg());
		empregado.setIdentidadeOrgao(colaborador.getPessoal().getRgOrgaoEmissor());
		if (colaborador.getPessoal().getRgDataExpedicao() != null)
			empregado.setIdentidadeDataExpedicao(DateUtil.formataDiaMesAno(colaborador.getPessoal().getRgDataExpedicao()));

		if (colaborador.getPessoal().getRgUf() != null && colaborador.getPessoal().getRgUf().getId() != null)
		{
			Estado estado = estadoManager.findById(colaborador.getPessoal().getRgUf().getId());
			empregado.setIdentidadeUF(estado.getSigla());
		}

		if (colaborador.getPessoal().getTituloEleitoral() != null) {
			if (colaborador.getPessoal().getTituloEleitoral().getTitEleitNumero() != null)
				empregado.setTituloNumero(colaborador.getPessoal().getTituloEleitoral().getTitEleitNumero());
			if (colaborador.getPessoal().getTituloEleitoral().getTitEleitSecao() != null)
				empregado.setTituloSecao(colaborador.getPessoal().getTituloEleitoral().getTitEleitSecao());
			if (colaborador.getPessoal().getTituloEleitoral().getTitEleitZona() != null)
				empregado.setTituloZona(colaborador.getPessoal().getTituloEleitoral().getTitEleitZona());
		}

		
		if (colaborador.getPessoal().getCertificadoMilitar() != null) {
			if (colaborador.getPessoal().getCertificadoMilitar().getCertMilNumero() != null)
				empregado.setCertificadoMilitarNumero(colaborador.getPessoal().getCertificadoMilitar().getCertMilNumero());
			if (colaborador.getPessoal().getCertificadoMilitar().getCertMilSerie() != null)
				empregado.setCertificadoMilitarSerie(colaborador.getPessoal().getCertificadoMilitar().getCertMilSerie());
			if (colaborador.getPessoal().getCertificadoMilitar().getCertMilTipo() != null)
				empregado.setCertificadoMilitarTipo(colaborador.getPessoal().getCertificadoMilitar().getCertMilTipo());
		}

		if (colaborador.getHabilitacao() != null) {
			if (colaborador.getHabilitacao().getNumeroHab() != null)
				empregado.setHabilitacaoNumero(colaborador.getHabilitacao().getNumeroHab());
			if (colaborador.getHabilitacao().getEmissao() != null)
				empregado.setHabilitacaoEmissao(DateUtil.formataDiaMesAno(colaborador.getHabilitacao().getEmissao()));
			if (colaborador.getHabilitacao().getVencimento() != null)
				empregado.setHabilitacaoVencimento(DateUtil.formataDiaMesAno(colaborador.getHabilitacao().getVencimento()));
			if (colaborador.getHabilitacao().getCategoria() != null)
				empregado.setHabilitacaoCategoria(colaborador.getHabilitacao().getCategoria());
		}

		if (colaborador.getPessoal().getCtps() != null) {
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
		
		if(colaborador.getVinculo().equals(Vinculo.ESTAGIO))
			empregado.setTipoAdmissao("00");
		else{
			if (colaborador.getVinculo().equals(Vinculo.TEMPORARIO))
				empregado.setVinculo(50);
			else{
				if (colaborador.getVinculo().equals(Vinculo.APRENDIZ)){
					empregado.setVinculo(55);
					empregado.setCategoria(07);
				}
				else{
					empregado.setTipoAdmissao("20");
					empregado.setVinculo(10);
				}
			}
		}

		return empregado;
	}
	
    public String getVinculo(String admissaoTipo, Integer admissaoVinculo, Integer admissaoCategoria) {
        String colocacao;
        if (admissaoTipo!=null && admissaoTipo.equals("00")) {
            colocacao = Vinculo.ESTAGIO;
        } else {
            if (admissaoVinculo!=null && admissaoVinculo == 50) {
                colocacao = Vinculo.TEMPORARIO;
            } else {
                if (admissaoCategoria!=null && admissaoCategoria == 7) {
                    colocacao = Vinculo.APRENDIZ;
                } else {
                    colocacao = Vinculo.EMPREGO;
                }
            }
        }
        return colocacao;
    }

	private void contratarColaborador(Colaborador colaborador, HistoricoColaborador historico, Empresa empresa) throws AddressException, MessagingException,Exception
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
			String strException = "Não foi possível contratar este colaborador no AC Pessoal.";
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

	public void update(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias,
			Empresa empresa, boolean editarHistorico, Double salarioColaborador) throws Exception
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

			try {
				
				historicoColaboradorManager.update(historicoColaborador);
			} catch (Exception e) {
				e.printStackTrace();
			}
			colaborador.setHistoricoColaborador(historicoColaborador);
		}
		
		// Flush necessário quando houver uma operação com banco/sistema externo.
		// garante que erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if (!colaborador.isNaoIntegraAc() && empresa.isAcIntegra())
		{
			if (editarHistorico)// deleta o registro na CTT do AC e cria um novo
				contratarColaborador(colaborador, historicoColaborador, empresa);
			else
				acPessoalClientColaborador.atualizar(bindEmpregado(colaborador, empresa.getCodigoAC()), empresa);
		}
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
		{
			for (Formacao f : formacaos)
			{
				f.setId(null);
				f.atualizaColaboradorECandidato(colaborador);

				formacaoManager.save(f);
			}
		}

		if (idiomas != null && !idiomas.isEmpty())
		{
			for (CandidatoIdioma c : idiomas)
			{
				ColaboradorIdioma ci = new ColaboradorIdioma();
				ci.setId(null);
				ci.setColaborador(colaborador);
				ci.setIdioma(c.getIdioma());
				ci.setNivel(c.getNivel());

				colaboradorIdiomaManager.save(ci);
			}
		}

		if (experiencias != null && !experiencias.isEmpty())
		{
			for (Experiencia e : experiencias)
			{
				e.setId(null);
				e.atualizaColaboradorECandidato(colaborador);
				
				if (!e.possuiCargo())
					e.setCargo(null);

				experienciaManager.save(e);
			}
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

	public boolean desligaColaboradorAC(String codigoAC, Empresa empresa, Date dataDesligamento)
	{
		return getDao().desligaByCodigo(codigoAC, empresa, dataDesligamento);
	}

	public Long religaColaboradorAC(String codigoAC, String empresaCodigo, String grupoAC)
	{
		Long colaboradorId = getDao().findByCodigoAC(codigoAC, empresaCodigo, grupoAC).getId();

		candidatoManager.reabilitaByColaborador(colaboradorId);
		mensagemManager.removeMensagemDesligamento(colaboradorId);
		getDao().religaColaborador(colaboradorId);

		return colaboradorId;
	}

	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId)
	{
			return getDao().findColaboradorUsuarioByCpf(cpf, empresaId);
	}

	public Colaborador findTodosColaboradorCpf(String cpf, Long empresaId, Long colaboradorId)
	{
		String cpfSemMascara = cpf.replaceAll("\\.", "").replaceAll("-", "").trim();
		if(cpfSemMascara.equals(""))
			return null;
		else
			return getDao().findTodosColaboradorCpf(cpfSemMascara, empresaId, colaboradorId);
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
		body.append("sua senha do sistema FortesRH é : " + senha + "<br>");
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
		return findBycandidato(candidatoId, empresaId).size() > 0;
	}

	public Collection<Colaborador> findBycandidato(Long candidatoId, Long empresaId)
	{
		return getDao().findbyCandidato(candidatoId, empresaId);
	}

//	@SuppressWarnings("deprecation")
//	public void aplicaPromocaoNoColaborador(Long candidatoId, Long colaboradorId, Solicitacao solicitacao) throws Exception
//	{
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//
//		try
//		{
//			candidatoManager.updateSetContratado(candidatoId);
//
//			Double valorAux = Double.valueOf(solicitacao.getValorPromocao());
//
//			Colaborador colaborador = new Colaborador();
//			colaborador.setId(colaboradorId);
//
//			solicitacao = solicitacaoManager.findByIdProjectionAreaFaixaSalarial(solicitacao.getId());
//
//			ReajusteColaborador reajusteTmp = new ReajusteColaborador();
//			reajusteTmp.setColaborador(colaborador);
//
//			reajusteTmp.setFaixaSalarialProposta(solicitacao.getFaixaSalarial());
//			reajusteTmp.setSalarioProposto(valorAux);
//			reajusteTmp.setAreaOrganizacionalProposta(solicitacao.getAreaOrganizacional());
//
//			HistoricoColaborador historico = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());
//			HistoricoColaborador historicoAnterior = historico;
//
//			reajusteTmp.setSalarioAtual(historico.getSalarioCalculado());
//			reajusteTmp.setFaixaSalarialAtual(historico.getFaixaSalarial());
//			reajusteTmp.setAreaOrganizacionalProposta(solicitacao.getAreaOrganizacional());
//			reajusteTmp.setTipoSalarioAtual(historico.getTipoSalario());
//			reajusteTmp.setTipoSalarioProposto(solicitacao.getFaixaSalarial().getFaixaSalarialHistoricoAtual().getTipo());
//
//			HistoricoColaborador historicoColaborador = new HistoricoColaborador();
//			historicoColaborador.setAreaOrganizacional(solicitacao.getAreaOrganizacional());
//			historicoColaborador.setColaborador(colaborador);
//			historicoColaborador.setData(new Date());
//			historicoColaborador.setMotivo(HistoricoColaboradorUtil.getMotivoReajuste(reajusteTmp, historico));
//			historicoColaborador.setSalario(valorAux);
//			historicoColaborador.setReajusteColaborador(null);
//			historicoColaborador.setHistoricoAnterior(historicoAnterior);
//
//			historicoColaboradorManager.save(historicoColaborador);
//
//			transactionManager.commit(status);
//		}
//		catch (Exception e)
//		{
//			transactionManager.rollback(status);
//			throw e;
//		}
//
//	}

	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds)
	{
		return getDao().findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentoIds);
	}

	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds)
	{
		return getDao().findByAreasOrganizacionaisEstabelecimentos(areasOrganizacionaisIds, estabelecimentoIds, null, null);
	}

	public Colaborador findByCodigoAC(String codigo, Empresa empresa)
	{
		return getDao().findByCodigoAC(codigo, empresa);
	}

	public Colaborador findColaboradorById(Long id)
	{
		return getDao().findColaboradorById(id);
	}

	public boolean setCodigoColaboradorAC(String codigo, Long id)
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

	public Collection<Colaborador> getColaboradoresIntegraAc(Collection<Colaborador> colaboradores)
	{
		Collection<Colaborador> retorno = new ArrayList<Colaborador>();

		for (Colaborador colaborador : colaboradores)
		{
			if (!colaborador.isNaoIntegraAc())
				retorno.add(colaborador);
		}

		return retorno;
	}

	public Colaborador findByIdProjectionUsuario(Long colaboradorId)
	{
		return getDao().findByIdProjectionUsuario(colaboradorId);
	}

	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, CamposExtras camposExtras, Long empresaId, String order, Date dataAdmissaoIni, Date dataAdmissaoFim, String sexo, Integer[] tempoServicoIni, Integer[] tempoServicoFim)
	{
		return getDao().findAreaOrganizacionalByAreas(habilitaCampoExtra, estabelecimentosIds, areasIds, camposExtras, empresaId, order, dataAdmissaoIni, dataAdmissaoFim, sexo, tempoServicoIni, tempoServicoFim);
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

	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim, String agruparPor)
			throws Exception
	{
		Collection<Colaborador> colaboradors = getDao().findColaboradoresMotivoDemissao(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim, agruparPor);
		if (colaboradors == null || colaboradors.isEmpty())
			throw new Exception("Não existem dados para o filtro informado.");

		return colaboradors;
	}

	public Collection<MotivoDemissaoQuantidade> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds,
			Date dataIni, Date dataFim) throws Exception
	{
		List<Object[]> lista = getDao().findColaboradoresMotivoDemissaoQuantidade(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim);

		if (lista == null || lista.isEmpty())
			throw new Exception("Não existem dados para o filtro informado.");

		Collection<MotivoDemissaoQuantidade> motivos = new ArrayList<MotivoDemissaoQuantidade>();
		for (Object[] item : lista)
		{
			if(item[0] == null)
				motivos.add(new MotivoDemissaoQuantidade("[não informado]", getDao().countSemMotivos(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim)));
			else
				motivos.add(new MotivoDemissaoQuantidade((String) item[0], (Integer) item[1]));
		}
		
		CollectionUtil<MotivoDemissaoQuantidade> util = new CollectionUtil<MotivoDemissaoQuantidade>();
		
		// Ordena por quantidade 
		motivos = util.sortCollectionDesc(motivos, "quantidade");

		return motivos;
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

	public void solicitacaoDesligamentoAc(Date dataSolicitacaoDesligamento, String observacaoDemissao, Long motivoId, Long colaboradorId, Empresa empresa) throws Exception 
	{
		Collection<HistoricoColaborador> historicosColaborador = new ArrayList<HistoricoColaborador>();
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaboradorId);
		historicoColaborador.setDataSolicitacaoDesligamento(dataSolicitacaoDesligamento);
		historicoColaborador.setObsACPessoal(observacaoDemissao);
		historicosColaborador.add(historicoColaborador);
		
		if(acPessoalClientColaborador.solicitacaoDesligamentoAc(historicosColaborador, empresa))
		{
			getDao().atualizaDataSolicitacaoDesligamentoAc(dataSolicitacaoDesligamento, colaboradorId);
			desligaColaborador(null, null, observacaoDemissao, motivoId, colaboradorId);
		}
	}
	
	public void desligaColaborador(Boolean desligado, Date dataDesligamento, String observacaoDemissao, Long motivoDemissaoId, Long colaboradorId) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try
		{
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBean("usuarioManager");
			usuarioManager.desativaAcessoSistema(colaboradorId);
			candidatoManager.habilitaByColaborador(colaboradorId);
			candidatoSolicitacaoManager.setStatusByColaborador(colaboradorId, StatusCandidatoSolicitacao.INDIFERENTE);
			getDao().desligaColaborador(desligado, dataDesligamento, observacaoDemissao, motivoDemissaoId, colaboradorId);
			
			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}
	
	public void religaColaborador(Long colaboradorId) throws Exception
	{
		UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBean("usuarioManager");
		usuarioManager.reativaAcessoSistema(colaboradorId);
		candidatoManager.reabilitaByColaborador(colaboradorId);
		candidatoSolicitacaoManager.setStatusByColaborador(colaboradorId, StatusCandidatoSolicitacao.APROMOVER);
		getDao().religaColaborador(colaboradorId);
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
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(empresaId, AreaOrganizacional.TODAS, null);
		areas = areaOrganizacionalManager.montaFamilia(areas);

		for (Colaborador colaborador : colaboradorByHistoricoColaboradors)
		{
			colaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areas, colaborador.getAreaOrganizacional().getId()));
		}

		CollectionUtil<Colaborador> cutil = new CollectionUtil<Colaborador>();
		colaboradorByHistoricoColaboradors = cutil.sortCollectionStringIgnoreCase(colaboradorByHistoricoColaboradors, "descricaoEstabelecimentoAreaOrganizacional");

		return colaboradorByHistoricoColaboradors;
	}

	public Collection<Colaborador> ordenaPorEstabelecimentoArea(Long empresaId, Collection<Colaborador> colaboradors)
			throws Exception
	{
		// monta familia das areas
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(null, AreaOrganizacional.TODAS, null);
		areas = areaOrganizacionalManager.montaFamilia(areas);

		for (Colaborador colaboradorTmp : colaboradors)
		{
			colaboradorTmp.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areas, colaboradorTmp.getAreaOrganizacional().getId()));
		}

		CollectionUtil<Colaborador> cu1 = new CollectionUtil<Colaborador>();
		colaboradors = cu1.sortCollectionStringIgnoreCase(colaboradors, "descricaoEmpresaEstabelecimentoAreaOrganizacional");

		return colaboradors;
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
			throw new ColecaoVaziaException("O reajuste não pode ser aplicado enquanto os cadastros destes colaboradores não for concluído no AC Pessoal:<br>"
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

	public Collection<Colaborador> findListComHistoricoFuturo(int page, int pagingSize, Map parametros)
	{
		return getDao().findList(page, pagingSize, parametros, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);
	}

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

	public Collection<Colaborador> findAllSelect(Long... empresaIds)
	{
		return getDao().findAllSelect(empresaIds);
	}
	
	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado)
	{
		return getDao().findAllSelect(colaboradorIds, colabDesligado);
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
			
			criarMensagemAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível Editar o colaborador.");
		}
	}
	
	private void criarMensagemAtualizacaoInfoPessoais(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado, Long empresaId)
	{
		StringBuffer mensagem = new StringBuffer();
		
		mensagem.append("Dados originais: \n");
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getLogradouro()).equals(colaboradorAtualizado.getEndereco().getLogradouro()) ) 
			mensagem.append("Logradouro: " + colaboradorOriginal.getEndereco().getLogradouro() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getNumero()).equals(colaboradorAtualizado.getEndereco().getNumero()) ) 
			mensagem.append("Número: " + colaboradorOriginal.getEndereco().getNumero() + "\n");
		
		if (!StringUtils.defaultString(colaboradorOriginal.getEndereco().getComplemento()).equals(colaboradorAtualizado.getEndereco().getComplemento()) ) 
				mensagem.append("Complemento: " + colaboradorOriginal.getEndereco().getComplemento() + "\n");
			
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getBairro()).equals(colaboradorAtualizado.getEndereco().getBairro()) ) 
			mensagem.append("Bairro: " + colaboradorOriginal.getEndereco().getBairro()+ "\n");
		
		if(colaboradorAtualizado.getEndereco().getCidade() !=  null && !colaboradorAtualizado.getEndereco().getCidade().equals(colaboradorOriginal.getEndereco().getCidade()))
			mensagem.append("Cidade: " + colaboradorOriginal.getEndereco().getCidade().getNome()+ "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getUf().getSigla()).equals(colaboradorAtualizado.getEndereco().getUf().getSigla())) 
			mensagem.append("UF: " + colaboradorOriginal.getEndereco().getUf().getSigla() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getCepFormatado()).equals(colaboradorAtualizado.getEndereco().getCepFormatado()) ) 
			mensagem.append("CEP: " + colaboradorOriginal.getEndereco().getCepFormatado() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getContato().getEmail()).equals(colaboradorAtualizado.getContato().getEmail())) 
			mensagem.append("Email: " + colaboradorOriginal.getContato().getEmail() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getContato().getFoneFixo()).equals(colaboradorAtualizado.getContato().getFoneFixo())) 
			mensagem.append("Telefone Fixo: " + colaboradorOriginal.getContato().getFoneFixo() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getContato().getFoneCelular()).equals(colaboradorAtualizado.getContato().getFoneCelular())) 
			mensagem.append("Fone celular: " + colaboradorOriginal.getContato().getFoneCelular() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getPessoal().getEscolaridade()).equals((colaboradorAtualizado.getPessoal().getEscolaridade())) ) 
			mensagem.append("Escolaridade: " + colaboradorOriginal.getPessoal().getEscolaridadeDescricao() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getPessoal().getEstadoCivil()).equals((colaboradorAtualizado.getPessoal().getEstadoCivil())) ) 
			mensagem.append("Estado civil: " + colaboradorOriginal.getPessoal().getEstadoCivilDescricao() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getPessoal().getConjuge()).equals((colaboradorAtualizado.getPessoal().getConjuge())) ) 
			mensagem.append("Nome do conjuge: " + colaboradorOriginal.getPessoal().getConjuge() + "\n");
		
		if(colaboradorOriginal.getPessoal().getQtdFilhos() != ((colaboradorAtualizado.getPessoal().getQtdFilhos())) ) 
			mensagem.append("Quantidade de filhos: " + colaboradorOriginal.getPessoal().getQtdFilhos() == null ? 0 : colaboradorOriginal.getPessoal().getQtdFilhos() + "\n");
		
		mensagem.append("\n\n");
				
		mensagem.append("Dados atualizados:\n");		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getLogradouro()).equals(colaboradorAtualizado.getEndereco().getLogradouro()) ) 
			mensagem.append("Logradouro: " + colaboradorAtualizado.getEndereco().getLogradouro() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getNumero()).equals(colaboradorAtualizado.getEndereco().getNumero()) ) 
			mensagem.append("Número: " + colaboradorAtualizado.getEndereco().getNumero() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getComplemento()).equals(colaboradorAtualizado.getEndereco().getComplemento()) ) 
			mensagem.append("Complemento: " + colaboradorAtualizado.getEndereco().getComplemento() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getBairro()).equals(colaboradorAtualizado.getEndereco().getBairro()) ) 
			mensagem.append("Bairro: " + colaboradorAtualizado.getEndereco().getBairro() + "\n");
		
		if(colaboradorAtualizado.getEndereco().getCidade() !=  null && !colaboradorAtualizado.getEndereco().getCidade().equals(colaboradorOriginal.getEndereco().getCidade()))
			mensagem.append("Cidade: " + colaboradorAtualizado.getEndereco().getCidade().getNome() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getUf().getSigla()).equals(colaboradorAtualizado.getEndereco().getUf().getSigla()) ) 
			mensagem.append("UF: " + colaboradorAtualizado.getEndereco().getUf().getSigla() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getEndereco().getCepFormatado()).equals(colaboradorAtualizado.getEndereco().getCepFormatado()) ) 
			mensagem.append("CEP: " + colaboradorAtualizado.getEndereco().getCepFormatado() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getContato().getEmail()).equals(colaboradorAtualizado.getContato().getEmail())) 
			mensagem.append("Email: " + colaboradorAtualizado.getContato().getEmail() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getContato().getFoneFixo()).equals(colaboradorAtualizado.getContato().getFoneFixo())) 
			mensagem.append("Telefone Fixo: " + colaboradorAtualizado.getContato().getFoneFixo() + "\n");
		
		if(!StringUtils.defaultString(colaboradorOriginal.getContato().getFoneCelular()).equals(colaboradorAtualizado.getContato().getFoneCelular())) 
			mensagem.append("Fone celular: " + colaboradorAtualizado.getContato().getFoneCelular() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getPessoal().getEscolaridade()).equals((colaboradorAtualizado.getPessoal().getEscolaridade())) ) 
			mensagem.append("Escolaridade: " + colaboradorAtualizado.getPessoal().getEscolaridadeDescricao() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getPessoal().getEstadoCivil()).equals((colaboradorAtualizado.getPessoal().getEstadoCivil())) ) 
			mensagem.append("Estado civil: " + colaboradorAtualizado.getPessoal().getEstadoCivilDescricao() + "\n");

		if(!StringUtils.defaultString(colaboradorOriginal.getPessoal().getConjuge()).equals((colaboradorAtualizado.getPessoal().getConjuge())) ) 
			mensagem.append("Nome do conjuge: " + colaboradorAtualizado.getPessoal().getConjuge() + "\n");

		if(colaboradorOriginal.getPessoal().getQtdFilhos() != ((colaboradorAtualizado.getPessoal().getQtdFilhos())) ) 
			mensagem.append("Quantidade de filhos: " + colaboradorOriginal.getPessoal().getQtdFilhos() == null ? 0 : colaboradorOriginal.getPessoal().getQtdFilhos() + "\n");
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRole(empresaId, "ROLE_VISUALIZAR_MSG");
		
		usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", "geral/colaborador/prepareUpdate.action?colaborador.id=" + colaboradorOriginal.getId(), usuarioEmpresas, null, TipoMensagem.ENDERECO);
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
	
	// Refatorar Testes Samuel
	private Colaborador bindColaborador(Colaborador colaborador, TEmpregado empregado)
	{
		colaborador.setCodigoAC(empregado.getCodigoAC());
		colaborador.setNome(empregado.getNome());
		if(empregado.getNomeComercial() != null && !empregado.getNomeComercial().equals(""))
			colaborador.setNomeComercial(empregado.getNomeComercial());
		
		colaborador.setDataAdmissao(empregado.getDataAdmissaoFormatada());

		if (StringUtils.isNotBlank(empregado.getMatricula()))
			colaborador.setMatricula(empregado.getMatricula());
		else
			colaborador.setMatricula(empregado.getCodigoAC());

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
		
		//Se o valor de escolaridade for 12 - Continuar
		if((empregado.getEscolaridade()) != null)
		{
			if(colaborador.getPessoal().getEscolaridade() != null && colaborador.getPessoal().getEscolaridade().equals("Especialização") && empregado.getEscolaridade().equals("Superior Completo"))
				colaborador.getPessoal().setEscolaridade("Especialização");
			else
				colaborador.getPessoal().setEscolaridade(empregado.getEscolaridade());
		}
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
		
		return colaborador;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		return getDao().findByCodigoAC(empregadoCodigoAC, empresaCodigoAC, grupoAC);
	}

	public Long findByUsuario(Long usuarioId)
	{
		return getDao().findByUsuario(usuarioId);
	}

	public Integer getCountAtivos(Date dataIni, Date dataFim, Long empresaId)
	{
		return getDao().getCountAtivos(dataIni, dataFim, empresaId);
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

	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Long empresaId, Boolean somenteAtivos)
	{
		return getDao().findByNomeCpfMatricula(colaborador, empresaId, somenteAtivos);
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
		Colaborador colaboradorTmp = removeColaboradorDependencias(colaborador);

		if(empresa.isAcIntegra())
		{
			if( ! acPessoalClientColaborador.remove(colaboradorTmp, empresa))
				throw new IntegraACException("Não foi possível remover o colaborador no AC Pessoal.");
		}
	}

	public void cancelarContratacaoNoAC(TEmpregado empregado, HistoricoColaborador historicoColaborador, String mensagem) throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setCodigoAC(empregado.getEmpresaCodigoAC());
		empresa.setGrupoAC(empregado.getGrupoAC());
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(Long.valueOf(empregado.getId()));
		colaborador.setNomeComercial(empregado.getNomeComercial());
		colaborador.setEmpresa(empresa);
		colaborador.setHistoricoColaborador(historicoColaborador);
		
		historicoColaboradorManager.remove(historicoColaborador);
		removeColaboradorDependencias(colaborador);		
		gerenciadorComunicacaoManager.enviaMensagemCancelamentoContratacao(colaborador, mensagem);
	}
	
	public Colaborador removeColaboradorDependencias(Colaborador colaborador) 
	{
		formacaoManager.removeColaborador(colaborador);
		colaboradorIdiomaManager.removeColaborador(colaborador);
		experienciaManager.removeColaborador(colaborador);
		configuracaoNivelCompetenciaManager.removeColaborador(colaborador);
		configuracaoNivelCompetenciaColaboradorManager.removeColaborador(colaborador);
		colaboradorPeriodoExperienciaAvaliacaoManager.removeConfiguracaoAvaliacaoPeriodoExperiencia(colaborador);
		
		Colaborador	colaboradorTmp = getDao().findColaboradorByIdProjection(colaborador.getId());
		
		candidatoManager.habilitaByColaborador(colaborador.getId());
		candidatoSolicitacaoManager.setStatusByColaborador(colaborador.getId(), StatusCandidatoSolicitacao.INDIFERENTE);
		
		historicoColaboradorManager.removeColaborador(colaborador.getId());
				
		remove(colaborador.getId());
		
		if(colaboradorTmp.getCamposExtras() != null && colaboradorTmp.getCamposExtras().getId() != null)
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
		return getDao().findByAreaOrganizacionalIds(areasIds, null, null, colaborador, null, null, null, false);
	}

	public Colaborador findByIdHistoricoAtual(Long colaboradorId, boolean exibirSomenteAtivos)
	{
		return getDao().findByIdHistoricoAtual(colaboradorId, exibirSomenteAtivos);
	}
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds)
	{
		return getDao().findByIdHistoricoAtual(colaboradorIds);
	}

	public void migrarBairro(String bairro, String bairroDestino)
	{
		getDao().migrarBairro(bairro, bairroDestino);
	}

	public Collection<Colaborador> findByAreaOrganizacionalEstabelecimento(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentoIds, Boolean desligado)
	{
		return getDao().findByAreaOrganizacionalEstabelecimento(areaOrganizacionalIds, estabelecimentoIds, desligado);
	}

	public void validaQtdCadastros() throws Exception
	{
		//TODO remprot
//		int qtdColaboradorNoBanco = getDao().getCount(new String[]{"desligado"}, new Object[]{false});
//		
//		RPClient remprot = Autenticador.getRemprot();
//		if(remprot.getRegistered())
//		{
//			if(qtdColaboradorNoBanco >= remprot.getUserCount())
//				throw new Exception("Sua licença só permite cadastrar " + remprot.getUserCount() + " colaboradores.<br>Quantidade de colaboradores cadastrados no sistema: " + qtdColaboradorNoBanco);			
//		}	
//		else
//			if(qtdColaboradorNoBanco >= Autenticador.getQtdCadastrosVersaoDemo())
//				throw new Exception("Versão demonstração, só é permitido cadastrar " + Autenticador.getQtdCadastrosVersaoDemo() + " Colaboradores");
	}
	
	public String avisoQtdCadastros() throws Exception
	{
//		TODO remprot
//		int qtdColaboradorNoBanco = getDao().getCount(new String[]{"desligado"}, new Object[]{false});
//		
//		RPClient remprot = Autenticador.getRemprot();
//		if(remprot.getRegistered())
//		{
//			if((remprot.getUserCount() - (remprot.getUserCount() * 0.05 )) <= qtdColaboradorNoBanco )
//				return "Atualmente existem " + qtdColaboradorNoBanco + " colaboradores cadastrados no sistema.<br>Sua licença permite cadastrar " + remprot.getUserCount() + " colaboradores.";			
//		}	
		
		return null;
	}

	public Collection<String> findEmailsDeColaboradoresByPerfis(Collection<Perfil> perfis, Long empresaId)
	{
		if (perfis == null || perfis.isEmpty())
			return new ArrayList<String>();
		
		Long[] perfilIds = new CollectionUtil<Perfil>().convertCollectionToArrayIds(perfis);
		
		return getDao().findEmailsDeColaboradoresByPerfis(perfilIds, empresaId);
	}

	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa) throws Exception {
		Collection<Colaborador> admitidosHaDias = getDao().findAdmitidosHaDias(dias, empresa);
		
		admitidosHaDias = setFamiliaAreas(admitidosHaDias, empresa.getId());
		
		return admitidosHaDias;
	}

	public Collection<Colaborador> findAdmitidos(Long[] empresaIds, Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos) throws Exception {
		
		Collection<Colaborador> admitidos = getDao().findAdmitidos(dataIni, dataFim, areasIds, estabelecimentosIds, exibirSomenteAtivos);
		
		if (admitidos == null || admitidos.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
		
		admitidos = setFamiliaAreas(admitidos, empresaIds);
		
		return admitidos;
	}

	public Collection<Colaborador> findByNomeCpfMatriculaAndResponsavelArea(Colaborador colaborador, Long empresaId, Long colaboradorLogadoId)
	{
		return getDao().findByNomeCpfMatriculaAndResponsavelArea(colaborador, empresaId, colaboradorLogadoId);
	}

	public Long verificaColaboradorLogadoVerAreas()
	{
		Long colaboradorLogadoId = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession()).getId();
		if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))
			return null;//pega todas as áreas
		else
			if(colaboradorLogadoId == null)
				return 0L;//não traz nada, não existe área com responsável 0
		
		return colaboradorLogadoId;	
	}

	public Collection<Colaborador> findByCpf(String cpf, Long empresaId) {
		return getDao().findByCpf(cpf, empresaId);
	}

	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida) 
	{
		return getDao().findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenhoId, isAvaliado, respondida);
	}
	
	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados) 
	{
		return getDao().findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, isAvaliados);
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
	public Integer qtdColaboradoresByTurmas(Collection<Long> turmaIds) 
	{
		if (turmaIds != null && !turmaIds.isEmpty())
			return getDao().qtdColaboradoresByTurmas(turmaIds);
		else
			return 0;
	}

	public Integer getCountComHistoricoFuturoSQL(Map parametros)
	{
		return getDao().findComHistoricoFuturoSQL(parametros, 0, 0).size();
	}

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
			colaborador.setDataSolicitacaoDesligamentoAc((Date) array[13]);
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
				colaborador.setDataSolicitacaoDesligamentoAc((Date) array[13]);

			if(array[14] != null)
				colaborador.setAreaOrganizacionalId(((BigInteger)array[14]).longValue());
			
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
		
		Collection<Colaborador> colaboradores = getDao().findAdmitidosNoPeriodo(periodoIni, periodoFim, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa, menorPeriodo);
		Collection<Colaborador> colaboradoresComAvaliacoes = getDao().findComAvaliacoesExperiencias(periodoIni, periodoFim, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa, menorPeriodo);
		
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
			throw new Exception ("Não existem Colaboradores com os filtros selecionados" ); 
		
		return colaboradores;
	}
	
	public List<AcompanhamentoExperienciaColaborador> getAvaliacoesExperienciaPendentesPeriodo(Date dataReferencia, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck, Integer tempoDeEmpresa, Collection<PeriodoExperiencia> periodoExperiencias) throws Exception 
	{
		int menorPeriodo = 0;
		if(!periodoExperiencias.isEmpty())
			menorPeriodo = ((PeriodoExperiencia)periodoExperiencias.toArray()[0]).getDias();
		
		List<AcompanhamentoExperienciaColaborador> acompanhamentos = new ArrayList<AcompanhamentoExperienciaColaborador>();

		Collection<Colaborador> colaboradores = getDao().findAdmitidosNoPeriodo(null, dataReferencia, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa, menorPeriodo);
		Collection<Colaborador> colaboradoresRespostas = getDao().findComAvaliacoesExperiencias(null, dataReferencia, empresa, areasCheck, estabelecimentoCheck, tempoDeEmpresa, menorPeriodo);
		
		Date data;
		String performance;
		
		for (Colaborador colab : colaboradores)
		{
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

				experienciaColaborador.addPeriodo(data, performance);
			}
			
			acompanhamentos.add(experienciaColaborador);
		}
			
		if(acompanhamentos.isEmpty())
			throw new Exception ("Não existem Colaboradores com os filtros selecionados" ); 
		
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findByEmpresasIds(new Long[]{empresa.getId()}, AreaOrganizacional.TODAS);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

		for (AcompanhamentoExperienciaColaborador acompanhamento: acompanhamentos)
			acompanhamento.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, acompanhamento.getAreaOrganizacionalId()));
		
		Collections.sort(acompanhamentos);
		return acompanhamentos;
	}

	public Collection<Colaborador> findColabPeriodoExperiencia(Long empresaId, Date periodoIni, Date periodoFim, String[] avaliacaoCheck, String[] areasCheck, String[] estabelecimentoCheck, String[] colaboradorsCheck) throws Exception 
	{
		Collection<Colaborador> retorno = new ArrayList<Colaborador>();
		 	retorno = getDao().findColabPeriodoExperiencia(empresaId, periodoIni, periodoFim, StringUtil.stringToLong(avaliacaoCheck), StringUtil.stringToLong(areasCheck), StringUtil.stringToLong(estabelecimentoCheck), StringUtil.stringToLong(colaboradorsCheck), true);
		
		 if(retorno.isEmpty())
			throw new Exception("Não existem informações para os filtros selecionados");
		
		
		return retorno;
	}

	public Collection<Colaborador> findColabPeriodoExperienciaAgrupadoPorModelo(Long empresaId, Date periodoIni, Date periodoFim, Long avaliacaoId, String[] areasCheck, String[] estabelecimentoCheck, String[] colaboradorsCheck, boolean considerarAutoAvaliacao) throws Exception 
	{
		AvaliacaoDesempenhoManager avaliacaoDesempenhoManager = (AvaliacaoDesempenhoManager) SpringUtil.getBean("avaliacaoDesempenhoManager");
		Collection<AvaliacaoDesempenho> avaliacaoIds = avaliacaoDesempenhoManager.findIdsAvaliacaoDesempenho(avaliacaoId);
		CollectionUtil<AvaliacaoDesempenho> clu = new CollectionUtil<AvaliacaoDesempenho>();
		Collection<Colaborador> retorno = getDao().findColabPeriodoExperiencia(empresaId, periodoIni, periodoFim, clu.convertCollectionToArrayIds(avaliacaoIds), StringUtil.stringToLong(areasCheck), StringUtil.stringToLong(estabelecimentoCheck), StringUtil.stringToLong(colaboradorsCheck), considerarAutoAvaliacao);
		
		if(retorno.isEmpty())
			throw new Exception("Não existem informações para os filtros selecionados");

		return retorno;
	}
 
	public Collection<DynaRecord> preparaRelatorioDinamico(Collection<Colaborador> colaboradores, Collection<String> colunasMarcadas, Integer[] tempoServicoIni, Integer[] tempoServicoFim) 
	{
		Collection<DynaRecord> retorno = new ArrayList<DynaRecord>();
		
		for (Colaborador colaborador : colaboradores)
		{				
			DynaRecord record = new DynaRecord();
			
			for (int i=1; i <= colunasMarcadas.size(); i++)
				BeanUtils.setValue(record, "campo" + i, BeanUtils.getValue(colaborador, (String) colunasMarcadas.toArray()[i - 1]));
			
			if (tempoServicoIni != null && tempoServicoFim != null && tempoServicoIni.length == tempoServicoFim.length){
				for (int i = 0; i < tempoServicoIni.length; i++) 
				{
					if (colaborador.getTempoServico() >= tempoServicoIni[i] && colaborador.getTempoServico() <= tempoServicoFim[i])
					{
						record.setTempoServico("De " + tempoServicoIni[i] + " até " + tempoServicoFim[i] + " meses");
						break;
					}
				}
			}			
			
			record.setColaborador(colaborador);
			retorno.add(record);
		}			

		return retorno;
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

	public Colaborador findByUsuarioProjection(Long usuarioId) 
	{
		return getDao().findByUsuarioProjection(usuarioId);
	}
	
	public String[] findEmailsByUsuarios(Collection<Long> usuarioEmpresaIds)
	{
		if(usuarioEmpresaIds.isEmpty())
			return new String[0];
		
		return getDao().findEmailsByPapel(usuarioEmpresaIds);
	}

	public Collection<DataGrafico> countSexo(Date data, Collection<Long> empresaIds, Long[] areasIds) 
	{
		return getDao().countSexo(data, empresaIds, areasIds);
	}

	public Collection<DataGrafico> countEstadoCivil(Date data, Collection<Long> empresaIds, Long[] areasIds) {
		return getDao().countEstadoCivil(data, empresaIds, areasIds);
	}

	public Collection<DataGrafico> countFormacaoEscolar(Date data, Collection<Long> empresaIds, Long[] areasIds) 
	{
		return  getDao().countFormacaoEscolar(data, empresaIds, areasIds);
	}
	
	public Collection<DataGrafico> countFaixaEtaria(Date data, Collection<Long> empresaIds, Long[] areasIds)
	{
		return  getDao().countFaixaEtaria(data, empresaIds, areasIds);
	}

	public Collection<DataGrafico> countDeficiencia(Date data, Collection<Long> empresaIds, Long[] areasIds) 
	{
		return getDao().countDeficiencia(data, empresaIds, areasIds);
	}

	public Collection<DataGrafico> countMotivoDesligamento(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens) 
	{
		return getDao().countMotivoDesligamento(dataIni, dataFim, empresaIds, areasIds, qtdItens);
	}

	public Collection<DataGrafico> countColocacao(Date dataBase, Collection<Long> empresaIds, Long[] areasIds) 
	{
		return getDao().countColocacao(dataBase, empresaIds, areasIds);
	}
	
	public Collection<DataGrafico> countOcorrencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens) 
	{
		return getDao().countOcorrencia(dataIni, dataFim, empresaIds, areasIds, qtdItens);
	}
	
	public Collection<DataGrafico> countProvidencia(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, int qtdItens) 
	{
		return getDao().countProvidencia(dataIni, dataFim, empresaIds, areasIds, qtdItens);
	}

	public int getCountAtivos(Date dataBase, Collection<Long> empresaIds, Long[] areasIds) {
		return getDao().getCountAtivos(dataBase, empresaIds, areasIds);
	}

	public Integer countAdmitidosDemitidosTurnover(Date dataIni, Date dataFim, Collection<Long> empresaIds, Long[] areasIds, boolean isAdmitidos) 
	{
		Integer totalPorEmpresas = 0;
		if(empresaIds != null)
		{
			for (Long empresaId : empresaIds)
			{	
				Empresa empresaTmp = empresaManager.findByIdProjection(empresaId);
				totalPorEmpresas += getDao().countAdmitidosDemitidosTurnover(dataIni, dataFim, empresaTmp, areasIds, isAdmitidos);
			}			
		}
		
		return totalPorEmpresas;
	}

	public Collection<TurnOver> montaTurnOver(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, int filtrarPor) throws Exception 
	{
		if(filtrarPor == 1)
			cargosIds = null;
		else
			areasIds = null;
		
		int ate = DateUtil.mesesEntreDatas(dataIni, dataFim);
		Date dataTmp = DateUtil.getInicioMesData(dataIni);
		Collection<TurnOver> admitidos = new ArrayList<TurnOver>();
		Collection<TurnOver> demitidos = new ArrayList<TurnOver>();
		Collection<TurnOver> turnOvers = new LinkedList<TurnOver>();
		double qtdAtivos = 0;
		
		if(empresaIds != null)
		{
			for (Long empresaId : empresaIds) 
			{
				Empresa empresa = empresaManager.findByIdProjection(empresaId);
				admitidos.addAll(getDao().countAdmitidosDemitidosPeriodoTurnover(dataIni, dataFim, empresa, estabelecimentosIds, areasIds, cargosIds, true));
				demitidos.addAll(getDao().countAdmitidosDemitidosPeriodoTurnover(dataIni, dataFim, empresa, estabelecimentosIds, areasIds, cargosIds, false));

				if (empresa.isTurnoverPorSolicitacao())
					qtdAtivos += getDao().countAtivosTurnover(DateUtil.getUltimoDiaMesAnterior(dataIni), empresa.getId(), estabelecimentosIds, areasIds, cargosIds, true);
				else
					qtdAtivos += getDao().countAtivosPeriodo(DateUtil.getUltimoDiaMesAnterior(dataIni), Arrays.asList(empresa.getId()), estabelecimentosIds, areasIds, cargosIds, null, false, null, false);
			}
		}


		for (int i = 0; i <= ate; i++)
		{
			double qtdAdmitidos = 0;
			double qtdDemitidos = 0;
			
			for (TurnOver admitido : admitidos)
			{
				if(dataTmp.equals(admitido.getMesAno()))
					qtdAdmitidos = admitido.getQtdAdmitidos();
			}
			
			for (TurnOver demitido : demitidos)
			{
				if(dataTmp.equals(demitido.getMesAno()))
					qtdDemitidos = demitido.getQtdDemitidos();
			}
		
			TurnOver turnOverTmp = new TurnOver();
			turnOverTmp.setMesAno(dataTmp);
			turnOverTmp.setQtdAdmitidos(qtdAdmitidos);
			turnOverTmp.setQtdDemitidos(qtdDemitidos);
			turnOverTmp.setQtdAtivos(qtdAtivos);
			
			turnOverTmp.setTurnOver((((qtdAdmitidos + qtdDemitidos) / 2) / qtdAtivos) * 100);
			turnOvers.add(turnOverTmp);

			qtdAtivos = qtdAtivos + (qtdAdmitidos - qtdDemitidos);
			dataTmp = DateUtil.setaMesPosterior(dataTmp);
		}
		
		if (turnOvers == null || turnOvers.isEmpty())
			throw new ColecaoVaziaException();
		
		return turnOvers;
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
		return getDao().countAtivosPeriodo(dataIni, empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasId, considerarDataAdmissao, colaboradorId, isAbsenteismo);
	}

	public Collection<Object[]> montaGraficoTurnover(Collection<TurnOver> turnOvers) 
	{
		Collection<Object[]>  graficoEvolucaoTurnover = new ArrayList<Object[]>();
		for (TurnOver turnOver : turnOvers) 
			graficoEvolucaoTurnover.add(new Object[]{DateUtil.getUltimoDiaMes(turnOver.getMesAno()).getTime(), turnOver.getTurnOver()});
		
		return graficoEvolucaoTurnover;
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
	
	public int findQtdVagasPreenchidas(Long empresaId, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		return getDao().findQtdVagasPreenchidas(empresaId, solicitacaoIds, dataIni, dataFim);
	}
	
	public Collection<Colaborador> findSemCodigoAC(Long empresaId) {
		return getDao().findSemCodigoAC(empresaId);
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager) {
		this.usuarioMensagemManager = usuarioMensagemManager;
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

	public double calculaIndiceProcessoSeletivo(Long empresaId, Date dataDe, Date dataAte) 
	{
		//(demitidos em até 90 dias / admitidos no periodo * 100)
		double qtdDemitidosEm90Dias = getDao().qtdDemitidosEm90Dias(empresaId, dataDe, dataAte);
		double qtdAdmitidosPeriodo = getDao().qtdAdmitidosPeriodo(empresaId, dataDe, dataAte);
		
		if(qtdDemitidosEm90Dias == 0.0)
			return 100.0;
		if(qtdAdmitidosPeriodo == 0.0)
			return 0.0;
		
		return MathUtil.round(100 - ((qtdDemitidosEm90Dias / qtdAdmitidosPeriodo) * 100.00) , 2);
	}

	public void deleteColaborador(Long[] colaboradorIds) throws Exception {
		if (colaboradorIds != null && colaboradorIds.length > 0) {
			historicoColaboradorManager.deleteHistoricoColaborador(colaboradorIds);
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

	public String findCodigoACDuplicado(Long empresaId) {
		return getDao().findCodigoACDuplicado(empresaId);
	}

	public Collection<Long> findIdsByAreaOrganizacionalEstabelecimento(Collection<Long> areaIds, Collection<Long> estabelecimentoIds, Boolean desligado) {
		return getDao().findIdsByAreaOrganizacionalEstabelecimento(areaIds, estabelecimentoIds, desligado);
	}
	
	public Collection<PendenciaAC> findPendencias(Long empresaId) {
		Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();

		Collection<Colaborador> colaboradors = getDao().findPendenciasSolicitacaoDesligamentoAC(empresaId);

		for (Colaborador colaborador : colaboradors) {
			PendenciaAC pendenciaAC = new PendenciaAC();
			
			pendenciaAC.setPendencia("Solicitação de Desligamento");
			pendenciaAC.setDetalhes("Solicitação de desligamento do colaborador "+colaborador.getNome());
			pendenciaAC.setStatus(StatusRetornoAC.getDescricao(StatusRetornoAC.AGUARDANDO));
			
			pendenciaACs.add(pendenciaAC);
		}

		return pendenciaACs;
	}
	
	public void cancelarSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem, String empresaCodigoAC, String grupoAC) throws Exception
	{
		getDao().removerMotivoDemissaoColaborador(colaborador.getId());
		gerenciadorComunicacaoManager.enviaMensagemCancelamentoSolicitacaoDesligamentoAC(colaborador, mensagem, empresaCodigoAC, grupoAC);
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

	public Collection<Colaborador> findParentesByNome(String nome, Long empresaId) 
	{
		return getDao().findParentesByNome(nome, empresaId);
	}
	
	public boolean pertenceEmpresa(Long colaboradorId, Long empresaId) 
	{
		Colaborador colaborador = findByIdProjectionEmpresa(colaboradorId);
		return colaborador.getEmpresa().getId().equals(empresaId);
	}

	public Collection<Colaborador> triar(Long solicitacaoId, Long empresaId, String escolaridade, String sexo, String idadeMin, String idadeMax, String[] cargosCheck, String[] areasCheck, boolean exibeCompatibilidade, Integer percentualMinimo) throws Exception 
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
		
		Collection<Colaborador> colaboradores = getDao().triar(empresaId, escolaridade, sexo, dataNascIni, dataNascFim, LongUtil.arrayStringToArrayLong(cargosCheck), LongUtil.arrayStringToArrayLong(areasCheck), competenciasIdsFaixaSolicitacao, exibeCompatibilidade);
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

	public void setColaboradorPeriodoExperienciaAvaliacaoManager(ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager) 
	{
		this.colaboradorPeriodoExperienciaAvaliacaoManager = colaboradorPeriodoExperienciaAvaliacaoManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}

}