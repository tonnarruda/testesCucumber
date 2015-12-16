package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.desenvolvimento.TurmaDocumentoAnexoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;
import com.fortes.rh.model.dicionario.FiltroSituacaoAvaliacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.CaixaMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.geral.relatorio.MensagemVO;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.ArrayUtil;
import com.fortes.rh.util.SpringUtil;

public class UsuarioMensagemManagerImpl extends GenericManagerImpl<UsuarioMensagem, UsuarioMensagemDao> implements UsuarioMensagemManager
{
	private MensagemManager mensagemManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	public Map<Character, CaixaMensagem> listaMensagens(Long usuarioId, Long empresaId, Long colaboradorId)
	{
		Map<Character, CaixaMensagem> caixasMensagens = new LinkedHashMap<Character, CaixaMensagem>();

		TipoMensagem tipoMensagemPermitidas = new TipoMensagem(true);
		Character[] arrayTipos = new Character[0];
		for (char tipo : tipoMensagemPermitidas.keySet())
		{
			arrayTipos = (Character[]) ArrayUtil.add(arrayTipos, tipo);
			caixasMensagens.put(tipo, new CaixaMensagem(tipo, new ArrayList<MensagemVO>(), 0));
		}

		ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");

		addMensagensTipoRES(empresaId, caixasMensagens, tipoMensagemPermitidas);
		
		addMensagensTipoPesquisa(usuarioId, colaboradorId, caixasMensagens, tipoMensagemPermitidas, colaboradorQuestionarioManager);
		
		addMensagensTipoAvaliacaoDesempenho(colaboradorId, caixasMensagens, tipoMensagemPermitidas, colaboradorQuestionarioManager);
		
		addMensagensTipoTED(usuarioId, colaboradorId, caixasMensagens, tipoMensagemPermitidas, colaboradorQuestionarioManager);

		addMensagensOutrosTipos(usuarioId, empresaId, caixasMensagens, arrayTipos);

		return caixasMensagens;
	}

	private void addMensagensOutrosTipos(Long usuarioId, Long empresaId, Map<Character, CaixaMensagem> caixasMensagens, Character[] arrayTipos)
	{
		MensagemVO vo;
		char tp;
		Collection<UsuarioMensagem> usuarioMensagens = new ArrayList<UsuarioMensagem>();
		if(arrayTipos != null && arrayTipos.length > 0)
			usuarioMensagens = getDao().listaUsuarioMensagem(usuarioId, empresaId, arrayTipos);

		for (UsuarioMensagem usuarioMensagem : usuarioMensagens) 
		{
			tp = usuarioMensagem.getMensagem().getTipo();

			vo = new MensagemVO();
			vo.setUsuarioMensagemId(usuarioMensagem.getId());
			vo.setRemetente(usuarioMensagem.getMensagem().getRemetente());
			vo.setData(usuarioMensagem.getMensagem().getData());
			vo.setTexto(usuarioMensagem.getMensagem().getTexto());
			vo.setTipo(tp);
			vo.setLink(usuarioMensagem.getMensagem().getLink());
			vo.setLida(usuarioMensagem.isLida());

			caixasMensagens.get(tp).getMensagens().add(vo); 
			if (!usuarioMensagem.isLida())
				caixasMensagens.get(tp).incrementaNaoLidas();
		}
	}

