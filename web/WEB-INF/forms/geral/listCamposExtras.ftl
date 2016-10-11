<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
	<title>Configurar campos extras para o cadastro de colaborador e candidato</title>
</head>
<body>
	<style type="text/css">
		#wwgrp_empresa{
			display: -webkit-box;
		}
		#wwctrl_empresa{
			margin-left: 5px;
		}
	
		.configCampos {
			margin: 8px 0;
			width: 600px;
			border: 1px solid #BBB;
			border-collapse: collapse;
		}
		.configCampos td {
			border: 1px solid #BBB;
			padding: 0 5px;
			width: 80px;
			text-align: center;
		}
		#endRight{
			border-right: 0px;
		}
		#endLeft{
			border-left: 0px;
		}
		.configCampos td, .configCampos th {
			padding: 5px;
		}
		.configCampos td:first-child {
			width: 36px;
		}
		.configCampos td.campo {
			width: 400px;
			text-align: left;
		}
		.grupoObrigatorio{
			padding-left: 25px !important;
		}
		
		.configCampos td:nth-child(1) {
			border-right: none;
		}
		.configCampos td:nth-child(2) {
			border-left: none;
		}
		
		.configCampos td:nth-child(1) > input[type="checkbox"], .configCampos th:nth-child(1) > input[type="checkbox"] {
		    display: none;   
		}
		
		.configCampos td:nth-child(1) > label, .configCampos th:nth-child(1) > label {
		    cursor: pointer;
		    height: 0px;
		    position: relative; 
		    width: 40px;  
		}
		
		.configCampos td:nth-child(1) > label::before, .configCampos th:nth-child(1) > label::before {
		    background: rgb(0, 0, 0);
		    box-shadow: inset 0px 0px 10px rgba(0, 0, 0, 0.5);
		    border-radius: 8px;
		    content: '';
		    height: 12px;
		    left: 8px;
		    margin-top: -5px;
		    position:absolute;
		    opacity: 0.3;
		    transition: all 0.4s ease-in-out;
		    width: 30px;
		}
		.configCampos td:nth-child(1) > label::after, .configCampos th:nth-child(1) > label::after {
		    background: rgb(255, 255, 255);
		    border-radius: 16px;
		    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
		    content: '';
		    height: 20px;
		    left: 5px;
		    margin-top: -8px;
		    position: absolute;
		    top: 14px;
		    transition: all 0.3s ease-in-out;
		    width: 20px;
		}
		.configCampos td:nth-child(1) > input[type="checkbox"]:checked + label::before, .configCampos th:nth-child(1) > input[type="checkbox"]:checked + label::before {
		    background: inherit;
		    opacity: 0.5;
		}
		.configCampos td:nth-child(1) > input[type="checkbox"]:checked + label::after, .configCampos th:nth-child(1) > input[type="checkbox"]:checked + label::after {
		    background: inherit;
		    left: 22px;
		}
		.label-visivel {
			background-color: #5292C0;
		}
		
		.desabilitado {
			background: #E8E8E8;	
			color: gray;
		}
		.desabilitado .label-visivel {
			background-color: #F2F2F2 !important;
		}
	</style>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<script type='text/javascript'>
		var entidade = '${entidade?if_exists}';
		$(function() {
			showBox(entidade == '' ? $("#menuCadastro a:eq(0)").attr("class") : entidade);
		});
	
		function enviaForm()
		{
			var abas = $('input[name=camposVisivels]:checked').parents('table');
			var abasStr = $.map(abas, function (t){ return t.id; }).join(',');
			
			$('input[name=camposTabs]').val(abasStr);
			$("form:visible").submit();		
		}
	
		function abas(num) {
			$("div[id*=box]:visible div[class*=option-aba]").attr("style", "border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);");
			$("div[id*=box]:visible .option-aba"+num).attr("style", "border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(246, 246, 246); background: rgb(246, 246, 246);");

			$("div[id*=box]:visible div[class*=content]").hide();
			$("div[id*=box]:visible .content"+num).show();
		}
		
		function showBox(entity) {
			$("div[id*=box]").hide();
			$("#box-"+entity).show();
			
			$("#box-colaborador").html("");
			$("#box-candidato").html("");
			$("#box-candidatoExterno").html("");
			
			$("#box-"+entity).load("configCamposExtras.action?entidade="+entity+"&empresa.id="+${empresa.id});
			
			$("#menuCadastro a").removeClass("ativa");
			$("#menuCadastro a."+entity).addClass("ativa");
			
			abas(1);
		}
	</script>	

	<style type="text/css">
		#menuCadastro
		{
			margin: -16px;
			margin-bottom: 16px;
			color: #FFCB03;
			background: #E0DFDF;
		}
		#menuCadastro a 
		{
			float: left;
			display: block;
			padding: 7px 15px;
			font-family: Arial, Helvetica, sans-serif;
			font-size: 12px;
			text-align: center;
			text-decoration: none;
			color: #5C5C5A;
			border-right: 1px solid #C6C6C6;
		}
		#menuCadastro a:hover
		{
			  color: #5292C0;
		}
		#menuCadastro .ativa {
			border-bottom: 2px solid #5292C0;
		}
		
		.abas div
		{
			display: inline;
			padding: 2px 8px;
			*padding: 5px 8px;
			background-color: #D5D5D5;
			border: 1px solid #CCC;
		}
		.abas div a
		{
			font-family: Verdana, sans-serif;
			font-size: 10px;
			color: #000;
			text-decoration: none;
			text-transform: uppercase;
		}
		.abas div a:hover
		{
			text-decoration: none;
			color: #600;
		}
		div.option-aba1
		{
			background-color: #F6F6F6;
			border-bottom-color: #F6F6F6;
		}
		.abas
		{
			position: relative;
			z-index: 500;
			margin-bottom: 2px;
		}
		
		.content1, .content2, .content3, .content4, .content5, .content6, .content7 {
			background-color: #F6F6F6;
			padding: 10px;
			border: 1px solid #CCC;
		}
		.mini-info {
			padding: 7px 12px;
		    padding-left: 36px;
		    margin-top: 5px;
		    border-radius: 3px;
		    color: #2b7bb5;
		    width: 922px;
		    background: #DCEBFC;
		    background-image: url(/fortesrh/imgs/infoHelp.png);
		    background-repeat: no-repeat;
		    background-size: 17px 17px;
			background-position: 10px center;
		}
	</style> 

	<div id="menuCadastro">	
		<@authz.authorize ifAllGranted="ROLE_CONFIG_CAMPOS_EXTRAS_PARA_COLABORADOR">			
			<a href="javascript:showBox('colaborador');" class="colaborador">Colaborador</a>
		</@authz.authorize>
		<@authz.authorize ifAllGranted="ROLE_CONFIG_CAMPOS_EXTRAS_PARA_CANDIDATO">			
			<a href="javascript:showBox('candidato');" class="candidato">Candidato</a>
		</@authz.authorize>
		<@authz.authorize ifAllGranted="ROLE_CONFIG_CAMPOS_EXTRAS_PARA_CANDIDATO_EXT">			
			<a href="javascript:showBox('candidatoExterno');" class="candidatoExterno">Candidato pelo módulo externo</a>
		</@authz.authorize>
		<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
		<div style="clear: both"></div>
	</div>
	
	<div id="box-colaborador"></div>
	<div id="box-candidato"></div>
	<div id="box-candidatoExterno"></div>
	
	<div class="mini-info"> As configurações realizadas serão visíveis nos cadastros de colaborador e candidato, caso estejam habilitadas em: Utilitários > Configurações > Campos Extras </div>
	<div class="buttonGroup">
		<button onclick="enviaForm()" class="btnGravar" ></button>
	</div>
</body>
</html>