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

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.core.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.widget.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.button.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.position.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.1.8.16.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/combobox.js?version=${versao}"/>"></script>

	<script type="text/javascript">
		var reg = [${candidatos_}];
		
		var accentMap = {
	      "á": "a",
	      "ö": "o",
	      "ô": "o"
	    };
	    var normalize = function( term ) {
		    var ret = "";
		    for ( var i = 0; i < term.length; i++ ) {
		      ret += accentMap[ term.charAt(i) ] || term.charAt(i);
		    }
		    return ret;
	    };
    
		$(function() {
		
			$( "#nomeBusca" ).autocomplete({
	      	source: function( request, response ) {
			        	var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
			        	response( $.grep( reg, function( value ) {
			          		value = value.label || value.value || value;
			          		return matcher.test( value ) || matcher.test( normalize( value ) );
		        		}) );
      				}
	    	});
		});

		function pesquisar()
		{
			var nome = document.getElementById("nome").value;
			var cpf = document.getElementById("cpf").value;
			var value = document.getElementById("vinculo").value;
			var empresaId = <@authz.authentication operation="empresaId"/>;

			DWRUtil.useLoadingMessage('Carregando...');

			if (value == 'A')
			{
				CandidatoDWR.find(createListCandidato, nome, cpf, empresaId);
			}
			else if (value == 'C')
			{
				var matricula = document.getElementById("matricula").value;
				ColaboradorDWR.find(createListColaborador, nome, cpf, matricula, empresaId, false, null);
			}

			return false;
		}

		function createListCandidato(data)
		{
			resetSelect("candidato");
			DWRUtil.addOptions("candidato", data);
		}
		function createListColaborador(data)
		{
			resetSelect("colaborador");
			DWRUtil.addOptions("colaborador", data);
		}

		function resetSelect(select)
		{
			DWRUtil.removeAllOptions(select);
			document.getElementById(select).options[0] = new Option("Selecione...", "");
		}

		function enviaForm()
		{
			var value = document.getElementById("vinculo").value;

			if (value == 'A')
			{
				return validaFormulario('form', new Array('candidato','ficha'), null);
			}
			else if (value == 'C')
			{
				return validaFormulario('form', new Array('colaborador', 'ficha'), null);
			}
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
		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<@ww.select label="Ficha para" name="vinculo" id="vinculo" list=r"#{'A':'Candidato','C':'Colaborador'}" />
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssStyle="width: 300px;"/>
					<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpf" cssClass="mascaraCpf" liClass="liLeft" />
					<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matricula" disabled="true" cssStyle="width: 140px;" />
				</ul>

				<button type="button" onclick="pesquisar();" class="btnPesquisar grayBGE"></button>
			</@ww.div>
		</li><br>

		<@ww.textfield label="Nome" name="candidato.nome" id="nomeBusca" cssStyle="width: 300px;"/>

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