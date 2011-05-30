function montaLine(data, idGrafico) {
    var options = {
	    series: {
           lines: { show: true },
           points: { show: true }
        },
        grid: { hoverable: true },
        xaxis: { 
        	mode: "time",
        	ticks: data.map(function (item){return item[0]}),
        	timeformat: '%b/%y ',
        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
        },
        yaxis: { 
        	 tickFormatter: function (v) {return v;}
        }
    };			
        
    var plot = $.plot($(idGrafico), [data], options);

	var previousPoint = null;				
	$(idGrafico).bind("plothover", function (event, pos, item) {
        if (item) 
        {
        	if (previousPoint != item.dataIndex) 
        	{
        		previousPoint = item.dataIndex;
                $("#tooltip").remove();
                var y = item.datapoint[1].toFixed(4);		                    
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

function montaPie(data, clazz, options) {
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
		colors : [ "#edc240", "#afd8f8", "#cb4b4b", "#4da74d", "#9440ed" ],//"#D7ECFC","#DECD99","#226FA5","#8080FF"
		legend : {
			margin : 2,
			noColumns: config.noColumns,
			labelBoxBorderColor : '#FFF',
			container: config.container,
			labelFormatter : config.legendLabelFormatter
		}
	});

	//$(class).bind("plothover", pieHover);
}