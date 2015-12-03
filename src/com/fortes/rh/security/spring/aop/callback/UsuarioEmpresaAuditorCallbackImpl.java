package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class UsuarioEmpresaAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		Usuario usuario = (Usuario) metodo.getParametros()[0];
		String[] empresaIds = (String[]) metodo.getParametros()[1];
		String[] selectPerfis = (String[]) metodo.getParametros()[2];
		
		metodo.processa();
		
		String dados = "[DADOS INSERIDOS/ATUALIZADOS]\n";
		dados+="\nNome do usuário: " + usuario.getNome();
		dados+="\nLogin: " + usuario.getLogin();
		dados+="\nAtivo: " + (usuario.isAcessoSistema() ? "Sim" : "Não");
		dados+="\nSuper Administrador: " + (usuario.isSuperAdmin() ? "Sim" : "Não");
		
		if (usuario.getColaborador() != null)
			dados+="\nColaborador: " + usuario.getColaborador().getNome();
		
		if (empresaIds != null && selectPerfis != null)
		{
			dados+="\n\nEmpresas - Perfis: ";
			
			for (int i = 0; i < empresaIds.length; i++)
			{
				dados+="\nID Empresa: " + empresaIds[i];
				dados+="\nID Perfil: " + selectPerfis[i] + "\n";
			}
		}
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), usuario.getNome(), dados);
	}
}
