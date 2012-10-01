package com.fortes.rh.business.captacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.hibernate.NonUniqueResultException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.f2rh.Curriculo;
import com.fortes.f2rh.F2rhFacade;
import com.fortes.f2rh.F2rhFacadeImpl;
import com.fortes.model.type.File;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FormatoArquivoInvalidoException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.Zip;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


@SuppressWarnings("unchecked")
public class CandidatoManagerImpl extends GenericManagerImpl<Candidato, CandidatoDao> implements CandidatoManager
{
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private AnuncioManager anuncioManager;
	private Mail mail;
	private FormacaoManager formacaoManager;
	private ExperienciaManager experienciaManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private SolicitacaoManager solicitacaoManager;
	private PlatformTransactionManager transactionManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private BairroManager bairroManager;
	private CandidatoCurriculoManager candidatoCurriculoManager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private CidadeManager cidadeManager;
	private EstadoManager estadoManager;
	private SolicitacaoExameManager solicitacaoExameManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private int totalSize;

	public int getTotalSize()
	{
		return totalSize;
	}
	
	//TODO refatorar
	public Collection<Candidato> busca(Map<String, Object> parametros, Long empresaId, Long solicitacaoId, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar) throws Exception
	{
		Collection<Candidato> candidatos = null;
		// Experiencia
		if( parametros.get("experiencias") != null && !parametros.get("tempoExperiencia").equals("") && !parametros.get("tempoExperiencia").equals("0"))
		{
			Collection<Candidato> candidatosExperiencia;
			if (empresaId != null && empresaId == -1L)
				candidatosExperiencia = getDao().getCandidatosByExperiencia(parametros, null);
			else			
				candidatosExperiencia = getDao().getCandidatosByExperiencia(parametros, empresaId);
			
			candidatos = new ArrayList<Candidato>();

			for (Candidato candidato : candidatosExperiencia)
				if(temExperiencia(candidato.getExperiencias(), (String)parametros.get("tempoExperiencia"), (Long[])parametros.get("experiencias")))
					candidatos.add(candidato);

			CollectionUtil<Candidato> cluCandidatos = new CollectionUtil<Candidato>();

			parametros.put("candidatosComExperiencia", cluCandidatos.convertCollectionToArrayIds(candidatos));
		}

		if(parametros.get("bairrosIds") != null && ((Long[])parametros.get("bairrosIds")).length > 0)
		{
			Collection<Bairro> colBairros = bairroManager.getBairrosByIds((Long[])parametros.get("bairrosIds"));
			CollectionUtil<Bairro> cluBairro = new CollectionUtil<Bairro>();
			parametros.put("bairros", cluBairro.convertCollectionToArrayString(colBairros, "getNome"));
		}

		Collection<Candidato> retorno = null;

		Collection<Long> idsCandidatos = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacaoId);

		if(candidatos != null && candidatos.size() == 0)
			retorno = null;
		else
		{
			if (empresaId != null && empresaId != -1)
				retorno = getDao().findBusca(parametros, empresaId, idsCandidatos, somenteSemSolicitacao, qtdRegistros, ordenar);
			else
				retorno = getDao().findBusca(parametros, null, idsCandidatos, somenteSemSolicitacao, qtdRegistros, ordenar);
		}
		
