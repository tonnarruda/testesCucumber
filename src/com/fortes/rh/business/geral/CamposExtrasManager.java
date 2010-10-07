package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.CamposExtras;

public interface CamposExtrasManager extends GenericManager<CamposExtras>
{
	CamposExtras update(CamposExtras camposExtras, Long camposExtrasId);
}
