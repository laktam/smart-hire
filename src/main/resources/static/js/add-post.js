document.getElementById('postForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const formData = new FormData(event.target);

    fetch('/posts/add', {
        method: 'POST',
        body: formData, 
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('responseMessage').textContent = data.message || 'Post added successfully!';
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('responseMessage').textContent = 'Failed to add post. Please try again.';
    });
});