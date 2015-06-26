<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<title>Resultado da Avaliação Agrupado por Faixa</title>

	<#assign validarCamposAvaliacaoDesempenho="return validaFormularioEPeriodo('form', new Array('avaliacaoId','periodoIni','periodoFim'), new Array('avaliacaoId','periodoIni','periodoFim'))"/>
	<#include "../ftl/mascarasImports.ftl" />

	<#if periodoIni?exists>
		<#assign periodoIniFormatado = periodoIni?date/>
	<#else>
		<#assign periodoIniFormatado = ""/>
	</#if>
	<#if periodoFim?exists>
		<#assign periodoFimFormatado = periodoFim?date/>
	<#else>
		<#assign periodoFimFormatado = ""/>
	</#if>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		var imgDel = '<@ww.url includeParams="none" value="/imgs/delete.gif"/>';
		
		function adicionarFaixas(valor) 
		{
			if (!valor)
				valor = '';
			
			var campo = "<li>";
			campo += "<input type='text' name='percentualInicial'  value='" + valor + "' />";
			campo += " a ";
			campo += "<input type='text' name='percentualFinal'  value='" + valor + "' />";
			campo += " <img title='Remover' src="+ imgDel +" onclick='javascript:$(this).parent().remove();' style='cursor:pointer;'/>";
			campo += "</li>";
			
			$('ul#percentual').append(campo);
			prepareInput();
		}
		
		function prepareInput()
		{
			$('#percentual :input').attr('maxlength','3');
			$('#percentual :input').css('width','25px').css('text-align','right');
			
			$('#percentual :input').unbind('keypress');
			$('#percentual :input').keypress(function(event) {
				return(somenteNumeros(event,''));
			});
		}
		
		$(function() 
		{
			if ($('#percentual').children().size() == 0)
				adicionarFaixas();
		});

	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="imprimeRelatorioPerformanceAvaliacaoDesempenho.action" onsubmit="${validarCamposAvaliacaoDesempenho}" method="POST">

		<#list empresas as empresa>
			<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
		</#list>
		
		<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
		<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
		<@ww.select label="Avaliação" name="avaliacao.id" id="avaliacaoId" required="true" listKey="id" listValue="titulo" list="avaliacoes" headerKey="" headerValue="Selecione"/>
		<@ww.select label="Filtrar apenas Colaboradores da Empresa" name="empresa.id" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="-1" headerValue="Todas" cssClass="selectEmpresa"/>

		<label>Agrupar resultado entre as faixas(%):</label>
		<ul id="percentual"></ul>
		
		<a href="javascript:;" onclick="javascript:adicionarFaixas();" style="text-decoration: none;">
			<img src='<@ww.url includeParams="none" value="/imgs/mais.gif"/>'/> 
			Inserir mais uma faixa
		</a>

	</@ww.form>

	<div class="buttonGroupAvaliacaoDesempenho">
		<button class="btnRelatorio" onclick="${validarCamposAvaliacaoDesempenho};"></button>
	</div>
</body>
</html>