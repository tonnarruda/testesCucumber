package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.CollectionUtil;


public class EstabelecimentoDWR
{
	private EstabelecimentoManager estabelecimentoManager;

	public String calcularDV(String cnpj, String idEstabelecimento, String idEmpresa)
	{
		Long id = Long.valueOf(idEstabelecimento);
		Long empresaId = Long.valueOf(idEmpresa);

		String complemento = cnpj.substring(8,12);

		String dvRetorno = estabelecimentoManager.calculaDV(cnpj);

		if(estabelecimentoManager.verificaCnpjExiste(complemento,id,empresaId))
			dvRetorno = "XX";

		return dvRetorno;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresa(Long empresaId)
	{
		Collection<Estabelecimento> estabelecimentos;
		
		if(empresaId == null || empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos
			estabelecimentos = estabelecimentoManager.findAll();
		else
			estabelecimentos = estabelecimentoManager.findAllSelect(empresaId);

		CollectionUtil<Estabelecimento> cu1 = new CollectionUtil<Estabelecimento>();
		estabelecimentos = cu1.sortCollectionStringIgnoreCase(estabelecimentos, "descricaoComEmpresa");

		return cu1.convertCollectionToMap(estabelecimentos, "getId", "getDescricaoComEmpresa");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresas(Long empresaId, Long[] empresaIds)
	{
		Collection<Estabelecimento> estabelecimentos;
		if(empresaId == null || empresaId == 0 || empresaId == -1 )
			estabelecimentos = estabelecimentoManager.findAllSelect(empresaIds);
		else
			estabelecimentos = estabelecimentoManager.findAllSelect(empresaId);

		return new CollectionUtil<Estabelecimento>().convertCollectionToMap(estabelecimentos, "getId", "getDescricaoComEmpresa");
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

}
