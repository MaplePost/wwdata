// requjiring jQuery to be loaded
// this function is called when the document object model is ready
$( document ).ready(function() {
    console.log( "ready!" );
    wwdataLoad();
});

/**
 *  Called in the event the data set is loaded
 */
function chartme(data) {
    console.log("load succcess : chartme() ");


    //Our custom chart data
    var chartdata = [];
    var valueMatrix = [];
    var sevenDayWindow = [];
    var fourteenDayWindow = [];
    var previousWeekAverage = 0.0;
    var currentWeekAverage = 0.0;

    // using date math to determine our beginning window of data
    // we want 14 days window beginning at the last updated
    var begindate = data.lastUpdated - 14 * 24 * 60 * 60 * 1000;
    // this is our 7 day window
    var sevenday = data.lastUpdated - 7 * 24 * 60 * 60 * 1000;
    
    // this sets up our 14 day chart and the previous and current week averages
    data.phesdData.forEach((v, i) => {
        if (v.sampleDate >= begindate && v.sampleDate <= data.lastUpdated) {
            chartdata = [...chartdata, { "Date": new Date(v.sampleDate), "value": v.nPPMoV_Ct_mean }];
            valueMatrix = [...valueMatrix, v.nPPMoV_Ct_mean];
        }
        // the last 7 day window
        if (v.sampleDate >= begindate && v.sampleDate <= sevenday) {
            sevenDayWindow = [...sevenDayWindow,v.nPPMoV_Ct_mean];
        }
        // the 14th to seventh day window
        if (v.sampleDate >= sevenday && v.sampleDate <= data.lastUpdated) {
            fourteenDayWindow = [...fourteenDayWindow, v.nPPMoV_Ct_mean];
        }
    }
    );

    // last week average
    for (i=0;i<sevenDayWindow.length;i++) {
        currentWeekAverage += sevenDayWindow[i];
    }
    currentWeekAverage = currentWeekAverage / sevenDayWindow.length;

        // last week average
        for (i=0;i<fourteenDayWindow.length;i++) {
            previousWeekAverage +=  fourteenDayWindow[i];
        }
        previousWeekAverage = previousWeekAverage / fourteenDayWindow.length;
    
    console.log("Maximum in last 14 days " + Math.max(...valueMatrix));
    console.log("Minimum in last 14 days " + Math.min(...valueMatrix));
    console.log("Current 7 day average "+ currentWeekAverage);
    console.log("Previous 7 day average "+ previousWeekAverage);

    // the data provided by java is not the weekly averages we adjust it here
    data.lastValue = previousWeekAverage;
    data.currentValue = currentWeekAverage;

    // load the data set from the data pasered in wwdataLoad function
    $("#lastValue").html(Math.round(previousWeekAverage * 100 )/ 100 );
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

// let's make a chart here using dimple

var svg = dimple.newSvg("#fourteenDayChart", 400, 400);


 var myChart = new dimple.chart(svg, chartdata);
 myChart.setBounds(60, 30, 365, 305);
 var x = myChart.addCategoryAxis("x", "Date");
 x.addOrderRule("Date");
 myChart.addMeasureAxis("y", "value");
 var s = myChart.addSeries(null, dimple.plot.line);
 myChart.draw();
    
}

/**
 *  Called from document load function, sets the ball rolling to update the data
 */
function wwdataLoad () {
    console.log( "wwdataLoad() " );
    // we load the data structure from our json file
    // that is created from the maven java build process
    $.ajax({
        type: 'GET',
        url: "js/wwdata.json",
        dataType: "json", // data type of response
        success: chartme
    });
    
}