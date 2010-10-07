package com.fortes.rh.business.sesmt;

import java.util.Collection;

import javax.persistence.PersistenceException;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.relatorio.ComissaoReuniaoPresencaMatriz;

public interface ComissaoReuniaoManager extends GenericManager<ComissaoReuniao>
{
	ComissaoReuniao findByIdProjection(Long id);
	Collection<ComissaoReuniao> findByComissao(Long comissaoId);
	Collection<ComissaoReuniao> findImprimirCalendario(Long comissaoId) throws ColecaoVaziaException;
	Collection<ComissaoMembro> findImprimirAta(ComissaoReuniao comissaoReuniao, Long comissaoId);
	Collection<ComissaoReuniaoPresencaMatriz> findRelatorioPresenca(Long comissaoId);
	void removeByComissao(Long id);
	ComissaoReuniao saveOrUpdate(ComissaoReuniao comissaoReuniao, String[] colaboradorChecks, String[] colaboradorIds, String[] justificativas) throws PersistenceException, Exception;
	void sugerirReuniao(Comissao comissao);
}