<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.7.3.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.weekcalendar.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/weekcalendar.js"/>'></script>	
	
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/weekCalendarReset.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery-ui/jquery-ui-1.7.3.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.weekcalendar.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/weekcalendar.css"/>');
	</style>
	
	
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