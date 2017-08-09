package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class CertificacaoDWR
{
	private ColaboradorManager colaboradorManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;

    public  Collection<CheckBox> getColaboradores(String dataIni, String dataFim, boolean colaboradorCertificado, boolean colaboradorNaoCertificado, Integer mesesCertificacoesAVencer, String[] areaOrganizacionalIds, String[] estabelecimentoIds, String[] certificacoesIds, String situacaoColaborador, Long empresaId) {
    	Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
		Long [] areasIdsLong = StringUtil.stringToLong(areaOrganizacionalIds);
		Long [] estabelecimentoIdsLong = StringUtil.stringToLong(estabelecimentoIds);
		Long [] certificacoesIdsLong = StringUtil.stringToLong(certificacoesIds);
		
		Date dataFinal = null;
		if(dataFim != null && !dataFim.equals("") && !dataFim.equals("  /  /    "))
			dataFinal = DateUtil.criarDataDiaMesAno(dataFim);
		
		if(colaboradorNaoCertificado){
			Collection<Colaborador> colaboradores = colaboradorManager.findDadosBasicosNotIds(null, null, areasIdsLong, estabelecimentoIdsLong, situacaoColaborador, empresaId); 
			for (Colaborador colaborador : colaboradores) {
				CheckBox checkBox = new CheckBox();
				checkBox.setId(colaborador.getId());
				checkBox.setNome(colaborador.getNomeMaisNomeComercial());
				checkboxes.add(checkBox);
			}
		} else if(colaboradorCertificado){
	    	Collection<ColaboradorCertificacao> colaboradoresCertificacao = colaboradorCertificacaoManager.colaboradoresCertificados(null, dataFinal, mesesCertificacoesAVencer, certificacoesIdsLong, areasIdsLong, estabelecimentoIdsLong, null, situacaoColaborador);
	    	Collection<Long> colaboradoresIdsAdicionados = new ArrayList<Long>();
	    	for (ColaboradorCertificacao colaboradorCert : colaboradoresCertificacao) {
	    		CheckBox checkBox = new CheckBox();
				checkBox.setId(colaboradorCert.getColaboradorId());
				checkBox.setNome(colaboradorCert.getColaborador().getNomeMaisNomeComercial());
				
				if(!colaboradoresIdsAdicionados.contains(colaboradorCert.getColaboradorId())){
					colaboradoresIdsAdicionados.add(colaboradorCert.getColaboradorId());
					checkboxes.add(checkBox);
				}
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

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
}
