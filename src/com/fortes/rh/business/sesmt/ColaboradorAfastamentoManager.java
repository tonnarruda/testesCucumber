package com.fortes.rh.business.sesmt;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public interface ColaboradorAfastamentoManager extends GenericManager<ColaboradorAfastamento>
{
	Integer getCount(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento);
	Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento, String ascOuDesc, boolean ordenaColaboradorPorNome, char afastadoPeloINSS) throws Exception;
	Collection<ColaboradorAfastamento> findRelatorioAfastamentos(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento, boolean ordenaColaboradorPorNome, char afastadoPeloINSS) throws ColecaoVaziaException;
	public void importarCSV(File arquivo, Empresa empresa) throws IOException;
	public Integer getCountAfastamentosImportados();
	public Integer getCountTiposAfastamentosCriados();
	Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId);
}