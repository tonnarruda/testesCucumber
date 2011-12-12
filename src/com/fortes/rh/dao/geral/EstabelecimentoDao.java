package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Estabelecimento;

public interface EstabelecimentoDao extends GenericDao<Estabelecimento>
{
	boolean remove(String codigo, Long id);
	Estabelecimento findByCodigo(String codigo, String empCodigo, String grupoAC);
	Collection<Estabelecimento> findAllSelect(Long empresaId);
	Estabelecimento findEstabelecimentoCodigoAc(Long estabelecimentoId);
	boolean verificaCnpjExiste(String complemento, Long id, Long empresaId);
	Estabelecimento findEstabelecimentoByCodigoAc(String estabelecimentoCodigoAC, String empresaCodigoAC, String grupoAC);
	Collection<Estabelecimento> findAllSelect(Long[] empresaIds);
	public Collection<Estabelecimento> findEstabelecimentos(Long[] estabelecimentoIds);
	Collection<Estabelecimento> findSemCodigoAC(Long empresaId);
	String findCodigoACDuplicado(Long empresaId);
}