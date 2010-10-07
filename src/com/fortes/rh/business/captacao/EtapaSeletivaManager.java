/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.web.tags.CheckBox;

public interface EtapaSeletivaManager extends GenericManager<EtapaSeletiva>
{
	int sugerirOrdem(Long empresaId);

	EtapaSeletiva findPrimeiraEtapa(Long empresaId);

	public void update(EtapaSeletiva etapaSeletiva, Empresa empresa);

	public void remove(EtapaSeletiva etapaSeletiva, Empresa empresa);

	Collection<EtapaSeletiva> findAllSelect(Long empresaId);

	Collection<EtapaSeletiva> findAllSelect(int page, int pagingSize, Long empresaId);

	public EtapaSeletiva findByEtapaSeletivaId(Long etapaSeletivaId, Long empresaId);

	Integer getCount(Long empresaId);

	public EtapaSeletiva findByIdProjection(Long etapaSeletivaId);

	Collection<ProcessoSeletivoRelatorio> montaProcessosSeletivos(Long[] etapaIds);
	
	public Collection<CheckBox> populaCheckOrderNome(long empresaId);
	
	public Collection<EtapaSeletiva> populaEtapaseletiva(String[] areasCheck);
	
	public Collection<EtapaSeletiva> findByCargo(Long cargoId);
	
}