package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class FuncaoManagerImpl extends GenericManagerImpl<Funcao, FuncaoDao> implements FuncaoManager
{
	public Integer getCount(Long empresaId, String funcaoNome)
	{
		return getDao().getCount(empresaId, funcaoNome);
	}

	public Collection<Funcao> findByEmpresa(int page, int pagingSize, Long empresaId, String funcaoNome) throws ColecaoVaziaException
	{
		Collection<Funcao> funcoes = getDao().findByEmpresa(page, pagingSize, empresaId, funcaoNome);
		
		if(funcoes.isEmpty())
			throw new ColecaoVaziaException();
		
		return funcoes;
		
	}

	public Collection<Funcao> findByEmpresa(Long empresaId)
	{
		return getDao().findByEmpresa(empresaId);
	}

	public void removeFuncao(Funcao funcao)
	{
		RiscoFuncaoManager riscoFuncaoManager = (RiscoFuncaoManager) SpringUtil.getBean("riscoFuncaoManager");
		riscoFuncaoManager.removeByFuncao(funcao.getId());
		
		HistoricoFuncaoManager historicoFuncaoManager = (HistoricoFuncaoManager) SpringUtil.getBean("historicoFuncaoManager");
		Collection<HistoricoFuncao> historicoFuncaos = historicoFuncaoManager.findByFuncao(funcao.getId());
		for (HistoricoFuncao historicoFuncao : historicoFuncaos)
			historicoFuncaoManager.remove(historicoFuncao.getId());
		
		remove(funcao);
	}

	public Collection<Long> getIdsFuncoes(Collection<HistoricoColaborador> historicosColaborador)
	{
		Collection<Long> idFuncaos = new HashSet<Long>();

		for (HistoricoColaborador historicoColaborador : historicosColaborador)
		{
			if (historicoColaborador.getFuncao() != null && !idFuncaos.contains(historicoColaborador.getFuncao().getId()))
				idFuncaos.add(historicoColaborador.getFuncao().getId());
		}

		return idFuncaos;
	}

	public Funcao findByIdProjection(Long funcaoId)
	{
		return getDao().findByIdProjection(funcaoId);
	}

	public Collection<Funcao> findFuncoesDoAmbiente(Long ambienteId, Date data) 
	{
		return getDao().findFuncoesDoAmbiente(ambienteId, data);
	}
	
	public Collection<QtdPorFuncaoRelatorio> getQtdColaboradorByFuncao(Long empresaId, Long estabelecimentoId, Date data, char tipoAtivo) {
		
		Map<Long, QtdPorFuncaoRelatorio> mapQtdPorFuncaoRelatorio = new HashMap<Long, QtdPorFuncaoRelatorio>();  
		Collection<QtdPorFuncaoRelatorio> qtdPorFuncaoRelatorios = new ArrayList<QtdPorFuncaoRelatorio>();  
		Collection<Object[]> totalHomensMulheresPorFuncao = getDao().getQtdColaboradorByFuncao(empresaId, estabelecimentoId, data, tipoAtivo); 
		
		for (Object[] HomensMulheresDeUmaFuncao : totalHomensMulheresPorFuncao) {
			
			if(mapQtdPorFuncaoRelatorio.containsKey((Long)HomensMulheresDeUmaFuncao[0])){
				QtdPorFuncaoRelatorio qtdPorFuncaoRelatorioTmp = mapQtdPorFuncaoRelatorio.get((Long)HomensMulheresDeUmaFuncao[0]);
				qtdPorFuncaoRelatorioTmp.setQtdHomesAndMulheres((Integer) HomensMulheresDeUmaFuncao[2], (Integer) HomensMulheresDeUmaFuncao[3]);
			} else {
				QtdPorFuncaoRelatorio qtdPorFuncaoRelatorioTmp = new QtdPorFuncaoRelatorio((Long)HomensMulheresDeUmaFuncao[0], (String)HomensMulheresDeUmaFuncao[1], (Integer) HomensMulheresDeUmaFuncao[2], (Integer) HomensMulheresDeUmaFuncao[3]);
				mapQtdPorFuncaoRelatorio.put(qtdPorFuncaoRelatorioTmp.getFuncaoId(), qtdPorFuncaoRelatorioTmp);
				qtdPorFuncaoRelatorios.add(qtdPorFuncaoRelatorioTmp);
			}
		}
		
		return qtdPorFuncaoRelatorios;
	}
	
	public Collection<CheckBox> populaCheckBox() {
		try
		{
			Collection<Funcao> funcoesTmp = getDao().findAll();
			return CheckListBoxUtil.populaCheckListBox(funcoesTmp, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public void atualizaNomeUltimoHistorico(Long funcaoId) {
		getDao().atualizaNomeUltimoHistorico(funcaoId);
	}

	public Collection<Long> findFuncaoAtualDosColaboradores(Date data, Long estabelecimentoId)
	{
		return getDao().findFuncaoAtualDosColaboradores(data, estabelecimentoId);
	}

	public Collection<String> findColaboradoresSemFuncao(Date data, Long estabelecimentoId)
	{
		return getDao().findColaboradoresSemFuncao(data, estabelecimentoId);
	}
}