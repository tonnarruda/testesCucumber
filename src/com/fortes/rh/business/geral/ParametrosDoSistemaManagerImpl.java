package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClientSistema;

public class ParametrosDoSistemaManagerImpl extends GenericManagerImpl<ParametrosDoSistema, ParametrosDoSistemaDao> implements ParametrosDoSistemaManager
{
	private AcPessoalClientSistema acPessoalClientSistema;

	public Collection<Integer> getDiasLembretePesquisa()
	{
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) findAll().toArray()[0];
		return getIntervaloAviso(parametrosDoSistema.getDiasLembretePesquisa());
	}
	
	public Collection<Integer> getDiasLembretePeriodoExperiencia()
	{
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) findAll().toArray()[0];
		return getIntervaloAviso(parametrosDoSistema.getDiasLembretePeriodoExperiencia());
	}

	private Collection<Integer> getIntervaloAviso (String diasLembrete) {
		if (diasLembrete == null)
		{
			return null;
		}
		String[] dias = diasLembrete.split("&");
		Collection<Integer> result = new ArrayList<Integer>(dias.length);
		for (String diaLembrete : dias)
		{
			result.add(Integer.valueOf(diaLembrete.trim()));
		}
		return result;
	}

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

	public void setAcPessoalClientSistema(AcPessoalClientSistema acPessoalClientSistema)
	{
		this.acPessoalClientSistema = acPessoalClientSistema;
	}

	public boolean isACIntegrado(Empresa empresa) throws Exception
	{
		return acPessoalClientSistema.idACIntegrado(empresa);
	}

	public String findModulos(Long id)
	{
		return getDao().findModulos(id);
	}

	public void updateModulos(String papeis)
	{
		String papeisCodificados = StringUtil.encodeString(papeis);
		getDao().updateModulos(papeisCodificados);
	}

	public String[] getModulosDecodificados()
	{
		String permissoesDecodificadas = "";
		String modulos = this.findModulos(1L);

		if (StringUtils.isNotBlank(modulos))
		{
			permissoesDecodificadas = StringUtil.decodeString(modulos);
			return permissoesDecodificadas.split(",");			
		}

		return new String[]{};
	}

	public void disablePapeisIds() {
		getDao().disablePapeisIds();
	}

	public String getUrlDaAplicacao() {
		ParametrosDoSistema param = findById(1L);
		return param.getAppUrl();
	}
	public String getEmailDoSuporteTecnico() {
		ParametrosDoSistema param = findById(1L);
		return param.getEmailDoSuporteTecnico();
	}

	public void updateCampoExtra(boolean campoExtraColaborador) {
		getDao().updateCampoExtra(campoExtraColaborador);
		
	}
}
