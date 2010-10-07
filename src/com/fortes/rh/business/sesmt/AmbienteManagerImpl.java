package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.model.sesmt.relatorio.Ltcat;
import com.fortes.rh.model.sesmt.relatorio.Ppra;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatCabecalho;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class AmbienteManagerImpl extends GenericManagerImpl<Ambiente, AmbienteDao> implements AmbienteManager
{
	private HistoricoAmbienteManager historicoAmbienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private FuncaoManager funcaoManager;
	private EpcManager epcManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private EpiManager epiManager;
	
	private Collection<PpraLtcatRelatorio> relatorios = null;
	
	private boolean exibirPpra; 
	private boolean exibirLtcat;
	
	private Collection<Ambiente> getAmbientes(String[] ambienteCheck, Date data) 
	{
		Collection<Long> ambienteIds = LongUtil.arrayStringToCollectionLong(ambienteCheck);
		return getDao().findByIds(ambienteIds, data);
	}
	
	public Collection<PpraLtcatRelatorio> montaRelatorioPpraLtcat(Empresa empresa, Long estabelecimentoId, Date data, String[] ambienteCheck, boolean gerarPpra, boolean gerarLtcat) throws ColecaoVaziaException 
	{
		exibirPpra = gerarPpra;
		exibirLtcat = gerarLtcat;
		
		Estabelecimento estabelecimento = estabelecimentoManager.findById(estabelecimentoId);
		
		relatorios = new ArrayList<PpraLtcatRelatorio>();
		
		Collection<Ambiente> ambientes = getAmbientes(ambienteCheck,data);
		
		if (ambientes.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
		
		for (Ambiente ambiente : ambientes)
		{
			relatorios.add(this.populaRelatorioPorAmbiente(empresa, estabelecimento, ambiente, data));
		}
		
		return relatorios;
	}
	
	private PpraLtcatRelatorio populaRelatorioPorAmbiente(Empresa empresa, Estabelecimento estabelecimento, Ambiente ambiente, Date data) 
	{
		Ppra ppra = new Ppra();
		Ltcat ltcat = new Ltcat();
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho(empresa, estabelecimento, ambiente.getNome(), ambiente.getHistoricoAtual().getDescricao());
		PpraLtcatRelatorio ppraLtcatRelatorio = new PpraLtcatRelatorio(cabecalho, ppra, ltcat, exibirPpra, exibirLtcat);
		
		cabecalho.setQtdHomens(getDao().getQtdColaboradorByAmbiente(ambiente.getId(), data, Sexo.MASCULINO));
		cabecalho.setQtdMulheres(getDao().getQtdColaboradorByAmbiente(ambiente.getId(), data, Sexo.FEMININO));
		
		ppraLtcatRelatorio.setTempoExposicao(ambiente.getHistoricoAtual().getTempoExposicao());
		
		Collection<Funcao> funcoesDoAmbiente = funcaoManager.findFuncoesDoAmbiente(ambiente.getId(), data);
		ppraLtcatRelatorio.formataFuncoes(funcoesDoAmbiente);
		
		Collection<RiscoMedicaoRisco> riscosDoAmbiente = riscoMedicaoRiscoManager.findMedicoesDeRiscosDoAmbiente(ambiente.getId(), data);
		ppraLtcatRelatorio.formataRiscosPpra(riscosDoAmbiente);
		ppraLtcatRelatorio.formataRiscosLtcat(riscosDoAmbiente);
		
		Collection<Epc> epcsDoAmbiente = epcManager.findEpcsDoAmbiente(ambiente.getId(), data);
		ppraLtcatRelatorio.formataEpcs(epcsDoAmbiente);
		
		Collection<Epi> episDoAmbiente = epiManager.findEpisDoAmbiente(ambiente.getId(), data); 
		ppraLtcatRelatorio.formataEpis(episDoAmbiente);
		
		return ppraLtcatRelatorio;
	}
	
	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public Collection<Ambiente> findAmbientes(Long empresaId)
	{
		return getDao().findAmbientes(0, 0, empresaId);
	}

	public Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId)
	{
		return getDao().findAmbientes(page, pagingSize, empresaId);
	}

	public void saveAmbienteHistorico(Ambiente ambiente, HistoricoAmbiente historicoAmbiente, String[] riscoChecks, String[] epcEficazChecks, String[] epcCheck) throws Exception
	{
		save(ambiente);

		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteManager.save(historicoAmbiente, riscoChecks, epcEficazChecks, epcCheck);
	}
	
	public void removeCascade(Long id) throws Exception 
	{
		//O hibernate gerencia a remoção dos relacionamentos.			
		Ambiente ambiente = findById(id);
		getDao().remove(ambiente);
	}
	
	public Collection<Ambiente> findByEstabelecimento(Long estabelecimentoId)
	{
		return getDao().findByEstabelecimento(estabelecimentoId);
	}

	public Collection<Ambiente> findByEmpresa(Long empresaId)
	{
		return find(new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"nome"});
	}

	public Collection<CheckBox> getAmbientes(Empresa empresa) throws Exception
	{
		return CheckListBoxUtil.populaCheckListBox( find(new String[]{"empresa.id"},new Object[]{empresa.getId()}, new String[]{"nome"}), "getId", "getNome");
	}

	public Collection<Long> getIdsAmbientes(Collection<HistoricoColaborador> historicosColaborador)
	{
		Collection<Long> idAmbientes = new HashSet<Long>();

		for (HistoricoColaborador historicoColaborador : historicosColaborador)
		{
			if(historicoColaborador.getAmbiente()!=null && !idAmbientes.contains(historicoColaborador.getAmbiente().getId()))
				idAmbientes.add(historicoColaborador.getAmbiente().getId());
		}

		return idAmbientes;
	}

	public Ambiente findByIdProjection(Long ambienteId)
	{
		return getDao().findByIdProjection(ambienteId);
	}

	public Collection<CheckBox> populaCheckBox(Long estabelecimentoId) 
	{
		try
		{
			Collection<Ambiente> ambientes = getDao().findByEstabelecimento(estabelecimentoId);
			return CheckListBoxUtil.populaCheckListBox(ambientes, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<CheckBox>();
		}
	}

	public Collection<Ambiente> findByIds(Collection<Long> ambienteIds, Date data) 
	{
		return getDao().findByIds(ambienteIds, data);
	}

	public int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo) {
		return getDao().getQtdColaboradorByAmbiente(ambienteId, data, sexo);
	}
	
	public void setFuncaoManager(FuncaoManager funcaoManager) {
		this.funcaoManager = funcaoManager;
	}
	public void setEpcManager(EpcManager epcManager) {
		this.epcManager = epcManager;
	}
	public void setRiscoMedicaoRiscoManager(
			RiscoMedicaoRiscoManager riscoMedicaoRiscoManager) {
		this.riscoMedicaoRiscoManager = riscoMedicaoRiscoManager;
	}
	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}
	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}
	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager)
	{
		this.historicoAmbienteManager = historicoAmbienteManager;
	}
}