package com.fortes.rh.business.importacao;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

@Component
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

			Collection<Colaborador> colaboradoresByCpf = colaboradorManager.findByCpf(colaborador.getPessoal().getCpf(), empresa.getId(), null, null);

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
