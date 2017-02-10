package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.CollectionUtil;

@Component
@RemoteProxy(name="EstabelecimentoDWR")
public class EstabelecimentoDWR
{
	@Autowired private EstabelecimentoManager estabelecimentoManager;

	@RemoteMethod
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
	
	@RemoteMethod
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresa(Long empresaId)
	{
		Collection<Estabelecimento> estabelecimentos;
		
		if(empresaId == null || empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos
			estabelecimentos = estabelecimentoManager.findAll();
		else
			estabelecimentos = estabelecimentoManager.findAllSelect(empresaId);

		CollectionUtil<Estabelecimento> cu1 = new CollectionUtil<Estabelecimento>();
		estabelecimentos = cu1.sortCollectionStringIgnoreCase(estabelecimentos, "nome");

		return cu1.convertCollectionToMap(estabelecimentos, "getId", "getNome");
	}

	@RemoteMethod
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
}