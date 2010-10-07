package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.model.sesmt.Anexo;

public interface AnexoManager extends GenericManager<Anexo>
{
	Collection<Anexo> findByOrigem(Long origemId, char origem);
	Anexo gravaAnexo(File arquivo, Anexo anexo);
	Anexo populaAnexo(Anexo anexo);
	String getStringRetorno(char origem);
}