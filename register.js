// Adjust IDs and parameters accordingly
async function registerUser(){	
    var username = document.getElementById("username-register").value;
    var pass = document.getElementById("pass-register").value;
    var confirmPass = document.getElementById("confirm-pass-register").value;
    if (username == ""){
	alert("Username field must not be empty.");
	return;
    }

    if (pass == "" || confirmPass == ""){
		alert("Password or Confirm Password field must not be empty.");
		return;
    }
	
    if (confirmPass != pass){
	alert("Passwords do not match. Try again.");
	return;
    }
	
    // Convert JS info into JSON
    const userData = {
	user: username,
	pass: pass,
    };  
	
	
    const jsonData = JSON.stringify(userData);
    console.log(jsonData);
	
    // Took lines 38-70 from Kathy's Assignment 4
    try{
	const response = await fetch('/knhang_CSCI201_Assignment4/RegisterUserServlet', { // Have to change this line
	method: 'POST',
	headers: {
	'Content-Type': 'application/json'
	},
	body: jsonData
     });
        
        const data = await response.json();
        console.log(data);
        if (data == "Username is taken."){
		alert("Username is already taken.");
		return;
	}
	else if(data == "User information is missing."){
		alert("User information is missing.");
		return;
	}
	else{
        	sessionStorage.setItem("userID", data);
        	window.location.href = "map.html";
	}
    }
    catch (error){
		console.error('Error:', error);
	}
}
