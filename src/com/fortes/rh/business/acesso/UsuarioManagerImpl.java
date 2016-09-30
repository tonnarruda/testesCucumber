/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32
 */

package com.fortes.rh.business.acesso;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.LoginExisteException;
import com.fortes.rh.exception.SenhaNaoConfereException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class UsuarioManagerImpl extends GenericManagerImpl<Usuario, UsuarioDao> implements UsuarioManager
{
	private ColaboradorManager colaboradorManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	@TesteAutomatico
	public Usuario findByLogin(String login)
	{
		return getDao().findByLogin(login);
	}
	
	@TesteAutomatico
	public Usuario findByIdProjection(Long usuarioId)
	{
		return getDao().findByIdProjection(usuarioId);
	}

	public Usuario save(Usuario usuario)
	{
		usuario.setSenha(StringUtil.encodeString(usuario.getSenha()));
		return getDao().save(usuario);
	}

	@Override
	public void update(Usuario usuario)
	{
		if(usuario.getSenha() == null || usuario.getSenha().equals(""))
		{
			Usuario usuarioTmp = findById(usuario.getId());
			usuario.setSenha(usuarioTmp.getSenha());
		}
		else
			usuario.setSenha(StringUtil.encodeString(usuario.getSenha()));

		getDao().update(usuario);
	}

	public Colaborador prepareCriarUsuario(Colaborador colaborador)
	{
		return colaboradorManager.findByIdProjectionUsuario(colaborador.getId());
	}

	public void updateSenha(Usuario usuario)
	{
		Usuario usuarioOriginal = getDao().findById(usuario.getId());
		if(!(usuario.getNovaSenha() == null || usuario.getNovaSenha().equals("")) && (usuarioOriginal.getSenha().equals(StringUtil.encodeString(usuario.getSenha()))))
		{
			if(usuario.getNovaSenha().equals(usuario.getConfNovaSenha()))
			{
				usuarioOriginal.setSenha(StringUtil.encodeString(usuario.getNovaSenha()));
				getDao().update(usuarioOriginal);
			}
		}
	}

	@TesteAutomatico
	public Collection findUsuarios(int page, int pagingSize,String nomeBusca,Empresa empresa)
	{
		return getDao().findUsuarios(page,pagingSize,nomeBusca,empresa);
	}

	@TesteAutomatico
	public Integer getCountUsuario(String nomeBusca, Empresa empresa)
	{
		return getDao().getCountUsuario(nomeBusca, empresa);
	}

	@TesteAutomatico
	public Collection<Usuario> findAllSelect()
	{
		return getDao().findAllSelect();
	}

	public Collection<Usuario> findAllBySelectUsuarioEmpresa(Long empresaId)
	{
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findAllBySelectUsuarioEmpresa(empresaId) ;
		Collection<Usuario> usuarios = new ArrayList<Usuario>();

		for (UsuarioEmpresa empresa : usuarioEmpresas)
		{
			usuarios.add(empresa.getUsuario());
		}
		return usuarios;
	}

	public void removeUsuario(Usuario usuario) throws Exception
	{
		try
		{
			//REMOVE PRIMEIRO USUARIOEMPRESA PARA NAO DAR ERRO DE FOREIGN KEY.
			Collection<UsuarioEmpresa> usuarioEmpresas =  usuarioEmpresaManager.find(new String[]{"usuario.id"},new Object[]{usuario.getId()});
			for (UsuarioEmpresa usuarioEmpresa : usuarioEmpresas)
			{
				usuarioEmpresaManager.remove(usuarioEmpresa.getId());
			}

			getDao().remove(new Long[]{usuario.getId()});

		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public boolean existeLogin(Usuario usuario)
	{
		Usuario usu = getDao().findByLogin(usuario);

		if(usu == null || usu.getId().equals(usuario.getId()))
			return false;

		return true;
	}

	public Collection<CheckBox> populaCheckOrderNome(Long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Usuario> usuarios = findAllBySelectUsuarioEmpresa(empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(usuarios, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	@TesteAutomatico(metodoMock="desativaAcessoSistema")
	public void removeAcessoSistema(Long... colaboradoresIds)
	{
		getDao().desativaAcessoSistema(true, colaboradoresIds);
	}
	
	@TesteAutomatico
	public void reativaAcessoSistema(Long colaboradorId)
	{
		getDao().reativaAcessoSistema(colaboradorId);
	}

	@TesteAutomatico
	public void desativaAcessoSistema(Long... colaboradoresIds)
	{
		getDao().desativaAcessoSistema(false, colaboradoresIds);
	}
	
	private void validaUsuario(Usuario usuario) throws LoginExisteException, SenhaNaoConfereException
	{
		if(existeLogin(usuario))
			throw new LoginExisteException();
		if(!usuario.confirmarSenha())
			throw new SenhaNaoConfereException();

		if (usuario.getColaborador() != null && usuario.getColaborador().getId() == null)
			usuario.setColaborador(null);
	}

	public Usuario save(Usuario usuario, Long colaboradorId, String[] empresaIds, String[] selectPerfils) throws LoginExisteException, SenhaNaoConfereException, Exception
	{
		validaUsuario(usuario);

		if(usuario.isSuperAdmin())
			getDao().desativaSuperAdmin();
			
		usuario = save(usuario);
		colaboradorManager.atualizarUsuario(colaboradorId, usuario.getId());
		if(colaboradorId != null)
			usuario.setColaborador(colaboradorManager.findByIdDadosBasicos(colaboradorId, null));
		usuarioEmpresaManager.save(usuario, empresaIds, selectPerfils);

		return usuario;
	}

	public void update(Usuario usuario, Long colaboradorId, String[] empresaIds, String[] selectPerfils) throws LoginExisteException, SenhaNaoConfereException, Exception
	{
		validaUsuario(usuario);
		
		if(usuario.isSuperAdmin())
			getDao().desativaSuperAdmin();

		update(usuario);
		colaboradorManager.atualizarUsuario(colaboradorId, usuario.getId());

		usuarioEmpresaManager.removeAllUsuario(usuario);
		if(colaboradorId != null)
			usuario.setColaborador(colaboradorManager.findByIdDadosBasicos(colaboradorId, null));
		usuarioEmpresaManager.save(usuario, empresaIds, selectPerfils);
	}

	public void createAuto(Empresa empresa, Perfil perfil, String senhaPadrao) throws Exception
	{
		Collection<Colaborador> colaboradores = colaboradorManager.findSemUsuarios(empresa.getId(), null);

		if(colaboradores.isEmpty())
			throw new ColecaoVaziaException("Não existe colaborador sem usuário.");

		for (Colaborador colaborador : colaboradores){
			Usuario usuario = findByLogin(colaborador.getPessoal().getCpf());
			if (usuario == null){
				usuario = new Usuario();
				usuario.setLogin(colaborador.getPessoal().getCpf());
				usuario.setAcessoSistema(true);
				usuario.setSenha(senhaPadrao);
				usuario.setNome(colaborador.getNome());
				save(usuario);
			}
			
			UsuarioEmpresa usuarioEmpresa = usuarioEmpresaManager.findByUsuarioEmpresa(usuario.getId(), empresa.getId());
			if (usuarioEmpresa == null){
				usuarioEmpresa = new UsuarioEmpresa();
				usuarioEmpresa.setUsuario(usuario);
				usuarioEmpresa.setEmpresa(empresa);
				usuarioEmpresa.setPerfil(perfil);
				usuarioEmpresaManager.save(usuarioEmpresa);
			}
			
			colaboradorManager.atualizarUsuario(colaborador.getId(), usuario.getId());
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema(usuario.getLogin(), senhaPadrao, colaborador.getContato().getEmail(), empresa);
		}
	}
	
	@TesteAutomatico
	public Collection<Empresa> findEmpresas(String usuarioNome) 
	{
		return getDao().findEmpresas(usuarioNome);
	}
	
	@TesteAutomatico
	public String[] findEmailsByUsuario(Long[] usuariosIds, String notEmail)
	{
		return getDao().findEmailsByUsuario(usuariosIds, notEmail);
	}

	@TesteAutomatico
	public void setUltimoLogin(Long id) 
	{
		getDao().setUltimoLogin(id);
	}

	@TesteAutomatico
	public Collection<Usuario> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
	
	@TesteAutomatico
	public void updateConfiguracoesMensagens(Long usuarioId, String caixasMensagens) 
	{
		getDao().updateConfiguracoesMensagens(usuarioId, caixasMensagens);
	}

	@TesteAutomatico
	public String[] findEmailsByPerfil(String role, Long empresaId) 
	{
		return getDao().findEmailsByPerfil(role, empresaId);
	}

	public Collection<Usuario> findByAreaEstabelecimento(Long[] areasIds, Long[] estabelecimentosIds)
	{
		return colaboradorManager.findUsuarioByAreaEstabelecimento(areasIds, estabelecimentosIds);
	}
	
	public String[] findEmailByPerfilAndGestor(String role, Long empresaId, Long areaOrganizacionalId, boolean isVerTodosColaboradores, String emailDesconsiderado)
	{
		Collection<AreaOrganizacional> todasAreas = areaOrganizacionalManager.findAllListAndInativas(true, null, empresaId);
		Collection<AreaOrganizacional> areaOrganizacionais = areaOrganizacionalManager.getAncestrais(todasAreas, areaOrganizacionalId);
		Collection<Long> areasIds = LongUtil.collectionToCollectionLong(areaOrganizacionais);
		
		return getDao().findEmailsByPerfilAndGestor(role, empresaId, areasIds, isVerTodosColaboradores, emailDesconsiderado);
	}
	
	@TesteAutomatico
	public boolean isResponsavelOrCoResponsavel(Long usuarioId){
		return getDao().isResponsavelOrCoResponsavel(usuarioId);
	}
	
	public String findEmailByUsuarioId(Long usuarioId) {
		return getDao().findEmailByUsuarioId(usuarioId);
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	
	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager)
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setAreaOrganizacionalManager( AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}