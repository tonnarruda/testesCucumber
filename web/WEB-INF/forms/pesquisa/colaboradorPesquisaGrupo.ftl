<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<#if pesquisa.tipoAvaliacao == 0>
	<title>Inserir Colaboradores na Pesquisa</title>
<#elseif pesquisa.tipoAvaliacao == 1>
	<title>Inserir Colaboradores na Avaliação</title>
</#if>

<#assign formAction="insert.action"/>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>

<script type='text/javascript'>
	function populaCargos(frm, nameCheck)
	{
		dwr.util.useLoadingMessage('Carregando...');
		var gruposIds = getArrayCheckeds(frm, nameCheck);
		CargoDWR.getCargoByGrupo(gruposIds, <@authz.authentication operation="empresaId"/>, createListCargos);
	}

	function createListCargos(data)
	{
		addChecks('cargosCheck',data)
	}

	function mudaAction(validar, action)
	{
		var qtdSelectGrupo = qtdeChecksSelected(document.forms[0],'gruposCheck');
		var qtdSelectCargo = qtdeChecksSelected(document.forms[0],'cargosCheck');
		var qtdEstabelecimentoSelect = qtdeChecksSelected(document.forms[0], 'estabelecimentosCheck');

		if(qtdEstabelecimentoSelect == 0 && validar)
		{
			jAlert("Nenhum Estabelecimeto selecionado.");
		}
		else if(qtdSelectGrupo == 0 && qtdSelectCargo == 0 && validar)
		{
			jAlert("Nenhum Grupo Ocupacional ou Cargo selecionado.");
		}
		else
		{
			document.form.action = action;
			
			document.form.submit();
		}
	}
</script>
</head>

<body>
<b>${pesquisa.titulo}<br>
<#if pesquisa.dataInicio?exists && pesquisa.dataFim?exists>
${pesquisa.dataInicio?string("dd'/'MM'/'yyyy")} a ${pesquisa.dataFim?string("dd'/'MM'/'yyyy")}</b>
</#if>
<br>
<br>

<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.hidden name="pesquisa.id" />
		<@ww.hidden name="selecaoAleatoria" />
		<@ww.hidden name="porcentagem" />
		<@ww.hidden name="aplicarPartes" />
		<@ww.hidden name="filtro" />

		<@frt.checkListBox name="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargos(document.forms[0],'gruposCheck');"/>

		<@frt.checkListBox name="cargosCheck" label="Cargos" list="cargosCheckList" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="mudaAction(true, 'populaColaboradores.action?pesquisa.tipoAvaliacao=${pesquisa.tipoAvaliacao}');" class="btnAvancar" accesskey="A">
		</button>
		<button onclick="mudaAction(false, 'prepareInsert.action');" class="btnVoltar" accesskey="V">
		</button>
		<button onclick="mudaAction(false, 'list.action');" class="btnCancelar" accesskey="C">
		</button>
	</div>
</body>
</html>