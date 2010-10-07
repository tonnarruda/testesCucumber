package com.fortes.rh.security;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.ctc.wstx.util.DataUtil;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

public class UserDetailsServiceImpl implements UserDetailsService
{
	private UsuarioManager usuarioManager;
	private ColaboradorManager colaboradorManager;

	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException
 	{
 		Usuario user = usuarioManager.findByLogin(login);

		if(user != null)
		{
			Collection<Colaborador> colaboradors =  colaboradorManager.findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"usuario.id","desligado"}, new Object[]{user.getId(), true});

			if(colaboradors == null || colaboradors.size() > 0)
				throw new UsernameNotFoundException("Usuário '" + login + "' está desligado.");

			String ultimoLogin = "Último login: ";
			if (user.getUltimoLogin() != null)
				ultimoLogin += DateUtil.formataDate(user.getUltimoLogin(),"dd/MM/yyyy - HH:mm");
			else
				ultimoLogin = "";
		
			usuarioManager.setUltimoLogin (user.getId());
			
			return new UserDetailsImpl(user.getId(), user.getNome(), login, StringUtil.decodeString(user.getSenha()), ultimoLogin, null ,user.isAcessoSistema(),true,true,true,null,null, user.getColaborador());
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
}