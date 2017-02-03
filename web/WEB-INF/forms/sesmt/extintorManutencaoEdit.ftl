<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
	<style type="text/css">
		.ui-autocomplete-input {
			height: 16px;
			width: 280px;
			padding: 0 !important;
		}
		.ui-autocomplete[role=listbox] {
			max-width: 300px;
			max-height: 200px;
			background: #FFF;
			border: 1px solid #BEBEBE;
			overflow: auto;
		}
		.ui-autocomplete a {
			display: block;
			padding: 3px;
		}
		#ui-active-menuitem {
			color: #000;
			background: #EEE;
		}
		.ui-button-icon-only {
			border: none;
			background: #EEE;
			padding: 0;
			width: 18px;
			height: 18px;
			border: 1px solid #BEBEBE;
			border-left: none;
			vertical-align: bottom;
			background-image: url(../../imgs/ui-icons_222222_256x240.png);
			background-position: -64px -16px;
		}
		.divEsq {
			float: left;
			width: 49%;
		}
		.divDir {
			float: right;
			width: 49%;
		}
	</style>
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

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ExtintorDWR.js?version=${versao}"/>'></script>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.core.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.widget.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.button.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.position.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/combobox.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">
		$(function() {
			
			$("#check11").change(function() {
				$('#outroMotivo').toggle($(this).is(':checked'));
			});
			
			$('#outroMotivo').toggle($('#check11').is(':checked'));
		});
	

		function populaExtintores()
	    {
	      var estabelecimentoId = document.getElementById("estabelecimento").value;

	      dwr.util.useLoadingMessage('Carregando...');
	      ExtintorDWR.getExtintorByEstabelecimento(estabelecimentoId, "Selecione...", createListExtintores);
	    }

	    function createListExtintores(data)
	    {
	      $('.ui-autocomplete-input').eq(0).val($(data).data('-1'));
	    
	      dwr.util.removeAllOptions("extintor");
	      dwr.util.addOptions("extintor", data);
	    }
	    
	    function gravar()
	    {
			$('.ui-autocomplete-input').eq(0).css('background-color', ($('#extintor').val() == -1) ? '#FFEEC2' : '#FFFFFF');
	    		
	    	return validaFormularioEPeriodo('form', new Array('estabelecimento','extintor','saida'), new Array('saida','retorno'))
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

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.select label="Estabelecimento" id="estabelecimento" required="true" name="estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:240px;"/>
		<@ww.select label="Extintor" id="extintor" required="true" name="extintorManutencao.extintor.id" list="extintors" listKey="id" listValue="descricao" headerValue="${msgSelecione}" headerKey="-1" cssStyle="width:240px;"/>

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
		<button onclick="javascript:gravar();" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>

	<script type="text/javascript">
		desabilitaOutroMotivo();
	</script>

</body>
</html>