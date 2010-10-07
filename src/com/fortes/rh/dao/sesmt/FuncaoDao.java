package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Funcao;

public interface FuncaoDao extends GenericDao<Funcao>
{
	Integer getCount(Long cargoId);
	Collection<Funcao> findByCargo(int page, int pagingSize, Long cargoId);
	Collection<Funcao> findByEmpresa(Long empresaId);
	Collection<Funcao> findFuncaoByFaixa(Long faixaId);
	Funcao findByIdProjection(Long funcaoId);
	Collection<Funcao> findFuncoesDoAmbiente(Long ambienteId, Date data);
	Collection<Long> findFuncaoAtualDosColaboradores(Date data, Long estabelecimentoId);
	Collection<String> findColaboradoresSemFuncao(Date data, Long estabelecimentoId);
	int getQtdColaboradorByFuncao(Long funcaoId, Long estabelecimentoId, Date data, String sexo);
}