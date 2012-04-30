function checkSystem(checkBox)
{
	getFilhos(checkBox);
	getPais(checkBox);
}

function getFilhos(checkBox)
{
	var idCheck = checkBox.id;
	var checkFilho = true;

	var contadorWhile = 1;
	var checkAtual;
	while(checkFilho)
	{
		if(document.getElementById(idCheck + "_" + contadorWhile))
		{
			checkAtual = document.getElementById(idCheck + "_" + contadorWhile);

			if(checkBox.checked)
			{
				if(!checkAtual.checked)
				{
					checkAtual.checked = true;
					getFilhos(checkAtual);
				}
			}
			else
			{
				if(checkAtual.checked)
				{
					checkAtual.checked = false;
					getFilhos(checkAtual);
				}
			}
			contadorWhile++;
		}
		else
		{
			checkFilho = false;
		}
	}
}

function getPais(checkBox)
{
	var checkId = checkBox.id;
	var lengthId = checkId.length;

	var checkIrmao;
	var irmaosDesmarcados = true;

	for(var x = 0; x < lengthId; x++)
	{
		if(checkId.charAt(lengthId - x) == "_")
		{
			var checkIdPai = checkId.substring(0,lengthId - x);
			break;
		}
	}
	if(document.getElementById(checkIdPai))
	{
		checkPai = document.getElementById(checkIdPai);

		if(checkBox.checked == true && checkPai.checked == false)
		{
			checkPai.checked = true;
			getPais(checkPai);
		}
		else if(checkBox.checked == false)
		{
			var contador = 1;
			while(irmaosDesmarcados && document.getElementById(checkIdPai + "_" + contador))
			{
				checkIrmao = document.getElementById(checkIdPai + "_" + contador);

				if(checkIrmao.checked == true)
				{
					irmaosDesmarcados = false;
				}
				contador++;
			}
			
			if($(checkBox).parent().parent().find('li').size() > 1 && irmaosDesmarcados && checkPai.checked == true)
			{
				checkPai.checked = false;
				getPais(checkPai);
			}

		}
	}
}