<%--
 * layout.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="favicon.ico" />


<link rel="shortcut icon" href="favicon.ico" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- Bootstrap core CSS -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<!--external css-->
<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="assets/css/zabuto_calendar.css">
<link rel="stylesheet" type="text/css"
	href="assets/js/gritter/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css" href="assets/lineicons/style.css">

<!-- Custom styles for this template -->
<link href="assets/css/style.css" rel="stylesheet">
<link href="assets/css/style-responsive.css" rel="stylesheet">

<script src="assets/js/chart-master/Chart.js"></script>

<!-- Full Calendar -->

<link rel='stylesheet' href='styles/fullcalendar.css' />

<style type="text/css">
#calendar {
	width: 80%;
	margin: 0 auto;
}
</style>


</head>

<body>

	<section id="container"> <!-- ******************************************************************************************************************
            TOP BAR CONTENT & NOTIFICATIONS
      **************************************************************************************************************-->
	<!--header start-->
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<!--header end--> <!-- *****************************************************************************************************************
            MAIN SIDEBAR MENU
      **************************************************************************************************************-->

	<!--sidebar start-->
	<div>
		<tiles:insertAttribute name="sidebar" />
	</div>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper site-min-height">



	<div class="page-header">
		<h2>
			<tiles:insertAttribute name="title" />
		</h2>
	</div>

	<tiles:insertAttribute name="body" /> <jstl:if
		test="${message != null}">
		<br />
		<span class="message"><spring:message code="${message}" /></span>
	</jstl:if> </section> </section> <!--main content end-->

	<div>
		<tiles:insertAttribute name="footer" />
	</div>
	</section>



	<!-- js placed at the end of the document so the pages load faster -->


	<script src='scripts/jquery.min.js'></script>

	<script src="assets/js/jquery.js"></script>
	<script src="assets/js/jquery-1.8.3.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script class="include" type="text/javascript"
		src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
	<script src="assets/js/jquery.scrollTo.min.js"></script>
	<!-- <script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script> -->
	<script src="assets/js/jquery.sparkline.js"></script>
	<!--common script for all pages-->
	<script src="assets/js/common-scripts.js"></script>
	<script type="text/javascript"
		src="assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript" src="assets/js/gritter-conf.js"></script>

	<!--script for this page-->
	<script src="assets/js/sparkline-chart.js"></script>
	<script src="assets/js/zabuto_calendar.js"></script>

	<!-- Full Calendar -->
	<script src='scripts/moment.min.js'></script>
	<script src='scripts/fullcalendar.js'></script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					


					//poner en rojo los campos incorrectos
					if ($('span.error').length > 0) {

						if ($('span.error').parent().length > 0) {
							$('span.error').parent()
									.attr('style', 'color:red;');

						}
						if ($('span.error').siblings('input').length > 0) {
							$('span.error').siblings('input').attr('style',
									'border:1px solid red;');

						}
						if ($('span.error').siblings('textarea').length > 0) {
							$('span.error').siblings('textarea').attr('style',
									'border:1px solid red;');

						}
						if ($('span.error').siblings('select').length > 0) {
							$('span.error').siblings('select').attr('style',
									'border:1px solid red;');

						}

					}

					if ($('div.error').length > 0) {
						$('div.error').attr('style', 'color:red;');
						//alert('errorr');
					}

					//mover de sitio el error que debe salir debajo del formulario y ponerlo rojo.
					if ($('span.message').length > 0) {

						$('span.message').replaceWith(
								"<div class=\"message\" style=\"color:red;\">"
										+ $('span.message').text() + "</div>");

						jQuery($('div.form-group:last')).append(
								$('div.message'));

					}

					function askSubmission(msg, form) {
						if (confirm(msg))
							form.submit();
					}

				});
	</script>
	<script type="text/javascript">
		function relativeRedir(loc) {
			var b = document.getElementsByTagName('base');
			if (b && b[0] && b[0].href) {
				if (b[0].href.substr(b[0].href.length - 1) == '/'
						&& loc.charAt(0) == '/')
					loc = loc.substr(1);
				loc = b[0].href + loc;
			}
			window.location.replace(loc);
		}
	</script>
	<script type="application/javascript">
		

            $(document).ready(function () {
                $("#date-popover").popover({html: true, trigger: "manual"});
                $("#date-popover").hide();
                $("#date-popover").click(function (e) {
                    $(this).hide();
                });

                $("#my-calendar").zabuto_calendar({
                    action: function () {
                        return myDateFunction(this.id, false);
                    },
                    action_nav: function () {
                        return myNavFunction(this.id);
                    },
                    ajax: {
                        url: "show_data.php?action=1",
                        modal: true
                    },
                    legend: [
                        {type: "text", label: "Special event", badge: "00"},
                        {type: "block", label: "Regular event", }
                    ]
                });
            });



            function myNavFunction(id) {
                $("#date-popover").hide();
                var nav = $("#" + id).data("navigation");
                var to = $("#" + id).data("to");
                console.log('nav ' + nav + ' to: ' + to.month + '/' + to.year);
            }

	
	</script>


	<script type="text/javascript">
		$(document).ready(function() {

			if ($("#eventos").length > 0) {
				var string = $("#eventos").val();
				var eventos = JSON.parse(string);

			}
			var calendar = $('#calendar').fullCalendar({

				header : {
					left : 'prev,next today',
					center : 'title',
					right : 'month,agendaWeek,agendaDay'
				},

				defaultView : 'agendaWeek',

				selectable : false,
				selectHelper : false,
				editable : false,

				eventColor : '#19D119',
				slotDuration : '00:20:00',
				events : eventos

			});

		});

		/* 		function askSubmission(msg, form) {
		 if (confirm(msg))
		 form.submit();
		 } */
	</script>

</body>
</html>
