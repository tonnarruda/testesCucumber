package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.dao.sesmt.AnexoDao;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.util.ArquivoUtil;

@Component
public class AnexoManagerImpl extends GenericManagerImpl<Anexo, AnexoDao> implements AnexoManager
{
	@Autowired
	AnexoManagerImpl(AnexoDao fooDao) {
		setDao(fooDao);
	}
	
	public Collection<Anexo> findByOrigem(Long origemId, char origem)
	{
		return getDao().findByOrigem(origemId, origem);
	}

	public Anexo gravaAnexo(File arquivo, Anexo anexo)
	{
		String pasta = "";

		if (anexo.getOrigem() == OrigemAnexo.LTCAT || anexo.getOrigem() == OrigemAnexo.PPRA)
			pasta = "sesmt";

		OrigemAnexo origemAnexo = new OrigemAnexo();
		String nomeAnexo = origemAnexo.get(anexo.getOrigem()) + "_" + anexo.getOrigemId() + "_";
		String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf("."));
		arquivo.setName(nomeAnexo + new Date().getTime() + extensao);

		java.io.File arquivoSalvo = ArquivoUtil.salvaArquivo(pasta, arquivo, false);

		if(arquivoSalvo != null)
			anexo.setUrl(arquivo.getName());

		return anexo;
	}

	public Anexo populaAnexo(Anexo anexo)
	{
		Anexo anexoTmp = findById(anexo.getId());

		anexoTmp.setNome(anexo.getNome());
		anexoTmp.setObservacao(anexo.getObservacao());

		return anexoTmp;
	}

	public String getStringRetorno(char origem)
	{
		String retorno = "";

		if(origem == OrigemAnexo.LTCAT)
			retorno = "successLTCAT";
		else if(origem == OrigemAnexo.PPRA)
			retorno = "successPPRA";

		return retorno;
	}


}