const image_input = document.querySelector("#imageUpload");
var uploaded_image = "";

image_input.addEventListener("change", function(){
	const reader = new FileReader();
	reader.addEventListener("load", () => {
		uploaded_image = reader.result;
		document.querySelector("#display_image").style.backgroundImage = `url(${uploaded_image})`;
	});
	reader.readAsDataURL(this.files[0]);
});


const caption_input = document.querySelector("#caption");
const caption_display = document.querySelector("#display_caption");

caption_input.addEventListener("input", function() {
    if (this.value.length > 255) {
        this.value = this.value.slice(0, 255);
    }
    caption_display.textContent = this.value; // Update the text content of the display element
});

