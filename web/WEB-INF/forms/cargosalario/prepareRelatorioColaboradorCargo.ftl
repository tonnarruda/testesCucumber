<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Cargo</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js?version=${versao}"/>"></script>
	
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function compl()
		{
			var compl = document.getElementById("complementares");
			var img = document.getElementById("imgCompl");
			if(compl.style.display == "none")
			{
				compl.style.display = "";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_up.gif"/>"
			}
			else
			{
				compl.style.display = "none";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"
			}
		}
		
		function desabilitaOutroCheck(esseCheck , outroCheck)
		{
			$("#" + outroCheck).attr('disabled', $("#" + esseCheck).attr("checked"));
		}
		
		function submeterAction(action)
		{
			$('form[name=form]').attr('action', action);
			return validaFormulario('form', new Array('data'), new Array('data'));
		}
		
		$(document).ready(function($){
			var empresa = $('#empresa').val();
			
			populaAreaComCargoVinculado(empresa);
			populaEstabelecimento(empresa);
			populaCargosByAreaVinculados();
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);;		
		});
		
		function exibirBtnRelatorio(){
			var exibirBtn = $('#exibirEstabelecimento').is(':checked') && $('#exibirAreaOrganizacional').is(':checked');
			$('#btnRelatorioPDF').toggleDisabled(exibirBtn);
			$('#btnRelatorioPDF').toggleClass('btnRelatorio', !exibirBtn);
			$('#btnRelatorioPDF').toggleClass('btnRelatorioDesabilitado', exibirBtn);
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if data?exists>
		<#assign dataTemp = data?date/>
	<#else>
		<#assign dataTemp = ""/>
	</#if>

	<@ww.form name="form" action="relatorioColaboradorCargo.action" validate="true" method="POST">
		<#list empresas as empresa>	
				<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
		</#list>
		
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="newChangeEmpresa(this.value);"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@ww.datepicker label="Data de Referência" id="data" name="data" required="true" cssClass="mascaraData" value="${dataTemp}"/><br>
		
		<li>
			<a href="javascript:compl();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"> Filtro complementar</a>
		</li>
		<li>
			<@ww.div id="complementares" cssStyle="display: ;" cssClass="divInfo">
				<ul>
					<@ww.checkbox label="" name="exibColabAdmitido" theme="simple"/> 
					Exibir apenas colaboradores admitidos a mais de
					<@ww.textfield theme="simple" name="qtdMeses" id="qtdMeses" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/> 
					meses considerando a data 
					<@ww.select theme="simple" name="opcaoFiltro" list=r"#{'0':'Atual','1':'de Referência'}"/>
					<br><br>
					
					<@ww.checkbox label="" name="exibColabDesatualizado" labelPosition="left" theme="simple"/>
					Exibir apenas colaboradores com dados desatualizados a mais de
					<@ww.textfield theme="simple" name="qtdMesesDesatualizacao" id="qtdMesesAtualizacao" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/> 
					meses.  
					
				</ul>
			</@ww.div>
		</li>
		<br>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="500" onClick="populaCargosByAreaVinculados();" filtro="true" selectAtivoInativo="true" />
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true" />
		
		<@ww.select label="Colocação do Colaborador" name="vinculo" id="vinculo" list="vinculos" headerKey="" headerValue="Todas" cssStyle="width: 180px;" />
		<@ww.checkbox label="Exibir relatório resumido" name="relatorioResumido" id="resumido" labelPosition="left" onchange="desabilitaOutroCheck('resumido', 'exibirSalario')"/>
		
		<@authz.authorize ifAllGranted="EXIBIR_SALARIO_RELAT_COLAB_CARGO">
			<@ww.checkbox label="Exibir Salário" name="exibirSalario" id="exibirSalario" labelPosition="left" onchange="desabilitaOutroCheck('exibirSalario', 'resumido')"/>
		</@authz.authorize>

		<@ww.checkbox label="Exibir estabelecimento" name="exibirEstabelecimento" id="exibirEstabelecimento" labelPosition="left" onchange="exibirBtnRelatorio();" />
		
		<@ww.checkbox label="Exibir área organizacional" name="exibirAreaOrganizacional" id="exibirAreaOrganizacional" labelPosition="left" onchange="exibirBtnRelatorio();" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button id='btnRelatorioPDF' onclick="return submeterAction('relatorioColaboradorCargo.action');" class="btnRelatorio" ></button>
		<button onclick="return submeterAction('relatorioColaboradorCargoXLS.action');" class="btnRelatorioExportar"></button>
	</div>
</body>
</html>
