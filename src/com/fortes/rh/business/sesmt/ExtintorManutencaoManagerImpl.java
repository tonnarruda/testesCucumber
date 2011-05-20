package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.util.CollectionUtil;

public class ExtintorManutencaoManagerImpl extends GenericManagerImpl<ExtintorManutencao, ExtintorManutencaoDao> implements ExtintorManutencaoManager
{
	public Collection<ExtintorManutencao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean somenteSemRetorno)
	{
		return getDao().findAllSelect(page, pagingSize, empresaId, estabelecimentoId,  extintorId, inicio, fim, somenteSemRetorno);
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean somenteSemRetorno)
	{
		return getDao().getCount(empresaId, estabelecimentoId,  extintorId, inicio, fim, somenteSemRetorno);
	}

	public ExtintorManutencao saveOrUpdate(ExtintorManutencao extintorManutencao, String[] servicoChecks) throws Exception
	{
		CollectionUtil<ExtintorManutencaoServico> collectionUtil = new CollectionUtil<ExtintorManutencaoServico>();
		Collection<ExtintorManutencaoServico> servicos = collectionUtil.convertArrayStringToCollection(ExtintorManutencaoServico.class, servicoChecks);

		extintorManutencao.setServicos(servicos);

		if (extintorManutencao.getId() == null)
			this.save(extintorManutencao);
		else
			this.update(extintorManutencao);

		return extintorManutencao;
	}
	
	public void save(ExtintorManutencao extintorManutencao, String[] servicoChecks) throws Exception
	{
		CollectionUtil<ExtintorManutencaoServico> collectionUtil = new CollectionUtil<ExtintorManutencaoServico>();
		Collection<ExtintorManutencaoServico> servicos = collectionUtil.convertArrayStringToCollection(ExtintorManutencaoServico.class, servicoChecks);

		extintorManutencao.setServicos(servicos);

		this.save(extintorManutencao);
	}

	public Collection<ExtintorManutencao> findManutencaoVencida(Long estabelecimentoId, Date dataVencimento, String motivo)
	{
		return getDao().findManutencaoVencida(estabelecimentoId, dataVencimento, motivo);
	}
}