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
    console.log( "load succcess : chartme() " );

    // load the data set from the data pasered in wwdataLoad function
    $("#lastValue").html(data.lastValue);
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    const event = new Date(data.lastUpdated);

    $("#lastUpdated").html(event.toLocaleDateString(undefined, options));
    $("#localStatus").html("OK");
    $("#currentValue").html(data.currentValue);

    console.log(data.lastValue);
    console.log(data.currentValue);

    $("#WwUp").hide();
    $("#WwDown").hide();
    $("#WwSame").hide();
    if(data.lastValue < data.currentValue){
        $("#WwUp").show();
    } else if(data.lastValue> data.currentValue){
        $("#WwDown").show();
    } else {
        $("#WwSame").show();
    }

//Using forEach()
var chartdata = [];
data.phesdData.forEach((v,i) => 
   chartdata= [...chartdata, {"Date": new Date(v.sampleDate), "value":v.nPPMoV_Ct_mean}]
)
console.log("chart data" + chartdata);
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