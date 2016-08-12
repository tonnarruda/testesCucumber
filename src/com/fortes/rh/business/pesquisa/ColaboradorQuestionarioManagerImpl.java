package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.FiltroSituacaoAvaliacao;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.LongUtil;
@SuppressWarnings("rawtypes")
public class ColaboradorQuestionarioManagerImpl extends GenericManagerImpl<ColaboradorQuestionario, ColaboradorQuestionarioDao> implements ColaboradorQuestionarioManager
{
	private PerguntaManager perguntaManager;
	private ColaboradorManager colaboradorManager;
	private ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager;
	
	public Collection<ColaboradorQuestionario> findByQuestionario(Long questionarioId)
	{
		return getDao().findByQuestionario(questionarioId);
	}

	public Collection<ColaboradorQuestionario> findByQuestionarioEmpresaRespondida(Long questionarioId, Boolean respondida, Collection<Long> estabelecimentoIds, Long empresaId )
	{
		return getDao().findByQuestionarioEmpresaRespondida(questionarioId, respondida, estabelecimentoIds, empresaId);
	}

	public Collection<Colaborador> selecionaColaboradores(Collection<Colaborador> colaboradores, char qtdPercentual, double percentual, int quantidade)
	{
		if(colaboradores.size() == 0)
			return colaboradores;

		if(qtdPercentual == '1')
		{
			//Verifica a quantidade de Colaboradores que devem ser selectionados de acordo com a porcentagem.
			quantidade = (int) ((percentual/100) * colaboradores.size());
			quantidade = quantidade < 1 ? 1 : quantidade;
		}

		return selectionaColaboradoresRandom(colaboradores, quantidade);
	}

	@SuppressWarnings("unchecked")
	private Collection<Colaborador> selectionaColaboradoresRandom(Collection<Colaborador> colaboradors, int quantidade)
	{
		Object[] arrayColaboradors;
		Colaborador colaboradorTmp;
		Collection<Colaborador> result = new ArrayList<Colaborador>();
		int value;

		for (int i = 1; i <= quantidade; i++)
		{
			if(!colaboradors.isEmpty())
			{
				//pega a posição sorteada
				value = (int) Math.round(Math.random() * (colaboradors.size()-1));

				arrayColaboradors = colaboradors.toArray();
				colaboradorTmp = (Colaborador) arrayColaboradors[value];

				colaboradors.remove(colaboradorTmp);
				result.add(colaboradorTmp);
			}
		}

		Comparator comparator = new BeanComparator("nomeComercial", new ComparatorString());

        Collections.sort((List) result, comparator);

		return result;
	}

	public Collection<Colaborador> selecionaColaboradoresPorParte(Collection<Colaborador> colaboradores, char filtrarPor, Collection<Long> areasIds, Collection<Long> cargosIds, char qtdPercentual, double percentual, int quantidade)
	{
		Collection<Colaborador> result = new ArrayList<Colaborador>();
		Collection<Colaborador> colaboradoresTmps;

		if(filtrarPor == '1')
		{
			for (Long areaId : areasIds)
			{
				colaboradoresTmps = new ArrayList<Colaborador>();

				for (Colaborador colaborador : colaboradores)
				{
					if(colaborador.getAreaOrganizacional() != null && colaborador.getAreaOrganizacional().getId() != null && colaborador.getAreaOrganizacional().getId().equals(areaId))
					{
						colaboradoresTmps.add(colaborador);
					}
				}

				result.addAll(selecionaColaboradores(colaboradoresTmps, qtdPercentual, percentual, quantidade));
			}
		}
		else
		{
			for (Long cargoId : cargosIds)
			{
				colaboradoresTmps = new ArrayList<Colaborador>();

				for (Colaborador colaborador : colaboradores)
				{
					if(colaborador.getFaixaSalarial() != null && colaborador.getFaixaSalarial().getCargo() != null && colaborador.getFaixaSalarial().getCargo().getId() != null && colaborador.getFaixaSalarial().getCargo().getId().equals(cargoId))
					{
						colaboradoresTmps.add(colaborador);
					}
				}

				result.addAll(selecionaColaboradores(colaboradoresTmps, qtdPercentual, percentual, quantidade));
			}
		}

		return result;
	}

