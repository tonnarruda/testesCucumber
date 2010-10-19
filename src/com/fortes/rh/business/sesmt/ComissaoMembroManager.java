package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

public interface ComissaoMembroManager extends GenericManager<ComissaoMembro>
{
	Collection<ComissaoMembro> findByComissaoPeriodo(Long[] comissaoPeriodoIds);
	Collection<ComissaoMembro> findByComissaoPeriodo(Long comissaoPeriodoId);
	void updateFuncaoETipo(String[] comissaoMembroIds, String[] funcaoComissaos, String[] tipoComissaos) throws Exception;
	void removeByComissaoPeriodo(Long[] comissaoPeriodoIds);
	void save(String[] colaboradorsCheck, ComissaoPeriodo comissaoPeriodo) throws Exception;
	Collection<Colaborador> findColaboradorByComissao(Long comissaoId);
	Collection<ComissaoMembro> findDistinctByComissaoPeriodo(Long comissaoPeriodoId);
	Collection<ComissaoMembro> findByComissao(Long comissaoId, String tipoMembroComissao);
	Collection<ComissaoMembro> findByColaborador(Long colaboradorId);
	Collection<Colaborador> findColaboradoresNaComissao(Long comissaoId,Collection<Long> colaboradorIds);
}