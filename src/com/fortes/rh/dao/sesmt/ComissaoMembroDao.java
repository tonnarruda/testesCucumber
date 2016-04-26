package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;

public interface ComissaoMembroDao extends GenericDao<ComissaoMembro>
{
	Collection<ComissaoMembro> findByComissaoPeriodo(Long[] comissaoPeriodoIds);
	void updateFuncaoETipo(Long id, String funcao, String tipo);
	void removeByComissaoPeriodo(Long[] comissaoPeriodoIds);
	Collection<Colaborador> findColaboradorByComissao(Long comissaoId);
	Collection<ComissaoMembro> findDistinctByComissaoPeriodo(Long comissaoPeriodoId, Date dataLimiteParaDesligados);
	Collection<ComissaoMembro> findByComissao(Long comissaoId, String tipoMembroComissao);
	Collection<ComissaoMembro> findByColaborador(Long colaboradorId);
	Collection<Colaborador> findColaboradoresNaComissao(Long comissaoId);
	Collection<Comissao> findComissaoByColaborador(Long colaboradorId);
	public Map<Long, Date> colaboradoresComEstabilidade(Long[] colaboradoresIds);
}