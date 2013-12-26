package com.fortes.rh.business.sesmt;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.DocX;

public class PcmatManagerImpl extends GenericManagerImpl<Pcmat, PcmatDao> implements PcmatManager
{
	private FasePcmatManager fasePcmatManager;
	private MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager;
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
		DocX docx = new DocX(new FileInputStream(ArquivoUtil.getWebInfPath() + "PCMAT.docx"));
		
		montarAreasVivencia(docx, pcmatId);
		montarRiscos(docx, pcmatId);
	    montarMedidasControleIndividualColetivas(docx, pcmatId);
		
        return docx;
	}
	
	private void montarAreasVivencia(DocX docx, Long pcmatId) 
	{
		Collection<AreaVivenciaPcmat> areasVivenciaPcmat = areaVivenciaPcmatManager.findByPcmat(pcmatId);
		
        docx.addParagraph("DIMENSIONAMENTO DA ÁREA DE VIVÊNCIA", "Heading1");
        
        for (AreaVivenciaPcmat areaVivenciaPcmat : areasVivenciaPcmat) 
        {
        	docx.addParagraph(areaVivenciaPcmat.getAreaVivencia().getNome(), "Heading2");
        	docx.addParagraph(areaVivenciaPcmat.getDescricao(), null, 700, false)
					.getParagraph()
					.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
		}
	}

	private void montarRiscos(DocX docx, Long pcmatId) 
	{
		XWPFParagraph para;
		XWPFRun run;
		XWPFTable table;
		XWPFTableRow row;
		
		Map<FasePcmat, Collection<RiscoFasePcmat>> riscosFasesPcmat = fasePcmatManager.findByPcmatRiscos(pcmatId);
		Map<Long, Collection<MedidaRiscoFasePcmat>> medidasRiscosFasesPcmat = medidaRiscoFasePcmatManager.findByPcmatRiscos(pcmatId);
		
		docx.addParagraph("MEMORIAL SOBRE CONDIÇÕES E MEIO AMBIENTE DE TRABALHO", "Heading1");
		docx.addParagraph("DESCRIÇÃO GERAL DE RISCOS", "Heading2");
		
		Iterator<Entry<FasePcmat, Collection<RiscoFasePcmat>>> faseIterator = riscosFasesPcmat.entrySet().iterator();
	    while (faseIterator.hasNext()) 
	    {
	    	Map.Entry<FasePcmat, Collection<RiscoFasePcmat>> pares = (Map.Entry<FasePcmat, Collection<RiscoFasePcmat>>) faseIterator.next();
	    	
	    	docx.addParagraph(pares.getKey().getFase().getDescricao(), "Heading3");
	    	docx.addParagraph(pares.getKey().getDescricao(), null, 700, true);
			
			if (!pares.getValue().isEmpty())
			{
				table = docx.createTable();
		        
				docx.addTableHeader(table, new String[] { "GRUPO DE RISCOS", "TIPO DE RISCO", "MEDIDAS PREVENTIVAS" }, new Integer[] { 2200, 3000, 3000 });
				
		        for (RiscoFasePcmat riscoFasePcmat : pares.getValue())
		        {
			        row = docx.addTableRow(table, riscoFasePcmat.getRisco().getDescricaoGrupoRisco(), riscoFasePcmat.getRisco().getDescricao());
			        
			        para = row.getCell(2).getParagraphs().get(0);
			        
			        if (medidasRiscosFasesPcmat.containsKey(riscoFasePcmat.getId()))
			        {
			        	Iterator<MedidaRiscoFasePcmat> medidasIterator = medidasRiscosFasesPcmat.get(riscoFasePcmat.getId()).iterator();
			        	
				        while (medidasIterator.hasNext())
				        {
					        run = para.createRun();
					        run.setText("- "+ medidasIterator.next().getMedidaSeguranca().getDescricao());
				        	
					        if (medidasIterator.hasNext())
				        		run.addBreak();
				        }
			        }
		        }
			}
	    }
	}
	
	private void montarMedidasControleIndividualColetivas(DocX docx, Long pcmatId) 
	{
		Collection<EpiPcmat> episPcmat = epiPcmatManager.findByPcmat(pcmatId);
		
		docx.addParagraph("MEDIDAS DE CONTROLE INDIVIDUAL E COLETIVA", "Heading1");
		docx.addParagraph("MEDIDA DE CONTROLE INDIVIDUAL: EPI - EQUIPAMENTOS DE PROTECÃO INDIVIDUAL", "Heading2", null, true);
		
		XWPFTable table = docx.createTable();
		
		docx.addTableHeader(table, new String[] { "EPI", "CARACTERÍSTICAS", "ATIVIDADES" }, new Integer[] { 2200, 3000, 3000 });
		
        for (EpiPcmat epiPcmat : episPcmat) 
        	docx.addTableRow(table, epiPcmat.getEpi().getNome(), epiPcmat.getEpi().getDescricao(), epiPcmat.getAtividades());
        
        docx.addParagraph("MEDIDAS DE CONTROLE COLETIVO: EPC - EQUIPAMENTOS DE PROTEÇÃO COLETIVA", "Heading2");
		
		Collection<EpcPcmat> epcsPcmat = epcPcmatManager.findByPcmat(pcmatId);
		
		for (EpcPcmat epcPcmat : epcsPcmat) 
		{
			docx.addParagraph(epcPcmat.getEpc().getDescricao(), "Heading3");
			docx.addParagraph(epcPcmat.getDescricao(), null, 700, false);
		}
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

	public void setMedidaRiscoFasePcmatManager(
			MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager) {
		this.medidaRiscoFasePcmatManager = medidaRiscoFasePcmatManager;
	}
}
