package com.fortes.rh.business.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.ws.UsuarioIntranet;
import com.fortes.rh.util.DateUtil;


public class RHServiceIntranetImpl implements RHServiceIntranet
{
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	public Boolean usuarioIsDesligado(String colaboradorId)
	{
		Colaborador colaborador = colaboradorManager.findByIdDadosBasicos(Long.valueOf(colaboradorId), StatusRetornoAC.CONFIRMADO);
		if (colaborador != null)
			return colaborador.isDesligado();

		return null;
	}
	
	public Map<String, String> getListaSetor(String[] empresaIds) 
	{
		Map<String, String> areas = new HashMap<String, String>();
		Long[] empresaIdsLong = new Long[empresaIds.length];
		
		for (int i = 0; i < empresaIdsLong.length; i++) 
			empresaIdsLong[i] = Long.valueOf(empresaIds[i]);
		
		Collection<AreaOrganizacional> areasOrganizacionais = areaOrganizacionalManager.findByEmpresasIds(empresaIdsLong, true);
		
		for (AreaOrganizacional areaOrganizacional : areasOrganizacionais) 
			areas.put(areaOrganizacional.getId().toString(), areaOrganizacional.getEmpresa().getNome()+" - "+areaOrganizacional.getNome());			
		
		return areas;
	}
	
	public Collection<UsuarioIntranet> atualizaUsuarios(String empresaId)
	{
		Collection<Colaborador> colaboradores = colaboradorManager.findByEmpresa(Long.valueOf(empresaId));
		Collection<UsuarioIntranet> usuarioIntranets = new ArrayList<UsuarioIntranet>();
		
		for (Colaborador colaborador : colaboradores) 
		{
			UsuarioIntranet usuarioIntranet = new UsuarioIntranet();
			usuarioIntranet.setRhId(colaborador.getId().toString());
			usuarioIntranet.setDesligado(colaborador.isDesligado());
			usuarioIntranet.setNomeComercialParaRH(colaborador.getNome(), colaborador.getNomeComercial());
		    usuarioIntranet.setEmail(colaborador.getContato().getEmail());
		    usuarioIntranet.setCelular(colaborador.getContato().getFoneCelular());
			usuarioIntranet.setDataNascimento(DateUtil.formataDiaMesAnoTime(colaborador.getPessoal().getDataNascimento()));
			usuarioIntranet.setAreaId(colaborador.getAreaOrganizacional().getId().toString());
			usuarioIntranet.setAreaNome(colaborador.getAreaOrganizacional().getNome());
			usuarioIntranet.setCargoId(colaborador.getFaixaSalarial().getCargo().getId().toString());
			usuarioIntranet.setCargoNome(colaborador.getFaixaSalarial().getCargo().getNome());
			
			usuarioIntranets.add(usuarioIntranet);
		}
		
		return usuarioIntranets;
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