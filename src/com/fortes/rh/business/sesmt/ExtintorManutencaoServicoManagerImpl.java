package com.fortes.rh.business.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoServicoDao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;

@Component
public class ExtintorManutencaoServicoManagerImpl extends GenericManagerImpl<ExtintorManutencaoServico, ExtintorManutencaoServicoDao> implements ExtintorManutencaoServicoManager
{
	@Autowired
	ExtintorManutencaoServicoManagerImpl(ExtintorManutencaoServicoDao extintorManutencaoServicoDao) {
			setDao(extintorManutencaoServicoDao);
	}
}