<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/buttons.css"/>');
</style>
<title>Históricos do Ambiente - ${ambiente.nome}</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@display.table name="historicoAmbientes" id="historicoAmbiente" pagesize=10 class="dados">
		<@display.column title="Ações" class="acao">
			<@frt.link href="prepareUpdate.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}" imgTitle="Editar" iconeClass="fa-edit"/>
			<#if 1 < historicoAmbientes?size>
					<@frt.link href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}'});" imgTitle="Excluir" iconeClass="fa-times"/>
				<#else>
					<@frt.link href="javascript:;" imgTitle="Não é possível remover o único histórico do ambiente" iconeClass="fa-times" opacity=true/>
				</#if>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:70px;"/>
		<@display.column property="nomeAmbiente" title="Nome do Ambiente" style="text-align: center;width:250px;"/>
		<@display.column property="descricao" title="Histórico - Descrição"/>
	</@display.table>

	<div class="buttonGroup">
		<button onclick="window.location='../historicoAmbiente/prepareInsert.action?ambiente.id=${ambiente.id}'" accesskey="I">Inserir</button>
		<button onclick="window.location='../ambiente/list.action'">Voltar</button>
	</div>
</body>
</html>