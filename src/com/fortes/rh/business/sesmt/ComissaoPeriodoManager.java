package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.security.spring.aop.callback.ComissaoPeriodoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Período da Comissão(Membros)")
public interface ComissaoPeriodoManager extends GenericManager<ComissaoPeriodo>
{
	void save(Long comissaoId, Long eleicaoId, Date aPartirDe);
	Collection<ComissaoPeriodo> findByComissao(Long comissaoId);
	ComissaoPeriodo findByIdProjection(Long id);
	ComissaoPeriodo clonar(Long id, Date aPartirDe) throws Exception;
	void removeByComissao(Long id);
	@Audita(operacao="Atualização", auditor=ComissaoPeriodoAuditorCallbackImpl.class)
	void atualiza(ComissaoPeriodo comissaoPeriodo, String[] comissaoMembroIds, String[] funcaoComissaos, String [] tipoComissaos) throws Exception;
	Date getDataFim(ComissaoPeriodo comissaoPeriodo);
	boolean validaDataComissaoPeriodo(Date data, Long comissaoPeriodoId);
	Collection<ComissaoMembro> findComissaoMembro(Long comissaoPeriodoId);
}