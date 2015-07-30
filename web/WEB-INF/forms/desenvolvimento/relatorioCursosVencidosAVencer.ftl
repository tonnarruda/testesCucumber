<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<title>Cursos vencidos e a vencer</title>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
		<#include "../ftl/mascarasImports.ftl" />
		
		<#assign validaCampos="return validaFormulario('form', new Array('dataReferencia', '@empresasCheck'), new Array('dataReferencia'))"/>
		
		<script>
			$(document).ready(function($){
				populaCursos();
				$('#dataReferencia').val($.datepicker.formatDate('dd/mm/yy',new Date()));		
			});
			
			function populaCursos()	
			{
				var empresasIds = $("input[name='empresasCheck']:checked").map(function(){
										return this.value;
									}).get();
			
				CursoDWR.getCursosByEmpresasIds(empresasIds, createListCursos);
			}
			
			function createListCursos(data) {
				addChecksByMap("cursosCheck", data);
			}
			
		</script>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.form name="form" action="imprimirCursosVencidosAVencer.action" onsubmit="${validaCampos}" method="POST">
		
			<@ww.datepicker label="Data de Referência" id="dataReferencia" name="dataReferencia" required="true" cssClass="mascaraData" />
			<@frt.checkListBox name="empresasCheck" id="empresasCheck" label="Empresas*" list="empresasCheckList" filtro="true" onClick="populaCursos();" />
			<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" filtro="true"  />
			<@ww.select name="filtroAprovado" label="Considerar colaboradores" list="statusAprovacao" cssStyle="width: 500px;"/>
			<@ww.select name="filtroSituacao" label="Considerar colaboradores que possuem cursos" list=r"#{'0':'Vencidos e a vencer','1':'A vencer', '2':'Vencidos'}" cssStyle="width: 500px;"/>
			<@ww.select name="filtroAgrupamento" label="Agrupar relatório por" list=r"#{'0':'Cursos','1':'Colaboradores'}" cssStyle="width: 500px;"/>
		
			<div class="buttonGroup">
				<button onclick="${validaCampos};" class="btnRelatorio"></button>
			</div>
		</@ww.form>
	</body>
</html>