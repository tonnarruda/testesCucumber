package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Empresa;

public class CartaoFactory
{
	public static Cartao getEntity()
	{
		Cartao cartao = new Cartao();
		cartao.setImgUrl("");
		cartao.setMensagem("");
		cartao.setTipoCartao("");
		return cartao;
	}
	
	public static Cartao getEntity(Empresa empresa)
	{
		Cartao cartao = getEntity();
		cartao.setEmpresa(empresa);

		return cartao;
	}
	
	public static Cartao getEntity(Long id, Empresa empresa)
	{
		Cartao cartao = getEntity();
		cartao.setId(id);
		cartao.setEmpresa(empresa);
		return cartao;
	}
	
	public static Cartao getEntity(Empresa empresa, String tipoCartao)
	{
		Cartao cartao = getEntity();
		cartao.setEmpresa(empresa);
		cartao.setTipoCartao(tipoCartao);
		return cartao;
	}
}
