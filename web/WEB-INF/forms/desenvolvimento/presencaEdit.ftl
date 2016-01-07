<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');

		.matriz th, .matriz td
		{
			border-bottom: 1px solid #7E7E7E;
			border-right: 1px solid #7E7E7E;
			text-align: center;
		}
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ListaPresencaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<#assign presente = "Presente"/>
	<#assign faltou = "Faltou"/>
	<#assign marcarTodos = "Marcar Todos"/>
	<#assign desmarcarTodos = "Desmarcar Todos"/>

	<script type="text/javascript">
		var colaboradorTurmaIds = new Array();

		function marcarTodos(diaTurmaId, img)
		{
			DWREngine.setErrorHandler(errorListaPresenca);
			if(img.title == "${marcarTodos}")
				ListaPresencaDWR.marcarTodos(function(){alteraImg(diaTurmaId, img);}, diaTurmaId, ${turma.id}, ${empresaSistema.controlarVencimentoCertificacaoPor});
			else
				ListaPresencaDWR.desmarcarTodos(function(){alteraImg(diaTurmaId, img);}, diaTurmaId, ${turma.id}, ${empresaSistema.controlarVencimentoCertificacaoPor});
		}

		function alteraImg(diaTurmaId, img)
		{
			for(var count = 0; count < colaboradorTurmaIds.length; count++)
			{
				var idImg = diaTurmaId + "_" + count;
				elemento = document.getElementById(idImg);

				if(img.title == "${marcarTodos}" && elemento.title == "${faltou}")
				{
					elemento.src = "<@ww.url includeParams="none" value="/imgs/check.gif"/>";
					elemento.title = "${presente}";
				}

				if(img.title == "${desmarcarTodos}" && elemento.title == "${presente}")
				{
					elemento.src = "<@ww.url includeParams="none" value="/imgs/no_check.gif"/>";
					elemento.title = "${faltou}";
				}

				calculaFrequencia(colaboradorTurmaIds[count]);
			}

			if(img.title == "${marcarTodos}")
			{
				img.src = "<@ww.url includeParams="none" value="/imgs/check.gif"/>";
				img.title = "${desmarcarTodos}";
			}
			else
			{
				img.src = "<@ww.url includeParams="none" value="/imgs/no_check.gif"/>";
				img.title = "${marcarTodos}";
			}
		}

		function setFrequencia(diaTurmaId, colaboradorTurmaId, img)
		{
			var presente = img.title != "${presente}"
			DWREngine.setErrorHandler(errorListaPresenca);
			ListaPresencaDWR.calculaFrequencia(function(data){alteraFrequencia(data, colaboradorTurmaId);}, colaboradorTurmaId, ${diaTurmas?size});
			ListaPresencaDWR.updateFrequencia(function(data){mudaImagem(data, colaboradorTurmaId, img);}, diaTurmaId, colaboradorTurmaId, presente, ${empresaSistema.controlarVencimentoCertificacaoPor});
		}

		function mudaImagem(data, colaboradorTurmaId, img)
		{
			if(data)
			{
				img.src = "<@ww.url includeParams="none" value="/imgs/check.gif"/>";
				img.title="${presente}";
			}
			else
			{
				img.src = "<@ww.url includeParams="none" value="/imgs/no_check.gif"/>";
				img.title="${faltou}";
		    }

		    calculaFrequencia(colaboradorTurmaId);
		}

		function calculaFrequencia(colaboradorTurmaId)
		{
			DWREngine.setErrorHandler(errorListaPresenca);
			ListaPresencaDWR.calculaFrequencia(function(data){alteraFrequencia(data, colaboradorTurmaId);}, colaboradorTurmaId, ${diaTurmas?size});
		}

		function alteraFrequencia(data, colaboradorTurmaId)
		{
			var obj = document.getElementById(colaboradorTurmaId + "_");<!-- não retirar o '_', pode ter conflito com id -->
			if(obj != null)
				obj.innerHTML = data;
		}

		function calculaFrequenciaOnLoad()
		{
			for(var count = 0; count < colaboradorTurmaIds.length; count++)
			{
				calculaFrequencia(colaboradorTurmaIds[count]);
			}
		}

		function errorListaPresenca(msg)
		{
			jAlert(msg);
		}
	</script>

<@ww.head/>
	<title>Lista de Frequência</title>

	<#assign retorno="verTurmasCurso.action"/>
	<#if voltarPara?exists>
		<#assign retorno=voltarPara/>
	</#if>
	<#assign rowCount = 0/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="" method="POST">

		Curso: ${turma.curso.nome}<br>
		Turma: ${turma.descricao}<br>
		Período: ${turma.dataPrevIni?string("dd'/'MM'/'yyyy")} a ${turma.dataPrevFim?string("dd'/'MM'/'yyyy")}<br><br>

		<div id="espaco">
			<table class="matriz" cellpadding="5">
				<thead>
					<tr>
						<th align="left"><b>Colaboradores</b></th>
						<#list diaTurmas as diaTurma>
							<th align="left">
								${diaTurma.dia?string("dd'/'MM")}<br />
								<#if diaTurma.turnoDescricao?exists>
									${diaTurma.turnoDescricao} <br />
								</#if>
								
								<img border="0" style="cursor: pointer;" id="marcar" title="${marcarTodos}" onclick="marcarTodos(${diaTurma.id}, this);"  src="<@ww.url includeParams="none" value="/imgs/no_check.gif"/>" align="absbottom">
							</th>
						</#list>
						<th width="45px"><b>%</b></th>
					</tr>
				</thead>
				<tbody>
					<#list colaboradorTurmasLista as lista>
						<tr>
							<td>
								<script type="text/javascript">
									colaboradorTurmaIds[${rowCount}] = ${lista.id};
								</script>
								${lista.colaborador.nome}
							</td>
							<#list diaTurmas as diaTurma>
							<td>
								<#assign checked = false>
								<#list colaboradorPresencas as cp>
									<#if cp.diaTurma.id == diaTurma.id && cp.colaboradorTurma.id == lista.id>
										<#assign checked = true>
									</#if>
								</#list>
	
								<#if checked>
									<img border="0" style="cursor: pointer;" id="${diaTurma.id}_${rowCount}" title="${presente}" onclick="setFrequencia(${diaTurma.id}, ${lista.id}, this);"  src="<@ww.url includeParams="none" value="/imgs/check.gif"/>">
								<#else>
									<img border="0" style="cursor: pointer;" id="${diaTurma.id}_${rowCount}" title="${faltou}" onclick="setFrequencia(${diaTurma.id}, ${lista.id}, this);"  src="<@ww.url includeParams="none" value="/imgs/no_check.gif"/>">
								</#if>
							</td>
							</#list>
							<td align="right">
								<div id="${lista.id}_"></div>
							</td>
						</tr>
						<#assign rowCount = rowCount + 1/>
					</#list>
				</tbody>
			</table>
			<@ww.hidden name="turma.id" />
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="window.location='${retorno}?curso.id=${turma.curso.id}'" class="btnVoltar" accesskey="V"></button>
	</div>

	<script type="text/javascript">
		calculaFrequenciaOnLoad();
	</script>
</body>
</html>