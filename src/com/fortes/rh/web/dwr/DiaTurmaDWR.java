package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.web.tags.CheckBox;

public class DiaTurmaDWR
{
	private DiaTurmaManager diaTurmaManager;
	private TurmaManager turmaManager;

	public Map getDias(String dataIniStr, String dataFimStr) throws Exception
	{
		Date dataIni = DateUtil.montaDataByString(dataIniStr);
		Date dataFim = DateUtil.montaDataByString(dataFimStr);

		if (dataIni.after(dataFim)) // verifica se dataIni maior q dataFim
			return null;

		Collection<DiaTurma> diasTurmas = diaTurmaManager.montaListaDias(dataIni, dataFim);

		return new CollectionUtil<DiaTurma>().convertCollectionToMap(diasTurmas,"getDescricao","getDescricao");
	}

	public String getDiasPorTurma(Long turmaId, String divName)
	{
		String[] properties = new String[] {"dataPrevIni","dataPrevFim"};
		String[] sets = new String[] {"dataPrevIni","dataPrevFim"};;
		String[] key = new String[] {"id"};;
		Object[] value = new Long[] {turmaId};

		Collection<Turma> turmas = turmaManager.findToList(properties, sets, key, value);

		Turma turma = (Turma) turmas.toArray()[0];

		Date dataIni = turma.getDataPrevIni();
		Date dataFim = turma.getDataPrevFim();

		Collection<CheckBox> diasCheckList = null;
		StringBuilder result = new StringBuilder();
		try
		{
			diasCheckList = CheckListBoxUtil.populaCheckListBox(diaTurmaManager.montaListaDias((Date)dataIni.clone(), dataFim),"getId", "getDescricao");
			Collection<DiaTurma> diasTurmaMarcados = diaTurmaManager.find(new String[] { "turma.id" }, new Object[] { turmaId });
			diasCheckList = CheckListBoxUtil.marcaCheckListBoxString(diasCheckList, diasTurmaMarcados, "getDescricao");

			for (CheckBox checkBox : diasCheckList)
			{
				String check = (checkBox.isSelecionado() ? "checked" : "");

				result.append("<label for=\"checkGroup"+ divName + checkBox.getNome() +"\" >");
				result.append("<input name=\""+ divName +"\" value=\""+ checkBox.getNome() +"\" type=\"checkbox\" id=\"checkGroup"+ divName + checkBox.getNome() + "\" " + check + "\">" + checkBox.getNome());
		    	result.append("</label><br>");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		if (dataIni.after(dataFim)) // verifica se dataIni maior q dataFim
			return null;

		return result.toString();
	}

	public void setDiaTurmaManager(DiaTurmaManager diaTurmaManager)
	{
		this.diaTurmaManager = diaTurmaManager;
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}
}
