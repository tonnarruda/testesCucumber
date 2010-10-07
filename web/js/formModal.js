function gradient(id, level)
{
	var box = document.getElementById(id);
	box.style.opacity = level;
	box.style.MozOpacity = level;
	box.style.KhtmlOpacity = level;
	box.style.filter = "alpha(opacity=" + level * 100 + ")";
	box.style.display="block";
	return;
}
function fadein(id)
{
	var level = 0;
	while(level <= 1)
	{
		setTimeout( "gradient('" + id + "'," + level + ")", (level * 700) + 10);
		level += 0.02;
	}
}

// Open the lightbox
function openbox(formtitle, focus)
{
	var box = document.getElementById('box');
	var btitle = document.getElementById('boxtitle');
	btitle.innerHTML = formtitle;

	gradient("box", 0);
	fadein("box");

	if(focus != "")
		document.getElementById(focus).focus();
}


function openboxFrequencia(formtitle, focus)
{
	var box = document.getElementById('boxFrequencia');
	var btitle = document.getElementById('boxtitleFrequencia');
	btitle.innerHTML = formtitle;

	gradient("boxFrequencia", 0);
	fadein("boxFrequencia");

	if(focus != "")
		document.getElementById(focus).focus();
		document.getElementById('ativosDataAtual').checked = "true";
}

// Close the lightbox
function closebox()
{
	document.getElementById('box').style.display='none';
}

function closeboxFrequencia()
{
	document.getElementById('boxFrequencia').style.display='none';
}
