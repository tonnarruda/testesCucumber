/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.EtapaSeletivaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.web.tags.CheckBox;

public interface EtapaSeletivaManager extends GenericManager<EtapaSeletiva>
{
	@Audita(operacao="Atualização", auditor=EtapaSeletivaAuditorCallbackImpl.class)
	void update(EtapaSeletiva etapaSeletiva, Empresa empresa);
	@Audita(operacao="Remoção", auditor=EtapaSeletivaAuditorCallbackImpl.class)
	public void remove(EtapaSeletiva etapaSeletiva, Empresa empresa);

	int sugerirOrdem(Long empresaId);

	EtapaSeletiva findPrimeiraEtapa(Long empresaId);

	Collection<EtapaSeletiva> findAllSelect(Long empresaId);

	Collection<EtapaSeletiva> findAllSelect(int page, int pagingSize, Long empresaId);

	public EtapaSeletiva findByEtapaSeletivaId(Long etapaSeletivaId, Long empresaId);

	Integer getCount(Long empresaId);

	public EtapaSeletiva findByIdProjection(Long etapaSeletivaId);

	Collection<ProcessoSeletivoRelatorio> montaProcessosSeletivos(Long empresaId, Long[] etapaIds);
	
	public Collection<CheckBox> populaCheckOrderNome(long empresaId);
	
	public Collection<EtapaSeletiva> populaEtapaseletiva(String[] areasCheck);
	
	public Collection<EtapaSeletiva> findByCargo(Long cargoId);
	
}