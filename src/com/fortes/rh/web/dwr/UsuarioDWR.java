package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.ConfiguracaoCaixasMensagens;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.ArrayUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

@Component
public class UsuarioDWR
{
	@Autowired
	private UsuarioManager usuarioManager;
	
	public void gravarLayoutCaixasMensagens(Long usuarioId, Character[] caixasEsquerda, Character[] caixasDireita, Character[] caixasMinimizadas) throws Exception 
	{
		TipoMensagem tipoMensagem = new TipoMensagem();
		Character[] caixas = (Character[]) ArrayUtil.addAll(caixasEsquerda, caixasDireita);

		if(tipoMensagem.size() != caixas.length)
		{
			for (Character tipo : tipoMensagem.keySet()) 
			{
				boolean contem = false;
				for (Character tipoCaixa : caixas) 
				{
					if(tipo.equals(tipoCaixa))
						contem = true;
				}
				
				if(!contem)
				{
					if(caixasEsquerda.length < 4)
						caixasEsquerda = (Character[]) ArrayUtil.add(caixasEsquerda, tipo);
					else
						caixasDireita = (Character[]) ArrayUtil.add(caixasDireita, tipo);
				}
			}
		}
		
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

	@SuppressWarnings("rawtypes")
	public Map getEmpresaUsuario(String usuarioNome)
	{
		Collection<Empresa> empresas = usuarioManager.findEmpresas(usuarioNome);
		return new CollectionUtil<Empresa>().convertCollectionToMap(empresas,"getId","getNome");
	}
	
	public Collection<Usuario> getByAreaOrganizacionalEstabelecimento(Long[] areasIds, Long[] estabelecimentosIds)
	{
		Collection<Usuario> usuarios = usuarioManager.findByAreaEstabelecimento(areasIds, estabelecimentosIds);
		return new CollectionUtil<Usuario>().sortCollectionStringIgnoreCase(usuarios, "nome"); 
	}
	
	public void setUsuarioManager(UsuarioManager usuarioManager) 
	{
		this.usuarioManager = usuarioManager;
	}
}