		return retorno;
	}

	private boolean temExperiencia(Collection<Experiencia> experiencias, String tempoDeExperiencia, Long[] cargos)
	{
		//experiencia exigida
		int expParametro = Integer.parseInt(tempoDeExperiencia);
		//experiencia acumulada
		int totalExperiencia = 0;

		for(Experiencia experienciaTmp : experiencias)
		{
			if(estaContido(experienciaTmp, cargos))
			{
				totalExperiencia += getMesesExperiencia(experienciaTmp);
			}
		}

		if(totalExperiencia >= expParametro)
		{
			return true;
		}

		return false;
	}

	private int getMesesExperiencia(Experiencia experienciaTmp)
	{
		Long meses = 0L;

		if(experienciaTmp.getDataDesligamento() == null)
			experienciaTmp.setDataDesligamento(new Date());

		if(experienciaTmp.getDataDesligamento().after(experienciaTmp.getDataAdmissao()))
		{
			Long dif = experienciaTmp.getDataDesligamento().getTime() - experienciaTmp.getDataAdmissao().getTime() ;
			meses = dif/(1000L*60*60*24*30);
		}

		return meses.intValue();
	}

	private boolean estaContido(Experiencia experienciaTmp, Long[] cargos)
	{
		Long idExperiencia = experienciaTmp.getCargo().getId();

		for (Long cargo : cargos)
		{
			if(cargo.equals(idExperiencia))
			{
				return true;
			}
		}
		return false;
	}

	public Collection<Candidato> list(int page, int pagingSize, String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno)
	{
		return getDao().find(page, pagingSize, nomeBusca, cpfBusca, empresaId, indicadoPor, visualizar, dataIni, dataFim, observacaoRH, exibeContratados, exibeExterno);
	}

	public Integer getCount(String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno)
	{
		return getDao().getCount(nomeBusca, cpfBusca, empresaId, indicadoPor, visualizar, dataIni, dataFim, observacaoRH, exibeContratados, exibeExterno);
	}

	public File getFoto(Long id) throws Exception
	{
		return getDao().getFile("foto", id);
	}

	public void removeCandidato(Candidato candidato) throws Exception
	{
		ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
		ColaboradorManager colaboradorManager= (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		
		candidatoSolicitacaoManager.removeCandidato(candidato.getId());
		colaboradorManager.setCandidatoNull(candidato.getId());
		colaboradorQuestionarioManager.removeByCandidato(candidato.getId());
		solicitacaoExameManager.removeByCandidato(candidato.getId());
		configuracaoNivelCompetenciaManager.removeByCandidato(candidato.getId());
		formacaoManager.removeCandidato(candidato);
		experienciaManager.removeCandidato(candidato);
		candidatoIdiomaManager.removeCandidato(candidato);
		candidatoCurriculoManager.removeCandidato(candidato);
		getDao().removeAreaInteresseConhecimentoCargo(candidato.getId());
		getDao().remove(candidato.getId());
	}
	public void updateSenha(Candidato candidato)
	{
		if(candidato.getNovaSenha().equals(candidato.getConfNovaSenha()))
		{
			getDao().updateSenha(candidato.getId(), StringUtil.encodeString(candidato.getSenha()), StringUtil.encodeString(candidato.getNovaSenha()));
		}
	}

	public boolean exportaCandidatosBDS(Empresa empresa, Collection<Candidato> candidatos, String[] empresasCheck, String emailAvulso, String assunto) throws Throwable
	{
		java.io.File xmlFile = null;
		java.io.File zipFile = null;

		try
		{
			// Cria o arquivo xml
			String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
			xmlFile = new java.io.File(fileName);
			FileOutputStream outputStream = new FileOutputStream(xmlFile);
			String encoding = "UTF-8";
			XStream stream = new XStream(new DomDriver(encoding));

			outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
			stream.toXML(candidatos, outputStream);
			outputStream.flush();
			outputStream.close();

			// Compacta o arquivo
			ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh", true);// cria o arquivo candidatos.zip
			zipOutputStream.close();

			// Envia o arquivo por email
			zipFile = new java.io.File(fileName + ".fortesrh");

			String body = "Passos para a Importação dos Candidatos:<br>"+
						  "1. Acesse Movimentações > Solicitação de Pessoal; <br>" +
						  "2. Clique em \"Candidatos da Seleção\", \"Triagem\", \"Importar BDS\" e selecione o arquivo em anexo.Em seguida clique em \"Importar\"" ;
			mail.send(empresa, assunto, body, new java.io.File[] { zipFile }, anuncioManager.montaEmails(emailAvulso, empresasCheck));
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if(zipFile != null && zipFile.exists())
			{
				zipFile.delete();
			}

			if(xmlFile != null && xmlFile.exists())
			{
				xmlFile.delete();
			}
		}
		return true;
	}

	public Collection<Candidato> findCandidatosById(Long[] ids)
	{
		return getDao().findCandidatosById(ids);
	}

	public Collection<Candidato> populaCandidatos(Collection<Candidato> candidatos)
	{
		if(candidatos.isEmpty())
			return candidatos;

		CollectionUtil<Candidato> cu = new CollectionUtil<Candidato>();
		Long[] candidatoIds = cu.convertCollectionToArrayIds(candidatos);

		Collection<Experiencia> experiencias = experienciaManager.findInCandidatos(candidatoIds);
		Collection<Formacao> formacaos = formacaoManager.findInCandidatos(candidatoIds);
		Collection<CandidatoIdioma> candidatoIdiomas = candidatoIdiomaManager.findInCandidatos(candidatoIds);

		for(Candidato candidato : candidatos)
		{
			Collection<Experiencia> experienciaAux = new ArrayList<Experiencia>();
			Collection<Formacao> formacaoAux = new ArrayList<Formacao>();
			Collection<CandidatoIdioma> candidatoIdiomaAux = new ArrayList<CandidatoIdioma>();

			for(Experiencia experiencia : experiencias)
			{
				if(experiencia.getCandidato() != null && candidato.getId().equals(experiencia.getCandidato().getId()))
				{
					experiencia.setCandidato(null);

					if(experiencia.getCargo() != null && experiencia.getCargo().getNomeMercado() != null)
						experiencia.setNomeMercado(experiencia.getCargo().getNomeMercado());

					experiencia.setCargo(null);
					experienciaAux.add(experiencia);
				}
			}

			for(Formacao formacao : formacaos)
			{
				if(formacao.getCandidato() != null && candidato.getId().equals(formacao.getCandidato().getId()))
				{
					formacao.setCandidato(null);
					formacao.setAreaFormacao(null);
					formacaoAux.add(formacao);
				}
			}

			for(CandidatoIdioma candidatoIdioma : candidatoIdiomas)
			{
				if(candidatoIdioma.getCandidato() != null && candidato.getId().equals(candidatoIdioma.getCandidato().getId()))
				{
					candidatoIdioma.setCandidato(null);
					candidatoIdiomaAux.add(candidatoIdioma);
				}
			}

			candidato.setExperiencias(experienciaAux);
			candidato.setFormacao(formacaoAux);
			candidato.setCandidatoIdiomas(candidatoIdiomaAux);

			candidato.setId(null);
			candidato.setFoto(null);
			candidato.setCargos(null);
			candidato.setAreasInteresse(null);
			candidato.setSenha(null);
			if (candidato.getEndereco().getUf() != null)
				candidato.getEndereco().getUf().setNome(null);
			if (candidato.getEndereco().getUf() != null)
				candidato.getEndereco().getUf().setSigla(null);
			if (candidato.getEndereco().getCidade() != null)
				candidato.getEndereco().getCidade().setUf(null);
			if (candidato.getEndereco().getCidade() != null)
				candidato.getEndereco().getCidade().setNome(null);
			candidato.setConhecimentos(null);

			//Passa conhecimento para dentro da observação
			List<String> conhecimentoNomes = getDao().getConhecimentosByCandidatoId(candidato.getId());
			if(conhecimentoNomes != null && !conhecimentoNomes.isEmpty())
			{
				String conhecimentoAux = "\n---- Conhecimento: \n";
				for (String conhecimentoNome : conhecimentoNomes)
				{
					conhecimentoAux += conhecimentoNome+ "\n";
				}

				conhecimentoAux += "----";
				candidato.setObservacao(candidato.getObservacao() + conhecimentoAux);
			}
		}

		return candidatos;
	}

	public void importaBDS(java.io.File arquivoBDS, Solicitacao solicitacao) throws Exception
	{
		if(arquivoBDS == null || (!arquivoBDS.exists()))
			throw new FileNotFoundException();

		String nomeXml = StringUtil.mudaExtensaoFile(arquivoBDS.getAbsolutePath(), "");

		//Descompacta arquivo .fortesrh
		Zip unZip = new Zip();
		unZip.unzip(arquivoBDS.getAbsolutePath(), "");

		//Pega xml descompactado
		java.io.File xmlFile = new java.io.File(nomeXml);

		//Lendo um xml
        try
		{
        	Collection<Candidato> candidatoImportados;

        	String encoding = "UTF-8";
        	XStream stream = new XStream(new DomDriver(encoding));
        	BufferedReader inputXml = new BufferedReader(new FileReader(xmlFile));

        	candidatoImportados = (Collection<Candidato>) stream.fromXML(inputXml);
			inputXml.close();

			candidatoImportados = retiraCandidatoSolicitacao(candidatoImportados, solicitacao);

			salvaCandidatosImportados(candidatoImportados, solicitacao);
		}
		finally
		{
			arquivoBDS.delete();
			if(xmlFile != null && xmlFile.exists())
			{
				xmlFile.delete();
			}
		}
	}

	//TODO: BACALHAU findById dentro do for
	private Collection<Candidato> retiraCandidatoSolicitacao(Collection<Candidato> candidatoImportados, Solicitacao solicitacao)
	{
		//Busca candidatos ja cadastrados na base (procura ppor cpf)
		Collection<Candidato> candidatosJaCadastrados = getCandidatosByCpf(candidatoImportados, solicitacao.getEmpresa().getId());
		//Pega candidatos ja cadastrados na solicitacao
		Collection<CandidatoSolicitacao> candidatosSolicitacaos = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacao, null);

		Collection<Candidato> candidatos = new ArrayList<Candidato>();
		Collection<Candidato> candidatosRemovidos = new ArrayList<Candidato>();

		for(Candidato candidato : candidatoImportados)
		{
			for(Candidato candidatoJaCadastrado : candidatosJaCadastrados)
			{
				if(candidato.getPessoal() != null && candidato.getPessoal().getCpf() != null && candidato.getPessoal().getCpf().equals(candidatoJaCadastrado.getPessoal().getCpf()))
				{
					candidatoJaCadastrado = findById(candidatoJaCadastrado.getId());

					//VERIFICA SE OS DADOS DO CANDIDATO VINDO DO ARQUIVO XML É MAIS RECENTE DO QUE OS DADOS DO BANCO.
					if(candidato.getDataAtualizacao().after(candidatoJaCadastrado.getDataAtualizacao()))
					{
						candidato.setId(candidatoJaCadastrado.getId());
						candidato.setFoto(candidatoJaCadastrado.getFoto());
						candidato.setBlackList(candidatoJaCadastrado.isBlackList());
						candidato.setContratado(candidatoJaCadastrado.isContratado());
						candidato.setDisponivel(candidatoJaCadastrado.isDisponivel());
						candidato.setOrigem(candidatoJaCadastrado.getOrigem());
					}
					else
					{
						candidato = candidatoJaCadastrado;
					}

					break;
				}
			}
			candidatos.add(candidato);
		}

		for(Candidato candidato : candidatos)
		{
			for(CandidatoSolicitacao candidatoSolicitacao : candidatosSolicitacaos)
			{
				if(candidato.getPessoal() != null && candidato.getPessoal().getCpf() != null && candidato.getPessoal().getCpf().equals(candidatoSolicitacao.getCandidato().getPessoal().getCpf()))
				{
					candidatosRemovidos.add(candidato);
					break;
				}
			}
		}

		candidatos.removeAll(candidatosRemovidos);

		return candidatos;
	}

	private Collection<Candidato> getCandidatosByCpf(Collection<Candidato> candidatoImportados, Long empresaId)
	{
		return getDao().getCandidatosByCpf(getCpfByCandidato(candidatoImportados), empresaId);
	}

	private String[] getCpfByCandidato(Collection<Candidato> candidatoImportados)
	{
		String[] strCpf = new String[candidatoImportados.size()];
		int cont = 0;
		for (Candidato candidato : candidatoImportados)
		{
			if(candidato.getPessoal() != null && candidato.getPessoal().getCpf() != null && !candidato.getPessoal().getCpf().equals(""))
			{
				strCpf[cont] = candidato.getPessoal().getCpf();
				cont++;
			}
		}

		return strCpf;
	}

	//TODO: BACALHAU findById para 4 campos
	private void salvaCandidatosImportados(Collection<Candidato> candidatoImportados, Solicitacao solicitacao) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try{
			solicitacao = solicitacaoManager.findById(solicitacao.getId());

			Collection<Cargo> cargos = new ArrayList<Cargo>();
			if(solicitacao.getFaixaSalarial().getCargo() != null)
				cargos.add(solicitacao.getFaixaSalarial().getCargo());

			Collection<AreaInteresse> areas = new ArrayList<AreaInteresse>();
			if(solicitacao.getAreaOrganizacional().getAreasInteresse() != null && !solicitacao.getAreaOrganizacional().getAreasInteresse().isEmpty())
				areas.addAll(solicitacao.getAreaOrganizacional().getAreasInteresse());

			Date data = new Date();
			for (Candidato candidato : candidatoImportados)
			{
				candidato.setDataAtualizacao(data);
				candidato.setAreasInteresse(areas);
				candidato.setCargos(cargos);
				candidato.setEmpresa(solicitacao.getEmpresa());

				if(candidato.getId() == null)
				{
					candidato.setBlackList(false);
					candidato.setContratado(false);
					candidato.setDisponivel(true);
					candidato.setOrigem(OrigemCandidato.BDS);
					candidato.getPessoal().setRgUf(null);
					candidato.getPessoal().getCtps().setCtpsUf(null);

					save(candidato);
				}
				else
					update(candidato);

				Collection<CandidatoIdioma> candidatoIdiomas = candidato.getCandidatoIdiomas();
				Collection<Formacao> formacaos = candidato.getFormacao();
				Collection<Experiencia> experiencias = candidato.getExperiencias();

				candidatoIdiomaManager.montaIdiomasBDS(candidatoIdiomas, candidato);
				formacaoManager.montaFormacaosBDS(formacaos, candidato);
				experienciaManager.montaExperienciasBDS(experiencias, candidato);

				CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
				candidatoSolicitacao.setCandidato(candidato);
				candidatoSolicitacao.setTriagem(true);
				candidatoSolicitacao.setSolicitacao(solicitacao);

				candidatoSolicitacaoManager.save(candidatoSolicitacao);
			}

			transactionManager.commit(status);

		}catch(Exception e){
			transactionManager.rollback(status);
			throw e;
		}

	}

	public Candidato saveOrUpdateCandidatoByColaborador(Colaborador colaborador)
	{
		Candidato candidato = null;
		
		if(colaborador.getCandidato() == null || colaborador.getCandidato().getId() == null)
			candidato = new Candidato();
		else
			candidato = colaborador.getCandidato();

		candidato.setPessoal(colaborador.getPessoal());
		candidato.setDataAtualizacao(new Date());
		candidato.setCursos(colaborador.getCursos());
		candidato.setEndereco(colaborador.getEndereco());
		candidato.setNome(colaborador.getNome());
		candidato.setColocacao(Vinculo.EMPREGO);
		candidato.setPretencaoSalarial(null);
		candidato.setDisponivel(true);
		candidato.setBlackList(false);
		candidato.setContratado(false);
		candidato.setObservacao(colaborador.getObservacao());
		candidato.setOrigem(OrigemCandidato.CADASTRADO);
		candidato.setEmpresa(colaborador.getEmpresa());
		
		Contato contato = new Contato();
		if(colaborador.getContato() != null)
		{
			contato.setDdd(colaborador.getContato().getDdd());
			contato.setEmail(colaborador.getContato().getEmail());
			contato.setFoneFixo(colaborador.getContato().getFoneFixo());			
		}
		
	   	candidato.setContato(contato);

	   	SocioEconomica socioEconomica = new SocioEconomica();
	   	candidato.setSocioEconomica(socioEconomica);

	   	if(candidato.getId() == null)
	   		candidato = save(candidato);
	   	
    	Collection<Formacao> formacaos = colaborador.getFormacao();
    	for (Formacao formacao : formacaos)
		{
    		formacao.setCandidato(candidato);
		}
		candidato.setFormacao(formacaos);

    	Collection<Experiencia> experiencias = colaborador.getExperiencias();
    	for (Experiencia experiencia : experiencias)
		{
    		if(experiencia.getCargo() == null || experiencia.getCargo().getId() == null)
    			experiencia.setCargo(null);

    		experiencia.setCandidato(candidato);
		}

    	candidato.setExperiencias(experiencias);

	   	candidato.setCandidatoIdiomas(candidatoIdiomaManager.montaCandidatoIdiomaByColaboradorIdioma(colaborador.getColaboradorIdiomas(), candidato));

	   	update(candidato);

		return candidato;
	}

	public String recuperaSenha(String cpf, Empresa empresa) 
	{
		try
		{
			String mensagem = "Candidato não localizado!";
			Candidato candidato = findCandidatoCpf(cpf, empresa.getId());
	
			if(candidato != null)
			{
				if (candidato.getContato().getEmail() == null || candidato.getContato().getEmail().equals(""))
					mensagem = "Candidato não possui email cadastrado!\n Por favor entre em contato com a empresa.";
				else
				{
					enviaNovaSenha(candidato, empresa);
					mensagem = "Nova Senha enviada por e-mail (" + candidato.getContato().getEmail() +"). <br>(Caso não tenha recebido, favor entrar em contato com a empresa)";					
				}
			}
	
			return mensagem;
		}
		catch (NonUniqueResultException notUniqueResultException) 
		{
			return "Caro Sr(a) não identificamos uma senha associada ao seu cpf!<br> Por favor entre em contato com a empresa.";
		}
		
	}

	private void enviaSenha(Candidato candidato, Empresa empresa, String senha)
	{
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		String link = parametrosDoSistema.getAppUrl();
		String subject = "Reenvio de senha.";

		String nomeUsuario = candidato.getNome();

		StringBuilder body = new StringBuilder();
		body.append("Sr(a) " + nomeUsuario + ", <br>");
		body.append("sua senha do sistema FortesRH é : " + senha + "<br>");
		body.append("Acesse o RH em:<br>");
		body.append("<a href='" + link + "'>RH</a>");

		try
		{
			mail.send(empresa, subject,body.toString(), null, candidato.getContato().getEmail());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void enviaNovaSenha(Candidato candidato, Empresa empresa)
	{
		//Gera uma nova senha e envia
		String senha = StringUtil.getSenhaRandom(6);
		getDao().atualizaSenha(candidato.getId(), StringUtil.encodeString(senha));

		enviaSenha(candidato, empresa, senha);

	}

	private void enviaEmailEsqueciMinhaSenha(Candidato candidato, Empresa empresa)
	{
		//Recupera a senha do candidato e envia
		String senha = StringUtil.decodeString(candidato.getSenha());

		enviaSenha(candidato, empresa, senha);
	}

	private Candidato findCandidatoCpf(String cpf, Long empresaId)
	{
		return getDao().findCandidatoCpf(cpf, empresaId);
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void updateSetContratado(Long candidatoId)
	{
		getDao().updateSetContratado(candidatoId);
	}

	public void updateBlackList(String observacao, boolean blackList, Long... candidatoIds)
	{
		getDao().updateBlackList(observacao, blackList, candidatoIds);
	}

	public void setBlackList(HistoricoCandidato historicoCandidato, Long candidatoSolicitacaoId, boolean blacklist)
	{
		if(historicoCandidato.getApto() == Apto.NAO)
		{
			CandidatoSolicitacao candidatoSol = candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacaoId);

			if(blacklist)
				updateBlackList(historicoCandidato.getObservacao(), blacklist, candidatoSol.getCandidato().getId());
		}
	}

	public void setBlackList(HistoricoCandidato historicoCandidato, String[] candidatosCheck, boolean blacklist) throws Exception
	{
		if (historicoCandidato.getApto() == Apto.NAO)
		{
			if (blacklist)
			{
				Long[] candidatoSolicitacaoIds = StringUtil.stringToLong(candidatosCheck);
				Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacaoIds);

				Long[] candidatoIds = new Long[candidatoSolicitacaos.size()];
				int cont = 0;

				for (CandidatoSolicitacao candidatoSolicitacao : candidatoSolicitacaos)
				{
					candidatoIds[cont++] = candidatoSolicitacao.getCandidato().getId();
				}

				updateBlackList(historicoCandidato.getObservacao(), blacklist, candidatoIds);
			}
		}
	}

	public Candidato findByIdProjection(Long candidatoId)
	{
		return getDao().findByIdProjection(candidatoId);
	}

	public Candidato findByCandidatoId(Long id)
	{
	    return getDao().findByCandidatoId(id);
	}

	public Collection<Conhecimento> findConhecimentosByCandidatoId(Long candidatoId)
	{
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();

		List arrayConhecimentos = getDao().findConhecimentosByCandidatoId(candidatoId);

		Conhecimento conhecimento;
		Object[] retorno;

		for (Object object : arrayConhecimentos)
		{
			conhecimento = new Conhecimento();
			retorno = (Object[])object;

			conhecimento.setId((Long) retorno[0]);
			conhecimento.setNome((String) retorno[1]);

			conhecimentos.add(conhecimento);
		}

		return conhecimentos;
	}

	public Collection<Cargo> findCargosByCandidatoId(Long candidatoId)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		List arrayCargos = getDao().findCargosByCandidatoId(candidatoId);

		Cargo cargo;
		Object[] retorno;

		for (Object object : arrayCargos)
		{
			cargo = new Cargo();
			retorno = (Object[])object;

			cargo.setId((Long) retorno[0]);
			cargo.setNomeMercado((String) retorno[1]);

			cargos.add(cargo);
		}

		return cargos;
	}

	public Collection<AreaInteresse> findAreaInteressesByCandidatoId(Long candidatoId)
	{
		Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();

		List arrayAreaInteresses = getDao().findAreaInteressesByCandidatoId(candidatoId);

		AreaInteresse areaInteresse;
		Object[] retorno;

		for (Object object : arrayAreaInteresses)
		{
			areaInteresse = new AreaInteresse();
			retorno = (Object[])object;

			areaInteresse.setId((Long) retorno[0]);
			areaInteresse.setNome((String) retorno[1]);

			areaInteresses.add(areaInteresse);
		}

		return areaInteresses;
	}
	
	private String getTextoCurriculo(File ocrTexto) throws FormatoArquivoInvalidoException, Exception
	{
		if(ocrTexto != null)
		{
			
			if (ocrTexto.getContentType() == null || 
					(ocrTexto.getContentType() != null && !ocrTexto.getContentType().equals("text/plain")))
			{
				boolean extensaoValida=false;
				String extensao = "";
				int pos = ocrTexto.getName().indexOf(".");
				
				if(pos > 0)
				{
					extensao = ocrTexto.getName().substring(pos); 
					
					if (extensao.equalsIgnoreCase(".txt") || (extensao.equalsIgnoreCase(".ocr")))
						extensaoValida=true;
				}

				if (!extensaoValida)
					throw new FormatoArquivoInvalidoException("Utilize um arquivo de texto (ex.: arquivo.txt).");
			}
			
			return ArquivoUtil.convertToLatin1Compatible(ocrTexto.getBytes());
		}
		
		return "";
	}

	public String getTextoExamePalografico(File ocrTexto) throws Exception
	{
		return getTextoCurriculo(ocrTexto);
	}

	public Candidato saveCandidatoCurriculo(Candidato candidato, File[] imagemEscaneada, File ocrTexto) throws Exception
	{
		if (StringUtils.isNotBlank(getTextoCurriculo(ocrTexto)))
			candidato.setOcrTexto(ArquivoUtil.convertToLatin1Compatible(ocrTexto.getBytes()));
		
		if (candidato.getId() == null)
			getDao().save(candidato);
		else
		{
			//TODO cuidado ta usando findById, até o dia 10/05/2011 a consulta era pequena, falou
			Candidato candTemp = getDao().findById(candidato.getId());
			candTemp.setDataAtualizacao(new Date());
			candTemp.setNome(candidato.getNome());
			candTemp.setCargos(candidato.getCargos());
			candTemp.setColocacao(candidato.getColocacao());
			candTemp.getPessoal().setIndicadoPor(candidato.getPessoal().getIndicadoPor());
			candTemp.getPessoal().setDataNascimento(candidato.getPessoal().getDataNascimento());
			candTemp.getPessoal().setCpf(candidato.getPessoal().getCpf());
			candTemp.getPessoal().setSexo(candidato.getPessoal().getSexo());
			
			if(candidato.getEndereco().getCidade() == null || candidato.getEndereco().getCidade().getId() == null || candidato.getEndereco().getCidade().getId().equals(-1L))
				candTemp.getEndereco().setCidade(null);
			else
				candTemp.getEndereco().setCidade(candidato.getEndereco().getCidade());
			
			if(candidato.getEndereco().getUf() == null || candidato.getEndereco().getUf().getId() == null)
				candTemp.getEndereco().setUf(null);
			else
				candTemp.getEndereco().setUf(candidato.getEndereco().getUf());
				
			candTemp.getEndereco().setBairro(candidato.getEndereco().getBairro());
			if(StringUtils.isNotBlank(candidato.getOcrTexto()))
				candTemp.setOcrTexto(candidato.getOcrTexto());
			
			getDao().update(candTemp);
		}

		if (imagemEscaneada != null)
		{
			Collection<CandidatoCurriculo> candidatoCurriculos;
			candidatoCurriculos = candidatoCurriculoManager.findToList(new String[]{"id","curriculo"}, new String[]{"id","curriculo"}, new String[]{"candidato.id"}, new Object[]{candidato.getId()});

			if(candidatoCurriculos != null && candidatoCurriculos.size() > 0)
			{
				CollectionUtil<CandidatoCurriculo> clu = new CollectionUtil<CandidatoCurriculo>();
				ArquivoUtil.deletaArquivos("curriculos", clu.convertCollectionToArrayString(candidatoCurriculos, "getCurriculo"));
				candidatoCurriculoManager.remove(clu.convertCollectionToArrayIds(candidatoCurriculos));
			}
			
			java.io.File fileTmp[] = new java.io.File[imagemEscaneada.length];

			for(int i=0; i<imagemEscaneada.length; i++)
			{
				fileTmp[i] = ArquivoUtil.salvaArquivo("curriculos", imagemEscaneada[i], true);

				CandidatoCurriculo candidatoCurriculo = new CandidatoCurriculo();
				candidatoCurriculo.setCandidato(candidato);
				candidatoCurriculo.setCurriculo(fileTmp[i].getName());
				candidatoCurriculo = candidatoCurriculoManager.save(candidatoCurriculo);
				candidatoCurriculos.add(candidatoCurriculo);
			}

			candidato.setCandidatoCurriculos(candidatoCurriculos);
		}

		return candidato;
	}

	public String getOcrTextoById(Long candidatoId)
	{
		Candidato candidato = new Candidato();
		Collection<Candidato> candidatos = getDao().findToList(new String[]{"ocrTexto"}, new String[]{"ocrTexto"}, new String[]{"id"}, new Object[]{candidatoId});

		if(candidatos.size() > 0 )
			candidato = (Candidato) candidatos.toArray()[0];

		return candidato.getOcrTexto();
	}

	public void atualizaTextoOcr(Candidato candidato)
	{
		getDao().atualizaTextoOcr(candidato);
	}

	public void setBairroManager(BairroManager bairroManager)
	{
		this.bairroManager = bairroManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public void setCandidatoIdiomaManager(CandidatoIdiomaManager candidatoIdiomaManager)
	{
		this.candidatoIdiomaManager = candidatoIdiomaManager;
	}

	public void setExperienciaManager(ExperienciaManager experienciaManager)
	{
		this.experienciaManager = experienciaManager;
	}

	public void setFormacaoManager(FormacaoManager formacaoManager)
	{
		this.formacaoManager = formacaoManager;
	}

	public void setAnuncioManager(AnuncioManager anuncioManager)
	{
		this.anuncioManager = anuncioManager;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public void setCandidatoCurriculoManager(CandidatoCurriculoManager candidatoCurriculoManager)
	{
		this.candidatoCurriculoManager = candidatoCurriculoManager;
	}

	public Collection<Candidato> getCandidatosByNome(String candidatoNome)
	{
		return getDao().getCandidatosByNome(candidatoNome);
	}

	public Collection<Candidato> getCandidatosByExperiencia(Map<String, Object> parametros, long empresaId)
	{
		return getDao().getCandidatosByExperiencia(parametros, empresaId);
	}

	public void update(Candidato candidato)
	{
		if(candidato.getPessoal().getRgUf() != null && candidato.getPessoal().getRgUf().getId() == null)
			candidato.getPessoal().setRgUf(null);

		if(candidato.getPessoal().getCtps() != null && candidato.getPessoal().getCtps().getCtpsUf() != null  && candidato.getPessoal().getCtps().getCtpsUf().getId() == null)
			candidato.getPessoal().getCtps().setCtpsUf(null);

		super.update(candidato);
	}

	public Collection<AvaliacaoCandidatosRelatorio> findRelatorioAvaliacaoCandidatos(Date dataIni, Date dataFim, Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, char statusSolicitacao) throws ColecaoVaziaException
	{
		Collection<AvaliacaoCandidatosRelatorio> avaliacaoCandidatos = getDao().findRelatorioAvaliacaoCandidatos(dataIni, dataFim, empresaId, estabelecimentoIds, areaIds, cargoIds, statusSolicitacao);

		if (avaliacaoCandidatos == null || avaliacaoCandidatos.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		Collection<EtapaSeletiva> etapaSeletivas = etapaSeletivaManager.findAllSelect(empresaId);

		CollectionUtil<AvaliacaoCandidatosRelatorio> collectionUtil = new CollectionUtil<AvaliacaoCandidatosRelatorio>();
		// criando uma coleção com etapas distintas
		Collection<AvaliacaoCandidatosRelatorio> resultado = collectionUtil.distinctCollection(avaliacaoCandidatos);

		// somando quantidades pertencentes as etapas repetidas
		for (AvaliacaoCandidatosRelatorio avaliacao : resultado)
		{
			for (AvaliacaoCandidatosRelatorio avaliacao2 : avaliacaoCandidatos)
			{
				if (avaliacao.getEtapaSeletiva() != null && avaliacao2.getEtapaSeletiva() != null &&
						avaliacao.getEtapaSeletiva().getNome().equalsIgnoreCase(avaliacao2.getEtapaSeletiva().getNome()))
				{
					avaliacao.setOnceQtdAptos(avaliacao2.getQtdAptos());
					avaliacao.setOnceQtdNaoAptos(avaliacao2.getQtdNaoAptos());
				}
			}

			// setando a ordem
			for (EtapaSeletiva etapaSeletiva : etapaSeletivas)
			{
				if (avaliacao.getEtapaSeletiva() != null && avaliacao.getEtapaSeletiva().getNome().equals(etapaSeletiva.getNome()))
				{
					avaliacao.getEtapaSeletiva().setOrdem(etapaSeletiva.getOrdem());
				}
			}
		}

		resultado = collectionUtil.sortCollection(resultado, "etapaSeletiva.ordem");

		return resultado;
	}

	public void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId)
	{
		gerenciadorComunicacaoManager.enviaEmailResponsavelRh(nomeCandidato, empresaId);
	}
	
	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager)
	{
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public Collection<Candidato> findByNomeCpf(Candidato candidato, Long empresaId)
	{
		return getDao().findByNomeCpf(candidato, empresaId);
	}
	
	public Collection<Candidato> findByNomeCpfAllEmpresas(Candidato candidato)
	{
		return getDao().findByNomeCpf(candidato, null);
	}

	public void migrarBairro(String bairro, String bairroDestino)
	{
		getDao().migrarBairro(bairro, bairroDestino);
	}

	public Collection<Candidato> buscaSimplesDaSolicitacao(Long empresaId, String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long cidade, String[] cargosCheck, String[] conhecimentosCheck, Long solicitacaoId, boolean somenteSemSolicitacao, Integer qtdRegistro, String ordenar)
	{
		Collection<Long> candidatosJaSelecionados = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacaoId);
		if(empresaId == -1L)
			return getDao().findCandidatosForSolicitacaoAllEmpresas(indicadoPor, nomeBusca, cpfBusca, escolaridade, uf, cidade, cargosCheck, conhecimentosCheck, candidatosJaSelecionados, somenteSemSolicitacao, qtdRegistro, ordenar);
		else
			return getDao().findCandidatosForSolicitacaoByEmpresa(empresaId, indicadoPor, nomeBusca, cpfBusca, escolaridade, uf, cidade, StringUtil.stringToLong(cargosCheck), StringUtil.stringToLong(conhecimentosCheck), candidatosJaSelecionados, somenteSemSolicitacao, qtdRegistro, ordenar);
	}

	public Candidato verifyCPF(String cpf, Long empresaId, Long candidatoId, Boolean contratado) throws Exception 
	{
		String cpfSemMascara = cpf.replaceAll("\\.", "").replaceAll("-", "").trim();
		if(cpfSemMascara.equals(""))
			return null;
		else
			return getDao().findByCPF(cpfSemMascara, empresaId, candidatoId, contratado, false);
	}
	
	public Candidato findByCPF(String cpf, Long empresaId, boolean verificaColaborador)
    {
			return getDao().findByCPF(cpf, empresaId, null, null, false);
    }

	public void ajustaSenha(Candidato candidato) 
	{
		if(StringUtils.isBlank(candidato.getSenha()))
			candidato.setSenha(getDao().getSenha(candidato.getId()));
		else
			candidato.setSenha(StringUtil.encodeString(candidato.getSenha()));

	}

	public String[] montaStringBuscaF2rh(Curriculo curriculo, Long uf, Long cidadeValue, String escolaridadeValue, Date dataCadIni, Date dataCadFim, String idadeMin, String idadeMax, Long idiomaValue, Map ufs, Map cidades, Collection<Idioma> idiomas, Integer page) 
	{
		String nome = "";
		String cpf = "";
		String escolaridade = "";
		String idioma = "";
		String data_cad_ini = "";
		String data_cad_fim = "";
		String cargo = "";
		String sexo = "";
		String idade_ini = "";
		String idade_fim = "";
		String estado = "";
		String cidade = "";
		String bairro = "";
		String palavra_chave = "";
		String pagina = "";
		
		nome = montaParametro(nome, "nome", "");
		cpf = montaParametro(cpf, "cpf", "");
		escolaridade = montaParametro(escolaridade, "escolaridade", Escolaridade.getEscolaridadeF2rh(escolaridadeValue));
		idioma = montaParametro(idioma, "idioma", getIdioma(idiomas, idiomaValue));
		data_cad_ini = montaParametro(data_cad_ini, "data_cad_ini", DateUtil.formataDate(dataCadIni, "yyyy-MM-dd"));
		data_cad_fim = montaParametro(data_cad_fim, "data_cad_fim", DateUtil.formataDate(dataCadFim, "yyyy-MM-dd"));
		cargo = montaParametro(cargo, "cargo", curriculo.getCargo());
		sexo = montaParametro(sexo, "sexo", curriculo.getSexo());
		idade_ini = montaParametro(idade_ini, "idade_ini", idadeMin);
		idade_fim = montaParametro(idade_fim, "idade_fim", idadeMax);
		estado = montaParametro(estado, "estado", (String) ufs.get(uf));
		if(cidades != null && cidades.size() > 0)
			cidade = montaParametro(cidade, "cidade", (String) cidades.get(cidadeValue));
		
		pagina = montaParametro(pagina, "page", String.valueOf(page));
		bairro = montaParametro(bairro, "bairro", curriculo.getBairro());
		palavra_chave = montaParametro(palavra_chave, "palavra_chave", curriculo.getObservacoes_complementares());
		
		return new String[]{nome, cpf, escolaridade, idioma, data_cad_ini, data_cad_fim, cargo, sexo, idade_ini, idade_fim, estado, cidade, bairro, palavra_chave, pagina};
	}

	private String getIdioma(Collection<Idioma> idiomas, Long value) 
	{
		if(value != null)
		{
			for (Idioma idioma : idiomas) {
				if(idioma.getId().equals(value))
					return idioma.getNome();
			}
		}
		
		return "";
	}

	private String montaParametro(String variavel, String chave, String value) {
		if(StringUtils.isNotBlank(value))
		{
			try {
				variavel = chave + "=" + value;
			} catch (Exception e){
				variavel = chave + "=";
			}			
		}
		
		return variavel;
	}

	public Collection<Candidato> getCurriculosF2rh(String[] curriculosId, Empresa empresa) 
	{
		F2rhFacade f2rhFacade = new F2rhFacadeImpl();
		Collection<Curriculo> curriculos = new ArrayList<Curriculo>();
		
		try {
			curriculos = f2rhFacade.buscarCurriculosComFoto(f2rhFacade.montaIds(curriculosId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collection<Candidato> candidatos = new ArrayList<Candidato>();

		for (Curriculo curriculo : curriculos) 
		{
			Candidato candidato = new Candidato();
			candidato.setEmpresa(empresa);
			bind(candidato, curriculo);
			
			Candidato candidatoJaGravado = findByCPF(candidato.getPessoal().getCpf(), null, false);
			if(candidatoJaGravado == null)
				candidatos.add(getDao().save(candidato));
			else
				candidatos.add(candidatoJaGravado);
		}
		
		return candidatos;
	}

	//F2RH
	private void bind(Candidato candidato, Curriculo curriculo) 
	{
		candidato.setIdF2RH(curriculo.getId());
		candidato.setNome(StringUtil.subStr(curriculo.getNome(), 60));
		candidato.setFoto(curriculo.getFoto()); // anexa foto
		
		Pessoal pessoal = new Pessoal();
		pessoal.setDataNascimento(DateUtil.montaDataByString(curriculo.getData_nascimento_rh()));
		pessoal.setCpf(StringUtil.subStr(curriculo.getCpf(), 11));
		pessoal.setEscolaridade(Escolaridade.bindF2rh(curriculo.getEscolaridade_rh()));
		
		if(curriculo.getSexo() != null)
			pessoal.setSexo(curriculo.getSexo().charAt(0));
		
		candidato.setDataCadastro(new Date());
		candidato.setDataAtualizacao(DateUtil.montaDataByString(curriculo.getUpdated_rh()));
		
		candidato.setColocacao(Vinculo.EMPREGO);
		candidato.setPretencaoSalarial(null);
		candidato.setDisponivel(true);
		candidato.setBlackList(false);
		candidato.setContratado(false);
		candidato.setObservacao(curriculo.getObservacoes_complementares());
		candidato.setOrigem(OrigemCandidato.F2RH);

		Contato contato = new Contato();
		contato.setEmail(StringUtil.subStr(curriculo.getUser().getEmail(), 40));
	   	contato.setDdd(StringUtil.subStr(curriculo.getDdd_rh(), 5));
	   	contato.setFoneFixo(StringUtil.subStr(curriculo.getTelefone_rh(), 10));
	   	
	   	Endereco endereco = new Endereco();
	   	endereco.setBairro(StringUtil.subStr(curriculo.getBairro(), 20));
	   	endereco.setLogradouro(StringUtil.subStr(curriculo.getEndereco(), 40));
	   	endereco.setCep(StringUtil.subStr(curriculo.getCep(), 10));
	   	
	   	try {
	   		Estado estado = estadoManager.findBySigla(curriculo.getEstado()); 
	   		endereco.setUf(estado);

	   		try {
	   			if(StringUtils.isNotBlank(curriculo.getCidade_rh()))
	   				endereco.setCidade(cidadeManager.findByNome(curriculo.getCidade_rh(), estado.getId()));
	   		} catch (Exception e) {}
		} catch (Exception e) {}

		
		candidato.setPessoal(pessoal);
		candidato.setContato(contato);
		candidato.setEndereco(endereco);
	   	
	}

	public void habilitaByColaborador(Long colaboradorId) 
	{
		getDao().updateDisponivelAndContratadoByColaborador(true, false, colaboradorId);
	}

	public void reabilitaByColaborador(Long colaboradorId) 
	{
		getDao().updateDisponivelAndContratadoByColaborador(false, true, colaboradorId);		
	}

	public void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas)
	{
		Date DiaDoMesDeReferencia = DateUtil.retornaDataDiaAnterior(new Date());
		Date inicioMes = DateUtil.getInicioMesData(DiaDoMesDeReferencia);
		Date fimMes = DateUtil.getUltimoDiaMes(DiaDoMesDeReferencia);
		
		Collection<Candidato> candidatos = findQtdCadastradosByOrigem(inicioMes, fimMes);
		
		gerenciadorComunicacaoManager.enviaEmailQtdCurriculosCadastrados(empresas, inicioMes, fimMes, candidatos);
	}

	private Collection<Candidato> findQtdCadastradosByOrigem(Date date, Date date2) 
	{
		return getDao().findQtdCadastradosByOrigem(date, date2);
	}

	public String getComoFicouSabendoVagas() 
	{
		Collection<String> comoFicouSabendoVagas = getDao().getComoFicouSabendoVagas();
		if(comoFicouSabendoVagas.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(comoFicouSabendoVagas);
	}

	public void updateExamePalografico(Candidato candidato) 
	{
			getDao().updateExamePalografico(candidato);
	}

	public Collection<Candidato> triagemAutomatica(Solicitacao solicitacao, Integer tempoExperiencia, Map<String, Integer> pesos, Integer percentualMinimo) 
	{
		return getDao().triagemAutomatica(solicitacao, tempoExperiencia, pesos, percentualMinimo);
	}

	public int findQtdCadastrados(Long empresaId, Date dataDe, Date dataAte) {
		return getDao().findQtdCadastrados(empresaId, dataDe, dataAte);
	}

	public Collection<DataGrafico> countComoFicouSabendoVagas(Long empresaId, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoDivulgacaoVaga = new ArrayList<DataGrafico>();
		Collection<ComoFicouSabendoVaga> vagas = getDao().countComoFicouSabendoVagas(empresaId, dataIni, dataFim);
		
		for (ComoFicouSabendoVaga vaga : vagas)
			graficoDivulgacaoVaga.add(new DataGrafico(null, vaga.getNome(), vaga.getQtd(), ""));
		
		return graficoDivulgacaoVaga;
	}

	public int findQtdAtendidos(Long empresaId, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		return historicoCandidatoManager.findQtdAtendidos(empresaId, solicitacaoIds, dataIni, dataFim);
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void setEstadoManager(EstadoManager estadoManager) {
		this.estadoManager = estadoManager;
	}

	public void setCidadeManager(CidadeManager cidadeManager) {
		this.cidadeManager = cidadeManager;
	}
	
	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager) {
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager) {
		this.solicitacaoExameManager = solicitacaoExameManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

}