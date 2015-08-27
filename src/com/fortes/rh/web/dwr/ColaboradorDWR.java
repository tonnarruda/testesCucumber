package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.VerificacaoParentesco;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("unchecked")
public class ColaboradorDWR
{
    private ColaboradorManager colaboradorManager;
    private EmpresaManager empresaManager;
    private UsuarioManager usuarioManager;
    private ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager;
    private SolicitacaoManager solicitacaoManager;

	public Map<Long, String> getColaboradores(String[] areaOrganizacionalIds, Long empresaId)
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

        return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeMaisNomeComercial", Colaborador.class);
    }
    
    public Map<Long, String> getColaboradoresByAreaNome(String[] areaOrganizacionalIds, String nome, String matricula, Long empresaId)
    {
    	Collection<Colaborador> colaboradores;
    	Colaborador colaborador = null;
    	
    	if (StringUtils.isNotBlank(nome) || StringUtils.isNotBlank(matricula))
    	{
    		colaborador = new Colaborador();
    		
    		if (StringUtils.isNotBlank(nome))
    			colaborador.setNome(nome);
    		
    		if (StringUtils.isNotBlank(matricula))
    			colaborador.setMatricula(matricula);
    	}
    	
    	if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
    	{
    		Collection<Long> areasIds = LongUtil.arrayStringToCollectionLong(areaOrganizacionalIds);
    		colaboradores = colaboradorManager.findByAreaOrganizacionalIdsNome(areasIds, colaborador);
    	}
    	else
    	{
    		colaboradores = colaboradorManager.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador, empresaId, null);
    	}
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getMatriculaNomeMaisNomeComercial", Colaborador.class);
    }
    
    public Map<Long, String> getColaboradoresByAvaliacoes(Long[] avaliacaoIds)
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	
    	if(avaliacaoIds != null && avaliacaoIds.length > 0)
    		colaboradores = colaboradorManager.findByAvaliacoes(avaliacaoIds);
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeComercialDesligado", Colaborador.class);
    }

    public String updateConfiguracaoRelatorioDinamico(String campos, String titulo, Long usuarioId)
    {
    	try {
    		ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico = configuracaoRelatorioDinamicoManager.findByUsuario(usuarioId);

    		if(configuracaoRelatorioDinamico == null)
    		{
    			Usuario usuario = usuarioManager.findById(usuarioId);
    			configuracaoRelatorioDinamicoManager.save(new ConfiguracaoRelatorioDinamico(usuario, campos, titulo));

    		}else
    			configuracaoRelatorioDinamicoManager.update(campos, titulo, usuarioId);

    		return "OK";

    	} catch (Exception e) {
    		return "ERRO";
    	}
    }

    public Map<Long, String> getColaboradoresAreaEstabelecimento(String[] areaOrganizacionalIds, String[] estabelecimentoIds, Long empresaId)
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

    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeMaisNomeComercial", Colaborador.class);
    }

    public Map<Long, String> getColaboradoresByArea(String[] areaOrganizacionalIds, Long empresaId)
    {
        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

        if(areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
        	colaboradores = colaboradorManager.findByAreaOrganizacionalEstabelecimento(LongUtil.arrayStringToCollectionLong(areaOrganizacionalIds), null, SituacaoColaborador.ATIVO);        	
        else
            colaboradores = colaboradorManager.findAllSelect(empresaId, "nomeComercial");

        return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeMaisNomeComercial", Colaborador.class);
    }
    
    public Map<Long, String> getByAreaEstabelecimentoEmpresas(String[] areaOrganizacionalIds, String[] estabelecimentoIds, Long empresaId, Long[] empresaIds, String situacao, boolean exibirNomeEmpresa)
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	
    	if((areaOrganizacionalIds == null || areaOrganizacionalIds.length == 0) && (estabelecimentoIds == null || estabelecimentoIds.length == 0))
    	{
    		if(empresaId != null && empresaId != 0)
    			empresaIds = new Long[]{empresaId};
    			
    		colaboradores = colaboradorManager.findAllSelect(situacao, empresaIds);
    	}
    	else
    	{
    		colaboradores = colaboradorManager.findByAreaOrganizacionalEstabelecimento(LongUtil.arrayStringToCollectionLong(areaOrganizacionalIds), LongUtil.arrayStringToCollectionLong(estabelecimentoIds), situacao);        	
    	}
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", (exibirNomeEmpresa ? "getNomeComercialEmpresa" : "getNomeEOuNomeComercial"), Colaborador.class);
    }

    public Map<Long, String> getColaboradoresByEstabelecimentoDataAdmissao(Long estabelecimentoId, String dataAdmissao, Long empresaId)
    {
    	Date data = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			data = sdf.parse(dataAdmissao);
		} 
		catch (ParseException e) {
			return null;
		}
    	
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	
    	colaboradores.addAll(colaboradorManager.findByEstabelecimentoDataAdmissao(estabelecimentoId, data, empresaId));
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeMaisNomeComercial", Colaborador.class);
    }
    
    public Map<Long, String> getAvaliadores(Long avaliacaoDesempenhoId)
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(new Colaborador( -1L, "Selecione..."));
    	colaboradores.addAll(colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, false, null, null, null));
    	
    	return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeMaisNomeComercial", Colaborador.class);
    }

	public Map<Long, String> find(String nome, String cpf, String matricula, Long empresaId, boolean somenteAtivos, Long[] empresaIds)
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf(StringUtil.removeMascara(cpf));

		Colaborador colaborador = new Colaborador();
		colaborador.setNome(nome);
		colaborador.setMatricula(matricula);
		colaborador.setPessoal(pessoal);
		
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		if(empresaId == null || empresaId == 0 || empresaId == -1 )
			colaboradores = colaboradorManager.findByNomeCpfMatricula(colaborador, somenteAtivos, null, null, empresaIds);
		else
			colaboradores = colaboradorManager.findByNomeCpfMatricula(colaborador, somenteAtivos, null, null, empresaId);
		
		return new CollectionUtil<Colaborador>().convertCollectionToMap(colaboradores,"getId","getNomeCpfMatricula");
	}
	
	public Collection<Object> findByNome(String nome, Long empresaId, boolean incluirColaboradoresDesligados)
	{
		Collection<String> nomesColabJaSubstituidos = solicitacaoManager.getNomesColabSubstituidosSolicitacaoEncerrada(empresaId);
		Collection<Colaborador> colaboradores = colaboradorManager.findByNomeCpfMatricula(new Colaborador(nome), !incluirColaboradoresDesligados, StringUtil.converteCollectionToArrayString(nomesColabJaSubstituidos), null, empresaId);
		
		Collection<Object> retorno = new ArrayList<Object>();
		Map<String, String> colaboradorMap;
		
		for (Colaborador colaborador : colaboradores) 
		{
			colaboradorMap = new HashMap<String, String>();
			colaboradorMap.put("value", colaborador.getId().toString());
			colaboradorMap.put("label", colaborador.getNomeCpf());
			colaboradorMap.put("nome", colaborador.getNome());
			retorno.add(colaboradorMap);
		}
		
		return retorno;
	}
	
    public Map<Long, String> getByFuncaoAmbiente(Long funcaoId, Long ambienteId)
    {
        Collection<Colaborador> colaboradores = colaboradorManager.findByFuncaoAmbiente(funcaoId, ambienteId);

        return CollectionUtil.convertCollectionToMap(colaboradores, "getId", "getNomeMaisNomeComercial", Colaborador.class);
    }
    
    public Map<String, Object> findFuncaoAmbiente(Long colaboradorId)
    {
    	Colaborador colaborador = colaboradorManager.findFuncaoAmbiente(colaboradorId);
    	
    	Map<String, Object> dados = new HashMap<String, Object>();
    	dados.put("ambienteNome", "[Não cadastrado]");
    	dados.put("funcaoNome" ,  "[Não cadastrado]");
    	
    	if (colaborador.getAmbiente() != null)
    	{
    		dados.put("ambienteNome", colaborador.getAmbiente().getNome());
    		dados.put("ambienteId", colaborador.getAmbiente().getId());    		
    	}
    	
    	if(colaborador.getFuncao() != null)
    	{
    		dados.put("funcaoNome" ,  colaborador.getFuncao().getNome());
    		dados.put("funcaoId" ,  colaborador.getFuncao().getId());
    	}
    	
    	return dados;
    }
    
    public Collection<Object> findParentesByNome(Long colaboradorId, Long empresaId, String... nome)
    {
    	Empresa empresa = empresaManager.findById(empresaId);
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

    	if (empresa.getVerificaParentesco() == VerificacaoParentesco.BUSCA_TODAS_AS_EMPRESAS)
    		colaboradores = colaboradorManager.findParentesByNome(colaboradorId, null, nome);
    	else if (empresa.getVerificaParentesco() == VerificacaoParentesco.BUSCA_MESMA_EMPRESA)
    		colaboradores = colaboradorManager.findParentesByNome(colaboradorId, empresaId, nome);
    	else
    		return null;
    	
    	return colaboradorManager.montaParentesByNome(colaboradores);
    }
    
    public Boolean existeParentesByNome(Long colaboradorId, String nomePai, String nomeMae, String nomeConjuge, Long empresaId)
    {
    	Empresa empresa = empresaManager.findById(empresaId);

    	if (empresa.getVerificaParentesco() == VerificacaoParentesco.BUSCA_TODAS_AS_EMPRESAS)
    		return (colaboradorManager.findParentesByNome(colaboradorId, null, nomePai).size() > 0)  ||  (colaboradorManager.findParentesByNome(colaboradorId, null, nomeMae).size() > 0) || (colaboradorManager.findParentesByNome(colaboradorId, null, nomeConjuge).size() > 0);
    	else if (empresa.getVerificaParentesco() == VerificacaoParentesco.BUSCA_MESMA_EMPRESA)
    		return (colaboradorManager.findParentesByNome(colaboradorId, empresaId, nomePai).size() > 0)  ||  (colaboradorManager.findParentesByNome(colaboradorId, empresaId, nomeMae).size() > 0) || (colaboradorManager.findParentesByNome(colaboradorId, empresaId, nomeConjuge).size() > 0);
    	else
    		return false;
    }
    
    public Map<String, Object> verificaDesligadoByCandidato(Long candidatoId)
    {
    	Map<String, Object> dados = new HashMap<String, Object>();
    	dados.put("desligado", true);
    	dados.put("empresa", "");
    	
    	Colaborador colaborador = colaboradorManager.findByCandidato(candidatoId, null);
    	
    	if(colaborador != null)
    	{
    		dados.put("desligado", colaborador.isDesligado());
    		dados.put("empresa", colaborador.getEmpresaNome());
    	}
    	
    	return dados;
    }
    
    public Map getOcorrenciasByPeriodo(String dataIni, String dataFim, Long[] empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, int qtdItens){
    	Date dataInicio = null;
    	Date dataFinal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			dataInicio = sdf.parse(dataIni);
			dataFinal = sdf.parse(dataFim);
		} 
		catch (ParseException e) {
			return null;
		}
    	
		Collection<Long> empresas = new CollectionUtil<Long>().convertArrayToCollection(empresaIds);
		
		Collection<Ocorrencia> ocorrencias = colaboradorManager.getOcorrenciasByPeriodo(dataInicio, dataFinal, empresas, estabelecimentosIds, areasIds, cargosIds, qtdItens);
		
		return new CollectionUtil<Ocorrencia>().convertCollectionToMap(ocorrencias,"getId","getDescricaoComEmpresa");
	}
    
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setConfiguracaoRelatorioDinamicoManager(ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager) {
		this.configuracaoRelatorioDinamicoManager = configuracaoRelatorioDinamicoManager;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
}