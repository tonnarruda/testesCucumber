<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		#menuEleicao a.ativaResultado{border-bottom: 2px solid #5292C0;}
		#eleicao { height: 25px; }
		
	</style>

	<title></title>

	<#assign validarCampos="return validaFormulario('form', null, null)"/>
</head>
<body>
	<#include "eleicaoLinks.ftl" />
	<@ww.form name="form" id="form" action="saveVotos.action" onsubmit="${validarCampos}" validate="true" method="POST">
		
		<#assign i = 1/>
		<div id = "eleicao">
			<h3><b>Eleição: ${eleicao.descricao}</b></h3>
		</div>
		<@display.table name="candidatoEleicaos" id="candidatoEleicao" class="dados">
			<@display.column title="Candidato">
			${i} - ${candidatoEleicao.candidato.nome}
		</@display.column>

			<@display.column title="Qtd. de Votos" style="width:80px;text-align:center;">
				<input type="text" name="qtdVotos" id="qtdVotos" value="${candidatoEleicao.qtdVoto}" style="border: 1px solid #BEBEBE;width:40px;text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,''));"/>
				<input type="hidden" name="idCandidatoEleicaos" id="idCandidatoEleicaos" value="${candidatoEleicao.id}"/>
			</@display.column>

			<@display.column title="Eleito" style="width:60px;text-align:center;">
				<#assign checked=""/>
				<#if candidatoEleicao.eleito>
					<#assign checked="checked"/>
				</#if>

				<input type="checkbox" value="${candidatoEleicao.id}" name="eleitosIds" ${checked}/>
			</@display.column>
			
			<#assign i = i + 1/>
			
		</@display.table>

		<@ww.textfield label="Votos nulos" name="eleicao.qtdVotoNulo" id="votoNulo" cssStyle="width:40px;text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,''));"/>
		<@ww.textfield label="Votos brancos" name="eleicao.qtdVotoBranco" id="votoBranco" cssStyle="width:40px;text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,''));"/>

		<@ww.hidden name="eleicao.id"/>
		<@ww.token/>
	</@ww.form>

	<@ww.form name="formImprimir" id="formImprimir" action="/sesmt/eleicao/imprimirResultado.action"  method="POST">
		<@ww.hidden name="eleicao.id"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="document.formImprimir.submit();" class="btnImprimirResultado"></button>
	</div>
</body>
</html>