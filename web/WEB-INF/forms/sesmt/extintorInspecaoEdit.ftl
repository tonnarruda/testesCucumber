<html>
<head>
	<@ww.head/>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<#if extintorInspecao.id?exists>
		<title>Editar Inspeção</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir Inspeção</title>
		<#assign formAction="insert.action"/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign date = ""/>
	<#if extintorInspecao.data?exists>
		<#assign date = extintorInspecao.data?date/>
	</#if>

	<style type="text/css">
		.divEsq {
			float: left;
			width: 49%;
		}
		.divDir {
			float: right;
			width: 49%;
		}
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ExtintorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>

	<script type="text/javascript">
		var empresasResponsaveis = [${empresasResponsaveis}];
		$(function() {
			$("#empresaResponsavel").autocomplete(empresasResponsaveis);
			
			$("#check11").change(function() {
				$('#outroMotivo').toggle($(this).is(':checked'));
			});
			
			$('#outroMotivo').toggle($('#check11').is(':checked'));
		});

		function populaExtintores()
	    {
	      var estabelecimentoId = document.getElementById("estabelecimento").value;

	      DWRUtil.useLoadingMessage('Carregando...');
	      ExtintorDWR.getExtintorByEstabelecimento(createListExtintores, estabelecimentoId, "Selecione...");
	    }

	    function createListExtintores(data)
	    {
	      DWRUtil.removeAllOptions("extintor");
	      DWRUtil.addOptions("extintor", data);
	    }
    </script>

	<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','extintor','data'), new Array('data'))"/>

	<#assign msgSelecione = "Selecione o estabelecimento." />
	<#if extintors?exists >
		<#assign msgSelecione = "Selecione..." />
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

		<@ww.select label="Estabelecimento" id="estabelecimento" required="true" name="estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:240px;"/>
		<@ww.select label="Extintor" id="extintor" required="true" name="extintorInspecao.extintor.id" list="extintors" listKey="id" listValue="descricao" headerValue="${msgSelecione}" headerKey="" cssStyle="width:240px;"/>

		<@ww.textfield label="Empresa responsável" name="extintorInspecao.empresaResponsavel" id="empresaResponsavel"/>

		<@ww.datepicker label="Data" id="data" name="extintorInspecao.data" required="true" cssClass="mascaraData" value="${date}"/>

		<h4>Itens irregulares:</h4>
		<#assign linha = 1/>

		<div style="width:40%;">

		<#list extintorInspecaoItems as item>
			<#assign checked = "" />
			<#assign alinharDiv = "divEsq" />

			<#if extintorInspecao.id?exists>
				<#list extintorInspecao.itens as itemCheck>
					<#if itemCheck.id == item.id>
						<#assign checked  = "checked = \"checked\"" />
						<#break/>
					</#if>
				</#list>
			</#if>

			<#if linha % 2 == 0 >
				<#assign alinharDiv = "divDir" />
			</#if>
			<#assign linha = linha + 1 />

			<div class="${alinharDiv}">
				<input type="checkbox" ${checked} id="check${item.id}" value="${item.id}" name="itemChecks" />
				<label for="check${item.id}">${item.descricao}</label>
			</div>
		</#list>
		</div>

		<@ww.textfield  name="extintorInspecao.outroMotivo" id="outroMotivo" cssStyle="display:none;" maxLength="50"/>

		<div style="clear:both;">
			<br><@ww.textarea label="Observações" name="extintorInspecao.observacao" cssStyle="height:70px; width:340px; " />
		</div>

		<@ww.hidden name="extintorInspecao.id"/>
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>
</body>
</html>