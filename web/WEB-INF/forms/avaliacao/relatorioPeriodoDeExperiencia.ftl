<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório Período de Acompanhamento de Experiência</title>

<#assign DataAtual = "${dataDoDia}"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

		<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" method="POST">
		
			<@ww.datepicker label="Data" name="dataReferencia" required="true" id="dataReferencia" value="${DataAtual}" cssClass="mascaraData"/>
			<!-- <@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas"/> -->
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick='jQuery("#imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia").submit();'></button>
		</div>
</body>
</html>