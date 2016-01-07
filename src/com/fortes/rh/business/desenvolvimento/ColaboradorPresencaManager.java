package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public interface ColaboradorPresencaManager extends GenericManager<ColaboradorPresenca>
{
	public Collection<ColaboradorPresenca> findPresencaByTurma(Long id);
	public boolean existPresencaByTurma(Long turmaId);
	public void updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca, boolean validarCertificacao) throws Exception;
	public void marcarTodos(Long diaTurmaId, Long turmaId, boolean validarCertificacao);
	public void removeByDiaTurma(Long diaTurmaId, Long turmaId, boolean validarCertificacao) throws Exception;
	public String calculaFrequencia(Long colaboradorTurmaId, Integer qtdDias);
	public Collection<ColaboradorTurma> preparaLinhaEmBranco(Collection<ColaboradorTurma> colaboradorTurmas, int qtdMaxLinha, Long estabelecimentoId);
	public void removeByColaboradorTurma(Long[] colaboradorTurmaIds);
	public Integer qtdDiaPresentesTurma(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds);
	public Integer qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(Long diaTurmaId, Long estabelecimentoId);
}