	public void save(Questionario questionario, Long[] colaboradoresIds, Turma turma) throws Exception
	{
		if (questionario != null && questionario.getId() != null)
		{
			ColaboradorQuestionario colaboradorQuestionario;
			Colaborador colaboradorTmp;

			Collection<ColaboradorQuestionario> colaboradorQuestionarios = getDao().findByQuestionario(questionario.getId());
			Long[] colaboradoresForaDoQuestionario = retiraColaboradores(colaboradorQuestionarios, colaboradoresIds);

			for (Long colaboradorIdTmp : colaboradoresForaDoQuestionario)
			{
				if(colaboradorIdTmp != null)
				{
					colaboradorTmp = new Colaborador();
					colaboradorTmp.setId(colaboradorIdTmp);

					colaboradorQuestionario = new ColaboradorQuestionario();
					colaboradorQuestionario.setColaborador(colaboradorTmp);
					colaboradorQuestionario.setQuestionario(questionario);
					colaboradorQuestionario.setTurma(turma);

					getDao().save(colaboradorQuestionario);
				}
			}
		}
	}

	private Long[] retiraColaboradores(Collection<ColaboradorQuestionario> colaboradorQuestionarios, Long[] colaboradoresIds)
	{
		Long[] result = new Long[colaboradoresIds.length];
		int cont = 0;
		boolean exist;

		for (int i = 0; i < colaboradoresIds.length; i++)
		{
			exist = false;
			for (ColaboradorQuestionario colaboradorQuestionarioTmp : colaboradorQuestionarios)
			{
				if (colaboradorQuestionarioTmp.getColaborador().getId().equals(colaboradoresIds[i]))
					exist = true;
			}

			if (!exist)
				result[cont++] = colaboradoresIds[i];
		}

		return result;
	}

	public ColaboradorQuestionario findByQuestionario(Long questionarioId, Long colaboradorId, Long turmaId)
	{
		return getDao().findByQuestionario(questionarioId, colaboradorId, turmaId);
	}

	public ColaboradorQuestionario findColaboradorComEntrevistaDeDesligamento(Long colaboradorId)
	{
		return getDao().findColaboradorComEntrevistaDeDesligamento(colaboradorId);
	}
	
	public ColaboradorQuestionario findColaborador(Long colaboradorId, Long questionarioId, Long turmaId)
	{
		return getDao().findColaborador(colaboradorId, questionarioId, turmaId);
	}

	public void removeByColaboradorETurma(Long colaboradorId, Long turmaId)
	{
		getDao().removeByColaboradorETurma(colaboradorId, turmaId);
	}

	public Collection<ColaboradorQuestionario> findRespondidasByColaboradorETurma(Long colaboradorId, Long turmaId, Long empresaId)
	{
		return getDao().findRespondidasByColaboradorETurma(colaboradorId, turmaId, empresaId);
	}

	public Collection<ColaboradorQuestionario> findFichasMedicas(Character vinculo, Date dataIni, Date dataFim, String nomeBusca, String cpfBusca, String matriculaBusca)
	{
		if(vinculo == null)
			return getDao().findFichasMedicas();
		else	
			return getDao().findFichasMedicas(vinculo, dataIni, dataFim, nomeBusca, cpfBusca, matriculaBusca);
	}

	public ColaboradorQuestionario findByQuestionarioCandidato(Long id, Long candidatoId)
	{
		return getDao().findByQuestionarioCandidato(id, candidatoId);
	}

	public ColaboradorQuestionario findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public ColaboradorQuestionario findByIdColaboradorCandidato(Long id)
	{
		return getDao().findByIdColaboradorCandidato(id);
	}

	public Collection<ColaboradorResposta> populaQuestionario(Avaliacao avaliacao)
	{
		if(avaliacao == null || avaliacao.getId() == null)
			return new ArrayList<ColaboradorResposta>();
			
		Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(avaliacao.getId());
		Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();

		for (Pergunta pergunta : perguntas)
		{
			ColaboradorResposta colaboradorResposta = new ColaboradorResposta();
			colaboradorResposta.setPergunta(pergunta);
			
			colaboradorRespostas.add(colaboradorResposta);
		}
		
		return colaboradorRespostas;
	}

	public void setPerguntaManager(PerguntaManager perguntaManager)
	{
		this.perguntaManager = perguntaManager;
	}

	public Collection<ColaboradorQuestionario> findAvaliacaoByColaborador(Long colaboradorId)
	{
		return getDao().findAvaliacaoByColaborador(colaboradorId);
	}
	
	public Collection<ColaboradorQuestionario> findAvaliacaoDesempenhoByColaborador(Long colaboradorId){
		return getDao().findAvaliacaoDesempenhoByColaborador(colaboradorId);
	}

	public Collection<ColaboradorQuestionario> findColaboradorHistoricoByQuestionario(Long questionarioId, Boolean respondida, Long empresaId)
	{
		return getDao().findColaboradorHistoricoByQuestionario(questionarioId, respondida, empresaId);
	}
	
