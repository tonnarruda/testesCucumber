	<style type="text/css">
		#menuEleicao
		{
			margin: -16px;
			color: #FFCB03;
			background-color: #E0DFDF;
		}
		#menuEleicao a 
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
		#menuEleicao a:hover
		{
			color: #5292C0;
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
	<a href="javascript: executeLink('../eleicao/prepareUpdate.action?eleicao.id=${eleicao.id}');" class="ativaGeral">Geral</a>
	<a href="javascript: executeLink('../etapaProcessoEleitoral/calendarioEleicaoList.action?eleicao.id=${eleicao.id}');" class="ativaCalendario">Calendário</a>
	<a href="javascript: executeLink('../comissaoEleicao/list.action?eleicao.id=${eleicao.id}');" class="ativaComissao">Comissão Eleitoral</a>
	<a href="javascript: executeLink('../candidatoEleicao/list.action?eleicao.id=${eleicao.id}');" class="ativaCandidato">Candidatos</a>
	<a href="javascript: executeLink('../candidatoEleicao/listVotos.action?eleicao.id=${eleicao.id}');" class="ativaResultado">Resultado</a>
	<a href="javascript: executeLink('../eleicao/prepareComunicados.action?eleicao.id=${eleicao.id}');" class="ativaComunicado">Atas e Comunicados</a>
	
	<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>
	
	<br>
	<br>

	<@ww.actionerror />
	<@ww.actionmessage />