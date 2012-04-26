package com.fortes.rh.web.action.importacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fortes.model.type.File;
import com.fortes.rh.business.importacao.ImportacaoColaboradorManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupport;

public class ImportacaoAction extends MyActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private ImportacaoColaboradorManager importacaoColaboradorManager;
	private AfastamentoManager afastamentoManager;
	
	private File arquivo;
	
	private String nomeArquivo;
	
	List<String> motivos = new ArrayList<String>();
	Collection<Afastamento> afastamentos;
	
	public String prepareImportarAfastamentos()
	{
		return SUCCESS;
	}
	
	public String carregarAfastamentos()
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
		
			Collection<ColaboradorAfastamento> colaboradorAfastamentos = colaboradorAfastamentoManager.carregarCSV(arquivoCriado);
			for (ColaboradorAfastamento colaboradorAfastamento : colaboradorAfastamentos) {
				if (!motivos.contains(colaboradorAfastamento.getAfastamento().getDescricao()))
					motivos.add(colaboradorAfastamento.getAfastamento().getDescricao());
			}
			
			afastamentos = afastamentoManager.findToList(new String[]{"id","descricao"}, new String[]{"id","descricao"}, new String[]{"descricao"});
			
		} catch (IOException e) {
			e.printStackTrace();
			addActionError("Erro na leitura do arquivo de importação.");
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao executar a importação, verifique o formato e o conteúdo do arquivo CSV.");
			return INPUT;
		}
		
		
		return SUCCESS;
	}
	
	public String importarAfastamentos()
	{
//		try {
//		
//			if (arquivoCriado == null)
//				throw new Exception("Erro ao salvar o arquivo de importação no servidor.");
//		
//			colaboradorAfastamentoManager.importarCSV(arquivoCriado, getEmpresaSistema());
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			addActionError("Erro na leitura do arquivo de importação.");
//			return INPUT;
//		} catch (Exception e) {
//			e.printStackTrace();
//			addActionError("Erro ao executar a importação, verifique o formato e o conteúdo do arquivo CSV.");
//			return INPUT;
//		}
//		
//		addMensagensAfastamentos();
		
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

	
	public String getNomeArquivo()
	{
		return nomeArquivo;
	}

	
	public void setNomeArquivo(String nomeArquivo)
	{
		this.nomeArquivo = nomeArquivo;
	}

	
	public List<String> getMotivos()
	{
		return motivos;
	}

	
	public void setMotivos(List<String> motivos)
	{
		this.motivos = motivos;
	}

	
	public Collection<Afastamento> getAfastamentos()
	{
		return afastamentos;
	}

	
	public void setAfastamentos(Collection<Afastamento> afastamentos)
	{
		this.afastamentos = afastamentos;
	}

	
	public void setAfastamentoManager(AfastamentoManager afastamentoManager)
	{
		this.afastamentoManager = afastamentoManager;
	}
	
}