package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;

public interface CertificacaoDao extends GenericDao<Certificacao>
{
	Collection<Certificacao> findAllSelect(Long empresaId);
	Collection<Certificacao> findAllSelect(Long id, String nomeBusca);
	Collection<Certificacao> findByFaixa(Long faixaId);
	Collection<MatrizTreinamento> findMatrizTreinamento(Collection<Long> faixaIds);
	Certificacao findByIdProjection(Long id);
}