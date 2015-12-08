package com.fortes.rh.test.model.acesso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.TipoMensagem;
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

    public void testCaixasMensagensPadrao()
    {
    	Usuario usuario = UsuarioFactory.getEntity();
    	TipoMensagem tiposMensagem = new TipoMensagem();
    	List<Character> tiposUsuario = new ArrayList<Character>();
    	
    	Matcher matcher = Pattern.compile("'[A-Z]{1}'").matcher(usuario.getCaixasMensagens());
    	
    	while (matcher.find())
    	{
    		tiposUsuario.add(matcher.group().charAt(1));
			assertTrue("Existe caixa de mensagens do tipo " + matcher.group() + " em TipoMensagem", tiposMensagem.containsKey(matcher.group().charAt(1)));
    	}
    	
    	for (Character tipoMensagem : tiposMensagem.keySet())
    	{
    		assertTrue("Existe caixa de mensagens do tipo '" + tipoMensagem + "' em Usuario", tiposUsuario.contains(tipoMensagem));
    	}
    }
}
