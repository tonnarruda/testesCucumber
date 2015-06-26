<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<#if colaboradorTurma.id?exists>
	<title>Atualizar Curso do Colaborador</title>
	<#assign formAction="updateDNT.action"/>
	<#assign accessKey="A"/>
<#else>
<title>Incluir Curso para Colaborador</title>
	<#assign formAction="insertDNT.action"/>
	<#assign accessKey="I"/>
</#if>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DntDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

<script type='text/javascript'>
	function populaTurmas()
	{
		var id = document.getElementById("curso").value;
		if(id != "")
		{
			DWRUtil.useLoadingMessage('Carregando...');
			DntDWR.getTurmas(createListTurmas, document.getElementById("curso").value);
		}
		else
		{
			DWRUtil.removeAllOptions("turma");

			var elOptNew = document.createElement('option');
			elOptNew.text = '[Selecione o curso...]';
			elOptNew.value = '';
			var elSel = document.getElementById('turma');

			try
			{
				elSel.add(elOptNew, null); // standards compliant; doesn't work in IE
			}
			catch(ex)
			{
				elSel.add(elOptNew); // IE only
			}
		}
	}

	function createListTurmas(data)
	{
		DWRUtil.removeAllOptions("turma");
		DWRUtil.addOptions("turma", data);
	}
</script>

	<@ww.head/>
</head>
<body>
	<@ww.actionmessage/>

	<#if colaboradorTurma.id?exists>
		<#assign validarCampos="document.forms[0].submit();"/>
	<#else>
		<#assign validarCampos="return validaFormulario('form', new Array('colaborador','curso','prioridade'), null)"/>
	</#if>

	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

		<#if colaboradorTurma.id?exists>
			<li>
				Nome do Colaborador: <br><b>${colaboradorTurma.colaborador.nomeComercial?string}</b>
			</li>
		<#else>
			<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" required="true" headerKey="" headerValue="[Selecione...]" list="colaboradors" listKey="id" listValue="nomeMaisNomeComercial"/>
		</#if>

		<@ww.select label="Curso" id="curso" name="colaboradorTurma.curso.id" required="true" headerKey="" headerValue="[Selecione...]" list="cursos" listKey="id" listValue="nome" onchange="populaTurmas();"/>
		<@ww.select label="Turma" id="turma" name="colaboradorTurma.turma.id" headerKey="" headerValue="[Selecione o curso...]" list="turmas" listKey="id" listValue="descricao"/>
		<@ww.select label="Prioridade" id="prioridade" name="colaboradorTurma.prioridadeTreinamento.id" required="true" headerKey="" headerValue="[Selecione...]" list="prioridadeTreinamentos" listKey="id" listValue="descricao"/>
		<@ww.hidden name="colaboradorTurma.id" />
		<@ww.hidden name="colaboradorTurma.colaborador.id" />
		<@ww.hidden name="colaboradorTurma.dnt.id" />
		<@ww.hidden name="areaFiltroId" />
		<@ww.hidden name="colaboradorTurma.origemDnt" />
		<@ww.hidden name="gestor" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='../dnt/listDetalhes.action?dnt.id=${colaboradorTurma.dnt.id}&areaFiltro.id=${areaFiltroId}&gestor=${gestor?string}'" class="btnCancelar" accesskey="V">
		</button>
	</div>

</body>
</html>