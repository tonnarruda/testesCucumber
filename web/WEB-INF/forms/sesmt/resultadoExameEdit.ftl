<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>

	<script type='text/javascript'>
	    function limpaData()
	    {
			$('.mascaraData').each(function(){
				console.log($(this).val());
				if($(this).val() == '  /  /    ' )
					$(this).val(null);
			});
			
			document.form.submit();
	    }
	</script>
	
	<#include "../ftl/mascarasImports.ftl" />

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Resultados dos Exames de ${solicitacaoExame.colaborador.nome}</title>
	<#assign formAction="saveResultados.action"/>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" method="post" onsubmit="limpaData();">
		<@ww.hidden name="solicitacaoExame.id" />
		<@ww.hidden name="solicitacaoExameData" />
		<@ww.hidden name="solicitacaoExame.colaborador.nome" />

		<div>Resultados:</div>
		
		<#assign i = 0/>
		<@display.table name="listaExamesResultados" id="lista" class="dados">

			<@display.column title="Exame" style="width:500px;">
				${lista[0].nome}
			</@display.column>

			<#assign selected=""/>

			<@display.column title="Data Realização" style="width:200px;">
				<@ww.datepicker name="datasRealizacaoExames" id="datasRealizacaoExames_${lista[0].id}" value="${lista[1].data?date}" cssClass="mascaraData" cssStyle="border: 1px solid #BEBEBE"/>
			</@display.column>
			
			<@display.column title="Resultado" style="width:200px;">

				<#assign selectedNaoRealizado = "" />
				<#assign selectedNormal = "" />
				<#assign selectedAnormal = "" />

				<select name="selectResultados" id="selectResultado_${lista[0].id}" style="width:180px;">

					<#if lista[1].resultado == "" || lista[1].resultado == "NAO_REALIZADO">
						<#assign selectedNaoRealizado = "selected" />
					</#if>
					<#if lista[1].resultado == "NORMAL">
						<#assign selectedNormal = "selected" />
					</#if>
					<#if lista[1].resultado == "ANORMAL">
						<#assign selectedAnormal = "selected" />
					</#if>
	
					<option value="NAO_REALIZADO" ${selectedNaoRealizado}>Não Realizado</option>
					<option value="NORMAL" ${selectedNormal}>Normal</option>
					<option value="ANORMAL" ${selectedAnormal}>Alterado</option>

				</select>
			</@display.column>

			<@display.column title="Observação" style="width:400px;">
				<@ww.textfield name="observacoes" value="${lista[1].observacao}" cssStyle="vertical-align:top; width: 340px;border:1px solid #BEBEBE;"/>
			</@display.column>
		</@display.table>
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="limpaData();" class="btnGravar">
		</button>
		<button onclick="document.form.action='list.action';document.form.submit();" class="btnVoltar" accesskey="V">
		</button>
	</div>

</body>
</html>