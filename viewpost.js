var likes;
var username;
var userID;
function displayImage() {
	var id = sessionStorage.getItem("EntryID");
    var url = '/201_NewProject/DisplayImageServlet?imageID=' + id;
    
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = 'blob'; 
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Success: Display the image
                var blob = xhr.response;
                var imageUrl = URL.createObjectURL(blob);
                document.getElementById('display_image').src = imageUrl;
        
            } else {
                // Error: Log status and display error message
                console.error("Error:", xhr.status);
                alert("Error fetching image");
            }
        }
    };
    xhr.send();
}

function editCaption(){
	var id = sessionStorage.getItem("EntryID");
    var caption = document.getElementById('caption').value;
    // Construct the URL with image ID and caption
    var url = '/201_NewProject/EditCaptionServlet?imageID=' + id + '&newCaption=' + encodeURIComponent(caption);
     var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                displayCaption();
            } else {
                console.log("Error:", xhr.status);
                var errorResponse = JSON.parse(xhr.responseText);
                alert(errorResponse); 
            }
        }
    };
    xhr.send();
}

function redirectToPage(){
	
      window.location.replace("http://localhost:8080/201_NewProject/map.html");
}

function like() {
	var heartIcon = document.querySelector('.heart-icons i');
            // Replace the far heart icon with the fas heart icon
            heartIcon.outerHTML = '<i class="fas fa-heart" onclick="unlike(this)"></i>';
	var id = sessionStorage.getItem("EntryID");
    var url = '/201_NewProject/LikeServlet?imageID=' + id;
	 var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
            } else {
                console.log("Error:", xhr.status);
                var errorResponse = JSON.parse(xhr.responseText);
                alert(errorResponse); 
            }
        }
    };
    
    likes++;
    document.getElementById('likeCount').textContent = likes;
    xhr.send();
}

function addFriend(){
	var id = sessionStorage.getItem("userID");
	console.log(username);
    var url = '/201_NewProject/AddFriendServelt?imageID=' + id + '&username=' + username;
    
    
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
				var friendID = xhr.responseText;
				toggleCaptionEdit();
				userID = friendID;
                alert("Friend added with ID: " + friendID);
            } else {
                console.log("Error:", xhr.status);
                alert("Already Friends"); 
            }
        }
    };
    xhr.send();
}    

       
function displayCaption() {
    var id = sessionStorage.getItem("EntryID");
    var url = '/201_NewProject/DisplayCaptionServlet?imageID=' + id;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var responseParts = xhr.responseText.split('\n'); // Split response into parts
                if (responseParts.length >= 2) {
                    var caption = responseParts[0];
                    var user = responseParts[1];
                    var likeCount = responseParts[2]
                    var friendID = responseParts[3]
                    likes = parseInt(likeCount);
                    username = user;
                    userID = parseInt(friendID); 
                    
                   
                    document.getElementById('display_caption').textContent = caption;
                    document.getElementById('display_username').textContent = user + "'s Entry";
                    document.getElementById('likeCount').textContent = likeCount;
                    console.log(caption);
                    console.log(user);
                    console.log(likeCount);
                } else {
                    console.log("Invalid response format");
                    alert("Invalid response format");
                }
                
     			toggleCaptionEdit();
            } else {
                console.log("Error:", xhr.status);
                alert("Error: " + xhr.status); 
            }
        }
    };
    xhr.send();
}

document.addEventListener("DOMContentLoaded", function() {
    // This function will be executed after the DOM content is fully loaded
    
    // Hide the addFriend button initially
    document.getElementById("addFriend").style.display = "none";
    
    // Now you can use addFriend function or any other DOM manipulation
});


function toggleCaptionEdit() {
	
	var id = sessionStorage.getItem("userID");
	console.log("My id=" + userID);
	console.log("Ur id = " + id);
    if (userID == parseInt(id)) {
		console.log("DONE");
        document.getElementById("captionEditDiv").style.display = "block";
    } else {
		
        document.getElementById("captionEditDiv").style.display = "none";
         document.getElementById("addFriend").style.display = "block";
         
         
    }
}
displayCaption();
displayImage();
