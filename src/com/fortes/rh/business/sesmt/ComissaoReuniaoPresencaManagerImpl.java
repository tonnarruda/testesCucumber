package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.util.CollectionUtil;

@Component
public class ComissaoReuniaoPresencaManagerImpl extends GenericManagerImpl<ComissaoReuniaoPresenca, ComissaoReuniaoPresencaDao> implements ComissaoReuniaoPresencaManager
{
	@Autowired
	ComissaoReuniaoPresencaManagerImpl(ComissaoReuniaoPresencaDao fooDao) {
		setDao(fooDao);
	}
	
	public void saveOrUpdateByReuniao(Long comissaoReuniaoId, Long comissaoId, String[] colaboradorChecks, String[] colaboradorIds, String[] justificativas) throws Exception
	{
		Collection<ComissaoReuniaoPresenca> presencasJaRegistradas = getDao().findByReuniao(comissaoReuniaoId);
		Collection<ComissaoReuniaoPresenca> presencasARegistrar = new ArrayList<ComissaoReuniaoPresenca>();

		CollectionUtil<Colaborador> util = new CollectionUtil<Colaborador>();
		// Apenas os marcados
		Collection<Colaborador> colaboradoresPresentes = util.convertArrayStringToCollection(Colaborador.class, colaboradorChecks);
		// Todos os hidden
		Collection<Colaborador> colaboradoresTotal = util.convertArrayStringToCollection(Colaborador.class, colaboradorIds);

		int i=0;
		for (Colaborador colaborador : colaboradoresTotal)
		{
			ComissaoReuniaoPresenca comissaoReuniaoPresenca = new ComissaoReuniaoPresenca(colaborador, comissaoReuniaoId);

			if (colaboradoresPresentes.contains(colaborador))
				comissaoReuniaoPresenca.setPresente(true);
			else{
				if (justificativas != null && justificativas.length > i) {
					comissaoReuniaoPresenca.setJustificativaFalta(justificativas[i]);
				}
			}

			presencasARegistrar.add(comissaoReuniaoPresenca);
			i++;
		}

		// Nenhuma presença foi registrada, salva todos
		if (presencasJaRegistradas == null || presencasJaRegistradas.isEmpty())
		{
			getDao().saveOrUpdate(presencasARegistrar);
		}
		else
		{
			for (ComissaoReuniaoPresenca comissaoReuniaoPresenca : presencasARegistrar)
			{
				// É salva a nova presença
				if (!presencasJaRegistradas.contains(comissaoReuniaoPresenca))
				{
					getDao().save(comissaoReuniaoPresenca);
				}
				// No caso de um colaborador com presença já registrada, faz update
				else
				{
					for (ComissaoReuniaoPresenca comissaoReuniaoPresenca2 : presencasJaRegistradas)
					{
						if (comissaoReuniaoPresenca2.getColaborador().equals(comissaoReuniaoPresenca.getColaborador()))
						{
							comissaoReuniaoPresenca.setId(comissaoReuniaoPresenca2.getId());
							getDao().update(comissaoReuniaoPresenca);
						}
					}
				}
			}
		}
	}

	@TesteAutomatico
	public Collection<ComissaoReuniaoPresenca> findByReuniao(Long comissaoReuniaoId)
	{
		return getDao().findByReuniao(comissaoReuniaoId);
	}

	@TesteAutomatico
	public void removeByReuniao(Long comissaoReuniaoId)
	{
		getDao().removeByReuniao(comissaoReuniaoId);
	}

	@TesteAutomatico
	public Collection<ComissaoReuniaoPresenca> findByComissao(Long comissaoId, boolean ordenarPorDataNome)
	{
		return getDao().findByComissao(comissaoId, ordenarPorDataNome);
	}

	@TesteAutomatico
	public boolean existeReuniaoPresenca(Long comissaoId,	Collection<Long> colaboradorIds) 
	{
		return getDao().existeReuniaoPresenca(comissaoId, colaboradorIds);
	}

	@TesteAutomatico
	public Collection<ComissaoReuniaoPresenca> findPresencasByComissao(Long comissaoId) 
	{
		return getDao().findPresencasByComissao(comissaoId);
	}

	@TesteAutomatico
	public List<ComissaoReuniaoPresenca> findPresencaColaboradoresByReuniao(Long comissaoReuniaoId, Date dataReuniao) 
	{
		return getDao().findPresencaColaboradoresByReuniao(comissaoReuniaoId, dataReuniao);
	}
}