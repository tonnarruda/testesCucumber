package com.fortes.rh.web.dwr;

import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.util.DateUtil;

public class HistoricoFuncaoDWR
{
	private HistoricoFuncaoManager historicoFuncaoManager;
	
	public boolean existeHistoricoAmbienteByData(Long funcaoId, String data) throws Exception{
		HistoricoFuncao historicoFuncao = historicoFuncaoManager.findByFuncaoAndData(funcaoId, DateUtil.criarDataDiaMesAno(data));
		if(historicoFuncao == null || historicoFuncao.getId() == null)
			return false;
		
		return true;
	}
	
	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager) {
		this.historicoFuncaoManager = historicoFuncaoManager;
	}	
}