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

	<#if extintorInspecao.id?exists>
		<title>Editar Inspeção de Extintor</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir Inspeção de Extintor</title>
		<#assign formAction="insert.action"/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign date = ""/>
	<#if extintorInspecao.data?exists>
		<#assign date = extintorInspecao.data?date/>
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
		var empresasResponsaveis = [${empresasResponsaveis}];
		$(function() {
			$("#empresaResponsavel").autocomplete( {source: empresasResponsaveis} );
			
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
	    	$('.ui-autocomplete-input').eq(0).val($(data).data('-2'));
	    	
			dwr.util.removeAllOptions("extintor");
			dwr.util.addOptions("extintor", data);
	    }
	    
	    function gravar()
	    {
			$('.ui-autocomplete-input').eq(0).css('background-color', ($('#extintor').val() == -1) ? '#FFEEC2' : '#FFFFFF');
	    		
	    	return validaFormulario('form', new Array('estabelecimento','extintor','data'), new Array('data'));
	    }
    </script>

	<#assign msgSelecione = "Selecione o estabelecimento." />
	<#if extintors?exists >
		<#assign msgSelecione = "Selecione..." />
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

		<@ww.select label="Estabelecimento" id="estabelecimento" required="true" name="estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:300px;"/>
		<@ww.select label="Extintor" id="extintor" required="true" name="extintorInspecao.extintor.id" list="extintors" listKey="id" listValue="descricao" headerValue="${msgSelecione}" headerKey="-1" />

		<@ww.textfield label="Empresa responsável" name="extintorInspecao.empresaResponsavel" id="empresaResponsavel"/>

		<@ww.datepicker label="Data" id="data" name="extintorInspecao.data" required="true" cssClass="mascaraData" value="${date}"/>

		<h4>Itens irregulares:</h4>
		<#assign linha = 1/>

		<div style="width:40%;">

		<#list extintorInspecaoItems as item>
			<#assign checked = "" />
			<#assign alinharDiv = "divEsq" />

			<#if extintorInspecao.itens?exists>
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
		<button onclick="javascript:gravar();" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>
</body>
</html>