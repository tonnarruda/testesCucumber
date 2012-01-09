package com.fortes.rh.business.sesmt;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public interface ColaboradorAfastamentoManager extends GenericManager<ColaboradorAfastamento>
{
	Integer getCount(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento);
	Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, String ascOuDesc, boolean ordenaColaboradorPorNome, boolean ordenaPorCid, char afastadoPeloINSS) throws Exception;
	Collection<ColaboradorAfastamento> findRelatorioAfastamentos(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, boolean ordenaColaboradorPorNome, boolean ordenaPorCid, char afastadoPeloINSS) throws ColecaoVaziaException;
	void importarCSV(File arquivo, Empresa empresa) throws Exception;
	Integer getCountAfastamentosImportados();
	Integer getCountTiposAfastamentosCriados();
	Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId);
	Collection<DataGrafico> findQtdCatsPorDiaSemana(Long empresaId, Date dataIni, Date dataFim);
	Integer findQtdAfastamentosInss(Long empresaId, Date dataIni, Date dataFim, boolean inss);
}