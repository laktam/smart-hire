document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    if (!file) {
        alert("Select a pdf resume file.");
        return;
    }

    const postSelect = document.getElementById('postSelect');
    const selectedPost = postSelect.value;

    if (!selectedPost) {
        alert("Select application job title.");
        return;
    }

    const formData = new FormData();
    formData.append('cv', file);

    const uploadStatus = document.getElementById('uploadStatus');
    const fileUrl = document.getElementById('fileUrl');
    uploadStatus.textContent = "Analyzing application...";
    fileUrl.innerHTML = "";

    fetch(`apply/${selectedPost}/upload`, {  // Use selectedPost as part of the fetch URL
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.url) {
            uploadStatus.textContent = "Application added succesfully!";
            fileUrl.innerHTML = `<strong>Uploaded file url :</strong> <a href="${data.url}" target="_blank">${data.url}</a>`;
        } else {
            uploadStatus.textContent = "Error.";
        }
    })
    .catch(error => {
        uploadStatus.textContent = "Error.";
        console.error('Erreur:', error);
    });
});
