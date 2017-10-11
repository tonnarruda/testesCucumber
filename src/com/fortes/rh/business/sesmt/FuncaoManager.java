package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.web.tags.CheckBox;
public interface FuncaoManager extends GenericManager<Funcao>
{
	Integer getCount(Long empresaId, String funcaoNome);
	Collection<Funcao> findByEmpresa(int page, int pagingSize, Long empresaId, String funcaoNome);
	Collection<Funcao> findByEmpresa(Long empresaId);
	public Collection<Long> getIdsFuncoes(Collection<HistoricoColaborador> historicosColaborador);
	Funcao findByIdProjection(Long funcaoId);
	Collection<Funcao> findFuncoesDoAmbiente(Long ambienteId, Date data);
	Collection<Long> findFuncaoAtualDosColaboradores(Date data, Long estabelecimentoId);
	Collection<String> findColaboradoresSemFuncao(Date data, Long estabelecimentoId);
	Collection<QtdPorFuncaoRelatorio> getQtdColaboradorByFuncao(Long empresaId, Long estabelecimentoId, Date data, char tipoAtivo);
	void removeFuncao(Funcao funcao);
	Collection<CheckBox> populaCheckBox();
	void atualizaNomeUltimoHistorico(Long funcaoId);
}