/**
 * 
 */
	//make the submit button have an onclick="validate()"
 function loginUser(){
			let url="http://localhost:8080/final/LoginServlet?user="
			+document.login.user.value+"&pass="+document.login.pass.value;
			
			fetch(url, {method:"POST"})
			.then(response=> response.json())
			.then((result) => {
				
				if (Number.isNaN(parseInt(result))){
					
					document.getElementById("wrong").innerHTML=result;
				} else {
					//result is the user id, store user id in local storage
					//change page to the logged in view
					
					localStorage.setItem("userID", result);
					window.location.assign("NewFile1.html");
				}
				
			
			
			}).catch(function(error){
			
					console.log("request failed", error)
			});
		}
 
 