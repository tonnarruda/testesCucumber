package com.fortes.rh.business.ws;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.ws.UsuarioIntranet;
import com.fortes.rh.util.CollectionUtil;


public class RHServiceIntranetImpl implements RHServiceIntranet
{
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	public UsuarioIntranet getUsuario(Long colaboradorId)
	{
		UsuarioIntranet usuarioIntranet = null;
		
		Colaborador colaborador = colaboradorManager.findByIdDadosBasicos(colaboradorId, StatusRetornoAC.CONFIRMADO);
		if (colaborador != null)
		{
			usuarioIntranet = new UsuarioIntranet();
			usuarioIntranet.setCargoNome(colaborador.getHistoricoColaborador().getFaixaSalarial().getCargo().getNome());
			usuarioIntranet.setAreaNome(colaborador.getHistoricoColaborador().getAreaOrganizacional().getNome());
			usuarioIntranet.setDesligado(colaborador.isDesligado());
		}

		return usuarioIntranet;
	}
	
	public Map<Long, String> getListaSetor(Long[] empresaIds) 
	{
		Map<Long, String> areas = new HashMap<Long, String>();
		Collection<AreaOrganizacional> areasOrganizacionais = areaOrganizacionalManager.findByEmpresasIds(empresaIds, true);
		
		for (AreaOrganizacional areaOrganizacional : areasOrganizacionais) 
			areas.put(areaOrganizacional.getId(), areaOrganizacional.getEmpresa().getNome()+" - "+areaOrganizacional.getNome());			
		
		return areas;
	}
	
	public Long[] getUsuarioPorSetor(Long areaId)
	{
		Collection<Colaborador> colaboradors = colaboradorManager.findByAreasOrganizacionalIds(new Long[]{areaId});
		Long[] colaboradoresIds = new CollectionUtil<Colaborador>().convertCollectionToArrayIds(colaboradors);
		
		return colaboradoresIds;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) 
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) 
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}