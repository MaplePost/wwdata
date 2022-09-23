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
    $("#lastUpdated").html("last updated:" + data.lastUpdated);
    $("#localStatus").html("status: OK");
    $("#currentValue").html(data.currentValue);

    console.log(data.lastValue);
    console.log(data.currentValue);
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