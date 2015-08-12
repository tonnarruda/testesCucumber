package com.fortes.rh.business.sesmt;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.relatorio.ColaboradorAfastamentoMatriz;
import com.fortes.security.auditoria.Modulo;

@Modulo("Afastamento do Colaborador")
public interface ColaboradorAfastamentoManager extends GenericManager<ColaboradorAfastamento>
{
	Integer getCount(Long empresaId, String matriculaBusca, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento);
	Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, String matriculaBusca, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, String[] ordenarPor, boolean isListagemColaboradorAfastamento, char afastadoPeloINSS) throws Exception;
	Collection<ColaboradorAfastamento> findRelatorioAfastamentos(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, String[] ordenarPor, char afastadoPeloINSS) throws ColecaoVaziaException;
	Collection<ColaboradorAfastamento> findRelatorioResumoAfastamentos(Long empresaId, String[] estabelecimentosCheck, String[] areasCheck, String[] motivosCheck, ColaboradorAfastamento colaboradorAfastamento) throws ColecaoVaziaException;
	void importarCSV(File arquivo, Map<String, Long> afastamentos, Empresa empresa) throws Exception;
	Collection<ColaboradorAfastamento> carregarCSV(File arquivo) throws Exception;
	Integer getCountAfastamentosImportados();
	Integer getCountTiposAfastamentosCriados();
	Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId);
	Collection<DataGrafico> findQtdCatsPorDiaSemana(Long empresaId, Date dataIni, Date dataFim);
	Integer findQtdAfastamentosInss(Long empresaId, Date dataIni, Date dataFim, boolean inss);
	Collection<ColaboradorAfastamentoMatriz> montaMatrizResumo(Long empresaId, String[] estabelecimentosCheck, String[] areasCheck, String[] motivosCheck, ColaboradorAfastamento colaboradorAfastamento, char ordenarPor, char totalizarDiasPor, boolean agruparPorArea) throws Exception;
	Collection<Absenteismo> countAfastamentosByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> afastamentosIds);
	ColaboradorAfastamento findByColaboradorAfastamentoId(Long colaboradorAfastamentoId);
	boolean possuiAfastamentoNestePeriodo(ColaboradorAfastamento colaboradorAfastamento, boolean isUpdate);
}