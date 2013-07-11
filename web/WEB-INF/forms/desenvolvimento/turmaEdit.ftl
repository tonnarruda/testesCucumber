<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
	@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	
	#formDialog { display: none; }
</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/json2.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>

	<#include "../ftl/mascarasImports.ftl" />

	<#if turma?exists && turma.id?exists>
		<title>Editar Turma - ${turma.descricao}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Turma</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#if avaliacaoRespondida == true>
		<#assign somenteLeitura = true/>
	<#else>
		<#assign somenteLeitura = false/>
	</#if>

	<#if turma.custo?exists>
		<#assign custo = turma.custo/>
	<#else>
		<#assign custo = ''/>
	</#if>

	<script type="text/javascript">
		var diasIds = "";
		function populaDias(frm)
		{
			var dIni = document.getElementById('prevIni');
			var dFim = document.getElementById('prevFim');
	
			if(validaDate(dIni) && validaDate(dFim))
			{
				if(dIni.value != "  /  /    " && dFim.value != "  /  /    ")
				{
					diasIds = getArrayCheckeds(frm, "diasCheck");
					DWRUtil.useLoadingMessage('Carregando...');
					DiaTurmaDWR.getDias(montaListDias, dIni.value, dFim.value);
				}
			}
		}
	
		function montaListDias(data)
		{
			if(data != null)
			{
				addChecks('diasCheck',data)
				marcaCheckListBoxString(diasIds);
			}
			else
				jAlert("Data Inválida.");
		}
	
		function marcaCheckListBoxString(checks)
		{
			for(var count = 0; count < checks.length; count++)
			{
				elemento = document.getElementById("checkGroupdiasCheck"+checks[count]);
				if(elemento != null)
					elemento.checked = true;
			}
		}
		
		function limpaDespesas()
		{
			 $('#totalCustos').text('0,00');
			 $('.despesa').val('');
		}
		
		function abrirPopupDespesas() 
		{
			var camposOcultos = '';
			$('#formDialog').dialog({ 	modal: true, 
										width: 500,
										height: 360,
										buttons: 
										[
										    {
										        text: "Gravar",
										        click: function() { 
										        	var despesas = new Array();

													$('.despesa').each(function(){
													    if (this.value && moeda2float(this.value) > 0)
													        despesas.push({tipoDespesaId:this.name, despesa:moeda2float(this.value)});
													});
													
													var despesasJSON = JSON.stringify(despesas);
													var despesasTotal = somaDespesas();
													var turmaId = $('#turmaId').val();
													
													if (turmaId != '')
														TurmaDWR.saveDespesas(despesasJSON, turmaId, despesasTotal);
													
													$('#custos').val(despesasJSON);
													$('#custo').val(float2moeda(despesasTotal));
													
													somenteLeitura((despesasTotal <= 0), 'custo');
													
										        	$(this).dialog("close"); 
										        }
										    },
										    {
										        text: "Limpar",
										        click: function() { limpaDespesas(); }
										    }
										] ,
										open: function(event, ui) 
										{ 
											var turmaId = $('#turmaId').val();
											if (turmaId != '')
											{
												TurmaDWR.getDespesas(turmaId, function(turmaTipoDespesas) 
												{
													$(turmaTipoDespesas).each(function(i, turmaTipoDespesa) {
														$("#tipoDespesa :input[name='" + turmaTipoDespesa.tipoDespesa.id + "']").val( float2moeda(turmaTipoDespesa.despesa) );
													});
													
													$('#totalCustos').text(float2moeda(somaDespesas()));
												});
											
											} else 
											{
												var custos = $('#custos').val();
												
												if (custos != '') 
												{
													custos = JSON.parse(custos);
	
													$(custos).each(function() {
														$("#tipoDespesa :input[name='" + this.tipoDespesaId + "']").val( float2moeda(this.despesa) );
													});
													
													$('#totalCustos').text(float2moeda(somaDespesas()));
												}
											}
										},
										close: function(event, ui)
										{
											limpaDespesas();
										}
									});
		}
		
		function somaDespesas() 
		{
			var total = 0;

			$('.despesa').each(function (i, item) {
			    var valor = $(item).val();
			    if (valor && valor != '')
			        total += moeda2float(valor);
			});
			
			return total;
		}
		
		$(function() {
			<#if contemCustosDetalhados>
				somenteLeitura(false, 'custo');
			</#if>
	
			$('.despesa').blur(function() {
				var valor = somaDespesas();
				$('#totalCustos').text(float2moeda(valor));
			});

			$('#tooltipCurso').qtip({
				content: 'A alteração do curso está restrita à empresa que o criou.'
			});
			$('#tooltipCusto').qtip({
				content: 'A alteração do custo está restrita à empresa que o criou.'
			});
			$('#tooltipDias').qtip({
				content: 'Não é possível editar os dias previstos, pois existem presenças.'
			});
			$('#tooltipAvaliacao').qtip({
				content: 'A alteração da avaliação do curso está restrita à empresa que o criou.'
			});
		});
	</script>
<@ww.head/>

