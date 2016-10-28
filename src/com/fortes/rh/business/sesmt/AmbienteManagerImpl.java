package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
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

@Component
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
	
	@Autowired
	AmbienteManagerImpl(AmbienteDao fooDao) {
		setDao(fooDao);
	}
	
	private Collection<Ambiente> getAmbientes(String[] ambienteCheck, Date data, Long estabelecimentoId) 
	{
		Collection<Long> ambienteIds = LongUtil.arrayStringToCollectionLong(ambienteCheck);
		return getDao().findByIds(ambienteIds, data, estabelecimentoId);
	}
	
	public Collection<PpraLtcatRelatorio> montaRelatorioPpraLtcat(Empresa empresa, Long estabelecimentoId, Date data, String[] ambienteCheck, boolean gerarPpra, boolean gerarLtcat, boolean exibirComposicaoSesmt) throws ColecaoVaziaException 
	{
		this.exibirPpra = gerarPpra;
		this.exibirLtcat = gerarLtcat;
		ComposicaoSesmt composicaoSesmt = null;
		
		Estabelecimento estabelecimento = estabelecimentoManager.findById(estabelecimentoId);
		boolean isControlaRiscoPorAmbiente = empresaManager.isControlaRiscoPorAmbiente(empresa.getId());
		
		relatorios = new ArrayList<PpraLtcatRelatorio>();
		
		Collection<Ambiente> ambientes = getAmbientes(ambienteCheck,data, estabelecimentoId);
		
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
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho(empresa, estabelecimento, ambiente.getNome(), ambiente.getHistoricoAtual().getDescricao());
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
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho(empresa, estabelecimento, ambiente.getNome(), ambiente.getHistoricoAtual().getDescricao());
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
	
	public Integer getCount(Long empresaId, Ambiente ambiente)
	{
		return getDao().getCount(empresaId, ambiente);
	}

	public Collection<Ambiente> findAmbientes(Long empresaId)
	{
		return getDao().findAmbientes(0, 0, empresaId, null);
	}

	public Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId, Ambiente ambiente)
	{
		return getDao().findAmbientes(page, pagingSize, empresaId, ambiente);
	}

	public void saveAmbienteHistorico(Ambiente ambiente, HistoricoAmbiente historicoAmbiente, String[] riscoChecks, Collection<RiscoAmbiente> riscosAmbientes, String[] epcCheck) throws Exception
	{
		save(ambiente);

		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
	}
	
	public void removeCascade(Long id) throws Exception 
	{
		//O hibernate gerencia a remoção dos relacionamentos.			
		Ambiente ambiente = findById(id);
		getDao().remove(ambiente);
	}
	
	public Collection<Ambiente> findByEstabelecimento(Long... estabelecimentoIds)
	{
		return getDao().findByEstabelecimento(estabelecimentoIds);
	}

	public Collection<Ambiente> findByEmpresa(Long empresaId)
	{
		return find(new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"nome"});
	}

	public Collection<CheckBox> getAmbientes(Empresa empresa) throws Exception
	{
		return CheckListBoxUtil.populaCheckListBox( find(new String[]{"empresa.id"},new Object[]{empresa.getId()}, new String[]{"nome"}), "getId", "getNome", null);
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
			Collection<Ambiente> ambientes = getDao().findByEstabelecimento(estabelecimentoIds);
			return CheckListBoxUtil.populaCheckListBox(ambientes, "getId", "getNomeComEstabelecimento", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<CheckBox>();
		}
	}

	public Collection<CheckBox> populaCheckBox(Long... estabelecimentoId) 
	{
		try
		{
			Collection<Ambiente> ambientes = getDao().findByEstabelecimento(estabelecimentoId);
			return CheckListBoxUtil.populaCheckListBox(ambientes, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<CheckBox>();
		}
	}

	public void deleteByEstabelecimento(Long[] estabelecimentoIds) throws Exception {
		getDao().deleteByEstabelecimento(estabelecimentoIds);
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId) {
		Collection<Ambiente> ambientesOrigin = getDao().findAllByEmpresa(empresaOrigemId);
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findByEmpresa(empresaDestinoId);
		
		for (Estabelecimento estabelecimento : estabelecimentos) {
			for (Ambiente ambienteOrigin : ambientesOrigin){
				Ambiente ambiente = new Ambiente();
				ambiente.setNome(ambienteOrigin.getNome());
				ambiente.setEstabelecimento(estabelecimento);
				ambiente.setEmpresa(new Empresa(empresaDestinoId));
				getDao().save(ambiente);

				if(ambienteOrigin.getHistoricoAtual() != null){
					HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente(null, ambienteOrigin.getHistoricoAtual().getData(), ambiente);
					historicoAmbiente.setTempoExposicao(ambienteOrigin.getHistoricoAtual().getTempoExposicao());
					historicoAmbiente.setDescricao(ambienteOrigin.getHistoricoAtual().getDescricao());
					historicoAmbienteManager.save(historicoAmbiente);
				}
			}
		}
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