package com.fortes.rh.web.action.captacao;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.NonUniqueResultException;

import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.captacao.CandidatoCurriculoManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.SexoCadastro;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.validation.validator.NotEmpty;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("unchecked")
public class CandidatoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	private CandidatoManager candidatoManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;
	private AreaInteresseManager areaInteresseManager;
	private ConhecimentoManager conhecimentoManager;
	private CargoManager cargoManager;
	private FormacaoManager formacaoManager;
	private ExperienciaManager experienciaManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private EmpresaManager empresaManager;
	private BairroManager bairroManager;
	private CandidatoCurriculoManager candidatoCurriculoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ComoFicouSabendoVagaManager comoFicouSabendoVagaManager;

	private Candidato candidato = new Candidato();

	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] conhecimentosCheck;
	private Collection<CheckBox> conhecimentosCheckList = new ArrayList<CheckBox>();

	private Map escolaridades = new Escolaridade();
	private Map estadosCivis = new EstadoCivil();
	private Collection<Estado> ufs = null;
	private Collection<Cidade> cidades = new ArrayList<Cidade>();
	private Map colocacaoList = new Vinculo();
	
	private Date dataCadIni;
	private Date dataCadFim;
	private String nomeBusca = "";
	private String cpfBusca = "";
	private char visualizar;//T = todos, D = disponiveis, I = indisponiveis
	private String indicadoPor; // para manter filtro de candidatoList
	private String observacaoRH; // idem.
	private String cidadeBusca;
	private String ufBusca;
	private String escolaridadeBusca;
	private Collection areasInteresse;
	private Long[] selectedIds;
	private String tipoAcao;
	private String destinatario;
	private Collection candidatosAchados;
	//private Collection candidatosExporta = null;
	private String msgExportacao = "";
	private String msgErroExportacao = "";
	private String bairros = "";

	@NotEmpty
	private String senha;
	@NotEmpty
	private String confirmaSenha;

	private boolean manterFoto;
	private boolean exibirFoto;
	private Long empresaId;

	// Utilizados no insert e update para a chamada ao metodo saveDetalhes();
	private Collection<Formacao> formacaos;
	private Collection<CandidatoIdioma> idiomas;
	private Collection<Experiencia> experiencias;

	private Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas; 

	private int page = 1;
	private boolean moduloExterno;
	private String msgAlert;
	private int maxCandidataCargo;
	
	// utilizados pelo módulo externo
	private boolean upperCase = false; 

	private com.fortes.model.type.File[] imagens = null;
	private com.fortes.model.type.File ocrTexto;

	private Map sexos;
	private Map deficiencias;
	private Map estados;
	
	private String nomeImg;
	private ParametrosDoSistema parametrosDoSistema;
	private boolean habilitaCampoExtra;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private CamposExtrasManager camposExtrasManager;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private CamposExtras camposExtras;

	private void prepare() throws Exception
	{
		String abaExtra = "";
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraCandidato();
		
		comoFicouSabendoVagas = comoFicouSabendoVagaManager.findAllSemOutros();
		comoFicouSabendoVagas.add(comoFicouSabendoVagaManager.findById(1L));

		Long empresaId;
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
	
		if(!moduloExterno)
		{
			if(habilitaCampoExtra)
				abaExtra = ",abaExtra";

			empresaId = getEmpresaSistema().getId();
			cargosCheckList = CheckListBoxUtil.populaCheckListBox(cargoManager.findAllSelect(empresaId, "nomeMercado"), "getId", "getNomeMercado");
			
			parametrosDoSistema.setCamposCandidatoVisivel("nome,nascimento,naturalidade,sexo,cpf,escolaridade,endereco,email,fone,celular,nomeContato,parentes,estadoCivil,qtdFilhos,nomeConjuge,profConjuge,nomePai,profPai,nomeMae,profMae,pensao,possuiVeiculo,deficiencia,formacao,idioma,desCursos,funcaoPretendida,areasInteresse,conhecimentos,colocacao,expProfissional,infoAdicionais,identidade,cartairaHabilitacao,tituloEleitoral,certificadoMilitar,ctps,comoFicouSabendoVaga");
			parametrosDoSistema.setCamposCandidatoObrigatorio("nome,escolaridade,ende,num,cidade,ddd,fone");
			parametrosDoSistema.setCamposCandidatoTabs("abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais" + abaExtra);
		}
		else
		{
			
			if (parametrosDoSistema.getUpperCase() != null)
				upperCase = parametrosDoSistema.getUpperCase();
			
			empresaId = this.empresaId;
			
			cargosCheckList = CheckListBoxUtil.populaCheckListBox(cargoManager.findAllSelectModuloExterno(empresaId, "nomeMercado"), "getId", "getNomeMercado");
		}

		if(habilitaCampoExtra)
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
		else
			parametrosDoSistemaManager.ajustaCamposExtras(parametrosDoSistema, configuracaoCampoExtraManager.findAllNomes());

		sexos = new SexoCadastro();
		deficiencias = new Deficiencia();
		estados = new com.fortes.rh.model.dicionario.Estado();

		if (ufs == null)
			ufs = estadoManager.findAll(new String[]{"sigla"});

		if (candidato != null && candidato.getId() != null)
		{
			candidato = candidatoManager.findByIdProjection(candidato.getId());

			if (candidato == null)
			{
				msgAlert = "Este candidato não existe no banco de dados.";
				return;
			}

			candidato.setFoto(candidatoManager.getFoto(candidato.getId()));
			candidato.setConhecimentos(candidatoManager.findConhecimentosByCandidatoId(candidato.getId()));
			candidato.setCargos(candidatoManager.findCargosByCandidatoId(candidato.getId()));
			candidato.setAreasInteresse(candidatoManager.findAreaInteressesByCandidatoId(candidato.getId()));
		}

		if(candidato != null && candidato.getEndereco() != null && candidato.getEndereco().getUf() != null && candidato.getEndereco().getUf().getId() != null)
			cidades = cidadeManager.find(new String[]{"uf"}, new Object[]{candidato.getEndereco().getUf()}, new String[]{"nome"});

		areasCheckList = CheckListBoxUtil.populaCheckListBox(areaInteresseManager.findAllSelect(empresaId), "getId", "getNome");
		conhecimentosCheckList = CheckListBoxUtil.populaCheckListBox(conhecimentoManager.findAllSelect(empresaId), "getId", "getNome");

		if(habilitaCampoExtra && candidato.getCamposExtras() != null && candidato.getCamposExtras().getId() != null)
			camposExtras = camposExtrasManager.findById(candidato.getCamposExtras().getId());
		
		maxCandidataCargo = empresaManager.findToList(new String[]{"maxCandidataCargo"}, new String[]{"maxCandidataCargo"}, new String[]{"id"}, new Object[]{empresaId}).toArray(new Empresa[]{})[0].getMaxCandidataCargo();
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		Map session = ActionContext.getContext().getSession();
		session.put("SESSION_FORMACAO", null);
		session.put("SESSION_IDIOMA", null);
		session.put("SESSION_EXPERIENCIA", null);

		Pessoal pessoal = new Pessoal();

		pessoal.setQtdFilhos(0);
		candidato.setPessoal(pessoal);

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		Long empresaId;
		if(moduloExterno)
		{
			Map session = ActionContext.getContext().getSession();
			if(session.get("SESSION_CANDIDATO_ID") == null)
				return Action.INPUT;

			empresaId = this.empresaId;
		}
		else
		{
			empresaId = getEmpresaSistema().getId();
		}

		if (candidato.getEmpresa().getId().equals(empresaId))
		{
			Map session = ActionContext.getContext().getSession();
			//Se usuário vier do módulo externo, pega id da sessão
			if(moduloExterno)
				candidato.setId((Long) session.get("SESSION_CANDIDATO_ID"));

			session.put("SESSION_FORMACAO", formacaoManager.findByCandidato(candidato.getId()));
			session.put("SESSION_IDIOMA", candidatoIdiomaManager.findByCandidato(candidato.getId()));
			session.put("SESSION_EXPERIENCIA", experienciaManager.findByCandidato(candidato.getId()));

			cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, candidato.getCargos(), "getId");
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, candidato.getAreasInteresse(), "getId");

			Collection<String> idAreas = new ArrayList<String>();
			for (CheckBox cb : areasCheckList)
			{
				if (cb.isSelecionado())
					idAreas.add(cb.getId().toString());
			}

			Collection<Conhecimento> conhecimentos;
			if (idAreas.isEmpty())
				conhecimentos =  conhecimentoManager.findAllSelect(empresaId);
			else
				conhecimentos =  conhecimentoManager.findByAreaInteresse(LongUtil.collectionStringToArrayLong(idAreas),empresaId);

			conhecimentosCheckList = CheckListBoxUtil.populaCheckListBox(conhecimentos, "getId", "getNome");
			conhecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(conhecimentosCheckList, candidato.getConhecimentos(), "getId");

			return Action.SUCCESS;
		}
		else
		{
			msgAlert = "O candidato solicitado não existe na empresa " + SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getNome() +".";
			return Action.ERROR;
		}

	}

	public String insert() throws Exception
	{
		try
		{
			candidatoManager.validaQtdCadastros();			
		} catch (Exception e)
		{
			addActionMessage(e.getMessage());
			prepare();
			return Action.INPUT;
		}

		
		Empresa empresa = new Empresa();

		if(!moduloExterno)
			empresa.setId(getEmpresaSistema().getId());
		else
			empresa.setId(this.empresaId);
		
		try 
		{
			Candidato candidatoTmp = candidatoManager.verifyCPF(candidato.getPessoal().getCpf(), empresa.getId(), candidato.getId(), null);
			if(candidatoTmp != null)
			{
				if (moduloExterno)
					addActionError("Já existe um currículo com esse CPF.");
				else
					addActionError("CPF já cadastrado!");
				
				prepare();
				return Action.INPUT;				
			}
		} catch (NonUniqueResultException notUniqueResultException) 
		{
			addActionError("CPF já cadastrado!");
			prepare();
			return Action.INPUT;				
		}
		
		if(!fotoValida(candidato.getFoto()))
		{
			prepare();
			candidato.setFoto(null);
			return Action.INPUT;
		}

		Map session = ActionContext.getContext().getSession();
		formacaos = (Collection<Formacao>) session.get("SESSION_FORMACAO");
		idiomas = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");
		experiencias = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

		candidato.setEmpresa(empresa);
		candidato.setBlackList(false);
		candidato.setContratado(false);
		candidato.setDisponivel(true);
		candidato.setDataCadastro(new Date());
		candidato.setDataAtualizacao(new Date());
		candidato.setSenha(StringUtil.encodeString(candidato.getSenha()));

		ajustaCamposNulls(candidato);
		
		if(moduloExterno)
		{
			candidato.setOrigem(OrigemCandidato.EXTERNO);
			candidatoManager.enviaEmailResponsavelRh(candidato.getNome(), empresa.getId());
		}else
			candidato.setOrigem(OrigemCandidato.CADASTRADO);

		carregaChecksCandidato();

		candidato.getPessoal().setCpf(StringUtil.removeMascara(candidato.getPessoal().getCpf()));

		if(candidato.getPessoal().getCtps().getCtpsUf().getId() == null)
			candidato.getPessoal().getCtps().setCtpsUf(null);

		if(candidato.getPessoal().getRgUf().getId() == null)
			candidato.getPessoal().setRgUf(null);

		if (candidato.getComoFicouSabendoVaga()!=null && candidato.getComoFicouSabendoVaga().getId()==null)
			candidato.setComoFicouSabendoVaga(null);
		
		if(habilitaCampoExtra && camposExtras != null)
			candidato.setCamposExtras(camposExtrasManager.save(camposExtras));
		
		candidatoManager.save(candidato);

		saveDetalhes();

		session.put("SESSION_FORMACAO", null);
		session.put("SESSION_IDIOMA", null);
		session.put("SESSION_EXPERIENCIA", null);

		return Action.SUCCESS;
	}

	private void ajustaCamposNulls(Candidato candidatoAux) 
	{
		if(candidatoAux.getEndereco().getUf() == null || candidatoAux.getEndereco().getUf().getId() == null)
			candidatoAux.getEndereco().setUf(null);
		if(candidatoAux.getEndereco().getCidade() == null || candidatoAux.getEndereco().getCidade().getId() == null)
			candidatoAux.getEndereco().setCidade(null);
	}

	private boolean fotoValida(com.fortes.model.type.File foto)
	{
		boolean fotoValida =  true;
		if(foto != null)
		{
			if(foto.getContentType().length() >= 5)
			{
				if(!foto.getContentType().substring(0, 5).equals("image"))
				{
					addActionError("Tipo de arquivo não suportado");
					fotoValida = false;
				}

				if(foto.getSize() > 524288)
				{
					addActionError("Tamanho do arquivo maior que o suportado");
					fotoValida = false;
				}
			}
		}

		return fotoValida;
	}


	public String update() throws Exception
	{
		Long empresaId;
		if(!moduloExterno)
			empresaId = getEmpresaSistema().getId();
		else
			empresaId = this.empresaId;

		if (!candidato.getEmpresa().getId().equals(empresaId))
		{
			msgAlert = "O candidato solicitado não existe na empresa .";
			return Action.ERROR;
		}

		if (manterFoto)
		{
			candidato.setFoto(candidatoManager.getFoto(candidato.getId()));
		}
		else if(!fotoValida(candidato.getFoto()))
		{

			prepareUpdate();
			return Action.INPUT;
		}

		try 
		{
			Candidato candidatoTmp = candidatoManager.verifyCPF(candidato.getPessoal().getCpf(), empresaId, candidato.getId(), null);
			if(candidatoTmp != null)
			{
				if (moduloExterno)
					addActionError("Já existe um currículo com esse CPF.");
				else
					addActionError("CPF já cadastrado!");
				
				prepare();
				return Action.INPUT;				
			}
		} catch (NonUniqueResultException notUniqueResultException) 
		{
			addActionError("CPF já cadastrado!");
			prepare();
			return Action.INPUT;				
		}
		
		Map session = ActionContext.getContext().getSession();
		formacaos = (Collection<Formacao>) session.get("SESSION_FORMACAO");
		idiomas = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");
		experiencias = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

		candidato.setDataAtualizacao(new Date());
		ajustaCamposNulls(candidato);

		carregaChecksCandidato();

		candidato.getPessoal().setCpf(StringUtil.removeMascara(candidato.getPessoal().getCpf()));
		candidatoManager.ajustaSenha(candidato);
		
		if (candidato.getComoFicouSabendoVaga()!=null && candidato.getComoFicouSabendoVaga().getId()==null)
			candidato.setComoFicouSabendoVaga(null);
		
		if(habilitaCampoExtra && camposExtras != null)
			candidato.setCamposExtras(camposExtrasManager.save(camposExtras));		
		
		candidatoManager.update(candidato);
				
		if(!moduloExterno)
			candidato.setOcrTexto(candidatoManager.getOcrTextoById(candidato.getId()));

		formacaoManager.removeCandidato(candidato);
		candidatoIdiomaManager.removeCandidato(candidato);
		experienciaManager.removeCandidato(candidato);

		saveDetalhes();

		session.put("SESSION_FORMACAO", null);
		session.put("SESSION_IDIOMA", null);
		session.put("SESSION_EXPERIENCIA", null);
		
		return Action.SUCCESS;
	}
	
	public String prepareInsertCurriculo() throws Exception
	{
		candidato = candidatoManager.findByCandidatoId(candidato.getId());
		return Action.SUCCESS;
	}

	public String prepareInsertCurriculoPlus() throws Exception
	{
		if(candidato != null && candidato.getId() != null)
		{
			candidato = candidatoManager.findByIdProjection(candidato.getId());
			candidato.setCargos(candidatoManager.findCargosByCandidatoId(candidato.getId()));			
		}
		
		Empresa empresa = (Empresa) empresaManager.findToList(new String[]{"maxCandidataCargo"}, new String[]{"maxCandidataCargo"}, new String[]{"id"}, new Object[]{getEmpresaSistema().getId()}).toArray()[0];
		maxCandidataCargo = empresa.getMaxCandidataCargo();
		ufs = estadoManager.findAll(new String[]{"sigla"});
		
		if(candidato != null && candidato.getEndereco() != null && candidato.getEndereco().getUf() != null && candidato.getEndereco().getUf().getId() != null)
			cidades = cidadeManager.find(new String[]{"uf"}, new Object[]{candidato.getEndereco().getUf()}, new String[]{"nome"});
		
		cargosCheckList = CheckListBoxUtil.populaCheckListBox(cargoManager.findAllSelect(getEmpresaSistema().getId(), "nomeMercado"), "getId", "getNomeMercado");
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, candidato.getCargos(), "getId");
		
		sexos = new SexoCadastro();

		return Action.SUCCESS;
	}

	public String prepareInsertCurriculoTexto() throws Exception
	{
		bairros = bairroManager.getArrayBairros();
		
		Empresa empresa = (Empresa) empresaManager.findToList(new String[]{"maxCandidataCargo"}, new String[]{"maxCandidataCargo"}, new String[]{"id"}, new Object[]{getEmpresaSistema().getId()}).toArray()[0];
		maxCandidataCargo = empresa.getMaxCandidataCargo();

		ufs = estadoManager.findAll(new String[]{"sigla"});
		cargosCheckList = CheckListBoxUtil.populaCheckListBox(cargoManager.findAllSelect(getEmpresaSistema().getId(), "nomeMercado"), "getId", "getNomeMercado");
		sexos = new SexoCadastro();
		
		return Action.SUCCESS;
	}
	

	public String insertCurriculo() throws Exception
	{		
//		try
//		{
//			candidatoManager.validaQtdCadastros();			
//		} catch (Exception e)
//		{
//			addActionMessage(e.getMessage());
//			prepare();
//			return Action.INPUT;
//		}
	
		try
		{
			if (candidato.getId() == null)
				prepareCandidato();

			candidato.setCargos(cargoManager.populaCargos(cargosCheck));
			candidato = candidatoManager.saveCandidatoCurriculo(candidato, imagens, ocrTexto);
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			if (candidato.getId() == null)
				prepareInsertCurriculoPlus();
			
			CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

			addActionError("Não foi possível inserir currículo escaneado.");
			e.printStackTrace();
			return Action.INPUT;
		}
	}
	
	public String insertCurriculoTexto() throws Exception
	{		
		prepareInsertCurriculoTexto();

		try
		{
			candidatoManager.validaQtdCadastros();						
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
		
		try
		{
			prepareCandidato();
			candidato.setOcrTexto(ArquivoUtil.convertToLatin1Compatible(candidato.getOcrTexto().getBytes()));
			candidato = candidatoManager.save(candidato);
			addActionMessage("Currículo ("+ candidato.getNome() +") cadastrado com sucesso.");
		}
		catch (Exception e)
		{
			addActionMessage("Erro ao cadastrar Currículo");
			return Action.INPUT;
		}
		
		candidato = null;
		return Action.SUCCESS;
	}

	private void prepareCandidato()
	{
		if (candidato.getId() != null)
			return;
		
		candidato.setEmpresa(getEmpresaSistema());
		candidato.setBlackList(false);
		candidato.setContratado(false);
		candidato.setDisponivel(true);
		
		Date hoje = new Date();
		candidato.setDataCadastro(hoje);
		candidato.setDataAtualizacao(hoje);
		
		candidato.setCargos(cargoManager.populaCargos(cargosCheck));
		candidato.setOrigem(OrigemCandidato.CADASTRADO);
		candidato.setSocioEconomica(new SocioEconomica());
		candidato.getSocioEconomica().setPagaPensao(false);
		if(candidato.getEndereco().getCidade().getId() == null)
			candidato.getEndereco().setCidade(null);
		if(candidato.getEndereco().getUf().getId() == null)
			candidato.getEndereco().setUf(null);
	}

	public String prepareUpdateCurriculo() throws Exception
	{
		if (candidato != null && candidato.getId() != null)
		{
			candidato = candidatoManager.findByIdProjection(candidato.getId());
			candidato.setOcrTexto(candidatoManager.getOcrTextoById(candidato.getId()));
			candidato.setCandidatoCurriculos(candidatoCurriculoManager.findToList(new String[]{"curriculo"}, new String[]{"curriculo"}, new String[]{"candidato.id"}, new Object[]{candidato.getId()}));
		}

		return Action.SUCCESS;
	}

	public String updateCurriculo() throws Exception
	{
		if (candidato != null && candidato.getId() != null)
		{
			candidatoManager.atualizaTextoOcr(candidato);
			return Action.SUCCESS;
		}
		else
		{
			addActionError("Nenhum candidato selecione para ser atualizado.");
			return Action.INPUT;
		}
	}

	public String showCurriculo() throws Exception{
		java.io.File file = ArquivoUtil.getArquivo(nomeImg,"curriculos");
		com.fortes.model.type.File arquivo = new com.fortes.model.type.File();
		arquivo.setBytes(FileUtil.getFileBytes(file));
		arquivo.setName(file.getName());
		arquivo.setSize(file.length());
		int pos = arquivo.getName().indexOf(".");
		if(pos > 0){
			arquivo.setContentType(arquivo.getName().substring(pos));
		}
		if (arquivo != null && arquivo.getBytes() != null)
		{
			HttpServletResponse response = ServletActionContext.getResponse();

			response.addHeader("Expires", "0");
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Content-type", arquivo.getContentType());
			response.addHeader("Content-Transfer-Encoding", "binary");

			response.getOutputStream().write(arquivo.getBytes());
		}
			return Action.SUCCESS;
	}

	public String showFoto() throws Exception
	{
		if (candidato != null && candidato.getId() != null)
		{
			candidato.setFoto(candidatoManager.getFoto(candidato.getId()));
		}

		if (candidato.getFoto() != null && candidato.getFoto().getBytes() != null)
		{
			HttpServletResponse response = ServletActionContext.getResponse();

	        response.addHeader("Expires", "0");
	        response.addHeader("Pragma", "no-cache");
	        response.addHeader("Content-type", candidato.getFoto().getContentType());
	        response.addHeader("Content-Transfer-Encoding", "binary");

	        response.getOutputStream().write(candidato.getFoto().getBytes());
		}

		return Action.SUCCESS;
	}

	private void carregaChecksCandidato()
	{
		candidato.setCargos(cargoManager.populaCargos(cargosCheck));

		if (areasCheck != null && areasCheck.length > 0)
		{
			Collection<AreaInteresse> areasTmp = new ArrayList<AreaInteresse>();
			for (int i = 0; i < areasCheck.length; i++)
			{
				Long idArea = Long.valueOf(areasCheck[i]);
				AreaInteresse a = new AreaInteresse();
				a.setId(idArea);
				areasTmp.add(a);
			}
			candidato.setAreasInteresse(areasTmp);
		}

		if (conhecimentosCheck != null && conhecimentosCheck.length > 0)
		{
			Collection<Conhecimento> conhecimentosTmp = new ArrayList<Conhecimento>();
			for (int i = 0; i < conhecimentosCheck.length; i++)
			{
				Long idConhecimento = Long.valueOf(conhecimentosCheck[i]);
				Conhecimento c = new Conhecimento();
				c.setId(idConhecimento);
				conhecimentosTmp.add(c);
			}
			candidato.setConhecimentos(conhecimentosTmp);
		}
	}

	private void saveDetalhes()
	{
		if (formacaos != null && !formacaos.isEmpty())
		{
			for (Formacao f : formacaos)
			{
				f.setId(null);
				f.setCandidato(candidato);
				formacaoManager.save(f);
			}
		}

		if (idiomas != null && !idiomas.isEmpty())
		{
			for (CandidatoIdioma c : idiomas)
			{
				c.setId(null);
				c.setCandidato(candidato);
				candidatoIdiomaManager.save(c);
			}
		}

		if (experiencias != null && !experiencias.isEmpty())
		{
			for (Experiencia e : experiencias)
			{
				e.setId(null);
				e.setCandidato(candidato);

				if(e.getCargo() == null || e.getCargo().getId() == null)
					e.setCargo(null);
				experienciaManager.save(e);
			}
		}

		Bairro bairro = new Bairro();
		bairro.setNome(candidato.getEndereco().getBairro());
		bairro.setCidade(candidato.getEndereco().getCidade());

		if(bairro.getNome() != null && !bairro.getNome().trim().equals("") && bairro.getCidade().getId() != null)
		{
			if(!bairroManager.existeBairro(bairro))
			{
				bairroManager.save(bairro);
			}
		}
	}

	public Candidato getCandidato()
	{
		if (candidato == null)
			candidato = new Candidato();
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public Map getEscolaridades()
	{
		return escolaridades;
	}

	public void setEscolaridades(Map escolaridades)
	{
		this.escolaridades = escolaridades;
	}

	public Map getEstadosCivis()
	{
		return estadosCivis;
	}

	public Collection getAreasInteresse()
	{
		return areasInteresse;
	}

	public String getCidadeBusca()
	{
		return cidadeBusca;
	}

	public void setCidadeBusca(String cidadeBusca)
	{
		this.cidadeBusca = cidadeBusca;
	}

	public String getCpfBusca()
	{
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca)
	{
		this.cpfBusca = cpfBusca;
	}

	public String getEscolaridadeBusca()
	{
		return escolaridadeBusca;
	}

	public void setEscolaridadeBusca(String escolaridadeBusca)
	{
		this.escolaridadeBusca = escolaridadeBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Long[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(Long[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public String getTipoAcao()
	{
		return tipoAcao;
	}

	public void setTipoAcao(String tipoAcao)
	{
		this.tipoAcao = tipoAcao;
	}

	public String getUfBusca()
	{
		return ufBusca;
	}

	public void setUfBusca(String ufBusca)
	{
		this.ufBusca = ufBusca;
	}

	public void setEstadosCivis(Map estadosCivis)
	{
		this.estadosCivis = estadosCivis;
	}

	public Collection getCandidatosAchados()
	{
		return candidatosAchados;
	}

	public void setCandidatosAchados(Collection candidatosAchados)
	{
		this.candidatosAchados = candidatosAchados;
	}

	public AreaInteresseManager getAreaInteresse()
	{
		return areaInteresseManager;
	}

	public AreaInteresseManager getAreaInteresseManager()
	{
		return areaInteresseManager;
	}

	public void setAreaInteresseManager(
			AreaInteresseManager areaInteresseManager)
	{
		this.areaInteresseManager = areaInteresseManager;
	}

	public ConhecimentoManager getConhecimentoManager()
	{
		return conhecimentoManager;
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager = conhecimentoManager;
	}

	public CandidatoManager getCandidatoManager()
	{
		return candidatoManager;
	}

	public String getDestinatario()
	{
		return destinatario;
	}

	public void setDestinatario(String destinatario)
	{
		this.destinatario = destinatario;
	}

	public String getMsgExportacao()
	{
		return msgExportacao;
	}

	public String getMsgErroExportacao()
	{
		return msgErroExportacao;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public Collection<Cidade> getCidades()
	{
		return cidades;
	}

	public void setCidades(Collection<Cidade> cidades)
	{
		this.cidades = cidades;
	}

	public Collection<Estado> getUfs()
	{
		return ufs;
	}

	public void setUfs(Collection<Estado> ufs)
	{
		this.ufs = ufs;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}

	public String[] getConhecimentosCheck()
	{
		return conhecimentosCheck;
	}

	public void setConhecimentosCheck(String[] conhecimentosCheck)
	{
		this.conhecimentosCheck = conhecimentosCheck;
	}

	public Collection<CheckBox> getConhecimentosCheckList()
	{
		return conhecimentosCheckList;
	}

	public void setConhecimentosCheckList(Collection<CheckBox> conhecimentosCheckList)
	{
		this.conhecimentosCheckList = conhecimentosCheckList;
	}

	public String getConfirmaSenha()
	{
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha)
	{
		this.confirmaSenha = confirmaSenha;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setFormacaoManager(FormacaoManager formacaoManager)
	{
		this.formacaoManager = formacaoManager;
	}

	public void setExperienciaManager(ExperienciaManager experienciaManager)
	{
		this.experienciaManager = experienciaManager;
	}

	public void setCandidatoIdiomaManager(CandidatoIdiomaManager candidatoIdiomaManager)
	{
		this.candidatoIdiomaManager = candidatoIdiomaManager;
	}

	public boolean isManterFoto() {
		return manterFoto;
	}

	public void setManterFoto(boolean manterFoto) {
		this.manterFoto = manterFoto;
	}

	public boolean isExibirFoto() {
		return exibirFoto;
	}

	public void setExibirFoto(boolean exibirFoto) {
		this.exibirFoto = exibirFoto;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Map getColocacaoList() {
		return colocacaoList;
	}

	public boolean isModuloExterno()
	{
		return moduloExterno;
	}

	public void setModuloExterno(boolean moduloExterno)
	{
		this.moduloExterno = moduloExterno;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public int getMaxCandidataCargo()
	{
		return maxCandidataCargo;
	}

	public void setMaxCandidataCargo(int maxCandidataCargo)
	{
		this.maxCandidataCargo = maxCandidataCargo;
	}

	public void setBairroManager(BairroManager bairroManager)
	{
		this.bairroManager = bairroManager;
	}

	public com.fortes.model.type.File[] getImagemEscaneada()
	{
		return imagens;
	}

	public void setImagemEscaneada(com.fortes.model.type.File[] imagens)
	{
		this.imagens = imagens;
	}

	public com.fortes.model.type.File getOcrTexto()
	{
		return ocrTexto;
	}

	public void setOcrTexto(com.fortes.model.type.File ocrTexto)
	{
		this.ocrTexto = ocrTexto;
	}

	public void setCandidatoCurriculoManager(CandidatoCurriculoManager candidatoCurriculoManager)
	{
		this.candidatoCurriculoManager = candidatoCurriculoManager;
	}

	public Map getSexos()
	{
		return sexos;
	}

	public void setSexos(Map sexos)
	{
		this.sexos = sexos;
	}

	public Map getEstados()
	{
		return estados;
	}

	public void setEstados(Map estados)
	{
		this.estados = estados;
	}

	public Map getDeficiencias()
	{
		return deficiencias;
	}

	public void setDeficiencias(Map deficiencias)
	{
		this.deficiencias = deficiencias;
	}

	public void setNomeImg(String nomeImg)
	{
		this.nomeImg = nomeImg;
	}

	public void setAreasInteresse(Collection areasInteresse)
	{
		this.areasInteresse = areasInteresse;
	}

	public Date getDataCadFim()
	{
		return dataCadFim;
	}

	public void setDataCadFim(Date dataCadFim)
	{
		this.dataCadFim = dataCadFim;
	}

	public Date getDataCadIni()
	{
		return dataCadIni;
	}

	public void setDataCadIni(Date dataCadIni)
	{
		this.dataCadIni = dataCadIni;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}

	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public boolean isUpperCase()
	{
		return upperCase;
	}

	public String getObservacaoRH()
	{
		return observacaoRH;
	}

	public void setObservacaoRH(String observacaoRH)
	{
		this.observacaoRH = observacaoRH;
	}

	public String getBairros()
	{
		return bairros;
	}

	public ParametrosDoSistema getParametrosDoSistema() {
		return parametrosDoSistema;
	}

	public void setComoFicouSabendoVagaManager(
			ComoFicouSabendoVagaManager comoFicouSabendoVagaManager) {
		this.comoFicouSabendoVagaManager = comoFicouSabendoVagaManager;
	}

	public Collection<ComoFicouSabendoVaga> getComoFicouSabendoVagas() {
		return comoFicouSabendoVagas;
	}

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
	}

	public void setHabilitaCampoExtra(boolean habilitaCampoExtra) {
		this.habilitaCampoExtra = habilitaCampoExtra;
	}

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras() {
		return configuracaoCampoExtras;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public CamposExtras getCamposExtras() {
		return camposExtras;
	}

	public void setCamposExtras(CamposExtras camposExtras) {
		this.camposExtras = camposExtras;
	}

	public void setCamposExtrasManager(CamposExtrasManager camposExtrasManager) {
		this.camposExtrasManager = camposExtrasManager;
	}

	
}

@SuppressWarnings("unchecked")
class FileImageFilter implements FileFilter {

		public boolean accept(File pathname) {

             if (!pathname.isFile()) {
                 return false;
             }

             FileImageInputStream input = null;
             boolean ret;
             try {

                 // tenta ler arquivo como image
                 input = new FileImageInputStream(pathname);
                 Iterator i = ImageIO.getImageReaders(input);
                 ret = i.hasNext();
             } catch (FileNotFoundException e) {
                 throw new RuntimeException(e);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             } finally {
                 if (input != null) {
                     try {
                         input.close();
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
             return ret;
         }

}