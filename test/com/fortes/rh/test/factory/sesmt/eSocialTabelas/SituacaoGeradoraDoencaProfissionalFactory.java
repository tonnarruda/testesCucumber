package com.fortes.rh.test.factory.sesmt.eSocialTabelas;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;

public class SituacaoGeradoraDoencaProfissionalFactory
{
	public static SituacaoGeradoraDoencaProfissional getEntity()
	{
		SituacaoGeradoraDoencaProfissional situacaoGeradoraDoencaProfissional = new SituacaoGeradoraDoencaProfissional();
		situacaoGeradoraDoencaProfissional.setId(null);
		return situacaoGeradoraDoencaProfissional;
	}

	public static SituacaoGeradoraDoencaProfissional getEntity(Long id)
	{
		SituacaoGeradoraDoencaProfissional situacaoGeradoraDoencaProfissional = getEntity();
		situacaoGeradoraDoencaProfissional.setId(id);

		return situacaoGeradoraDoencaProfissional;
	}

	public static Collection<SituacaoGeradoraDoencaProfissional> getCollection()
	{
		Collection<SituacaoGeradoraDoencaProfissional> situacaoGeradoraDoencaProfissionals = new ArrayList<SituacaoGeradoraDoencaProfissional>();
		situacaoGeradoraDoencaProfissionals.add(getEntity());

		return situacaoGeradoraDoencaProfissionals;
	}
	
	public static Collection<SituacaoGeradoraDoencaProfissional> getCollection(Long id)
	{
		Collection<SituacaoGeradoraDoencaProfissional> situacaoGeradoraDoencaProfissionals = new ArrayList<SituacaoGeradoraDoencaProfissional>();
		situacaoGeradoraDoencaProfissionals.add(getEntity(id));
		
		return situacaoGeradoraDoencaProfissionals;
	}
}
