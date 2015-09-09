package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TamanhoEPIDao;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class TamanhoEPIManagerImpl extends GenericManagerImpl<TamanhoEPI, TamanhoEPIDao> implements TamanhoEPIManager {
	public Collection<CheckBox> populaCheckOrderDescricao(Long tipoEPIId) {
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		
		try {
			Collection<TamanhoEPI> tamanhoEPIs = getDao().findAll(new String[]{"descricao"});
			checks = CheckListBoxUtil.populaCheckListBox(tamanhoEPIs, "getId", "getDescricao");
			
			if ( tipoEPIId != null) {
				SolicitacaoEpiItemManager solicitacaoEpiItemManager = (SolicitacaoEpiItemManager) SpringUtil.getBean("solicitacaoEpiItemManager");
				for (CheckBox tamanhoCheckBox : checks) {
					tamanhoCheckBox.setDesabilitado(solicitacaoEpiItemManager.countByTipoEPIAndTamanhoEPI(tipoEPIId, Long.parseLong(tamanhoCheckBox.getId())) > 0);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return checks;
	}
}