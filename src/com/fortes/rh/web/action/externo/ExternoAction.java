package com.fortes.rh.web.action.externo;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings( { "serial", "unchecked" })
public class ExternoAction extends MyActionSupport
{
	private static final String MSG_COD_CAD_SUCCESS = "0";
	private static final String MSG_COD_ATU_SUCCESS = "1";
	private static final String MSG_COD_ATU_SENHA_SUCCESS = "2";
	private static final String MSG_CAD_SUCCESS = "Dados cadastrados com sucesso.";
	private static final String MSG_ATU_SUCCESS = "Dados atualizados com sucesso.";
	private static final String MSG_CPF_BRANCO = "CPF não pode ser em branco.";
	private static final String MSG_CPF_NAO_CAD = "CPF não cadastrado.";
	private static final String MSG_SENHA_NAO_CONF = "Senha não confere.";
	private static final String MSG_SENHA_DIF = "A senha informada não confere com a senha do seu login.";
	private static final String MSG_SENHA_NAO_CONFIRMADA = "A senha não foi confirmada corretamente.";
	private static final String MSG_SENHA_ALT_SUCCESS = "Sua senha foi alterada com sucesso.";
	private static final String MSG_SENHA_VAZIA = "Campo \"Nova Senha\" vazio.";
	private static final String MSG_INF_LOGIN = "Dados cadastrados com sucesso.\\nInforme seu CPF e senha para ver as vagas disponívies.";

	private String cpf;
	private String senha;
	private String msg;
	private String mensagem;
	private Long empresaId;
	private String mensagemLogin;

	private Collection<Anuncio> anuncios = null;
	private AnuncioManager anuncioManager;
	private EmpresaManager empresaManager;

	private Candidato candidato;
	private CandidatoManager candidatoManager;

	private Solicitacao solicitacao;
	private Anuncio anuncio;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	private CandidatoSolicitacao candidatoSolicitacao;

	private boolean moduloExterno=true; // flag para regra em recuperaSenhaLogin

	private boolean sucessoEnvioCurriculo; // flag de alerta ftl
	

	public String checaLogin() throws Exception
	{
		if (cpf.equals(""))
		{
			msg = MSG_CPF_BRANCO;
			return Action.INPUT;
		}

		Map session = ActionContext.getContext().getSession();
		candidato = candidatoManager.findByCPF(cpf, null);

		if (candidato == null)
		{
			msg = MSG_CPF_NAO_CAD;
			return Action.INPUT;
		}

		if (!candidato.getSenha().equals(StringUtil.encodeString(senha)))
		{
			msg = MSG_SENHA_NAO_CONF;
			return Action.INPUT;
		}

		// coloca id do usuario na sessao
		session.put("SESSION_CANDIDATO_ID", candidato.getId());
		session.put("SESSION_CANDIDATO_SENHA", candidato.getSenha());
		session.put("SESSION_CANDIDATO_NOME", candidato.getNome());
		session.put("SESSION_CANDIDATO_CPF", candidato.getPessoal().getCpfFormatado());
		session.put("SESSION_EMPRESA", empresaId);

		return Action.SUCCESS;
	}

	public String logoutExterno() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		session.put("SESSION_CANDIDATO_ID", null);
		session.put("SESSION_CANDIDATO_SENHA", null);
		session.put("SESSION_CANDIDATO_NOME", null);
		session.put("SESSION_CANDIDATO_CPF", null);
		session.put("SESSION_EMPRESA", null);

