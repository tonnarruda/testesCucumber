package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;

public class CidadeManagerImpl extends GenericManagerImpl<Cidade, CidadeDao> implements CidadeManager
{
	public Collection<Cidade> findAllSelect(Long estadoId)
	{
		return getDao().findAllSelect(estadoId);
	}

	public Collection<Cidade> findAllByUf(String uf)
	{
		return getDao().findAllByUf(uf);
	}

	public Cidade findByCodigoAC(String codigoAC, String sigla)
	{
		return getDao().findByCodigoAC(codigoAC, sigla);
	}

	public Cidade findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public Collection<Cidade> findByEstado(Estado estado)
	{
		if(estado != null && estado.getId() != null)
			return find(new String[]{"uf.id"}, new Object[]{estado.getId()}, new String[]{"nome"});
		
		return new ArrayList<Cidade>();
	}

	public Cidade findByNome(String nome, Long estadoId) 
	{
		return getDao().findByNome(nome, estadoId);
	}

	public Collection<Cidade> findSemCodigoAC() {
		return getDao().findSemCodigoAC();
	}

	public String findCodigoACDuplicado() {
		return getDao().findCodigoACDuplicado();
	}
}