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
        formweek.style.display = "none";
    }
    if(event.target==formmoney){
        formmoney.style.display = "none";
    }
    if(event.target==editday){
        editday.style.display="none";
    }
    if(event.target==editmonth){
        editmonth.style.display="none";
    }

}

//V??? bi???u ?????
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
                text: "Bi???u ????? thu chi theo danh m???c"
            }
        }
    });
}

function vebieudomien(){
    console.log("co goi ham hay ko?")
    var thu = [];
    var chi = [];
    for (let i = 0; i < 12; i++) {
        thu.push(document.getElementById(i + "rev").value);
        chi.push(document.getElementById(i + "exp").value);
    }
    new Chart(document.getElementById("bieudomien"), {
        type: 'bar',
        data: {
            labels: ["Th??ng 1", "Th??ng 2", "Th??ng 3", "Th??ng 4","Th??ng 5","Th??ng 6","Th??ng 7","Th??ng 8","Th??ng 9","Th??ng 10","Th??ng 11","Th??ng 12"],
            datasets: [
                {
                    label: "Thu",
                    backgroundColor: "#3e95cd",
                    data: thu
                }, {
                    label: "Chi",
                    backgroundColor: "#8e5ea2",
                    data: chi
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Bi???u ????? thu chi c?? nh??n theo th??ng'
            }
        }
    });

}