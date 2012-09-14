package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.CaixaMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.geral.relatorio.MensagemVO;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.SpringUtil;

public class UsuarioMensagemManagerImpl extends GenericManagerImpl<UsuarioMensagem, UsuarioMensagemDao> implements UsuarioMensagemManager
{
	private MensagemManager mensagemManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	
	public Map<Character, CaixaMensagem> listaMensagens(Long usuarioId, Long empresaId, Long colaboradorId)
	{
		Map<Character, CaixaMensagem> caixasMensagens = new LinkedHashMap<Character, CaixaMensagem>();
		MensagemVO vo;
		char tp;
		TipoMensagem tipoMensagem = new TipoMensagem();
		
		for (char tipo : tipoMensagem.keySet())
			caixasMensagens.put(tipo, new CaixaMensagem(tipo, new ArrayList<MensagemVO>(), 0));
		
		Collection<UsuarioMensagem> usuarioMensagens = getDao().listaUsuarioMensagem(usuarioId, empresaId);
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
		
		QuestionarioManager questionarioManager = (QuestionarioManager) SpringUtil.getBean("questionarioManager");
		Collection<Questionario> questionarios = questionarioManager.findQuestionarioPorUsuario(usuarioId);
		for (Questionario questionario : questionarios) 
		{
			vo = new MensagemVO();
			vo.setTexto(questionario.getTitulo());
			vo.setLink("pesquisa/colaboradorResposta/prepareResponderQuestionario.action?questionario.id=" + questionario.getId() + "&colaborador.id=" + colaboradorId + "&tela=index&validarFormulario=true");
			vo.setTipo(TipoMensagem.PESQUISAS_AVAL_DISPONIVEIS);
			vo.setLida(true);
			
			caixasMensagens.get(TipoMensagem.PESQUISAS_AVAL_DISPONIVEIS).getMensagens().add(vo);
			caixasMensagens.get(TipoMensagem.PESQUISAS_AVAL_DISPONIVEIS).incrementaNaoLidas();
		}
		
		AvaliacaoDesempenhoManager avaliacaoDesempenhoManager = (AvaliacaoDesempenhoManager) SpringUtil.getBean("avaliacaoDesempenhoManager");
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = avaliacaoDesempenhoManager.findAllSelect(null, true, null);
		ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
		for (AvaliacaoDesempenho avaliacaoDesempenho : avaliacaoDesempenhos)
		{
			Collection<ColaboradorQuestionario> avaliadosComAvaliacaoPendente = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), colaboradorId, false, true);
			if (avaliadosComAvaliacaoPendente != null && !avaliadosComAvaliacaoPendente.isEmpty())
			{
				for (ColaboradorQuestionario colabQuestionarioAvaliado : avaliadosComAvaliacaoPendente)
				{
					vo = new MensagemVO();
					vo.setTexto(colabQuestionarioAvaliado.getAvaliacaoDesempenho().getTitulo() + " (" + colabQuestionarioAvaliado.getColaborador().getNome() + ") (" + colabQuestionarioAvaliado.getAvaliacaoDesempenho().getPeriodoFormatado() + ")");
					vo.setLink("avaliacao/desempenho/prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=" + colabQuestionarioAvaliado.getId());
					vo.setTipo(TipoMensagem.PESQUISAS_AVAL_DISPONIVEIS);
					vo.setLida(true);
					
					caixasMensagens.get(TipoMensagem.PESQUISAS_AVAL_DISPONIVEIS).getMensagens().add(vo);
					caixasMensagens.get(TipoMensagem.PESQUISAS_AVAL_DISPONIVEIS).incrementaNaoLidas();
				}
			}
		}
		
		Collection<ColaboradorQuestionario> colaboradorQuestionariosTeD = colaboradorQuestionarioManager.findQuestionarioByTurmaLiberadaPorUsuario(usuarioId);
		for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionariosTeD) 
		{
			vo = new MensagemVO();
			vo.setTexto(colaboradorQuestionario.getQuestionario().getTitulo());
			vo.setLink("pesquisa/colaboradorResposta/prepareResponderQuestionario.action?colaborador.id=" + colaboradorId + "&questionario.id=" + colaboradorQuestionario.getQuestionario().getId() + "&turmaId=" + colaboradorQuestionario.getTurma().getId() + "&voltarPara=../../index.action");
			vo.setTipo(TipoMensagem.AVALIACOES_TED);
			vo.setLida(true);
			
			caixasMensagens.get(TipoMensagem.AVALIACOES_TED).getMensagens().add(vo);
			caixasMensagens.get(TipoMensagem.AVALIACOES_TED).incrementaNaoLidas();
		}
		
		return caixasMensagens;
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

	public void saveMensagemAndUsuarioMensagem(String msg, String remetente, String link, Collection<UsuarioEmpresa> usuarioEmpresas, Colaborador colaborador, char tipoMensagem)
	{
		Mensagem mensagem = new Mensagem();
		mensagem.setData(new Date());

		mensagem.setTexto(msg);
		mensagem.setRemetente(remetente);
		mensagem.setLink(link);
		mensagem.setColaborador(colaborador);
		mensagem.setTipo(tipoMensagem);

		mensagem = mensagemManager.save(mensagem);

		for (UsuarioEmpresa usuarioEmpresa : usuarioEmpresas)
		{
			UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
			usuarioMensagem.setUsuario(usuarioEmpresa.getUsuario());
			usuarioMensagem.setMensagem(mensagem);
			usuarioMensagem.setEmpresa(usuarioEmpresa.getEmpresa());
			usuarioMensagem.setLida(false);

			save(usuarioMensagem);
		}
	}

	public void saveMensagemAndUsuarioMensagemRespAreaOrganizacional(String msg, String remetente, String link, Collection<Long> areasIds)
	{
		Collection<UsuarioEmpresa> usuariosResponsaveisAreaOrganizacionais = usuarioEmpresaManager.findUsuarioResponsavelAreaOrganizacional(areasIds);
		saveMensagemAndUsuarioMensagem(msg, remetente, link, usuariosResponsaveisAreaOrganizacionais, null, TipoMensagem.AVALIACAO_DESEMPENHO);
	}

	public void setMensagemManager(MensagemManager mensagemManager)
	{
		this.mensagemManager = mensagemManager;
	}

	public Long getAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao)
	{
		Integer count = getDao().getCount(new String[]{"empresa.id", "usuario.id"}, new Object[]{empresaId, usuarioId});
		if (count <= 1)
			return null;
		else
		{
			Long id = getDao().findAnteriorOuProximo(usuarioMensagemId, usuarioId, empresaId, opcao);
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

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
}