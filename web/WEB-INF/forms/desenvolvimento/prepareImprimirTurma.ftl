<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

<@ww.head/>
	<title>Relatório de Plano de Treinamentos</title>
<script type='text/javascript'>
	function populaColaborador()
	{
		DWRUtil.useLoadingMessage('Carregando...');
		var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
		var estabelecimentosIds = getArrayCheckeds(document.forms[0], 'estabelecimentosCheck');
		if(areasIds.length == 0 && estabelecimentosIds.length == 0)
		{
			DWRUtil.removeAllOptions("colaboradores");
			document.getElementById("colaboradores").options[0] = new Option("Todos", "");
		}
		else
			ColaboradorDWR.getColaboradoresAreaEstabelecimento(createListColaborador, areasIds, estabelecimentosIds, <@authz.authentication operation="empresaId"/>);
	}

	function createListColaborador(data)
	{
		DWRUtil.removeAllOptions("colaboradores");
		document.getElementById("colaboradores").options[0] = new Option("Todos", "");
		DWRUtil.addOptions("colaboradores", data);
	}

	function imprimir()
	{
		var qtdSelect = qtdeChecksSelected(document.forms[0], 'areasCheck');
		var qtdSelectE = qtdeChecksSelected(document.forms[0], 'estabelecimentosCheck');

		if(document.getElementById("dnt").value == "-1")
		{
			jAlert("Selecione uma DNT.");
		}
		else if(qtdSelectE == 0)
		{
			jAlert("Nenhum Estabelecimento selecionado.");
		}
		else if(qtdSelect == 0 && document.getElementById("colaboradores").value == "")
		{
			jAlert("Nenhuma Área ou Colaborador selecionado.");
		}
		else
		{
			
			document.form.submit();
		}
	}

	function desableColab(check,colab)
	{
		if(check.checked == true)
			document.getElementById(colab).disabled = true;
		else
			document.getElementById(colab).disabled = false;
	}
</script>
</head>
<body>
<@ww.actionmessage />

<@ww.form name="form" action="imprimirRelatorioTreinamentos.action"  validate="true" method="POST">
	<@ww.select name="dnt.id" id="dnt" list="dnts" listKey="id" required="true" listValue="nome" label="DNT" headerKey="-1" headerValue="Selecione"/>
	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" onClick="populaColaborador();" filtro="true"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" onClick="populaColaborador();" filtro="true"/>
	<@ww.select name="colaborador.id" id="colaboradores" list="colaboradors" listKey="id" listValue="nomeMaisNomeComercial" label="Colaborador" headerKey="" headerValue="Todos"/>
	<@ww.checkbox onchange="javascript: desableColab(this,'colaboradores');" labelPosition="right" label="Imprimir colaboradores sem Turma" name="semPlano" />
</@ww.form>


<div class="buttonGroup">
	<button onclick="imprimir()" class="btnRelatorio" accesskey="I">
	</button>
</div>
</body>
</html>