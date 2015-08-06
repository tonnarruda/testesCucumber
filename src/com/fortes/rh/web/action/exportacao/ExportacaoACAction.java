package com.fortes.rh.web.action.exportacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;

@SuppressWarnings("serial")
public class ExportacaoACAction extends MyActionSupport
{
	private static final String INDEX = "index";
	private static final String EMPRESA = "empresa";
	private static final String ESTABELECIMENTO = "estabelecimento";
	
	private EmpresaManager empresaManager;
	private GrupoACManager grupoACManager;
	private ColaboradorManager colaboradorManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private FaixaSalarialManager faixaSalarialManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private OcorrenciaManager ocorrenciaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private AcPessoalClientColaborador acPessoalClientColaborador;
	
	private List<String> codigosACs;
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
			empresa = empresaManager.findById(empresaId);
			empresa.setAcIntegra(true);
			
			atualizarHistoricosParaPendente();
			verificarHistoricosPorIndice();
			verificarEmpresaAC();
			verificarEstabelecimentoAC();
			atualizarDataSolicitacaoDesligamentoAC();
			exportarAreasOrganizacionaisAC();
			exportarFaixasSalariaisAC();
			exportarColaboradoresAC();
			exportarOcorrenciasAC();
			
			if(registrosNaoForamConfirmadosNoAC())
				addActionSuccess("Primeira fase da exportação concluída com sucesso.<br />Confirme todos os registros pendentes no AC Pessoal referentes à integração com RH." +
						"<br />Após confirmar todos os registros no AC Pessoal, clique no botão \"Exportar\" novamente.");
			else
			{
				exportarHistoricosColaboardoresAC();
				exportarColaboradoresOcorrenciasAc();
				exportarDesligamentos();
				addActionSuccess("Exportação concluída com sucesso.<br />Confirme os registros pendentes no AC Pessoal referentes à integração com RH." +
						"<br />Finalize a exportação marcando a integração da empresa exportada no sistema RH .");
			}
		}
		catch (FortesException e)
		{
			addActionWarning(e.getMessage());
			e.printStackTrace();
			
			if (e instanceof ExisteHistoricoIndiceException)
				return prepareExportarAC();

			else if (e instanceof EmpresaSemCodigoACException)
				return prepareExportarEmpresaAC();
			
			else if (e instanceof EstabelecimentosSemCodigoACException)
				return prepareExportarEstabelecimentoAC();

		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
			return prepareExportarAC();
		}

		return prepareExportarAC();
	}

	private void exportarColaboradoresOcorrenciasAc() throws Exception, IntegraACException 
	{
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = colaboradorOcorrenciaManager.findByEmpresaId(empresaId);
		
		for (ColaboradorOcorrencia colaboradorOcorrencia : colaboradorOcorrencias) {
			colaboradorOcorrencia.getOcorrencia().setIntegraAC(true);
			
			if(colaboradorOcorrencia.getProvidencia() != null && colaboradorOcorrencia.getProvidencia().getId() == null)
				colaboradorOcorrencia.setProvidencia(null);
			
			colaboradorOcorrenciaManager.saveColaboradorOcorrencia(colaboradorOcorrencia, empresa);
		}
	}

	private void atualizarHistoricosParaPendente() {
		Collection<Colaborador> colaboradoresIds = colaboradorManager.findSemCodigoAC(empresaId);
		
		if(colaboradoresIds != null && colaboradoresIds.size() > 0)
			historicoColaboradorManager.updateStatusAcByEmpresaAndStatusAtual(StatusRetornoAC.PENDENTE, StatusRetornoAC.CONFIRMADO, new CollectionUtil<Colaborador>().convertCollectionToArrayIds(colaboradoresIds));
	}
	
	private void exportarDesligamentos(){
		Collection<Colaborador> colaboradores = colaboradorManager.listColaboradorComDataSolDesligamentoAC(empresaId, true);

		for (Colaborador colaborador : colaboradores) {
			Collection<HistoricoColaborador> historicosColaborador = new ArrayList<HistoricoColaborador>();
			HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());
			historicoColaborador.setDataSolicitacaoDesligamento(colaborador.getDataDesligamento());
			historicoColaborador.setObsACPessoal(colaborador.getObservacaoDemissao());
			historicosColaborador.add(historicoColaborador);
			try {
				acPessoalClientColaborador.solicitacaoDesligamentoAc(historicosColaborador, empresa);
			} catch (IntegraACException e) {
				e.printStackTrace();
			} 
		}
	} 

	private boolean registrosNaoForamConfirmadosNoAC()
	{
		boolean existeHistoricoColaboradorPendenteNoAC = historicoColaboradorManager.findPendenciasByHistoricoColaborador(empresaId, new Integer[]{StatusRetornoAC.AGUARDANDO}).size() > 0;
		boolean existeHistoricoFaixaPendenteNoAC = faixaSalarialHistoricoManager.findPendenciasByFaixaSalarialHistorico(empresaId).size() > 0;
		return existeHistoricoColaboradorPendenteNoAC || existeHistoricoFaixaPendenteNoAC;
	}
	
	private void verificarHistoricosPorIndice() throws ExisteHistoricoIndiceException
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
	
	private void verificarEmpresaAC() throws EmpresaSemCodigoACException
	{
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
	
	private void verificarEstabelecimentoAC() throws EstabelecimentosSemCodigoACException
	{
		estabelecimentos = estabelecimentoManager.findSemCodigoAC(empresaId);
		
		if (!estabelecimentos.isEmpty())
			throw new EstabelecimentosSemCodigoACException("Existe estabelecimento que ainda não foi vinculado a um estabelecimento do AC Pessoal.<br />Em empresas integradas, apenas o AC Pessoal controla os estabelecimentos.");
	}
	
	private void atualizarDataSolicitacaoDesligamentoAC(){
		colaboradorManager.setDataSolicitacaoDesligamentoACByDataDesligamento(empresaId);
	}
	
	public String prepareExportarEstabelecimentoAC()
	{
		return ESTABELECIMENTO;
	}
	
	public String exportarEstabelecimentoAC()
	{
		estabelecimentos = estabelecimentoManager.findSemCodigoAC(empresaId);
		Estabelecimento estabelecimento = null;
		
		try 
		{
			for (int i = 0; i < estabelecimentos.size(); i++) 
			{
				estabelecimento = estabelecimentos.toArray(new Estabelecimento[estabelecimentos.size()])[i];
				estabelecimentoManager.updateCodigoAC(estabelecimento.getId(), codigosACs.get(i));
			}
		} 
		catch (ConstraintViolationException e) 
		{
			addActionWarning("Existe outro estabelecimento usando o código AC informado.");
			e.printStackTrace();
			return ESTABELECIMENTO;
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível atualizar os estabelecimentos com os códigos AC informados.");
			e.printStackTrace();
			return ESTABELECIMENTO;
		}
		
		return SUCCESS;
	}
	
	private void exportarAreasOrganizacionaisAC() throws Exception
	{
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.find(new String[]{"empresa.id"}, new Object[]{empresaId});
		
		areas = areaOrganizacionalManager.ordenarAreasHierarquicamente(areas, null, 1);
		
		int maxNiveisRH = ((AreaOrganizacional) areas.toArray()[areas.size() - 1]).getNivelHierarquico();
		int maxNiveisAC = areaOrganizacionalManager.getMascaraLotacoesAC(empresa).split("\\.").length;
		
		if (maxNiveisRH > maxNiveisAC){
			StringBuffer msgPassos = new StringBuffer(); 	
			msgPassos.append("<ol>");
			msgPassos.append("  <li>Desmarque a integração no AC Pessoal;</li>");
			msgPassos.append("  <li>Altere a máscara das lotações para "+maxNiveisRH+" níveis no AC Pessoal;</li>");
			msgPassos.append("  <li>Marque novamente a integração no AC Pessoal;</li>");
			msgPassos.append("</ol>");
			
			throw new AreaNaoInseridaACException("A máscara de lotações no AC Pessoal não é compatível com a quantidade máxima de níveis da hierarquia de áreas organizacionais.<br />Quantidade de níveis da hierarquia de áreas organizacionais: "+maxNiveisRH + msgPassos);
		}
		
		for (AreaOrganizacional areaOrganizacional : areas) 
		{
			try 
			{
				if (areaOrganizacional.getCodigoAC() == null)
					areaOrganizacionalManager.insertLotacaoAC(areaOrganizacional, empresa);
			} 
			catch (Exception e) {
				throw new AreaNaoInseridaACException("Não foi possível inserir a área organizacional " + areaOrganizacional.getNome() + " no AC Pessoal");
			}
		}
	}
	
	private void exportarFaixasSalariaisAC() throws Exception
	{
		Collection<FaixaSalarial> faixaSalariais = faixaSalarialManager.findComHistoricoAtualByEmpresa(empresaId, true);
		FaixaSalarialHistorico faixaSalarialHistoricoAtual;
		
		String nomeNoAC = "";
		Collection<String> nomesInseridosNoAC = new ArrayList<String>();
		
		for (FaixaSalarial faixaSalarial : faixaSalariais)
		{
			faixaSalarialHistoricoAtual= faixaSalarialHistoricoManager.findById(faixaSalarial.getFaixaSalarialHistoricoAtual().getId());
			faixaSalarial = faixaSalarialManager.findById(faixaSalarial.getId());
			
			nomeNoAC = montaNomeParaAC(nomesInseridosNoAC, (StringUtil.subStr(faixaSalarial.getCargo().getNome(), 24)+ " " +  StringUtil.subStr(faixaSalarial.getNome(), 5)), 1);
			faixaSalarial.setNomeACPessoal(nomeNoAC);
			
			faixaSalarialManager.saveFaixaSalarial(faixaSalarial, faixaSalarialHistoricoAtual, empresa, new String[]{});

			Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = faixaSalarialHistoricoManager.findAllSelect(faixaSalarial.getId());
			for (FaixaSalarialHistorico faixaSalarialHistorico : faixaSalarialHistoricos)
				if(!faixaSalarialHistoricoAtual.getId().equals(faixaSalarialHistorico.getId()))
					faixaSalarialHistoricoManager.criarFaixaSalarialHistoricoNoAc(faixaSalarialHistorico, empresa);
		}
	}
	
	private void exportarColaboradoresAC() throws Exception
	{
		Collection<Colaborador> colaboradores = colaboradorManager.findByEmpresaAndStatusAC(empresaId, null, null, StatusRetornoAC.PENDENTE, true, false, true, "c.nome");

		//Ver Samuel
		//		Collection<Colaborador> colaboradores = colaboradorManager.findByEmpresaAndStatusAC(empresaId, StatusRetornoAC.PENDENTE, true, SituacaoColaborador.TODOS);
		for (Colaborador colaborador : colaboradores)
		{
			colaborador.getHistoricoColaborador().setAreaOrganizacional(colaborador.getAreaOrganizacional());
			colaborador.getHistoricoColaborador().setEstabelecimento(colaborador.getEstabelecimento());
			colaborador.getHistoricoColaborador().setFaixaSalarial(colaborador.getFaixaSalarial());
			
			colaboradorManager.contratarColaboradorNoAC(colaborador, colaborador.getHistoricoColaborador(), empresa, false);
			
			historicoColaboradorManager.updateStatusAc(StatusRetornoAC.AGUARDANDO, colaborador.getHistoricoColaborador().getId());
		}
	}

	private void exportarOcorrenciasAC() throws Exception
	{
		Collection<Ocorrencia> ocorrencias = ocorrenciaManager.findSemCodigoAC(empresaId, null); 
		for (Ocorrencia ocorrencia : ocorrencias) 
		{
			ocorrencia = ocorrenciaManager.findById(ocorrencia.getId());
			
			ocorrencia.setIntegraAC(true);
			ocorrencia.setEmpresa(empresa);
			ocorrenciaManager.saveOrUpdate(ocorrencia, empresa);
		}
	}

	private void exportarHistoricosColaboardoresAC() throws Exception
	{
		Collection<HistoricoColaborador> historicos = historicoColaboradorManager.findByEmpresaComHistorico(empresaId, StatusRetornoAC.PENDENTE);
		historicoColaboradorManager.saveHistoricoColaboradorNoAc(historicos, empresa);

		if(historicos != null && historicos.size() > 0)
			historicoColaboradorManager.updateStatusAc(StatusRetornoAC.AGUARDANDO, new CollectionUtil<HistoricoColaborador>().convertCollectionToArrayIds(historicos));
	}

	private String montaNomeParaAC(Collection<String> nomesInseridosNoAC, String nomeNoAC, int count)
	{
		if(!nomesInseridosNoAC.contains(nomeNoAC))
		{
			nomesInseridosNoAC.add(nomeNoAC);
			return nomeNoAC;
		}
		else{
			return montaNomeParaAC(nomesInseridosNoAC, StringUtil.subStr(nomeNoAC, 28) + "_" + count, ++count);
		}
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
	
	class EstabelecimentosSemCodigoACException extends FortesException 
	{
		public EstabelecimentosSemCodigoACException(String msg) 
		{
			super(msg);
		}
	}
	
	class AreaNaoInseridaACException extends FortesException 
	{
		public AreaNaoInseridaACException(String msg) 
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

	public List<String> getCodigosACs() {
		return codigosACs;
	}

	public void setCodigosACs(List<String> codigosACs) {
		this.codigosACs = codigosACs;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager) {
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public void setColaboradorOcorrenciaManager(
			ColaboradorOcorrenciaManager colaboradorOcorrenciaManager) {
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}
	
	public void setAcPessoalClientColaborador(
			AcPessoalClientColaborador acPessoalClientColaborador) {
		this.acPessoalClientColaborador = acPessoalClientColaborador;
	}
}
