<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<#if extintor.id?exists>
		<title>Editar Extintor</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
	<#else>
		<title>Inserir Extintor</title>
		<#assign formAction="insert.action"/>
		<#assign edicao=false/>
	</#if>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		var fabricantes = [${fabricantes}];
		var localizacoes = [${localizacoes}];

		$(function() {
			$("#fabricante").autocomplete(fabricantes);
			$("#localizacao").autocomplete(localizacoes);
		});
		
		function gravar() {
			<#if edicao>
				return validaFormulario('form', new Array('tipo'), new Array('dataInspecao','dataRecarga','dataHidro'));
			<#else>
				return validaFormulario('form', new Array('estabelecimento','localizacao','tipo'), new Array('dataInspecao','dataRecarga','dataHidro'));
			</#if>
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" method="POST">
		<#if !edicao>
			<@ww.select label="Estabelecimento" name="historicoExtintor.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
			<@ww.textfield label="Localização" name="historicoExtintor.localizacao" id="localizacao" required="true" maxLength="50"/>
		</#if>

		<@ww.select label="Tipo" id="tipo" required="true" name="extintor.tipo" list="tipos" headerKey="" headerValue="Selecione..."/>
		<@ww.textfield label="Fabricante" name="extintor.fabricante" id="fabricante" maxLength="50"/>
		<@ww.textfield label="Número do Cilindro" name="extintor.numeroCilindro" cssStyle="width:70px;text-align:right;" maxLength="10" onkeypress="return(somenteNumeros(event,''));"/>
		<@ww.textfield label="Capacidade (ex.: 12kg)" cssStyle="width:70px;text-align:right;" name="extintor.capacidade" maxLength="10"/>
		
		<@ww.select label="Ativo" name="extintor.ativo" id="ativo" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 50px;" />
		
		<li>
			<fieldset>
				<legend>Periodicidade máxima (em meses) para:</legend>

				<ul>
					<#if extintor.id?exists>
						<@ww.textfield label="Inspeção" name="extintor.periodoMaxInspecao" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5" />
						<@ww.textfield label="Recarga" name="extintor.periodoMaxRecarga" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5" />
						<@ww.textfield label="Teste hidrostático" name="extintor.periodoMaxHidrostatico" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5"/>
					<#else>
						<table>
							<tr>
								<td>
									<@ww.textfield label="Inspeção" name="extintor.periodoMaxInspecao" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5" liClass="liLeft" />
								</td>
								<td>
									<@ww.datepicker label="Data da inspeção" id="dataInspecao" name="extintorInspecao.data" cssClass="mascaraData" />
								</td>
							</tr>
							<tr>
								<td>
									<@ww.textfield label="Recarga" name="extintor.periodoMaxRecarga" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5" liClass="liLeft"/>
								</td>
								<td>
									<@ww.datepicker label="Data da recarga" id="dataRecarga" name="dataRecarga" cssClass="mascaraData"  />
								</td>
							</tr>
							<tr>
								<td>
									<@ww.textfield label="Teste hidrostático" name="extintor.periodoMaxHidrostatico" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5" liClass="liLeft"/>
								</td>
								<td>
									<@ww.datepicker label="Data do teste hidrostático" id="dataHidro" name="dataHidro" cssClass="mascaraData"  />
								</td>
							</tr>
						</table>
	
					</#if>
				</ul>
			</fieldset>
		</li>
		
		<@ww.token/>
		<@ww.hidden name="extintor.id" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="javascript:gravar();" class="btnGravar"></button>
	
	<#if edicao>
		</div>
		<br>
		<@display.table name="historicoExtintores" id="historico" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../historicoExtintor/prepare.action?historicoExtintor.id=${historico.id}&extintor.id=${extintor.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#if 1 < historicoExtintores.size()>
					<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoExtintor/delete.action?historicoExtintor.id=${historico.id}&extintor.id=${extintor.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<a href="#"><img border="0" src="<@ww.url value="/imgs/delete.gif?historicoExtintor.id=${historico.id}"/>" title="Não é possível excluir o último histórico." class="disabledImg"></a>
				</#if>
			</@display.column>	
			<@display.column title="Data" style="text-align: center; width: 90px;">
				${historico.data?date}
			</@display.column>
			<@display.column title="Estabelecimento" property="estabelecimento.nome"/>
			<@display.column title="Localização" property="localizacao"/>
		</@display.table>

		<div class="buttonGroup">
			<button onclick="window.location='../historicoExtintor/prepare.action?extintor.id=${extintor.id}'" class="btnInserir" accesskey="I"></button>
	</#if>

		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="C"></button>
	</div>
</body>
</html>