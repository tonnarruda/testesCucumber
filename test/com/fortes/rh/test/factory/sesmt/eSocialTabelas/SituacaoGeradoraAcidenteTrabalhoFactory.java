package com.fortes.rh.test.factory.sesmt.eSocialTabelas;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;

public class SituacaoGeradoraAcidenteTrabalhoFactory
{
	public static SituacaoGeradoraAcidenteTrabalho getEntity()
	{
		SituacaoGeradoraAcidenteTrabalho situacaoGeradoraAcidenteTrabalho = new SituacaoGeradoraAcidenteTrabalho();
		situacaoGeradoraAcidenteTrabalho.setId(null);
		return situacaoGeradoraAcidenteTrabalho;
	}

	public static SituacaoGeradoraAcidenteTrabalho getEntity(Long id)
	{
		SituacaoGeradoraAcidenteTrabalho situacaoGeradoraAcidenteTrabalho = getEntity();
		situacaoGeradoraAcidenteTrabalho.setId(id);

		return situacaoGeradoraAcidenteTrabalho;
	}

	public static Collection<SituacaoGeradoraAcidenteTrabalho> getCollection()
	{
		Collection<SituacaoGeradoraAcidenteTrabalho> situacaoGeradoraAcidenteTrabalhos = new ArrayList<SituacaoGeradoraAcidenteTrabalho>();
		situacaoGeradoraAcidenteTrabalhos.add(getEntity());

		return situacaoGeradoraAcidenteTrabalhos;
	}
	
	public static Collection<SituacaoGeradoraAcidenteTrabalho> getCollection(Long id)
	{
		Collection<SituacaoGeradoraAcidenteTrabalho> situacaoGeradoraAcidenteTrabalhos = new ArrayList<SituacaoGeradoraAcidenteTrabalho>();
		situacaoGeradoraAcidenteTrabalhos.add(getEntity(id));
		
		return situacaoGeradoraAcidenteTrabalhos;
	}
}
