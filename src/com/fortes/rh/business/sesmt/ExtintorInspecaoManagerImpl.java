package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

public class ExtintorInspecaoManagerImpl extends GenericManagerImpl<ExtintorInspecao, ExtintorInspecaoDao> implements ExtintorInspecaoManager
{
	public Collection<ExtintorInspecao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim)
	{
		return getDao().findAllSelect(page, pagingSize, empresaId, estabelecimentoId,  extintorId, inicio, fim);
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim)
	{
		return getDao().getCount(empresaId, estabelecimentoId,  extintorId, inicio, fim);
	}

	public ExtintorInspecao saveOrUpdate(ExtintorInspecao extintorInspecao, String[] itemChecks) throws Exception
	{
		CollectionUtil<ExtintorInspecaoItem> collectionUtil = new CollectionUtil<ExtintorInspecaoItem>();
		Collection<ExtintorInspecaoItem> itens = collectionUtil.convertArrayStringToCollection(ExtintorInspecaoItem.class, itemChecks);

		extintorInspecao.setItens(itens);

		if (extintorInspecao.getId() == null)
			this.save(extintorInspecao);
		else
			this.update(extintorInspecao);

		return extintorInspecao;
	}

	public String getEmpresasResponsaveis(Long empresaId)
	{
		Collection<String> descricaoEmpresas = getDao().findEmpresasResponsaveisDistinct(empresaId);
		if(descricaoEmpresas == null || descricaoEmpresas.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(descricaoEmpresas);
	}

	public Collection<ExtintorInspecao> findInspecoesVencidas(Long estabelecimentoId, Date dataVencimento)
	{
		return getDao().findInspecoesVencidas(estabelecimentoId, dataVencimento);
	}
}