package com.fortes.rh.web.action.desenvolvimento;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class LntEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private LntManager lntManager;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private CursoLntManager cursoLntManager;
	private EmpresaManager empresaManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private TurmaManager turmaManager;
	private TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private TipoDespesaManager tipoDespesaManager;
	
	private Lnt lnt = new Lnt();
	private char status = StatusLnt.TODOS;
	private final HashMap<Character, String> listaStatusLnt = new StatusLnt();
	private Collection<Lnt> lnts;
	private String[] areasCheck = new String[]{};
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckListDialog = new ArrayList<CheckBox>();
	private Collection<CursoLnt> cursosLnt;
	private Collection<ParticipanteCursoLnt> participantesCursosLnt = new ArrayList<ParticipanteCursoLnt>();
	private Map<String, Object> parametros;
	private Collection<TipoDespesa> tipoDespesas;
	private Turma turma;
	
	private String[] participantesRemovidos;
	private Long[] cursosRemovidos;
	
	private Long[] empresasCheck = new Long[]{};
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> empresasCheckListDialog = new ArrayList<CheckBox>();
	private Long[] empresaIds;
	private Long[] areasIds;
	
	private Character apruparRelatorioPor = 'A';
	
	private Collection<CheckBox> avaliacaoTurmasCheckList = new ArrayList<CheckBox>();
	private Long[] avaliacaoTurmasCheck;

	private Collection<CheckBox> participantesCheckList = new ArrayList<CheckBox>();
	private Long[] participantesCheck;
	
	private String[] diasCheck;
	
	private Collection<Turma> turmas = new ArrayList<Turma>();
	
	private String[] horasIni;
	private String[] horasFim;
	private Long cursoId;
	private Long cursoLntId;
	private Long turmaIdAnterior;
	private String msg;
	private boolean continuarAdd = false;
	private CursoLnt cursoLnt;
	private String custos;

	@SuppressWarnings("static-access")
	private void populaEmpresasLnt(Collection<Empresa> empresas) throws Exception {
		populaEmpresasPermitidasCheckList();
		
		Collection<Long> empresasIdsCollectionLnt = new LongUtil().arrayLongToCollectionLong(new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas));
		Collection<Long> empresasIdsCollection = new LongUtil().arrayLongToCollectionLong(empresaIds);
		Collection<Long> empresasIdAdd = new ArrayList<Long>();
		
		for (Long empresaLnt : empresasIdsCollectionLnt) {
			if(!empresasIdsCollection.contains(empresaLnt))
				empresasIdAdd.add(empresaLnt);
		}
		
		if(empresasIdAdd.size() > 0){
			CheckBox checkBox = null;
			for (Empresa empresa : empresas) {
				if(empresasIdAdd.contains(empresa.getId())){
					checkBox = new CheckBox();
					checkBox.setId(empresa.getId());
					checkBox.setNome(empresa.getNome());
					checkBox.setSelecionado(true);
					checkBox.setDesabilitado(true);
					empresasCheckListDialog.add(checkBox);
				}
			}
			
			empresasCheckList.addAll(empresasCheckListDialog);
		}
		
		empresasCheckList = CheckListBoxUtil.marcaCheckListBox(empresasCheckList, lnt.getEmpresas(), "getId");
	}

	private void populaEmpresasPermitidasCheckList() throws Exception {
		Collection<Empresa> empresasPermitidas = empresaManager.findEmpresasPermitidas(true, getEmpresaSistema().getId(), getUsuarioLogado().getId());
		empresaIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresasPermitidas);
		empresasCheckList = CheckListBoxUtil.populaCheckListBox(empresasPermitidas, "getId","getNome", null);
	}
	
	public String list() throws Exception
	{
		setTotalSize(lntManager.getCountFindAllLnt(lnt.getDescricao(), status, getEmpresaSistema().getId()));
		lnts = lntManager.findAllLnt(lnt.getDescricao(), status, getEmpresaSistema().getId(), getPage(), getPagingSize());
		return Action.SUCCESS;
	}
	
	public String gerarCursosETurmas() throws Exception
	{
		Collection<AvaliacaoTurma> avaliacaoTurmas = avaliacaoTurmaManager.findAllSelect(true, getEmpresaSistema().getId());
		avaliacaoTurmasCheckList = CheckListBoxUtil.populaCheckListBox(avaliacaoTurmas, "getId", "getQuestionarioTitulo", null);
		
		tipoDespesas = tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao"});
		cursosLnt = cursoLntManager.findByLntId(lnt.getId());
		
		
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		populaEmpresasPermitidasCheckList();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		lnt = lntManager.findEntidadeComAtributosSimplesById(lnt.getId());
		lnt.setAreasOrganizacionais(areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), new Long[]{}));
		lnt.setEmpresas(empresaManager.findByLntId(lnt.getId()));
		
		populaEmpresasLnt(lnt.getEmpresas());
		populaAreasLnt();

		return Action.SUCCESS;
	}

	@SuppressWarnings("static-access")
	private void populaAreasLnt() throws NumberFormatException, Exception {
		Collection<Long> empresasIdsCollection = new LongUtil().arrayLongToCollectionLong(empresaIds);
		for (Empresa empresa : lnt.getEmpresas()){ 
			if(empresasIdsCollection.contains(empresa.getId()))
				areasCheckList.addAll(areaOrganizacionalManager.populaCheckComParameters(empresa.getId()));
		}

		if(empresasCheckListDialog.size() > 0){
			Collection<CheckBox> areasCheckBox = new ArrayList<CheckBox>();
			for (CheckBox checkBox : empresasCheckListDialog) {
				areasCheckBox = areaOrganizacionalManager.populaCheckComParameters(new Long(checkBox.getId()));
				Collection<Long> areasOrganizacionaisIdLnt = new LongUtil().arrayLongToCollectionLong(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(lnt.getAreasOrganizacionais())); 
				
				for (CheckBox checkBoxArea : areasCheckBox) {
					if(areasOrganizacionaisIdLnt.contains(new Long(checkBoxArea.getId()))){
						checkBoxArea.setDesabilitado(true);
						checkBoxArea.setSelecionado(true);
						areasCheckListDialog.add(checkBoxArea);
					}
				}
			}

			areasCheckList.addAll(areasCheckListDialog);
		}
		
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, lnt.getAreasOrganizacionais(), "getId");
	}

	public String insert()
	{
		try {
			lnt.setDataFinalizada(null);
			lnt.setAreasOrganizacionais(areaOrganizacionalManager.populaAreas(areasCheck));
			lnt.setEmpresas(new CollectionUtil<Empresa>().convertArrayLongToCollection(Empresa.class, empresasCheck));
			lntManager.save(lnt);
			if(lnt.getDataInicio().compareTo(DateUtil.criarDataMesAno(new Date())) <= 0 && lnt.getDataFim().compareTo(DateUtil.criarDataMesAno(new Date())) >= 0 )
				gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
			addActionSuccess("LNT gravada com sucesso.");
			
			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Não foi possível gravar esta LNT.");
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	public String update()
	{
		try {
			Lnt lntTemp = lntManager.findById(lnt.getId());
			if(!lntTemp.getFinalizada()){
				if(lntTemp.getPrazoLntVencida()){
					lntTemp.setDescricao(lnt.getDescricao());
					lntTemp.setDataInicio(lnt.getDataInicio());
					lntTemp.setDataFim(lnt.getDataFim());
					lntManager.update(lntTemp);
				}else{
					removeParticipantesDasAreasDesmarcadas();
					lnt.setAreasOrganizacionais(areaOrganizacionalManager.populaAreas(areasCheck));
					lnt.setEmpresas(new CollectionUtil<Empresa>().convertArrayLongToCollection(Empresa.class, empresasCheck));
					verificaNecessidadeDeNotificarInicio(lntTemp);
					lntManager.update(lnt);
				}
			}else{
				lntTemp.setDescricao(lnt.getDescricao());
				lntManager.update(lntTemp);
			}
			
			addActionSuccess("LNT atualizada com sucesso.");
			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Não foi possível atualizar esta LNT.");
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	private void removeParticipantesDasAreasDesmarcadas() {
		Collection<ParticipanteCursoLnt>  participantesCursosLnt = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), null, null, new String[]{});
		Collection<Long> areasIdsCollection = LongUtil.arrayStringToCollectionLong(areasCheck);
		
		for (ParticipanteCursoLnt participanteCursoLnt : participantesCursosLnt) {
			if(!areasIdsCollection.contains(participanteCursoLnt.getAreaOrganizacional().getId()))
				participanteCursoLntManager.remove(participanteCursoLnt);
		}
	}
	
	private void verificaNecessidadeDeNotificarInicio(Lnt lntTemp) {
		Date dataInicioAntiga = lntTemp.getDataInicio();
		if(lnt.getDataInicio() != dataInicioAntiga && dataInicioAntiga.compareTo(DateUtil.criarDataMesAno(new Date())) > 0 && 
				lnt.getDataInicio().compareTo(DateUtil.criarDataMesAno(new Date())) <= 0)
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
	}

	public String delete() throws Exception
	{
		try{
			lntManager.removeComDependencias(lnt.getId(), colaboradorTurmaManager, cursoLntManager);
			addActionSuccess("LNT excluída com sucesso.");
		}catch (Exception e){
			e.printStackTrace();
			setActionErr("Não foi possivel remover Lnt e suas dependências.");
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possivel remover Lnt e suas dependências.");
		}

		return list();
	}
	
	public String prepareParticipantes() throws Exception{
		
		Collection<Empresa> empresasPermitidas = empresaManager.findEmpresasPermitidas(true, getEmpresaSistema().getId(), getUsuarioLogado().getId());
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresasPermitidas));
		
		lnt = lntManager.findEntidadeComAtributosSimplesById(lnt.getId());
		lnt.setAreasOrganizacionais(areas);
		
		montaEmpresaChecks(empresasPermitidas);
		cursosLnt = cursoLntManager.findByLntIdAndEmpresasIdsAndAreasParticipantesIds(lnt.getId(),getAreasIds(areas),getEmpresasIds(empresasPermitidas)); 
		
		if (cursosLnt.size() == 0)
			cursosLnt.add(new CursoLnt(""));
		
		if(empresasCheck != null && empresasCheck.length > 0){
			empresasCheckList = CheckListBoxUtil.marcaCheckListBox(empresasCheckList, StringUtil.LongToString(empresasCheck));
			areasCheckList = CheckListBoxUtil.populaCheckListBox(areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), empresasCheck), "getId", "getNome", new String[]{});
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		}
		
		return Action.SUCCESS;
	}

	private Long[] getEmpresasIds(Collection<Empresa> empresasPermitidas){
		Long[] empresasIds;
		if(empresasCheck != null && empresasCheck.length > 0)
			empresasIds = empresasCheck;
		else
			empresasIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresasPermitidas);
		
		return empresasIds;		
	}
	
	private Long[] getAreasIds(Collection<AreaOrganizacional> areas){
		Long[] areasIds;
		if(areasCheck != null && areasCheck.length > 0)
			areasIds = StringUtil.stringToLong(areasCheck);
		else
			areasIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas);
		
		return areasIds;		
	}
	
	private void montaEmpresaChecks(Collection<Empresa> empresasPermitidas) throws Exception {
		Collection<Long> empresasIdsArea = new ArrayList<Long>();
		for (AreaOrganizacional area : lnt.getAreasOrganizacionais()) {
			if(!empresasIdsArea.contains(area.getEmpresa().getId()))
				empresasIdsArea.add(area.getEmpresa().getId());	
		}

		Collection<Empresa> empresasPermitidasDasAreas = new ArrayList<Empresa>();
		for (Empresa empresa : empresasPermitidas) {
			if(empresasIdsArea.contains(empresa.getId()))
				empresasPermitidasDasAreas.add(empresa);
		}
		
		empresasCheckList = CheckListBoxUtil.populaCheckListBox(empresasPermitidasDasAreas, "getId","getNome", null);
		empresasCheckListDialog = CheckListBoxUtil.populaCheckListBox(empresasPermitidasDasAreas, "getId","getNome", null);
		empresasPermitidas = empresasPermitidasDasAreas;
	}

	public String relatorioParticipantes() throws Exception{
		try {
			String[] order = new String[]{"areaNome","cu.nomeNovoCurso","c.nome"};
			if(apruparRelatorioPor == 'C')
				order = new String[]{"cu.nomeNovoCurso","areaNome","c.nome"};
			
			participantesCursosLnt = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, empresaIds, order);
			
			if(participantesCursosLnt.size() == 0){
				addActionMessage("Não existem participantes para esta LNT.");
				list();
				return Action.INPUT;
			}
				
			lnt = lntManager.findEntidadeComAtributosSimplesById(lnt.getId());
			parametros = RelatorioUtil.getParametrosRelatorio("Participantes da LNT", getEmpresaSistema(), "LNT: " + lnt.getDescricao() + "\nPeríodo: " + lnt.getPeriodoFormatado());
			
			if(apruparRelatorioPor == 'C')
				return "agrupadoPorCursoLnt";
				
			return "agrupadoPorArea";
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Ocorreu uma inconsistência ao gerar o relatório.");
			list();
			
			return Action.INPUT;
		}
	}
	
	public String relatorioParticipantesByUsuarioMsg() throws Exception{
		try {
			areasIds = 	areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(getUsuarioLogado().getId(), getEmpresaSistema().getId());
			areasIds = new CollectionUtil<Long>().convertCollectionToArrayLong(areaOrganizacionalManager.getDescendentesIds(areasIds));
			
			if(areasIds == null || areasIds.length == 0){
				setActionMsg("Não existem participantes para esta LNT ou sua área organizacional, cujo é responsável ou corresponsável, não consta na LNT.");
				return Action.INPUT;
			}
			
			participantesCursosLnt = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, empresaIds, new String[]{"areaNome","cu.nomeNovoCurso","c.nome"});
			
			if(participantesCursosLnt.size() == 0){
				setActionMsg("Não existem participantes para esta LNT.");
				return Action.INPUT;
			}
				
			lnt = lntManager.findEntidadeComAtributosSimplesById(lnt.getId());
			parametros = RelatorioUtil.getParametrosRelatorio("Participantes da LNT", getEmpresaSistema(), "LNT: " + lnt.getDescricao() + "\nPeríodo: " + lnt.getPeriodoFormatado());
			
			return Action.SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			setActionErr("Ocorreu uma inconsistência ao gerar o relatório.");
			prepareParticipantes();
			
			return Action.INPUT;
		}
	}
	
	public String saveParticipantes() throws Exception{
		if (cursosLnt != null) {
			cursosLnt.removeAll(Collections.singleton(null));
			cursosLnt.removeAll(cursosLntsVazios(cursosLnt));
			relacionarParticipantes(cursosLnt);
		}
		try {
			cursoLntManager.saveOrUpdate(lnt, cursosLnt, participantesRemovidos, cursosRemovidos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addActionSuccess("Cursos e participantes gravados com sucesso.");
		prepareParticipantes();
		
		return Action.SUCCESS;
	}
	
	private void relacionarParticipantes(Collection<CursoLnt> cursosLnts) {
		Map<Long, ParticipanteCursoLnt> mapParticipantes;
		Map<Long, Collection<ParticipanteCursoLnt>> participanteCursoLnts = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(lnt.getId(), null, null);
		
		for (CursoLnt cursoLnt : cursosLnts) {
			if (cursoLnt.getParticipanteCursoLnts() != null) {
				mapParticipantes = new HashMap<Long, ParticipanteCursoLnt>();
				cursoLnt.getParticipanteCursoLnts().removeAll(Collections.singleton(null));

				for (ParticipanteCursoLnt participanteCursoLnt : cursoLnt.getParticipanteCursoLnts()) {
					if(!mapParticipantes.containsKey(participanteCursoLnt.getColaborador().getId())){

						boolean existeParticipantaNaLnt = false;
						
						if(participanteCursoLnts != null && participanteCursoLnts.size() > 0 && participanteCursoLnts.get(cursoLnt.getId()) != null && participanteCursoLnts.get(cursoLnt.getId()).size() > 0){
							for (ParticipanteCursoLnt participanteCursoLntExistente : participanteCursoLnts.get(cursoLnt.getId())){
								if(participanteCursoLnt.getColaborador().getId().equals(participanteCursoLntExistente.getColaborador().getId()))
									existeParticipantaNaLnt =  true;
							}
						}
						
						if(!existeParticipantaNaLnt){
							participanteCursoLnt.setCursoLnt(cursoLnt);
							mapParticipantes.put(participanteCursoLnt.getColaborador().getId(), participanteCursoLnt);
						}
					}
				}
				
				cursoLnt.setParticipanteCursoLnts(mapParticipantes.values());
			}
		}
	}
		
	private Collection<CursoLnt> cursosLntsVazios(Collection<CursoLnt> cursosLnts) {
		Collection<CursoLnt> cursosLntsVazios = new ArrayList<CursoLnt>();
		for (CursoLnt cursoLnt : cursosLnts) {
			if(cursoLnt.getNomeNovoCurso() == null || cursoLnt.getNomeNovoCurso().equals(""))
				cursosLntsVazios.add(cursoLnt);
		}
		return cursosLntsVazios;
	}

	public String saveTurma() throws Exception
	{
		try {
			if (turma != null && participantesCheck != null){
				if (turma.getId() != null){
					Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaManager.findByTurma(turma.getId(), null, true, null, null, false, null);
					colaboradorTurmaManager.insereColaboradorTurmas(participantesCheck, colaboradoresTurmas, turma, null, 0, null, getEmpresaSistema().isControlarVencimentoPorCertificacao(), new CursoLnt(cursoLntId));
					msg = "Participantes gravados com sucesso.";
				}else{
					turma.setEmpresa(getEmpresaSistema());
					Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();

					for (Long  colabId: participantesCheck) 
						colaboradorTurmas.add(new ColaboradorTurma(new CursoLnt(cursoLntId), colabId, turma.getCurso().getId()));

					turmaManager.salvarTurmaDiasCustosColaboradoresAvaliacoes(turma, diasCheck, custos, colaboradorTurmas,  avaliacaoTurmasCheck, horasIni, horasFim, turmaAvaliacaoTurmaManager);
					msg = "Turma e participantes gravados com sucesso.";
				}

				cursoId = turma.getCurso().getId();
				turmaIdAnterior = turma.getId();
				
				if(continuarAdd)
					continuarAdd = cursoLntManager.existePerticipanteASerRelacionado(cursoLntId);
			}

			gerarCursosETurmas();
			if(continuarAdd){
				return "succesContinuarAdd";
			}else{
				addActionSuccess(msg);
				msg = null;
				return Action.SUCCESS;
			}

		} catch (Exception e) {
			gerarCursosETurmas();
			e.printStackTrace();
			addActionError("Não foi possível gravar turma e/ou participantes");
			return Action.INPUT;
		}
	}

	public String finalizar() throws Exception
	{
		try {
			lntManager.finalizar(lnt.getId(), getEmpresaSistema().getId());
			list();
			addActionSuccess("LNT finalizada com sucesso.");
		} catch (Exception e) {
			addActionError("Não foi possível finalizar esta LNT.");
		}
		return Action.SUCCESS;
	}
	
	public String reabrir() throws Exception
	{
		try {
			lntManager.reabrir(lnt.getId());
			list();
			addActionSuccess("LNT reaberta com sucesso.");
		} catch (Exception e) {
			addActionError("Não foi possível reabrir esta LNT.");
		}
		return Action.SUCCESS;
	}
	
	public String visualizarParticipantesCursoLnt(){
		
		try {
			cursoLnt = cursoLntManager.findById(cursoLntId);
			lnt = cursoLnt.getLnt();
			turmas = colaboradorTurmaManager.findParticipantesCursoLntAgrupadoNaTurma(cursoLntId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public Lnt getLnt()
	{
		if(lnt == null)
			lnt = new Lnt();
		return lnt;
	}

	public void setLnt(Lnt lnt)
	{
		this.lnt = lnt;
	}

	public void setLntManager(LntManager lntManager)
	{
		this.lntManager = lntManager;
	}
	
	public Collection<Lnt> getLnts()
	{
		return lnts;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public Collection<CheckBox> getParticipantesCheckList() {
		return participantesCheckList;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setCursoLntManager(CursoLntManager cursoLntManager) {
		this.cursoLntManager = cursoLntManager;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager)
	{
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager) {
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}
	
	public void setTurmaManager(TurmaManager turmaManager) {
		this.turmaManager = turmaManager;
	}

	public HashMap<Character, String> getListaStatusLnt()
	{
		return listaStatusLnt;
	}

	public void setParticipanteCursoLntManager(ParticipanteCursoLntManager participanteCursoLntManager)
	{
		this.participanteCursoLntManager = participanteCursoLntManager;
	}

	public Collection<ParticipanteCursoLnt> getParticipantesCursosLnt()
	{
		return participantesCursosLnt;
	}
	
	public void setParticipantesCursosLnt(Collection<ParticipanteCursoLnt> participantesCursosLnt)
	{
		this.participantesCursosLnt = participantesCursosLnt;
	}
	
	public Collection<CursoLnt> getCursosLnt()
	{
		return cursosLnt;
	}
	
	public void setCursosLnt(Collection<CursoLnt> cursosLnt)
	{
		this.cursosLnt = cursosLnt;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
	
	public void setCursosRemovidos(Long[] cursosRemovidos) {
		this.cursosRemovidos = cursosRemovidos;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheck(Long[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Long[] getEmpresaIds() {
		return empresaIds;
	}

	public Collection<CheckBox> getEmpresasCheckListDialog() {
		return empresasCheckListDialog;
	}

	public Collection<CheckBox> getAreasCheckListDialog() {
		return areasCheckListDialog;
	}
	
	public Collection<CheckBox> getAvaliacaoTurmasCheckList() {
		return avaliacaoTurmasCheckList;
	}

	public void setAvaliacaoTurmasCheckList(Collection<CheckBox> avaliacaoTurmasCheckList) {
		this.avaliacaoTurmasCheckList = avaliacaoTurmasCheckList;
	}
	
	public Character getApruparRelatorioPor() {
		return apruparRelatorioPor;
	}

	public void setApruparRelatorioPor(Character apruparRelatorioPor) {
		this.apruparRelatorioPor = apruparRelatorioPor;
	}

	public void setAreasIds(Long[] areasIds) {
		this.areasIds = areasIds;
	}

	public Long[] getEmpresasCheck() {
		return empresasCheck;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String[] getAreasCheck() {
		return areasCheck;
	}

	public void setEmpresaIds(Long[] empresaIds) {
		this.empresaIds = empresaIds;
	}

	public void setTurmaAvaliacaoTurmaManager(TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager) {
		this.turmaAvaliacaoTurmaManager = turmaAvaliacaoTurmaManager;
	}
	
	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public void setDiasCheck(String[] diasCheck) {
		this.diasCheck = diasCheck;
	}

	public Turma getTurma() {
		return turma;
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Collection<Turma> turmas) {
		this.turmas = turmas;
	}

	public void setHorasIni(String[] horasIni) {
		this.horasIni = horasIni;
	}

	public void setHorasFim(String[] horasFim) {
		this.horasFim = horasFim;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public void setParticipantesCheck(Long[] participantesCheck) {
		this.participantesCheck = participantesCheck;
	}

	public void setAvaliacaoTurmasCheck(Long[] avaliacaoTurmasCheck) {
		this.avaliacaoTurmasCheck = avaliacaoTurmasCheck;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public Long getCursoLntId() {
		return cursoLntId;
	}

	public void setCursoLntId(Long cursoLntId) {
		this.cursoLntId = cursoLntId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isContinuarAdd() {
		return continuarAdd;
	}

	public void setContinuarAdd(boolean continuarAdd) {
		this.continuarAdd = continuarAdd;
	}

	public Long getTurmaIdAnterior() {
		return turmaIdAnterior;
	}

	public void setTurmaIdAnterior(Long turmaIdAnterior) {
		this.turmaIdAnterior = turmaIdAnterior;
	}

	public CursoLnt getCursoLnt() {
		return cursoLnt;
	}

	public void setCursoLnt(CursoLnt cursoLnt) {
		this.cursoLnt = cursoLnt;
	}

	public String[] getParticipantesRemovidos() {
		return participantesRemovidos;
	}

	public void setParticipantesRemovidos(String[] participantesRemovidos) {
		this.participantesRemovidos = participantesRemovidos;
	}

	public Collection<TipoDespesa> getTipoDespesas() {
		return tipoDespesas;
	}

	public void setTipoDespesas(Collection<TipoDespesa> tipoDespesas) {
		this.tipoDespesas = tipoDespesas;
	}
	
	public void setTipoDespesaManager(TipoDespesaManager tipoDespesaManager) {
		this.tipoDespesaManager = tipoDespesaManager;
	}

	public void setCustos(String custos) {
		this.custos = custos;
	}
}