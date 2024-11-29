document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    if (!file) {
        alert("Veuillez sélectionner un fichier à télécharger.");
        return;
    }

    const formData = new FormData();
    formData.append('cv', file);

    const uploadStatus = document.getElementById('uploadStatus');
    const fileUrl = document.getElementById('fileUrl');
    uploadStatus.textContent = "Téléchargement en cours...";

    fetch('/upload/cv', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.url) {
            uploadStatus.textContent = "Téléchargement réussi!";
            fileUrl.innerHTML = `<strong>URL du fichier téléchargé :</strong> <a href="${data.url}" target="_blank">${data.url}</a>`;
        } else {
            uploadStatus.textContent = "Erreur lors du téléchargement.";
        }
    })
    .catch(error => {
        uploadStatus.textContent = "Une erreur est survenue.";
        console.error('Erreur:', error);
    });
});