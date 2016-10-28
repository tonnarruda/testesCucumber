package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

@Component
public class ExtintorManutencaoManagerImpl extends GenericManagerImpl<ExtintorManutencao, ExtintorManutencaoDao> implements ExtintorManutencaoManager
{
	@Autowired
	ExtintorManutencaoManagerImpl(ExtintorManutencaoDao extintorManutencaoDao) {
			setDao(extintorManutencaoDao);
	}
	
	public Collection<ExtintorManutencao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean somenteSemRetorno, String localizacao)
	{
		Collection<ExtintorManutencao> consultaExtintorManutencao = getDao().findAllSelect(page, pagingSize, empresaId, estabelecimentoId,  extintorId, inicio, fim, somenteSemRetorno, localizacao);
		
		for (ExtintorManutencao extintorManutencao : consultaExtintorManutencao) 
		{
			String extintorManutencaoServicosString = "";

			if (extintorManutencao.getServicos()!=null)
			{
				Collection<ExtintorManutencaoServico> extintorManutencaoServicos = extintorManutencao.getServicos();
			
				for (ExtintorManutencaoServico extintorManutencaoItem : extintorManutencaoServicos) 
					extintorManutencaoServicosString += extintorManutencaoItem.getDescricao() + ", ";

			}
			
			extintorManutencao.setServicosRelatorio(StringUtil.isBlank(extintorManutencaoServicosString) ? "" : extintorManutencaoServicosString.substring(0, extintorManutencaoServicosString.length()-2));
		}
		
		return consultaExtintorManutencao;
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

	public ExtintorManutencao findByIdProjection(Long extintorManutencaoId) {
		return getDao().findByIdProjection(extintorManutencaoId);	}

}