		return Action.SUCCESS;
	}

	public String prepareUpdateSenha() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		if(session.get("SESSION_CANDIDATO_ID") == null)
			return Action.INPUT;

		return Action.SUCCESS;
	}

	public String prepareRecuperaSenha()throws Exception
	{
		return Action.SUCCESS;
	}

	public String recuperaSenha() throws Exception
	{
		Empresa empresa = empresaManager.findById(empresaId);

		if(empresa == null)
			addActionError("Empresa informada não identificada.");
		else
			addActionMessage(candidatoManager.recuperaSenha(StringUtil.removeMascara(cpf), empresa));

		return Action.SUCCESS;
	}

	public String updateExternoSenha() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Long id = (Long) session.get("SESSION_CANDIDATO_ID");
		String senhaSessao = (String) session.get("SESSION_CANDIDATO_SENHA");

		candidato.setId(id);

		if (candidato.getNovaSenha() == null || candidato.getNovaSenha().equals(""))
		{
			msg = MSG_SENHA_VAZIA;
			return Action.INPUT;
		}

		if (senhaSessao.equals(StringUtil.encodeString(candidato.getSenha())))
		{
			if (candidato.getNovaSenha().equals(candidato.getConfNovaSenha()))
			{
				candidatoManager.updateSenha(candidato);
				session.put("SESSION_CANDIDATO_SENHA", StringUtil.encodeString(candidato.getNovaSenha()));
			}
			else
			{
				msg = MSG_SENHA_NAO_CONFIRMADA;
				return Action.INPUT;
			}
		}
		else
		{
			msg = MSG_SENHA_DIF;
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String prepareListAnuncio() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		if(session.get("SESSION_CANDIDATO_ID") == null)
			return Action.INPUT;

		Long sessionEmpresa = (Long) session.get("SESSION_EMPRESA");
		anuncios = anuncioManager.findAnunciosSolicitacaoAberta(sessionEmpresa);

		if (msg != null)
		{
			if (msg.equals(MSG_COD_CAD_SUCCESS))
				msg = MSG_CAD_SUCCESS;
			else if (msg.equals(MSG_COD_ATU_SUCCESS))
				msg = MSG_ATU_SUCCESS;
			else if (msg.equals(MSG_COD_ATU_SENHA_SUCCESS))
				msg = MSG_SENHA_ALT_SUCCESS;
		}

		return Action.SUCCESS;
	}

	public String enviarCurriculo() throws Exception
	{
		boolean estaNaSolicitacao = false;

		Map session = ActionContext.getContext().getSession();

		Collection<CandidatoSolicitacao> candidatosTmp = candidatoSolicitacaoManager.find(new String[] { "candidato.id" }, new Object[] { session.get("SESSION_CANDIDATO_ID") });

		for (CandidatoSolicitacao solicitacaoAux : candidatosTmp)
		{
			if (solicitacaoAux.getSolicitacao().getId().equals(solicitacao.getId()))
			{
				estaNaSolicitacao = true;
				break;
			}
		}

		if (!estaNaSolicitacao)
		{
			candidato = new Candidato();
			candidato.setId((Long) session.get("SESSION_CANDIDATO_ID"));

			candidatoSolicitacao = new CandidatoSolicitacao();

			candidatoSolicitacao.setCandidato(candidato);
			candidatoSolicitacao.setCandidatoContratado(false);
			candidatoSolicitacao.setTriagem(true);
			candidatoSolicitacao.setSolicitacao(solicitacao);

			candidatoSolicitacaoManager.save(candidatoSolicitacao);

		}
		
		return Action.SUCCESS;
	}

	public String verAnuncio() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		if(session.get("SESSION_CANDIDATO_ID") == null)
			return Action.INPUT;

		anuncio = anuncioManager.findById(anuncio.getId());
		return Action.SUCCESS;
	}

	private boolean verificarArquivosExterno()
	{
		boolean retorno = false;
		String caminhoExterno = ArquivoUtil.getPathExterno();
		File dirExterno = new File(caminhoExterno);

		if (dirExterno.exists() && dirExterno.isDirectory())
		{
			File layout = new File(caminhoExterno + "layout.css");
			File logotipo = new File(caminhoExterno + "logotipo.png");

			retorno = (layout.exists() && logotipo.exists());
		}

		return retorno;
	}

	public String prepareLogin() throws Exception
	{
		mensagemLogin = empresaManager.findByIdProjection(empresaId).getMensagemModuloExterno();
		if (msg != null && msg.equals(MSG_COD_CAD_SUCCESS))
			msg = MSG_INF_LOGIN;

		if (!verificarArquivosExterno())
		{
			addActionError("Não foi possível localizar os arquivos de layout do módulo externo.");
			return "erro";
		}

		return Action.SUCCESS;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public CandidatoManager getCandidatoManager()
	{
		return candidatoManager;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public Collection getAnuncios()
	{
		return anuncios;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void setAnuncioManager(AnuncioManager anuncioManager)
	{
		this.anuncioManager = anuncioManager;
	}

	public Anuncio getAnuncio()
	{
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio)
	{
		this.anuncio = anuncio;
	}

	public CandidatoSolicitacao getCandidatoSolicitacao()
	{
		return candidatoSolicitacao;
	}

	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao)
	{
		this.candidatoSolicitacao = candidatoSolicitacao;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public String getMensagem()
	{
		return mensagem;
	}

	public void setMensagem(String mensagem)
	{
		this.mensagem = mensagem;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public boolean isModuloExterno()
	{
		return moduloExterno;
	}

	public boolean isSucessoEnvioCurriculo() {
		return sucessoEnvioCurriculo;
	}

	public void setSucessoEnvioCurriculo(boolean sucessoEnvioCurriculo) {
		this.sucessoEnvioCurriculo = sucessoEnvioCurriculo;
	}

	public String getMensagemLogin() {
		return mensagemLogin;
	}
}
