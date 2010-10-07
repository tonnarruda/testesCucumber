<!--
  Autor: Robertson Freitas
  Data: 04/07/2006
  Requisito: RFA019 - Solicitar Banco de Dados Solidário
 -->
<html>
<head>

<@ww.head/>
<#if solicitacaoBDS.id?exists>
	<title>Editar Solicitação de Banco de Dados Solidário</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Solicitação de Banco de Dados Solidário</title>
	<#assign formAction="insertBDS.action"/>
	<#assign accessKey="I"/>
</#if>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>

	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.select label="Área" name="areaOrganizacional.id" list="areas" listKey="id" listValue="nome" />
		<@ww.select label="Cargo" name="cargo.id" list="cargos" listKey="id" listValue="nome"  />

		<@ww.datepicker label="Data" name="data" cssClass="mascaraData" />
		<@ww.select label="Tipo de Seleção" name="tipo" list="vinculos" />
		<@ww.select label="Escolaridade" name="escolaridade" list="escolaridades" />
		<@ww.textarea label="Experiência" name="experiencia" cssStyle="width:445px;" />
		<@ww.select label="Sexo" name="sexo" list="sexos" />
		<@ww.textfield label="Idade Mínima" name="idadeMinima" onkeypress = "return(somenteNumeros(event,''));" size="4"/>
		<@ww.textfield label="Idade Máxima" name="idadeMaxima" onkeypress = "return(somenteNumeros(event,''));" size="4"/>



		<@ww.optiontransferselect
				cssStyle="width: 180px;"
				allowSelectAll="false"

				label="Empresas do BDS"
				leftTitle="Selecionadas"
				rightTitle="Não selecionadas"

				name="empresasBDSIdNaoSelecionadas"
				list="empresasBDSNaoSelecionadasMap"

				doubleName="empresasBDSIdSelecionadas"
				doubleList="empresasBDSSelecionadasMap"

			 required="true" />
		<@ww.hidden label="Id" name="id" />

		<@ww.textarea label="Observação" name="observacao" cssStyle="width:445px;" />

	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>