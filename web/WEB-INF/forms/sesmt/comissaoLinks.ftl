<style type="text/css">
	#menuComissao
	{
		margin: -16px;
		width: 1000px;
		color: #FFCB03;
		background-color: #23516B;
	}
	#menuComissao a
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
	#menuComissao a:hover
	{
		background-color: #6391AB;
	}
</style>

<div id="menuComissao">
	<a href="../comissao/prepareUpdate.action?comissao.id=${comissao.id}" class="ativaGeral">Geral</a>
	<a href="../comissaoPeriodo/list.action?comissao.id=${comissao.id}" class="ativaComissao">Comissão</a>
	<a href="../comissaoReuniao/list.action?comissao.id=${comissao.id}" class="ativaReuniao">Reuniões</a>
	<a href="../comissaoPlanoTrabalho/list.action?comissao.id=${comissao.id}" class="ativaPlano">Plano de Trabalho</a>
	<a href="../comissao/prepareDocumentos.action?comissao.id=${comissao.id}" class="ativaDocumentos">Atas e Comunicados</a>

	<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>

	<br>
	<br>

	<@ww.actionerror />
	<@ww.actionmessage />