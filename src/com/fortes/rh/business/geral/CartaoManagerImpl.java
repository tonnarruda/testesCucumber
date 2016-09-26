package com.fortes.rh.business.geral;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CartaoDao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.ArquivoUtil;

public class CartaoManagerImpl extends GenericManagerImpl<Cartao, CartaoDao> implements CartaoManager
{
	public Cartao findByEmpresaIdAndTipo(Long empresaId, String tipoCartao) {
		return getDao().findByEmpresaIdAndTipo(empresaId, tipoCartao);
	}
	
	public Collection<Cartao> findByEmpresaId(Long empresaId) {
		return getDao().findByEmpresaId(empresaId);
	}
	
	public Cartao saveImagemCartao(Cartao cartao, String local)
	{
		cartao.setImgUrl("");

		if(cartao.getFile() != null && !cartao.getFile().getName().equals(""))
		{
			java.io.File logoSalva = ArquivoUtil.salvaArquivo(local, cartao.getFile(), true);

			if(logoSalva != null)
				cartao.setImgUrl(logoSalva.getName());
		}

		return cartao;
	}
	
	public DataSource[] geraCartao(Cartao cartao, Colaborador colaborador){
		char barra = java.io.File.separatorChar;
		
		String path = ArquivoUtil.getSystemConf().getProperty("sys.path").trim();
		path = path + barra + "WEB-INF" + barra + "report" + barra; 
		Map<String,Object> parametros = new HashMap<String, Object>();
		parametros.put("SUBREPORT_DIR", path);
		
		String pathBackGroundRelatorio = "";
		String pathLogo = ArquivoUtil.getPathLogoEmpresa() + cartao.getImgUrl();
		java.io.File logo = new java.io.File(pathLogo);
		if(logo.exists())
			pathBackGroundRelatorio = pathLogo;

		parametros.put("BACKGROUND", pathBackGroundRelatorio);
		String mensagem = "";

		if(cartao.getMensagem()!= null)
			mensagem = cartao.getMensagem();
		
		parametros.put("MSG", mensagem.replaceAll("#NOMECOLABORADOR#", colaborador.getNome()));
		Collection<Colaborador> colaboradoresTemp = Arrays.asList(colaborador);
		try {
			return ArquivoUtil.montaRelatorio(parametros, colaboradoresTemp, "cartaoAniversariante.jasper");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}