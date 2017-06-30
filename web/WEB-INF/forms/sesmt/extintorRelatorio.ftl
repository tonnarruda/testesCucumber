<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Extintores - Manutenção e Inspeção</title>
		
		<#assign date = ""/>
		<#if dataVencimento?exists>
			<#assign date = dataVencimento?date/>
		</#if>
		
		<#include "../ftl/mascarasImports.ftl" />
		
		<script type="text/javascript" >
			function submeter(){
					if($('.fieldsetPadrao').find("input:checked").length > 0)
					return validaFormulario('form', new Array('estabelecimento','data'), new Array('data'));
				else
					jAlert('Marque pelo menos umas das opções de "Exibir Extintores com"');
			}
		</script>
		
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="relatorioManutencaoAndInspecao.action" onsubmit="submeter()" method="POST">
			<@ww.select label="Estabelecimento" id="estabelecimento" name="historicoExtintor.estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey=""  cssStyle="width:240px;" required="true"/>
			<@ww.datepicker label="Vencimentos até" id="data" name="dataVencimento" cssClass="mascaraData" value="${date}" required="true"/>
			
		<li>
			<fieldset class="fieldsetPadrao">
				<ul>
				<legend>Exibir Extintores com:</legend>
			
				<@ww.checkbox label="Inspeção Vencida" name="inspecaoVencida" labelPosition="left"/>
				<@ww.checkbox label="Carga Vencida" name="cargaVencida" labelPosition="left"/>
				<@ww.checkbox label="Teste Hidrostático Vencido" name="testeHidrostaticoVencido" labelPosition="left"/>
				</ul>
			</fieldset>
		</li>
			
		</@ww.form>
		<div class="buttonGroup">
			<button onclick="submeter()" class="btnRelatorio" ></button>
		</div>
	</body>
</html>