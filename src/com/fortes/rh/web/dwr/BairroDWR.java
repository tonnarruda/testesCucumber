package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.util.CollectionUtil;

@Component
@SuppressWarnings({"rawtypes"})
@RemoteProxy(name="BairroDWR")
public class BairroDWR
{
	@Autowired private BairroManager bairroManager;

	@RemoteMethod
	public String[] getBairros(String cidadeId)
	{
		if(cidadeId != null && !cidadeId.equals("") && !cidadeId.equals("-1"))
		{
			Collection<Bairro> bairros =  bairroManager.findAllSelect(Long.valueOf(cidadeId));

			return  new CollectionUtil<Bairro>().convertCollectionToArrayString(bairros, "getNome");
		}

		return new String[0];
	}

	@RemoteMethod
	public Map getBairrosMap(String cidadeId)
	{
		if(cidadeId != null && !cidadeId.equals("-1") && !cidadeId.equals(""))
		{
			Collection<Bairro> bairros =  bairroManager.findAllSelect(Long.valueOf(cidadeId));

			return new CollectionUtil<Bairro>().convertCollectionToMap(bairros,"getId","getNome");
		}

		return new HashMap();
	}

	@RemoteMethod
	public Map getBairrosCheckList(Long[] cidadesChecks)
	{
		if(cidadesChecks != null && cidadesChecks.length > 0)
		{
			Collection<Bairro> bairros = bairroManager.findAllSelect(cidadesChecks);
			
			return new CollectionUtil<Bairro>().convertCollectionToMap(bairros,"getId","getNome");
		}
		
		return new HashMap();
	}
	
	@RemoteMethod
	public Long novoBairro(String nomeBairro, String cidadeId)
	{
		Cidade cidade = new Cidade();
		cidade.setId(Long.parseLong(cidadeId));
		Bairro bairro = new Bairro();
		bairro.setNome(nomeBairro);
		bairro.setCidade(cidade);

		if(!bairroManager.existeBairro(bairro))
		{
			bairro = bairroManager.save(bairro);
			return bairro.getId();
		}
		else
		{
			return null;
		}
	}
}
