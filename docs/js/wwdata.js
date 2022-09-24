// requjiring jQuery to be loaded
// this function is called when the document object model is ready
$(document).ready(function () {
    console.log("ready!");
    wwdataLoad();
});

/**
 *  Called in the event the data set is loaded this prepares and calculates our
 * data points including all time max and min, the 7 day average for this week and the previous
 * and creates a chart for the past 14 days showing the data and bounded by all time max and min
 */
function chartme(data) {
    console.log("load succcess : chartme() ");


    //Our custom chart data
    var chartdata = [];
    // all data samples
    var allTimeData = [];
    // a value matrix for calculations for the4 past 14 days
    var valueMatrix = [];
    //the 7 day readings
    var sevenDayWindow = [];
    // the previous 7 day readings
    var fourteenDayWindow = [];
    // averages
    var previousWeekAverage = 0.0;
    var currentWeekAverage = 0.0;

    // all time max and min
    var allTimeMax;
    var allTimeMin;

    // using date math to determine our beginning window of data
    // we want 14 days window beginning at the last updated
    // the data provided by java for date is in millisecond ephoc format alreadey
    // we we can use them directly in date math without having to translate them
    var begindate = data.lastUpdated - 14 * 24 * 60 * 60 * 1000;
    // this is our 7 day window point
    var sevenday = data.lastUpdated - 7 * 24 * 60 * 60 * 1000;

    // calculate all time max and mins
    // this sets up our 14 day chart and the previous and current week averages
    data.phesdData.forEach((v, i) => {
        allTimeData = [...allTimeData, v.nPPMoV_Ct_mean];
    });

    // all time max and min
    allTimeMax = Math.max(...allTimeData);
    allTimeMin = Math.min(...allTimeData);

    // we use this to format the date the way we want it to show on the 14 day chart axis
    const chartDateOptions = { year: 'numeric', month: 'numeric', day: 'numeric' };

    // this sets up our 14 day chart and the previous and current week data buckets
    
    data.phesdData.forEach((v, i) => {
        if (v.sampleDate >= begindate && v.sampleDate <= data.lastUpdated) {
            var showDate = new Date(v.sampleDate);
            var display = showDate.toLocaleDateString(undefined, chartDateOptions);

            chartdata = [...chartdata, { "Date": display, "Channel": "data", "value": v.nPPMoV_Ct_mean }];
            // we create 2 other series to show max and min bounding lines
            chartdata = [...chartdata, { "Date": display, "Channel": "max", "value": allTimeMax }];
            chartdata = [...chartdata, { "Date": display, "Channel": "min", "value": allTimeMin }];
            valueMatrix = [...valueMatrix, v.nPPMoV_Ct_mean];
        }
        // the last 7 day window
        if (v.sampleDate >= begindate && v.sampleDate < sevenday) {
            fourteenDayWindow = [...fourteenDayWindow, v.nPPMoV_Ct_mean];
        }
        // the 14th to seventh day window
        if (v.sampleDate >= sevenday && v.sampleDate <= data.lastUpdated) {
            sevenDayWindow = [...sevenDayWindow, v.nPPMoV_Ct_mean];
        }
    }
    );

//// below we set all of our data to html

    console.log("All Time Max " + allTimeMax);
    console.log("All Time Min " + allTimeMin);

    // the max min display widget
    $("#maxmin").html(Math.round(allTimeMax * 100) / 100 + " / " + Math.round(allTimeMin * 100) / 100);

    // this week average
    for (i = 0; i < sevenDayWindow.length; i++) {
        currentWeekAverage += sevenDayWindow[i];
    }
    currentWeekAverage = currentWeekAverage / sevenDayWindow.length;

    // last week average
    for (i = 0; i < fourteenDayWindow.length; i++) {
        previousWeekAverage += fourteenDayWindow[i];
    }
    previousWeekAverage = previousWeekAverage / fourteenDayWindow.length;

    console.log("Maximum in last 14 days " + Math.max(...valueMatrix));
    console.log("Minimum in last 14 days " + Math.min(...valueMatrix));
    console.log("Current 7 day average " + currentWeekAverage);
    console.log("Previous 7 day average " + previousWeekAverage);

    // the data provided by java is not the weekly averages we adjust it here
    data.lastValue = previousWeekAverage;
    data.currentValue = currentWeekAverage;

    // load the data set from the data pasered in wwdataLoad function
    $("#lastValue").html(Math.round(previousWeekAverage * 100) / 100);
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    const event = new Date(data.lastUpdated);

    $("#lastUpdated").html(event.toLocaleDateString(undefined, options));
    // let's check how far out the current date and last processed date is
    const d = new Date();
    let t0 = d.getTime();
    var days = (t0 - data.lastUpdated) / (1000 * 24 * 60 * 60);
    var status = "OK";

    if ((t0 - data.lastUpdated) > 1000 * 24 * 60 * 60) {
        status = "Data is " + Math.round(days) + " days old";
    }
    $("#localStatus").html(status);
    $("#currentValue").html(Math.round(currentWeekAverage * 100) / 100);

    console.log(data.lastValue);
    console.log(data.currentValue);

    $("#WwUp").hide();
    $("#WwDown").hide();
    $("#WwSame").hide();
    if (data.lastValue < data.currentValue) {
        $("#WwUp").show();
    } else if (data.lastValue > data.currentValue) {
        $("#WwDown").show();
    } else {
        $("#WwSame").show();
    }

    // let's make a chart here using dimple and our prepared data set in chartdata

    var svg = dimple.newSvg("#fourteenDayChart", 400, 400);

    var myChart = new dimple.chart(svg, chartdata);
    myChart.setBounds(60, 30, 300, 300);
    var x = myChart.addCategoryAxis("x", "Date");
    x.addOrderRule("Date");
    myChart.addMeasureAxis("y", "value");
    myChart.addLegend(60, 10, 300, 20, "left");
    var s = myChart.addSeries("Channel", dimple.plot.line);

    myChart.draw();

}

/**
 *  Called from document load function, sets the ball rolling to update the data
 */
function wwdataLoad() {
    console.log("wwdataLoad() ");
    // we load the data structure from our json file
    // that is created from the maven java build process
    $.ajax({
        type: 'GET',
        url: "js/wwdata.json",
        dataType: "json", // data type of response
        success: chartme
    });

}