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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
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
import com.fortes.rh.model.captacao.CandidatoJsonVO;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.CampoExtra;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
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

@Component
@SuppressWarnings({"unchecked","rawtypes"})
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
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private CamposExtrasManager camposExtrasManager;
	
	private int totalSize;
	
	@Autowired
	CandidatoManagerImpl(CandidatoDao candidatoDao) {
		setDao(candidatoDao);
	}

	public int getTotalSize()
	{
		return totalSize;
	}
	
	public Collection<Candidato> busca(Map<String, Object> parametros, Long solicitacaoId, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar, Long... empresaIds) throws Exception{
		if( parametros.get("experiencias") != null && !parametros.get("tempoExperiencia").equals("") && !parametros.get("tempoExperiencia").equals("0")){
			Collection<Candidato> candidatosExperiencia;
			candidatosExperiencia = getDao().getCandidatosByExperiencia(parametros, empresaIds);
			Collection<Candidato> candidatos = new ArrayList<Candidato>();

			for (Candidato candidato : candidatosExperiencia)
				if(temExperiencia(candidato.getExperiencias(), (String)parametros.get("tempoExperiencia"), (Long[])parametros.get("experiencias")))
					candidatos.add(candidato);

			CollectionUtil<Candidato> cluCandidatos = new CollectionUtil<Candidato>();
			parametros.put("candidatosComExperiencia", cluCandidatos.convertCollectionToArrayIds(candidatos));
		}

		if(parametros.get("bairrosIds") != null && ((Long[])parametros.get("bairrosIds")).length > 0){
			Collection<Bairro> colBairros = bairroManager.getBairrosByIds((Long[])parametros.get("bairrosIds"));
			CollectionUtil<Bairro> cluBairro = new CollectionUtil<Bairro>();
			parametros.put("bairros", cluBairro.convertCollectionToArrayString(colBairros, "getNome"));
		}

		Collection<Long> idsCandidatos = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacaoId);
		Collection<Candidato> retorno = getDao().findBusca(parametros, empresaIds, idsCandidatos, somenteSemSolicitacao, qtdRegistros, ordenar);
		
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

	public Collection<Candidato> list(int page, int pagingSize, String nomeBusca, String cpfBusca, String ddd, String foneFixo, String foneCelular, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Long... empresasIds)
	{
		return getDao().find(page, pagingSize, nomeBusca, cpfBusca, ddd, foneFixo, foneCelular, indicadoPor, visualizar, dataIni, dataFim, observacaoRH, exibeContratados, exibeExterno, empresasIds);
	}

	public Integer getCount(String nomeBusca, String cpfBusca, String ddd, String foneFixo, String foneCelular, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Long... empresasIds)
	{
		return getDao().getCount(nomeBusca, cpfBusca, ddd, foneFixo, foneCelular, indicadoPor, visualizar, dataIni, dataFim, observacaoRH, exibeContratados, exibeExterno, empresasIds);
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
		
		if(colaborador.getCandidato() == null || colaborador.getCandidato().getId() == null){
			candidato = new Candidato();
			candidato.setDataCadastro(new Date());
		}else
			candidato = colaborador.getCandidato();

		candidato.setPessoal(colaborador.getPessoal());
		candidato.setHabilitacao(colaborador.getHabilitacao());
		candidato.setDataAtualizacao(new Date());
		candidato.setCursos(colaborador.getCursos());
		candidato.setEndereco(colaborador.getEndereco());
		candidato.setNome(colaborador.getNome());
		candidato.setColocacao(Vinculo.EMPREGO);
		candidato.setPretencaoSalarial(null);
		candidato.setBlackList(false);
		candidato.setDisponivel(colaborador.isDesligado());
		candidato.setContratado(!colaborador.isDesligado());
		candidato.setObservacao(colaborador.getObservacao());
		candidato.setOrigem(OrigemCandidato.CADASTRADO);
		candidato.setEmpresa(colaborador.getEmpresa());
		candidato.setContato(colaborador.getContato());
		
		CamposExtras camposExtras = montaCamposExtras(colaborador.getCamposExtras(), candidato.getCamposExtras(), colaborador.getEmpresa().getId());
		if(camposExtras != null ){
			camposExtrasManager.saveOrUpdate(camposExtras);
			candidato.setCamposExtras(camposExtras);
		}
		
		if(candidato.getId() == null){
			SocioEconomica socioEconomica = new SocioEconomica();
			candidato.setSocioEconomica(socioEconomica);
		}
		
	   	if(candidato.getId() == null)
	   		candidato = save(candidato);
	   	
    	if (colaborador.getFormacao() != null && !colaborador.getFormacao().isEmpty()){
    		for (Formacao formacao : colaborador.getFormacao()) 
				formacao.setCandidato(candidato);
    		formacaoManager.saveOrUpdate(colaborador.getFormacao());
    		candidato.setFormacao(colaborador.getFormacao());
    	}

    	if (colaborador.getExperiencias() != null && !colaborador.getExperiencias().isEmpty()) 
    		candidato.setExperiencias(colaborador.getExperiencias());
	   	
	   	candidato.setCandidatoIdiomas(candidatoIdiomaManager.montaCandidatoIdiomaByColaboradorIdioma(colaborador.getColaboradorIdiomas(), candidato));

	   	/** Não remover está linha, pois setar o colaborador como nulo evita exceção ao gravar candidato.*/
	   	candidato.setColaborador(null);
	   	update(candidato);

		return candidato;
	}

	private CamposExtras montaCamposExtras(CamposExtras camposExtrasColaborador, CamposExtras camposExtrasCandidato, Long empresaId) {
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "ativoColaborador", "empresa.id"}, new Object[]{true, true, empresaId}, new String[]{"ordem"});
		
		if(configuracaoCampoExtras != null && configuracaoCampoExtras.size() > 0 && camposExtrasCandidato == null )
			camposExtrasCandidato = new CamposExtras();
		
		if ( camposExtrasColaborador != null ) {
			for (ConfiguracaoCampoExtra configuracaoCampoExtra : configuracaoCampoExtras) {
				if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_1.getDescricao())){
					camposExtrasCandidato.setTexto1(camposExtrasColaborador.getTexto1());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_2.getDescricao())){
					camposExtrasCandidato.setTexto2(camposExtrasColaborador.getTexto2());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_3.getDescricao())){
					camposExtrasCandidato.setTexto3(camposExtrasColaborador.getTexto3());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_4.getDescricao())){
					camposExtrasCandidato.setTexto4(camposExtrasColaborador.getTexto4());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_5.getDescricao())){
					camposExtrasCandidato.setTexto5(camposExtrasColaborador.getTexto5());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_6.getDescricao())){
					camposExtrasCandidato.setTexto6(camposExtrasColaborador.getTexto6());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_7.getDescricao())){
					camposExtrasCandidato.setTexto7(camposExtrasColaborador.getTexto7());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_8.getDescricao())){
					camposExtrasCandidato.setTexto8(camposExtrasColaborador.getTexto8());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_DATA_1.getDescricao())){
					camposExtrasCandidato.setData1(camposExtrasColaborador.getData1());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_DATA_2.getDescricao())){
					camposExtrasCandidato.setData2(camposExtrasColaborador.getData2());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_DATA_3.getDescricao())){
					camposExtrasCandidato.setData3(camposExtrasColaborador.getData3());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_VALOR_1.getDescricao())){
					camposExtrasCandidato.setValor1(camposExtrasColaborador.getValor1());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_VALOR_2.getDescricao())){
					camposExtrasCandidato.setValor2(camposExtrasColaborador.getValor2());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_NUMERO.getDescricao())){
					camposExtrasCandidato.setNumero1(camposExtrasColaborador.getNumero1());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_LONGO_1.getDescricao())){
					camposExtrasCandidato.setTextolongo1(camposExtrasColaborador.getTextolongo1());
				}
				else if(configuracaoCampoExtra.getDescricao().equals(CampoExtra.CAMPO_DE_TEXTO_LONGO_2.getDescricao())){
					camposExtrasCandidato.setTextolongo1(camposExtrasColaborador.getTextolongo1());
				}
			}
		}
		return camposExtrasCandidato;
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
		String link = parametrosDoSistema.getAppUrl() + "/externo/prepareLogin.action?empresaId=" + empresa.getId();
		String subject = "Reenvio de senha.";

		String nomeUsuario = candidato.getNome();

		StringBuilder body = new StringBuilder();
		body.append("Sr(a) " + nomeUsuario + ", <br>");
		body.append("sua senha do sistema RH é: " + senha + "<br>");
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

	private Candidato findCandidatoCpf(String cpf, Long empresaId)
	{
		return getDao().findCandidatoCpf(cpf, empresaId);
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void updateSetContratado(Long candidatoId, Long empresaId)
	{
		getDao().updateSetContratado(candidatoId, empresaId);
	}

	@TesteAutomatico
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
	
	public Collection<Candidato> findCandidatosIndicadosPor(Date dataIni, Date dataFim, Long[] empresasIds) throws ColecaoVaziaException
	{
		Collection<Candidato> candidatosIndicadosPor = getDao().findCandidatosIndicadosPor(dataIni, dataFim, empresasIds);

		if (candidatosIndicadosPor == null || candidatosIndicadosPor.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		return candidatosIndicadosPor;
	}

	public void enviaAvisoDeCadastroCandidato(String nomeCandidato, Long empresaId)
	{
		gerenciadorComunicacaoManager.enviaAvisoDeCadastroCandidato(nomeCandidato, empresaId);
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

	public Collection<Candidato> buscaSimplesDaSolicitacao(String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, String[] cargosCheck, String[] conhecimentosCheck, Long solicitacaoId, boolean somenteSemSolicitacao, Integer qtdRegistro, String ordenar, boolean opcaoTodasEmpresas, Long... empresaIds)
	{
		Collection<Long> candidatosJaSelecionados = candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacaoId);
		return  getDao().findCandidatosForSolicitacao(indicadoPor, nomeBusca, cpfBusca, escolaridade, uf, cidadesCheck, cargosCheck, conhecimentosCheck, candidatosJaSelecionados, somenteSemSolicitacao, qtdRegistro, ordenar, empresaIds, opcaoTodasEmpresas);
	}

	public Candidato verifyCPF(String cpf, Long empresaId, Long candidatoId, Boolean contratado) throws Exception 
	{
		String cpfSemMascara = cpf.replaceAll("\\.", "").replaceAll("-", "").trim();
		if(cpfSemMascara.equals(""))
			return null;
		else
		{
			Collection<Candidato> candidatos =  findByCPF(cpfSemMascara, empresaId, candidatoId, contratado);
			
			if(candidatos == null || candidatos.isEmpty())
				return null;
			else
				return (Candidato) candidatos.toArray()[0];
		}
	}
	
	public Candidato findByCPF(String cpf, Long empresaId)
    {
		Collection<Candidato> candidatos =  findByCPF(cpf, empresaId, null, null);
		
		if(candidatos == null || candidatos.isEmpty())
			return null;
		else
			return (Candidato) candidatos.toArray()[0];
    }
	
	public Collection<Candidato> findByCPF(String cpf, Long empresaId, 	Long candidatoId, Boolean contratado) 
	{
		return getDao().findByCPF(cpf, empresaId, candidatoId, contratado);
	}

	public void ajustaSenha(Candidato candidato) 
	{
		if(StringUtils.isBlank(candidato.getSenha()))
			candidato.setSenha(getDao().getSenha(candidato.getId()));
		else
			candidato.setSenha(StringUtil.encodeString(candidato.getSenha()));

	}

	@TesteAutomatico
	public void updateDisponivelAndContratadoByColaborador(boolean disponivel, boolean contratado, Long... colaboradoresIds) 
	{
		getDao().updateDisponivelAndContratadoByColaborador(disponivel, contratado, colaboradoresIds);
	}

	@TesteAutomatico
	public void updateDisponivel(boolean disponivel, Long candidatoId)
	{
		getDao().updateDisponivel(disponivel, candidatoId);
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

	@TesteAutomatico
	public void updateExamePalografico(Candidato candidato) 
	{
			getDao().updateExamePalografico(candidato);
	}

	@TesteAutomatico
	public Collection<Candidato> triagemAutomatica(Solicitacao solicitacao, Integer tempoExperiencia, Map<String, Integer> pesos, Integer percentualMinimo) 
	{
		return getDao().triagemAutomatica(solicitacao, tempoExperiencia, pesos, percentualMinimo);
	}

	@TesteAutomatico
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
	
	@TesteAutomatico
	public Collection<Colaborador> findColaboradoresMesmoCpf(String[] candidatosCpfs) 
	{
		return getDao().findColaboradoresMesmoCpf(candidatosCpfs);
	}

	public int findQtdAtendidos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		return historicoCandidatoManager.findQtdAtendidos(empresaId, estabelecimentoIds, areaIds, solicitacaoIds, dataIni, dataFim);
	}

	@TesteAutomatico
	public void deleteCargosPretendidos(Long... cargosIds)
	{
		getDao().deleteCargosPretendidos(cargosIds);
	}
	
	@TesteAutomatico
	public void inserirNonoDigitoCelular(Long[] ufIds){
		getDao().inserirNonoDigitoCelular(ufIds);
	}

	@TesteAutomatico
	public boolean existeCamposExtras(Long camposExtrasId) {
		return getDao().existeCamposExtras(camposExtrasId);
	}
	
	@TesteAutomatico
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId) {
		return getDao().getAutoComplete(descricao, empresaId);
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

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public void setCamposExtrasManager(CamposExtrasManager camposExtrasManager) {
		this.camposExtrasManager = camposExtrasManager;
	}
	
	public Collection<CandidatoJsonVO> getCandidatosJsonVO(Long etapaSeletivaId) {
		Collection<CandidatoJsonVO> candidatoJsonVOs = new ArrayList<CandidatoJsonVO>();
		Collection<Candidato> candidatos = getDao().getCandidatosByEtapaSeletiva(etapaSeletivaId);
		Map<Long, Collection<String>> funcoesPretendidasMap = getDao().getFuncoesPretendidasByEtapaSeletiva(etapaSeletivaId);
		String[] funcoesPretendidas = null;
		String dataNascimento = "";
		
		for (Candidato cd : candidatos) {
			try {
				if(funcoesPretendidasMap.containsKey(cd.getId()))
					funcoesPretendidas = new CollectionUtil<String>().convertCollectionToArrayString(funcoesPretendidasMap.get(cd.getId()));
				else
					funcoesPretendidas = new String[]{};
				
				if(cd.getPessoal().getDataNascimento() != null)
					dataNascimento = DateUtil.formataDiaMesAno(cd.getPessoal().getDataNascimento());
				
				candidatoJsonVOs.add(new CandidatoJsonVO(cd.getId().toString(), cd.getNome(), dataNascimento, 
						cd.getPessoal().getSexoDescricao(), cd.getPessoal().getCpfFormatado(), cd.getPessoal().getEscolaridadeDescricao(), 
						cd.getEndereco().getCepFormatado(), cd.getEndereco().getLogradouro(), cd.getEndereco().getNumero(), 
						cd.getEndereco().getComplemento(), cd.getEndereco().getBairro(), cd.getEndereco().getCidade().getNome(), cd.getEndereco().getUf().getSigla(), 
						cd.getContato().getEmail(), cd.getContato().getFoneFixoFormatado(), cd.getContato().getFoneCelularFormatado(), 
						cd.getPessoal().getEstadoCivilDescricao(), cd.getPessoal().getMae(), cd.getPessoal().getRg(), cd.getPessoal().getPis(), 
						funcoesPretendidas, cd.getCamposExtras().getNumero1String(), cd.getCamposExtras().getData1String()));
				
			} catch (Exception e) {
				System.out.println("Erro ao criar candidatoJsonVO - Id:" + cd.getId() + " Nome: " + cd.getNome());
			}
		}
		
		return candidatoJsonVOs;
	}
}