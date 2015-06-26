<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/backUpList.css?version=${versao}"/>');
</style>
<title>Agrupar Investimentos</title>
<#assign formAction="agrupar.action"/>
<#assign accessKey="A"/>
<#assign validarCampos="return validaFormulario('form', new Array('@gastosCheck'), null)"/>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	<@ww.label label="Grupo de Investimentos" name="grupoGastoAgrupar.nome" />
	<li>
		<div id="wwlbl_agrupar_" class="wwlbl">
			<label for="agrupar_" class="desc">Investimentos do Grupo <b>${grupoGastoAgrupar.nome}</b>:</label>
		</div>
	</li>
	<li>
		<div id="divlista">
			<#list gastoDoGrupos as lista>
				<#if lista?exists>
					${lista.nome}<br>
				</#if>
			</#list>
		</div>
	</li>
	<br>
	<@frt.checkListBox name="gastosCheck" id="gastosCheck" label="Investimentos ainda sem Grupo" list="gastosCheckList" filtro="true"/>
	<@ww.hidden name="grupoGastoAgrupar.id" />
	<@ww.token/>
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}">
	</button>
	<button onclick="window.location='../grupoGasto/list.action'" class="btnCancelar" accesskey="V">
	</button>
</div>
</body>
</html>