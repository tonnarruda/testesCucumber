package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class AmbienteDWR
{
	private AmbienteManager ambienteManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	
	public Map<String, Collection<Ambiente>> getAmbienteByEstabelecimentoAndAmbientesDeTerceiros(Long empresaId, Long estabelecimentoId, String estabelecimentoNome, String data)
	{
		return ambienteManager.montaMapAmbientes(empresaId, estabelecimentoId, estabelecimentoNome, DateUtil.criarDataDiaMesAno(data));
	}
	
	public Map<String, Collection<Ambiente>> getAmbientes(Long empresaId, Long tabelaReajusteId, Long estabelecimentoId, String estabelecimentoNome)
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = tabelaReajusteColaboradorManager.findEntidadeComAtributosSimplesById(tabelaReajusteId);
		
		return ambienteManager.montaMapAmbientes(empresaId, estabelecimentoId, estabelecimentoNome, tabelaReajusteColaborador.getData());
	}
	
	public Map<Object, Object> getAmbientesByEstabelecimentoOrAmbientesDeTerceiro(Long empresaId, Long estabelecimentoId, Integer localAmbiente, String data)
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		Collection<Ambiente> ambientesAux =  new ArrayList<Ambiente>();
				
		if(localAmbiente.equals(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao()) || (estabelecimentoId != null && estabelecimentoId >= 1L))
			ambientesAux = ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, DateUtil.criarDataDiaMesAno(data)); 
		
		Ambiente ambienteVazio = new Ambiente();
		ambienteVazio.setId(0L);
		ambientes.add(ambienteVazio);
		ambientes.addAll(ambientesAux);
		
		if(ambientesAux.size() < 1){
		
			ambienteVazio.setNome(" Nenhum");
		}
		else{
			ambienteVazio.setNome(" Selecione...");
		}
		
		return new CollectionUtil<Ambiente>().convertCollectionToMap(ambientes,"getId","getNome");
	}
	
	public Map<Object, Object> getAmbienteChecks(Long empresaId, Long estabelecimentoId, Integer localAmbiente, String data)
	{
		Collection<Ambiente> ambientes = ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresaId, estabelecimentoId, localAmbiente, DateUtil.criarDataDiaMesAno(data));
		
		return  new CollectionUtil<Ambiente>().convertCollectionToMap(ambientes,"getId","getNome");
	}
	
	public Map<Object, Object> getAmbientesByEstabelecimentos(String[] estabelecimentosIds, String data)
	{
		Collection<Ambiente> ambientes = ambienteManager.findAmbientesPorEstabelecimento(LongUtil.arrayStringToArrayLong(estabelecimentosIds), DateUtil.criarDataDiaMesAno(data));
		
		return  new CollectionUtil<Ambiente>().convertCollectionToMap(ambientes,"getId","getNomeComEstabelecimento");
	}
	
	public boolean existeHistoricoAmbienteByData(Long estabelecimentoId, Long ambienteId, String data) throws Exception{
		return historicoAmbienteManager.existeHistoricoAmbienteByData(estabelecimentoId, ambienteId, DateUtil.criarDataDiaMesAno(data));
	}
	
	public void setAmbienteManager(AmbienteManager ambienteManager) {
		this.ambienteManager = ambienteManager;
	}

	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager) {
		this.historicoAmbienteManager = historicoAmbienteManager;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager) {
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}	
}