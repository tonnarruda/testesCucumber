<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js?version=${versao}"/>'></script>

	<script type='text/javascript'>
	    function populaSolicitacoes(empresaId)
	    {
	      	SolicitacaoDWR.getSolicitacoes(empresaId, createListSolicitacaos);	
	    }
	    
	    function createListSolicitacaos(data)
	    {
	    	addChecks('solicitacaosCheckIds',data)
	    }
	</script>
	
<title>Incluir candidato "${candidato.nome}" em uma solicitação em aberto</title>
<#assign validarCampos="return validaFormulario('form', new Array('@solicitacaosCheckIds'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="gravarSolicitacoesCandidato.action" onsubmit="${validarCampos}"  validate="true" method="POST">
	
		<@ww.hidden name="candidato.id"/>
		<@ww.hidden name="statusCandSol"/>
		<@ww.hidden name="voltarPara"/>
		<#if !compartilharCandidatos>
			<@ww.hidden name="empresa.id"/>
		</#if>

		<@ww.select label="Empresa" name="empresa.id" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" disabled="!compartilharCandidatos" onchange="populaSolicitacoes(this.value)";/>
		<@frt.checkListBox label="Solicitações disponíveis" name="solicitacaosCheckIds" id="solicitacao" list="solicitacaosCheck" width="600" filtro="true"/>
	
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="${validarCampos};" accesskey="I"></button>
		
		<#if voltarPara?exists && voltarPara?has_content>
			<button onclick="window.location='${voltarPara}'" class="btnVoltar" accesskey="V"></button>
		<#else>
			<button onclick="window.location='../../captacao/candidato/list.action'" class="btnVoltar" accesskey="V"></button>
		</#if>
	</div>
</body>
</html>