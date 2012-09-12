package com.fortes.rh.web.dwr;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.model.geral.ConfiguracaoCaixasMensagens;
import com.fortes.rh.util.StringUtil;

public class UsuarioDWR
{
	private UsuarioManager usuarioManager;
	
	public void gravarLayoutCaixasMensagens(Long usuarioId, Character[] caixasEsquerda, Character[] caixasDireita, Character[] caixasMinimizadas) throws Exception 
	{
		ConfiguracaoCaixasMensagens config = new ConfiguracaoCaixasMensagens();
		config.setCaixasEsquerda(caixasEsquerda);
		config.setCaixasDireita(caixasDireita);
		config.setCaixasMinimizadas(caixasMinimizadas);
		
		String configJson = StringUtil.toJSON(config, null);
		
		try {
			usuarioManager.updateConfiguracoesMensagens(usuarioId, configJson);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro ao gravar as configurações da página inicial");
		}
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) 
	{
		this.usuarioManager = usuarioManager;
	}
}
