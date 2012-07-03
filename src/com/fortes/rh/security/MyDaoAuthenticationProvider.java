package com.fortes.rh.security;

import java.util.ArrayList;
import java.util.Collection;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.CollectionUtil;

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider
{
	private EmpresaManager empresaManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private PapelManager papelManager;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
	{
		Object salt = null;

		if (this.getSaltSource() != null)
		{
			salt = this.getSaltSource().getSalt(userDetails);
		}

		if (!this.getPasswordEncoder().isPasswordValid(userDetails.getPassword(), authentication.getCredentials().toString(), salt))
		{
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}

		Long empresaId = Long.parseLong(((UsernamePasswordEmpresaAuthenticationToken)authentication).getEmpresa());
		configuraPapeis(userDetails, empresaId);
	}
	
	public void configuraPapeis(UserDetails userDetails, Long empresaId) 
	{
		UsuarioEmpresa usuarioEmpresa = usuarioEmpresaManager.findByUsuarioEmpresa(((UserDetailsImpl)userDetails).getId(), empresaId);

		if(usuarioEmpresa != null || ((UserDetailsImpl)userDetails).getId() == 1L)
		{
			ParametrosDoSistema parametrosDoSistema =  parametrosDoSistemaManager.findByIdProjection(1L);
			
			papelManager.atualizarPapeis(parametrosDoSistema.getAtualizaPapeisIdsAPartirDe());
			
			String contexto = parametrosDoSistema.getAppContext();

			Collection<Papel> roles = new ArrayList<Papel>();
			
			//	Se for o super-usuario carrega todas os papeis para ele
			if(((UserDetailsImpl)userDetails).getId() == 1L)
			{
				roles = papelManager.findAll();
			}
			else
			{
				// pega os papeis a partir dos modulos permitidos
				Collection<Long> papeisPermitidos = papelManager.getPapeisPermitidos();

				for (Papel papel : usuarioEmpresa.getPerfil().getPapeis())
				{
					if (papeisPermitidos.contains(papel.getId()))
						roles.add(papel);
				}
			}

			GrantedAuthority[] arrayAuths = new GrantedAuthority[roles.size() + 1];
			roles = new CollectionUtil<Papel>().sortCollection(roles, "ordem");

			int index = 0;
			arrayAuths[index] = new GrantedAuthorityImpl("ROLE_USER");
			for (Papel role : roles)
			{
				if (StringUtils.isNotBlank(role.getCodigo()))
					arrayAuths[++index] = new GrantedAuthorityImpl(role.getCodigo());
			}

			Long idDoUsuario = ((UserDetailsImpl)userDetails).getId();
			Collection<Empresa> empresasDoUsuario = null;
			if (idDoUsuario == 1L)
				empresasDoUsuario = empresaManager.findTodasEmpresas();
			else
				empresasDoUsuario = empresaManager.findByUsuarioPermissao(idDoUsuario, new String[]{});
			
			Empresa empresa = empresaManager.findById(empresaId);
			
			String menu = Menu.getMenuFormatado(roles, contexto, parametrosDoSistema, empresasDoUsuario, empresa);
			
			((UserDetailsImpl)userDetails).setEmpresa(empresa);
			((UserDetailsImpl)userDetails).setAuthorities(arrayAuths);
			((UserDetailsImpl)userDetails).setMenuFormatado(menu);
		}
		else
		{
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}
	}
	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager)
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
	public void setPapelManager(PapelManager papelManager)
	{
		this.papelManager = papelManager;
	}

}
