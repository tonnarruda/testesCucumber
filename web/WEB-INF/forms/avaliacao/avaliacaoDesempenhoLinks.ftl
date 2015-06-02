 	<style type="text/css">
		#menuParticipantes
		{
			margin: -16px;
			color: #FFCB03;
			background-color: #E0DFDF;
		}
		#menuParticipantes a 
		{
			float: left;
			display: block;
			padding: 7px 15px;
			font-family: Arial, Helvetica, sans-serif;
			font-size: 12px;
			text-align: center;
			text-decoration: none;
			color: #5C5C5A;
  			border-right: 1px solid #C6C6C6;
		}
		#menuParticipantes a:hover
		{
			color: #5292C0;
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
	<a href="prepareAvaliados.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}" class="ativaAvaliado" id="ativaAvaliado">Avaliados</a>
	<a href="prepareAvaliadores.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}" class="ativaAvaliador" id="ativaAvaliador">Avaliadores</a>
	<div style="clear: both"></div>
</div>
	
	<br>
	<br>

	<@ww.actionerror />
	<@ww.actionmessage />