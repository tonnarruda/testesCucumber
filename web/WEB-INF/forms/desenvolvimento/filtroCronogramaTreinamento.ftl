<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>
	<title>Cronograma de Treinamento</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		function getTurmasByFiltro()
		{
			dwr.util.useLoadingMessage('Carregando...');
			dwr.engine.setErrorHandler(errorMsg);
			TurmaDWR.getTurmasByFiltro(document.getElementById('dataIni').value, document.getElementById('dataFim').value, document.getElementById('realizada').value, <@authz.authentication operation="empresaId"/>, populaTurmas);
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

	<#if filtroPlanoTreinamento.dataIni?exists>
		<#assign dateIni = filtroPlanoTreinamento.dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if filtroPlanoTreinamento.dataFim?exists>
		<#assign dateFim = filtroPlanoTreinamento.dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('@turmasCheck'), new Array('dataIni', 'dataFim'))"/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="imprimirCronogramaTreinamento.action" onsubmit="${validarCampos}" method="POST">
		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					Período:<br>
					<@ww.datepicker name="dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
					<@ww.label value="a" liClass="liLeft" />
					<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />
					<@ww.select label="Realizada" name="realizada" id="realizada" list=r"#{'T':'Todas','S':'Sim','N':'Não'}" />

					<div class="btnCarregarTurmas grayBGE" onclick="javascript: getTurmasByFiltro();" />
				</ul>
			</@ww.div>
		</li>
		<br/>
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas" list="turmasCheckList" width="735" height="300" filtro="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>