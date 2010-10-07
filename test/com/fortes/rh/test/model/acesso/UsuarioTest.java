package com.fortes.rh.test.model.acesso;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;



public class UsuarioTest extends TestCase
{
    public void testConfirmarSenha()
    {
    	Usuario usuario = UsuarioFactory.getEntity();

    	usuario.setSenha("1234");
    	usuario.setConfNovaSenha("1234");    	
    	assertEquals(true, usuario.confirmarSenha());
    	
    	usuario.setSenha(null);
    	usuario.setConfNovaSenha("1234");
    	assertEquals(false, usuario.confirmarSenha());

    	usuario.setSenha("1234");
    	usuario.setConfNovaSenha("123456");
    	assertEquals(false, usuario.confirmarSenha());
    }

}
