<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<#if pesquisa.tipoAvaliacao == 0>
	<title>Inserir Colaboradores na Pesquisa</title>
<#elseif pesquisa.tipoAvaliacao == 1>
	<title>Inserir Colaboradores na Avaliação</title>
</#if>

<script type='text/javascript'>
	function mudaAction(validar, action)
	{
		var qtdSelectObj = qtdeChecksSelected(document.forms[0],'areasCheck');
		var qtdEstabelecimentoSelect = qtdeChecksSelected(document.forms[0], 'estabelecimentosCheck');

		if(qtdEstabelecimentoSelect == 0 && validar)
		{
			jAlert("Nenhum Estabelecimeto selecionado.");
		}
		else if(qtdSelectObj == 0 && validar)
		{
			jAlert("Nenhuma Área Organizacional selecionada.");
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
	<@ww.form name="form" action="" validate="true" method="POST">
		<@ww.hidden name="pesquisa.id" />
		<@ww.hidden name="selecaoAleatoria" />
		<@ww.hidden name="porcentagem" />
		<@ww.hidden name="aplicarPartes" />
		<@ww.hidden name="filtro" />

		<@frt.checkListBox name="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" />
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