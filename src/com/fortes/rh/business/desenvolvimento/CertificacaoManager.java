package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.relatorio.CertificacaoTreinamentosRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.web.tags.CheckBox;

public interface CertificacaoManager extends GenericManager<Certificacao>
{
	Collection<Certificacao> findAllSelect(Long empresaId);
	Collection<Certificacao> findByFaixa(Long faixaId);
	Collection<MatrizTreinamento> getByFaixasOrCargos(String[] faixaSalarialsCheck, String[] cargosCheck);
	Certificacao findByIdProjection(Long id);
	Collection<MatrizTreinamento> montaMatriz(boolean imprimirMatriz, String[] faixaSalarialId, Collection<ColaboradorTurma> colaboradorTurmas);
	Collection<CertificacaoTreinamentosRelatorio> montaCertificacao(Long certificacaoId, String[] colaboradoresCheck, Certificado certificado, Collection<Curso> cursos, boolean vencimentoPorCertificacao);
	Collection<Certificacao> findAllSelect(Integer page, Integer pagingSize, Long id, String nomeBusca);
	Integer getCount(Long empresaId, String nomeBusca);
	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
	Collection<Colaborador> findColaboradoresNaCertificacoa(Long certificacaoId);
	Collection<Certificacao> findAllSelectNotCertificacaoId(Long empresaId, Long certificacaoId);
	Collection<Certificacao> findByCursoId(Long cursoId);
	Collection<CheckBox> populaCheckBoxDesabilitandoSemPeriodicidade(Long empresaId);
}