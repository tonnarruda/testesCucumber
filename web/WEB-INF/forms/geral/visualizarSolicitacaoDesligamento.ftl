<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Solicitação de Desligamento</title>
	
	<style>
		input, textarea, select { background-color: #ececec; }
	</style>
</head>
<body>
	<@ww.actionerror />
	
	<@ww.form name="form2">
		<@ww.label cssStyle="font-family: Arial, Helvetica, sans-serif;font-size:16px;font-weight:bold;" name="colaborador.nome"/><br />
	
		<@ww.textfield label="Data de Soliciação de Desligamento" cssStyle="width:90px;" disabled="true" value="${colaborador.dataSolicitacaoDesligamento?string('dd/MM/yyyy')}"/>
		<@ww.select label="Motivo do Desligamento" list="motivoDemissaos"  listKey="id" listValue="motivo" cssStyle="width: 355px;" disabled="true" value="${colaborador.motivoDemissao.id}"/>
		<@ww.textarea label="Observação" cssStyle="width:355px;" disabled="true" value="${colaborador.observacaoDemissao}"/>
	</@ww.form>
	
	<@ww.form name="form" action="aprovarSolicitacaoDesligamento.action" method="POST">
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="colaborador.dataSolicitacaoDesligamento"/>
		<@ww.hidden name="colaborador.dataAdmissao"/>
		<@ww.hidden name="colaborador.motivoDemissao.id"/>
		<@ww.hidden name="colaborador.observacaoDemissao"/>
		
		<div class="buttonGroup">
			<input type="button" value="" onclick="newConfirm('Deseja realmente confirmar esse desligamento?', function(){ this.form.submit(); })" class="btnAprovar" />
			<input type="button" value="" onclick="newConfirm('Deseja realmente reprovar esse desligamento?', function(){ window.location='reprovarSolicitacaoDesligamento.action?colaborador.id=${colaborador.id}'})" class="btnReprovar" />
			<input type="button" value="" onclick="window.location='prepareAprovarReprovarSolicitacaoDesligamento.action'" class="btnVoltar" />
		</div>
	</@ww.form>
</body>
</html>