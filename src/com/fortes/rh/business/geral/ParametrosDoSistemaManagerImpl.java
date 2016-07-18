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

	public void verificaBancoConsistente() {
		boolean bancoConsistente = (getDao().getQuantidadeConstraintsDoBanco() == getDao().getQuantidadeConstraintsQueOBancoDeveriaTer());
		getDao().updateBancoConsistente(bancoConsistente);
	}

	//TODO sem test
	public void addCamposExtrasDoCamposVisivel(Collection<String> camposExtras, String camposVisivesisColaborador, String camposVisivesisCandidato) {
		ParametrosDoSistema parametrosDoSistema  = getDao().findById(1L);
		
		if(!"".equals(camposVisivesisColaborador) && !parametrosDoSistema.getCamposColaboradorTabs().contains("abaExtra"))
			parametrosDoSistema.setCamposColaboradorTabs(parametrosDoSistema.getCamposColaboradorTabs() + ",abaExtra");	
		
		if(!"".equals(camposVisivesisCandidato) && !parametrosDoSistema.getCamposCandidatoTabs().contains("abaExtra"))
			parametrosDoSistema.setCamposCandidatoTabs(parametrosDoSistema.getCamposCandidatoTabs() + ",abaExtra");	
		
		addCamposExtrasColaboradorVisivel(camposExtras,camposVisivesisColaborador, parametrosDoSistema);
		addCamposExtrasCandidatoIntenoExternoVisivel(camposExtras, camposVisivesisCandidato, parametrosDoSistema);
		
		update(parametrosDoSistema);
	}

	private void addCamposExtrasCandidatoIntenoExternoVisivel(Collection<String> camposExtras, String camposVisivesisCandidato,	ParametrosDoSistema parametrosDoSistema) {
		String newCamposCandidatoVisivel = ""; 
		String[] camposCandidatoVisivelArray = parametrosDoSistema.getCamposCandidatoVisivel().split(",");
		for (String campoCandVisivelExistente : camposCandidatoVisivelArray) {
			if(!camposExtras.contains(campoCandVisivelExistente.trim()))
				newCamposCandidatoVisivel += campoCandVisivelExistente + ",";
		}
		
		String newCamposCandidatoExternoVisivel = ""; 
		String[] camposCandidatoExternoVisivelArray = parametrosDoSistema.getCamposCandidatoExternoVisivel().split(",");
		for (String campoCandExternoVisivelExistente : camposCandidatoExternoVisivelArray) {
			if(!camposExtras.contains(campoCandExternoVisivelExistente.trim()))
				newCamposCandidatoExternoVisivel += campoCandExternoVisivelExistente + ",";
		}
		
		if(!"".equals(camposVisivesisCandidato)){
			newCamposCandidatoVisivel += camposVisivesisCandidato;
			newCamposCandidatoExternoVisivel += camposVisivesisCandidato;
		}
		
		if(!"".equals(newCamposCandidatoVisivel))
			parametrosDoSistema.setCamposCandidatoVisivel(newCamposCandidatoVisivel.substring(0, newCamposCandidatoVisivel.length() -1));
		
		if(!"".equals(newCamposCandidatoExternoVisivel))
			parametrosDoSistema.setCamposCandidatoExternoVisivel(newCamposCandidatoExternoVisivel.substring(0, newCamposCandidatoExternoVisivel.length() -1));
	}

	private void addCamposExtrasColaboradorVisivel(Collection<String> camposExtras, String camposVisivesisColaborador,
			ParametrosDoSistema parametrosDoSistema) {
		String newCamposColaboradorVisivel = ""; 
		String[] camposColaboradorVisivelArray = parametrosDoSistema.getCamposColaboradorVisivel().split(",");
		for (String campoColabVisivelExistente : camposColaboradorVisivelArray) {
			if(!camposExtras.contains(campoColabVisivelExistente.trim()))
				newCamposColaboradorVisivel += campoColabVisivelExistente + ",";
		}
		
		if(!"".equals(camposVisivesisColaborador))
			newCamposColaboradorVisivel += camposVisivesisColaborador;
		
		if(!"".equals(newCamposColaboradorVisivel))
			parametrosDoSistema.setCamposColaboradorVisivel(newCamposColaboradorVisivel.substring(0,newCamposColaboradorVisivel.length() -1));
	}
}
