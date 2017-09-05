package com.fortes.rh.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.UrlUtil;

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider
{
	private EmpresaManager empresaManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private PapelManager papelManager;
	private ColaboradorManager colaboradorManager;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException{
		Object salt = null;
		if (this.getSaltSource() != null)
			salt = this.getSaltSource().getSalt(userDetails);
		
		try {
			String credentials = "";
			Boolean captchaValido;
			Boolean acessoAoSistema;
			UsernamePasswordEmpresaAuthenticationToken usernameAuthentication = (UsernamePasswordEmpresaAuthenticationToken)authentication;
			UserDetailsImpl userDetailsImpl =  (UserDetailsImpl) userDetails;
			
			if(authentication.getPrincipal().toString().toUpperCase().equals("SOS")){
				credentials = Access.check(authentication.getCredentials().toString(), usernameAuthentication.getSOSSeed());
				if(credentials != null && !"".equals(credentials) && credentials.equals("Bad credentials"))
					throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentialsSOS", "Bad credentials"), userDetails);
				acessoAoSistema = captchaValido = true;
			}else{
				credentials = authentication.getCredentials().toString();
				captchaValido = checaCaptcha(usernameAuthentication);
				acessoAoSistema = userDetailsImpl.isAccountNonExpired();
			}
			
			if (!captchaValido || !this.getPasswordEncoder().isPasswordValid(userDetails.getPassword(), credentials, salt) || !acessoAoSistema)
				throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);

			if( userDetailsImpl.getColaborador() != null && userDetailsImpl.getColaborador().getId() != null ) {
				Colaborador colaborador = colaboradorManager.findByIdProjectionEmpresa(userDetailsImpl.getColaborador().getId());

				if ( colaborador.getDataAdmissao().getTime() > new Date().getTime() )
					throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
			}

			configuraPapeis(userDetails, Long.parseLong(usernameAuthentication.getEmpresa()));
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof BadCredentialsException)
				throw new BadCredentialsException(e.getMessage(), userDetails);
			else
				throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}
	}

	private boolean checaCaptcha(UsernamePasswordEmpresaAuthenticationToken usernameAuthentication) throws IOException {
		boolean captchaValido = true;
		
		if(parametrosDoSistemaManager.isUtilizarCaptchaNoLogin(1L)){
			boolean possueInternet = UrlUtil.pingUrl("http://www.google.com/");
			if(possueInternet)
				captchaValido = verificaCotexto(usernameAuthentication.getContexto()) && VerifyRecaptcha.verify(usernameAuthentication.getRecaptchaResponse());
		}

		return captchaValido;
	}
	
	private boolean verificaCotexto(String contexto)
	{
		return contexto.equals(parametrosDoSistemaManager.getContexto());
	}
	
	public void configuraPapeis(UserDetails userDetails, Long empresaId) throws Exception 
	{
		UsuarioEmpresa usuarioEmpresa = usuarioEmpresaManager.findByUsuarioEmpresa(((UserDetailsImpl)userDetails).getId(), empresaId);

		if(usuarioEmpresa != null || ((UserDetailsImpl)userDetails).getId() == 1L){
			ParametrosDoSistema parametrosDoSistema =  parametrosDoSistemaManager.findByIdProjection(1L);
			
			String contexto = parametrosDoSistema.getAppContext();
			String versao = parametrosDoSistema.getAppVersao();

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
					if (papeisPermitidos.contains(papel.getId()) || papel.isSemModulo())
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
			
			String menu = Menu.getMenuFormatado(roles, contexto, parametrosDoSistema, empresasDoUsuario, empresa, idDoUsuario);
			
			((UserDetailsImpl)userDetails).setEmpresa(empresa);
			((UserDetailsImpl)userDetails).setAuthorities(arrayAuths);
			((UserDetailsImpl)userDetails).setMenuFormatado(menu);
			((UserDetailsImpl)userDetails).setVersao(versao);
			((UserDetailsImpl)userDetails).setParametrosDoSistema(parametrosDoSistemaManager.findByIdProjectionSession(1L));
		}
		else{
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
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
