// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
  var chartDataElement = document.getElementById('chart-data');
  var highPriority = chartDataElement.getAttribute('data-high');
  var mediumPriority = chartDataElement.getAttribute('data-medium');
  var lowPriority = chartDataElement.getAttribute('data-low');

  var ctx = document.getElementById("myPieChart");
  var myPieChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: ["High Priority", "Medium Priority", "Low Priority"],
      datasets: [{
        data: [highPriority, mediumPriority, lowPriority],
        backgroundColor: ['#f87721', '#969696', '#212529'],
        hoverBackgroundColor: ['#FF6600', '#505050', '#000000'],
        hoverBorderColor: "rgba(234, 236, 244, 1)",
      }],
    },
    options: {
      maintainAspectRatio: false,
      tooltips: {
        backgroundColor: "#694611",
        bodyFontColor: "#FFFFFF",
        borderColor: '#694611',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        caretPadding: 10,
      },
      legend: {
        display: false
      },
      cutoutPercentage: 80,
    },
  });
