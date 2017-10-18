package com.fortes.rh.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.fortes.model.AbstractModel;
import com.fortes.web.tags.CheckBox;

/**
 * Classe utilitária para trabalhar com objetos com.fortes.web.tags.CheckBox
 * @author Francisco / Gustavo
 * @since 27/03/2007
 */
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
					if(boxTmp.getId().equals(marcadoTmp.trim()))
					{
						boxTmp.setSelecionado(true);
						break;
					}
			}
			listBoxMarcados.add(boxTmp);
		}

		return listBoxMarcados;
	}

	public static Collection<CheckBox> marcaCheckListBox(Collection<CheckBox> listCheckBox, Collection<? extends AbstractModel> models)
	{
		Collection<Object> ids = CollectionUtils.collect(models, TransformerUtils.invokerTransformer("getId"));
		Long[] marcados = (ids == null ? null : ids.toArray(new Long[]{}));
		
		return marcaCheckListBox(listCheckBox, marcados);
	}
	
	public static Collection<CheckBox> marcaCheckListBox(Collection<CheckBox> listCheckBox, final Long[] marcados)
	{
		if (ArrayUtils.isEmpty(marcados))
			return listCheckBox;
		
		Collection<CheckBox> listCheckBoxMarcados = new ArrayList<CheckBox>();
		listCheckBoxMarcados.addAll(listCheckBox);
		
		IterableUtils.forEach(listCheckBoxMarcados, new Closure<CheckBox>() {
			public void execute(CheckBox checkBox) {
				if(ArrayUtils.contains(marcados, Long.parseLong(checkBox.getId())))
					checkBox.setSelecionado(Boolean.TRUE);
			}
		});
		
		return listCheckBoxMarcados;
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
				String id = String.valueOf(mKey.invoke(ob, new Object[]{}));
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
	 * @param parameters TODO
	 * @return Collection de ListCheckBox
	 * @throws Exception */
	public static Collection<CheckBox> populaCheckListBox(Collection list, String methodKey, String methodValue, String[] parameters) throws Exception
	{
		//Collection que retornará os CheckListBox
		Collection<CheckBox> listBox = new ArrayList<CheckBox>();
		//metodo que retornará nome do check
		Method mKey   = null;
		//metodo que retornará value do check
		Method mValue = null;
		//metodo que retornará outros parametros para o check
		Method mParameter = null;

		//loop por elementos da Collection
		for (Object itemTmp : list)
		{
			CheckBox checkBox = new CheckBox();

			mKey   = itemTmp.getClass().getMethod(methodKey);
			mValue = itemTmp.getClass().getMethod(methodValue);

			if (parameters != null) {
				Map<String, String> checkParameters = new LinkedMap();
				for (String parameter : parameters) {
					mParameter = itemTmp.getClass().getMethod(parameter);
					Object parameterValue = mParameter.invoke(itemTmp, new Object[]{});
					if(parameterValue != null)
						checkParameters.put(parameter.replaceFirst("get", ""), (String) parameterValue.toString());
				}
				
				checkBox.setParameters(checkParameters);
			}
			
			checkBox.setId((Long) mKey.invoke(itemTmp, new Object[]{}));
			checkBox.setNome((String) mValue.invoke(itemTmp, new Object[]{}));
			checkBox.setSelecionado(false);
			
			listBox.add(checkBox);
		}
		return listBox;
	}

	public static Collection<CheckBox> populaCheckListBox(Map<String, String> dicionario) 
	{
		Collection<CheckBox> listBox = new ArrayList<CheckBox>();
		CheckBox checkBox = null;

		for (String key : dicionario.keySet())
		{
			checkBox = new CheckBox();
			checkBox.setId(key);
			checkBox.setNome(dicionario.get(key));
			checkBox.setTitulo(dicionario.get(key));
			checkBox.setSelecionado(false);

			listBox.add(checkBox);
		}
		return listBox;
	}

	public static Long[] retornaArrayLongId(Collection<CheckBox> listCheckBox)
	{
		Collection<Object> ids =  CollectionUtils.collect(listCheckBox, TransformerUtils.invokerTransformer("getId"));
		Long[] arrayIds = LongUtil.collectionStringToArrayLong(ids);
		
		return arrayIds;
	}

}