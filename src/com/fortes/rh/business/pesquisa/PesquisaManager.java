package com.fortes.rh.business.pesquisa;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;

public interface PesquisaManager extends GenericManager<Pesquisa>
{
	Collection<Pesquisa> findToListByEmpresa(Long empresaId, int page, int pagingSize, String questionarioTitulo, char questionarioLiberado);
	void delete(Long pesquisaId, Long empresaId) throws Exception;
	Pesquisa findByIdProjection(Long pesquisaId);
	void verificarEmpresaValida(Long pesquisaId, Long empresaId) throws Exception;
	void update(Pesquisa pesquisa, Questionario questionario, Long empresaId) throws Exception;
	Pesquisa save(Pesquisa pesquisa, Questionario questionario, Empresa empresa) throws Exception;
	Pesquisa findParaSerRespondida(Long id);
	Pesquisa findByQuestionario(Long questionarioId);
	Pesquisa clonarPesquisa(Long pesquisaId, Long[] empresasIds) throws Exception;
	Long getIdByQuestionario(Long questionarioId);
	Integer getCount(Long empresaId, String questionarioTitulo);
	boolean existePesquisaParaSerRespondida(String colaboradorCodigoAC, Long empresaId);
}