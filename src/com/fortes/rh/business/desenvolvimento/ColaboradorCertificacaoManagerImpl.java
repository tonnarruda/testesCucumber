package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	private CertificacaoManager certificacaoManager;
	
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}

	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Long empresaId, Long certificacaoId,	Long[] estabelecimentoIds, Long[] areaIds, char filtroCetificacao) 
	{
		Collection<ColaboradorCertificacao> colabNaCertificacaoNaoCertificados = new ArrayList<ColaboradorCertificacao>();
		
		if(certificacaoId != null)
			colabNaCertificacaoNaoCertificados.addAll(getDao().colabNaCertificacaoNaoCertificados(certificacaoId, null, null));
		else{
			Collection<Certificacao> certificacoes = certificacaoManager.findAllSelect(empresaId);
			
			for (Certificacao certificacao : certificacoes) 
				colabNaCertificacaoNaoCertificados.addAll(getDao().colabNaCertificacaoNaoCertificados(certificacao.getId(), null, null));
		}
		
		
		return null;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager) {
		this.certificacaoManager = certificacaoManager;
	}
}
