<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Reajuste Coletivo/Dissídio</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type='text/javascript'>
		function aplicar()
		{
			var qtdAreasSelect = qtdeChecksSelected(document.forms[0], 'areasCheck');
			var qtdGruposSelect = qtdeChecksSelected(document.forms[0], 'gruposCheck');
			var qtdEstabelecimentoSelect = qtdeChecksSelected(document.forms[0], 'estabelecimentosCheck');

			if(validaFormulario('form', new Array('tabelaReajuste', 'valorDissidio'), null, true))
			{
				value = document.getElementById('optFiltro').value;

				if(value == "1" && qtdAreasSelect == 0)
				{
					jAlert("Nenhuma Área Organizacional selecionada.");
				}
				else if(value == "2" && qtdGruposSelect == 0)
				{
					jAlert("Nenhum Grupo Ocupacional selecionado.");
				}
				else if(qtdEstabelecimentoSelect == 0)
				{
					jAlert("Nenhum Estabelecimeto selecionado.");
				}
				else
				{
					
					document.form.submit();
				}
			}
		}

		function filtrarOpt()
		{
			value = document.getElementById('optFiltro').value;
			if(value == "1")
			{
				document.getElementById('wwgrp_areasCheck').style.display = "";
				document.getElementById('wwgrp_gruposCheck').style.display = "none";
			}
			else if(value == "2")
			{
				document.getElementById('wwgrp_areasCheck').style.display = "none";
				document.getElementById('wwgrp_gruposCheck').style.display = "";

			}
		}
	</script>
	<style type="text/css">
		.divAviso 
		{
			background-color:#FFFFFF;
			border:1px solid #7E9DB9;
			color:#5C5C5A;
			padding:5px;
		}
	</style>
</head>

<body>

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="aplicarDissidio.action" method="POST">
		<@ww.select label="Tabela de Reajuste" name="tabelaReajusteColaborador.id" id="tabelaReajuste" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" required="true"/>
		<@ww.select id="optFiltro" label="Filtrar Por" name="filtrarPor" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();"/>

		<@frt.checkListBox name="areasCheck" id="areaCheck" label="Áreas Organizacionais *" list="areasCheckList" />
		<@frt.checkListBox name="gruposCheck" id="grupoCheck"  label="Grupos Ocupacionais" list="gruposCheckList"/>

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentoCheck" label="Estabelecimentos *" list="estabelecimentosCheckList" />

		<@ww.select id="optFiltro" label="Reajuste Por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o salario atual(%)', '2':'Valor adicionado ao salário(R$)'}" liClass="liLeft" required="true"/>
		<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
		
		<div class="divAviso">
			<span style="color: blue;">
				Atenção:<br> 
				Este reajuste será aplicado apenas aos colaboradores com salário definido por valor. Para reajustar salários definidos por cargo e por índice, crie novos históricos diretamente no 
				<a href="../cargo/list.action" class="ativaSimples" style="color: #CD661D;">CARGO</a> 
				ou
				<a href="../indice/list.action" class="ativaSimples" style="color: #CD661D;">INDICE</a> 
				que receberá o reajuste.
			</span>
		</div>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="aplicar();" class="btnGravar">
		</button>
	</div>

	<script type='text/javascript'>
		<#if filtrarPor?exists && filtrarPor = '1'>
			document.getElementById('wwgrp_areasCheck').style.display = "";
			document.getElementById('wwgrp_gruposCheck').style.display = "none";
		<#else>
			document.getElementById('wwgrp_areasCheck').style.display = "none";
			document.getElementById('wwgrp_gruposCheck').style.display = "";
		</#if>
	</script>
</body>
</html>