<html>
<head>
	<@ww.head/>
	<#if extintorManutencao.id?exists>
		<title>Editar Manutenção de Extintor</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir Manutenção de Extintor</title>
		<#assign formAction="insert.action"/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign dateSaida = ""/>
	<#assign dateRetorno = ""/>
	<#if extintorManutencao.saida?exists>
		<#assign dateSaida = extintorManutencao.saida?date/>
	</#if>
	<#if extintorManutencao.retorno?exists>
		<#assign dateRetorno = extintorManutencao.retorno?date/>
	</#if>
	
	<#assign msgSelecione = "Selecione o estabelecimento." />
	<#if extintors?exists >
		<#assign msgSelecione = "Selecione..." />
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

	<script type="text/javascript">

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

	    function desabilitaOutroMotivo()
	    {
	    	elemOutroMotivo = document.getElementById('outroMotivo');
	    	elemMotivo = document.getElementById('motivo');

	    	if (elemMotivo.value=="0")
	    	{
				elemOutroMotivo.style.width="270px";
				elemOutroMotivo.style.display="";
	    	}
	    	else
	    	{
				elemOutroMotivo.style.display="none";
	    	}
	    }
    </script>

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('estabelecimento','extintor','saida'), new Array('saida','retorno'))"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.select label="Estabelecimento" id="estabelecimento" required="true" name="estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:240px;"/>
		<@ww.select label="Extintor" id="extintor" required="true" name="extintorManutencao.extintor.id" list="extintors" listKey="id" listValue="descricao" headerValue="${msgSelecione}" headerKey="" cssStyle="width:240px;"/>

		<@ww.datepicker label="Data de Saída" id="saida" name="extintorManutencao.saida" required="true" cssClass="mascaraData validaDataIni" value="${dateSaida}" liClass="liLeft"/>
		<@ww.datepicker label="Data de Retorno" id="retorno" name="extintorManutencao.retorno" cssClass="mascaraData validaDataFim" value="${dateRetorno}"/>

		<@ww.select label="Motivo" id="motivo" name="extintorManutencao.motivo" list="motivos" headerKey="" headerValue="Selecione..." onchange="desabilitaOutroMotivo();"/>
		<@ww.textfield label="" theme="simple" name="extintorManutencao.outroMotivo" id="outroMotivo" cssStyle="display:none;" />

		<h4>Serviços efetuados:</h4>
		<#assign linha = 1/>

		<div style="width:46%;">
			<#list extintorManutencaoServicos as item>
				<#assign checked = "" />
				<#assign alinharDiv = "divEsq" />

				<#if extintorManutencao.id?exists>
					<#list extintorManutencao.servicos as itemCheck>
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
					<input type="checkbox" ${checked} id="check${item.id}" value="${item.id}" name="servicoChecks" />
					<label for="check${item.id}">${item.descricao}</label>
				</div>
			</#list>
		</div>
		<div style="clear:both;">
			<br><@ww.textarea label="Observações" name="extintorManutencao.observacao" cssStyle="height:70px; width:340px; " />
		</div>

		<@ww.token/>
		<@ww.hidden name="extintorManutencao.id"/>
</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>

	<script type="text/javascript">
		desabilitaOutroMotivo();
	</script>

</body>
</html>