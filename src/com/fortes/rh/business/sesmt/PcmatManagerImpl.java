package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.DocX;
import com.fortes.rh.util.StringUtil;

public class PcmatManagerImpl extends GenericManagerImpl<Pcmat, PcmatDao> implements PcmatManager
{
	private EmpresaManager empresaManager;
	private ObraManager obraManager;
	private FasePcmatManager fasePcmatManager;
	private MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager;
	private AreaVivenciaPcmatManager areaVivenciaPcmatManager;
	private AtividadeSegurancaPcmatManager atividadeSegurancaPcmatManager;
	private EpiPcmatManager epiPcmatManager;
	private EpcPcmatManager epcPcmatManager;
	private SinalizacaoPcmatManager sinalizacaoPcmatManager;
	
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
	
	public XWPFDocument gerarDocumento(Long empresaId, Long obraId, Long pcmatId) throws Exception 
	{
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		Obra obra = obraManager.findByIdProjection(obraId);
		Pcmat pcmat = findEntidadeComAtributosSimplesById(pcmatId);
		
		DocX docx = new DocX();
		
		montarCapa(docx, obra);
		montarObjetivo(docx, pcmat);
		montarIdentificacao(docx, empresa, obra, pcmat);
		montarLayoutCanteiroObra(docx);
		montarAreasVivencia(docx, pcmatId);
		montarRiscos(docx, pcmatId);
	    montarMedidasControleIndividualColetivas(docx, pcmatId);
	    montarSinalizacao(docx, pcmatId);
	    montarProgramaEducativo(docx, pcmatId);
		
        return docx;
	}

	private void montarCapa(DocX docx, Obra obra) 
	{
		XWPFRun run;
		
		run = docx.addParagraph(obra.getNome());
		run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
		run.setFontSize(28);
		run.setBold(true);
		run.addBreak();
		
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		
		run = docx.addParagraph("PCMAT");
		run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
		run.setFontSize(24);
		run.setBold(true);
		run.setUnderline(UnderlinePatterns.SINGLE);
		run.addBreak();
		
		run = docx.addParagraph("PROGRAMA DE CONDIÇÕES E MEIO AMBIENTE DE TRABALHO NA INDÚSTRIA DA CONSTRUÇÃO");
		run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
		run.setFontSize(16);
		run.setBold(true);
		run.addBreak(BreakType.PAGE);
	}

	private void montarObjetivo(DocX docx, Pcmat pcmat) 
	{
		if (!StringUtil.isBlank(pcmat.getObjetivo()))
		{
			docx.addParagraph("OBJETIVO DO PCMAT", "Heading1");
			docx.addParagraph(pcmat.getObjetivo(), null, 700, false)
				.getParagraph()
				.setAlignment(ParagraphAlignment.BOTH);
		}
	}
	
	private void montarIdentificacao(DocX docx, Empresa empresa, Obra obra, Pcmat pcmat) 
	{
		docx.addParagraph("IDENTIFICAÇÃO DO ESTABELECIMENTO", "Heading1");
		
		docx.addParagraph("EMPRESA: " + empresa.getRazaoSocial(), "Heading2");
		docx.addParagraph("Endereço: " + StringUtils.defaultString(empresa.getEndereco()));
		docx.addParagraph("Cidade: " + empresa.getCidade().getNome() + "/" + empresa.getUf().getSigla());
		docx.addParagraph("CNPJ: " + empresa.getCnpj());
		docx.addParagraph("CNAE: " + empresa.getCnae());
		
		docx.addParagraph("OBRA: " + obra.getNome(), "Heading2");
		docx.addParagraph("Tipo de Obra: " + StringUtils.defaultString(obra.getTipoObra()));
		docx.addParagraph("Endereço: " + obra.getEndereco().getEnderecoFormatado());
		docx.addParagraph("Bairro: " + StringUtils.defaultString(obra.getEndereco().getBairro()));
		docx.addParagraph("Cidade: " + obra.getEndereco().getCidadeEstado());
		docx.addParagraph("CEP: " + StringUtils.defaultString(obra.getEndereco().getCepFormatado()));
		
		docx.addParagraph("Nº DE EMPREGADOS PREVISTOS", "Heading2");
		docx.addParagraph("Número máximo de empregados: " + pcmat.getQtdFuncionarios());
		
		docx.addParagraph("CRONOGRAMA DE ÍNICIO E TÉRMINO PREVISTO", "Heading2");
		docx.addParagraph("Início: " + DateUtil.formataMesExtensoAno(pcmat.getDataIniObra()));
		docx.addParagraph("Conclusão: " + DateUtil.formataMesExtensoAno(pcmat.getDataFimObra()));
		
		docx.addParagraph("CRONOGRAMA GERAL DA OBRA", "Heading2")
			.addBreak();
		
		XWPFTable table = docx.createTable();
		docx.addTableHeader(table, new String[] { "ITEM", "DESCRIÇÃO" }, new Integer[] { 200, 3000 });
	}
	
	private void montarLayoutCanteiroObra(DocX docx) 
	{
		docx.addParagraph("LAYOUT DO CANTEIRO DE OBRA", "Heading1");
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
					.setAlignment(ParagraphAlignment.BOTH);
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
	    	docx.addParagraph(pares.getKey().getDescricao(), null, 700, true)
	    		.getParagraph()
				.setAlignment(ParagraphAlignment.BOTH);
			
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
			docx.addParagraph(epcPcmat.getDescricao(), null, 700, false)
				.getParagraph()
				.setAlignment(ParagraphAlignment.BOTH);
		}
	}

	private void montarSinalizacao(DocX docx, Long pcmatId) 
	{
		Collection<SinalizacaoPcmat> sinalizacaoPcmats = sinalizacaoPcmatManager.findByPcmat(pcmatId);
		
		docx.addParagraph("SINALIZAÇÃO", "Heading1");
		docx.addParagraph("Toda a obra será sinalizada com cartazes, informando sobre riscos, atenções e avisos. Segue abaixo alguns textos para confecção dos modelos a serem utilizados:", null, 700, true);
		
		XWPFTable table = docx.createTable();
		docx.addTableHeader(table, new String[] { "TIPO DE CARTAZ" }, new Integer[] { 8000 });
		
		for (SinalizacaoPcmat sinalizacaoPcmat : sinalizacaoPcmats) 
			docx.addTableRow(table, sinalizacaoPcmat.getDescricao());
	}
	
	private void montarProgramaEducativo(DocX docx, Long pcmatId) 
	{
		Collection<AtividadeSegurancaPcmat> atividadesSegurancaPcmat = atividadeSegurancaPcmatManager.findByPcmat(pcmatId);
		
		docx.addParagraph("PROGRAMA EDUCATIVO", "Heading1");
		
		XWPFTable table = docx.createTable();
		docx.addTableHeader(table, new String[] { "PALESTRA", "PROGRAMAÇÃO", "CARGA HORÁRIA" }, new Integer[] { 3500, 3500, 1000 });
		
		for (AtividadeSegurancaPcmat atividade : atividadesSegurancaPcmat) 
			docx.addTableRow(table, atividade.getNome(), DateUtil.formataMesExtensoAno(atividade.getData()), atividade.getCargaHorariaEmHora());
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

	public void setObraManager(ObraManager obraManager) {
		this.obraManager = obraManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setSinalizacaoPcmatManager(
			SinalizacaoPcmatManager sinalizacaoPcmatManager) {
		this.sinalizacaoPcmatManager = sinalizacaoPcmatManager;
	}
}
