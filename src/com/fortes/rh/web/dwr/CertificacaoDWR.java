package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class CertificacaoDWR
{
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;

    public  Collection<CheckBox> getColaboradores(String dataIni, String dataFim, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Integer mesesCertificacoesAVencer, String[] areaOrganizacionalIds, String[] estabelecimentoIds, String[] certificacoesIds)
    {
    	Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();

		Long [] areasIdsLong = StringUtil.stringToLong(areaOrganizacionalIds);
		Long [] estabelecimentoIdsLong = StringUtil.stringToLong(estabelecimentoIds);
		Long [] certificacoesIdsLong = StringUtil.stringToLong(certificacoesIds);
		
		Date dataFinal = null;
		if(dataFim != null && !dataFim.equals("") && !dataFim.equals("  /  /    "))
			dataFinal = DateUtil.criarDataDiaMesAno(dataFim);
		
    	Collection<ColaboradorCertificacao> colaboradoresCertificacao = colaboradorCertificacaoManager.colaboradoresParticipamCertificacao(null, dataFinal, mesesCertificacoesAVencer, colaboradorCertificado, colaboradorNaoCertificado, areasIdsLong, estabelecimentoIdsLong, certificacoesIdsLong, x);
    	
    	CheckBox checkBox;
    	for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificacao) {
    		checkBox = new CheckBox();
			checkBox.setId(colaboradorCertificacao.getColaborador().getId());
			checkBox.setNome(colaboradorCertificacao.getColaborador().getNome());
			checkboxes.add(checkBox);
    	}
    	
    	return checkboxes;
    }

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}
}
