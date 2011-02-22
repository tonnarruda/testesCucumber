<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.weekcalendar.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.dateFormat-1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/weekcalendar.js"/>'></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/HistoricoCandidatoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>	
	
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/weekCalendarReset.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.weekcalendar.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/weekcalendar.css"/>');
	</style>
	<script type='text/javascript'>
	
		function updateAgenda(id, dataIni, dataFim, observacao)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			HistoricoCandidatoDWR.updateAgenda(verify, id, dataIni, jQuery.format.date(dataIni, "HH:mm"), jQuery.format.date(dataFim, "HH:mm"), observacao);
		}

		function verify(success)
		{
			if(!success)
				jAlert("Erro ao gravar informações.");
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
</body>
</html>