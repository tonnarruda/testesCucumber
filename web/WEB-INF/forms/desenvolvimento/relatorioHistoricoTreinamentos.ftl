<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<title>Histórico de Treinamentos por Colaborador</title>

	<#include "../ftl/mascarasImports.ftl" />

	<#include "../ftl/showFilterImports.ftl" />

	<@ww.head/>

	<#if dataIni?exists>
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="filtroHistoricoTreinamentos.action" method="POST" id="formBusca">

		<li>
			<@ww.div cssClass="divInfo" cssStyle="width:510px;padding:10px;">
				<ul>
					<@ww.textfield label="Nome do Colaborador" id="nome" name="colaborador.nome" maxLength="100" cssStyle="width: 500px;" />
					<@ww.textfield label="Nome Comercial" id="nomeComercial" name="colaborador.nomeComercial" maxLength="100" cssStyle="width: 500px;"/>
					<@ww.textfield label="Matrícula do Colaborador" id="matricula" name="colaborador.matricula" maxLength="20" cssStyle="width: 170px;"/>
					<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>

					<input type="submit" value="" class="btnPesquisar grayBGE" />
				</ul>
			</@ww.div>
		</li>

		<@ww.hidden id="filtrarPor" name="filtrarPor"/>

	</@ww.form>
	<br>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormularioEPeriodo('formRelatorio',new Array('@colaboradoresCheck'), new Array('dataIni','dataFim'), false, '${urlImgs}')"/>

	<@ww.form id="formRelatorio" name="formRelatorio" action="relatorioHistoricoTreinamentos.action" onsubmit="${validarCampos}" method="POST" >
		
		<@frt.checkListBox label="Colaboradores*" id="colaboradoresCheck" name="colaboradoresCheck" list="colaboradoresCheckList" form="document.getElementById('formRelatorio')" filtro="true"/>
		
		Período:<br>
		<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<@ww.checkbox label="Imprimir matriz comparativa dos cursos exigidos pelo cargo" name="imprimirMatriz" labelPosition="left"/>
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnRelatorio" ></button>
		</div>
	</@ww.form>
</body>
</html>