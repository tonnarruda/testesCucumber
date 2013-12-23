package com.fortes.rh.business.sesmt;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPTab;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.util.DateUtil;

public class PcmatManagerImpl extends GenericManagerImpl<Pcmat, PcmatDao> implements PcmatManager
{
	private FasePcmatManager fasePcmatManager;
	private AreaVivenciaPcmatManager areaVivenciaPcmatManager;
	private AtividadeSegurancaPcmatManager atividadeSegurancaPcmatManager;
	private EpiPcmatManager epiPcmatManager;
	private EpcPcmatManager epcPcmatManager;
	
	public Collection<Pcmat> findByObra(Long obraId) 
	{
		return getDao().findByObra(obraId);
	}

	public void validaDataMaiorQueUltimoHistorico(Long pcmatId, Long obraId, Date aPartirDe) throws FortesException 
	{
		Pcmat ultimoPcmat = getDao().findUltimoHistorico(pcmatId, obraId);
		
		if (ultimoPcmat != null && !ultimoPcmat.getAPartirDe().before(aPartirDe)) 
			throw new FortesException("Somente é possível cadastrar um PCMAT após a data "+DateUtil.formataDiaMesAno(ultimoPcmat.getAPartirDe()) + ".");		
	}
	
	public Pcmat findUltimoHistorico(Long pcmatId, Long obraId)
	{
		return getDao().findUltimoHistorico(pcmatId, obraId);
	}

	public void clonar(Long pcmatOrigemId, Date aPartirDe, Long obraId) 
	{
		Pcmat pcmatDestino = (Pcmat) getDao().findEntidadeComAtributosSimplesById(pcmatOrigemId).clone();
		pcmatDestino.setId(null);
		pcmatDestino.setProjectionIdObra(obraId);
		pcmatDestino.setAPartirDe(aPartirDe);
		getDao().save(pcmatDestino);
		
		fasePcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		areaVivenciaPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		atividadeSegurancaPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		epiPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		epcPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
	}
	
	public XWPFDocument gerarDocumento(Long pcmatId) throws Exception 
	{
		Collection<AreaVivenciaPcmat> areasVivenciaPcmat = areaVivenciaPcmatManager.findByPcmat(pcmatId);
		
		XWPFDocument document;
		XWPFParagraph para;
		XWPFRun run;
		
		document = new XWPFDocument(new FileInputStream("/Users/rubensgadelha/Downloads/PCMAT.docx"));
		
		// ÁREAS DE VIVÊNCIA
		
		para = document.createParagraph();
        para.setStyle("Heading1");
        run = para.createRun();
        run.setText("DIMENSIONAMENTO DA ÁREA DE VIVÊNCIA");
        
        for (AreaVivenciaPcmat areaVivenciaPcmat : areasVivenciaPcmat) 
        {
        	para = document.createParagraph();
        	para.setStyle("Heading2");
        	run = para.createRun();
        	run.setText(areaVivenciaPcmat.getAreaVivencia().getNome());

        	para = document.createParagraph();
        	para.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
        	run = para.createRun();
        	run.setText(areaVivenciaPcmat.getDescricao());
		}
        
        return document;
	}

	public void setFasePcmatManager(FasePcmatManager fasePcmatManager) {
		this.fasePcmatManager = fasePcmatManager;
	}

	public void setAtividadeSegurancaPcmatManager(AtividadeSegurancaPcmatManager atividadeSegurancaPcmatManager) {
		this.atividadeSegurancaPcmatManager = atividadeSegurancaPcmatManager;
	}

	public void setEpiPcmatManager(EpiPcmatManager epiPcmatManager) {
		this.epiPcmatManager = epiPcmatManager;
	}

	public void setEpcPcmatManager(EpcPcmatManager epcPcmatManager) {
		this.epcPcmatManager = epcPcmatManager;
	}

	public void setAreaVivenciaPcmatManager(AreaVivenciaPcmatManager areaVivenciaPcmatManager) {
		this.areaVivenciaPcmatManager = areaVivenciaPcmatManager;
	}
}
