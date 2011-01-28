<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>
	<title>Cronograma de treinamento</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		function getTurmasByFiltro()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			DWREngine.setErrorHandler(errorMsg);
			TurmaDWR.getTurmasByFiltroInvestimento(populaTurmas, <@authz.authentication operation="empresaId"/>, null);
		}

		function populaTurmas(data)
		{
			addChecks('turmasCheck', data);
		}

		function errorMsg(msg)
		{
			jAlert(msg);
			addChecks('turmasCheck', null);
		}
	</script>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('@turmasCheck'), null)"/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="imprimirRelatorioInvestimento.action" onsubmit="${validarCampos}" method="POST">
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" width="735" height="300"/>
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas" list="turmasCheckList" width="735" height="300"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>