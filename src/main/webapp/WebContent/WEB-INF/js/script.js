const input = document.getElementById('txtpassword');
let realPassword = '';

input.addEventListener('input', (e) => {
  const newValue = e.target.value;
  const oldLength = realPassword.length;
  const newLength = newValue.length;

  // Xác định người dùng thêm hay xóa ký tự
  if (newLength > oldLength) {
    // Thêm ký tự mới
    const addedChar = newValue.slice(-1);
    realPassword += addedChar;
  } else {
    // Xóa ký tự
    realPassword = realPassword.slice(0, newLength);
  }

  // Hiển thị dấu *
  input.value = '*'.repeat(realPassword.length);
});
