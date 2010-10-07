	<style type="text/css">
		#menuEleicao
		{
			margin: -16px;
			height: 25px;
			width: 1000px;
			color: #FFCB03;
			background-color: #23516B;
		}
		#menuEleicao a 
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
		}
		#menuEleicao a:hover
		{
			background-color: #6391AB;
		}
	</style> 


	<!--[if IE]>
		<style type="text/css" media="screen">
			#menuEleicao
			{
				height: 41px;
			}
		</style>
	<![endif]-->
	
<div id="menuEleicao">
	<a href="../eleicao/prepareUpdate.action?eleicao.id=${eleicao.id}" class="ativaGeral">Geral</a>
	<a href="../etapaProcessoEleitoral/calendarioEleicaoList.action?eleicao.id=${eleicao.id}" class="ativaCalendario">Calendário</a>
	<a href="../comissaoEleicao/list.action?eleicao.id=${eleicao.id}" class="ativaComissao">Comissão Eleitoral</a>
	<a href="../candidatoEleicao/list.action?eleicao.id=${eleicao.id}" class="ativaCandidato">Candidatos</a>
	<a href="../candidatoEleicao/listVotos.action?eleicao.id=${eleicao.id}" class="ativaResultado">Resultado</a>
	<a href="../eleicao/prepareComunicados.action?eleicao.id=${eleicao.id}" class="ativaComunicado">Atas e Comunicados</a>
	
	<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>
	
	<br>
	<br>

	<@ww.actionerror />
	<@ww.actionmessage />