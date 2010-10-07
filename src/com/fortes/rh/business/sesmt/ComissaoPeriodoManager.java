package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

public interface ComissaoPeriodoManager extends GenericManager<ComissaoPeriodo>
{
	void save(Long comissaoId, Long eleicaoId, Date aPartirDe);
	Collection<ComissaoPeriodo> findByComissao(Long comissaoId);
	ComissaoPeriodo findByIdProjection(Long id);
	void clonar(Long id) throws Exception;
	void removeByComissao(Long id);
	void update(ComissaoPeriodo comissaoPeriodo, String[] comissaoMembroIds, String[] funcaoComissaos, String [] tipoComissaos) throws Exception;
	Date getDataFim(ComissaoPeriodo comissaoPeriodo);
	boolean validaDataComissaoPeriodo(Date data, Long comissaoPeriodoId);
}