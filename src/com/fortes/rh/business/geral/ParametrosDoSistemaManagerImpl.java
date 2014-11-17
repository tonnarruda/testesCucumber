package com.fortes.rh.business.geral;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.web.ws.AcPessoalClientSistema;

public class ParametrosDoSistemaManagerImpl extends GenericManagerImpl<ParametrosDoSistema, ParametrosDoSistemaDao> implements ParametrosDoSistemaManager
{
	private AcPessoalClientSistema acPessoalClientSistema;
	
	public ParametrosDoSistema findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public boolean getSistemaAtualizado()
	{
		ParametrosDoSistema parametrosDoSistema = getDao().findByIdProjection(1L);

		return parametrosDoSistema.getAtualizadoSucesso();
	}

	public boolean verificaCompatibilidadeComWebServiceAC(String versaoWebServiceAC, String versaoMinimaWebServicxeCompativel)
	{
		long acVersao = 0;
		if(versaoWebServiceAC != null && !versaoWebServiceAC.replace(".", "").equals(""))
			acVersao = Long.parseLong(versaoWebServiceAC.replace(".", ""));

		long acVersaoCompativel = 0;
		if(versaoMinimaWebServicxeCompativel != null && !versaoMinimaWebServicxeCompativel.replace(".", "").equals(""))
			acVersaoCompativel = Long.parseLong(versaoMinimaWebServicxeCompativel.replace(".", ""));

		if(acVersao == 0 || acVersaoCompativel == 0 || acVersao != acVersaoCompativel)
			return false;

		return true;
	}

	public String getVersaoWebServiceAC(Empresa empresa) throws Exception
	{
		return acPessoalClientSistema.getVersaoWebServiceAC(empresa);
	}
	
	public String getContexto()
	{
		return getDao().getContexto();
	}

	public void setAcPessoalClientSistema(AcPessoalClientSistema acPessoalClientSistema)
	{
		this.acPessoalClientSistema = acPessoalClientSistema;
	}

	public boolean isACIntegrado(Empresa empresa) throws Exception
	{
		return acPessoalClientSistema.idACIntegrado(empresa);
	}

	public String getUrlDaAplicacao() {
		ParametrosDoSistema param = findById(1L);
		return param.getAppUrl();
	}
	public String getEmailDoSuporteTecnico() {
		ParametrosDoSistema param = findById(1L);
		return param.getEmailDoSuporteTecnico();
	}

	public Boolean isIdiomaCorreto() 
	{
		Locale pt_BR = new Locale("pt", "BR");
		return pt_BR.equals(Locale.getDefault());
	}

	public void ajustaCamposExtras(ParametrosDoSistema parametrosDoSistema, String[] camposExtras) 
	{
		Collection<String> visiveis = CollectionUtils.subtract(Arrays.asList(parametrosDoSistema.getCamposCandidatoVisivel().split(",")) , Arrays.asList(camposExtras));
		parametrosDoSistema.setCamposCandidatoVisivel(StringUtils.join(visiveis.iterator(), ","));

		Collection<String> obrigatorios = CollectionUtils.subtract(Arrays.asList(parametrosDoSistema.getCamposCandidatoObrigatorio().split(",")) , Arrays.asList(camposExtras));
		parametrosDoSistema.setCamposCandidatoObrigatorio(StringUtils.join(obrigatorios.iterator(), ","));
	}

	public void updateServidorRemprot(String servidorRemprot) {
		getDao().updateServidorRemprot(servidorRemprot);
	}

	public boolean removeDadosPortalColaborador() 
	{
		return removeDadosPortalColaborador();
	}
}
