package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ColaboradorRespostaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		ColaboradorQuestionario colaboradorQuestionario = (ColaboradorQuestionario) metodo.getParametros()[1];
		Long usuarioId = (Long) metodo.getParametros()[2];
		Long candidatoId = (Long) metodo.getParametros()[3];
		
		metodo.processa();
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nMensagem: ");

		if (candidatoId != null)
		{
			dados.append("\nAs respostas foram salvas pelo candidato ID " + candidatoId + " pelo modulo externo");
		}
		else 
		{
			Usuario usuario = carregaUsuario(metodo, usuarioId);
			dados.append("\nAs respostas foram salvas pelo usuário ");
			dados.append(usuario.getNome());
			
		}
		ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) metodo.getComponente();
		Collection<ColaboradorResposta> colaboradorResposta = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		
		dados.append(".\n");
		dados.append(new GeraDadosAuditados(new Object[]{}, colaboradorQuestionario).gera());
		dados.append(montaColaboradorRespostas(colaboradorResposta));

		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Resposta", dados.toString());
	}
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable 
	{
		ColaboradorQuestionario colaboradorQuestionario = (ColaboradorQuestionario) metodo.getParametros()[1];
		Usuario usuario = carregaUsuario(metodo, (Long) metodo.getParametros()[2]);
		
		ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) metodo.getComponente();
		Collection<ColaboradorResposta> colaboradorRespostaAnterior = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		metodo.processa();
		Collection<ColaboradorResposta> colaboradorRespostaAtual = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nMensagem: ");
		dados.append("\nAs respostas foram salvas pelo usuário ");
		dados.append(usuario.getNome());
		dados.append(".\n");
		dados.append(new GeraDadosAuditados(new Object[]{}, colaboradorQuestionario).gera());
		dados.append("  [DADOS ANTERIORES]\n");
		dados.append(montaColaboradorRespostas(colaboradorRespostaAnterior));
		dados.append("\n");
		dados.append("  [DADOS ATUALIZADOS]\n");
		dados.append(montaColaboradorRespostas(colaboradorRespostaAtual));
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Resposta", dados.toString());
	}
	
	public Auditavel salvaQuestionarioRespondido(MetodoInterceptado metodo) throws Throwable 
	{
		
		ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) metodo.getComponente();
		QuestionarioManager questionarioManager = colaboradorRespostaManager.getQuestionarioManager();
		Questionario questionario = questionarioManager.findEntidadeComAtributosSimplesById(((Questionario) metodo.getParametros()[1]).getId());
		String respostas = (String) metodo.getParametros()[0];
		Long colaboradorQuestionarioId = (Long) metodo.getParametros()[6];

		StringBuilder dados = new StringBuilder();
		dados.append("\nMensagem: ");
		
		if (questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && ((Character) metodo.getParametros()[4]) == 'A')
		{
			CandidatoManager candidatoManager = colaboradorRespostaManager.getCandidatoManager();
			Candidato candidato = candidatoManager.findEntidadeComAtributosSimplesById((Long) metodo.getParametros()[2]);
		
			dados.append("\nAs respostas do(a) candidato(a) ");
			dados.append(candidato.getNome());
		}
		else
		{
			ColaboradorManager colaboradorManager = colaboradorRespostaManager.getColaboradorManager();
			Colaborador colaborador = colaboradorManager.findEntidadeComAtributosSimplesById((Long) metodo.getParametros()[2]);

			dados.append("\nAs respostas do(a) colaborador(a) ");
			dados.append(colaborador.getNome());
		}		
		
		dados.append(" para a ");
		dados.append(TipoQuestionario.getDescricao(questionario.getTipo()));
		dados.append(" ");
		dados.append(questionario.getTitulo());
		dados.append(" foram gravadas.\n");
		
		colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionarioId);
		if(colaboradorQuestionarioId != null){
			dados.append("  [DADOS ANTERIORES]\n");
			dados.append(montaColaboradorRespostas(colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionarioId)));
			dados.append("\n");
			dados.append("  [DADOS ATUALIZADOS]\n");
		}
		
		dados.append(montaColaboradorRespostas(respostas, questionario.getId(), questionarioManager));
		
		metodo.processa();

		return new AuditavelImpl(TipoQuestionario.getDescricaoMaisc(questionario.getTipo()), metodo.getOperacao(), "Respostas para a " + TipoQuestionario.getDescricao(questionario.getTipo()) + " " + questionario.getTitulo(), dados.toString());
	}
	
	private Usuario carregaUsuario(MetodoInterceptado metodo, Long usuarioId) 
	{
		ColaboradorRespostaManager manager = (ColaboradorRespostaManager) metodo.getComponente();
		return manager.findUsuarioParaAuditoria(usuarioId);
	}

	private StringBuilder montaColaboradorRespostas(Collection<ColaboradorResposta> colaboradorRespostas) {
		StringBuilder dados = new StringBuilder();
		dados.append("  colaboradorRespostas:[\n ");

		Long perguntaMultiplaEscolhaId = null;
		for (ColaboradorResposta colaboradorResposta : colaboradorRespostas) {
			
			if(!colaboradorResposta.getPergunta().getId().equals(perguntaMultiplaEscolhaId)){
				dados.append("	id: " + colaboradorResposta.getId() + "\n");
				dados.append("	Pergunta: \n");
				dados.append("		id: " + colaboradorResposta.getPergunta().getId() + "\n");
				dados.append("		ordem: " + colaboradorResposta.getPergunta().getOrdem() + "\n");
				dados.append("		Texto: " + colaboradorResposta.getPergunta().getTexto() + "\n");
			}
			dados.append("	Resposta: \n");
			switch (colaboradorResposta.getPergunta().getTipo()) {
			case TipoPergunta.NOTA:
				if(colaboradorResposta.getValor() == null)
					dados.append("		[Sem resposta]\n");
				else
					dados.append("		Nota:	" + colaboradorResposta.getValor() + "\n");
				
				dados.append("		Comentário:	" + colaboradorResposta.getComentario() + "\n");
				break;
			case TipoPergunta.SUBJETIVA:
				if(colaboradorResposta.getComentario() == null || colaboradorResposta.getComentario().isEmpty())
					dados.append("		[Sem resposta]\n");
				else
					dados.append("		Texto:	" + colaboradorResposta.getComentario() + "\n");
				break;
			case TipoPergunta.MULTIPLA_ESCOLHA:
				perguntaMultiplaEscolhaId = colaboradorResposta.getPergunta().getId();
				if(colaboradorResposta.getResposta().getTexto() == null)
					dados.append("		[Sem resposta]\n");
				else
					dados.append("		Texto:	" + colaboradorResposta.getResposta().getTexto() + "\n");
				dados.append("		Comentário:	" + colaboradorResposta.getComentario() + "\n");
				break;
			default:
				if(colaboradorResposta.getResposta().getTexto() == null)
					dados.append("		[Sem resposta]\n");
				else
					dados.append("		Texto:	" + colaboradorResposta.getResposta().getTexto() + "\n");
				
				dados.append("		Comentário:	" + colaboradorResposta.getComentario() + "\n");
				break;
			}
			dados.append("	,\n");
		}
		dados.append("  ]");
		return dados;
	}
	
	private StringBuilder montaColaboradorRespostas(String respostas, Long questionarioId, QuestionarioManager questionarioManager){
		StringBuilder dados = new StringBuilder();
		PerguntaManager perguntaManager = questionarioManager.getPerguntaManager();
		RespostaManager respostaManager = questionarioManager.getRespostaManager();
		Collection<Pergunta> perguntas = perguntaManager.findByQuestionario(questionarioId);
		
		LinkedHashMap<Long, Pergunta> mapPerguntas = new LinkedHashMap<Long, Pergunta>();
		
		for (Pergunta pergunta : perguntas) {
			if(pergunta != null && pergunta.getId() != null) mapPerguntas.put(pergunta.getId(), pergunta);
		}
		
		String[] perguntasRespostas = respostas.split("¨");
		Long perguntaId;
		Long respostaId;
		int contador = 0;
		dados.append("  colaboradorRespostas:[\n ");
		for(int i=0;i<perguntasRespostas.length;i++)
		{	
			if(perguntasRespostas[i].substring(0,2).equals("PG"))/**PG: É uma Pergunta**/
		    {
				perguntaId = Long.parseLong(perguntasRespostas[i].substring(2));
				dados.append("	Pergunta: \n");
				dados.append("		id: " + perguntaId + "\n");
				dados.append("		ordem: " + mapPerguntas.get(perguntaId).getOrdem() + "\n");
				dados.append("		Texto: " + mapPerguntas.get(perguntaId).getTexto() + "\n");
				
				contador=0;

				if(i+1 < perguntasRespostas.length){
					if(perguntasRespostas[i+1].substring(0,1).equals("R") && !perguntasRespostas[i+1].substring(0,2).equals("RC")){/**R: É uma Resposta**/
						contador += 1;
						dados.append("	Resposta: \n");
						
						switch (mapPerguntas.get(perguntaId).getTipo()) {
						
						case TipoPergunta.NOTA:
								if(perguntasRespostas[i+ contador].substring(2) == null || perguntasRespostas[i+ contador].substring(2).isEmpty())
									dados.append("		[Sem resposta]\n");
								else
									dados.append("		Nota:	" + perguntasRespostas[i+ contador].substring(2) + "\n");
							break;
							
						case TipoPergunta.SUBJETIVA:
								dados.append("		Texto:	" + perguntasRespostas[i + contador].substring(2) + "\n");
							break;
							
						case TipoPergunta.MULTIPLA_ESCOLHA:
								boolean respostaMultiplaEscolha = true;
								contador = 0;
								
								while (respostaMultiplaEscolha) {
									contador++;
									
									if(i+contador < perguntasRespostas.length){
										respostaId = (Long.parseLong(perguntasRespostas[i + contador].substring(2)));
										dados.append("		Texto:	" + respostaManager.findById(respostaId).getTexto() + "\n");
									}
									if((i+contador+1) >= perguntasRespostas.length || !perguntasRespostas[i+contador+1].substring(0,2).equals("RM"))
										respostaMultiplaEscolha = false;
								}
							break;

						default:
								respostaId = (Long.parseLong(perguntasRespostas[i+contador].substring(2)));
								dados.append("		Texto:	" + respostaManager.findById(respostaId).getTexto() + "\n");
							break;
						}
						i=i+contador;
					}
					else{
						dados.append("	Resposta: \n");
						dados.append("		[Sem resposta]\n");
					}
					
					if(( contador == 0 ? 1 : contador) < perguntasRespostas.length && perguntasRespostas[i+( perguntasRespostas.length > 2 ? ( contador == 0 ? 1 : contador) : 0)].substring(1,2).equals("C")){/**C: É um Comentário**/
							dados.append("		Comentário:	" + perguntasRespostas[i+ (contador == 0 ? 1 : contador)].substring(2) + "\n");
							i=i+contador+1;
					}
				}
				else{
					dados.append("	Resposta: \n");
					dados.append("		[Sem resposta]\n");
				}
				dados.append("	,\n");
		    }
		}
		dados.append("  ]");
		return dados;
	}
}