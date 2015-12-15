package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.util.CollectionUtil;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	private CertificacaoManager certificacaoManager;
	
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}

	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Long empresaId, Long certificacaoId,	Long[] areaIds, Long[] estabelecimentoIds, char filtroCetificacao) 
	{
		Collection<ColaboradorCertificacao> colabNaCertificacaoNaoCertificados = new ArrayList<ColaboradorCertificacao>();
		
		if(certificacaoId != null)
			colabNaCertificacaoNaoCertificados.addAll(getDao().colabNaCertificacaoNaoCertificados(certificacaoId, areaIds, estabelecimentoIds));
		else{
			Collection<Certificacao> certificacoes = certificacaoManager.findAllSelect(empresaId);
			
			for (Certificacao certificacao : certificacoes) 
				colabNaCertificacaoNaoCertificados.addAll(getDao().colabNaCertificacaoNaoCertificados(certificacao.getId(), areaIds, estabelecimentoIds));
		}
		
		colabNaCertificacaoNaoCertificados.addAll(getDao().colaboradoresCertificados(certificacaoId, areaIds, estabelecimentoIds));
		
		CollectionUtil<ColaboradorCertificacao> cUtil = new CollectionUtil<ColaboradorCertificacao>();
		colabNaCertificacaoNaoCertificados = cUtil.sortCollectionStringIgnoreCase(colabNaCertificacaoNaoCertificados, "colaborador.nome");
		
		return colabNaCertificacaoNaoCertificados;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager) {
		this.certificacaoManager = certificacaoManager;
	}
}
