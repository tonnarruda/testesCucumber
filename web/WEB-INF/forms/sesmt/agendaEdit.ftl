<html>
	<head>
		<@ww.head/>
		
		<script type="text/javascript">
		function validarPeriodos()
		{
			qtdPeriodo = document.getElementById('qtdPeriodo').value;
			periodicidade = document.getElementById('periodicidade').value;
			
			if (qtdPeriodo == 0)
				document.getElementById('qtdPeriodo').value = '';
				
			if (periodicidade == 0)
				document.getElementById('periodicidade').value = '';
				
			return validaFormulario('form', new Array('mesAno','evento','estabelecimento','qtdPeriodo','periodicidade'), new Array('mesAno'));
		}
		</script>
		
		<#if agenda.id?exists>
			<title>Editar Agenda</title>
			<#assign formAction="update.action"/>
			<#assign validarCampos="return validaFormulario('form', new Array('mesAno','evento','estabelecimento'), new Array('mesAno'))"/>
		<#else>
			<title>Inserir Agenda</title>
			<#assign formAction="insert.action"/>
			<#assign validarCampos="return validarPeriodos()"/>
		</#if>
		
		<#include "../ftl/mascarasImports.ftl" />
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form name="form" action="${formAction}" method="POST">
			<@ww.textfield label="Mês/Ano" name="dataMesAno" id="mesAno" cssClass="mascaraMesAnoData"/>
			<@ww.select label="Estabelecimento" name="agenda.estabelecimento.id" id="estabelecimento" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 300px;"  required="true" />
			<@ww.select label="Evento" name="agenda.evento.id" id="evento" list="eventos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 300px;"  required="true" />
			<@ww.hidden name="agenda.id" />
		
			<#if !agenda.id?exists>
				<li>
					<@ww.div cssClass="divInfo" >
						<ul>	
							<@ww.textfield label="Qtd. Repetições" name="qtdPeriodo" id="qtdPeriodo" cssStyle="width:20px; text-align:right;" maxLength="2" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" />
							<@ww.textfield label="" name="periodicidade" id="periodicidade" cssStyle="width:20px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress = "return(somenteNumeros(event,''));" />
							<@ww.select label="Periodicidade" name="tipoPeriodo" id="tipo" list=r"#{0:'Mês(es)',1:'Ano(s)'}" cssStyle="width: 80px;" />
						</ul>
					</@ww.div>
				</li>
			</#if>
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
