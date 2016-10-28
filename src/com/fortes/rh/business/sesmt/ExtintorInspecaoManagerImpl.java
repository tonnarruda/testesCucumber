package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

@Component
public class ExtintorInspecaoManagerImpl extends GenericManagerImpl<ExtintorInspecao, ExtintorInspecaoDao> implements ExtintorInspecaoManager
{
	@Autowired
	ExtintorInspecaoManagerImpl(ExtintorInspecaoDao extintorInspecaoDao) {
			setDao(extintorInspecaoDao);
	}
	
	public Collection<ExtintorInspecao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao)
	{
		Collection<ExtintorInspecao> consultaExtintorInpecao = getDao().findAllSelect(page, pagingSize, empresaId, estabelecimentoId, extintorId, inicio, fim, regularidade, localizacao); 
		
		for (ExtintorInspecao consultaExtintorInspecao : consultaExtintorInpecao)
		{
			if(consultaExtintorInpecao != null && consultaExtintorInspecao.getItens() != null && !consultaExtintorInspecao.getItens().isEmpty())
				consultaExtintorInspecao.setTipoDeRegularidade("Irregular");
		}
		
		for (ExtintorInspecao extintorInspecao : consultaExtintorInpecao) 
		{
			String extintorInspecaoItemsString = "";

			if (extintorInspecao.getItens()!=null)
			{
				Collection<ExtintorInspecaoItem> extintorInspecaoItems = extintorInspecao.getItens();

				for (ExtintorInspecaoItem extintorInspecaoItem : extintorInspecaoItems) {
					if("Outro".equals(extintorInspecaoItem.getDescricao()))
						extintorInspecaoItemsString += StringUtils.defaultString(extintorInspecao.getOutroMotivo()) + ", ";
					else
						extintorInspecaoItemsString += extintorInspecaoItem.getDescricao() + ", ";
				}

			}
			extintorInspecao.setItensRelatorio(StringUtil.isBlank(extintorInspecaoItemsString) ? "" : extintorInspecaoItemsString.substring(0, extintorInspecaoItemsString.length()-2));
		}
		
		return consultaExtintorInpecao;
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade)
	{
		return getDao().getCount(empresaId, estabelecimentoId,  extintorId, inicio, fim, regularidade);
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

	public ExtintorInspecao findByIdProjection(Long extintorInspecaoId) {
		return getDao().findByIdProjection(extintorInspecaoId);
	}
}