<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    @import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/font-awesome_buttons.css"/>');
	    
	    #wwgrp_descricaoCBO
	    {
			float: left;
	    	background-color: #E9E9E9;
			width: 420px;
			padding-left: 4px;
		}
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioAjudaESocialDWR.js?version=${versao}"/>'></script>

	<title>Históricos da Função ${funcao.nome}</title>
	<#assign accessKey="A"/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@display.table name="historicoFuncaos" id="historicoFuncao" pagesize=10 class="dados">
		<@display.column title="Ações" class="acao">
			<@frt.link href="../historicoFuncao/prepareUpdate.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}" imgTitle="Editar" iconeClass="fa-edit"/>
			<#if 1 < historicoFuncaos?size>
				<@frt.link href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoFuncao/delete.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}'});" imgTitle="Excluir" iconeClass="fa-times"/>
			<#else>
				<@frt.link href="javascript:;" imgTitle="Não é possível remover o único histórico da função" iconeClass="fa-times" opacity=true/>
			</#if>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
		<@display.column property="funcaoNome" title="Nome da Funação" style="text-align: center;width:100px;"/>
		<@display.column property="codigoCbo" title="CBO" style="text-align: center;width:50px;"/>
		<@display.column property="descricao" title="Descrição das Atividades Executadas"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button onclick="window.location='../historicoFuncao/prepareInsert.action?funcao.id=${funcao.id}'" accesskey="I">Inserir</button>
		<button onclick="window.location='../funcao/list.action'">Voltar</button>
	</div>
</body>
</html>