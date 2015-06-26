<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<#if cargo.id?exists>
	<title>Transferir Faixas entre Cargos</title>
	<#assign formAction="transferirFaixasCargo.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('cargoOrigem','faixas','novaFaixa'), null)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script language='javascript'>

		function populaFaixas()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var cargoId = document.getElementById("cargoOrigem").value;

			if (cargoId != "")
			{
				FaixaSalarialDWR.getFaixas(createListFaixas, cargoId);
			}
			else
			{
				DWRUtil.removeAllOptions("faixas");
			}
		}

		function createListFaixas(data)
		{
			DWRUtil.removeAllOptions("faixas");
			DWRUtil.addOptions("faixas", data);
		}

	</script>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />


	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
	
		<@ww.hidden id="cargoId" name="cargo.id" />
		<@ww.hidden id="cargoNome" name="cargo.nome" />
		<@ww.hidden id="pagina" name="page"/>
	
		<p style="font-weight:bold;">Cargo: ${cargo.nome}</p>
		<@ww.select label="Cargo Origem" onchange="populaFaixas(this,'cargoOrigem')" id="cargoOrigem" list="cargos"  listKey="id" listValue="nome"  headerKey="" headerValue="Selecione..." required="true"/>
		<@ww.select label="Faixa" id="faixas" name="faixa.id" list="faixasDoCargo"  listKey="id" listValue="nome"  headerKey="" headerValue="Selecione..." cssStyle="width:110px;" required="true"/>
		<@ww.textfield label="Novo nome da faixa" id="novaFaixa" name="novaFaixaNome" cssClass="inputNome" maxLength="5" cssStyle="width: 40px;" required="true"/>
	<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button class="btnGravar" onclick="${validarCampos};" > </button>
		<button class="btnVoltar" onclick="window.location='list.action?page=${page}'"> </button>
	</div>
	
	<p/>
	<div>
	
		<@display.table name="faixasDoCargo" id="faixaSalarial" pagesize=10 class="dados" defaultsort=2 sort="list">
				<@display.column title="Ações" class="acao">
					<a href="../faixaSalarial/prepareUpdate.action?faixaSalarialAux.id=${faixaSalarial.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
					<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='../faixaSalarial/delete.action?faixaSalarial.id=${faixaSalarial.id}&cargo.id=${cargo.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				</@display.column>
				<@display.column property="nome" title="Faixa"/>
			</@display.table>
	
	</div>
	
</body>
</html>