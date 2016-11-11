package com.fortes.rh.security;

import java.util.Collection;

//import org.acegisecurity.userdetails.UserDetails;
//import org.acegisecurity.userdetails.UserDetailsService;
//import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.StringUtil;

@Component
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private UsuarioManager usuarioManager;
	@Autowired
	private ColaboradorManager colaboradorManager;
	@Autowired
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException
 	{
 		Usuario user = usuarioManager.findByLogin(login);

		if(user != null)
		{ 
			Collection<Colaborador> colaboradors =  colaboradorManager.findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"usuario.id","desligado"}, new Object[]{user.getId(), true});

			if(colaboradors == null || colaboradors.size() > 0)
				throw new UsernameNotFoundException("Usuário '" + login + "' está desligado.");

			usuarioManager.setUltimoLogin(user.getId());
			
			return new UserDetailsImpl(user.getId(), user.getNome(), login, StringUtil.decodeString(user.getSenha()), user.isSuperAdmin(), user.getUltimoLogin(), 
										null, user.isAcessoSistema(), true, true, true, null, null, user.getColaborador(), parametrosDoSistemaManager.findByIdProjectionSession(1L));
		}
		else
			throw new UsernameNotFoundException("Usuário '" + login + "' não cadastrado...");
	}

 	public void setUsuarioManager(UsuarioManager usuarioManager)
 	{
 		this.usuarioManager = usuarioManager;
 	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}