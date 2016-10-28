package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

@Component
public class CamposExtrasManagerImpl extends GenericManagerImpl<CamposExtras, CamposExtrasDao> implements CamposExtrasManager
{
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	
	@Autowired
	CamposExtrasManagerImpl(CamposExtrasDao dao) {
		setDao(dao);
	}
	
	public CamposExtras update(CamposExtras camposExtras, Long camposExtrasId, Long empresaId)
	{
		if(camposExtrasId != null)
		{
			Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, empresaId}, new String[]{"ordem"});
			CamposExtras camposExtrasTemp = findById(camposExtrasId);

			for (ConfiguracaoCampoExtra configuracaoCampoExtra : configuracaoCampoExtras)
			{
				if(configuracaoCampoExtra.getNome().equals("texto1"))
					camposExtrasTemp.setTexto1(camposExtras.getTexto1());
				if(configuracaoCampoExtra.getNome().equals("texto2"))
					camposExtrasTemp.setTexto2(camposExtras.getTexto2());
				if(configuracaoCampoExtra.getNome().equals("texto3"))
					camposExtrasTemp.setTexto3(camposExtras.getTexto3());
				if(configuracaoCampoExtra.getNome().equals("texto4"))
					camposExtrasTemp.setTexto4(camposExtras.getTexto4());
				if(configuracaoCampoExtra.getNome().equals("texto5"))
					camposExtrasTemp.setTexto5(camposExtras.getTexto5());
				if(configuracaoCampoExtra.getNome().equals("texto6"))
					camposExtrasTemp.setTexto6(camposExtras.getTexto6());
				if(configuracaoCampoExtra.getNome().equals("texto7"))
					camposExtrasTemp.setTexto7(camposExtras.getTexto7());
				if(configuracaoCampoExtra.getNome().equals("texto8"))
					camposExtrasTemp.setTexto8(camposExtras.getTexto8());
				if(configuracaoCampoExtra.getNome().equals("textolongo1"))
					camposExtrasTemp.setTextolongo1(camposExtras.getTextolongo1());
				if(configuracaoCampoExtra.getNome().equals("textolongo2"))
					camposExtrasTemp.setTextolongo2(camposExtras.getTextolongo2());

				if(configuracaoCampoExtra.getNome().equals("data1"))
					camposExtrasTemp.setData1(camposExtras.getData1());
				if(configuracaoCampoExtra.getNome().equals("data2"))
					camposExtrasTemp.setData2(camposExtras.getData2());
				if(configuracaoCampoExtra.getNome().equals("data3"))
					camposExtrasTemp.setData3(camposExtras.getData3());
				
				if(configuracaoCampoExtra.getNome().equals("valor1"))
					camposExtrasTemp.setValor1(camposExtras.getValor1());
				if(configuracaoCampoExtra.getNome().equals("valor2"))
					camposExtrasTemp.setValor2(camposExtras.getValor2());
				
				if(configuracaoCampoExtra.getNome().equals("numero1"))
					camposExtrasTemp.setNumero1(camposExtras.getNumero1());
			}
			
			update(camposExtrasTemp);
			
			return camposExtrasTemp;
		}
		else
			return save(camposExtras);
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager)
	{
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}
}
