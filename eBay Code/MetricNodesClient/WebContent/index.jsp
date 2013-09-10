<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Metric Node</title>
</head>
<script src="scripts/jquery-1.10.1.js"></script>

<script type="text/javascript">
$( document ).ready(function() {
	$("#target").click(function() {
		  var requestID = $("input#requestId").val();
		  var core1= $("input#core1").val();
		  var core2= $("input#core2").val();
		  var freeM= $("input#free").val();
		  var usedM= $("input#used").val();
		  //var route2= $("input#route2").val();
	      var dataString = "requestId=" + requestID+"&core1=" + core1+"&core2="+core2+"&free="+freeM+"&used="+usedM+"&task=store";
		  
		  $.ajax({ 
				url: "NodesServlet?", 
				data: dataString, 
				success:function( data ) { 
					if(data.err != ''){
						$( "#ErrorMsg" ).html( "<strong><font color=\"red\">" + data.err  +"</font></strong> " ); 
					}else if(data.requestId!= ''){
						$( "#ErrorMsg" ).html( "<font color=\"red\">Record save is in process, your Request id is <strong>" + data.requestId  +"</strong></font> " );
					}
					
				}
			});
	});
	
	$("#read").click(function() {
		  var requestID = $("input#requestId").val();
		  //var route2= $("input#route2").val();
	      var dataString = "requestId=" + requestID+"&task=read";
		  
		  $.ajax({ 
				url: "NodesServlet?", 
				data: dataString, 
				success:function( data ) { 
					if(data.err != ''){
						$( "#ErrorMsg" ).html( "<strong><font color=\"red\">" + data.err  +"</font></strong> " ); 
					}		
					if(data.requestId!= ''){
						$("input#requestId").val(data.requestId);
						$("input#core1").val(data.core1) ;
						$("input#core2").val(data.core2);
						$("input#free").val(data.free);
						$("input#used").val(data.used);
						
					}
					
				}
			});
	});
});
</script>
<body>
	<table align="center">
		<tr>
			<td colspan="3"><div id="ErrorMsg"></div></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Request id:</td>
			<td><input type="text" id="requestId" name="requestId" value="" maxlength="25"></td>
			<td> </td>
		</tr>
		<tr>
			<td>Enter CPU Core 1:</td>
			<td><input type="text" id="core1" name="core1" value="" maxlength="15"></td>
			<td> </td>
		</tr>
		<tr>
			<td>Enter CPU Core 2:</td>
			<td><input type="text" id="core2" name="core2" value="" maxlength="15"></td>
			<td> </td>
		</tr>
		<tr>
			<td>Enter Free Memory Space:</td>
			<td><input type="text" id="free" name="free" value="" maxlength="15"></td>
			<td> </td>
		</tr>
		<tr>
			<td>Enter Used Memory Space:</td>
			<td><input type="text" id="used" name="used" value ="" maxlength="15"></td>
			<td> </td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<button id="target" style="align:center;">Store Data</button>
			<button id="read" style="align:center;">Read Data</button>
		</td>
		</tr>
	</table>
	<br></br>
	<div style="align:center;width:100%">
		
	</div>

	
</body>
</html>
