var xmlhttp;

function search() {
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    //GETTING INFO FROM DATABASE SERVLET
    var stockValue = document.getElementById('searchForm:stockInput').value;
    var url = "Stock?objective=search&stock="+encodeURIComponent(stockValue);
    //USING GET CALL because of cache
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = updateback;
    xmlhttp.send();
}
function updateback() {
    if (xmlhttp.readyState == 4) {
        if (xmlhttp.status == 200) {
            document.getElementById("results").innerHTML = xmlhttp.responseText; 
        }
    }
}

function getHistory() {
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    //GETTING INFO FROM DATABASE SERVLET
    var url = "Stock?objective=history&results=7";
    //USING GET CALL because of cache
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = historyBack;
    xmlhttp.send();
}
function getHistory2() {
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    //GETTING INFO FROM DATABASE SERVLET
    var url = "Stock?objective=history&results=27";
    //USING GET CALL because of cache
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = historyBack;
    xmlhttp.send();
}
function getHistory2() {
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    //GETTING INFO FROM DATABASE SERVLET
    var url = "Stock?objective=history&results=viewAll";
    //USING GET CALL because of cache
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = historyBack;
    xmlhttp.send();
}
function historyBack() {
    if (xmlhttp.readyState == 4) {
        if (xmlhttp.status == 200) {
            document.getElementById("history").innerHTML = xmlhttp.responseText; 
        }
    }
}