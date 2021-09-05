let day = document.getElementById('day');
let formday = document.getElementById('formday');
let month = document.getElementById('month');
let formmonth = document.getElementById('formmonth');
let week = document.getElementById('week');
let formweek = document.getElementById('formweek');
let formmoney = document.getElementById('formmoney');
let money = document.getElementById('money');
let close = document.getElementsByClassName('close')[0];

day.onclick = function () {
    formday.style.display = "block";
    formweek.style.display = "none";
    formmonth.style.display = "none";
    formmoney.style.display = "none";

}

month.onclick = function () {
    formmonth.style.display = "block";
    formday.style.display = "none";
    formweek.style.display = "none";
    formmoney.style.display = "none";
}

week.onclick = function () {
    formweek.style.display = "block";
    formday.style.display = "none";
    formmonth.style.display = "none";
    formmoney.style.display = "none";
}
money.onclick = function () {
    formmoney.style.display = "block";
    formday.style.display = "none";
    formweek.style.display = "none";
    formmonth.style.display = "none";

}
let openeditmonth = document.getElementById('openeditmonth');
let openeditday = document.getElementById('openeditday');
let editmonth = document.getElementById('editmonth');
let editday = document.getElementById('editday');

openeditday.onclick = function () {
    editday.style.display = "block";
    editmonth.style.display = "none";
}
openeditmonth.onclick = function () {
    editday.style.display = "none";
    editmonth.style.display = "block";
}

close.onclick = function () {
    formday.style.display = "none";
    formmonth.style.display = "none";
    formweek.style.display = "none";
    editday.style.display = "none";
    editmonth.style.display = "none";
    formmoney.style.display = "none";
    editday.style.display = "none";
    editmonth.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == formday) {
        formday.style.display = "none";
    }
    if (event.target == formmonth) {
        formmonth.style.display = "none";
    }
    if (event.target == formweek) {
        formweek.style.display = "none"
    }
    if(event.target==formmoney){
        formmoney.style.display = "none"
    }
}

//Vẽ biểu đồ
function ve() {
    var xValues = [];
    var yValues = [];
    var size = document.getElementById("size").value;
    for (let i = 0; i < size; i++) {
        xValues.push(document.getElementById(i + "k").value);
        yValues.push(document.getElementById(i + "v").value);
    }
    var barColors = [
        "#b91d47",
        "#00aba9",
        "#2b5797",
        "#a49e9e",
        "#458463",
        "#a93b75",
        "#ff020d",
        "#00ff77",
        "#ffd400",
        "#c528f6",
        "#000000",
        "#22ff00",
        "#ffffff"
    ];


    new Chart("myChart", {
        type: "pie",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: barColors,
                data: yValues,
            }]
        },
        options: {
            title: {
                display: true,
                text: "Biểu đồ thu chi cá nhân"
            }
        }
    });
}