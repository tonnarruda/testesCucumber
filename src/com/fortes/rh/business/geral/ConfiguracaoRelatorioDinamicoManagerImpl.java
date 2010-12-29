package com.fortes.rh.business.geral;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.dao.geral.ConfiguracaoRelatorioDinamicoDao;

public class ConfiguracaoRelatorioDinamicoManagerImpl extends GenericManagerImpl<ConfiguracaoRelatorioDinamico, ConfiguracaoRelatorioDinamicoDao> implements ConfiguracaoRelatorioDinamicoManager
{

	public void update(String campos, String titulo, Long usuarioId) 
	{
		getDao().removeByUsuario(usuarioId);
		
		ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico = new ConfiguracaoRelatorioDinamico();
		configuracaoRelatorioDinamico.setCampos(campos);
		configuracaoRelatorioDinamico.setTitulo(titulo);
		
		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);
		
		configuracaoRelatorioDinamico.setUsuario(usuario);
		save(configuracaoRelatorioDinamico);
	}

	public ConfiguracaoRelatorioDinamico findByUsuario(long usuarioId) 
	{
		return getDao().findByUsuario(usuarioId);
	}
}
