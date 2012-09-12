package com.fortes.rh.model.geral;


public class ConfiguracaoCaixasMensagens
{
    private Character[] caixasEsquerda;
    private Character[] caixasDireita;
    private Character[] caixasMinimizadas;
    
	public Character[] getCaixasEsquerda() 
	{
		return caixasEsquerda;
	}
	
	public void setCaixasEsquerda(Character[] caixasEsquerda) 
	{
		this.caixasEsquerda = caixasEsquerda;
	}
	
	public Character[] getCaixasDireita() 
	{
		return caixasDireita;
	}
	
	public void setCaixasDireita(Character[] caixasDireita) 
	{
		this.caixasDireita = caixasDireita;
	}
	
	public Character[] getCaixasMinimizadas() 
	{
		return caixasMinimizadas;
	}
	
	public void setCaixasMinimizadas(Character[] caixasMinimizadas) 
	{
		this.caixasMinimizadas = caixasMinimizadas;
	}
}