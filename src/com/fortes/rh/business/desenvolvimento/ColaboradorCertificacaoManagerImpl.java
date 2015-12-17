package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.util.CollectionUtil;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}

	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, Long empresaId, Long[] areaIds,	Long[] estabelecimentoIds, char filtroCetificacao, Long... certificacoesIds) 
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificacao = new ArrayList<ColaboradorCertificacao>();
		
		if(false)//Vendo regras com o pessoal da equipe
			for (Long certificacaoId : certificacoesIds) 
				colaboradoresCertificacao.addAll(getDao().colabNaCertificacaoNaoCertificados(areaIds, estabelecimentoIds, certificacaoId));
		
		colaboradoresCertificacao.addAll(getDao().colaboradoresCertificados(dataIni, dataFim, filtroCetificacao, areaIds, estabelecimentoIds, certificacoesIds));
		
		return new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradoresCertificacao, "colaborador.nome");
	}
}
