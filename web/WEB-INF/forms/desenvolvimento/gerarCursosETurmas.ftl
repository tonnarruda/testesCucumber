<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/lntList.css"/>');
		@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ParticipanteCursoLntDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/popupDespesasTurmaLnt.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script>
	
	<title>Gerar Cursos e Turmas</title>
	
	<style>
		.curso {
			width: 100%;
			border: 1px solid #e7e7e7;
			min-height: 20px;
			border-radius: 5px;
			margin-bottom: 10px;
		}
		.curso .title {
			padding: 10px 15px;
			font-weight: bold;
		}
		.curso .actions {
			padding: 0 0 10px 15px;
		}
		.curso .actions button {
			background: white;
			border: 2px solid #5294C3;
			padding: 0 10px;
			height: 24px;
			margin: 5px 10px 0 0;
			cursor: pointer;
			border-radius: 7px;
			color: #33556C;
		}
		.curso .actions button:hover {
			color: #448cbe;
		}
		.teste button {
			height: 50px !important;
		}
		#participantes-turma{
			float: top;
			margin-left: 525px;
		}
		li { 
			list-style: none; 
		}
		.calendar { 
			z-index: 99998 !important; 
		}
		#listCheckBoxdiasCheck{
			height: 100px !important;
		}
		#diasTable { 
			border: none; 
		}
		#diasTable td { 
			padding: 0px; 
		}
		#diasTable input[type='text'] {
			border: 1px solid #BEBEBE; width: 50px; 
		}
		.hora { 
			text-align: right; 
		}
		.invalido { 
			background-color: #FFEEC2 !important; 
		}
		#wwgrp_participantesCheck{
			padding-top: 29px !important;
		}
	</style>
	
	<script type='text/javascript'>
		$(function(){
			<#if msg?exists && msg != "">
				jAlert('${msg}');
			</#if>
			
			<#if continuarAdd && cursoId?exists && cursoLntId?exists>
				criarTurma(${cursoId}, ${cursoLntId});
				$('#carregar').removeAttr('disabled').css('opacity', '1');
			<#else>
				$('#carregar').attr('disabled', true).css('opacity', '0.2');
			</#if>
		});
			
		String.prototype.capitalize = function() {
		    return this.charAt(0).toUpperCase() + this.slice(1);
		}
		
		function criarTurma(cursoId, cursoLntId) {
			$('#dialog .cursoId').val(cursoId);
			$('#cursoLntId').val(cursoLntId);
			populaTurma(false);
			
			TurmaDWR.getTurmas(cursoId, function(turmas) {
				$("#dialog #turma").html("<option value>Nova turma</option>");
				$.each(turmas, function(key, turma) {
					$("#dialog #turma").append("<option value='"+key+"'>"+turma+"</option>");
				});
			});
			
			ParticipanteCursoLntDWR.getParticipantesCursoLnt(cursoLntId, function(colaboradores) {
				$("#listCheckBoxparticipantesCheck").html("");
				$.each(colaboradores, function(i, colaborador) {
					disabled = "";
					style = "";
					title = "";
					if(colaborador.iscritoNaTurma){
						title = "'Participante já inscrito em uma turma para essa LNT'";
						disabled = "disabled='disabled' title=" + title;
						style = "background: #efefef;";
					}

					$("#listCheckBoxparticipantesCheck").append("<label style='margin-top: 2px; " + style + "' title=" + title + " for='checkGroupparticipantesCheck"+ colaborador.id + "'><input name='participantesCheck'" + disabled + "value='" + colaborador.id + "' type='checkbox' id='checkGroupparticipantesCheck" + colaborador.id + "'>" + colaborador.nome + "</label>");
				});
			});
			
			$('#dialog').dialog({	title: "Cadastro de Turma", 
									resizable: false, 
									modal: true, 
									width: 960, 
									close: function(ev, ui) { 
										$('#continuarAdd').val('false'); 
										$('#carregar').attr('disabled', true).css('opacity', '0.2'); 
										$(this).dialog("close");  
									} 
								});
		}
		
		function populaDias()
		{
			$('#diasTable').empty();
			$('#listCheckBoxFilterDiasCheck').val('');
		
			var dIni = document.getElementById('prevIni');
			var dFim = document.getElementById('prevFim');
	
			if(validaDate(dIni) && validaDate(dFim))
			{
				if(dIni.value != "  /  /    " && dFim.value != "  /  /    ")
				{
					DWRUtil.useLoadingMessage('Carregando...');
					DiaTurmaDWR.getDias(montaListDias, dIni.value, dFim.value, $('#porTurno').val());
				}
			}
		}
		
		var turmaPopulada;
		var disabilitaCamposDias = true;
		function montaListDias(diasTurma){
			if (diasTurma != null){
				var dataFmt, id;
				
				for (var i = 0; i < diasTurma.length; i++)
				{
					dataFmt = diasTurma[i].diaFormatado;
					id = dataFmt.replace(/\//g,'') + diasTurma[i].turno;
					
					var row = 	"<tr class='" + (i%2 == 0 ? 'even' : 'odd') + "'>\n";
					row += 		"	<td><input name='diasCheck' id='" + id + "' value='" + dataFmt + ';' + diasTurma[i].turno + "' type='checkbox'/></td>\n";
					row += 		"	<td><label for='" + id + "'>" + dataFmt + "</label></td>\n";
					row += 		"	<td><label for='" + id + "'>" + diasTurma[i].diaSemanaDescricao.capitalize() + "</label></td>\n";
					
					if (diasTurma[i].turno != 'D'){
						row +=	"	<td><label for='" + id + "'>" + diasTurma[i].turnoDescricao.capitalize() + "</label></td>\n";
					}
					row += 		"	<td><input type='text' id='horaIni-" + id + "' name='horasIni' class='hora' maxlength='5' disabled=true /> às <input type='text' id='horaFim-" + id + "' name='horasFim' class='hora' maxlength='5' disabled=true /></td>\n";
					row += 		"</tr>\n";
				
					$('#diasTable').append(row);
					$('#horaIni-' + id).css('background-color', '#ECECEC');
					$('#horaFim-' + id).css('background-color', '#ECECEC');
				}
				
				$("input[name='diasCheck']").change(function() {
					var marcado = $(this).is(":checked");
					
					$('#horaIni-' + this.id).attr("disabled", !marcado);
					$('#horaFim-' + this.id).attr("disabled", !marcado);
					
					if(marcado){
						$('#horaIni-' + this.id).css('background-color', '#FFF');
						$('#horaFim-' + this.id).css('background-color', '#FFF');
					}else{
						$('#horaIni-' + this.id).css('background-color', '#ECECEC');
						$('#horaFim-' + this.id).css('background-color', '#ECECEC');
					}
				});
				
			}else{
				jAlert("Data inválida.");
			}
			
			if(turmaPopulada)
				marcaDias();
		}

		function marcaDias(){
			turno = 'D';
			if(turmaPopulada.porTurno == 'true')
				turno = 'T'
			
			$(turmaPopulada.diasTurma).each(function(j, diaTurma) {
				id = diaTurma.diaFormatado.replace('/','').replace('/','') + turno;
				$('#' + id).attr("checked","checked");
				$('#horaIni-' + id).val(diaTurma.horaIni).css('background-color', '#FFF').removeAttr("disabled");
				$('#horaFim-' + id).val(diaTurma.horaFim).css('background-color', '#FFF').removeAttr("disabled");
			});
			
			if(disabilitaCamposDias){
				$('#listCheckBoxdiasCheck').find('input').attr('disabled', 'disabled');
				$('#listCheckBoxdiasCheck').find('.hora').attr('disabled', 'disabled');
				$('#wwctrl_diasCheck').find('.linkCheck').removeAttr('onclick');
			}
		}
		
		function marcaTodos()
		{
			$('input[name=diasCheck]:enabled').attr('checked','checked');
			$("input[name='diasCheck']:visible").each( function(i, item) {
				if(!$(item).is(':disabled')){
					$('#horaIni-' + this.id).attr("disabled", false).css('background-color', '#FFF');
					$('#horaFim-' + this.id).attr("disabled", false).css('background-color', '#FFF');
				}
		    });
		}
		
		function desmarcaTodos()
		{
			$('input[name=diasCheck]:enabled').removeAttr('checked');
			$("input[name='diasCheck']:visible").each( function(i, item) {
				if(!$(item).is(':disabled')){
					$('#horaIni-' + this.id).attr("disabled", true).css('background-color', '#ECECEC');
					$('#horaFim-' + this.id).attr("disabled", true).css('background-color', '#ECECEC');
				}
		    });
		}
		
		function validarCampos()
		{
			if($('input[name=participantesCheck]:checked').length == 0){
				$('#listCheckBoxparticipantesCheck').css('background','#FFEEC2');
				jAlert('Marque pelo menos um participante para ser inserido na turma selecionada.');
				return false;
			}
			
			if($('#turma').val() != ""){
				document.form.submit();
			}else{
				var chave, horaIni, horaFim, horariosValidos = true;
				$('input[name=diasCheck]:checked').each(function() {
	
					chaveArray = $(this).val().replace(/\//g,'').split(";");
					id = chaveArray[0] + chaveArray[1];   
					
					horaIni = $('#horaIni-' + id).val();
					horaFim = $('#horaFim-' + id).val();
					
					if((!horaIni && horaFim) || (horaIni && !horaFim))
					{
						$('#horaIni-' + id).val('');
						$('#horaFim-' + id).val('');
					}
					
					if ( (horaIni && horaFim) && ((!horaIni != !horaFim) || !(validaHora(horaIni) && validaHora(horaFim)) || (parseInt(horaFim.replace(':','')) < parseInt(horaIni.replace(':',''))) ))
					{
						$('#horaIni-' + id).css('background-color', '#FFEEC2');
						$('#horaFim-' + id).css('background-color', '#FFEEC2');
						horariosValidos = false;
					}
					else
					{
						$('#horaIni-' + id).css('background-color', '#FFF');
						$('#horaFim-' + id).css('background-color', '#FFF');
					}
					
				});
				
				if (!horariosValidos){
					jAlert('Existe(m) horário(s) definidos incorretamente.<br />Informe os horários de início e término para a data ou deixe ambos em branco.');
					return false;
				}
				
				if(validaFormularioEPeriodo('form', new Array('desc','inst','custo','prevIni','prevFim'), new Array('prevIni', 'prevFim'), true)){
					processando('<@ww.url includeParams="none" value="/imgs/"/>');
					document.form.submit();
				}
			}
		}
		
		function populaTurma(turmaId)
		{
			turmaPopulada = null;
			$("#horario").val('').removeAttr('disabled').css('background-color','#FFF');
			$("#instituicao").val('').removeAttr('disabled').css('background-color','#FFF');
			$("#desc").val('').removeAttr('disabled').css('background-color','#FFF');
			$("#custo").val('').removeAttr('disabled').css('background-color','#FFF');
			$("#inst").val('').removeAttr('disabled').css('background-color','#FFF');
			$("#porTurno").val('').removeAttr('disabled').css('background-color','#FFF');
			$("#prevIni").val('  /  /    ').removeAttr('disabled').css('background-color','#FFF');
			$("#prevFim").val('  /  /    ').removeAttr('disabled').css('background-color','#FFF');
			$('#diasTable').empty();
			$('#listCheckBoxFilterDiasCheck').val('');
			$('#marcarTodos').click(function(){marcaTodos();})
			$('#desmarcarTodos').click(function(){desmarcaTodos();})
			$("input[name=avaliacaoTurmasCheck]").each(function(){$(this).removeAttr("checked").removeAttr('disabled');});
			$('#prevIni_button').show();
			$('#prevFim_button').show();
			
			if(turmaId){
				$('#detalharCusto').hide();
				TurmaDWR.getTurma(turmaId, function(turma){
					$("#horario").val(turma.horario).attr('disabled', 'disabled').css('background-color','#ececec');
					$("#instituicao").val(turma.instituicao).attr('disabled', 'disabled').css('background-color','#ececec');
					$("#desc").val(turma.descricao).attr('disabled', 'disabled').css('background-color','#ececec');
					$("#custo").val(turma.custoFormatado).attr('disabled', 'disabled').css('background-color','#ececec');
					$("#inst").val(turma.instrutor).attr('disabled', 'disabled').css('background-color','#ececec');
					$("#porTurno").val('' + turma.porTurno).attr('disabled', 'disabled').css('background-color','#ececec');
					
					var datas = turma.periodoFormatado.split(' a ');
					$("#prevIni").val(datas[0]).attr('disabled', 'disabled').css('background-color','#ececec');
					$("#prevFim").val(datas[1]).attr('disabled', 'disabled').css('background-color','#ececec');
					
					turmaPopulada = turma;
					disabilitaCamposDias = true;
					
					populaDias();
					$('#listCheckBoxavaliacaoTurmasCheck').find('input').attr('disabled', 'disabled');
					$('#prevIni_button').hide();
					$('#prevFim_button').hide();
									
					$(turma.avaliacaoTurmas).each(function(j, avaliacaoTurma) {
						$("input[name='avaliacaoTurmasCheck'][value='" + avaliacaoTurma.id + "']").attr("checked","checked");
					});
				});
			} else {
				$('#detalharCusto').show();
			}
		}
		
		function carregaTurmaAnterior(){
			if($('#turmaIdAnterior').val() != null && $('#turmaIdAnterior').val()){
				TurmaDWR.getTurma($('#turmaIdAnterior').val(), function(turma){
					$("#horario").val(turma.horario);
					$("#instituicao").val(turma.instituicao);
					$("#desc").val(turma.descricao);
					$("#custo").val(turma.custoFormatado);
					$("#inst").val(turma.instrutor);
					$("#porTurno").val('' + turma.porTurno);
					
					var datas = turma.periodoFormatado.split(' a ');
					$("#prevIni").val(datas[0]);
					$("#prevFim").val(datas[1]);
					
					turmaPopulada = turma;
					disabilitaCamposDias = false;
					
					populaDias();
									
					$(turma.avaliacaoTurmas).each(function(j, avaliacaoTurma) {
						$("input[name='avaliacaoTurmasCheck'][value='" + avaliacaoTurma.id + "']").attr("checked","checked");
					});
				});
			}
		}
	</script>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#if 0 < cursosLnt?size>
		<@display.table name="cursosLnt" id="cursoLnt" class="dados">
			<@display.column title="Ações" class="acao" style = "width:90px;">
			
				<#if !cursoLnt.curso?exists>				
					<@frt.link verifyRole="ROLE_MOV_CURSO" href="../../desenvolvimento/curso/prepareInsert.action?lntId=${lnt.id}&cursoLntId=${cursoLnt.id}" imgTitle="Criar Curso" iconeClass="fa-edit"/>
					<@frt.link verifyRole="ROLE_MOV_CURSO" imgTitle="Criar turmas/relacionar participantes\n(Não existe curso)" iconeClass="fa-users" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" imgTitle="Visualizar\n(Não existe curso)" iconeClass="fa-eye" opacity=true/>
				<#else>
					<@frt.link verifyRole="ROLE_MOV_CURSO" imgTitle="Criar Curso\n(Curso já existe)" iconeClass="fa-edit" opacity=true/>

					<#if cursoLnt.existePerticipanteASerRelacionado>
						<@frt.link verifyRole="ROLE_MOV_CURSO" imgTitle="Criar turmas/relacionar participantes" iconeClass="fa-users" onclick="criarTurma('${cursoLnt.curso.id}', '${cursoLnt.id}');"/>
					<#else>
						<@frt.link verifyRole="ROLE_MOV_CURSO" imgTitle="Criar turmas/relacionar participantes\n(Não existem participantes a serem relacionados)" iconeClass="fa-users" opacity=true/>
					</#if>
					
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" imgTitle="Visualizar" iconeClass="fa-eye" href="visualizarParticipantesCursoLnt.action?cursoLntId=${cursoLnt.id}"/>
				</#if>	
			
			</@display.column>
			
			<@display.column property="nomeNovoCurso" title="Nome Curso"/>
			<@ww.hidden cssClass="cursoLntId" value="${cursoLnt.id}" />
		</@display.table>
	<#else>	
		<div class="info">
			<ul>
				<li>Não existem cursos nesta LNT.</li>
			</ul>
		</div>
	</#if>
	<button onclick="window.location='list.action'" id="btVoltar" type="button">Voltar</button>
	
	
	<div id="dialog" style="display: none;">
		<@ww.form id="form" name="form" action="saveTurma.action" method="POST" >
			<div style="float: left; width: 525px; height: 600px;">
				<button onclick="carregaTurmaAnterior()" id="carregar" type="button">Carregar dados da turma anterior</button>
				<@ww.hidden name="turma.curso.id" cssClass="cursoId" />
				<@ww.hidden name="cursoLntId" id="cursoLntId" />
				<@ww.hidden name="lnt.id" value="${lnt.id}" />
				<@ww.hidden name="turmaIdAnterior" id="turmaIdAnterior"/>
				<@ww.hidden name="continuarAdd" id="continuarAdd" value="false" />
				<@ww.hidden name="custos" id="custos"/>
				
				<@ww.select onchange="populaTurma(this.value)" label="Turma" id="turma" name="turma.id" headerKey="" headerValue="Selecione..." list="turmas" listKey="id" listValue="descricao" cssStyle="width: 492px;"/>
				<@ww.textfield required="true" label="Descrição" name="turma.descricao" id="desc" maxLength="100" cssClass="valid" cssStyle="width: 491px;"/>
				<@ww.textfield required="true" label="Instrutor" size="55" name="turma.instrutor" id="inst" maxLength="100" cssStyle="width: 396px;" liClass="liLeft"/>

				<div class="wwctrl" id="wwctrl_custo">
					<div class="wwlbl" id="wwlbl_custo">
						<label class="desc" for="custo"> Custo (R$):<span class="req">* </span></label>
					</div> 
				
					<@ww.textfield required="true" id="custo" name="turma.custo"  cssClass="moeda valid" maxlength="12" size="12" cssStyle="width:90px; text-align:right;" theme="simple"/>
					<a href="javascript:;" id="detalharCusto"  onclick="abrirPopupDespesas();" title="Detalhamento dos custos"><img src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" border="0" align="absMiddle"/></a>
				</div>				
				
				<@ww.textfield label="Instituição" maxLength="100" name="turma.instituicao" id="instituicao"  cssStyle="width: 396px;" liClass="liLeft"/>
				<@ww.textfield label="Horário" maxLength="20" name="turma.horario" id="horario" cssStyle="width: 90px;"/>
				
				<fieldset style="padding: 5px 0px 5px 5px; width: 486px;">
					<legend>Dias Previstos</legend>
					<@ww.select label="Realizar turma por" name="turma.porTurno" id="porTurno" list=r"#{false:'Dia',true:'Turno'}" onchange="populaDias(document.forms[0]);"/>
					Período:*<br>
					<@ww.datepicker required="true" name="turma.dataPrevIni" value="  /  /    " id="prevIni" liClass="liLeft" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataIni"/>
					<@ww.label value="a" liClass="liLeft" />
					<@ww.datepicker required="true" name="turma.dataPrevFim" value="  /  /    " id="prevFim" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataFim" /><br />
					<div id="wwctrl_diasCheck" class="wwctrl">
						<div class="listCheckBoxContainer" style="width: 476px; height: 120px">
							<div class="listCheckBoxBarra">
								<input id="listCheckBoxFilterDiasCheck" class="listCheckBoxFilter" title="Digite para filtrar" type="text">
								&nbsp;<span id="marcarTodos" class="linkCheck" onclick="marcaTodos();">Marcar todos</span> | <span id="desmarcarTodos" class="linkCheck" onclick="desmarcaTodos();">Desmarcar todos</span>
							</div>
							<div id="listCheckBoxdiasCheck" class="listCheckBox">
								<table id="diasTable" class="dados">									
								</table>
							</div>
						</div>
					</div>
				</fieldset>
				
				<@frt.checkListBox label="Questionários de Avaliação do Curso" name="avaliacaoTurmasCheck" list="avaliacaoTurmasCheckList" width="493" height="100" filtro="true"/>
			</div>
			
			<div id="participantes-turma">
				<@frt.checkListBox label="Participantes" name="participantesCheck" list="participantesCheckList" width="400" height="499" filtro="true"/>
			</div>

			<div style="text-align: right;">
				<button onclick="validarCampos();" type="button">Gravar</button>
				<button style="margin-right:8px;" onclick="$('#continuarAdd').val('true');validarCampos();" type="button" style="margin-right: 0px !important;">Gravar e criar nova</button>
			</div>
		</@ww.form>
		
	</div>
	<div id="formDialog" title="Detalhamento dos custos" style="display: none;">
		<br />
		
		Total R$  
		<span id="totalCustos"></span>
		
		<br /><br />
		
		<@display.table name="tipoDespesas" id="tipoDespesa" class="dados" style="width:450px;">
			<@display.column property="descricao" title="Descrição"/>
			<@display.column title="Custo (R$)" style="text-align: center; width:120px;">
				<input type="text" name="${tipoDespesa.id}" class="despesa moedaCurso" maxlength="10" size="12" style="text-align:right; width: 90px;border:1px solid #BEBEBE;"/>
			</@display.column>
		</@display.table>
	</div>
</body>
</html>
