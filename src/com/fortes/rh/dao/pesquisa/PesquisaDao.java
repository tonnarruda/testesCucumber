package com.fortes.rh.dao.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.Pesquisa;

public interface PesquisaDao extends GenericDao<Pesquisa>
{
	Pesquisa findByIdProjection(Long pesquisaId) throws Exception;
	Pesquisa findByQuestionario(Long questionarioId);
	Collection<Pesquisa> findToList(Long empresaId, int page, int pagingSize, String questionarioTitulo, Boolean questionarioLiberado);
	Long getIdByQuestionario(Long questionarioId);
	boolean verificaEmpresaDoQuestionario(Long pesquisaId, Long empresaId);
	Integer getCount(Long empresaId, String questionarioTitulo);
	void removerPesquisaDoQuestionario(Long questionarioId);
	boolean existePesquisaParaSerRespondida(String colaboradorCodigoAC,	Long empresaId);
}