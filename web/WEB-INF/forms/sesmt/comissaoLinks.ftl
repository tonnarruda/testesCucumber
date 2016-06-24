<style type="text/css">
	#menuComissao
	{
		margin: -16px;
		color: #FFCB03;
		background-color: #E0DFDF;
	}
	#menuComissao a
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
	#menuComissao a:hover
	{
		color: #5292C0;
	}
</style>

<div id="menuComissao">
	<a href="javascript: executeLink('../comissao/prepareUpdate.action?comissao.id=${comissao.id}');" class="ativaGeral">Geral</a>
	<a href="javascript: executeLink('../comissaoPeriodo/list.action?comissao.id=${comissao.id}');" class="ativaComissao">Comissão</a>
	<a href="javascript: executeLink('../comissaoReuniao/list.action?comissao.id=${comissao.id}');" class="ativaReuniao">Reuniões</a>
	<a href="javascript: executeLink('../comissaoPlanoTrabalho/list.action?comissao.id=${comissao.id}');" class="ativaPlano">Plano de Trabalho</a>
	<a href="javascript: executeLink('../comissao/prepareDocumentos.action?comissao.id=${comissao.id}');" class="ativaDocumentos">Atas e Comunicados</a>

	<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>

	<br>
	<br>

	<@ww.actionerror />
	<@ww.actionmessage />