package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;

public interface TurmaDocumentoAnexoManager extends GenericManager<TurmaDocumentoAnexo>
{
	void salvarDocumentoAnexos(Long turmaId, Long[] avaliacaoTurmaIds);
	public Collection<TurmaDocumentoAnexo> findByColaborador(Long colaboradorId);
	void removeByTurma(Long turmaId);
}