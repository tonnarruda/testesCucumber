<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>


<title>Inserir Nono Dígito em Celulares</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');

		.yui-ac-container, .yui-ac-content, .yui-ac-shadow, .yui-ac-content ul{
			width: 400px;
		}
	</style>
	
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<#assign validarCampos="return validaFormulario('form', new Array('@estadosCheck'), null)"/>

</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="insert.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@frt.checkListBox name="estadosCheck" id="estadosCheck" label="Estados" list="estadosCheckList" filtro="true"/>
	
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar">
		</button>
	</div>
	
</body>
</html>