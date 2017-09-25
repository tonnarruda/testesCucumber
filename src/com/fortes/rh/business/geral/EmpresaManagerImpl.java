package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.dicionario.TipoEntidade;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;
import com.ibm.icu.text.SimpleDateFormat;

public class EmpresaManagerImpl extends GenericManagerImpl<Empresa, EmpresaDao> implements EmpresaManager
{
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private MotivoDemissaoManager motivoDemissaoManager;
	private AreaInteresseManager areaInteresseManager;
	private FaixaSalarialManager faixaSalarialManager;
	private ConhecimentoManager conhecimentoManager;
	private HabilidadeManager habilidadeManager;
	private OcorrenciaManager ocorrenciaManager;
	private AtitudeManager atitudeManager;
	private IndiceManager indiceManager;
	private CidadeManager cidadeManager;
	private CargoManager cargoManager;
	private RiscoManager riscoManager;
	private EpiManager epiManager;
	private Mail mail;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;
	
	public String[] getEmpresasByUsuarioEmpresa(Long usuarioId)
	{
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.find(new String[]{"usuario.id"}, new Object[]{usuarioId});

		int cont = 0;
		String[] str = null;
		if(!usuarioEmpresas.isEmpty())
		{
			str = new String[usuarioEmpresas.size()];
			for (UsuarioEmpresa usuarioEmpresa : usuarioEmpresas)
			{
				str[cont++] = usuarioEmpresa.getEmpresa().getId().toString();
			}
		}

		return str;
	}

	public Collection<UsuarioEmpresa> getPerfilEmpresasByUsuario(Long usuarioId)
	{
		return usuarioEmpresaManager.findByUsuario(usuarioId);
	}

	@TesteAutomatico(metodoMock="findByCodigo")
	public Empresa findByCodigoAC(String codigoAc, String grupoAC)
	{
		return getDao().findByCodigo(codigoAc, grupoAC);
	}

	public String saveLogo(File logo, String local)
	{
		String url = "";

		if(logo != null && !logo.getName().equals(""))
		{
			java.io.File logoSalva = ArquivoUtil.salvaArquivo(local, logo, true);

			if(logoSalva != null)
				url = logoSalva.getName();
		}

		return url;
	}

	public Empresa setLogo(Empresa empresa, File logo, String local, File logoCertificado)
	{
		String logoUrl = saveLogo(logo, local);
		String logoCertificadoUrl = saveLogo(logoCertificado, local);

		if(!logoUrl.equals(""))
			empresa.setLogoUrl(logoUrl);
		
		if(!logoCertificadoUrl.equals(""))
			empresa.setLogoCertificadoUrl(logoCertificadoUrl);

		return empresa;
	}

	@TesteAutomatico(metodoMock="getIntegracaoAC")
	public boolean findIntegracaoAC(Long id)
	{
		return getDao().getIntegracaoAC(id);
	}

