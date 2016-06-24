<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/font-awesome.min.css"/>');</style>

	<script type="text/javascript" src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>

	<style> .invalidInput { background: rgb(255, 238, 194) !important; } </style>

	<script type="text/javascript">
    	var urlFind = '<@ww.url includeParams="none" value="/captacao/candidato/findJson.action"/>';
		$(function() {
			$("#vinculo").change(function(){
				value = $(this).val();
				$("#entitityId").val("");
				
				if (value == 'A')
					urlFind = '<@ww.url includeParams="none" value="/captacao/candidato/findJson.action"/>';
				else if (value == 'C')
					urlFind = '<@ww.url includeParams="none" value="/geral/colaborador/findJson.action"/>';

				resetAutocomplete();
			});

			resetAutocomplete();
		});
		
		function resetAutocomplete() {
			$( "#nomeBusca" ).unbind("autocomplete", "data");
			$( "#nomeBusca" ).autocomplete({
				minLength: 3,
				delay: 500,
	      		source: ajaxData(urlFind),
	      		select: function( event, ui ) { 
					$("#entityId").val(ui.item.id);
					
				}
	    	}).data( "autocomplete" )._renderItem = renderData;
	    	$("#entityId").val("");
		};

		function enviaForm()
		{
			var value = document.getElementById("vinculo").value;
			
			if (value == 'A')
				$("#entityId").attr("name","candidato.id");
			else if (value == 'C')
				$("#entityId").attr("name","colaborador.id");
				

			if ( $("#entityId").val() == "" || $("#nomeBusca").val() == "" || $("#nomeBusca").val().length < 3) {
				$("#nomeBusca").addClass("invalidInput");
				jAlert("Informe um " + ($("#vinculo").val() == 'A' ? "candidato" : "colaborador") + " existente.");
				return false;
			}else{
				return validaFormulario('form', new Array('entityId', 'nomeBusca', 'ficha'), null,  true);
			}
		};
		
		function apagarEntityId(){
			if ( $("#entityId").val() != ""){
				if(event.keyCode != 13 || $("#entityId").val() != ""){
					$("#entityId").val("");
				}	
			}
		};
	</script>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign labelFiltro="Ocultar Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_up.gif"/>
	<#assign classHidden=""/>

	<title>Fichas Médicas</title>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form id="form" name="form" action="../../sesmt/fichaMedica/prepareResponderFichaMedica.action" onsubmit="return enviaForm()" method="POST" >
		<@ww.select label="Ficha para" name="vinculo" id="vinculo" list=r"#{'A':'Candidato','C':'Colaborador'}" />

		<@ww.textfield label="Nome" id="nomeBusca" cssStyle="width: 500px;" cssClass="inputNome" required="true" onchange="apagarEntityId()"/>
		<img src="${urlImgs}/backspace.png" style="float: left;margin-top: -22px;margin-left: 506px; cursor: pointer; display:none" onclick="decideDesabilitarCampo(false);" id="backspace">
		
		
		<@ww.hidden name="candidato.id" id="entityId"/>

		<@ww.select label="Ficha" name="questionario.id" id="ficha" list="fichaMedicas" cssStyle="width: 500px;" required="true" headerKey="" listKey="questionario.id" listValue="questionario.titulo" headerValue="Selecione..."/>

		<@ww.hidden name="voltarPara" value="../../sesmt/fichaMedica/prepareInsertFicha.action"/>
		<@ww.hidden name="inserirFichaMedica" value="true"/>
	<div class="buttonGroup">
		<button type="submit" class="btnAvancar" ></button>
		<button onclick="javascript: executeLink('listPreenchida.action');" class="btnVoltar" ></button>
	</div>
	</@ww.form>


</body>
</html>