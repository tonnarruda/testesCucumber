package com.fortes.rh.web.action.exportacao;

import java.util.Collection;

import org.hibernate.exception.ConstraintViolationException;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;

@SuppressWarnings("serial")
public class ExportacaoACAction extends MyActionSupport
{
	private static final String INDEX = "index";
	private static final String EMPRESA = "empresa";
	
	private EmpresaManager empresaManager;
	private GrupoACManager grupoACManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private EstabelecimentoManager estabelecimentoManager;
	
	private Collection<Empresa> empresas;
	private Collection<GrupoAC> gruposACs;
	private Collection<Estabelecimento> estabelecimentos;
	
	private Empresa empresa;
	
	private Long empresaId;
	private String grupoAC;
	private String codigoAC;
	
	public String prepareExportarAC()
	{
		empresas = empresaManager.findTodasEmpresas();
		gruposACs = grupoACManager.findAll(new String[]{"codigo"});
		
		return INDEX;
	}

	public String exportarAC()
	{
		try
		{
			verificaHistoricosPorIndice();
			verificaEmpresaAC();
			verificaEstabelecimentoAC();
			
			addActionSuccess("Exportação concluída com sucesso.");
		}
		catch (FortesException e)
		{
			addActionWarning(e.getMessage());
			e.printStackTrace();
			
			if (e instanceof ExisteHistoricoIndiceException)
				return prepareExportarAC();

			else if (e instanceof EmpresaSemCodigoACException)
				return prepareExportarEmpresaAC();
		}
		catch (Exception e)
		{
			addActionWarning(e.getMessage());
			e.printStackTrace();
			return prepareExportarAC();
		}

		return prepareExportarAC();
	}
	
	private void verificaHistoricosPorIndice() throws ExisteHistoricoIndiceException
	{
		
		if (historicoColaboradorManager.existeHistoricoPorIndice(empresaId))
		{
			throw new ExisteHistoricoIndiceException("Existem históricos de colaboradores por índice no RH.<br />Em empresas integradas, apenas o AC Pessoal controla os índices.<br />Não é possível prosseguir.");
		}
		else if (faixaSalarialHistoricoManager.existeHistoricoPorIndice(empresaId))
		{
			throw new ExisteHistoricoIndiceException("Existem históricos de faixas salariais por índice no RH.<br />Em empresas integradas, apenas o AC Pessoal controla os índices.<br />Não é possível prosseguir.");
		}
	}
	
	private void verificaEmpresaAC() throws EmpresaSemCodigoACException
	{
		empresa = empresaManager.findByIdProjection(empresaId);
		
		if (StringUtil.isBlank(empresa.getCodigoAC()))
			throw new EmpresaSemCodigoACException("A empresa do RH selecionada ainda não foi vinculada a uma empresa criada no AC Pessoal");
	}
	
	public String prepareExportarEmpresaAC()
	{
		return EMPRESA;
	}
	
	public String exportarEmpresaAC()
	{
		try 
		{
			empresaManager.updateCodigoGrupoAC(empresaId, codigoAC, grupoAC);
		} 
		catch (ConstraintViolationException e) 
		{
			addActionWarning("Existe outra empresa no mesmo grupo AC usando o código AC informado.");
			e.printStackTrace();
			return EMPRESA;
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível atualizar a empresa com o código AC informado.");
			e.printStackTrace();
			return EMPRESA;
		}
		
		return SUCCESS;
	}
	
	private void exportarEstabelecimentoAC()
	{
		estabelecimentos = estabelecimentoManager.findSemCodigoAC(empresaId);
	}
	
	class ExisteHistoricoIndiceException extends FortesException 
	{
		public ExisteHistoricoIndiceException(String msg) 
		{
			super(msg);
		}
	}
	
	class EmpresaSemCodigoACException extends FortesException 
	{
		public EmpresaSemCodigoACException(String msg) 
		{
			super(msg);
		}
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

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager) {
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}

	public Collection<GrupoAC> getGruposACs() {
		return gruposACs;
	}

	public void setGruposACs(Collection<GrupoAC> gruposACs) {
		this.gruposACs = gruposACs;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public String getCodigoAC() {
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC) {
		this.codigoAC = codigoAC;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(Collection<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}
}
