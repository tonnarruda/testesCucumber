function montaPie(data, clazz, options) {
	var config = {
        radius: 0.8,
        radiusLabel: 1, 
        percentMin: 0.05, 
        pieLeft: -120, 
        noColumns: 1,
        container: undefined,
    	hoverable: false,
        clickable: false,
        legendLabelFormatter: function(label, series) {
			return '<span class="legend">' + label + ' &#x2013; '+ series.percent.toFixed(2) + '% ('+ series.datapoints.points[1] + ')</span>';
		}
    };
	
    if (options){$.extend(config, options);}
    
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
				tilt : 0.8,
				radius : config.radius,
				combine : {
					color : '#999',
					threshold : 0,
					label : 'Outros'
				},
				label : {
					show : true,
					radius : config.radiusLabel,
					threshold : config.percentMin,
					formatter : function(label, series) {
						return '<div class="label">' + series.percent.toFixed(2) + '%</div>';
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