package com.fortes.rh.web.action.importacao;

import java.io.IOException;

import com.fortes.model.type.File;
import com.fortes.rh.business.importacao.ImportacaoColaboradorManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupport;

public class ImportacaoAction extends MyActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private ImportacaoColaboradorManager importacaoColaboradorManager;
	
	private File arquivo;
	
	public String prepareImportarAfastamentos()
	{
		return SUCCESS;
	}
	
	public String importarAfastamentos()
	{
		if(!arquivo.getName().toLowerCase().contains(".csv"))
		{
			addActionError("Formato do arquivo invalido.");
			return INPUT;
		}
			
		java.io.File arquivoCriado = ArquivoUtil.salvaArquivo(null, arquivo, false);
		
		try {
		
			if (arquivoCriado == null)
				throw new Exception("Erro ao salvar o arquivo de importação no servidor.");
		
			colaboradorAfastamentoManager.importarCSV(arquivoCriado, getEmpresaSistema());
			
		} catch (IOException e) {
			e.printStackTrace();
			addActionError("Erro na leitura do arquivo de importação.");
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao executar a importação, verifique o formato e o conteúdo do arquivo CSV.");
			return INPUT;
		}
		
		addMensagensAfastamentos();
		
		return SUCCESS;
	}

	private void addMensagensAfastamentos() {
		addActionMessage("Importação concluída. ");
		addActionMessage(colaboradorAfastamentoManager.getCountAfastamentosImportados() + " Afastamentos de Colaboradores foram importados. ");
		addActionMessage(colaboradorAfastamentoManager.getCountTiposAfastamentosCriados() + " novos Motivos de Afastamento foram criados. ");
	}
	
	public String prepareImportarColaboradorDadosPessoais()
	{
		return SUCCESS;
	}
	public String importarColaboradorDadosPessoais()
	{
		java.io.File arquivoCriado = ArquivoUtil.salvaArquivo(null, arquivo, false);
		
		try {
		
			if (arquivoCriado == null)
				throw new Exception("Erro ao salvar o arquivo de importação no servidor.");
		
			importacaoColaboradorManager.importarDadosPessoaisByCpf(arquivoCriado, getEmpresaSistema());
			
			addActionMessage("Importação concluída. ");
			addActionMessage(importacaoColaboradorManager.getCountColaboradoresImportados() + " Colaboradores foram atualizados. ");
			
			if (importacaoColaboradorManager.getCountColaboradoresNaoEncontrados() > 0)
				addActionMessage(importacaoColaboradorManager.getCountColaboradoresNaoEncontrados() + " não foram encontrados: " + importacaoColaboradorManager.getCpfsNaoEncontrados());
			
			if (importacaoColaboradorManager.getCountColaboradoresSemCpf() > 0)
				addActionMessage(importacaoColaboradorManager.getCountColaboradoresSemCpf() + " registros ignorados por não possuírem CPF.");
			
			return SUCCESS;
			
		} catch (IOException e) {
			e.printStackTrace();
			addActionError("Erro na leitura do arquivo de importação.");
			addActionError("Exceção detalhada: " + e.getMessage());
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao executar a importação.");
			addActionError("Exceção detalhada: " + e.getMessage());
			return INPUT;
		}
		
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager) {
		this.colaboradorAfastamentoManager = colaboradorAfastamentoManager;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public void setImportacaoColaboradorManager(ImportacaoColaboradorManager importacaoColaboradorManager) {
		this.importacaoColaboradorManager = importacaoColaboradorManager;
	}
	
}