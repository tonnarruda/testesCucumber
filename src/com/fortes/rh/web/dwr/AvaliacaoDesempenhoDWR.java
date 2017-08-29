package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.Option;
import com.opensymphony.webwork.dispatcher.SessionMap;

@SuppressWarnings("unchecked")
public class AvaliacaoDesempenhoDWR 
{
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	private ColaboradorManager colaboradorManager;
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;

	public Map<Long, String> getAvaliacoesByEmpresa(Long empresaId)
	{
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO);
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacoesDesempenho, "getId", ((empresaId == null || empresaId < 0) ? "getTituloComEmpresa" : "getTitulo"));
	}
	
	public Map<Long, String> getAvaliacoesDesempenhoByModelo(Long modeloId)
	{
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoManager.findByModelo(modeloId);
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacoesDesempenho, "getId", "getTitulo");
	}

	@SuppressWarnings({"rawtypes" })
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
	
	public Map<Long, String> getParticipantesByAvalEmpresaAreaCargo(Long avaliacaoDesempenhoId, Long empresaId, Long[] areasIds, Long[] cargosIds)
	{
		empresaId = (empresaId == null || empresaId == -1 || empresaId == 0) ? null : empresaId;
		
		Collection<Colaborador> participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, true, areasIds, cargosIds, false, empresaId);
		return new CollectionUtil<Colaborador>().convertCollectionToMap(participantes, "getId", "getNome");
	}
	
	public Map<Long, String> getAvaliacoesNaoLiberadasByTitulo(Long empresaId, String titulo)
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findTituloModeloAvaliacao(null, null, null, null, empresaId, titulo, null, false);
		return CollectionUtil.convertCollectionToMap(avaliacaoDesempenhos, "getId", "getTitulo", AvaliacaoDesempenho.class);
	}
	
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

	public Collection<Option> getAvaliados(Long avaliacaoDesempenhoId, Long[] empresaIds, Long[] areasIds, Long[] cargosIds)
	{
		Collection<Option> options = new ArrayList<Option>();
		Collection<Colaborador> participantes = new ArrayList<Colaborador>();
		
		if(avaliacaoDesempenhoId != null && !avaliacaoDesempenhoId.equals(-1L))
			participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenhoId, true, areasIds, cargosIds, true, empresaIds);
		
		Option option = null;
		for (Colaborador avaliado : participantes) {
			option = new Option();
			option.setId(avaliado.getId());
			option.setNome(avaliado.getNome());
			options.add(option);
		}

		return options;
	}
	
	public Map<Long, String> getAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId)
	{
		Map<Long, String> checks = new HashMap<Long, String>();
		Collection<Colaborador> avaliadores = configuracaoNivelCompetenciaColaboradorManager.findCargosAvaliadores(avaliacaoDesempenhoId, avaliadoId);
		for (Colaborador colaborador : avaliadores) {
				checks.put(colaborador.getId(), colaborador.getNome() + " (" + colaborador.getCargo().getNome() + ")");
		}
		
		return checks;
	}
	
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId) {
		return configuracaoCompetenciaAvaliacaoDesempenhoManager.existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenhoId);
	}
	
	public Map<Long, String> getEstabelecimentosDosParticipantes(Long[] avaliacoesDesempenhoIds)
	{
		Collection<Estabelecimento> estabelecimentos = avaliacaoDesempenhoManager.findEstabelecimentosDosParticipantes(avaliacoesDesempenhoIds);
		
		return new CollectionUtil<Estabelecimento>().convertCollectionToMap(estabelecimentos, "getId", "getNome");
	}
	
	public Map<Long, String> getAreasOrganizacionaisDosParticipantes(Long[] avaliacoesDesempenhoIds)
	{
		Collection<AreaOrganizacional> areaOrganizacionais = avaliacaoDesempenhoManager.findAreasOrganizacionaisDosParticipantes(avaliacoesDesempenhoIds);
		
		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionais, "getId", "getDescricaoStatusAtivo");
	}
	
	public Map<Long, String> getCargosDosParticipantes(Long[] avaliacoesDesempenhoIds)
	{
		Collection<Cargo> cargo = avaliacaoDesempenhoManager.findCargosDosParticipantes(avaliacoesDesempenhoIds);
		
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargo, "getId", "getNomeMercadoComStatus");
	}
	
	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager) {
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setConfiguracaoCompetenciaAvaliacaoDesempenhoManager(ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager) {
		this.configuracaoCompetenciaAvaliacaoDesempenhoManager = configuracaoCompetenciaAvaliacaoDesempenhoManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(
			ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}
}