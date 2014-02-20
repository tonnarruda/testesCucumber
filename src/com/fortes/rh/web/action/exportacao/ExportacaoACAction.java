package com.fortes.rh.web.action.exportacao;

import java.util.Collection;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.web.action.MyActionSupport;

@SuppressWarnings("serial")
public class ExportacaoACAction extends MyActionSupport
{
	private static final String INDEX = "index";
	
	private EmpresaManager empresaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	
	private Collection<Empresa> empresas;
	
	private Long empresaId;
	
	public String prepareExportarAC()
	{
		empresas = empresaManager.findTodasEmpresas();
		return INDEX;
	}

	public String exportarAC()
	{
		try
		{
			verificaHistoricosPorIndice();
			prepareExportarEmpresaAC();
		}
		catch (FortesException e)
		{
			addActionWarning(e.getMessage());
			e.printStackTrace();
			
			if (e instanceof ExisteHistoricoIndiceException)
				return prepareExportarAC();
		}
			
		return prepareExportarAC();
	}
	
	private void verificaHistoricosPorIndice() throws ExisteHistoricoIndiceException
	{
		
		if (historicoColaboradorManager.existeHistoricoPorIndice(empresaId))
		{
			throw new ExisteHistoricoIndiceException("Existem históricos de colaboradores por índice no RH.<br />Em empresas integradas apenas o AC Pessoal controla os índices.<br />Não é possível prosseguir.");
		}
		else if (faixaSalarialHistoricoManager.existeHistoricoPorIndice(empresaId))
		{
			throw new ExisteHistoricoIndiceException("Existem históricos de faixas salariais por índice no RH.<br />Em empresas integradas apenas o AC Pessoal controla os índices.<br />Não é possível prosseguir.");
		}
	}
	
	private void prepareExportarEmpresaAC()
	{
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public void setHistoricoColaboradorManager(
			HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setFaixaSalarialHistoricoManager(
			FaixaSalarialHistoricoManager faixaSalarialHistoricoManager) {
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}
	
	class ExisteHistoricoIndiceException extends FortesException {
		public ExisteHistoricoIndiceException(String msg) {
			super(msg);
		}
	}
}
