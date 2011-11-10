 	<style type="text/css">
		#menuParticipantes
		{
			margin: -16px;
			height: 25px;
			width: 1000px;
			color: #FFCB03;
			background-color: #23516B;
		}
		#menuParticipantes a 
		{
			float: left;
			display: block;
			padding: 5px 15px;
			font-weight: bold!important;
			font-family: Arial, Helvetica, sans-serif;
			font-size: 12px;
			text-align: center;
			text-decoration: none;
			color: #FFF;
			border-left: 1px solid #327195; /* Sombra clara */
			border-right: 1px solid #1C4055; /* Sombra escura */
			border-top: 1px solid #327195; /* Sombra clara */
		}
		#menuParticipantes a:hover
		{
			background-color: #6391AB;
		}
	</style> 


	<!--[if IE]>
		<style type="text/css" media="screen">
			#menuParticipantes
			{
				height: 41px;
			}
		</style>
	<![endif]-->
	
<div id="menuParticipantes">
	<a href="prepareAvaliados.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}" class="ativaAvaliado">Avaliados</a>
	<a href="prepareAvaliadores.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}" class="ativaAvaliador">Avaliadores</a>
	<a style="width: 760px; border-right: none; border-top: 1px solid #327195;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>
	
	<br>
	<br>

	<@ww.actionerror />
	<@ww.actionmessage />