package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Funcao;

public interface FuncaoDao extends GenericDao<Funcao>
{
	Integer getCount(Long empresaId, String funcaoNome);
	Collection<Funcao> findByEmpresa(int page, int pagingSize, Long empresaId, String funcaoNome);
	Collection<Funcao> findByEmpresa(Long empresaId);
	Funcao findByIdProjection(Long funcaoId);
	Collection<Funcao> findFuncoesDoAmbiente(Long ambienteId, Date data);
	Collection<Long> findFuncaoAtualDosColaboradores(Date data, Long estabelecimentoId);
	Collection<String> findColaboradoresSemFuncao(Date data, Long estabelecimentoId);
	Collection<Object[]> getQtdColaboradorByFuncao(Long empresaId, Long estabelecimentoId, Date data, char tipoAtivo);
}