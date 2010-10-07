<!--
  Autor: Robertson Freitas
  Data: 23/06/2006
  Requisito: RFA015
 -->
<html>
<head>
<@ww.head/>
<#if solicitacao.id?exists>
	<title>Editar Solicitação</title>
	<#assign formAction="updateRecebida.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Solicitação</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>
</head>
<body>
<@ww.actionerror />

<@ww.form name="form" action="${formAction}" validate="true" method="POST">

	<@ww.select label="Área" name="areaOrganizacional.id" list="areas" listKey="id" listValue="nome" required="true" />
	<@ww.select label="Cargo" name="cargo.id" list="cargos" listKey="id" listValue="nome" required="true" />
	<@ww.textfield label="Quantidade" name="quantidade" required="true" />
	<@ww.select label="Situação" name="situacao" list="situacoes" required="true"/>
	<@ww.select label="Tipo de Seleção" name="tipo" list="vinculo" required="true"/>
	<@ww.select label="Solicitar a" name="solicitados.id" list="solicitados" listKey="id" listValue="nome" required="true" />

	<@ww.textfield label="Cidade" name="cidade" required="true" />
	<@ww.select label="Estado" name="uf" list="estados"	/>

	<@ww.select label="Escolaridade" name="escolaridade" list="escolaridades" />
	<@ww.textarea label="Experiência" name="experiencia" cssStyle="width:445px;"/>
	<@ww.textfield label="Remuneração (R$)" name="remuneracao" />

	<@ww.optiontransferselect
		cssStyle="width: 180px;"
		allowSelectAll="false"

		label="Benefícios"
		leftTitle="Benefícios não Selecionados"
		rightTitle="Benefícios selecionados"

		name="beneficiosIdNaoSelecionadas"
		list="beneficiosNaoSelecionadasMap"

		doubleName="beneficiosIdSelecionadas"
		doubleList="beneficiosSelecionadasMap"

	 />

	<@ww.textfield label="Idade Mínima" name="idadeMinima" size="4"/>
	<@ww.textfield label="Idade Máxima" name="idadeMaxima" size="4"/>
	<@ww.textfield label="Horário" name="horario" />
	<@ww.select label="Sexo" name="sexo" list="sexos" />
	<@ww.textarea label="Inform. Complementares" name="infoComplementares" cssStyle="width:445px;" />

	<@ww.hidden label="Data" name="data" />
	<@ww.select label="Solicitador" name="solicitador.id" list="solicitadores" listKey="id" listValue="nome" required="true" />

	<@ww.textarea label="Observação" name="observacao" cssStyle="width:445px;" />

	<@ww.hidden label="SolicitacaoMae" name="solicitacaoMae.id" />
	<@ww.hidden label="Id" name="id" />
	<@ww.hidden name="solicitacao.id" />

<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="document.form.submit();" class="btnGravar" accesskey="${accessKey}">
	</button>
	<button onclick="window.location='solicitaBDS.action?solicitacao.id=${solicitacao.id}'" class="btnSolicitarBDS" accesskey="S">
	</button>
	<button onclick="window.location='listRecebidas.action'" class="btnCancelar" accesskey="V">
	</button>
</div>
</body>
</html>