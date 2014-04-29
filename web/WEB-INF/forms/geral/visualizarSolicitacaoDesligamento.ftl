<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Solicitação de Desligamento</title>
	
	<style>
		input, textarea, select { background-color: #ececec; }
	</style>
	
	<script>
		function enviarForm(acao)
		{
			$('#formAprovarReprovar').attr('action', acao);
			$('#formAprovarReprovar').submit();
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	
	<@ww.form name="form2">
		<@ww.label cssStyle="font-family: Arial, Helvetica, sans-serif;font-size:16px;font-weight:bold;" name="colaborador.nome"/><br />
	
		<@ww.textfield label="Data de Soliciação de Desligamento" cssStyle="width:90px;" disabled="true" value="${colaborador.dataSolicitacaoDesligamento?string('dd/MM/yyyy')}"/>
		<@ww.select label="Motivo do Desligamento" list="motivoDemissaos"  listKey="id" listValue="motivo" cssStyle="width: 355px;" disabled="true" value="${colaborador.motivoDemissao.id}"/>
		<@ww.textarea label="Observação" cssStyle="width:355px;" disabled="true" value="${colaborador.observacaoDemissao}"/>
	</@ww.form>
	
	<@ww.form id="formAprovarReprovar" name="form" method="POST">
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="colaborador.nome" />
		<@ww.hidden name="colaborador.dataSolicitacaoDesligamento"/>
		<@ww.hidden name="colaborador.dataAdmissao"/>
		<@ww.hidden name="colaborador.motivoDemissao.id"/>
		<@ww.hidden name="colaborador.solicitanteDemissao.id"/>
		<@ww.hidden name="colaborador.observacaoDemissao"/>
		
		<div class="buttonGroup">
			<input type="button" value="" onclick="newConfirm('Deseja realmente aprovar essa solicitação de desligamento?', function(){ enviarForm('aprovarSolicitacaoDesligamento.action'); })" class="btnAprovar" />
			<input type="button" value="" onclick="newConfirm('Deseja realmente reprovar essa solicitação de desligamento?', function(){ enviarForm('reprovarSolicitacaoDesligamento.action'); })" class="btnReprovar" />
			<input type="button" value="" onclick="window.location='prepareAprovarReprovarSolicitacaoDesligamento.action'" class="btnVoltar" />
		</div>
	</@ww.form>
</body>
</html>