package com.fortes.rh.test.factory.sesmt.eSocialTabelas;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;

public class CodificacaoAcidenteTrabalhoFactory
{
	public static CodificacaoAcidenteTrabalho getEntity()
	{
		CodificacaoAcidenteTrabalho codificacaoAcidenteTrabalho = new CodificacaoAcidenteTrabalho();
		codificacaoAcidenteTrabalho.setId(null);
		return codificacaoAcidenteTrabalho;
	}

	public static CodificacaoAcidenteTrabalho getEntity(Long id)
	{
		CodificacaoAcidenteTrabalho codificacaoAcidenteTrabalho = getEntity();
		codificacaoAcidenteTrabalho.setId(id);

		return codificacaoAcidenteTrabalho;
	}

	public static Collection<CodificacaoAcidenteTrabalho> getCollection()
	{
		Collection<CodificacaoAcidenteTrabalho> codificacaoAcidenteTrabalhos = new ArrayList<CodificacaoAcidenteTrabalho>();
		codificacaoAcidenteTrabalhos.add(getEntity());

		return codificacaoAcidenteTrabalhos;
	}
	
	public static Collection<CodificacaoAcidenteTrabalho> getCollection(Long id)
	{
		Collection<CodificacaoAcidenteTrabalho> codificacaoAcidenteTrabalhos = new ArrayList<CodificacaoAcidenteTrabalho>();
		codificacaoAcidenteTrabalhos.add(getEntity(id));
		
		return codificacaoAcidenteTrabalhos;
	}
}
