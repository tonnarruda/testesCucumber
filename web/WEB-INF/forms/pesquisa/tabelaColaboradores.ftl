<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<#if pesquisa.tipoAvaliacao == 0>
	<title>Inserir Colaboradores na Pesquisa</title>
<#elseif pesquisa.tipoAvaliacao == 1>
	<title>Inserir Colaboradores na Avaliação</title>
</#if>

<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<script type='text/javascript'>
	function verificaSelecao(frm)
	{
		with(frm)
		{
			for(i = 0; i < elements.length; i++)
			{
				if(elements[i].name == 'colaboradoresId')
				{
					if(elements[i].type == 'checkbox' && elements[i].checked)
					{
						document.formColab.action = "insert.action";
						document.formColab.submit();
						return true;
					}
				}
			}
		}

		jAlert('Selecione ao menos um colaborador!');
		return false;
	}

	function marcarDesmarcar(frm)
	{
		var vMarcar;

		if (document.getElementById('md').checked)
		{
			vMarcar = true;
		}
		else
		{
			vMarcar = false;
		}

		with(frm)
		{
			for(i = 0; i < elements.length; i++)
			{
				if(elements[i].name == 'colaboradoresId' && elements[i].type == 'checkbox')
				{
					elements[i].checked = vMarcar;
				}
			}
		}
	}
	function mudaAction(action)
	{
		document.formColab.action = action;
		
		document.formColab.submit();
	}
</script>
</head>
<body>

<@ww.form name="formColab" action="insert.action" method="POST">
	<@display.table name="colaboradores" id="colaborador" class="dados" defaultsort=2 sort="list">
		<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formColab);' checked />" style="width: 30px; text-align: center;">
			<input type="checkbox" value="${colaborador.id}" name="colaboradoresId" checked />
		</@display.column>
		<@display.column property="nomeComercial" title="Colaborador"/>
		<@display.column property="estabelecimento.nome" title="Estabelecimento"/>
		<@display.column property="areaOrganizacional.descricao" title="Área"/>
	</@display.table>

	<@ww.hidden name="pesquisa.id"/>
</@ww.form>

<div class="buttonGroup">
	<button onclick="verificaSelecao(document.formColab);" class="btnInserirSelecionados" accesskey="I">
	</button>
	<button onclick="javascript:history.go(-1);" class="btnVoltar" accesskey="V">
	</button>
	<button onclick="mudaAction('list.action');" class="btnCancelar" accesskey="C">
	</button>
</div>

</body>
</html>