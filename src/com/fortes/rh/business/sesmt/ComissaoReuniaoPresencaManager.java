package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;

public interface ComissaoReuniaoPresencaManager extends GenericManager<ComissaoReuniaoPresenca>
{
	void saveOrUpdateByReuniao(Long comissaoReuniaoId, Long comissaoId, String[] colaboradorChecks, String[] colaboradorIds, String[] justificativas) throws Exception;
	Collection<ComissaoReuniaoPresenca> findByReuniao(Long comissaoReuniaoId);
	void removeByReuniao(Long comissaoReuniaoId);
	Collection<ComissaoReuniaoPresenca> findByComissao(Long comissaoId, boolean ordenarPorDataNome);
	boolean existeReuniaoPresenca(Long comissaoId, Collection<Long> colaboradorIds);
	Collection<ComissaoReuniaoPresenca> findPresencasByComissao(Long comissaoId);
	List<ComissaoReuniaoPresenca> findPresencaColaboradoresByReuniao(Long comissaoReuniaoId, Date dataReuniao);
}