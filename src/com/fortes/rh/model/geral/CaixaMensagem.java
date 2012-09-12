package com.fortes.rh.model.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.relatorio.MensagemVO;


public class CaixaMensagem
{
	private char tipo;
	private Collection<MensagemVO> mensagens;
	private int naoLidas;
	
	
	public CaixaMensagem() 	{ }
	
	public CaixaMensagem(char tipo, Collection<MensagemVO> mensagens, int naoLidas) 
	{
		this.tipo = tipo;
		this.mensagens = mensagens;
		this.naoLidas = naoLidas;
	}

	public char getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(char tipo) 
	{
		this.tipo = tipo;
	}
	
	public Collection<MensagemVO> getMensagens() 
	{
		return mensagens;
	}
	
	public void setMensagens(Collection<MensagemVO> mensagens) 
	{
		this.mensagens = mensagens;
	}
	
	public int getNaoLidas() 
	{
		return naoLidas;
	}
	
	public void setNaoLidas(int naoLidas) 
	{
		this.naoLidas = naoLidas;
	}
}