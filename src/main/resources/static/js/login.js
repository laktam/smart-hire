document.addEventListener('DOMContentLoaded', function() {
    // Function to check and apply login state
    function applyLoginState() {
        const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
        
        // Elements that might exist on different pages
        const homeLink = document.getElementById('homeLink');
        const applicationsLink = document.getElementById('applicationsLink');
        const loginLink = document.getElementById('loginLink');
        const loginForm = document.getElementById('loginForm');
        const logoutButton = document.getElementById('logoutButton');
        const message = document.getElementById('message');

        // Redirect logic for home page
        if (window.location.pathname === '/') {
            if (!isLoggedIn) {
                window.location.href = '/offers';
                return; // Stop further execution
            }
        }

        if (isLoggedIn) {
            // Logged in state
            if (homeLink) homeLink.style.display = 'block';
            if (applicationsLink) applicationsLink.style.display = 'block';
            
            if (loginLink) {
                loginLink.textContent = 'Logout';
                loginLink.href = '#'; // Prevent navigation
            }
            
            if (loginForm) loginForm.style.display = 'none';
            if (logoutButton) logoutButton.style.display = 'block';
            
            if (message) {
                message.textContent = 'You are logged in';
                message.style.color = 'green';
            }
        } else {
            // Logged out state
            if (homeLink) homeLink.style.display = 'none';
            if (applicationsLink) applicationsLink.style.display = 'none';
            
            if (loginLink) {
                loginLink.textContent = 'Login';
                loginLink.href = '/login'; // Restore login page link
            }
            
            if (loginForm) loginForm.style.display = 'block';
            if (logoutButton) logoutButton.style.display = 'none';
            
            if (message) {
                message.textContent = 'Please log in';
                message.style.color = 'blue';
            }
        }
    }

    // Apply login state on page load
    applyLoginState();

    // Login form submission
    const loginButton = document.getElementById('loginButton');
    if (loginButton) {
        loginButton.addEventListener('click', function(e) {
            e.preventDefault();
            
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const message = document.getElementById('message');

            if (username === 'admin' && password === '123456') {
                localStorage.setItem('isLoggedIn', 'true');
                
                if (message) {
                    message.textContent = 'Login successful';
                    message.style.color = 'green';
                }
                
                // Redirect to home page
                window.location.href = '/';
            } else {
                if (message) {
                    message.textContent = 'Invalid username or password';
                    message.style.color = 'red';
                }
            }
        });
    }

    // Logout functionality
    const loginLink = document.getElementById('loginLink');
    if (loginLink) {
        loginLink.addEventListener('click', function(e) {
            const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
            
            if (isLoggedIn) {
                e.preventDefault();
                localStorage.setItem('isLoggedIn', 'false');
                
                // Reload to ensure state is updated across all pages
                window.location.reload();
            }
        });
    }

    // Additional logout button (if exists)
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            localStorage.setItem('isLoggedIn', 'false');
            
            // Reload to ensure state is updated across all pages
            window.location.reload();
        });
    }
});








/*const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
const homeLink = document.getElementById('homeLink');
const applicationsLink = document.getElementById('applicationsLink');
const loginLink = document.getElementById('loginLink');

const loginForm = document.getElementById('loginForm');
const loginButton = document.getElementById('loginButton');

//const message = document.getElementById('message');

const hardcodedUsername = 'admin';
const hardcodedPassword = '123456';


document.addEventListener('DOMContentLoaded', function() {


	if (!isLoggedIn) {
		setToLogoutState();
		homeLink.style.display = "none";
		applicationsLink.style.display = "none";
		//loginLink.style.display = "none";

	} else {
		setToLoginState();
	}

	// Handle button click
	loginButton.addEventListener('click', function(e) {
		e.preventDefault();

		if (localStorage.getItem('isLoggedIn') === 'true') {
			// Logout logic
			localStorage.setItem('isLoggedIn', 'false');
			setToLoginState();
		} else {
			// Login logic
			const username = document.getElementById('username').value;
			const password = document.getElementById('password').value;

			if (username === hardcodedUsername && password === hardcodedPassword) {
				localStorage.setItem('isLoggedIn', 'true');
				setToLogoutState();
			} else {
				message.textContent = 'Invalid username or password.';
				message.style.color = 'red';
			}
		}
	});
});



function setToLogoutState() {
	loginLink.textContent = "Logout";
	loginButton.innerText = "Logout";
	
	//message.textContent = 'You are logged in.';
	//message.style.color = 'green';
	loginForm.style.display = 'none';
}

function setToLoginState() {
	//message.textContent = 'You are logged out.';
	message.style.color = 'blue';
	loginForm.style.display = 'block';
	
	loginButton.innerText = "Login";
	loginLink.innerText = "Login";
}*/