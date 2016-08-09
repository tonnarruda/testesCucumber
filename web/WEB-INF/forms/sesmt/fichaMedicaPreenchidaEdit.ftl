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

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>"></script>

	<style> .invalidInput { background: rgb(255, 238, 194) !important; } </style>

	<script type="text/javascript">
    	var urlFind = "<@ww.url includeParams="none" value="/captacao/candidato/findJson.action"/>";
		$(function() {
			$("#vinculo").change(function(){
				value = $(this).val();
				$("#entitityId").val("");
				
				if (value == 'A')
					urlFind = "<@ww.url includeParams="none" value="/captacao/candidato/findJson.action"/>";
				else if (value == 'C')
					urlFind = "<@ww.url includeParams="none" value="/geral/colaborador/findJson.action"/>";

				resetAutocomplete();
			});

			resetAutocomplete();
		});
		
		function resetAutocomplete() {
			$( "#nomeBusca" ).unbind("autocomplete", "data");
			$( "#nomeBusca" ).autocomplete({
				minLength: 3,
	      		source: ajaxData(urlFind),
	      		select: function( event, ui ) { 
					$("#entityId").val(ui.item.id);
				}
	    	}).data( "autocomplete" )._renderItem = renderData;
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
			} else {
				$("#nomeBusca").removeClass("invalidInput");
			}

			return validaFormulario('form', new Array('entityId', 'nomeBusca', 'ficha'), null);
		}
	</script>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign labelFiltro="Ocultar Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_up.gif"/>
	<#assign classHidden=""/>

	<title>Fichas Médicas</title>

	<#assign validarCampos="return validaFormulario('form', new Array('nome','data'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="../../sesmt/fichaMedica/prepareResponderFichaMedica.action" onsubmit="enviaForm();" method="POST" >
		<@ww.select label="Ficha para" name="vinculo" id="vinculo" list=r"#{'A':'Candidato','C':'Colaborador'}" />

		<@ww.textfield label="Nome" id="nomeBusca" cssStyle="width: 500px;"/>
		<@ww.hidden name="candidato.id" id="entityId"/>

		<@ww.select label="Ficha" name="questionario.id" id="ficha" list="fichaMedicas" cssStyle="width: 500px;" required="true" headerKey="" listKey="questionario.id" listValue="questionario.titulo" headerValue="Selecione..."/>

		<@ww.hidden name="voltarPara" value="../../sesmt/fichaMedica/prepareInsertFicha.action"/>
		<@ww.hidden name="inserirFichaMedica" value="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnAvancar" ></button>
		<button onclick="window.location='listPreenchida.action'" class="btnVoltar" ></button>
	</div>

</body>
</html>