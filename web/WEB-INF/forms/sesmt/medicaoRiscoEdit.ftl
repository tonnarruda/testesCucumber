<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');

		.dados td
		{
			padding: 1px 1px 2px 2px !important;
			margin-left:0px !important;
			margin-right:0px !important;
			vertical-align:top !important;
		}
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type="text/javascript">
		var tecnicasUtilizadas = [${tecnicasUtilizadas}];
	</script>

	<#assign empresaControlaRiscoPor><@authz.authentication operation="empresaControlaRiscoPor"/></#assign>
    <#assign date = "" />
    <#if data?exists>
		<#assign date = data?date/>
	</#if>
	
	<#if medicaoRisco.id?exists>
		<title>Editar Medição dos Riscos</title>
		<#assign formAction="update.action"/>
		<#assign atualizacao = true/>
	<#else>
		<title>Inserir Medição dos Riscos</title>
		<#assign formAction="insert.action"/>
		
		<#assign atualizacao = false/>
	</#if>
	</head>
	<body>
		<@ww.actionerror />
		
		<@ww.form name="form" action="${formAction}" method="POST">
			
			<@ww.datepicker label="Data da medição" id="data" name="data" required="true" cssClass="mascaraData" value="${date}" onchange="setGravarDesabilitado('true');"/>
			
			<#if empresaControlaRiscoPor == 'A'>
				<#if atualizacao>
					<#assign headerValue="Selecione..." />
				<#else>
					<#assign headerValue="Selecione o estabelecimento" />
				</#if>
				<@ww.select label="Estabelecimento" id="estabelecimento" required="true" name="estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaAmbientes();" cssStyle="width:240px;"/>
				<@ww.select label="Ambiente" id="ambiente" required="true" name="ambiente.id" list="ambientes" listKey="id" listValue="nome" headerValue="${headerValue}" headerKey="" cssStyle="width:240px;" onchange="setGravarDesabilitado('true');"/>
			<#else>
				<#if atualizacao>
					<#assign headerValue="Selecione..." />
				<#else>
					<#assign headerValue="Selecione o cargo" />
				</#if>
				<@ww.select label="Cargo" id="cargo" name="cargo.id" required="true" list="cargos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaFuncoes();" cssStyle="width:240px;"/>
				<@ww.select label="Função" id="funcao" required="true" name="funcao.id" list="funcoes" listKey="id" listValue="nome" headerValue="${headerValue}" headerKey="" cssStyle="width:240px;" onchange="setGravarDesabilitado('true');"/>
			</#if>
			
			<button type="button" onclick="populaRiscos();" class="btnCarregarRiscos"></button>
			<br /><br />
			
			<@display.table name="riscoMedicaoRiscos" id="riscoMedicaoRisco" class="dados">
			
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
				
				<@display.column property="risco.descricao" title="Risco" style="vertical-align:top; text-align: center;"/>
				<@display.column title="Intensidade / Concentração" >
					<input type="text" value="${intensidadeMedida}" name="intensidadeValues" maxLength="100" style="text-align:right; width: 110px;border:1px solid #7E9DB9;" />
				</@display.column>
				<@display.column title="Técnica Utilizada" >
					<input type="text" value="${tecnicaUtilizada}" id="tecnica" name="tecnicaValues" maxLength="100" style="width: 190px;border:1px solid #7E9DB9;" />
					<@ww.hidden name="riscoIds" value="${riscoMedicaoRisco.risco.id}"/>
				</@display.column>
				<@display.column title="Descrição PPRA">
					<@ww.textarea theme="simple" value="${descricaoPpra}" name="ppraValues" cssStyle="width: 260px;height: 50px;"/>
				</@display.column>
				<@display.column title="Descrição LTCAT">
					<@ww.textarea theme="simple" value="${descricaoLtcat}" name="ltcatValues" cssStyle="width: 260px;height: 50px;"/>
				</@display.column>
			</@display.table>
			
			<@ww.hidden name="medicaoRisco.id" />
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button type="button" onclick="validarCampos();" class="btnGravar"></button>
			<button type="button" onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
		
		<script type="text/javascript">
		
		$(function() {
			insereHelp(3);			
			insereHelp(4);			
		});
		
		function insereHelp(posicao)
		{
			var id = "tooltipHelp" + posicao;
			$("#riscoMedicaoRisco th:eq(" + posicao + ")" ).append('<img id="' + id + '" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 1px" />');
			
			$('#' + id).qtip({
				content: 'Sugerimos que utilize este campo para descrever fonte geradora, meio de propagação, dano a saúde, tipo de exposição (eventual, intermitente, etc).'
			});
		}
		
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

		function populaFuncoes()
	    {
	      var cargoId = document.getElementById("cargo").value;

	      DWRUtil.useLoadingMessage('Carregando...');
	      FuncaoDWR.getByCargo(createListCargos, cargoId);
	    }

	    function createListCargos(data)
	    {
	      DWRUtil.removeAllOptions("funcao");
	      DWRUtil.addOptions("funcao", [{id: -1, nome:'Nenhuma'}], "id", "nome");
	      DWRUtil.addOptions("funcao", data);
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
	    	submeter("carregarRiscos.action");
	    }
	    
	    function validarCampos()
	    {
	    	if (desabilitarGravar == "true")
	    	{
	    		jAlert("Clique em Carregar Riscos.");
	    		return false;
	    	}
	    
	    	submeter("${formAction}");
	    }
	    
	    function submeter(formAction)
	    {
	    	document.form.action = formAction;
	    	
	    	<#if empresaControlaRiscoPor == 'A'>
	    		validaFormulario('form', new Array('data','ambiente'), new Array('data'));
			<#else>
	    		validaFormulario('form', new Array('data','funcao'), new Array('data'));
	    	</#if>
	    }
    </script>
		
	</body>
</html>
