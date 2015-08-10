package com.fortes.rh.business.ws;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.CargoIntranet;
import com.fortes.rh.model.ws.SetorIntranet;
import com.fortes.rh.model.ws.UnidadeIntranet;
import com.fortes.rh.model.ws.UsuarioIntranet;
import com.fortes.rh.util.DateUtil;


public class RHServiceIntranetImpl implements RHServiceIntranet
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CargoManager cargoManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;

	public Collection<SetorIntranet> setoresIntranetList(String empresaId) 
	{
		Collection<AreaOrganizacional> areasOrganizacionais = areaOrganizacionalManager.findByEmpresa(Long.valueOf(empresaId));
		Collection<SetorIntranet> setoresIntranet = new ArrayList<SetorIntranet>();
		
		SetorIntranet setorIntranet;
		for (AreaOrganizacional areaOrganizacional : areasOrganizacionais) 
		{
			setorIntranet = new SetorIntranet();
			setorIntranet.setId(areaOrganizacional.getId().toString());
			setorIntranet.setNome(areaOrganizacional.getNome());
			
			setoresIntranet.add(setorIntranet);
		}
		
		return setoresIntranet;
	}
	
	public Collection<CargoIntranet> cargosIntranetList(String empresaId) 
	{
		Collection<Cargo> cargos = cargoManager.findByEmpresa(Long.valueOf(empresaId));
		Collection<CargoIntranet> cargosIntranet = new ArrayList<CargoIntranet>();
		
		CargoIntranet cargoIntranet;
		for (Cargo cargo : cargos) 
		{
			cargoIntranet = new CargoIntranet();
			cargoIntranet.setId(cargo.getId().toString());
			cargoIntranet.setNome(cargo.getNome());
			
			cargosIntranet.add(cargoIntranet);
		}
		
		return cargosIntranet;
	}
	
	public Collection<UsuarioIntranet> usuariosIntranetList(String empresaId)
	{
		Collection<Colaborador> colaboradores = colaboradorManager.findByEmpresaAndStatusAC(Long.valueOf(empresaId), null, null, StatusRetornoAC.CONFIRMADO, false, false, true, "c.nome");
		Collection<UsuarioIntranet> usuarioIntranets = new ArrayList<UsuarioIntranet>();
		
		UsuarioIntranet usuarioIntranet;
		for (Colaborador colaborador : colaboradores) 
		{
			usuarioIntranet = new UsuarioIntranet();
			usuarioIntranet.setRhId(colaborador.getId().toString());
			usuarioIntranet.setDesligado(colaborador.isDesligado());
			usuarioIntranet.setNomeComercialParaRH(colaborador.getNome(), colaborador.getNomeComercial());
		    usuarioIntranet.setEmail(colaborador.getContato().getEmail());
		    usuarioIntranet.setCelular(colaborador.getContato().getFoneCelular());
		    usuarioIntranet.setCpf(colaborador.getPessoal().getCpf());
			usuarioIntranet.setDataNascimento(DateUtil.formataDiaMesAnoTime(colaborador.getPessoal().getDataNascimento()));
			usuarioIntranet.setAreaId(colaborador.getAreaOrganizacional().getId().toString());
			usuarioIntranet.setCargoId(colaborador.getFaixaSalarial().getCargo().getId().toString());
			usuarioIntranet.setEstabelecimentoId(colaborador.getEstabelecimento().getId().toString());
			
			usuarioIntranets.add(usuarioIntranet);
		}
		
		return usuarioIntranets;
	}
	
	public Collection<UnidadeIntranet> unidadesIntranetList(String empresaId)
	{
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findByEmpresa(Long.valueOf(empresaId));
		Collection<UnidadeIntranet> unidadesIntranet = new ArrayList<UnidadeIntranet>();
		
		UnidadeIntranet unidadeIntranet;
		for (Estabelecimento estabelecimento : estabelecimentos) 
		{
			unidadeIntranet = new UnidadeIntranet();
			unidadeIntranet.setId(estabelecimento.getId().toString());
			unidadeIntranet.setNome(estabelecimento.getNome());
			unidadeIntranet.setCep(estabelecimento.getEndereco().getCep());
			unidadeIntranet.setLogradouro(estabelecimento.getEndereco().getLogradouro());
			unidadeIntranet.setNumero(estabelecimento.getEndereco().getNumero());
			unidadeIntranet.setComplemento(estabelecimento.getEndereco().getComplemento());
			unidadeIntranet.setBairro(estabelecimento.getEndereco().getBairro());
			unidadeIntranet.setCidade(estabelecimento.getEndereco().getCidade().getNome());
			unidadeIntranet.setUf(estabelecimento.getEndereco().getUf().getSigla());
			
			unidadesIntranet.add(unidadeIntranet);
		}
		
		return unidadesIntranet;
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager) 
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setCargoManager(CargoManager cargoManager) 
	{
		this.cargoManager = cargoManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}
}