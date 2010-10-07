function alteraTipo(valor, indice)
{
	if(valor == indice)
	{
		document.getElementById('divValor').style.display = "none";
		document.getElementById('divQuantidade').style.display = "";
	}
	else
	{
		document.getElementById('divValor').style.display = "";
		document.getElementById('divQuantidade').style.display = "none";
	}
}

function alteraTipoSalario(tipo)
{
	if(!tipo)
	{
		escondeTudo();
		return;
	}

	tipo = Number(tipo)
	escondeTudo();
	switch (tipo)
	{
		case 1:
			exibeApenasaDiv("valorCalculadoDiv");
			break;
		case 2:
			exibeApenasaDiv("indiceDiv");
			exibeApenasaDiv("valorCalculadoDiv");
			break;
		case 3:
			exibeApenasaDiv("valorDiv");
			break;
	}
}

function exibeApenasaDiv(idDaDiv)
{
	document.getElementById(idDaDiv).style.display="";
}

function escondeTudo()
{
	document.getElementById("valorDiv").style.display = "none";
	document.getElementById("indiceDiv").style.display = "none";
	document.getElementById("valorCalculadoDiv").style.display = "none";
}