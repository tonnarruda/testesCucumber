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

    public  Collection<CheckBox> getColaboradores(String dataIni, String dataFim, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Integer mesesCertificacoesAVencer, String[] areaOrganizacionalIds, String[] estabelecimentoIds, String[] certificacoesIds, String situacaoColaborador) {
    	Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
		Long [] areasIdsLong = StringUtil.stringToLong(areaOrganizacionalIds);
		Long [] estabelecimentoIdsLong = StringUtil.stringToLong(estabelecimentoIds);
		Long [] certificacoesIdsLong = StringUtil.stringToLong(certificacoesIds);
		
		Date dataFinal = null;
		if(dataFim != null && !dataFim.equals("") && !dataFim.equals("  /  /    "))
			dataFinal = DateUtil.criarDataDiaMesAno(dataFim);
		
    	Collection<ColaboradorCertificacao> colaboradoresCertificacao = colaboradorCertificacaoManager.colaboradoresParticipamCertificacao(null, dataFinal, mesesCertificacoesAVencer, colaboradorCertificado, colaboradorNaoCertificado, areasIdsLong, estabelecimentoIdsLong, certificacoesIdsLong, null, situacaoColaborador);
    	Collection<Long> colaboradoresIdsAdicionados = new ArrayList<Long>();
    	for (ColaboradorCertificacao colaboradorCert : colaboradoresCertificacao) {
    		CheckBox checkBox = new CheckBox();
			checkBox.setId(colaboradorCert.getColaboradorId());
			checkBox.setNome(colaboradorCert.getColaborador().getNome());
			
			if(!colaboradoresIdsAdicionados.contains(colaboradorCert.getColaboradorId())){
				colaboradoresIdsAdicionados.add(colaboradorCert.getColaboradorId());
				checkboxes.add(checkBox);
			}
    	}
    	
    	return checkboxes;
    }
    
    public  Collection<CheckBox> getColaboradoresSemCertificacao(Long empresaId, String[] areaOrganizacionalIds, String[] estabelecimentoIds, String[] certificacoesIds, String situacaoColaborador) {
		Long [] areasIdsLong = StringUtil.stringToLong(areaOrganizacionalIds);
		Long [] estabelecimentoIdsLong = StringUtil.stringToLong(estabelecimentoIds);
		Long [] certificacoesIdsLong = StringUtil.stringToLong(certificacoesIds);
		
		return colaboradorCertificacaoManager.checkBoxColaboradoresSemCertificacaoDWR(empresaId, areasIdsLong, estabelecimentoIdsLong, certificacoesIdsLong, situacaoColaborador);
    }

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}
}
