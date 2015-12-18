package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}

	public Collection<ColaboradorCertificacao> montaRelatorioColaboradoresNasCertificacoes(Date dataIni, Date dataFim, char filtroCetificacao, Long[] areaIds, Long[] estabelecimentoIds, Long[] certificacoesIds){
		return getDao().colaboradoresCertificados(dataIni, dataFim, filtroCetificacao, areaIds, estabelecimentoIds, certificacoesIds);
	}

	public void verificaCertificacaoByColaboradorTurmaId(Long colaboradorTurmaId) 
	{
		Collection<ColaboradorCertificacao> colaboradoresCertificados  = getDao().colaboradoresCertificadosByColaboradorTurmaId(colaboradorTurmaId);
		
		for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificados) {
				colaboradorCertificacao.setData(new Date());
				getDao().save(colaboradorCertificacao);
		}
	}

	public Collection<ColaboradorCertificacao> getCertificacoesAVencer(Date data, Long empresaId) 
	{
		return getDao().getCertificacoesAVencer(data, empresaId);
	}
}
