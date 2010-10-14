package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

public class ComissaoMembroManagerImpl extends GenericManagerImpl<ComissaoMembro, ComissaoMembroDao> implements ComissaoMembroManager
{
	public Collection<ComissaoMembro> findByComissaoPeriodo(Long[] comissaoPeriodoIds)
	{
		if (comissaoPeriodoIds == null || comissaoPeriodoIds.length == 0)
			return new ArrayList<ComissaoMembro>();

		return getDao().findByComissaoPeriodo(comissaoPeriodoIds);
	}

	public Collection<ComissaoMembro> findByComissaoPeriodo(Long comissaoPeriodoId)
	{
		return getDao().findByComissaoPeriodo(new Long[]{comissaoPeriodoId});
	}

	public void updateFuncaoETipo(String[] comissaoMembroIds, String[] funcaoComissaos, String[] tipoComissaos) throws Exception
	{
		if(comissaoMembroIds != null && comissaoMembroIds.length > 0)
		{
			Long[] ids = LongUtil.arrayStringToArrayLong(comissaoMembroIds);
			for (int i = 0; i < ids.length; i++)
			{
				getDao().updateFuncaoETipo(ids[i], funcaoComissaos[i], tipoComissaos[i]);
			}
		}
	}

	public void removeByComissaoPeriodo(Long[] comissaoPeriodoIds)
	{
		getDao().removeByComissaoPeriodo(comissaoPeriodoIds);
	}

	public void save(String[] colaboradorsCheck, ComissaoPeriodo comissaoPeriodo) throws Exception
	{
		CollectionUtil<Colaborador> util = new CollectionUtil<Colaborador>();
		Collection<Colaborador> colaboradores = util.convertArrayStringToCollection(Colaborador.class, colaboradorsCheck);
		Collection<ComissaoMembro> comissaoMembrosJaExistentes = findByComissaoPeriodo(comissaoPeriodo.getId());

		for (Colaborador colaborador : colaboradores)
		{
			ComissaoMembro comissaoMembro = new ComissaoMembro(colaborador, comissaoPeriodo);

			if (!comissaoMembrosJaExistentes.contains(comissaoMembro))
				save(comissaoMembro);
		}
	}

	public Collection<Colaborador> findColaboradorByComissao(Long comissaoId)
	{
		return getDao().findColaboradorByComissao(comissaoId);
	}

	public Collection<ComissaoMembro> findDistinctByComissaoPeriodo(Long comissaoPeriodoId)
	{
		return getDao().findDistinctByComissaoPeriodo(comissaoPeriodoId);
	}

	public Collection<ComissaoMembro> findByComissao(Long comissaoId, String tipoMembroComissao)
	{
		return getDao().findByComissao(comissaoId, tipoMembroComissao);
	}

	/**
	 * Participações de um colaborador como membro, em todas as comissões. */
	public Collection<ComissaoMembro> findByColaborador(Long colaboradorId)
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public Collection<Colaborador> findColaboradoresNaComissao(Long comissaoId,Collection<Long> colaboradorIds)
	{
		return getDao().findColaboradoresNaComissao(comissaoId, colaboradorIds);
	}
	
}