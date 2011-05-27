<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<title>Turnover (rotatividade de colaboradores)</title>
<#include "../ftl/mascarasImports.ftl" />

<#assign validarCampos="return validaFormularioEPeriodoMesAno('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));"/>
<script>
	function filtrarOpt(){
		value =	document.getElementById('optFiltro').value;
		if(value == "1") {
			document.getElementById('divAreas').style.display = "";
			document.getElementById('divCargos').style.display = "none";
		} else if(value == "2") {
			document.getElementById('divAreas').style.display = "none";
			document.getElementById('divCargos').style.display = "";
		}
	}
</script>
</head>
<body>

<@ww.actionerror />
<@ww.actionmessage />


<@ww.div >
O <i>turnover</i> é um indicador que representa a rotatividade de funcionários dentro da empresa. <br/>
Ele é calculado pela fórmula [(Qtd. Admitidos + Qtd. Demitidos / 2) / Qtd. Colaboradores Ativos no início do mês] * 100
</@ww.div>
<br/>

<@ww.form name="form" action="list.action" validate="true" method="POST">
	<div>Período (Mês/Ano)*:</div>
	<@ww.textfield name="dataDe" id="dataDe" required="true"  cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
	<@ww.label value="a" liClass="liLeft"/>
	<@ww.textfield name="dataAte" id="dataAte" required="true" cssClass="mascaraMesAnoData validaDataFim"/>
	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>

	<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" list=r"#{'1':'Área Organizacional', '2':'Cargo'}" onchange="filtrarOpt();"/>
	<div id="divAreas">
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
	</div>
	<div id="divCargos" style="display:none;">
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList"/>
	</div>
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnRelatorio" accesskey="I"></button>
</div>

</body>
</html>