	public void save(AvaliacaoDesempenho avaliacaoDesempenho, Long[] colaboradorIds, boolean isAvaliado) 
	{
		Collection<Colaborador> participantes = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), isAvaliado, null);
		
		for (Long colaboradorId : colaboradorIds) 
		{
			if (!LongUtil.contains(colaboradorId, participantes))//verifica se já é um  participante
			{
				ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario(avaliacaoDesempenho);
				colaboradorQuestionario.setAvaliadoOuAvaliador(colaboradorId, isAvaliado);
				getDao().save(colaboradorQuestionario);
			}
		}
	}
	
	public void remove(Long[] participanteIds, Long avaliacaoDesempenhoId, boolean isAvaliado) throws Exception
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionariosRespondidas = getDao().findRespondidasByAvaliacaoDesempenho(participanteIds, avaliacaoDesempenhoId, isAvaliado);
		
		if (! colaboradorQuestionariosRespondidas.isEmpty())
			throw new AvaliacaoRespondidaException("Não é possível excluir, pois já existe(m) resposta(s) para este(s) colaborador(es).");
		
		getDao().removeByParticipante(avaliacaoDesempenhoId, participanteIds, isAvaliado);
	}

	public boolean verifyTemParticipantesAssociados(Long avaliacaoDesempenhoId)
	{
		return (getDao().getCountParticipantesAssociados(avaliacaoDesempenhoId) > 0);
	}
	
	public Collection<ColaboradorQuestionario> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Boolean respondida)
	{
		return getDao().findByAvaliacaoDesempenho(avaliacaoDesempenhoId, respondida);
	}
	
	public Collection<ColaboradorQuestionario> findByColaboradorAndAvaliacaoDesempenho(Long colaboradorId, Long avaliacaoDesempenhoId, boolean isAvaliado, boolean desconsiderarAutoAvaliacao)
	{
		return getDao().findByColaboradorAndAvaliacaoDesempenho(colaboradorId, avaliacaoDesempenhoId, isAvaliado, false);
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<ColaboradorQuestionario> associarParticipantes(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> avaliados, Collection<Colaborador> avaliadores) throws Exception
	{
		Collection<ColaboradorQuestionario> associados = getDao().findByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true);
		Collection<ColaboradorQuestionario> novosAssociados = new ArrayList<ColaboradorQuestionario>();
		
		for (Colaborador avaliado : avaliados)
		{
			for (Colaborador avaliador : avaliadores)
			{
				if(! avaliacaoDesempenho.isPermiteAutoAvaliacao() && avaliado.equals(avaliador))
					continue;
					
				if(verifyAssociados(associados, avaliado, avaliador))
					continue;
					
				novosAssociados.add(new ColaboradorQuestionario(avaliacaoDesempenho, avaliado.getId(), avaliador.getId()));
			}
		}
		
		getDao().saveOrUpdate(novosAssociados);
		getDao().removeParticipantesSemAssociacao(avaliacaoDesempenho.getId());
		
		return novosAssociados;
	}
	
	public void updateAvaliacaoFromColaboradorQuestionarioByAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho)  {
		getDao().updateAvaliacaoFromColaboradorQuestionarioByAvaliacaoDesempenho(avaliacaoDesempenho);
	}

	public void validaAssociacao(Collection<Colaborador> avaliados, Collection<Colaborador> avaliadores, boolean permiteAutoAvaliacao) throws Exception 
	{
		if (avaliados.isEmpty() || avaliadores.isEmpty())
			throw new FortesException("Não foi possível liberar esta avaliação: Nenhum avaliador possui colaboradores para avaliar. <br />Verifique se participantes foram desligados.");
		
		if (!permiteAutoAvaliacao && avaliados.size() == 1 && avaliadores.size() == 1)
		{
			if (((Colaborador)avaliados.toArray()[0]).equals((Colaborador)avaliadores.toArray()[0]))
				throw new FortesException("Não foi possível liberar esta avaliação: <br />Nenhum avaliador possui colaboradores para avaliar. (Não é permitida a autoavaliação).");
		}
	}

	private boolean verifyAssociados(Collection<ColaboradorQuestionario> associados, Colaborador avaliado, Colaborador avaliador) 
	{
		for (ColaboradorQuestionario associado : associados) 
		{
			if(associado.getColaborador().equals(avaliado) && associado.getAvaliador().equals(avaliador))
				return true;
		}
		
		return false;
	}

	public Collection<ColaboradorQuestionario> desassociarParticipantes(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception 
	{
		Collection<Colaborador> avaliados = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, false);
		Collection<Colaborador> avaliadores = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, false);
		Collection<ColaboradorQuestionario> desassociados = new ArrayList<ColaboradorQuestionario>();
		
		for (Colaborador avaliado : avaliados) 
		{
			ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario(avaliacaoDesempenho);
			colaboradorQuestionario.setColaborador(avaliado);
			desassociados.add(colaboradorQuestionario);
		}
	
		for (Colaborador avaliador : avaliadores) 
		{
			ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario(avaliacaoDesempenho);
			colaboradorQuestionario.setAvaliador(avaliador);
			desassociados.add(colaboradorQuestionario);
		}
		
		getDao().saveOrUpdate(desassociados);
		getDao().removeAssociadosSemResposta(avaliacaoDesempenho.getId());
		
		return desassociados;
	}

	public void clonarParticipantes(AvaliacaoDesempenho avaliacaoDesempenho, AvaliacaoDesempenho avaliacaoDesempenhoClone) throws Exception
	{
		Collection<ParticipanteAvaliacaoDesempenho> avaliados = participanteAvaliacaoDesempenhoManager.findParticipantes(avaliacaoDesempenho.getId(), TipoParticipanteAvaliacao.AVALIADO);
		Collection<ParticipanteAvaliacaoDesempenho> avaliadores = participanteAvaliacaoDesempenhoManager.findParticipantes(avaliacaoDesempenho.getId(), TipoParticipanteAvaliacao.AVALIADOR);
		
		participanteAvaliacaoDesempenhoManager.clone(avaliacaoDesempenhoClone, avaliados);
		participanteAvaliacaoDesempenhoManager.clone(avaliacaoDesempenhoClone, avaliadores);
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		
		for (ParticipanteAvaliacaoDesempenho avaliador : avaliadores) {
			for (ColaboradorQuestionario colaboradorQuestionario : findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getColaborador().getId(), FiltroSituacaoAvaliacao.TODAS.getOpcao(), false, false, null)) {
				ColaboradorQuestionario colaboradorQuestionarioClone = new ColaboradorQuestionario(avaliacaoDesempenhoClone);
				colaboradorQuestionarioClone.setAvaliacao(colaboradorQuestionario.getAvaliacao());
				colaboradorQuestionarioClone.setAvaliador(avaliador.getColaborador());
				colaboradorQuestionarioClone.setColaborador(colaboradorQuestionario.getColaborador());
				colaboradorQuestionarios.add(colaboradorQuestionarioClone);
			}
		}
	
		getDao().saveOrUpdate(colaboradorQuestionarios);
	}
	
	public void excluirColaboradorQuestionarioByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) 
	{
		getDao().excluirColaboradorQuestionarioByAvaliacaoDesempenho(avaliacaoDesempenhoId);
	}
	
	public Collection<ColaboradorQuestionario> findAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId, char respondida, boolean considerarPeriodoAvalDesempenho, boolean considerarLiberada, Boolean considerarRespostasParciais)
	{
		return getDao().findAvaliadosByAvaliador(avaliacaoDesempenhoId, avaliadorId, respondida, considerarPeriodoAvalDesempenho, considerarLiberada, considerarRespostasParciais); 
	}

	public Collection<ColaboradorQuestionario> getPerformance(Collection<Long> avaliados, Long avaliacaoDesempenhoId)
	{
		return getDao().getPerformance(avaliados, avaliacaoDesempenhoId);
	}

	public Collection<ColaboradorQuestionario> findBySolicitacaoRespondidas(Long solicitacaoId) 
	{
		return getDao().findBySolicitacaoRespondidas(solicitacaoId);
	}

	public Collection<Colaborador> findRespondidasBySolicitacao(Long solicitacaoid, Long avaliacaoId) {
		
		return getDao().findRespondidasBySolicitacao(solicitacaoid, avaliacaoId);
	}

	public Integer countByQuestionarioRespondido(Long questionarioId) 
	{
		return getDao().countByQuestionarioRespondido(questionarioId);
	}

	public Collection<ColaboradorQuestionario> findByColaborador(Long colaboradorId) {
	
		return getDao().findByColaborador(colaboradorId);
	}

	public Double getMediaPeformance(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao) {
		return getDao().getMediaPeformance(avaliadoId, avaliacaoDesempenhoId, desconsiderarAutoAvaliacao);
	}

	public Integer getQtdAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId, boolean desconsiderarAutoAvaliacao){
		return getDao().getQtdAvaliadores(avaliacaoDesempenhoId, avaliadoId, desconsiderarAutoAvaliacao);
	}
	
	public ColaboradorQuestionario findByColaboradorAvaliacao(Colaborador colaborador, Avaliacao avaliacao) {
		if (colaborador != null && colaborador.getId() != null && avaliacao != null && avaliacao.getId() != null)
			return getDao().findByColaboradorAvaliacao(colaborador.getId(), avaliacao.getId());
		
		return null;
	}

	public ColaboradorQuestionario findByColaboradorAvaliacaoCurso(Long colaboradorId, Long avaliacaoCursoId, Long turmaId) 
	{
		return getDao().findByColaboradorAvaliacaoCurso(colaboradorId, avaliacaoCursoId, turmaId);
	}
	
	public Collection<ColaboradorQuestionario> findQuestionarioByTurmaLiberadaPorUsuario(Long usuarioId) {
		return getDao().findQuestionarioByTurmaLiberadaPorUsuario(usuarioId);
	}

	public void removeByCandidato(Long candidatoId) {
		getDao().removeByCandidato(candidatoId);		
	}

	public void deleteRespostaAvaliacaoDesempenho(Long colaboradorQuestionarioId) {
		getDao().deleteRespostaAvaliacaoDesempenho(colaboradorQuestionarioId);
	}

	public Collection<ColaboradorQuestionario> findForRankingPerformanceAvaliacaoCurso(Long[] cursosIds, Long[] turmasIds, Long[] avaliacaoCursosIds) 
	{
		return getDao().findForRankingPerformanceAvaliacaoCurso(cursosIds, turmasIds, avaliacaoCursosIds);
	}

	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		getDao().removeBySolicitacaoId(solicitacaoId);
	}

	public Collection<ColaboradorQuestionario> findAutoAvaliacao(Long colaboradorId)
	{
		return getDao().findAutoAvaliacao(colaboradorId);
	}

	public Collection<ColaboradorQuestionario> findRespondidasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) 
	{
		return getDao().findRespondidasByAvaliacaoDesempenho(avaliacaoDesempenhoId);
	}

	public boolean existeMesmoModeloAvaliacaoEmDesempenhoEPeriodoExperiencia(Long avaliacaoId)
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = getDao().findByAvaliacaoComQtdDesempenhoEPeriodoExperiencia(avaliacaoId);
		
		for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios) {
			if(colaboradorQuestionario.getQtdPeriodoExperiencia() > 0 && colaboradorQuestionario.getQtdAvaliacaoDesempenho() > 0)
				return true;			
		}
		
		return false;
	}

	public void setParticipanteAvaliacaoDesempenhoManager( ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager) {
		this.participanteAvaliacaoDesempenhoManager = participanteAvaliacaoDesempenhoManager;
	}

	public boolean existeColaboradorQuestionarioRespondidoParcialmente(Long avaliacaoDesepenhoId, Long avaliadorId) {
		if(avaliadorId != null)
			return getDao().verifyExists(new String[]{"avaliacaoDesempenho.id", "avaliador.id", "respondidaParcialmente"}, new Object[]{avaliacaoDesepenhoId, avaliadorId, true});
		else
			return getDao().verifyExists(new String[]{"avaliacaoDesempenho.id", "respondidaParcialmente"}, new Object[]{avaliacaoDesepenhoId, true});
	}
	
	public void ajustaColaboradorQuestionarioByAvDesempenho(Long avaliacaoDesempenhoId, Collection<ColaboradorQuestionario> colaboradorQuestionarios) {
		Collection<ColaboradorQuestionario> colaboradorQuestionariosExistentesAvDesempenho = findByAvaliacaoDesempenho(avaliacaoDesempenhoId, null);
		colaboradorQuestionarios.removeAll(Collections.singleton(null));
		
		for (ColaboradorQuestionario colaboradorQuestionarioExistenteAvDesempenho : colaboradorQuestionariosExistentesAvDesempenho){
			boolean existe = false;
			for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios) {
				if(colaboradorQuestionario.getId() != null && colaboradorQuestionario.getId().equals(colaboradorQuestionarioExistenteAvDesempenho.getId())){
					existe = true;
					break;
				}
			}
			
			if(!existe){
				getDao().remove(colaboradorQuestionarioExistenteAvDesempenho.getId());
				colaboradorQuestionarios.remove(colaboradorQuestionariosExistentesAvDesempenho);
			}
		}
		
		colaboradorQuestionarios.removeAll(colaboradorQuestionariosExistentesAvDesempenho);
		getDao().saveOrUpdate(colaboradorQuestionarios);
	}
}
