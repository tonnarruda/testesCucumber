package com.fortes.rh.web.action.importacao;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.model.type.File;
import com.fortes.rh.business.importacao.ImportacaoColaboradorManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupport;

public class ImportacaoAction extends MyActionSupport
{
	private static final long serialVersionUID = 1L;
	
	@Autowired private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	@Autowired private ImportacaoColaboradorManager importacaoColaboradorManager;
	@Autowired private AfastamentoManager afastamentoManager;
	@Autowired private EpiManager epiManager;
	
	private File arquivo;
	private String pathArquivo;
	
	Map<String, Long> afastamentos = new HashMap<String, Long>();
	Collection<Afastamento> afastamentosRH;
	
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
		
		java.io.File arquivoCriado = ArquivoUtil.salvaArquivo(null, arquivo, true);
		
		
		try {
			if (arquivoCriado == null)
				throw new Exception("Erro ao salvar o arquivo de importação no servidor.");
		
			pathArquivo = arquivoCriado.getAbsolutePath();
			
			Collection<ColaboradorAfastamento> colaboradorAfastamentos = colaboradorAfastamentoManager.carregarCSV(arquivoCriado);
			for (ColaboradorAfastamento colaboradorAfastamento : colaboradorAfastamentos) {
				if (!afastamentos.containsKey(colaboradorAfastamento.getAfastamento().getDescricao()))
					afastamentos.put(colaboradorAfastamento.getAfastamento().getDescricao(), null);
			}
			
			afastamentosRH = afastamentoManager.findToList(new String[]{"id","descricao"}, new String[]{"id","descricao"}, new String[]{"descricao"});
			
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
		try {
			java.io.File arquivoCriado = new java.io.File(pathArquivo);
		
			if (!arquivoCriado.exists())
				throw new Exception("Não foi possível carregar o arquivo enviado.");
		
			colaboradorAfastamentoManager.importarCSV(arquivoCriado, afastamentos, getEmpresaSistema());
			
			arquivoCriado.delete();
			
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
		addActionSuccess("Importação concluída. ");
		addActionSuccess(colaboradorAfastamentoManager.getCountAfastamentosImportados() + " afastamentos de colaboradores foram importados. ");
		addActionSuccess(colaboradorAfastamentoManager.getCountTiposAfastamentosCriados() + " novos motivos de afastamento foram criados. ");
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
	
	public String prepareImportarEPIs()
	{
		return SUCCESS;
	}
	
	public String importarEPIs()
	{
		try {
			epiManager.importarArquivo(arquivo.getFileArchive(), getEmpresaSistema().getId());
			
			addActionSuccess("Importação concluída com sucesso.");
			
		} catch (FortesException e) {
			e.printStackTrace();
			addActionWarning(e.getMessage());
			return INPUT;
		
		} catch (IOException e) {
			e.printStackTrace();
			addActionError("Erro na leitura do arquivo de importação.");
			return INPUT;

		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao executar a importação.");
			return INPUT;
			
		} finally {
			if (arquivo != null && arquivo.getFileArchive() != null)
				arquivo.getFileArchive().delete();
		}
		
		return SUCCESS;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String getPathArquivo() {
		return pathArquivo;
	}

	public void setPathArquivo(String pathArquivo) {
		this.pathArquivo = pathArquivo;
	}

	public Collection<Afastamento> getAfastamentosRH() {
		return afastamentosRH;
	}

	public void setAfastamentosRH(Collection<Afastamento> afastamentosRH) {
		this.afastamentosRH = afastamentosRH;
	}

	public void setAfastamentos(Map<String, Long> afastamentos) {
		this.afastamentos = afastamentos;
	}

	public Map<String, Long> getAfastamentos() {
		return afastamentos;
	}
}