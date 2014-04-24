package com.fortes.rh.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

public class CampoExtraUtil
{
	public static Collection<ConfiguracaoCampoExtra> preencheConteudoCampoExtra(CamposExtras camposExtras, Collection<ConfiguracaoCampoExtra> confCampoExtra)
	{
		if (camposExtras != null)
		{
			for (ConfiguracaoCampoExtra configuracaoCampoExtra : confCampoExtra) {
				if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto1"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto1());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto2"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto2());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto3"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto3());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto4"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto4());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto5"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto5());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto6"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto6());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto7"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto7());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto8"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto8());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto9"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto9());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("texto10"))
					configuracaoCampoExtra.setConteudo(camposExtras.getTexto10());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("data1"))
					configuracaoCampoExtra.setConteudo(camposExtras.getData1String());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("data2"))
					configuracaoCampoExtra.setConteudo(camposExtras.getData2String());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("data3"))
					configuracaoCampoExtra.setConteudo(camposExtras.getData3String());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("valor1"))
					configuracaoCampoExtra.setConteudo(camposExtras.getValor1String());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("valor2"))
					configuracaoCampoExtra.setConteudo(camposExtras.getValor2String());
				else if(StringUtils.defaultString(configuracaoCampoExtra.getNome()).equals("numero1"))
					configuracaoCampoExtra.setConteudo(camposExtras.getNumero1String());
			}
		}
		
		return confCampoExtra;
	}

}