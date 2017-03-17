package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TamanhoEPIDao;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class TamanhoEPIManagerImpl extends GenericManagerImpl<TamanhoEPI, TamanhoEPIDao> implements TamanhoEPIManager {
	
	@Autowired private SolicitacaoEpiItemManager solicitacaoEpiItemManager;
	
	@Autowired
	TamanhoEPIManagerImpl(TamanhoEPIDao tamanhoEPIDao) {
		setDao(tamanhoEPIDao);
	}
	
	public Collection<CheckBox> populaCheckOrderDescricao(Long tipoEPIId) {
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		
		try {
			Collection<TamanhoEPI> tamanhoEPIs = getDao().findAll(new String[]{"descricao"});
			checks = CheckListBoxUtil.populaCheckListBox(tamanhoEPIs, "getId", "getDescricao", null);
			
			if ( tipoEPIId != null) {
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