package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public interface ColaboradorPresencaManager extends GenericManager<ColaboradorPresenca>
{
	public Collection<ColaboradorPresenca> findPresencaByTurma(Long id);
	public boolean existPresencaByTurma(Long turmaId);
	public void updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca) throws Exception;
	public void marcarTodos(Long diaTurmaId, Long turmaId);
	public void removeByDiaTurma(Long diaTurmaId) throws Exception;
	public String calculaFrequencia(Long colaboradorTurmaId, Integer qtdDias);
	public Collection<ColaboradorTurma> preparaLinhaEmBranco(Collection<ColaboradorTurma> colaboradorTurmas, int qtdMaxLinha, Long estabelecimentoId);
	public void removeByColaboradorTurma(Long[] colaboradorTurmaIds);
	public Integer qtdDiaPresentesTurma(Long turmaId, Long[] areasIds);
}