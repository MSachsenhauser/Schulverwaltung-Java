var $k = jQuery.noConflict();
$k(document).ready(function(){
	$k("#tabControl").tabs({ heightStyle: "fill", cookie: { expires: 7 } });
});