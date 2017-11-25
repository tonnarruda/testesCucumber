package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
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
	private ComposicaoSesmtManager composicaoSesmtManager;
	private EmpresaManager empresaManager;
	
	private Collection<PpraLtcatRelatorio> relatorios = null;
	
	private boolean exibirPpra; 
	private boolean exibirLtcat;
	
	public Collection<PpraLtcatRelatorio> montaRelatorioPpraLtcat(Empresa empresa, Estabelecimento estabelecimento, Integer localAmbiente, Date data, String[] ambienteCheck, boolean gerarPpra, boolean gerarLtcat, boolean exibirComposicaoSesmt) throws ColecaoVaziaException 
	{
		this.exibirPpra = gerarPpra;
		this.exibirLtcat = gerarLtcat;
		ComposicaoSesmt composicaoSesmt = null;
		relatorios = new ArrayList<PpraLtcatRelatorio>();
		Collection<Ambiente> ambientes = getDao().findByIds(empresa.getId(), LongUtil.arrayStringToCollectionLong(ambienteCheck), data, estabelecimento.getId(), localAmbiente);
		
		if(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao().equals(localAmbiente)){
			estabelecimento = estabelecimentoManager.findById(estabelecimento.getId());
		}
		boolean isControlaRiscoPorAmbiente = empresaManager.isControlaRiscoPorAmbiente(empresa.getId());
		
		if (ambientes.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
		
		if (exibirComposicaoSesmt)
			composicaoSesmt = composicaoSesmtManager.findByData(empresa.getId(), data);
		
		for (Ambiente ambiente : ambientes)
		{
			Collection<Funcao> funcoesDoAmbiente = funcaoManager.findFuncoesDoAmbiente(ambiente.getId(), data);
			
			if(isControlaRiscoPorAmbiente)//ambiente
			{
				relatorios.add(this.populaRelatorioPorAmbiente(empresa, estabelecimento, ambiente, data, composicaoSesmt, funcoesDoAmbiente));
			}
			else
			{
				for (Funcao funcao : funcoesDoAmbiente) 
					relatorios.add(this.populaRelatorioPorFuncao(empresa, estabelecimento, ambiente, data, composicaoSesmt, funcao));
			}
		}
		
		if(!isControlaRiscoPorAmbiente && relatorios.isEmpty())
			throw new ColecaoVaziaException("Nos históricos dos colaboradores não existem funções vinculadas aos ambientes selecionados no filtro.");
		
		return relatorios;
	}
	
	public int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo){
		return getDao().getQtdColaboradorByAmbiente(ambienteId, data, sexo, null);
	}
	
	private void populaRelatorioPor(Ambiente ambiente, Date data, ComposicaoSesmt composicaoSesmt, PpraLtcatRelatorio ppraLtcatRelatorio) 
	{
		ppraLtcatRelatorio.setComposicaoSesmts(Arrays.asList(composicaoSesmt));
		ppraLtcatRelatorio.setTempoExposicao(ambiente.getHistoricoAtual().getTempoExposicao());
		
		Collection<Epc> epcsDoAmbiente = epcManager.findEpcsDoAmbiente(ambiente.getId(), data);
		ppraLtcatRelatorio.formataEpcs(epcsDoAmbiente);
		
	}
	
	private PpraLtcatRelatorio populaRelatorioPorAmbiente(Empresa empresa, Estabelecimento estabelecimento, Ambiente ambiente, Date data, ComposicaoSesmt composicaoSesmt, Collection<Funcao> funcoesDoAmbiente) 
	{
		Ppra ppra = new Ppra();
		Ltcat ltcat = new Ltcat();
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho(empresa, estabelecimento, ambiente);
		PpraLtcatRelatorio ppraLtcatRelatorio = new PpraLtcatRelatorio(cabecalho, ppra, ltcat, exibirPpra, exibirLtcat);
		
		populaRelatorioPor(ambiente, data, composicaoSesmt, ppraLtcatRelatorio);
		
		Collection<Epi> episDoAmbiente = epiManager.findEpisDoAmbiente(ambiente.getId(), data); 
		ppraLtcatRelatorio.formataEpis(episDoAmbiente);
		
		ppraLtcatRelatorio.formataFuncoes(funcoesDoAmbiente);

		Collection<RiscoMedicaoRisco> riscosDoAmbiente = riscoMedicaoRiscoManager.findMedicoesDeRiscosDoAmbiente(ambiente.getId(), data);
		ppraLtcatRelatorio.formataRiscosPpra(riscosDoAmbiente);
		ppraLtcatRelatorio.formataRiscosLtcat(riscosDoAmbiente);
		
		cabecalho.setQtdHomens(getDao().getQtdColaboradorByAmbiente(ambiente.getId(), data, Sexo.MASCULINO, null));
		cabecalho.setQtdMulheres(getDao().getQtdColaboradorByAmbiente(ambiente.getId(), data, Sexo.FEMININO, null));
		
		return ppraLtcatRelatorio;
	}

	private PpraLtcatRelatorio populaRelatorioPorFuncao(Empresa empresa, Estabelecimento estabelecimento, Ambiente ambiente, Date data, ComposicaoSesmt composicaoSesmt, Funcao funcao) 
	{
		Ppra ppra = new Ppra();
		Ltcat ltcat = new Ltcat();
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho(empresa, estabelecimento, ambiente);
		PpraLtcatRelatorio ppraLtcatRelatorio = new PpraLtcatRelatorio(cabecalho, ppra, ltcat, exibirPpra, exibirLtcat);
		
		populaRelatorioPor(ambiente, data, composicaoSesmt, ppraLtcatRelatorio);

		Collection<Epi> episDaFuncao = epiManager.findByHistoricoFuncao(funcao.getHistoricoAtual().getId()); 
		ppraLtcatRelatorio.formataEpis(episDaFuncao);
		
		ppraLtcatRelatorio.formataFuncoes(Arrays.asList(funcao));

		//TODO Samuel falta implementar por funcao
		Collection<RiscoMedicaoRisco> riscosDaFuncao = riscoMedicaoRiscoManager.findMedicoesDeRiscosDaFuncao(funcao.getId(), data);
		
		ppraLtcatRelatorio.formataRiscosPpra(riscosDaFuncao);
		ppraLtcatRelatorio.formataRiscosLtcat(riscosDaFuncao);
		
		cabecalho.setQtdHomens(getDao().getQtdColaboradorByAmbiente(ambiente.getId(), data, Sexo.MASCULINO, funcao.getId()));
		cabecalho.setQtdMulheres(getDao().getQtdColaboradorByAmbiente(ambiente.getId(), data, Sexo.FEMININO, funcao.getId()));
		
		return ppraLtcatRelatorio;
	}
	
	public Integer getCount(Long empresaId, HistoricoAmbiente historicoAmbiente)
	{
		return getDao().getCount(empresaId, historicoAmbiente);
	}

	public Collection<Ambiente> findAmbientes(Long empresaId)
	{
		return getDao().findAmbientes(0, 0, empresaId, null);
	}

	public Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId, HistoricoAmbiente historicoAmbiente) throws ColecaoVaziaException
	{
		Collection<Ambiente> ambientes = getDao().findAmbientes(page, pagingSize, empresaId, historicoAmbiente);
		if(ambientes.isEmpty())
			throw new ColecaoVaziaException();
		
		return ambientes;
	}

	public void saveAmbienteHistorico(Empresa empresa, HistoricoAmbiente historicoAmbiente, String[] riscoChecks, Collection<RiscoAmbiente> riscosAmbientes, String[] epcCheck) throws Exception
	{
		Ambiente ambiente = new Ambiente(historicoAmbiente.getNomeAmbiente(), empresa);
		save(ambiente);

		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
	}
	
	public void removeCascade(Long ambienteId) throws Exception 
	{
		historicoAmbienteManager.removeByAmbiente(ambienteId);
		getDao().remove(ambienteId);
	}
	
	public Collection<Ambiente> findAmbientesPorEstabelecimento(Long[] estabelecimentoIds, Date data)
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			ambientes = getDao().findAmbientesPorEstabelecimento(estabelecimentoIds, data);
		return ambientes;
	}

	public Collection<Ambiente> findByEmpresa(Long empresaId)
	{
		return find(new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"nome"});
	}

	public Ambiente findByIdProjection(Long ambienteId)
	{
		return getDao().findByIdProjection(ambienteId);
	}
	
	public Collection<CheckBox> populaCheckBox(Long empresaId, Long estabelecimentoId, Integer localAmbiente, Date data) 
	{
		try
		{
			Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
			
			if(estabelecimentoId != null || localAmbiente.equals(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()))
				ambientes = findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data);
			
			return CheckListBoxUtil.populaCheckListBox(ambientes, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<CheckBox>();
		}
	}
	
	public Collection<CheckBox> populaCheckBoxByEstabelecimentos(Long[] estabelecimentoIds) 
	{
		try
		{
			Collection<Ambiente> ambientes = this.findAmbientesPorEstabelecimento(estabelecimentoIds, new Date());
			return CheckListBoxUtil.populaCheckListBox(ambientes, "getId", "getNomeComEstabelecimento", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<CheckBox>();
		}
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId) {
		Collection<Ambiente> ambientesOrigin = getDao().findAllByEmpresa(empresaOrigemId);
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findByEmpresa(empresaDestinoId);
		
		for (Estabelecimento estabelecimento : estabelecimentos) {
			for (Ambiente ambienteOrigin : ambientesOrigin){
				Ambiente ambiente = new Ambiente();
				ambiente.setNome(ambienteOrigin.getNome());
				ambiente.setEmpresa(new Empresa(empresaDestinoId));
				getDao().save(ambiente);

				if(ambienteOrigin.getHistoricoAtual() != null){
					HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente(null, ambienteOrigin.getHistoricoAtual().getData(), ambiente);
					historicoAmbiente.setTempoExposicao(ambienteOrigin.getHistoricoAtual().getTempoExposicao());
					historicoAmbiente.setDescricao(ambienteOrigin.getHistoricoAtual().getDescricao());
					historicoAmbiente.setNomeAmbiente(ambienteOrigin.getHistoricoAtual().getNomeAmbiente());
					historicoAmbiente.setEstabelecimento(estabelecimento);
					historicoAmbienteManager.save(historicoAmbiente);
				}
			}
		}
	}
	
	public void atualizaDadosParaUltimoHistorico(Long ambienteId) {
		getDao().atualizaDadosParaUltimoHistorico(ambienteId);
	}
	
	public void deleteAmbienteSemHistorico() throws Exception {
		getDao().deleteAmbienteSemHistorico();
	}
	
	public Collection<Ambiente> findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(Long empresaId, Long estabelecimentoId, Integer localAmbiente, Date data) {
		return getDao().findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, data);
	}

	public Map<String, Collection<Ambiente>> montaMapAmbientes(Long empresaId, Long estabelecimentoId, String estabelecimentoNome, Date data){
		Map<String, Collection<Ambiente>> ambientes = new LinkedHashMap<String, Collection<Ambiente>>();
		
		Collection<Ambiente> ambientesInternos = this.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao(), data);
		Collection<Ambiente> ambientesExternos = this.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, null, LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao(), data);

		if(ambientesInternos.size() > 0)
			ambientes.put("Ambientes do estabelecimento: " + estabelecimentoNome, ambientesInternos);
		
		if(ambientesExternos.size() > 0)
			ambientes.put("Ambientes de estabelecimentos de terceiros", ambientesExternos);
		
		return ambientes;
	}
	
	public void setFuncaoManager(FuncaoManager funcaoManager) {
		this.funcaoManager = funcaoManager;
	}
	public void setEpcManager(EpcManager epcManager) {
		this.epcManager = epcManager;
	}
	public void setRiscoMedicaoRiscoManager(RiscoMedicaoRiscoManager riscoMedicaoRiscoManager) {
		this.riscoMedicaoRiscoManager = riscoMedicaoRiscoManager;
	}
	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}
	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}
	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager)
	{
		this.historicoAmbienteManager = historicoAmbienteManager;
	}
	public void setComposicaoSesmtManager(ComposicaoSesmtManager composicaoSesmtManager) {
		this.composicaoSesmtManager = composicaoSesmtManager;
	}
	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}