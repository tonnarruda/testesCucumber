<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<@ww.head/>
	
	<title>Troca de Localização</title>
	<#assign accessKey="A"/>

	<#if historicoExtintor.data?exists>
		<#assign data = historicoExtintor.data>
	<#else>
		<#assign data = "">
	</#if>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type="text/javascript">
		
		var localizacoes = [${localizacoes}];
		var marcados = [];
		var extintores;
		
		$(function() {
			$("#localizacao").autocomplete(localizacoes);
			
			extintores = $("input[name='extintorsCheck']");
			$('.listCheckBoxBarra').append(" | <span><input type=\"text\" id=\"txtBusca\" placeholder=\"Digite aqui para filtrar\" style=\"width: 180px;\"/></span>");
			
		    $("#txtBusca").keyup(function(){
		        var map = {};

			    $("input[name='extintorsCheck']").each(function(){
			         if($(this).is(':checked') && (marcados.indexOf($(this).val()) == -1))
			          	marcados.push($(this).val());
	        	});
	        	
		        var texto = removerAcento($(this).val().toUpperCase());
			    extintores.each(function(){
			    	 nomeTeste = removerAcento($(this).parent("label").text().toUpperCase());
			         if(nomeTeste.indexOf(texto) >= 0){
			            map[$(this).val()] = $(this).parent("label").text();  
			         }
	        	});
	        	
	        	$('#listCheckBoxextintorsCheck').empty();
	            addChecks('extintorsCheck', map, "remove($(this).val())");
	         	checkJaMarcados();
    		});
    		
		});
		
		function marcarDesmarcarListCheckBox(frm, nameCheck, vMarcar)
		{
			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == nameCheck && elements[i].type == 'checkbox' && !elements[i].disabled)
					{
						elements[i].checked = vMarcar;
						if(!vMarcar)
							remove(elements[i].value);
		 			}
				}
			}
		}
		
		function checkJaMarcados()
		{
			for(i = 0; i < marcados.length; i++)
	           $('#checkGroupextintorsCheck' + marcados[i]).attr('checked',true);
		}

		function remove(id)
		{
			while (marcados.indexOf(id) !== -1) 
 				marcados.splice(marcados.indexOf(id), 1);
		}
		
		function validarCamposAndInsertMarcadosExtintors()
		{
			var map = {};
			extintores.each(function(){
	            map[$(this).val()] = $(this).parent("label").text();  
        	});
            addChecks('extintorsCheck', map);
			checkJaMarcados();
			
			return validaFormulario('form', new Array('@extintorsCheck','dataHist','estabelecimento','localizacao','hora'), new Array('dataHist','hora'));
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="troca.action" onsubmit="validarCamposAndInsertMarcadosExtintors();" validate="true" method="POST">
		
		<@frt.checkListBox label="Extintor / Localização Atual" name="extintorsCheck" id="extintorsCheck" list="extintorsCheckList"  width="600"/>
		
		<fieldset style="padding: 5px 0px 5px 5px; width: 595px;">
		<legend>Nova Localização</legend>
			<@ww.datepicker label="A partir de" name="historicoExtintor.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData" liClass="liLeft"/>
			<@ww.textfield label="Hora" name="historicoExtintor.hora" id="hora" cssStyle="width: 38px;" cssClass="mascaraHora" required="true"/>
			<@ww.select label="Estabelecimento" name="historicoExtintor.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
			<@ww.textfield label="Localização" name="historicoExtintor.localizacao" id="localizacao" required="true" maxLength="50"/>
		</fieldset>
		
		<@ww.hidden name="historicoExtintor.id" />
		<@ww.hidden name="historicoExtintor.extintor.id" />
		<@ww.hidden name="extintor.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validarCamposAndInsertMarcadosExtintors();" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>