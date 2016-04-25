<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<title>Incluir Colaboradores na Turma - ${colaboradorTurma.turma.descricao}</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorTurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/colaboradorTurma.js?version=${versao}"/>'></script>
	
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
	
		$(function(){
			$("#nome,#matricula").keyup(function(e) {
				console.log("passou!");
				if (e.keyCode == 13)
					enviaForm();
			});
		});
		
		function enviaForm()
		{
			validaFormularioEPeriodo('form', false, new Array('admIni', 'admFim'));
		}

		function marcarDesmarcar()
		{
			var marcar = $('#md').attr('checked');
			$("input[name='colaboradoresId']").attr('checked', marcar);
		}

		function inserir()
		{
			colaboradoresIds = $("input[name='colaboradoresId']:checked").map(function(){
			    return $(this).val();
			}).get();
			verificaSelecao($("input[name='turma.id']").val(), $("input[name='turma.curso.id']").val(), colaboradoresIds, ${empresaSistema.controlarVencimentoPorCertificacao?string});
		}
	</script>

<#include "../ftl/showFilterImports.ftl" />

<@ww.head/>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

<#if !colaborador?exists>
	<#assign labelFiltro="Ocultar Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_up.gif"/>
	<#assign classHidden="">
</#if>

<#assign dataAdmIni=""/>
<#if dataAdmissaoIni?exists>
	<#assign dataAdmIni=dataAdmissaoIni?date/>
</#if>
<#assign dataAdmFim=""/>
<#if dataAdmissaoFim?exists>
	<#assign dataAdmFim=dataAdmissaoFim?date/>
</#if>

</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="listFiltro.action" method="POST" id="formBusca">

            <@ww.select label="Empresa" name="empresaId" id="empresa" list="empresas" cssClass="empresaSelect" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" onchange="newChangeEmpresa(this.value);" disabled="!compartilharColaboradores"/>
			
			<label>Admitidos entre:</label><br />
			<@ww.datepicker name="dataAdmissaoIni" value="${dataAdmIni}" id="admIni" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
			<@ww.label value="e" liClass="liLeft" />
			<@ww.datepicker name="dataAdmissaoFim" value="${dataAdmFim}" id="admFim" cssClass="mascaraData validaDataFim"/>
			
			<@ww.textfield label="Nome do Colaborador" id="nome" name="colaborador.nome" maxLength="100" cssStyle="width: 500px;" />
			<@ww.textfield label="Matrícula do Colaborador" id="matricula" name="colaborador.matricula" maxLength="20" cssStyle="width: 170px;"/>
			
			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>
			<@frt.checkListBox id="areasCheck" name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" onClick="populaCargosByAreaVinculados();" selectAtivoInativo="true"/>
			<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
			<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
			
			<input type="button" onclick="enviaForm();" value="" class="btnPesquisar grayBGE" />

			<@ww.hidden name="colaboradorTurma.id" />	
			<@ww.hidden name="turma.id" />
			<@ww.hidden id="filtrarPor" name="filtrarPor"/>
			<@ww.hidden name="turma.curso.id"/>
			<@ww.hidden name="planoTreinamento" />

		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<#if colaboradorTurmas?exists>
		<@ww.form name="formColab" action="insert.action" validate="true" method="POST">
			
			<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados" defaultsort=2 sort="list" >
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar();' checked='checked'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${colaboradorTurma.id}" name="colaboradoresId" checked='checked'/>
				</@display.column>
				<@display.column property="colaborador.nome" title="Nome" style="width: 500px;"/>
				<@display.column property="colaborador.matricula" title="Matrícula" style="width: 100px;"/>
				<@display.column property="colaborador.areaOrganizacional.descricao" title="Área" style="width: 300px;"/>
			</@display.table>
		
			<@ww.hidden name="turma.id" />
			<@ww.hidden name="turma.curso.id" />
			<@ww.hidden name="filtrarPor" />
			<@ww.hidden name="planoTreinamento" />

		</@ww.form>
	</#if>

	<div class="buttonGroup">
		<#if colaboradorTurmas?exists && colaboradorTurmas?size != 0>
			<button onclick="javascript: inserir();" class="btnInserirSelecionados"></button>
		</#if>
		<button onclick="window.location='list.action?turma.id=${turma.id}&curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'" class="btnVoltar"></button>
	</div>
	
	<script type='text/javascript'>
		$('#cargosVinculadosAreas').click(function() {
			populaCargosByAreaVinculados();
		});
		
		$('#cargosVinculadosAreas').attr('checked', true);
		
		populaCargosByAreaVinculados();
	
		$(document).ready(function()
		{
			window.onload = function(){
				<#assign cargosChecks = request.getAttribute('cargosCheckList')>
	
				<#foreach item in cargosChecks > 
					<#if item.selecionado >
				    	console.log('${item.id}');
				    	$("#checkGroupcargosCheck" + ${item.id}).attr('checked', true);
				    </#if>
				</#foreach>
			};
		});
	</script>
</body>
</html>