function resetFormFields() {
			const form = document.getElementById('loginForm');
			if (form) {
				form.reset();
			}
			const errorMessage = document.getElementById('lblErrorMessage');
			if (errorMessage) {
				errorMessage.textContent = '';
			}

		}