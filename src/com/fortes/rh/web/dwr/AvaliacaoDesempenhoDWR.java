package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.Option;
import com.opensymphony.webwork.dispatcher.SessionMap;

@Component
@RemoteProxy(name="AvaliacaoDesempenhoDWR")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AvaliacaoDesempenhoDWR 
{
	@Autowired private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager;
	@Autowired private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;

	@RemoteMethod
	public Map<Long, String> getAvaliacoesByEmpresa(Long empresaId)
	{
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO);
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacoesDesempenho, "getId", ((empresaId == null || empresaId < 0) ? "getTituloComEmpresa" : "getTitulo"));
	}

	@RemoteMethod
	public Map<Long, String> getAvaliacoesByEmpresaPermitidas(String naoApagar, HttpServletRequest request, Long... empresasIds)
	{
		Map session = new SessionMap(request);
		
		Colaborador avaliador = new Colaborador();
		avaliador = SecurityUtil.getColaboradorSession(session);
		
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		if(SecurityUtil.verifyRole(session, new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}) )
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(null, true, empresasIds);//pega todas liberadas
		else
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), true, empresasIds);
		
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacaoDesempenhos, "getId", ((empresasIds.length > 1) ? "getTituloComEmpresa" : "getTitulo"));
	}
	
	@RemoteMethod
	public Map<Long, String> getParticipantesByAvalEmpresaAreaCargo(Long avaliacaoDesempenhoId, Long empresaId, Long[] areasIds, Long[] cargosIds)
	{
		empresaId = (empresaId == null || empresaId == -1 || empresaId == 0) ? null : empresaId;
		
		Collection<Colaborador> participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, true, empresaId, areasIds, cargosIds);
		return new CollectionUtil<Colaborador>().convertCollectionToMap(participantes, "getId", "getNome");
	}
	
	@RemoteMethod
	public Map<Long, String> getAvaliacoesNaoLiberadasByTitulo(Long empresaId, String titulo)
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findTituloModeloAvaliacao(null, null, null, null, empresaId, titulo, null, false);
		return CollectionUtil.convertCollectionToMap(avaliacaoDesempenhos, "getId", "getTitulo", AvaliacaoDesempenho.class);
	}
	
	@RemoteMethod
	public String verificaAvaliadosSemCompetencia(Long avaliacaoDesempenhoId)
	{
		if(configuracaoCompetenciaAvaliacaoDesempenhoManager.existe(null, avaliacaoDesempenhoId))
		{
			Collection<Colaborador> colaboradores = configuracaoCompetenciaAvaliacaoDesempenhoManager.findColabSemCompetenciaConfiguradaByAvalDesempenhoId(avaliacaoDesempenhoId);
			if(colaboradores.size() != 0)
			{
				StringBuilder msg = new StringBuilder();
				msg.append("Existem avaliadores sem nenhuma configuração de competência a avaliar nesta avaliação de desempenho.</br>");
				
				msg.append("</br>Colaboradores:</br>");
				for (Colaborador colaborador : colaboradores) 
					msg.append(colaborador.getNome() + "</br>");	
				
				msg.append("</br>Deseja liberar assim mesmo?");
				
				return msg.toString();
			}
		}
		
		return "";
	}
	
	@RemoteMethod
	public String verificaAvaliacoesComAvaliadosSemCompetencia(Long[] avaliacaoDesempenhoIds)
	{
		Collection<AvaliacaoDesempenho> avaliacoes = configuracaoCompetenciaAvaliacaoDesempenhoManager.findAvaliacoesComColabSemCompetenciaConfiguradaByAvalDesempenhoIds(avaliacaoDesempenhoIds);
		if(avaliacoes.size() != 0)
		{
			StringBuilder msg = new StringBuilder();
			msg.append("Existem avaliações com colaboradores sem nenhuma configuração de competência a avaliar.</br>");
			
			msg.append("</br>Avaliações:</br>");
			for (AvaliacaoDesempenho avaliacao : avaliacoes) 
				msg.append(avaliacao.getTitulo() + "</br>");	
			
			msg.append("</br>Deseja continuar?");
			
			return msg.toString();
		}
		
		return "";
	}

	@RemoteMethod
	public Collection<Option> getAvaliados(Long avaliacaoDesempenhoId, Long empresaId, Long[] areasIds, Long[] cargosIds)
	{
		Collection<Option> options = new ArrayList<Option>();
		Collection<Colaborador> participantes = new ArrayList<Colaborador>();
		
		if(avaliacaoDesempenhoId != null && !avaliacaoDesempenhoId.equals(-1L))
			participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, true, empresaId, areasIds, cargosIds);
		
		Option option = null;
		for (Colaborador avaliado : participantes) {
			option = new Option();
			option.setId(avaliado.getId());
			option.setNome(avaliado.getNome());
			options.add(option);
		}

		return options;
	}
	
	@RemoteMethod
	public Map<Long, String> getAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId)
	{
		Map<Long, String> checks = new HashMap<Long, String>();
		Collection<Colaborador> avaliadores = configuracaoNivelCompetenciaColaboradorManager.findCargosAvaliadores(avaliacaoDesempenhoId, avaliadoId);
		for (Colaborador colaborador : avaliadores) {
				checks.put(colaborador.getId(), colaborador.getNome() + " (" + colaborador.getCargo().getNome() + ")");
		}
		
		return checks;
	}
	
	@RemoteMethod
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId) {
		return configuracaoCompetenciaAvaliacaoDesempenhoManager.existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenhoId);
	}
}