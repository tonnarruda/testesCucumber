package com.fortes.rh.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import com.fortes.web.tags.CheckBox;

/**
 * Classe utilitária para trabalhar com objetos com.fortes.web.tags.CheckBox
 * @author Francisco / Gustavo
 * @since 27/03/2007
 */
@SuppressWarnings("unchecked")
public final class CheckListBoxUtil
{
	/**
	 * Quando esperamos receber os valore de um campo hidden ele não envia um array mas sim
	 * os valores separados por virgula. Essa função ler os valores e transforma em um array novamente.
	 * @param marcados - Array de String com o valores separados por virgula na primeira posição
	 * @return Array de String no formato correto
	 * @author Igo Coelho / Natanael Pantoja
	 * @since 05/01/2008
	 */
	private static String[] preapreArray(String[] marcados)
	{
		String[] resultado = marcados[0].split(",");
		for (int i = 0; i < resultado.length; i++)
			resultado[i] = resultado[i].trim();
		return resultado;
	}

	//Monta o prepareEdit
	public static Collection<CheckBox> marcaCheckListBox(Collection<CheckBox> listBox, String[] marcados)
	{
		String[] ar;

		if (marcados == null || marcados.length == 0)
			return listBox;

		if (marcados.length == 1 && marcados[0].indexOf(',') > 0)
			ar = preapreArray(marcados);
		else if (marcados.length == 1 && marcados[0].equals("[]"))
		{
			return listBox;
		}	
		else
			ar = marcados;

		Collection<CheckBox> listBoxMarcados = new ArrayList<CheckBox>();

		for (CheckBox boxTmp : listBox)
		{
			for (String marcadoTmp : ar)
			{
				if (marcadoTmp != null && !marcadoTmp.trim().equals(""))
					if(boxTmp.getId().equals(Long.valueOf(marcadoTmp.trim())) )
					{
						boxTmp.setSelecionado(true);
						break;
					}
			}
			listBoxMarcados.add(boxTmp);
		}

		return listBoxMarcados;
	}

	public static Collection<CheckBox> marcaCheckListBox(Collection<CheckBox> listBox, Collection list, String methodKey) throws Exception
	{
		if (list == null || list.isEmpty())
			return listBox;

		Collection<CheckBox> listBoxMarcados = new ArrayList<CheckBox>();

		Method mKey = null;

		for (CheckBox cb : listBox)
		{
			for (Object ob : list)
			{
				mKey = ob.getClass().getMethod(methodKey);
				Long id = (Long) mKey.invoke(ob, new Object[]{});
				if (cb.getId().equals(id))
					cb.setSelecionado(true);
			}

			listBoxMarcados.add(cb);
		}

		return listBoxMarcados;
	}

	public static Collection<CheckBox> marcaCheckListBoxString(Collection<CheckBox> listBox, Collection list, String methodKey) throws Exception
	{
		if (list == null || list.isEmpty())
			return listBox;

		Collection<CheckBox> listBoxMarcados = new ArrayList<CheckBox>();

		Method mKey = null;

		for (CheckBox cb : listBox)
		{
			for (Object ob : list)
			{
				mKey = ob.getClass().getMethod(methodKey);
				String texto = (String) mKey.invoke(ob, new Object[]{});
				if (cb.getNome().equals(texto))
					cb.setSelecionado(true);
			}

			listBoxMarcados.add(cb);
		}

		return listBoxMarcados;
	}

	/**
	 * Método que cria uma Collection de ListCheckBox de uma Collection de Objetos
	 * @param list
	 * @param methodKey
	 * @param methodValue
	 * @return Collection de ListCheckBox
	 * @throws Exception */
	public static Collection<CheckBox> populaCheckListBox(Collection list, String methodKey, String methodValue) throws Exception
	{
		//Collection que retornará os CheckListBox
		Collection<CheckBox> listBox = new ArrayList<CheckBox>();
		//metodo que retornará nome do check
		Method mKey   = null;
		//metodo que retornará value do check
		Method mValue = null;

		//loop por elementos da Collection
		for (Object itemTmp : list)
		{
			CheckBox checkBox = new CheckBox();

			mKey   = itemTmp.getClass().getMethod(methodKey);
			mValue = itemTmp.getClass().getMethod(methodValue);

			checkBox.setId((Long) mKey.invoke(itemTmp, new Object[]{}));
			checkBox.setNome((String) mValue.invoke(itemTmp, new Object[]{}));
			checkBox.setSelecionado(false);

			listBox.add(checkBox);
		}
		return listBox;
	}
}