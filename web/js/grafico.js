function montaLine(data, idGrafico, precisao, options) {
    var config = {
	    series: {
           lines: { show: true },
           points: { show: true }
        },
        grid: { hoverable: true },
        xaxis: {
        	tickSize: [1, "month"],
        	mode: 'time',
        	timeformat: '%b/%y ',
        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
        },
        yaxis: {
        	tickFormatter: function (v) { return formataNumero(v, precisao); }
        }
    };			

    if (options){$.extend(config, options);}
    
    var plot = $.plot($(idGrafico), data, config);

	var previousPoint = null;				
	$(idGrafico).bind("plothover", function (event, pos, item) {
        if (item) 
        {
        	if (previousPoint != item.dataIndex) 
        	{
        		previousPoint = item.dataIndex;
                $("#tooltip").remove();
                var y = formataNumero(item.datapoint[1], precisao);		                    
                showTooltip(item.pageX, item.pageY, y);
            }
        }
		else 
		{
        	$("#tooltip").remove();
        	previousPoint = null;            
        }
	});
}

function montaBar(data, idGrafico) {
    var config = {
		series: {
			bars: {
				show: true, 
         		align: 'center'
			}
    	},
        grid: { hoverable: true },
        xaxis: {
        	tickSize: [1, "month"],
        	mode: 'time',
        	timeformat: '%b/%y ',
        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
        }
    };			

    var plot = $.plot($(idGrafico), data, config);
}

function montaBarDuploCategoria(data, idGrafico, nomesParaRelacionar, distanciaTexto) 
{
	var config = {
		series: {
			bars: {
				show: true, 
				barWidth: 0.6,
         		align: 'center'
			}
    	},
        grid: { hoverable: true },
        xaxis: {
        	minTickSize: 1,
        	tickLength: distanciaTexto
        }
    };			

    var plot = $.plot($(idGrafico), data, config);

    if(typeof idGrafico == 'object')
    	idGrafico = '#' + idGrafico.id;
    
    $(idGrafico + ' .xAxis .tickLabel').each(function(){
    	var i =  parseInt($(this).text().replace('.0',''));
    	$(this).text(nomesParaRelacionar[i]);
    });

    $(idGrafico +' .xAxis .tickLabel').css({
        '-moz-transform':'rotate(315deg)',
        '-webkit-transform':'rotate(315deg)',
        '-o-transform':'rotate(315deg)',
        '-ms-transform':'rotate(315deg)',
        'transform':'rotate(315deg)'
   });
}

function montaPie(data, clazz, options, showDatasCombine) {
	var config = {
        radius: 0.8,
        radiusLabel: 1, 
        percentMin: 0.05, 
        combinePercentMin: 0, 
        pieLeft: -120, 
        noColumns: 1,
        container: undefined,
    	hoverable: false,
        clickable: false,
        legendLabelFormatter: function(label, series) {
			return '<span class="legend">' + label + ' &#x2013; '+ (isNaN(series.percent)?0:series.percent.toFixed(2)) + '% ('+ series.datapoints.points[1] + ')</span>';
		}
    };
	
    if (options){$.extend(config, options);}
    
	var somaData = 0;
	for (var i=0 ; i< data.length; i++)
	{
	    somaData+=data[i].data;
	}
    
	$.plot($(clazz), data, {
		series : {
			pie : {
				show : true,
				offset : {
					top : 0,
					left : config.pieLeft
				},
				stroke : {
					color : '#FFF',
					width : 1
				},
				tilt : ((somaData==0)?1:0.8),
				radius : config.radius,
				combine : {
					color : '#999',
					threshold :config.combinePercentMin,
					label : 'Outros'
				},
				label : {
					show : true,
					radius : config.radiusLabel,
					threshold : config.percentMin,
					formatter : function(label, series) {
						return '<div class="label">' + series.percent.toFixed(2)+ '%</div>';
					}
				}
			}
		},
		grid : {
			hoverable: config.hoverable,
            clickable: config.clickable
		},
		colors : [ "#edc240", "#afeef8", "#cb4b4b", "#4da74d", "#9440ed" ],//"#D7ECFC","#DECD99","#226FA5","#8080FF"
		legend : {
			margin : 2,
			noColumns: config.noColumns,
			labelBoxBorderColor : '#FFF',
			container: config.container,
			labelFormatter : config.legendLabelFormatter
		}
	});
	
	if ( showDatasCombine && $(clazz+"Legenda").find(".legend").last().size() > 0 && $(clazz+"Legenda").find(".legend").last().html().indexOf("Outros") >= 0 ) {
		var sum = 0;
		$(data).each(function(){
			sum+= this.data;
		});
		
		$(data).each(function(){
			var percent = 100*this.data/sum;
			if (percent <= config.combinePercentMin*100)
				$(clazz+"Legenda").find(".legendLabel").last().append("<span class=\"legend\">"+ this.label + " - " + percent.toFixed(2) +"% ("+this.data+")</span>");
		});
	}
	
}

function showTooltip(x, y, contents) 
{
    $('<div id="tooltip">' + contents + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y - 30,
        left: x + 5,
        border: '1px solid #fdd',
        padding: '2px',
        'background-color': '#fee',
        opacity: 0.80,
        'z-index': 20000
    }).appendTo("body").fadeIn(0);
}

function formataNumero(numero, precisao)
{
	if (!precisao) precisao = 2;
	return numero.toFixed(precisao).replace(/,/g,'#').replace(/\./g,',').replace(/#/g,'.');
}