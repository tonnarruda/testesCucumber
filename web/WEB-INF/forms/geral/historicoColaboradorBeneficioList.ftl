<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Históricos de Benefícios do Colaborador</title>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage/>
<strong>Colaborador:</strong> ${colaborador.nomeComercial}
<br><br>
<@display.table name="historicoColaboradorBeneficios" id="historicoColaboradorBeneficio" pagesize=10 class="dados" defaultsort=2 sort="list">
	<@display.column title="Ações" class="acao">
		<a href="prepareUpdate.action?historicoColaboradorBeneficio.id=${historicoColaboradorBeneficio.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?historicoColaboradorBeneficio.id=${historicoColaboradorBeneficio.id}&colaborador.id=${colaborador.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column property="data" title="A partir de " format="{0,date,dd/MM/yyyy}" style="width:100px;text-align:center"/>
	<@display.column title="Beneficios">
		<table class="dados">
			<tbody>
				<#assign classZebra="even"/>
				<#list historicoColaboradorBeneficio.beneficios as beneficio>
						<#if classZebra == "even">
							<#assign classZebra="odd"/>
						<#else>
							<#assign classZebra="even"/>
						</#if>
						<tr class="${classZebra}">
							<td>
								${beneficio.nome}
							</td>
						</tr>
		      	</#list>
	  		</tbody>
	  	</table>
	</@display.column>
</@display.table>

<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action?colaborador.id=${colaborador.id}'" accesskey="N">
	</button>
	<button onclick="window.location='../colaborador/list.action'" class="btnVoltar" accesskey="V">
	</button>
</div>

</body>
</html>