package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class DocumentoAnexoDaoHibernateTest extends GenericDaoHibernateTest<DocumentoAnexo>
{
	private DocumentoAnexoDao documentoAnexoDao;

	public DocumentoAnexo getEntity()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(null);
		documentoAnexo.setDescricao("documentoAnexo");
		documentoAnexo.setEtapaSeletiva(null);
		documentoAnexo.setData(new Date());
		documentoAnexo.setUrl("url");
		documentoAnexo.setOrigem('Z');
		documentoAnexo.setOrigemId(1L);

		return documentoAnexo;
	}

	public void testGetDocumentoAnexoByOrigemId()
	{
		Long origemId = 23423423L;
		char origem = 'W';

		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo.setOrigemId(origemId);
		documentoAnexo.setOrigem(origem);
		documentoAnexo = documentoAnexoDao.save(documentoAnexo);

		DocumentoAnexo documentoAnexo2 = getEntity();
		documentoAnexo2.setOrigemId(436546L);
		documentoAnexo2.setOrigem(origem);
		documentoAnexo2 = documentoAnexoDao.save(documentoAnexo2);

		Collection<DocumentoAnexo> retorno = documentoAnexoDao.getDocumentoAnexoByOrigemId(null,origem, origemId);

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjection()
	{
		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo = documentoAnexoDao.save(documentoAnexo);
		
		assertEquals(documentoAnexo, documentoAnexoDao.findByIdProjection(documentoAnexo.getId()));
	}

	public GenericDao<DocumentoAnexo> getGenericDao()
	{
		return documentoAnexoDao;
	}

	public void setDocumentoAnexoDao(DocumentoAnexoDao documentoAnexoDao)
	{
		this.documentoAnexoDao = documentoAnexoDao;
	}

}