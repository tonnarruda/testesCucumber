package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Colaborador;

public interface CertificacaoDao extends GenericDao<Certificacao>
{
	Collection<Certificacao> findAllSelect(Long empresaId);
	Collection<Certificacao> findAllSelect(Integer page, Integer pagingSize, Long id, String nomeBusca);
	Collection<Certificacao> findByFaixa(Long faixaId);
	Collection<MatrizTreinamento> findMatrizTreinamento(Collection<Long> faixaIds);
	Certificacao findByIdProjection(Long id);
	Integer getCount(Long empresaId, String nomeBusca);
	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
	Collection<Colaborador> findColaboradoresNaCertificacao(Long certificacaoId);
	public Collection<Certificacao> findAllSelectNotCertificacaoId(Long empresaId, Long certificacaoId);
	public Collection<Certificacao> findByCursoId(Long cursoId);
}