<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('curso','desc','inst','custo','prevIni','prevFim'), new Array('prevIni', 'prevFim'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form id="formTurma" name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		
		<#if turmaPertenceAEmpresaLogada>
			<@ww.select label="Curso" name="turma.curso.id" list="cursos" id="curso" listKey="id" listValue="nome"  headerKey="" headerValue="Selecione..."/>
		<#else>
			<strong>Curso: ${turma.curso.nome}</strong>
			<img id="tooltipCurso" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -3px; margin-top: -9px;" />
			<@ww.hidden name="turma.curso.id" id="curso" value="${turma.curso.id}" />
			</br></br>
		</#if>

		<@ww.textfield required="true" label="Descrição" name="turma.descricao" id="desc" cssStyle="width: 637px;" maxLength="100"/>
		<@ww.textfield label="Horário" maxLength="20" name="turma.horario" id="horario" liClass="liLeft" />
		<@ww.textfield required="true" label="Instrutor" size="55" name="turma.instrutor" id="inst" maxLength="100" liClass="liLeft"/>
		
		<li id="wwgrp_custo">
			<div class="wwlbl" id="wwlbl_custo">
				<label class="desc" for="custo"> Custo (R$):<span class="req">* </span></label>
			</div> 
			<div class="wwctrl" id="wwctrl_custo">
				<#if turmaPertenceAEmpresaLogada>
					<@ww.textfield theme="simple" id="custo" name="turma.custo" cssClass="moeda" maxlength="12" size="12" cssStyle="width:90px; text-align:right;"/>
					<a href="javascript:;" id=detalharCusto  onclick="abrirPopupDespesas();" title="Detalhamento dos custos"><img src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" border="0" align="absMiddle"/></a>
				<#else>
					<@ww.textfield theme="simple" id="custo" name="turma.custo" cssClass="moeda" maxlength="12" size="12" cssStyle="width:90px; text-align:right;" readonly=true/>
					<img id="tooltipCusto" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -3px" />
				</#if>
			</div> 
		</li>
		
		<@ww.textfield label="Instituição" maxLength="100" name="turma.instituicao" id="instituicao"  cssStyle="width: 437px;" liClass="liLeft"/>
		<@ww.textfield label="Qtd. Prevista de Participantes" name="turma.qtdParticipantesPrevistos" id="qtdParticipantesPrevistos" cssStyle="width:30px; text-align:right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
		<@ww.select label="Realizada" name="turma.realizada" list=r"#{true:'Sim',false:'Não'}"/>

		<#assign dataIni=""/>
		<#if turma?exists && turma.dataPrevIni?exists>
			<#assign dataIni=turma.dataPrevIni/>
		</#if>
		<#assign dataFim=""/>
		<#if turma?exists && turma.dataPrevFim?exists>
			<#assign dataFim=turma.dataPrevFim/>
		</#if>

		Período:*<br>

		<#if turma.temPresenca?exists && turma.temPresenca>
			<@ww.textfield name="turma.dataPrevIni" value="${dataIni}" id="prevIni" readonly=true maxlength="10" cssStyle="width:80px;" liClass="liLeft" />
			<@ww.label value="a" liClass="liLeft"/>
			<@ww.textfield name="turma.dataPrevFim" value="${dataFim}" id="prevFim" readonly=true maxlength="10" cssStyle="width:80px;" liClass="liLeft"/></br>
			<img id="tooltipDias" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -90px; margin-bottom:-18px" />
			<@frt.checkListBox name="diasCheck" label="Dias Previstos" list="diasCheckList" readonly=true valueString=true/>
		<#else>
			<@ww.datepicker required="true" name="turma.dataPrevIni" value="${dataIni}" id="prevIni" liClass="liLeft" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataIni"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker required="true" name="turma.dataPrevFim" value="${dataFim}" id="prevFim" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataFim" />
			<@frt.checkListBox name="diasCheck" label="Dias Previstos" list="diasCheckList" readonly=false valueString=true/>
		</#if>

		<#if turmaPertenceAEmpresaLogada>
			<@frt.checkListBox label="Questionários de Avaliação do Curso" name="avaliacaoTurmasCheck" list="avaliacaoTurmasCheckList"/>
		<#else>
			<img id="tooltipAvaliacao" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 250px; margin-bottom:-18px" />
			<@frt.checkListBox label="Questionários de Avaliação do Curso" name="avaliacaoTurmasCheck" list="avaliacaoTurmasCheckList" readonly=true/>
		</#if>

		<@ww.hidden name="turma.id" id="turmaId" />
		<@ww.hidden name="turma.empresa.id" />
		<@ww.hidden name="planoTreinamento" />
		<@ww.hidden name="avaliacaoRespondida" />
		<@ww.hidden name="custos" id="custos"/>
		
		<#if somenteLeitura>
			<#list avaliacaoTurmasCheckList as avaliacaoCheck>
				<#if avaliacaoCheck.selecionado>
					<@ww.hidden name="avaliacaoTurmasCheck" value="${avaliacaoCheck.id}" />
				</#if>
			</#list>
		</#if>
	</@ww.form>

	<#if planoTreinamento>
		<#assign urlVoltar="filtroPlanoTreinamento.action"/>
	<#else>
		<#assign urlVoltar="list.action?curso.id=${curso.id}"/>
	</#if>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='${urlVoltar}'" class="btnVoltar"></button>
	</div>
	
	<div id="formDialog" title="Detalhamento dos custos">
		<br />
		
		Total R$  
		<span id="totalCustos"></span>
		
		<br /><br />
		
		<@display.table name="tipoDespesas" id="tipoDespesa" class="dados" style="width:450px;">
			<@display.column property="descricao" title="Descrição"/>
			<@display.column title="Custo (R$)" style="text-align: center; width:120px;">
				<input type="text" name="${tipoDespesa.id}" class="despesa moeda" maxlength="10" size="12" style="text-align:right; width: 90px;border:1px solid #7E9DB9;"/>
			</@display.column>
		</@display.table>
	</div>
	
	<script type="text/javascript">
		$(function() {
			<#if somenteLeitura>
				 $("input[type='checkbox'][name='avaliacaoTurmasCheck']").attr("disabled" , "disabled");
			</#if>
		});
	</script>
</body>
</html>