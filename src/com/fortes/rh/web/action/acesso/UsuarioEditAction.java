/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.LoginExisteException;
import com.fortes.rh.exception.SenhaNaoConfereException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class UsuarioEditAction extends MyActionSupportEdit
{
	private UsuarioManager usuarioManager;
	private PerfilManager perfilManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	private Usuario usuario;
	private UsuarioEmpresa usuarioEmpresa;
	private Colaborador colaborador;
	private Long colaboradorId;//da erro se colocar dentro do colaborador, Francisco Barroso
	private Empresa empresa;
	private Perfil perfil;

	private Object[][] listaEmpresas;
	private Collection<Empresa> empresas;
	private Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
	private Collection<Colaborador> colaboradores;

	private String[] empresasId;
	private String[] perfilsId;
	private String[] selectPerfils;

	private String confirmacao;
	private String mensagem;
	private String senhaPadrao;

	private String cpf;

	private Collection<Perfil> perfils;

	private String origem = "";
	private Long usuarioId;
	private String empresasNaoListadas;
	private boolean colaboradorPertenceEmpresaLogada = true;

	public String getOrigem()
	{
		return origem;
	}

	public void setOrigem(String origem)
	{
		this.origem = origem;
	}

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		usuario = getUsuario();
		
		if(usuario.getId() != null)
			usuario =  usuarioManager.findById(usuario.getId());

		if(colaborador != null && colaborador.getId() != null)
			colaboradorId = colaborador.getId();
		else if (usuario != null && usuario.getId() != null)
			colaboradorId = colaboradorManager.findByUsuario(usuario.getId());

		perfils = perfilManager.findAll(new String[]{"nome"});

		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		usuarioId = usuarioLogado.getId();
		if(usuarioLogado.isSuperAdmin())
			usuarioId = 1L;
		
		empresas = empresaManager.findByUsuarioPermissao(usuarioId, "ROLE_CAD_USUARIO", "ROLE_COLAB_LIST_CRIARUSUARIO");
		colaboradores = colaboradorManager.findSemUsuarios(getEmpresaSistema().getId(), usuario);
		
		if(colaboradorId != null) {
			colaboradorPertenceEmpresaLogada = colaboradorManager.pertenceEmpresa(colaboradorId, getEmpresaSistema().getId());
			colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradorId);
		}
		
		listaEmpresas = new Object[empresas.size()][2];
		int i = 0;
		for (Empresa emp: empresas)
		{
			listaEmpresas[i][0] = emp;
			listaEmpresas[i][1] = perfils;
			i++;
		}
	}

	public String prepareAutoInsert()
	{
		preparePerfilPadrao();
		return Action.SUCCESS;
	}

	private void preparePerfilPadrao()
	{
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findByIdProjection(1L);
		perfil = parametros.getPerfilPadrao();
	}

	public String createAuto()
	{
		try
		{
			usuarioManager.createAuto(getEmpresaSistema(), perfil, senhaPadrao);
			prepareAutoInsert();
			addActionMessage("Usuários criados com sucesso.");
			return Action.SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			e.printStackTrace();
			prepareAutoInsert();
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareAutoInsert();
			addActionMessage("Erro ao criar usuários.");
			return Action.INPUT;
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		preparePerfilPadrao();

		usuario.setAcessoSistema(true);

		if (colaboradorId != null && !colaboradorId.equals(""))
		{
			colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradorId);
			usuario.setLogin(colaborador.getPessoal().getCpf());
		}

		// Empresa do sistema e perfil padrão selecionados
		if(getPerfil() == null || getPerfil().getId() == null)
		{
			addActionMessage("Perfil padrão não selecionado nas configurações do sistema.");
			return Action.INPUT;
		}
		
		empresasId = new String[] { getEmpresaSistema().getId().toString() };
		selectPerfils  = new String[] { getPerfil().getId().toString() };

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if(usuario.getId().equals(1L)){
			addActionMessage("Usuário inexistente");
			usuario = null;
			return Action.SUCCESS;
		}
		
		prepare();

		empresasId = empresaManager.getEmpresasByUsuarioEmpresa(usuario.getId());
		usuarioEmpresas = empresaManager.getPerfilEmpresasByUsuario(usuario.getId());
		
		empresasNaoListadas = empresaManager.getEmpresasNaoListadas(usuarioEmpresas, empresas);

		return Action.SUCCESS;
	}

	public String prepareUpdateSenhaUsuario() throws Exception
	{
		usuario = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		usuario.setSenha(null);
		return Action.SUCCESS;
	}

	public String updateSenhaUsuario() throws Exception
	{
		Usuario usuarioSessao = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		usuario.setId(usuarioSessao.getId());

		if(usuario.getNovaSenha() == null || usuario.getNovaSenha().equals(""))
		{
			mensagem = "Campo \"Nova Senha\" vazio.";
			return Action.SUCCESS;
		}

		if(usuarioSessao.getSenha().equals(StringUtil.encodeString(usuario.getSenha())))
		{
			if(usuario.getNovaSenha().equals(usuario.getConfNovaSenha()))
			{
				usuarioManager.updateSenha(usuario);
				mensagem = "Sua senha foi alterada com sucesso.";
			}
			else
			{
				mensagem = "A senha não foi confirmada corretamente.";
			}
		}
		else
		{
			mensagem = "A senha informada não confere com a senha do seu login.";
		}

		return Action.SUCCESS;
	}

	public String prepareRecuperaSenha()throws Exception
	{
		empresas = empresaManager.findToList(new String[]{"id","nome"}, new String[]{"id","nome"}, new String[]{"nome"});
		return Action.SUCCESS;
	}

	public String recuperaSenha() throws Exception
	{
		addActionMessage(colaboradorManager.recuperaSenha(cpf, empresa));
		prepareRecuperaSenha();
		return Action.SUCCESS;
	}

	public String prepareCriarUsuario() throws Exception
	{
		colaborador = usuarioManager.prepareCriarUsuario(colaborador);
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if(empresasId == null || empresasId.length == 0)
		{
			addActionError("Selecione pelo menos uma empresa para este colaborador");
			prepareInsert();
			return Action.INPUT;
		}

		try
		{
			usuario.setLogin(usuario.getLogin().trim());
			String senha = usuario.getSenha();
			usuario = usuarioManager.save(usuario, colaboradorId, empresasId, selectPerfils);

			if (colaborador != null && colaborador.getId() != null) {
				colaborador = colaboradorManager.findByIdProjectionUsuario(colaboradorId);
				gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema(usuario.getLogin(), senha, colaborador.getContato().getEmail(), getEmpresaSistema());
				System.out.println(origem);
				
				if(origem.equals("C"))
					return "usuarioColaborador";
				else
					return Action.SUCCESS;
			} else
				return Action.SUCCESS;
		}
		catch (LoginExisteException e)
		{
			prepareInsert();
			addActionWarning(e.getMessage());
			return Action.INPUT;
		}
		catch (SenhaNaoConfereException e)
		{
			prepareInsert();
			addActionWarning(e.getMessage());
			return Action.INPUT;
		}
		catch (Exception e)
		{
			prepareInsert();
			e.printStackTrace();
			addActionError("Erro ao atualizar Usuário");
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try
		{
			usuario.setLogin(usuario.getLogin().trim());
			usuarioManager.update(usuario, colaboradorId, empresasId, selectPerfils);

			if (colaborador != null && colaborador.getId() != null)
				if(origem.equals("C"))
					return "usuarioColaborador";
				else 
					return Action.SUCCESS;
			else
				return Action.SUCCESS;
		}
		catch (LoginExisteException e)
		{
			prepareUpdate();
			addActionError(e.getMessage());
			return Action.INPUT;
		}
		catch (SenhaNaoConfereException e)
		{
			prepareUpdate();
			addActionError(e.getMessage());
			return Action.INPUT;
		}
		catch (Exception e)
		{
			prepareUpdate();
			e.printStackTrace();
			addActionError("Erro ao atualizar Usuário");
			return Action.INPUT;
		}
	}

	public Usuario getUsuario()
	{
		if(usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario=usuario;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager)
	{
		this.usuarioManager = usuarioManager;
	}

	public Collection<Perfil> getPerfils()
	{
		return perfils;
	}

	public void setPerfils(Collection<Perfil> perfils)
	{
		this.perfils = perfils;
	}

	public void setPerfilManager(PerfilManager perfilManager)
	{
		this.perfilManager = perfilManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public String getConfirmacao()
	{
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao)
	{
		this.confirmacao = confirmacao;
	}

	public String getMensagem()
	{
		return mensagem;
	}

	public void setMensagem(String mensagem)
	{
		this.mensagem = mensagem;
	}

	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public String[] getEmpresasId()
	{
		return empresasId;
	}

	public void setEmpresasId(String[] empresasId)
	{
		this.empresasId = empresasId;
	}

	public String[] getSelectPerfils()
	{
		return selectPerfils;
	}

	public void setSelectPerfils(String[] selectPerfils)
	{
		this.selectPerfils = selectPerfils;
	}

	public UsuarioEmpresa getUsuarioEmpresa()
	{
		return usuarioEmpresa;
	}

	public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa)
	{
		this.usuarioEmpresa = usuarioEmpresa;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Collection<UsuarioEmpresa> getUsuarioEmpresas()
	{
		return usuarioEmpresas;
	}

	public void setUsuarioEmpresas(Collection<UsuarioEmpresa> usuarioEmpresas)
	{
		this.usuarioEmpresas = usuarioEmpresas;
	}

	public String[] getPerfilsId()
	{
		return perfilsId;
	}

	public void setPerfilsId(String[] perfilsId)
	{
		this.perfilsId = perfilsId;
	}

	public Object[][] getListaEmpresas()
	{
		return listaEmpresas;
	}

	public void setListaEmpresas(Object[][] listaEmpresas)
	{
		this.listaEmpresas = listaEmpresas;
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		this.colaboradorId = colaboradorId;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
	
	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setPerfil(Perfil perfil)
	{
		this.perfil = perfil;
	}

	public Perfil getPerfil()
	{
		return perfil;
	}

	public void setSenhaPadrao(String senhaPadrao)
	{
		this.senhaPadrao = senhaPadrao;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public String getEmpresasNaoListadas() {
		return empresasNaoListadas;
	}

	public boolean isColaboradorPertenceEmpresaLogada() {
		return colaboradorPertenceEmpresaLogada;
	}
}