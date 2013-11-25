             var xmlhttp;
            //STANDARD START TO AJAX CALL
            //STOCK STUFF
            
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
                       
                        var rootNode=xmlhttp.responseXML.documentElement;
                        var cds = rootNode.getElementsByTagName("STOCK");
                        var x = new Array;
                        var y = 0;
                        for (i=0;i<cds.length;i++){
                            var artistNodes = cds[i].getElementsByTagName("NAME");
                            var artist = artistNodes[0].firstChild.nodeValue;
                            x[y] = artist;
                            y++;
                            var titleNodes= cds[i].getElementsByTagName("PRICE");
                            var title = titleNodes[0].firstChild.nodeValue;
                            x[y] = title;
                            y++;
                        }
                        
                        document.getElementById("boxA").innerHTML = "<h3>"+x[0]+"</h3><p>"+x[1]+"</p>"; 
                        document.getElementById("boxB").innerHTML = "<h3>"+x[2]+"</h3><p>"+x[3]+"</p>";  
                        document.getElementById("boxC").innerHTML = "<h3>"+x[4]+"</h3><p>"+x[5]+"</p>"; 
                        document.getElementById("boxD").innerHTML = "<h3>"+x[6]+"</h3><p>"+x[7]+"</p>";  
                        document.getElementById("boxE").innerHTML = "<h3>"+x[8]+"</h3><p>"+x[9]+"</p>"; 
                        document.getElementById("boxF").innerHTML = "<h3>"+x[10]+"</h3><p>"+x[11]+"</p>";  
                        document.getElementById("boxG").innerHTML = "<h3>"+x[12]+"</h3><p>"+x[13]+"</p>";  
                        document.getElementById("boxH").innerHTML = "<h3>"+x[14]+"</h3><p>"+x[15]+"</p>"; 
                        
                    }
                }
            }
            function validateBuy() {
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                //GETTING INFO FROM DATABASE SERVLET
                var stockValue = document.getElementById('buyForm:stock').value;
                var stockAmount = document.getElementById('buyForm:amount').value;
                var url = "Stock?objective=buy&stock="+encodeURIComponent(stockValue)+"&amount="+encodeURIComponent(stockAmount);
                //USING GET CALL because of cache
                xmlhttp.open("GET", url, true);
                xmlhttp.onreadystatechange = confirm;
                xmlhttp.send();
            }
            function validateSell() {
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                //GETTING INFO FROM DATABASE SERVLET
                var stockValue = document.getElementById('sellForm:stock').value;
                var stockAmount = document.getElementById('sellForm:amount').value;
                var url = "Stock?objective=sell&stock="+encodeURIComponent(stockValue)+"&amount="+encodeURIComponent(stockAmount);
                //USING GET CALL because of cache
                xmlhttp.open("GET", url, true);
                xmlhttp.onreadystatechange = confirm;
                xmlhttp.send();
            }
            function confirm() {
                //When server is a go start the process of getting information and updating div
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                        document.getElementById("resultsB").innerHTML = xmlhttp.responseText; 
                    }
                }
            }
            
            
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
            
            $(function() {
                getStocks();
                setInterval(getStocks, 30000);
                setInterval(updateStocks, 300000);

            
            });
            
            
            
            