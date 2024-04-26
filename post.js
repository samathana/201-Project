const image_input = document.querySelector("#imageUpload");
var uploaded_image = null; // Initialize as null

image_input.addEventListener("change", function(){
    uploaded_image = this.files[0]; // Save the File object
    const reader = new FileReader();
    reader.addEventListener("load", () => {
        document.querySelector("#display_image").style.backgroundImage = `url(${reader.result})`;
    });
    reader.readAsDataURL(uploaded_image);
});

const caption_input = document.querySelector("#caption");
const caption_display = document.querySelector("#display_caption");

caption_input.addEventListener("input", function() {
    if (this.value.length > 255) {
        this.value = this.value.slice(0, 255);
    }
    caption_display.textContent = this.value; // Update the text content of the display element
});

// Function to send data to server
function sendDataToServer() {
    if (!uploaded_image) {
        console.error("No image selected");
        return;
    }
    
    const formData = new FormData();
    formData.append('image', uploaded_image); // Append the File object
    formData.append('caption', caption_input.value);

    // Send the data to the server using AJAX
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'UploadServlet', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
                window.location.replace("http://localhost:8080/201_NewProject/ViewPost.html");
              
        }
    };
    xhr.send(formData);
}
