// JavaScript code (script.js) - 페이지 로드 시 제품 목록 가져오기 및 표시

window.onload = function() {
    // 페이지 로드 시 제품 목록을 가져와서 표시
    fetchProducts();
};

function fetchProducts() {
    fetch('http://example.com/api/products')
        .then(response => response.json())
        .then(products => {
            displayProducts(products);
        })
        .catch(error => console.error('Error fetching products:', error));
}

function displayProducts(products) {
    const productList = document.getElementById('product-list');
    productList.innerHTML = ''; // 기존 제품 목록 초기화

    products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product');
        productItem.innerHTML = `
            <h2>${product.name}</h2>
            <p>${product.description}</p>
            <p>Price: $${product.price}</p>
            <img src="${product.imageUrl}" alt="${product.name}">
        `;
        productList.appendChild(productItem);
    });
}

// 회원가입 폼 보이게 하기
function showSignupForm() {
    document.getElementById('signup-form').style.display = 'block';
}

// 회원가입 처리 함수
function register() {
    const username = document.getElementById('signup-username').value;
    const password = document.getElementById('signup-password').value;
    const confirmPassword = document.getElementById('signup-confirm-password').value;
    const birthdate = document.getElementById('signup-birthdate').value;
    const signupResult = document.getElementById('signup-result'); // 결과를 표시할 요소

    // 패스워드 일치 여부 확인
    if (password !== confirmPassword) {
        signupResult.innerText = 'Passwords do not match.';
        return;
    }

    // 서버로 회원가입 정보 전송
    fetch('http://example.com/api/register', { // 실제 서버의 URL로 변경
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            password: password,
            birthdate: birthdate
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            signupResult.innerText = 'Registration successful. You can now log in.';
            // 회원가입 완료 후 로그인 폼으로 돌아가기
            document.getElementById('login-form').style.display = 'block';
        } else {
            signupResult.innerText = 'Registration failed. Please try again later.';
        }
    })
    .catch(error => {
        console.error('Error registering user:', error);
        signupResult.innerText = 'An error occurred while registering. Please try again later.';
    });
}
