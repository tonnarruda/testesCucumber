package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.relatorio.ManutencaoAndInspecaoRelatorio;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

@Component
public class ExtintorManagerImpl extends GenericManagerImpl<Extintor, ExtintorDao> implements ExtintorManager
{
	@Autowired private HistoricoExtintorManager historicoExtintorManager;
	@Autowired private ExtintorInspecaoManager extintorInspecaoManager;
	@Autowired private ExtintorManutencaoManager extintorManutencaoManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	
	@Autowired
	ExtintorManagerImpl(ExtintorDao extintorDao) {
			setDao(extintorDao);
	}
	
	public Integer getCount(Long empresaId, String tipoBusca, Integer numeroBusca, char ativo)
	{
		Boolean valorAtivo = null;

		if (ativo != 'T')
			valorAtivo = ativo == 'S' ? true : false;

		if (tipoBusca != null && tipoBusca.equals("T"))
			tipoBusca = null;

		return getDao().getCount(empresaId, tipoBusca, numeroBusca, valorAtivo);
	}

	public Collection<Extintor> findAllSelect(int page, int pagingSize, Long empresaId, String tipoBusca, Integer numeroBusca, char ativo)
	{
		Boolean valorAtivo = null;

		if (ativo != 'T')
			valorAtivo = ativo == 'S' ? true : false;

		if (tipoBusca != null && tipoBusca.equals("T"))
			tipoBusca = null;

		return getDao().findAllSelect(page, pagingSize, empresaId, tipoBusca, numeroBusca, valorAtivo);
	}

	public Collection<Extintor> findAllComHistAtual(Boolean ativo, Long estabelecimentoId, Long empresaId)
	{
		return getDao().findAllComHistAtual(ativo, estabelecimentoId, empresaId);
	}

	public String getFabricantes(Long empresaId)
	{
		Collection<String> fabricantes = getDao().findFabricantesDistinctByEmpresa(empresaId);
		if(fabricantes == null || fabricantes.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(fabricantes);
	}

	public String getLocalizacoes(Long empresaId)
	{
		Collection<String> localizacoes = getDao().findLocalizacoesDistinctByEmpresa(empresaId);
		if(localizacoes == null || localizacoes.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(localizacoes);
	}

	public Collection<ManutencaoAndInspecaoRelatorio> relatorioManutencaoAndInspecao(Long estabelecimentoId, Date dataVencimento, boolean inspecaoVencida, boolean cargaVencida, boolean testeHidrostaticoVencido) throws Exception
	{
		Collection<ManutencaoAndInspecaoRelatorio> relatorio = new ArrayList<ManutencaoAndInspecaoRelatorio>();
		ManutencaoAndInspecaoRelatorio manutencaoAndInspecaoRelatorio = new ManutencaoAndInspecaoRelatorio();
		
		Collection<ExtintorInspecao> extintorInspecaoVencidas = new ArrayList<ExtintorInspecao>();
		Collection<ExtintorManutencao> extintorCargaVencidas = new ArrayList<ExtintorManutencao>();
		Collection<ExtintorManutencao> extintorTesteHidrostaticoVencidos = new ArrayList<ExtintorManutencao>();
		
		if(inspecaoVencida)
		{
			extintorInspecaoVencidas = extintorInspecaoManager.findInspecoesVencidas(estabelecimentoId, dataVencimento);
			manutencaoAndInspecaoRelatorio.setExtintoresComInspecaoVencida(extintorInspecaoVencidas);
		}
		
		if(cargaVencida)
		{
			extintorCargaVencidas = extintorManutencaoManager.findManutencaoVencida(estabelecimentoId, dataVencimento, MotivoExtintorManutencao.PRAZO_RECARGA);
			manutencaoAndInspecaoRelatorio.setExtintoresComCargaVencida(extintorCargaVencidas);
		}
		
		if(testeHidrostaticoVencido)
		{
			extintorTesteHidrostaticoVencidos = extintorManutencaoManager.findManutencaoVencida(estabelecimentoId, dataVencimento, MotivoExtintorManutencao.PRAZO_HIDROSTATICO);			
			manutencaoAndInspecaoRelatorio.setExtintoresComTesteHidrostaticoVencido(extintorTesteHidrostaticoVencidos);
		}
		
		if(extintorInspecaoVencidas.isEmpty() && extintorCargaVencidas.isEmpty() && extintorTesteHidrostaticoVencidos.isEmpty())
			throw new Exception("Nenhum extintor com prazo vencido.");
		
		relatorio.add(manutencaoAndInspecaoRelatorio);
		
		return relatorio;
	}
	
	public String montaLabelFiltro(Long estabelecimentoId, Date dataVencimento)
	{
		Estabelecimento estabelecimento = estabelecimentoManager.findEstabelecimentoCodigoAc(estabelecimentoId);
		return "Estabelecimento: " + estabelecimento.getNome() + "\nVencimentos até: " + DateUtil.formataDiaMesAno(dataVencimento);
	}
	
	public void remove(Long extintorId)
	{
		historicoExtintorManager.removeByExtintor(extintorId);
		getDao().remove(extintorId);
	}
}