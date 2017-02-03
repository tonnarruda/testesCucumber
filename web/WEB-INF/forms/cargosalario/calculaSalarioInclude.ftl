<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js?version=${versao}"/>"></script>

<script type="text/javascript">

		function calculaSalario()
		{
			dwr.util.useLoadingMessage('Carregando...');
			document.getElementById("salarioCalculado").value = "0,00";

			tipoSalarioProposto = document.getElementById("tipoSalario").value;
			faixaSalarialId = document.getElementById("faixa").value;
			IndiceId        = document.getElementById("indice").value;
			quantidade      = document.getElementById("quantidade").value;
			salario         = document.getElementById("salarioProposto").value;

			dwr.engine.setErrorHandler(error);
			ReajusteDWR.calculaSalario(setSalarioCalculado, tipoSalarioProposto, faixaSalarialId, IndiceId, quantidade, salario);

		}

		function setSalarioCalculado(data)
		{
			if (tipoSalarioProposto == ${tipoSalario.getValor()})
			{
				document.getElementById("salarioProposto").value = data;
			}
			else
			{
				document.getElementById("salarioCalculado").value = data;
			}

		}

		function error(msg)
  		{
    		jAlert(msg);
    		document.getElementById("tipoSalario").value = '';
			document.getElementById("salarioCalculado").value = '0,00';
  		}

</script>
