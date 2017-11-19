$( function() {
    $( "#slider-range" ).slider({
        range: true,
        min: 0,
        max: 5000,
        values: [ 0, 1000 ],
        slide: function( event, ui ) {
            $( "#amount" ).val(ui.values[ 0 ] + " - " + ui.values[ 1 ] );
        }
    });
    $( "#amount" ).val( $( "#slider-range" ).slider( "values", 0 ) +
        " - " + $( "#slider-range" ).slider( "values", 1 ) );
} );

$(function () {
    $("#slider-rangeForCost").slider({
        range: true,
        min: 0,
        max: 50000,
        values: [0, 10000],
        slide: function (event, ui) {
            $("#amountCost").val(ui.values[0] + "₽ - " + ui.values[1] + "₽");
        }
    });
    $("#amountCost").val($("#slider-rangeForCost").slider("values", 0) +
        "₽ - " + $("#slider-rangeForCost").slider("values", 1) + "₽");
});



$("body").on('click', '[href*="#"]', function(e){
    var fixed_offset = 100;
    $('html,body').stop().animate({ scrollTop: $(this.hash).offset().top - fixed_offset }, 1000);
    e.preventDefault();
});
