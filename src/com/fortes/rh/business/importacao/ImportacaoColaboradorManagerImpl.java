package com.fortes.rh.business.importacao;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

public class ImportacaoColaboradorManagerImpl implements ImportacaoColaboradorManager {

	private ColaboradorManager colaboradorManager;

	private Integer countColaboradoresImportados = 0;
	private Integer countColaboradoresNaoEncontrados = 0;
	private Integer countColaboradoresSemCpf = 0;
	private String cpfsNaoEncontrados = "";

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void importarDadosPessoaisByCpf(File arquivo, Empresa empresa) throws Exception {
		
		countColaboradoresImportados = 0;
		countColaboradoresNaoEncontrados = 0;
		countColaboradoresSemCpf = 0;
		cpfsNaoEncontrados = "";

		ImportacaoCSVUtil importacaoCSVUtil = new ImportacaoCSVUtil();
		importacaoCSVUtil.importarCSV(arquivo, OpcaoImportacao.COLABORADORES_DADOS_PESSOAIS);

		Collection<Colaborador> colaboradors = importacaoCSVUtil.getColaboradores();

		for (Colaborador colaborador : colaboradors) {

			if (StringUtils.isBlank(colaborador.getPessoal().getCpf())) {
				countColaboradoresSemCpf++;
				continue;
			}

			Collection<Colaborador> colaboradoresByCpf = colaboradorManager.findByCpf(colaborador.getPessoal().getCpf(), empresa.getId());

			if (colaboradoresByCpf != null && !colaboradoresByCpf.isEmpty()) {
				boolean b = colaboradorManager.updateInfoPessoaisByCpf(colaborador, empresa.getId());
				countColaboradoresImportados += colaboradoresByCpf.size();
			} else {
				cpfsNaoEncontrados += " " + colaborador.getPessoal().getCpf();
				countColaboradoresNaoEncontrados++;
			}
		}
	}

	public Integer getCountColaboradoresSemCpf() {
		return countColaboradoresSemCpf;
	}

	public Integer getCountColaboradoresImportados() {
		return countColaboradoresImportados;
	}

	public Integer getCountColaboradoresNaoEncontrados() {
		return countColaboradoresNaoEncontrados;
	}

	public String getCpfsNaoEncontrados() {
		return cpfsNaoEncontrados;
	}
}
