<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.weekcalendar.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.dateFormat-1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/weekcalendar.js"/>'></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/HistoricoCandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>	
	
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/weekCalendarReset.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.weekcalendar.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/weekcalendar.css"/>');
		
		.waDivFormulario { padding: 0; }
	</style>
	<script type='text/javascript'>
	
		function updateAgenda(id, dataIni, dataFim, observacao)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			HistoricoCandidatoDWR.updateAgenda(verify, id, dataIni, $.format.date(dataIni, "HH:mm"), $.format.date(dataFim, "HH:mm"), observacao);
		}

		function verify(success)
		{
			if(!success)
				jAlert("Erro ao gravar informações.");
		}
		
		function imprimir()
		{
			var dataIni = $('.wc-day-1').text().match(/(\d\d)\/(\d\d)\/(\d\d\d\d)/)[0];
			var dataFim = $('.wc-day-8').text().match(/(\d\d)\/(\d\d)\/(\d\d\d\d)/)[0];
			window.location='imprimirAgenda.action?dataIni=' + dataIni + '&dataFim=' + dataFim ;
		}
	</script>
	
	<title>Agenda</title>
</head>
<body>
	<div id='calendar'></div>
	<div id="event_edit_container">
		<form>
			<input type="hidden" />
			<ul>
				<li>
					<span>Data: </span><span class="date_holder"></span> 
				</li>
				<li>
					<label for="start">Inicio: </label><select name="start"><option value=""></option></select>
				</li>
				<li>
					<label for="end">Fim: </label><select name="end"><option value=""></option></select>
				</li>
				<li>
					<label for="body">Observação: </label><textarea name="body"></textarea>
				</li>
			</ul>
		</form>
	</div>
	<div class="buttonGroup" style="margin-left:10px;">
		<button onclick="imprimir();" class="btnImprimirPdf grayBGE"></button>
	</div>
</body>
</html>