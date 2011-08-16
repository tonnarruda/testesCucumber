package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("unchecked")
public class ColaboradorDWR
{
    private ColaboradorManager colaboradorManager;
//    private HistoricoColaboradorManager historicoColaboradorManager;

	public Map getColaboradores(String[] areaOrganizacionalIds, Long empresaId)
    {
        Collection<Colaborador> colaboradores;
        if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
        {
            Long [] idsLong = StringUtil.stringToLong(areaOrganizacionalIds);
            colaboradores = colaboradorManager.findByAreasOrganizacionalIds(idsLong);
        }
        else
        {
            String[] properties = new String[]{"id","nome","nomeComercial"};
            String[] sets = new String[]{"id","nome","nomeComercial"};

            colaboradores = colaboradorManager.findToList(properties, sets, new String[]{"desligado","empresa.id"}, new Object[]{false,empresaId}, new String[]{"nomeComercial"});
        }

        return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeComercial", Colaborador.class);
    }
    
    public Map getColaboradoresByAreaNome(String[] areaOrganizacionalIds, String nome, Long empresaId)
    {
    	Collection<Colaborador> colaboradores;
    	
    	Colaborador colaborador = null;
    	if(StringUtils.isNotBlank(nome))
    		colaborador = new Colaborador(nome, null, null, null);
    	
    	if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
    	{
    		Collection<Long> areasIds = LongUtil.arrayStringToCollectionLong(areaOrganizacionalIds);
    		colaboradores = colaboradorManager.findByAreaOrganizacionalIdsNome(areasIds, colaborador);
    	}
    	else
    	{
    		colaboradores = colaboradorManager.findByNomeCpfMatricula(colaborador, empresaId, true);
    	}
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNome", Colaborador.class);
    }
    
    public Map getColaboradoresByAvaliacao(Long avaliacaoId)
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	
    	if(avaliacaoId != null)
    		colaboradores = colaboradorManager.findByAvaliacao(avaliacaoId);
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeDesligado", Colaborador.class);
    }

    public Map getColaboradoresAreaEstabelecimento(String[] areaOrganizacionalIds, String[] estabelecimentoIds, Long empresaId)
    {
    	Collection<Colaborador> colaboradores;

    	if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
    	{
    		if (estabelecimentoIds == null || estabelecimentoIds.length == 0)
    		{
    			Long [] idsLong = StringUtil.stringToLong(areaOrganizacionalIds);
    			colaboradores = colaboradorManager.findByAreasOrganizacionalIds(idsLong);
    		}
    		else
    		{
    			Long [] areasId = StringUtil.stringToLong(areaOrganizacionalIds);
    			Long [] estabelecimentosId = StringUtil.stringToLong(estabelecimentoIds);

    			CollectionUtil<Long> cu = new CollectionUtil<Long>();

    			Collection<Long> areas = cu.convertArrayToCollection(areasId);
    			Collection<Long> estabelecimentos = cu.convertArrayToCollection(estabelecimentosId);

    			colaboradores = colaboradorManager.findByAreasOrganizacionaisEstabelecimentos(areas, estabelecimentos);
    		}
    	}
    	else
    	{
			Long [] estabelecimentosId = StringUtil.stringToLong(estabelecimentoIds);
			colaboradores = colaboradorManager.findByEstabelecimento(estabelecimentosId);
    	}

    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeComercial", Colaborador.class);
    }

    public Map getColaboradoresByArea(String[] areaOrganizacionalIds, Long empresaId)
    {
        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

        if(areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
        	colaboradores = colaboradorManager.findByAreaOrganizacional(LongUtil.arrayStringToCollectionLong(areaOrganizacionalIds));        	
        else
            colaboradores = colaboradorManager.findAllSelect(empresaId, "nomeComercial");

        return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeComercial", Colaborador.class);
    }
    
    public Map getColaboradoresByAreaEmpresas(String[] areaOrganizacionalIds, Long empresaId, Long[] empresaIds)
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	
    	if(areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
    		colaboradores = colaboradorManager.findByAreaOrganizacional(LongUtil.arrayStringToCollectionLong(areaOrganizacionalIds));        	
    	else
    	{
    		if(empresaId != null && empresaId != 0)
    			empresaIds = new Long[]{empresaId};
    			
    		colaboradores = colaboradorManager.findAllSelect(empresaIds);
    	}
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeComercialEmpresa", Colaborador.class);
    }
    
    public Map getAvaliadores(Long avaliacaoDesempenhoId)
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(new Colaborador("Selecione...", -1L, null, null));
    	colaboradores.addAll(colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, false));
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNome", Colaborador.class);
    }

	public Map find(String nome, String cpf, String matricula, Long empresaId, boolean somenteAtivos)
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf(StringUtil.removeMascara(cpf));

		Colaborador colaborador = new Colaborador();
		colaborador.setNome(nome);
		colaborador.setMatricula(matricula);
		colaborador.setPessoal(pessoal);
		
		Collection<Colaborador> colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, empresaId, somenteAtivos);
		
		return new CollectionUtil<Colaborador>().convertCollectionToMap(colaboradors,"getId","getNomeCpfMatricula");
	}
	
    public Map getByFuncaoAmbiente(Long funcaoId, Long ambienteId)
    {
        Collection<Colaborador> colaboradores = colaboradorManager.findByFuncaoAmbiente(funcaoId, ambienteId);

        return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeComercial", Colaborador.class);
    }

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
}