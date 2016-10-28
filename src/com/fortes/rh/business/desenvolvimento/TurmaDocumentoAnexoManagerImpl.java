package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.TurmaDocumentoAnexoDao;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;

@Component
public class TurmaDocumentoAnexoManagerImpl extends GenericManagerImpl<TurmaDocumentoAnexo, TurmaDocumentoAnexoDao> implements TurmaDocumentoAnexoManager
{
	@Autowired
	TurmaDocumentoAnexoManagerImpl(TurmaDocumentoAnexoDao dao) {
		setDao(dao);
	}
	
	public void salvarDocumentoAnexos(Long turmaId, Long[] documentoAnexoIds) 
	{
		removeByTurma(turmaId);
		
		if (documentoAnexoIds != null)
		{
			for (Long documentoAnexoId : documentoAnexoIds) 
			{
				TurmaDocumentoAnexo turmaDocumentoAnexo = new TurmaDocumentoAnexo();
				turmaDocumentoAnexo.setProjectionDocumentoAnexosId(documentoAnexoId);
				turmaDocumentoAnexo.setProjectionTurmaId(turmaId);
				getDao().save(turmaDocumentoAnexo);
			}
		}
	}

	public Collection<TurmaDocumentoAnexo> findByColaborador(Long colaboradorId) {
		return getDao().findByColaborador(colaboradorId);
	}

	public void removeByTurma(Long turmaId)
	{
		getDao().removeByTurma(turmaId);
	}
}