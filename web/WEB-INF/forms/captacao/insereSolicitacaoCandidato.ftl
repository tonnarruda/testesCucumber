<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type='text/javascript'>
	    function populaSolicitacoes(empresaId)
	    {
	      	SolicitacaoDWR.getSolicitacoes(createListSolicitacaos, empresaId);	
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

	<@ww.form name="form" action="gravarSolicitacoesCandidato.action" onsubmit="${validarCampos}"  validate="true" method="POST">
	
		<@ww.hidden name="candidato.id"/>
		<@ww.hidden name="statusCandSol"/>
		<@ww.select label="Empresa" name="empresa.id" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" disabled="!compartilharCandidatos" onchange="populaSolicitacoes(this.value)";/>
		<@frt.checkListBox label="Solicitações disponíveis" name="solicitacaosCheckIds" id="solicitacao" list="solicitacaosCheck" width="600"/>
	
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="${validarCampos};" accesskey="I">
		</button>
		<button onclick="window.location='../../captacao/candidato/list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>