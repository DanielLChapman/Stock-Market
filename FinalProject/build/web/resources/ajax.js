             var xmlhttp;
            //STANDARD START TO AJAX CALL
            //STOCK STUFF
            {
            function getStocks() {
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                //GETTING INFO FROM DATABASE SERVLET
                var url = "Stock?objective=xml";
                //USING GET CALL because of cache
                xmlhttp.open("GET", url, true);
                xmlhttp.onreadystatechange = callback;
                xmlhttp.send();
            }
            
            function updateStocks() {
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                //GETTING INFO FROM DATABASE SERVLET
                var url = "Stock?objective=update";
                //USING GET CALL because of cache
                xmlhttp.open("GET", url, true);
                xmlhttp.onreadystatechange = updateback;
                xmlhttp.send();
            }
            function updateback() {
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                        getStocks();
                    }
                }
            }
            function callback() {
                //When server is a go start the process of getting information and updating div
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                         document.getElementById("results").innerHTML = xmlhttp.responseText;
                    }
                }
            }
            $(function() {
                getStocks();
                setInterval(getStocks, 30000);
                setInterval(updateStocks, 300000);
            
            });
            }
            {
            function validateBuy() {
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                //GETTING INFO FROM DATABASE SERVLET
                var url = "Stock?objective=buy&id="+encodeURIComponent(id.value)+"&stock="+encodeURIComponent(stock.value)+"&amount="+encodeURIComponent(amount.value);
                //USING GET CALL because of cache

                xmlhttp.open("GET", url, true);
                xmlhttp.onreadystatechange = confirm;
                xmlhttp.send();
            }
            function confirm() {
                //When server is a go start the process of getting information and updating div
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                         document.getElementById("buy").innerHTML = xmlhttp.responseText;
                    }
                }
            }
            }
            {
            function transactions() {
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                //GETTING INFO FROM DATABASE SERVLET
                var url = "Stock?objective=history&id="+encodeURIComponent(id.value);
                //USING GET CALL because of cache

                xmlhttp.open("GET", url, true);
                xmlhttp.onreadystatechange = confirm;
                xmlhttp.send();
            }
            function confirm() {
                //When server is a go start the process of getting information and updating div
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                         document.getElementById("buy").innerHTML = xmlhttp.responseText;
                    }
                }
            }
            }