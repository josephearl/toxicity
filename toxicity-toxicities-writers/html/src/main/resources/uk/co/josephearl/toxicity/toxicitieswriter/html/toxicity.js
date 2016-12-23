toxicity = {}

toxicity.uniqueColors = function(total) {
  // Distribute the colors evenly on the hue range
  var i = 360 / (total - 1);
  var r = [];
  for (var x = 0; x < total; x++) {
    r.push(toxicity.rgbToHex(toxicity.hsvToRgb(i * x, 50, 100)));
    // You can also alternate the saturation and value for even more contrast between the colors
  }
  return r;
}

toxicity.shuffle = function(array) {
  var i = 0, j = 0, temp = null;
  // Deterministic shuffle
  var random = 0.82343424;

  for (i = array.length - 1; i > 0; i -= 1) {
    j = Math.floor(random * (i + 1));
    temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  return array;
}

toxicity.hsvToRgb = function(h, s, v) {
  var r, g, b;
  var i;
  var f, p, q, t;

  // Make sure our arguments stay in-range
  h = Math.max(0, Math.min(360, h));
  s = Math.max(0, Math.min(100, s));
  v = Math.max(0, Math.min(100, v));

  // We accept saturation and value arguments from 0 to 100 because that's
  // how Photoshop represents those values. Internally, however, the
  // saturation and value are calculated from a range of 0 to 1. We make
  // That conversion here.
  s /= 100;
  v /= 100;

  if (s == 0) {
    // Achromatic (grey)
    r = g = b = v;
    return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
  }

  // Sector 0 to 5
  h = h/60;
  i = Math.floor(h);
  // Factorial part of h
  f = h - i;
  p = v * (1 - s);
  q = v * (1 - s * f);
  t = v * (1 - s * (1 - f));

  switch (i) {
  case 0:
    r = v;
    g = t;
    b = p;
    break;

  case 1:
    r = q;
    g = v;
    b = p;
    break;

  case 2:
    r = p;
    g = v;
    b = t;
    break;

  case 3:
    r = p;
    g = q;
    b = v;
    break;

  case 4:
    r = t;
    g = p;
    b = v;
    break;

  // case 5:
  default:
    r = v;
    g = p;
    b = q;
  }

  return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
}

toxicity.componentToHex = function(c) {
  var hex = c.toString(16);
  return hex.length == 1 ? "0" + hex : hex;
}

toxicity.rgbToHex = function(c) {
  var r = c[0], g = c[1], b = c[2];
  return "#" + toxicity.componentToHex(r) + toxicity.componentToHex(g) + toxicity.componentToHex(b);
}

toxicity.renderChart = function(el, thresholds, toxicities) {
  var CHEIGHT = 425;
  var BWIDTH = 6;
  var BGAP = 2;
  var LEFTSPACE = 25;

  toxicities.sort(function(da, db) { return db.total - da.total });

  var colors = toxicity.shuffle(toxicity.uniqueColors(thresholds.length));

  var checks = d3.layout.stack()(thresholds.map(function(metricType) {
    return toxicities.map(function(d, i) {
      return { x: i, y: d.toxicities[metricType] || 0, data: d };
    });
  }));

  d3.selectAll("svg").remove();

  var chart = d3.select(el).append("svg")
    .attr("class", "chart")
    .attr("width", LEFTSPACE + (BWIDTH + BGAP) * toxicities.length + 10)
  // To accommodate bottom label
    .attr("height", CHEIGHT + 5);

  var xscale = d3.scale.linear()
    .domain([0, toxicities.length])
    .rangeRound([LEFTSPACE, (BWIDTH + BGAP) * toxicities.length + LEFTSPACE]);

  //var xaxis = d3.svg.axis()
  //  .scale(xscale)
  //  .orient("bottom")
  //  .tickValues(toxicities.map(function(d, i) { return i; }))
  //  .tickFormat(function(i) { return toxicities[i].name; });

  var yscale = d3.scale.linear()
  // Dynamic max .domain([0, d3.max(toxicities, function(d) { return d.total })])
    .domain([0, 41])
    .rangeRound([CHEIGHT, 1]);

  var yaxis = d3.svg.axis()
    .scale(yscale)
    .orient("left")
    .ticks(10);

  var fscale = d3.scale.ordinal().range(colors);

  chart.selectAll("line")
    .data(yscale.ticks(10))
    .enter().append("line")
    .attr("x1", function(td) { return xscale(0) })
    .attr("x2", function(td) { return xscale(toxicities.length) })
    .attr("y1", yscale)
    .attr("y2", yscale)
    .style("stroke", "#ccc");

  var groups = chart.selectAll("g.checks")
    .data(checks)
    .enter().append("g")
    .attr("class", "check")
    .style("fill", function(d, i) { return fscale(i); })
    .style("stroke", function(d, i) { return d3.rgb(fscale(i)).darker(); });

  groups.selectAll("rect")
    .data(Object)
    .enter().append("rect")
    .attr("x", function(d) { return xscale(d.x); })
    .attr("y", function(d) { return yscale(d.y + d.y0); })
    .attr("height", function(d) { return CHEIGHT - yscale(d.y); })
    .attr("width", function(d) { return BWIDTH; })
    .call(tooltip(el, thresholds, colors, function(d) { return d.data; }));

  chart.append("g")
    .attr("class", "axis")
    .attr("transform", "translate(" + LEFTSPACE + ", 0)")
    .call(yaxis);

  chart.append("g")
    .attr("class", "axis")
    .attr("transform", "translate(" + 0 + ", " + CHEIGHT + ")");
  //  .call(xaxis)
  //  .selectAll("text")
  //  .style("text-anchor", "end")
  //  .style("font-size", "9px")
  //  .attr("dx", "-.6em")
  //  .attr("dy", ".10em")
  //  .attr("transform", "rotate(-70)");
}

var tooltip = function(el, thresholds, colors, a) {
  var accessor = arguments.length ? a : undefined;

  function tooltip(selection) {
    selection
      .on("mouseover", function(d) {
        if (accessor) {
          d = accessor(d);
        }
         var div = d3.select(el).selectAll("div.tooltip");
        if (div.empty()) {
           div = d3.select(el).append("div").attr("class", "tooltip").style("opacity", 0);
        }
        div.html("");
        div.append("h2").text(d.name);
        div.append("p").attr("class", "filename").text(d.path.split("/").slice(0, -1).join("/"));
        $(thresholds.slice(0).reverse()).each(function(i, metricType) {
          if (d.toxicities[metricType] > 0) {
            var color = colors[colors.length - i - 1];
            var p = div.append("p");
            p.append("span")
              .attr("class", "tooltip-swatch")
              .style("color", d3.rgb(color).darker())
              .style("background-color", color);
            p.append("span")
              .text(metricType + ": " + Math.round(d.toxicities[metricType] * 10) / 10);
            }
        });
        var p = div.append("p");
          p.append("span")
            .attr("class", "tooltip-swatch");
          p.append("span")
            .attr("class", "total")
            .text("Total: " + Math.round(d.total * 10) / 10);
        var ttx = d3.event.pageX;
        var tty = d3.event.pageY - $("div.tooltip").height() - 15;
        var hclip = (ttx + $("div.tooltip").width() + 20) - ($(window).width() + $(window).scrollLeft())
        if (hclip > 0) {
          ttx -= hclip
        }
        div.style("left", Math.max(ttx + 4, $(window).scrollLeft() + 5) + "px")
           .style("top", Math.max(tty, $(window).scrollTop() + 5) + "px");
        div.transition().duration(100).style("opacity", 0.95);
      })
      .on("mouseout", function(d) {
        div = d3.select("body").select("div.tooltip")
        div.transition().duration(250).style("opacity", 0);
      });
  }

  return tooltip;
};
