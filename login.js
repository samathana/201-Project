/**
 * 
 */
	
 function loginUser(){
			let url="http://localhost:8080/201_NewProject/LoginServlet?user="
			+document.login.user.value+"&pass="+document.login.pass.value;
			
			fetch(url, {method:"POST"})
			.then(response=> response.text())
			.then((result) => {
				
				if (Number.isNaN(parseInt(result))){
					
					document.getElementById("wrong").innerHTML=result;
				} else {
					//result is the user id, store user id in local storage
					//change page to the logged in view
					
					sessionStorage.setItem("userID", result);
					window.location.assign("map.html");
				}
				
			
			
			}).catch(function(error){
			
					console.log("request failed", error)
			});
		}
 
 