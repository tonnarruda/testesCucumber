package com.fortes.rh.business.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.ws.UsuarioIntranet;


public class RHServiceIntranetImpl implements RHServiceIntranet
{
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	public Boolean usuarioIsDesligado(Long colaboradorId)
	{
		Colaborador colaborador = colaboradorManager.findByIdDadosBasicos(colaboradorId, StatusRetornoAC.CONFIRMADO);
		if (colaborador != null)
			return colaborador.isDesligado();

		return null;
	}
	
	public Map<Long, String> getListaSetor(Long[] empresaIds) 
	{
		Map<Long, String> areas = new HashMap<Long, String>();
		Collection<AreaOrganizacional> areasOrganizacionais = areaOrganizacionalManager.findByEmpresasIds(empresaIds, true);
		
		for (AreaOrganizacional areaOrganizacional : areasOrganizacionais) 
			areas.put(areaOrganizacional.getId(), areaOrganizacional.getEmpresa().getNome()+" - "+areaOrganizacional.getNome());			
		
		return areas;
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager) 
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) 
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<UsuarioIntranet> atualizaUsuarios(Long empresaId)
	{
		Collection<Colaborador> colaboradores = colaboradorManager.findByEmpresa(empresaId);
		
		Collection<UsuarioIntranet> usuarioIntranets = new ArrayList<UsuarioIntranet>();
		
		for (Colaborador colaborador : colaboradores) {
			UsuarioIntranet usuarioIntranet = new UsuarioIntranet();
			
			usuarioIntranet.setDesligado(colaborador.isDesligado());
			usuarioIntranet.setNomeComercialParaRH(colaborador.getNome(), colaborador.getNomeComercial());
			usuarioIntranet.setEmail(colaborador.getContato().getEmail());
			usuarioIntranet.setCelular(colaborador.getContato().getFoneCelular());
			usuarioIntranet.setDataNascimento(colaborador.getPessoal().getDataNascimento());
			usuarioIntranet.setAreaId(colaborador.getAreaOrganizacional().getId());
			usuarioIntranet.setAreaNome(colaborador.getAreaOrganizacional().getNome());
			usuarioIntranet.setCargoId(colaborador.getFaixaSalarial().getCargo().getId());
			usuarioIntranet.setAreaNome(colaborador.getFaixaSalarial().getCargo().getNome());
			
			usuarioIntranets.add(usuarioIntranet);
		}
		
		return usuarioIntranets;
	}
}