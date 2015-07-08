<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<title>Cursos vencidos e a vencer</title>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
		<#include "../ftl/mascarasImports.ftl" />
		
		<#assign validaCampos="return validaFormulario('form', new Array('dataReferencia'), new Array('dataReferencia'))"/>
		
		<script>
			DWREngine.setAsync(true);
			
			var empresaIds = new Array();
			<#if empresas?exists>
				<#list empresas as empresa>
					empresaIds.push(${empresa.id});
				</#list>
			</#if>
		
			$(document).ready(function($){
				populaCursos($('#empresas').val());
				$('#dataReferencia').val($.datepicker.formatDate('dd/mm/yy',new Date()));		
			});
			
			function populaCursos(empresaId)	{
				CursoDWR.getCursosByEmpresasParticipantes(empresaIds, empresaId, createListCursos);
			}
			
			function createListCursos(data) {
				addChecksByMap("cursosCheck", data);
			}
			
		</script>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.form name="form" action="imprimirCursosVencidosAVencer.action" onsubmit="${validaCampos}" method="POST">
		
			<#list empresas as empresa>	
					<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
			</#list>
		
			<@ww.select label="Empresa" name="empresa.id" list="empresas" id="empresas" listKey="id" listValue="nome" onchange="populaCursos(this.value)" headerKey="-1" headerValue="Todas" />
			
			<@ww.datepicker label="Data de Referência" id="dataReferencia" name="dataReferencia" required="true" cssClass="mascaraData" />
		
			<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" filtro="true"  />
			
			<@ww.select id="filtroAgrupamento" name="filtroAgrupamento" label="Agrupar por" list=r"#{'0':'Cursos','1':'Colaboradores'}" />
				
			<@ww.select id="filtroSituacao" name="filtroSituacao" label="Situação" list=r"#{'0':'Todos','1':'a vencer', '2':'vencidos'}"/>
			
		
		<div class="buttonGroup">
			<button onclick="${validaCampos};" class="btnRelatorio"></button>
		</div>
		
		</@ww.form>
		
	</body>
</html>