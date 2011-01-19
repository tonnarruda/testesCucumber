<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	
	<script type="text/javascript">
			var tecnicasUtilizadas = [${tecnicasUtilizadas}];
			
	</script>

    <#assign date = "" />
    <#if data?exists>
		<#assign date = data?date/>
	</#if>
	
	<#if medicaoRisco.id?exists>
		<title>Editar Medição dos Riscos nos Ambientes</title>
		<#assign formAction="update.action"/>
		<#assign atualizacao = true/>
	<#else>
		<title>Inserir Medição dos Riscos nos Ambientes</title>
		<#assign formAction="insert.action"/>
		
		<#assign atualizacao = false/>
	</#if>
	
	<#assign validarCampos="return validarCampos();"/>
	</head>
	<body>
		<@ww.actionerror />
		
		<@ww.form name="form" action="${formAction}" method="POST">
			
			<@ww.datepicker label="Data da medição" id="data" name="data" required="true" cssClass="mascaraData" value="${date}" onchange="setGravarDesabilitado('true');"/>
			
			<#if atualizacao>
				<#assign headerValue="Selecione..." />
			<#else>
				<#assign headerValue="Selecione o estabelecimento." />
			</#if>
                                
			<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaAmbientes();" cssStyle="width:240px;"/>
			<@ww.select label="Ambiente" id="ambiente" required="true" name="ambiente.id" list="ambientes" listKey="id" listValue="nome" headerValue="${headerValue}" headerKey="" cssStyle="width:240px;" onchange="setGravarDesabilitado('true');"/>
			
			<button onclick="return populaRiscos();" class="btnCarregarRiscos"></button>
			
			<p/>
			<style type="text/css">
	
			.dados td
			{
				padding: 1px 1px 2px 2px !important;
				margin-left:0px !important;
				margin-right:0px !important;
				vertical-align:top !important;
			}
			
			</style>
			
			<@display.table name="riscoMedicaoRiscos" id="riscoMedicaoRisco" pagesize=10 class="dados">
			
				<#if riscoMedicaoRisco?exists >
					<#assign intensidadeMedida = riscoMedicaoRisco.intensidadeMedida />
					<#assign tecnicaUtilizada = riscoMedicaoRisco.tecnicaUtilizada />
					<#assign descricaoPpra = riscoMedicaoRisco.descricaoPpra />
					<#assign descricaoLtcat = riscoMedicaoRisco.descricaoLtcat />
				<#else>
					<#assign intensidadeMedida = "" />
					<#assign tecnicaUtilizada = "" />
					<#assign descricaoPpra = "" />
					<#assign descricaoLtcat = "" />
				</#if>
				
				<@display.column property="risco.descricao" title="Risco" style="vertical-align:top; text-align: center; width:80px;"/>
				<@display.column title="Intensidade / Concentração" >
					<input type="text" value="${intensidadeMedida}" name="intensidadeValues" maxLength="100" style="text-align:right; width: 110px;border:1px solid #7E9DB9;" />
				</@display.column>
				<@display.column title="Técnica Utilizada" >
					<input type="text" value="${tecnicaUtilizada}" id="tecnica" name="tecnicaValues" maxLength="100" style="width: 190px;border:1px solid #7E9DB9;" />
					<@ww.hidden name="riscoIds" value="${riscoMedicaoRisco.risco.id}"/>
				</@display.column>
				<@display.column title="Descrição PPRA" >
					<@ww.textarea theme="simple" value="${descricaoPpra}" name="ppraValues" cssStyle="width: 280px;height: 50px;"/>
				</@display.column>
				<@display.column title="Descrição LTCAT">
					<@ww.textarea theme="simple" value="${descricaoLtcat}" name="ltcatValues" cssStyle="width: 280px;height: 50px;"/>
				</@display.column>
			</@display.table>
			
			<@ww.hidden name="medicaoRisco.id" />
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
		
		<script type="text/javascript">
		function populaAmbientes()
	    {
	      var estabelecimentoId = document.getElementById("estabelecimento").value;

	      DWRUtil.useLoadingMessage('Carregando...');
	      AmbienteDWR.getAmbienteByEstabelecimento(createListAmbientes, estabelecimentoId);
	    }

	    function createListAmbientes(data)
	    {
	      DWRUtil.removeAllOptions("ambiente");
	      DWRUtil.addOptions("ambiente", data);
	    }
	    
	    var desabilitarGravar ="${desabilitarGravar?string}";
	    
	    function getDesabilitado()
	    {
	    	return desabilitarGravar;
	    }
	    
	    function setGravarDesabilitado(valor)
	    {
	    	desabilitarGravar = valor;
	    }
	    
	    function populaRiscos()
	    {
	    	document.form.action="carregarRiscos.action";
	    	return validaFormulario('form', new Array('data','ambiente'),new Array('data'));
	    }
	    
	    function validarCampos()
	    {
	    	if (desabilitarGravar == "true")
	    	{
	    		jAlert("Clique em Carregar Riscos.");
	    		return false;
	    	}
	    	
	    	document.form.action="${formAction}";
	    
	    	return validaFormulario('form', new Array('data','ambiente'),new Array('data'))
	    }
    </script>
		
	</body>
</html>
