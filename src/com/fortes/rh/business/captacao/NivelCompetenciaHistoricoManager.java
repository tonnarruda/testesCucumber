package com.fortes.rh.business.captacao;

import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.security.spring.aop.callback.NivelCompetenciaHistoricoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface NivelCompetenciaHistoricoManager extends GenericManager<NivelCompetenciaHistorico>
{
	@Audita(operacao="Remoção", auditor=NivelCompetenciaHistoricoAuditorCallbackImpl.class)
	public void removeNivelConfiguracaoHistorico(Long id) throws Exception;
	public Long findByData(Date date, Long empresaId);
	@Audita(operacao="Atualização", auditor=NivelCompetenciaHistoricoAuditorCallbackImpl.class)
	public void updateNivelConfiguracaoHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico);
	@Audita(operacao="Inserção", auditor=NivelCompetenciaHistoricoAuditorCallbackImpl.class)
	public NivelCompetenciaHistorico save(NivelCompetenciaHistorico nivelCompetenciaHistorico);
}
