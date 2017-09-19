<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Faturamentos</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	* Utilizado no Painel de Indicadores de C&S<br><br>
	<@display.table name="faturamentoMensals" id="faturamentoMensal" class="dados"  style="width:800px;">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?faturamentoMensal.id=${faturamentoMensal.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?faturamentoMensal.id=${faturamentoMensal.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="mesAno" title="Mês/Ano" format="{0,date, MM/yyyy}"  style="width:50px;"/>
		<@display.column property="estabelecimento.nome" title="Estabelecimento"/>
		<@display.column property="valor" title="Valor" format="{0, number, #,##0.00}" style="text-align: right;"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
