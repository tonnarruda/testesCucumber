package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.sesmt.OrdemDeServico;

public class OrdemDeServicoFactory
{
	public static OrdemDeServico getEntity()
	{
		OrdemDeServico ordemDeServico = new OrdemDeServico();
		ordemDeServico.setId(null);
		return ordemDeServico;
	}

	public static OrdemDeServico getEntity(Long id)
	{
		OrdemDeServico ordemDeServico = getEntity();
		ordemDeServico.setId(id);

		return ordemDeServico;
	}
	
	public static OrdemDeServico getEntity(Long id, Date data, boolean impressa)
	{
		OrdemDeServico ordemDeServico = getEntity();
		ordemDeServico.setId(id);
		ordemDeServico.setData(data);
		ordemDeServico.setImpressa(impressa);
		return ordemDeServico;
	}

	public static Collection<OrdemDeServico> getCollection()
	{
		Collection<OrdemDeServico> ordemDeServicos = new ArrayList<OrdemDeServico>();
		ordemDeServicos.add(getEntity());

		return ordemDeServicos;
	}
	
	public static Collection<OrdemDeServico> getCollection(Long id)
	{
		Collection<OrdemDeServico> ordemDeServicos = new ArrayList<OrdemDeServico>();
		ordemDeServicos.add(getEntity(id));
		
		return ordemDeServicos;
	}
}
