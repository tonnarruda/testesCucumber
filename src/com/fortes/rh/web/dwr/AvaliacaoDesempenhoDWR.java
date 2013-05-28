package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.opensymphony.webwork.dispatcher.SessionMap;

public class AvaliacaoDesempenhoDWR 
{
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	private ColaboradorManager colaboradorManager;

	@SuppressWarnings("unchecked")
	public Map<Long, String> getAvaliacoesByEmpresa(Long empresaId)
	{
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO);
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacoesDesempenho, "getId", ((empresaId == null || empresaId < 0) ? "getTituloComEmpresa" : "getTitulo"));
	}

	@SuppressWarnings("unchecked")
	public Map<Long, String> getAvaliacoesByEmpresaPermitidas(Long empresaId, String naoApagar, HttpServletRequest request)
	{
		Map session = new SessionMap(request);
		
		Colaborador avaliador = new Colaborador();
		avaliador = SecurityUtil.getColaboradorSession(session);
		
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		if(SecurityUtil.verifyRole(session, new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}) )
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(null, true, empresaId);//pega todas liberadas
		else
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), true, empresaId);
		
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacaoDesempenhos, "getId", ((empresaId == null || empresaId.equals(-1L)) ? "getTituloComEmpresa" : "getTitulo"));
	}
	
	public Map<Long, String> getParticipantesByAvalEmpresaAreaCargo(Long avaliacaoDesempenhoId, Long empresaId, Long[] areasIds, Long[] cargosIds)
	{
		empresaId =(empresaId == null || empresaId == -1 || empresaId == 0) ? null : empresaId;
		
		Collection<Colaborador> participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, true, empresaId, areasIds, cargosIds);
		return new CollectionUtil<Colaborador>().convertCollectionToMap(participantes, "getId", "getNome");
	}
	
	@SuppressWarnings("unchecked")
	public Map<Long, String> getAvaliacoesNaoLiberadasByTitulo(Long empresaId, String titulo)
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findTituloModeloAvaliacao(null, null, empresaId, titulo, null, false);
		return CollectionUtil.convertCollectionToMap(avaliacaoDesempenhos, "getId", "getTitulo", AvaliacaoDesempenho.class);
	}

	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager) {
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
}
