<html>
<head>
<@ww.head/>
	<#if eleicao.id?exists>
		<#assign formAction="update.action"/>
	<#else>
		<#assign formAction="insert.action"/>
	</#if>
	<style type="text/css">#menuEleicao a.ativaGeral{border-bottom: 2px solid #5292C0;}</style>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('posse','estabelecimento'), new Array('posse'))"/>

    <#if eleicao?exists && eleicao.posse?exists>
      <#assign dataPosse = eleicao.posse?date/>
    <#else>
      <#assign dataPosse = ""/>
    </#if>
</head>
<body>

    <#if eleicao?exists && eleicao.id?exists>
		<#include "eleicaoLinks.ftl" />
    <#else>
		<title>Inserir Eleição</title>
    </#if>

	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

		<@ww.textfield label="Descrição" name="eleicao.descricao" id="descricao" maxlength="100" cssStyle="width:160px;"/>

		<@ww.datepicker label="Posse" name="eleicao.posse" id="posse" required="true" cssClass="mascaraData" value="${dataPosse}"/>

		<@ww.select label="Estabelecimento" required="true" id="estabelecimento" name="eleicao.estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey=""/>

		<@ww.hidden name="eleicao.id"/>
		<@ww.hidden name="eleicao.empresa.id"/>
		<@ww.hidden name="eleicao.votacaoIni"/>
		<@ww.hidden name="eleicao.votacaoFim"/>
		<@ww.hidden name="eleicao.horarioVotacaoIni"/>
		<@ww.hidden name="eleicao.horarioVotacaoFim"/>
		<@ww.hidden name="eleicao.qtdVotoNulo"/>
		<@ww.hidden name="eleicao.qtdVotoBranco"/>
		<@ww.hidden name="eleicao.inscricaoCandidatoIni"/>
		<@ww.hidden name="eleicao.inscricaoCandidatoFim"/>
		<@ww.hidden name="eleicao.localInscricao"/>
		<@ww.hidden name="eleicao.localVotacao"/>
		<@ww.hidden name="eleicao.apuracao"/>
		<@ww.hidden name="eleicao.horarioApuracao"/>
		<@ww.hidden name="eleicao.localApuracao"/>
		<@ww.hidden name="eleicao.sindicato"/>
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>