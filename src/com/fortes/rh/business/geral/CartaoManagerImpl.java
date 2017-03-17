package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CartaoDao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.ArquivoUtil;

@Component
public class CartaoManagerImpl extends GenericManagerImpl<Cartao, CartaoDao> implements CartaoManager
{
	@Autowired
	public CartaoManagerImpl(CartaoDao cartaoDao) {
		setDao(cartaoDao);
	}
	
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
			File logoSalva = ArquivoUtil.salvaArquivo(local, cartao.getFile(), true);

			if(logoSalva != null)
				cartao.setImgUrl(logoSalva.getName());
		}

		return cartao;
	}
	
	public DataSource[] geraCartao(Cartao cartao, Colaborador colaborador){
		try {
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("SUBREPORT_DIR", ArquivoUtil.getPathReport());
			parametros.put("BACKGROUND", ArquivoUtil.getPathBackGroundCartao(cartao.getImgUrl()));
			
			String mensagem = "";
			if(cartao.getMensagem() != null)
				mensagem = cartao.getMensagem();

			parametros.put("MSG", mensagem.replaceAll("#NOMECOLABORADOR#", colaborador.getNome()));
			Collection<Colaborador> colaboradoresTemp = Arrays.asList(colaborador);
			
			return ArquivoUtil.montaRelatorio(parametros, colaboradoresTemp, "cartaoAniversariante.jasper");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}