package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class ExameManagerImpl extends GenericManagerImpl<Exame, ExameDao> implements ExameManager
{
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	Map<String,Object> parametros = new HashMap<String, Object>();
	
	@Autowired
	ExameManagerImpl(ExameDao exameDao) {
		setDao(exameDao);
	}
	
	public Exame findByIdProjection(Long exameId)
	{
		return getDao().findByIdProjection(exameId);
	}

	public Collection<Exame> findByEmpresaComAsoPadrao(Long empresaId)
	{
		return getDao().findByEmpresaComAsoPadrao(empresaId);
	}

	public Collection<Exame> findByAsoPadrao()
	{
		return getDao().findAsoPadrao();
	}

	public Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		return getDao().findByHistoricoFuncao(historicoFuncaoId);
	}

	public Collection<Exame> populaExames(String[] examesCheck)
	{
		Collection<Exame> exames = new ArrayList<Exame>();

		if(examesCheck != null && examesCheck.length > 0)
		{
			Long examesIds[] = LongUtil.arrayStringToArrayLong(examesCheck);

			Exame exame;
			for (Long exameId: examesIds)
			{
				exame = new Exame();
				exame.setId(exameId);

				exames.add(exame);
			}
		}

		return exames;
	}

	public String[] findBySolicitacaoExame(Long solicitacaoExameId)
	{
		Collection<Long> exameIds = getDao().findBySolicitacaoExame(solicitacaoExameId);
		String[] retorno = new String[exameIds.size()];

		int i=0;
		for (Long exameId : exameIds)
		{
			retorno[i++] = exameId.toString();
		}
		return retorno;
	}

	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<Exame> examesTmp = findByEmpresaComAsoPadrao(empresaId);
			return CheckListBoxUtil.populaCheckListBox(examesTmp, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public Collection<ExamesPrevistosRelatorio> findRelatorioExamesPrevistos(Long empresaId, Date dataInicio, Date dataFim, Long[] examesChecks, Long[] estabelecimentosChecks, Long[] areasChecks, Long[] colaboradoresChecks, char agruparPor, boolean imprimirAfastados, boolean imprimirDesligados, boolean exibirExamesNaoRealizados) throws Exception
	{
		Collection<ExamesPrevistosRelatorio> examesPrevistos = getDao().findExamesPeriodicosPrevistos(empresaId, dataInicio, dataFim, examesChecks, estabelecimentosChecks, areasChecks, colaboradoresChecks, imprimirAfastados, imprimirDesligados);

		if(exibirExamesNaoRealizados)
			examesPrevistos.addAll(getDao().findExamesPeriodicosPrevistosNaoRealizados(empresaId, dataInicio, dataFim, examesChecks, estabelecimentosChecks, areasChecks, colaboradoresChecks, imprimirAfastados, imprimirDesligados));
		
		if (examesPrevistos.isEmpty())
			return examesPrevistos;
		
		CollectionUtil<ExamesPrevistosRelatorio> collectionUtil = new CollectionUtil<ExamesPrevistosRelatorio>();
		
		examesPrevistos = collectionUtil.sortCollectionDate(examesPrevistos, "dataProximoExame", "asc");
		examesPrevistos = collectionUtil.sortCollectionStringIgnoreCase(examesPrevistos, "colaboradorNome");
		
		if (agruparPor == 'A'){
			examesPrevistos = collectionUtil.sortCollectionStringIgnoreCase(examesPrevistos, "areaOrganizacional.nome");			
		} else 	if (agruparPor == 'E')
			examesPrevistos = collectionUtil.sortCollectionStringIgnoreCase(examesPrevistos, "estabelecimento.nome");	
		
		return examesPrevistos;
	}

	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, Character tipoPessoa) throws ColecaoVaziaException
	{
		Collection<ExamesRealizadosRelatorio> examesRealizados = new ArrayList<ExamesRealizadosRelatorio>();
		
		if (!tipoPessoa.equals(TipoPessoa.CANDIDATO.getChave()))
			examesRealizados.addAll(getDao().findExamesRealizadosColaboradores(empresaId, nomeBusca, inicio, fim, motivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds));

		if (!tipoPessoa.equals(TipoPessoa.COLABORADOR.getChave()))
			examesRealizados.addAll(getDao().findExamesRealizadosCandidatos(empresaId, nomeBusca, inicio, fim, motivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds));
		
		if (examesRealizados == null || examesRealizados.isEmpty())
			throw new ColecaoVaziaException("Não existem exames realizados para os filtros informados.");
		
		CollectionUtil<ExamesRealizadosRelatorio> collectionUtil = new CollectionUtil<ExamesRealizadosRelatorio>();
		
		examesRealizados = collectionUtil.sortCollectionDate(examesRealizados, "data", "asc");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "nome");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "clinicaNome");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "estabelecimentoNome");
		
		return examesRealizados;
	}

	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizadosResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds, String resultadoExame) throws ColecaoVaziaException
	{
		Collection<ExamesRealizadosRelatorio> examesRealizados = getDao().findExamesRealizadosRelatorioResumido(empresaId, dataInicio, dataFim, clinicaAutorizada, examesIds, resultadoExame);
		
		if (examesRealizados == null || examesRealizados.isEmpty())
			throw new ColecaoVaziaException("Não existem exames realizados para os filtros informados.");
		
		return examesRealizados;
	}
	
	public void enviaLembreteExamesPrevistos(Collection<Empresa> empresas) 
	{
		gerenciadorComunicacaoManager.enviaLembreteExamesPrevistos(empresas);
	}
	
	public Collection<Exame> findPriorizandoExameRelacionado(Long empresaId, Long colaboradorId) {
		return getDao().findPriorizandoExameRelacionado(empresaId, colaboradorId);
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Integer count(Long empresaId, Exame exame) {
		return getDao().getCount(empresaId, exame);
	}

	public Collection<Exame> find(Integer page, Integer pagingSize, Long empresaId, Exame exame) 
	{
		return getDao().find(page, pagingSize, empresaId, exame);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
	
}