	public boolean criarEmpresa(TEmpresa empresaAC)
	{
		Empresa empresa = new Empresa();
		
		empresa.setCodigoAC(empresaAC.getCodigoAC());
		empresa.setNome(empresaAC.getNome());
		empresa.setRazaoSocial(empresaAC.getRazaoSocial());
		
		try
		{
			getDao().save(empresa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean verifyExistsCnpj(Long id, String cnpj)
	{
		Collection<Empresa> empresas = getDao().verifyExistsCnpj(cnpj);
		
		if(empresas.isEmpty())
			return false;
		else
		{
			if(empresas.size() == 1 && ((Empresa)empresas.toArray()[0]).getId().equals(id))
				return false;
			else
				return true;
		}
	}

	public String findCidade(Long id)
	{
		String cidade = getDao().findCidade(id);
		
		if(cidade == null)
			cidade = "";
		
		return cidade;
	}

	public Collection<Empresa> findDistinctEmpresasByQuestionario(Long questionarioId)
	{
		return getDao().findDistinctEmpresaByQuestionario(questionarioId);
	}
	public Collection<Empresa> findDistinctEmpresasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId)
	{
		return getDao().findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenhoId);
	}

	public Collection<CheckBox> populaCadastrosCheckBox()
	{
		Collection<CheckBox> entidades = new ArrayList<CheckBox>();
		
		TipoEntidade tipoEntidade = new TipoEntidade();
		Collection<String> chaves = tipoEntidade.keySet();
		
		for (String chave : chaves) {
			CheckBox checkBox = new CheckBox();
			checkBox.setId(Long.parseLong(chave));
			checkBox.setNome(tipoEntidade.get(chave));
			
			entidades.add(checkBox);
		}
		return entidades;
	}

	public List<String> sincronizaEntidades(Long empresaOrigemId, Long empresaDestinoId, String[] cadastrosCheck, String[] tipoOcorrenciasCheck) throws Exception{
		
		Empresa empresaDestino = findByIdProjection(empresaDestinoId);
		Map<Long, Long> areaIds = new  HashMap<Long, Long>();
		Map<Long, Long> conhecimentoIds = new  HashMap<Long, Long>();
		Map<Long, Long> habilidadeIds = new  HashMap<Long, Long>();
		Map<Long, Long> atitudeIds = new  HashMap<Long, Long>();
		Map<Long, Long> areaInteresseIds = new  HashMap<Long, Long>();
		Map<Long, Long> epiIds = new  HashMap<Long, Long>();
		List<String> mensagens = new ArrayList<String>();
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.AREAS)){
			areaOrganizacionalManager.sincronizar(empresaOrigemId, empresaDestino, areaIds, mensagens);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.CONHECIMENTOS)){
			conhecimentoManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, conhecimentoIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.HABILIDADES)){
			habilidadeManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, habilidadeIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.ATITUDES)){
			atitudeManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, atitudeIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.AREAS_INTERESSE)){
			areaInteresseManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, areaInteresseIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.CARGOS)){
		    if((empresaDestino.isAcIntegra() && !empresaDestino.isAderiuAoESocial()) || !empresaDestino.isAcIntegra()){
		        cargoManager = (CargoManager) SpringUtil.getBean("cargoManager");
		        cargoManager.sincronizar(empresaOrigemId, empresaDestino, areaIds, conhecimentoIds, habilidadeIds, atitudeIds, mensagens);
		    }
		    else{
		        mensagens.add("<strong>A empresa destino está integrada com o Fortes Pessoal e aderiu ao eSocial, por isso não é possível importar cargos.</strong>");
		    }
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.TIPOS_OCORRENCIA)){
			ocorrenciaManager.sincronizar(empresaOrigemId, empresaDestino, tipoOcorrenciasCheck);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.EPIS)){
			epiManager = (EpiManager) SpringUtil.getBean("epiManager");
			epiManager.sincronizar(empresaOrigemId, empresaDestinoId, epiIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.CURSOSETURMAS)){
			TurmaManager turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
			turmaManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.MOTIVOS_DESLIGAMENTO)){
			motivoDemissaoManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.RISCOS)){
			riscoManager.sincronizar(empresaOrigemId, empresaDestinoId, epiIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.AMBIENTE)){
			AmbienteManager ambienteManager = (AmbienteManager) SpringUtil.getBean("ambienteManager");
			ambienteManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
		
		return mensagens;
	}
	
	@TesteAutomatico(metodoMock="findTodasEmpresas")
	public Collection<Empresa> findEmailsEmpresa()
	{
		return getDao().findTodasEmpresas();
	}
	
	@TesteAutomatico
	public Empresa findEmailsEmpresa(Long empresaId)
	{
		return getDao().findEmailsEmpresa(empresaId);
	}

	@TesteAutomatico
	public Empresa findByIdProjection(Long id) {
		return getDao().findByIdProjection(id);
	}

	public Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String... roles)
	{
		if(usuarioId.equals(1L))
			return getDao().findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"nome"}); 
		else
			return getDao().findByUsuarioPermissao(usuarioId, roles);
	}

	public Long[] selecionaEmpresa(Empresa empresa, Long usuarioId, String role)
	{
		if(empresa == null || empresa.getId() == null || empresa.getId() == 0)
		{
			Collection<Empresa> empresas = findByUsuarioPermissao(usuarioId, role);
			
			CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
			return clu.convertCollectionToArrayIds(empresas);				
		}
		else
			return new Long[]{empresa.getId()};
	}

	public void removeEmpresa(Empresa empresa) 
	{
		getDao().removeEmpresaPadrao(empresa.getId());
	}
	
	public void remove(Empresa empresa) 
	{
		getDao().remove(new Long[]{empresa.getId()});
	}

	public Long ajustaCombo(Long empresaId, Long empresaSistemaId) 
	{
		if(empresaId == null)
			return empresaSistemaId;
		else if(empresaId.equals(-1L))
			return null;
		else
			return empresaId;
	}

	public void atualizaCamposExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras, Empresa empresa, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato, boolean habilitaCampoExtraAtualizarMeusDados) 
	{
		Collection<Empresa> empresas = null;

		if(empresa.getId() == null || empresa.getId().equals(-1L)){/**quando for para aplicar para todas as empresa**/
			configuracaoCampoExtraManager.removeAllNotModelo();
			empresas = getDao().findTodasEmpresas();
		}
	
		Collection<String> camposExtras = new ArrayList<String>();
		String camposVisivesisColaborador = "";
		String camposVisivesisCandidato = "";
		
		for (ConfiguracaoCampoExtra campoExtra : configuracaoCampoExtras){
			saveOrUpdateConfigCamposExtras(empresa, empresas, camposExtras, campoExtra);
			
			if(campoExtra.getAtivoColaborador())
				camposVisivesisColaborador += campoExtra.getNome() + ","; 			
			if(campoExtra.getAtivoCandidato())
				camposVisivesisCandidato += campoExtra.getNome() + ",";
		}

		if(empresa.getId() == null || empresa.getId().equals(-1L)){
			for (Empresa emp : empresas) {
				getDao().updateCampoExtra(emp.getId(), habilitaCampoExtraColaborador, habilitaCampoExtraCandidato, habilitaCampoExtraAtualizarMeusDados);
				decideRemoverOrAtualizarConfigCamposVisiveiAndObrigatorios(emp.getId(), camposVisivesisColaborador, camposVisivesisCandidato);
			}
		}
		else{
			getDao().updateCampoExtra(empresa.getId(), habilitaCampoExtraColaborador, habilitaCampoExtraCandidato, habilitaCampoExtraAtualizarMeusDados);
			decideRemoverOrAtualizarConfigCamposVisiveiAndObrigatorios(empresa.getId(), camposVisivesisColaborador, camposVisivesisCandidato);
		}
	}

	private void saveOrUpdateConfigCamposExtras(Empresa empresa, Collection<Empresa> empresas, Collection<String> camposExtras, ConfiguracaoCampoExtra campoExtra) {
		camposExtras.add(campoExtra.getNome());
		
		if(campoExtra.getEmpresa().getId() == null){
			campoExtra.setEmpresa(empresa);
			campoExtra.setId(null);
		}
		
		if(empresa.getId() == null || empresa.getId().equals(-1L)){
			for (Empresa empresaTmp : empresas){
				ConfiguracaoCampoExtra campoExtraTmp = (ConfiguracaoCampoExtra) campoExtra.clone();
				campoExtraTmp.setEmpresa(empresaTmp);
				campoExtraTmp.setId(null);
				configuracaoCampoExtraManager.save(campoExtraTmp);
			}
		}else{
			if(campoExtra.getId() == null)
				configuracaoCampoExtraManager.save(campoExtra);	
			else
				configuracaoCampoExtraManager.update(campoExtra);
		}
	}

	private void decideRemoverOrAtualizarConfigCamposVisiveiAndObrigatorios( Long empresaId, String camposVisivesisColaborador, String camposVisivesisCandidato) {
		if(camposVisivesisColaborador.isEmpty())
			configuracaoCampoExtraVisivelObrigadotorioManager.removeByEmpresaAndTipoConfig(empresaId, new String[]{TipoConfiguracaoCampoExtra.COLABORADOR.getTipo()});
		else 
			configuracaoCampoExtraVisivelObrigadotorioManager.saveOrUpdateConfCamposExtras(empresaId, camposVisivesisColaborador, new String[]{TipoConfiguracaoCampoExtra.COLABORADOR.getTipo()});
		
		if(camposVisivesisCandidato.isEmpty())
			configuracaoCampoExtraVisivelObrigadotorioManager.removeByEmpresaAndTipoConfig(empresaId, new String[]{TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()});
		else 
			configuracaoCampoExtraVisivelObrigadotorioManager.saveOrUpdateConfCamposExtras(empresaId, camposVisivesisCandidato, new String[]{TipoConfiguracaoCampoExtra.CANDIDATO_EXTERNO.getTipo(), TipoConfiguracaoCampoExtra.CANDIDATO.getTipo()});
	}
	
	public Collection<Empresa> findEmpresasPermitidas(Boolean compartilhar, Long empresId, Long usuarioId, String... roles) 
	{
		if(usuarioId.equals(1L))
			return getDao().findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"nome"}); 

		if (compartilhar)
			return getDao().findByUsuarioPermissao(usuarioId, roles);

		return getDao().findById(new Long[]{empresId});
	}

	@TesteAutomatico
	public Collection<Empresa> findTodasEmpresas() {
		return getDao().findTodasEmpresas();
	}
	
	@TesteAutomatico
	public Collection<Empresa> findEmpresasIntegradas() {
		return getDao().findEmpresasIntegradas();
	}

	public boolean checkEmpresaCodACGrupoAC(Empresa empresa) {
		if (empresa.getCodigoAC() == null || empresa.getGrupoAC() == null)
			return false;
		
		return getDao().checkEmpresaCodACGrupoAC(empresa);
	}

	public String getEmpresasNaoListadas(Collection<UsuarioEmpresa> usuarioEmpresas, Collection<Empresa> empresasListadas) 
	{
		Collection<String> empresasNaoListadas = new ArrayList<String>();
		for (UsuarioEmpresa usuarioEmpresa : usuarioEmpresas) 
		{
			if(!empresasListadas.contains(usuarioEmpresa.getEmpresa()))
				empresasNaoListadas.add(usuarioEmpresa.getEmpresa().getNome());
		}
		
		if(empresasNaoListadas.isEmpty())
			return null;
		else
			return StringUtil.converteCollectionToString(empresasNaoListadas);
	}

	public boolean verificaInconcistenciaIntegracaoAC(Empresa empresa) 
	{
		if(empresa.isAcIntegra())
		{
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			
			Collection<Colaborador> colaboradors = colaboradorManager.findSemCodigoAC(empresa.getId());
			Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findSemCodigoAC(empresa.getId());
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findSemCodigoAC(empresa.getId());
			Collection<FaixaSalarial> faixaSalarials = faixaSalarialManager.findSemCodigoAC(empresa.getId());
			Collection<Indice> indices = indiceManager.findSemCodigoAC(empresa);
			Collection<Ocorrencia> ocorrencias = ocorrenciaManager.findSemCodigoAC(empresa.getId(), true);
			Collection<Cidade> cidades = cidadeManager.findSemCodigoAC();
			
			if((colaboradors != null && !colaboradors.isEmpty()) || (estabelecimentos != null && !estabelecimentos.isEmpty()) || (areaOrganizacionals != null && !areaOrganizacionals.isEmpty())
					|| (faixaSalarials != null && !faixaSalarials.isEmpty()) || (indices != null && !indices.isEmpty()) || (ocorrencias != null && !ocorrencias.isEmpty())
					|| (cidades != null && !cidades.isEmpty()))
				return true;
			
			String faixaSalarialCodAcDuplicado = faixaSalarialManager.findCodigoACDuplicado(empresa.getId());
			String cidadeCodAcDuplicado = cidadeManager.findCodigoACDuplicado();
				
			if( StringUtils.isNotEmpty(faixaSalarialCodAcDuplicado) || StringUtils.isNotEmpty(cidadeCodAcDuplicado)) 
				return true;
		}	
		
		return false;
	}
	
	public Collection<String> verificaIntegracaoAC(Empresa empresa) 
	{
		Collection<String> msgs = new ArrayList<String>();
		
		String faixaSalarialCodAcDuplicado = faixaSalarialManager.findCodigoACDuplicado(empresa.getId());
		String cidadeCodAcDuplicado = cidadeManager.findCodigoACDuplicado();
		
		msgs.add("Verifique os seguintes itens:");

		if( StringUtils.isNotEmpty(faixaSalarialCodAcDuplicado) )
			msgs.add("- Existe faixa salarial duplicada, código AC: " + faixaSalarialCodAcDuplicado);

		if( StringUtils.isNotEmpty(cidadeCodAcDuplicado) )
			msgs.add("- Existe cidade duplicada, código AC: " + cidadeCodAcDuplicado);
		
		return msgs;
	}
	
	@TesteAutomatico
	public boolean checkEmpresaIntegradaAc() {
		return getDao().checkEmpresaIntegradaAc();
	}

	@TesteAutomatico
	public boolean checkEmpresaIntegradaAc(Long empresaId) {
		return getDao().checkEmpresaIntegradaAc(empresaId);
	}

	@TesteAutomatico
	public Collection<Empresa> findComCodigoAC() {
		return getDao().findComCodigoAC();
	}

	//utilizado apenas para auditar a integração com AC
	public void auditaIntegracao(Empresa empresa, boolean tavaIntegradaComAC) 
	{
		Logger logger = Logger.getLogger(Empresa.class);
		logger.info("Auditoria da integração");
		logger.info("Empresa: " + empresa.getNome() + " id: " + empresa.getId());
		logger.info("Antes: " + tavaIntegradaComAC);
		logger.info("Depois: " + empresa.isAcIntegra() + " Grupo AC: " + empresa.getGrupoAC());
	}
	
	public void enviaEmailInformandoDesintegracao(Empresa empresa, boolean tavaIntegradaComAC, String motivo, String usuario) 
	{
		try {
			if(tavaIntegradaComAC && !empresa.isAcIntegra()) {
				String subject = "Integração com o Fortes Pessoal desmarcada pelo usuário.";
				StringBuffer body = new StringBuffer();
				
				body.append("<strong>Cliente:</strong> " + Autenticador.getRazaoSocial());
				body.append("<br /><br />");
				body.append("<strong>Usuário:</strong> " + usuario + "<br />");
				body.append("<strong>Empresa:</strong> " + empresa.getCodigoAC() + " - " + empresa.getNome() + "<br />");
				body.append("<strong>Data:</strong> " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) + "</strong><br />");
				body.append("<strong>Motivo:</strong> " + motivo);
			
				mail.send(empresa, subject, body.toString(), null, "suporte.rh@grupofortes.com.br");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public Collection<Empresa> findByLntId(Long lntId) {
		return getDao().findByLntId(lntId);
	}

	@TesteAutomatico
	public boolean isControlaRiscoPorAmbiente(Long empresaId) 
	{
		return getDao().isControlaRiscoPorAmbiente(empresaId);
	}
	
	@TesteAutomatico(metodoMock="updateCodigoAC")
	public void updateCodigoGrupoAC(Long empresaId, String codigoAC, String grupoAC) 
	{
		getDao().updateCodigoAC(empresaId, codigoAC, grupoAC);
	}

	@TesteAutomatico
	public Collection<Empresa> findByGruposAC(String... gruposAC) 
	{
		return getDao().findByGruposAC(gruposAC);
	}
	
	@TesteAutomatico
	public String getCodigoGrupoAC(Long empresaId)
	{
		return getDao().getCodigoGrupoAC(empresaId);
	}
	
	@TesteAutomatico
	public boolean emProcessoExportacaoAC(Long empresaId) 
	{
		return getDao().emProcessoExportacaoAC(empresaId);
	}

	@TesteAutomatico
	public void setProcessoExportacaoAC(Long empresaId, boolean processoExportacaoAC)
	{
		getDao().setProcessoExportacaoAC(empresaId, processoExportacaoAC);
	}
	
	public Collection<Empresa> findEmpresasComCodigoACAndAtualizouDddCelularAndUFHabilitacao(){
		return getDao().findEmpresasComCodigoACAndAtualizouDddCelularAndUFHabilitacao();
	}
	
	public void setDddCelularAndUFHabilitacaoAtualizados(Long empresaId){
		getDao().setDddCelularAndUFHabilitacaoAtualizados(empresaId);
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager) {
		this.conhecimentoManager = conhecimentoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setAreaInteresseManager(AreaInteresseManager areaInteresseManager) {
		this.areaInteresseManager = areaInteresseManager;
	}
	
	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager)
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
	
	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager) {
		this.ocorrenciaManager = ocorrenciaManager;
	}
	
	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}
	
	public OcorrenciaManager getOcorrenciaManager() {
		return ocorrenciaManager;
	}
	
	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public void setHabilidadeManager(HabilidadeManager habilidadeManager) {
		this.habilidadeManager = habilidadeManager;
	}

	public void setAtitudeManager(AtitudeManager atitudeManager) {
		this.atitudeManager = atitudeManager;
	}

	public void setMotivoDemissaoManager(MotivoDemissaoManager motivoDemissaoManager) {
		this.motivoDemissaoManager = motivoDemissaoManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setIndiceManager(IndiceManager indiceManager) {
		this.indiceManager = indiceManager;
	}

	public void setCidadeManager(CidadeManager cidadeManager) {
		this.cidadeManager = cidadeManager;
	}

	public Empresa getCnae(Long empresaId) {
		return getDao().getCnae(empresaId);
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setConfiguracaoCampoExtraVisivelObrigadotorioManager(ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager) {
		this.configuracaoCampoExtraVisivelObrigadotorioManager = configuracaoCampoExtraVisivelObrigadotorioManager;
	}
}