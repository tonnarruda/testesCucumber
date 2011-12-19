<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Plano de Treinamento</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'), true)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		var imgFlag;
		function updateRealizada(turmaId, img)
		{
			DWRUtil.useLoadingMessage("Carregando...");
			imgFlag = img;

			var realizada = imgFlag.title != "Realizada"
			DWREngine.setErrorHandler(errorUpdateRealizada);
			TurmaDWR.updateRealizada(mudaImagem, turmaId, realizada);
		}

		function mudaImagem(data)
		{
			if(data)
			{
				imgFlag.src = "<@ww.url value="/imgs/flag_green.gif"/>";
				imgFlag.title="Realizada";
			}
			else
			{
				imgFlag.src = "<@ww.url value="/imgs/flag_red.gif"/>";
				imgFlag.title="Não Realizada";
		    }
		}

		function errorUpdateRealizada(msg)
		{
			jAlert(msg);
		}

		function mudaAction()
		{
			var dataInicial = document.getElementById("dataIni").value;
			var dataFinal = document.getElementById("dataFim").value;

			document.form.action = "imprimirPlanoTreinamento.action";
			if(validaFormulario('form', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'), true))
			{
				
				document.form.submit();

				document.getElementById("dataIni").value = dataInicial;
				document.getElementById("dataFim").value = dataFinal;
			}
		}
		
		
		function enviarEmail(turmaId)
		{
			DWRUtil.useLoadingMessage('Enviando...');
			DWREngine.setErrorHandler(errorMsg);
			TurmaDWR.enviarAviso(enviarAviso, turmaId, <@authz.authentication operation="empresaId"/>);
		}

		function enviarAviso(data)
		{
			alert(data);
		}

		function errorMsg(msg)
		{
			jAlert("Envio de email falhou.");
		}
		
		
	</script>

	<#include "../ftl/showFilterImports.ftl" />

	<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.dataIni?exists>
		<#assign dateIni = filtroPlanoTreinamento.dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.dataFim?exists>
		<#assign dateFim = filtroPlanoTreinamento.dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="filtroPlanoTreinamento.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Curso" name="filtroPlanoTreinamento.cursoId" list="cursos"  listKey="id" listValue="nome"  headerKey="" headerValue="Todos" />
		Período:<br>
		<@ww.datepicker name="filtroPlanoTreinamento.dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="filtroPlanoTreinamento.dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />
		<@ww.select label="Realizada" name="filtroPlanoTreinamento.realizada" list=r"#{'T':'Todas','S':'Sim','N':'Não'}" />

		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="javascript: document.form.action ='filtroPlanoTreinamento.action';">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<#if turmas?exists && 0 < turmas?size >
		<#assign totalParticipantes = 0/>
		<#assign totalCustos = 0/>
		<@display.table name="turmas" id="turma" class="dados">
			<@display.column title="Ações" media="html" class="acao" style="width: 95px;">
				<a href="prepareUpdate.action?turma.id=${turma.id}&planoTreinamento=true"><img border="0" title="<@ww.text name="list.edit.hint"/> Turma" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="enviarEmail(${turma.id});" ><img border="0" title="Enviar aviso por email" src="<@ww.url value="/imgs/icon_email.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?turma.id=${turma.id}&planoTreinamento=true'});"><img border="0" title="<@ww.text name="list.del.hint"/> Turma" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<a href="../colaboradorTurma/list.action?turma.id=${turma.id}&planoTreinamento=true"><img border="0" title="Colaboradores Inscritos na Turma" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>

				<#if turma.realizada>
					<img border="0" style="cursor: pointer;" onclick="updateRealizada(${turma.id}, this);" title="Realizada" src="<@ww.url value="/imgs/flag_green.gif"/>">
				<#else>
					<img border="0" style="cursor: pointer;" onclick="updateRealizada(${turma.id}, this);" title="Não Realizada" src="<@ww.url value="/imgs/flag_red.gif"/>">
				</#if>

				<#assign totalParticipantes = totalParticipantes + turma.qtdPessoas/>
				<#if turma.custo?exists>
					<#assign totalCustos = totalCustos + turma.custo/>
				</#if>
			</@display.column>
			<@display.column property="curso.nome" title="Curso"/>
			<@display.column property="descricao" title="Turma" style="width: 135px;"/>
			<@display.column property="periodoFormatado" title="Período" style="width: 140px;"/>
			<@display.column property="horario" title="Horário" style="width: 90px;"/>
			<@display.column property="instrutor" title="Instrutor" style="width: 100px;"/>
			<@display.column property="curso.cargaHorariaMinutos" title="CH" style="width: 30px;"/>
			<@display.column property="qtdPessoas" title="Partic." style="width: 100px;" style="text-align:right;"/>
			<@display.column property="custoFormatado" title="Invest." style="width: 80px;" style="text-align:right;"/>

			<@display.footer>
		  		<tr>
			  		<td colspan="7" >Total : ${turmas?size} turmas</td>
			  		<td style="text-align:right;">${totalParticipantes}</td>
			  		<td style="text-align:right;">${totalCustos?string(",##0.00")}</td>
		  		</tr>
	 		</@display.footer>
		</@display.table>

		<div class="buttonGroup">
			<button class="btnInserirTurma" onclick="window.location='prepareInsert.action?planoTreinamento=true'"></button>
			<button class="btnRelatorio" onclick="javascript: mudaAction();"></button>
		</div>
	</#if>

</body>
</html>