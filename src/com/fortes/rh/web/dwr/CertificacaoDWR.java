package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		
		Date dataInicial = null;
		if(dataIni != null && !dataIni.equals("") && !dataIni.equals("  /  /    "))
			dataInicial = DateUtil.criarDataDiaMesAno(dataIni);
		
		Date dataFinal = null;
		if(dataFim != null && !dataFim.equals("") && !dataFim.equals("  /  /    "))
			dataFinal = DateUtil.criarDataDiaMesAno(dataFim);
		
    	Collection<ColaboradorCertificacao> colaboradorCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataInicial, dataFinal, colaboradorCertificado, colaboradorNaoCertificado, (mesesCertificacoesAVencer == 0 ? null : mesesCertificacoesAVencer), areasIdsLong, estabelecimentoIdsLong, certificacoesIdsLong, null);
    	
    	CheckBox checkBox = null;
    	Map<Long, String> colaboradoresmaps = new HashMap<Long, String>();
    	for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificacoes) 
    		if(!colaboradoresmaps.containsKey(colaboradorCertificacao.getColaborador().getId()))
    			colaboradoresmaps.put(colaboradorCertificacao.getColaborador().getId(), colaboradorCertificacao.getColaborador().getNome());
    	
    	
    	for (Long colaboradorId : colaboradoresmaps.keySet())
    	{
    		checkBox = new CheckBox();
			checkBox.setId(colaboradorId);
			checkBox.setNome(colaboradoresmaps.get(colaboradorId));
			checkboxes.add(checkBox);
    	}
    	
    	return checkboxes;
    }

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}
}
