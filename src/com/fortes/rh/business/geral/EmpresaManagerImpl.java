/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA0026
 */

package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.Indice;
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
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class EmpresaManagerImpl extends GenericManagerImpl<Empresa, EmpresaDao> implements EmpresaManager
{
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private ConhecimentoManager conhecimentoManager;
	private HabilidadeManager habilidadeManager;
	private AtitudeManager atitudeManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AreaInteresseManager areaInteresseManager;
	private CargoManager cargoManager;
	private OcorrenciaManager ocorrenciaManager;
	private EpiManager epiManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private MotivoDemissaoManager motivoDemissaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private CidadeManager cidadeManager;
	private GastoManager gastoManager;

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

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager)
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public Empresa findByCodigoAC(String codigo, String grupoAC)
	{
		return getDao().findByCodigo(codigo, grupoAC);
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

	public Empresa setLogo(Empresa empresa, File logo, String local, File logoCertificado, File imgAniversariante)
	{
		String logoUrl = saveLogo(logo, local);
		String logoCertificadoUrl = saveLogo(logoCertificado, local);
		String imgAniversarianteUrl = saveLogo(imgAniversariante, local);

		if(!logoUrl.equals(""))
			empresa.setLogoUrl(logoUrl);
		
		if(!logoCertificadoUrl.equals(""))
			empresa.setLogoCertificadoUrl(logoCertificadoUrl);

		if(!imgAniversarianteUrl.equals(""))
			empresa.setImgAniversarianteUrl(imgAniversarianteUrl);

		return empresa;
	}

	public boolean findIntegracaoAC(Long id)
	{
		return getDao().getIntegracaoAC(id);
	}

	public boolean findExibirSalarioById(Long empresaId)
	{
		return getDao().findExibirSalarioById(empresaId);
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

	public void sincronizaEntidades(Long empresaOrigemId, Long empresaDestinoId, String[] cadastrosCheck) throws Exception
	{
		Map<Long, Long> areaIds = new  HashMap<Long, Long>();
		Map<Long, Long> conhecimentoIds = new  HashMap<Long, Long>();
		Map<Long, Long> habilidadeIds = new  HashMap<Long, Long>();
		Map<Long, Long> atitudeIds = new  HashMap<Long, Long>();
		Map<Long, Long> areaInteresseIds = new  HashMap<Long, Long>();
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.AREAS))
		{
			areaOrganizacionalManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.CONHECIMENTOS))
		{
			conhecimentoManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, conhecimentoIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.HABILIDADES))
		{
			habilidadeManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, habilidadeIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.ATITUDES))
		{
			atitudeManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, atitudeIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.AREAS_INTERESSE))
		{
			areaInteresseManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, areaInteresseIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.CARGOS))
		{
			cargoManager = (CargoManager) SpringUtil.getBean("cargoManager");
			cargoManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, areaInteresseIds, conhecimentoIds, habilidadeIds, atitudeIds);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.TIPOS_OCORRENCIA))
		{
			ocorrenciaManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.EPIS))
		{
			epiManager = (EpiManager) SpringUtil.getBean("epiManager");
			epiManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.CURSOSETURMAS))
		{
			TurmaManager turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
			turmaManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
		
		if (ArrayUtils.contains(cadastrosCheck, TipoEntidade.MOTIVOS_DESLIGAMENTO))
		{
			motivoDemissaoManager.sincronizar(empresaOrigemId, empresaDestinoId);
		}
	}
	
	public Collection<Empresa> findEmailsEmpresa()
	{
		
		return getDao().findTodasEmpresas();
		
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
		if(empresa == null || empresa.getId() == null)
		{
			Collection<Empresa> empresas = findByUsuarioPermissao(usuarioId, role);
			
			CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
			return clu.convertCollectionToArrayIds(empresas);				
		}
		else
			return new Long[]{empresa.getId()};
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

	public void removeEmpresa(long id) 
	{
		getDao().removeEmpresaPadrao(id);
	}

	public Long ajustaCombo(Long empresaId, Long empresaSistemaId) 
	{
		if(empresaId == null)
			return empresaSistemaId;
		else if(empresaId == -1)
			return null;
		else
			return empresaId;
	}

	public void atualizaCamposExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras, Empresa empresa, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato) 
	{
		if(empresa.getId() == null || empresa.getId().equals(-1L))//quanto for para aplicar para todas as empresa
			configuracaoCampoExtraManager.removeAllNotModelo();
			
		for (ConfiguracaoCampoExtra campoExtra : configuracaoCampoExtras)
		{
			if(campoExtra.getEmpresa().getId() == null)
			{
				campoExtra.setEmpresa(empresa);
				campoExtra.setId(null);
			}
			
			if(empresa.getId() == null || empresa.getId().equals(-1L))
			{
				Collection<Empresa> empresas = getDao().findTodasEmpresas();
				for (Empresa empresaTmp : empresas) 
				{
					ConfiguracaoCampoExtra campoExtraTmp = new ConfiguracaoCampoExtra();
					campoExtraTmp = (ConfiguracaoCampoExtra) campoExtra.clone();
					campoExtraTmp.setEmpresa(empresaTmp);
					campoExtraTmp.setId(null);
					configuracaoCampoExtraManager.save(campoExtraTmp);
				}
			}
			else
				configuracaoCampoExtraManager.update(campoExtra);
		}

		getDao().updateCampoExtra(empresa.getId(), habilitaCampoExtraColaborador, habilitaCampoExtraCandidato);
	}
	
	public Collection<Empresa> findEmpresasPermitidas(Boolean compartilhar, Long empresId, Long usuarioId, String... roles) 
	{
		if(usuarioId.equals(1L))
			return getDao().findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"nome"}); 

		if (compartilhar)
			return getDao().findByUsuarioPermissao(usuarioId, roles);

		return getDao().findById(new Long[]{empresId});
	}

	public Collection<Empresa> findTodasEmpresas() {
		return getDao().findTodasEmpresas();
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
			Collection<Ocorrencia> ocorrencias = ocorrenciaManager.findSemCodigoAC(empresa.getId());
			Collection<Cidade> cidades = cidadeManager.findSemCodigoAC();
			
			if((colaboradors != null && !colaboradors.isEmpty()) || (estabelecimentos != null && !estabelecimentos.isEmpty()) || (areaOrganizacionals != null && !areaOrganizacionals.isEmpty())
					|| (faixaSalarials != null && !faixaSalarials.isEmpty()) || (indices != null && !indices.isEmpty()) || (ocorrencias != null && !ocorrencias.isEmpty())
					|| (cidades != null && !cidades.isEmpty()))
				return true;
			
			String colaboradorCodAcDuplicado = colaboradorManager.findCodigoACDuplicado(empresa.getId());
			String estabelecimentoCodAcDuplicado = estabelecimentoManager.findCodigoACDuplicado(empresa.getId());
			String areaCodAcDuplicado = areaOrganizacionalManager.findCodigoACDuplicado(empresa.getId());
			String faixaSalarialCodAcDuplicado = faixaSalarialManager.findCodigoACDuplicado(empresa.getId());
			String indiceCodAcDuplicado = indiceManager.findCodigoACDuplicado(empresa);
			String ocorrenciaCodAcDuplicado = ocorrenciaManager.findCodigoACDuplicado(empresa.getId());
			String cidadeCodAcDuplicado = cidadeManager.findCodigoACDuplicado();
				
			if( StringUtils.isNotEmpty(colaboradorCodAcDuplicado) || StringUtils.isNotEmpty(estabelecimentoCodAcDuplicado) ||  StringUtils.isNotEmpty(areaCodAcDuplicado)
					|| StringUtils.isNotEmpty(faixaSalarialCodAcDuplicado) || StringUtils.isNotEmpty(indiceCodAcDuplicado) || StringUtils.isNotEmpty(ocorrenciaCodAcDuplicado)
					|| StringUtils.isNotEmpty(cidadeCodAcDuplicado)) 
				return true;
		}	
		
		return false;
	}
	
	public Collection<String> verificaIntegracaoAC(Empresa empresa) 
	{
		Collection<String> msgs = new ArrayList<String>();
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		
		String colaboradorCodAcDuplicado = colaboradorManager.findCodigoACDuplicado(empresa.getId());
		String estabelecimentoCodAcDuplicado = estabelecimentoManager.findCodigoACDuplicado(empresa.getId());
		String areaCodAcDuplicado = areaOrganizacionalManager.findCodigoACDuplicado(empresa.getId());
		String faixaSalarialCodAcDuplicado = faixaSalarialManager.findCodigoACDuplicado(empresa.getId());
		String indiceCodAcDuplicado = indiceManager.findCodigoACDuplicado(empresa);
		String ocorrenciaCodAcDuplicado = ocorrenciaManager.findCodigoACDuplicado(empresa.getId());
		String cidadeCodAcDuplicado = cidadeManager.findCodigoACDuplicado();
		
		msgs.add("Verifique os seguintes itens:");

		if( StringUtils.isNotEmpty(colaboradorCodAcDuplicado) )
			msgs.add("- Existe colaborador duplicado, código AC: " + colaboradorCodAcDuplicado);

		if( StringUtils.isNotEmpty(estabelecimentoCodAcDuplicado) )
			msgs.add("- Existe estabelecimento duplicado, código AC: " + estabelecimentoCodAcDuplicado);

		if( StringUtils.isNotEmpty(areaCodAcDuplicado) )
			msgs.add("- Existe área organizacional duplicada, código AC: " + areaCodAcDuplicado);

		if( StringUtils.isNotEmpty(faixaSalarialCodAcDuplicado) )
			msgs.add("- Existe faixa salarial duplicada, código AC: " + faixaSalarialCodAcDuplicado);

		if( StringUtils.isNotEmpty(indiceCodAcDuplicado) )
			msgs.add("- Existe índice duplicado, código AC: " + indiceCodAcDuplicado);
		
		if( StringUtils.isNotEmpty(ocorrenciaCodAcDuplicado) )
			msgs.add("- Existe ocorrência duplicada, código AC: " + ocorrenciaCodAcDuplicado);

		if( StringUtils.isNotEmpty(cidadeCodAcDuplicado) )
			msgs.add("- Existe cidade duplicada, código AC: " + cidadeCodAcDuplicado);
		
		return msgs;
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

	public Collection<Empresa> findByCartaoAniversario() {
		return getDao().findByCartaoAniversario();
	}

	public boolean checkEmpresaIntegradaAc() {
		return getDao().checkEmpresaIntegradaAc();
	}

	public Collection<Empresa> findComCodigoAC() {
		return getDao().findComCodigoAC();
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

	public void setGastoManager(GastoManager gastoManager) {
		this.gastoManager = gastoManager;
	}
}