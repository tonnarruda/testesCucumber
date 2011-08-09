package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.util.DateUtil;

public class AvaliacaoManagerImpl extends GenericManagerImpl<Avaliacao, AvaliacaoDao> implements AvaliacaoManager
{
	private PerguntaManager perguntaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private ColaboradorManager colaboradorManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private UsuarioMensagemManager usuarioMensagemManager;
	
	private QuestionarioManager questionarioManager;
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	
	public Collection<Avaliacao> findAllSelect(Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo) 
	{	
		return getDao().findAllSelect(empresaId, ativo, modeloAvaliacao, titulo);
	}
	
	public Collection<QuestionarioRelatorio> getQuestionarioRelatorio(Avaliacao avaliacao) {
		
		Collection<Pergunta> perguntas = perguntaManager.getPerguntasRespostaByQuestionario(avaliacao.getId());
		
		//trata as perguntas para substituir #AVALIADO# por AVALIADO (para impressão)
		for (Pergunta pergunta : perguntas)
			perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta, "AVALIADO");

        QuestionarioRelatorio questionarioRelatorio = new QuestionarioRelatorio();
        questionarioRelatorio.setAvaliacaoExperiencia(avaliacao);
        questionarioRelatorio.setPerguntas(perguntas);

        Collection<QuestionarioRelatorio> questionarioRelatorios = new ArrayList<QuestionarioRelatorio>();
        questionarioRelatorios.add(questionarioRelatorio);

        return questionarioRelatorios;
	}

	public void enviaLembrete() {
		
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaManager.findAll();
		Collection<Integer> diasLembrete = parametrosDoSistemaManager.getDiasLembretePeriodoExperiencia();
		
		for (Integer diaLembrete : diasLembrete)
		{
			for (PeriodoExperiencia periodoExperiencia : periodoExperiencias)
			{
				Integer dias = periodoExperiencia.getDias() - diaLembrete;
				if (dias <= 0)
					continue;
				
				Calendar dataAvaliacao = Calendar.getInstance();
				dataAvaliacao.add(Calendar.DAY_OF_YEAR, +diaLembrete);
				
				Collection<Colaborador> colaboradores = null;
				
				try {
					colaboradores = colaboradorManager.findAdmitidosHaDias(dias, periodoExperiencia.getEmpresa());
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				
				enviaLembrete(colaboradores, periodoExperiencia.getEmpresa(), diaLembrete, periodoExperiencia.getDias(), dataAvaliacao.getTime());
			}
		}
	}
	
	private void enviaLembrete(Collection<Colaborador> colaboradores, Empresa empresa, Integer diaLembrete, Integer diasAvaliacao, Date dataAvaliacao) 
	{
		Collection<UsuarioEmpresa> usuarioEmpresasPeriodoExperiencia = usuarioEmpresaManager.findUsuariosByEmpresaRoleAvaliacaoExperiencia(empresa.getId(), "RECEBE_MSG_PERIODOEXPERIENCIA");		
		Collection<UsuarioEmpresa> usuarioEmpresasPeriodoExperienciaGerencial = usuarioEmpresaManager.findUsuariosByEmpresaRoleAvaliacaoExperiencia(empresa.getId(), "GERENCIA_MSG_PERIODOEXPERIENCIA");		
		
		String data = DateUtil.formataDiaMesAno(dataAvaliacao);
		
		for (Colaborador colaborador : colaboradores)
		{
			StringBuilder mensagem = new StringBuilder();
			mensagem.append("Período de Experiência: ")
					.append(diaLembrete)
					.append(" dias para a Avaliação de ").append(diasAvaliacao).append(" dias de ")
					.append(colaborador.getNome()).append(".\n\n");
			
			mensagem.append("Lembrete de Avaliação de ")
					.append(diasAvaliacao)
					.append(" dias do Período de Experiência.\n")
					.append("\nColaborador: ").append(colaborador.getNome());
			
			if (StringUtils.isNotBlank(colaborador.getNomeComercial()))
				mensagem.append(" (").append(colaborador.getNomeComercial()).append(") ");
			
			mensagem.append("\nCargo: ").append(colaborador.getFaixaSalarial().getDescricao())
					.append("\nÁrea: ").append(colaborador.getAreaOrganizacional().getDescricao())
					.append("\nData da avaliação: ").append(data);
			
			String link = "avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action?colaborador.id=" + colaborador.getId();
			
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", link, usuarioEmpresasPeriodoExperienciaGerencial);
			usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(mensagem.toString(), "RH", link, usuarioEmpresasPeriodoExperiencia, colaborador.getAreaOrganizacional().getDescricaoIds());
		}
	}
	
	public Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] areaIds, Date periodoIni, Date periodoFim, Avaliacao avaliacao) throws Exception
    {
        Collection<ResultadoQuestionario> resultadoQuestionarios = new ArrayList<ResultadoQuestionario>();
        Collection<Resposta> respostas = respostaManager.findInPerguntaIds(perguntasIds);
        Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findInPerguntaIds(perguntasIds, null, areaIds, periodoIni, periodoFim, null, null);

        if(colaboradorRespostas.isEmpty())
        	throw new Exception("Nenhuma pergunta foi respondida.");
        
        Collection<QuestionarioResultadoPerguntaObjetiva> percentuaisDeRespostas = colaboradorRespostaManager.calculaPercentualRespostas(perguntasIds, null, areaIds, periodoIni, periodoFim, null);
        
        avaliacao.setTotalColab(questionarioManager.countColaborador(colaboradorRespostas)); 
        Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultiplas = colaboradorRespostaManager.calculaPercentualRespostasMultipla(perguntasIds, null, areaIds, periodoIni, periodoFim, null, avaliacao.getTotalColab());
        percentuaisDeRespostas.addAll(calculaPercentualRespostasMultiplas);
        
        resultadoQuestionarios = questionarioManager.montaResultadosQuestionarios(perguntas, respostas, colaboradorRespostas, percentuaisDeRespostas, false);
        
        return resultadoQuestionarios;
    }
	
	public Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId) {

		return getDao().getPontuacaoMaximaDaPerformance(avaliacaoId);
	}

	public void setPerguntaManager(PerguntaManager perguntaManager) {
		this.perguntaManager = perguntaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager) {
		this.periodoExperienciaManager = periodoExperienciaManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager) {
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager) {
		this.questionarioManager = questionarioManager;
	}

	public void setRespostaManager(RespostaManager respostaManager) {
		this.respostaManager = respostaManager;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager) {
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public void clonar(Long id)
	{
		Avaliacao avaliacao = (Avaliacao) getDao().findById(id).clone();
		avaliacao.setTitulo(avaliacao.getTitulo() + "(Clone)");
		avaliacao.setId(null);
		save(avaliacao);
		
		perguntaManager.clonarPergunta(id, null, avaliacao);
	}

	public Collection<Avaliacao> findPeriodoExperienciaIsNull(char acompanhamentoExperiencia, Long empresaId) 
	{
		return getDao().findPeriodoExperienciaIsNull(acompanhamentoExperiencia, empresaId);
	}
}
