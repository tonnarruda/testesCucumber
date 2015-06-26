<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Pesquisa</title>
	<#assign formAction="imprimirRelatorioColaboradoresPesquisa.action"/>
	<#assign accessKey="I"/>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorPesquisaDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
<script>
	function populaColaborador()
	{
		pesquisaId = document.getElementById("pesquisaSelect").value;
		if(pesquisaId == "")
		{
		  jAlert("Selecione a Pesquisa");
		}
		else
		{
		  var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
		  DWRUtil.useLoadingMessage('Carregando...');
	 	  ColaboradorPesquisaDWR.getColaboradoresByPesquisa(createListColaborador, pesquisaId,areasIds);
		}
	}

	function createListColaborador(data)
	{
		addChecks('colaboradoresCheck',data)
	}
</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.select name="pesquisa.id" id="pesquisaSelect"  headerKey="" headerValue="Selecione..."  onchange="javascript:populaColaborador()"  label="Pesquisas" list="pesquisas" listKey="id" listValue="titulo" required="true" cssStyle="width: 250px;"/>
		<@frt.checkListBox name="areasCheck" id="areasCheckList" onClick="javascript:populaColaborador()"  label="Áreas Organizacionais" list="areasCheckList" />
		<@frt.checkListBox name="colaboradoresCheck" id="colaboradoresCheck" label="Colaboradores" list="colaboradoresCheckList"/>

	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnRelatorio" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>