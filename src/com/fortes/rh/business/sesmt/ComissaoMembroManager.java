package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

public interface ComissaoMembroManager extends GenericManager<ComissaoMembro>
{
	Collection<ComissaoMembro> findByComissaoPeriodo(Long[] comissaoPeriodoIds);
	Collection<ComissaoMembro> findByComissaoPeriodo(ComissaoPeriodo comissaoPeriodo);
	void updateFuncaoETipo(String[] comissaoMembroIds, String[] funcaoComissaos, String[] tipoComissaos) throws Exception;
	void removeByComissaoPeriodo(Long[] comissaoPeriodoIds);
	void save(String[] colaboradorsCheck, ComissaoPeriodo comissaoPeriodo) throws Exception;
	Collection<Colaborador> findColaboradorByComissao(Long comissaoId);
	Collection<ComissaoMembro> findDistinctByComissaoPeriodo(Long comissaoPeriodoId, Date dataLimiteParaDesligados);
	Collection<ComissaoMembro> findByComissao(Long comissaoId, String tipoMembroComissao);
	Collection<ComissaoMembro> findByColaborador(Long colaboradorId);
	Collection<Colaborador> findColaboradoresNaComissao(Long comissaoId);
	public Collection<Comissao> findComissaoByColaborador(Long colaboradorId);
	Map<Long, Date> colaboradoresComEstabilidade(Long[] colaboradoresIds);
}