	private void addMensagensTipoAvaliacaoDesempenho(Long colaboradorId, Map<Character, CaixaMensagem> caixasMensagens, TipoMensagem tipoMensagemPermitidas, ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		MensagemVO vo;
		if(colaboradorId != null && tipoMensagemPermitidas.containsKey(TipoMensagem.AVALIACAO_DESEMPENHO))
		{
			AvaliacaoDesempenhoManager avaliacaoDesempenhoManager = (AvaliacaoDesempenhoManager) SpringUtil.getBean("avaliacaoDesempenhoManager");
			Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findAllSelect(null, true, null);
			for (AvaliacaoDesempenho avaliacaoDesempenho : avaliacaoDesempenhos)
			{
				Collection<ColaboradorQuestionario> avaliadosComAvaliacaoPendente = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), colaboradorId, FiltroSituacaoAvaliacao.NAO_RESPONDIDA.getOpcao(), true, true, true);
				if (avaliadosComAvaliacaoPendente != null && !avaliadosComAvaliacaoPendente.isEmpty())
				{
					for (ColaboradorQuestionario colabQuestionarioAvaliado : avaliadosComAvaliacaoPendente)
					{
						vo = new MensagemVO();
						vo.setTexto("Aval. Desempenho: " + colabQuestionarioAvaliado.getAvaliacaoDesempenho().getTitulo() + " (" + colabQuestionarioAvaliado.getColaborador().getNome() + ") (" + colabQuestionarioAvaliado.getAvaliacaoDesempenho().getPeriodoFormatado() + ")");
						vo.setLink("avaliacao/desempenho/prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=" + colabQuestionarioAvaliado.getId());
						vo.setData(colabQuestionarioAvaliado.getAvaliacaoDesempenho().getInicio());
						vo.setTipo(TipoMensagem.AVALIACAO_DESEMPENHO);
						vo.setRemetente("RH");

						caixasMensagens.get(TipoMensagem.AVALIACAO_DESEMPENHO).getMensagens().add(vo);
						caixasMensagens.get(TipoMensagem.AVALIACAO_DESEMPENHO).incrementaNaoLidas();
					}
				}
			}
		}
	}

	private void addMensagensTipoPesquisa(Long usuarioId, Long colaboradorId, Map<Character, CaixaMensagem> caixasMensagens, TipoMensagem tipoMensagemPermitidas, ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		if(colaboradorId != null)
		{
			MensagemVO vo;
			if(tipoMensagemPermitidas.containsKey(TipoMensagem.PESQUISAS))
			{
				QuestionarioManager questionarioManager = (QuestionarioManager) SpringUtil.getBean("questionarioManager");
				Collection<Questionario> questionarios = questionarioManager.findQuestionarioPorUsuario(usuarioId);
				for (Questionario questionario : questionarios) 
				{
					vo = new MensagemVO();
					vo.setTexto(questionario.getTitulo());
					vo.setLink("pesquisa/colaboradorResposta/prepareResponderQuestionario.action?questionario.id=" + questionario.getId() + "&colaborador.id=" + colaboradorId + "&tela=index&validarFormulario=true");
					vo.setTipo(TipoMensagem.PESQUISAS);

					caixasMensagens.get(TipoMensagem.PESQUISAS).getMensagens().add(vo);
					caixasMensagens.get(TipoMensagem.PESQUISAS).incrementaNaoLidas();
				}
			}
		}
	}
	
	private void addMensagensTipoTED(Long usuarioId, Long colaboradorId, Map<Character, CaixaMensagem> caixasMensagens, TipoMensagem tipoMensagemPermitidas, ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		if(colaboradorId != null)
		{
			MensagemVO vo;
			if(tipoMensagemPermitidas.containsKey(TipoMensagem.TED))
			{
				Collection<ColaboradorQuestionario> colaboradorQuestionariosTeD = colaboradorQuestionarioManager.findQuestionarioByTurmaLiberadaPorUsuario(usuarioId);
				for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionariosTeD) 
				{
					vo = new MensagemVO();
					vo.setTexto(colaboradorQuestionario.getNomeCursoTurmaAvaliacao());
					vo.setLink("pesquisa/colaboradorResposta/prepareResponderQuestionario.action?colaborador.id=" + colaboradorId + "&questionario.id=" + colaboradorQuestionario.getQuestionario().getId() + "&turmaId=" + colaboradorQuestionario.getTurma().getId() + "&voltarPara=../../index.action");
					vo.setTipo(TipoMensagem.TED);

					caixasMensagens.get(TipoMensagem.TED).getMensagens().add(vo);
					caixasMensagens.get(TipoMensagem.TED).incrementaNaoLidas();
				}
				
				TurmaDocumentoAnexoManager turmaDocumentoAnexoManager = (TurmaDocumentoAnexoManager) SpringUtil.getBean("turmaDocumentoAnexoManager");
				Collection<TurmaDocumentoAnexo> anexos = turmaDocumentoAnexoManager.findByColaborador(colaboradorId);
				for (TurmaDocumentoAnexo anexo : anexos) {
					vo = new MensagemVO();
					vo.setTexto( anexo.getDocumentoAnexos().getDescricao() + " disponível para download do curso " + anexo.getTurma().getCurso().getNome());
					vo.setLink("geral/documentoAnexo/showDocumento.action?documentoAnexo.id="+anexo.getDocumentoAnexos().getId());
					vo.setTipo(TipoMensagem.TED);
					vo.setAnexo(true);

					caixasMensagens.get(TipoMensagem.TED).getMensagens().add(vo);
					caixasMensagens.get(TipoMensagem.TED).incrementaNaoLidas();
				}
			}
		}
	}

	private void addMensagensTipoRES(Long empresaId, Map<Character, CaixaMensagem> caixasMensagens, TipoMensagem tipoMensagemPermitidas)
	{
		MensagemVO vo;
		if (tipoMensagemPermitidas.containsKey(TipoMensagem.RES))
		{
			SolicitacaoManager solicitacaoManager = (SolicitacaoManager) SpringUtil.getBean("solicitacaoManager");

			Collection<Solicitacao> solicitacaos = solicitacaoManager.findSolicitacaoList(empresaId, false, StatusAprovacaoSolicitacao.ANALISE, null);
			if (solicitacaos != null && solicitacaos.size() > 0) 
			{
				vo = new MensagemVO();
				vo.setTexto("Existem solicitações de pessoal aguardando liberação");
				vo.setLink("captacao/solicitacao/list.action?statusBusca=" + StatusAprovacaoSolicitacao.ANALISE);
				vo.setTipo(TipoMensagem.RES);

				caixasMensagens.get(TipoMensagem.RES).getMensagens().add(vo);
				caixasMensagens.get(TipoMensagem.RES).incrementaNaoLidas();
			}

			GerenciadorComunicacaoManager gerenciadorComunicacaoManager = (GerenciadorComunicacaoManager) SpringUtil.getBean("gerenciadorComunicacaoManager");

			if (gerenciadorComunicacaoManager.existeConfiguracaoParaCandidatosModuloExterno(empresaId))
			{
				Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoManager.findByFiltroSolicitacaoTriagem(true);
				for (CandidatoSolicitacao candidatoSolicitacao : candidatoSolicitacaos) 
				{
					vo = new MensagemVO();
					vo.setTexto("Currículo aguardando aprovação para " + candidatoSolicitacao.getSolicitacao().getDescricao());
					vo.setLink("captacao/candidatoSolicitacao/listTriagem.action?solicitacao.id=" + candidatoSolicitacao.getSolicitacao().getId());
					vo.setTipo(TipoMensagem.RES);

					caixasMensagens.get(TipoMensagem.RES).getMensagens().add(vo);
					caixasMensagens.get(TipoMensagem.RES).incrementaNaoLidas();
				}
			}
		}
	}

	public UsuarioMensagem findByIdProjection(Long usuarioMensagemId, Long empresaId)
	{
		return getDao().findByIdProjection(usuarioMensagemId, empresaId);
	}

	public Boolean possuiMensagemNaoLida(Long usuarioId, Long empresaId)
	{
		return getDao().possuiMensagemNaoLida(usuarioId, empresaId);
	}

	public void salvaMensagem(Empresa empresa, Mensagem mensagem, String[] usuariosCheck) throws Exception
	{
		try
		{
			for(int i=0; i < usuariosCheck.length; i++)
			{
				Usuario usuario = new Usuario();
				usuario.setId(Long.parseLong(usuariosCheck[i]));

				UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
				usuarioMensagem.setUsuario(usuario);
				usuarioMensagem.setMensagem(mensagem);
				usuarioMensagem.setEmpresa(empresa);
				usuarioMensagem.setLida(false);

				save(usuarioMensagem);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void saveMensagemAndUsuarioMensagem(String msg, String remetente, String link, Collection<UsuarioEmpresa> usuarioEmpresas, Colaborador colaborador, char tipoMensagem, Avaliacao avaliacao, Long notUsuarioId)
	{
		Mensagem mensagem = new Mensagem();
		mensagem.setData(new Date());

		mensagem.setTexto(msg);
		mensagem.setRemetente(remetente);
		mensagem.setLink(link);
		mensagem.setColaborador(colaborador);
		mensagem.setTipo(tipoMensagem);
		mensagem.setAvaliacao(avaliacao);

		mensagem = mensagemManager.save(mensagem);

		for (UsuarioEmpresa usuarioEmpresa : usuarioEmpresas)
		{
			if(notUsuarioId != null && notUsuarioId.equals(usuarioEmpresa.getUsuario().getId()))
				continue;
			
			UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
			usuarioMensagem.setUsuario(usuarioEmpresa.getUsuario());
			usuarioMensagem.setMensagem(mensagem);
			usuarioMensagem.setEmpresa(usuarioEmpresa.getEmpresa());
			usuarioMensagem.setLida(false);

			save(usuarioMensagem);
		}
	}

	public void saveMensagemAndUsuarioMensagemRespAreaOrganizacional(String msg, String remetente, String link, Collection<Long> areasIds, char tipoMensagem, Avaliacao avaliacao)
	{
		Collection<UsuarioEmpresa> usuariosResponsaveisAreaOrganizacionais = usuarioEmpresaManager.findUsuarioResponsavelAreaOrganizacional(areasIds);
		saveMensagemAndUsuarioMensagem(msg, remetente, link, usuariosResponsaveisAreaOrganizacionais, null, tipoMensagem, avaliacao, null);
	}

	public void saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(String mensagem, String remetente, String link, Collection<Long> areasIds, char tipoMensagem, Avaliacao avaliacao)
	{
		Collection<UsuarioEmpresa> usuariosCoResponsaveisAreaOrganizacionais = usuarioEmpresaManager.findUsuarioCoResponsavelAreaOrganizacional(areasIds);
		saveMensagemAndUsuarioMensagem(mensagem, remetente, link, usuariosCoResponsaveisAreaOrganizacionais, null, tipoMensagem, avaliacao, null);
	}

	public void setMensagemManager(MensagemManager mensagemManager)
	{
		this.mensagemManager = mensagemManager;
	}

	public Long getAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao, Character tipo)
	{
		Integer count = countMensagens(empresaId, usuarioId, tipo);
		if (count <= 1)
			return null;
		else
		{
			Long id = getDao().findAnteriorOuProximo(usuarioMensagemId, usuarioId, empresaId, opcao, tipo);
			return id;
		}
	}

	public void delete(UsuarioMensagem usuarioMensagem, Long[] usuarioMensagemIds)
	{
		if(usuarioMensagem != null && usuarioMensagem.getId() != null)
			remove(usuarioMensagem.getId());
		else
			if(usuarioMensagemIds != null && usuarioMensagemIds.length > 0)
				remove(usuarioMensagemIds);		
	}

	public Integer countMensagens(Long empresaId, Long usuarioId, Character tipo) 
	{
		return getDao().countMensagens(empresaId, usuarioId, tipo);
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) 
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}
}