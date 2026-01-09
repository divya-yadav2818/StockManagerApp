// =========================
// SEARCH / FILTER PRODUCTS
// =========================
function filterProducts() {
    const input = document.getElementById("search");
    if (!input) return;
    const filter = input.value.toLowerCase();
    const table = document.getElementById("productTable");
    if (!table) return;
    const tr = table.getElementsByTagName("tr");

    for (let i = 1; i < tr.length; i++) {
        let tdName = tr[i].getElementsByTagName("td")[0];
        let tdUnit = tr[i].getElementsByTagName("td")[1];
        if (tdName && tdUnit) {
            let txtValue = tdName.textContent + " " + tdUnit.textContent;
            tr[i].style.display = txtValue.toLowerCase().includes(filter) ? "" : "none";
        }
    }
}

// =========================
// EDIT PRODUCT FORM
// =========================
function editProduct(name, unit, qty, minQty, type) {
    const originalNameInput = document.getElementById("originalName");
    const nameInput = document.getElementById("name");
    const unitInput = document.getElementById("unit");
    const qtyInput = document.getElementById("qty");
    const minQtyInput = document.getElementById("minQty");
    const typeSelect = document.getElementById("type");

    if (!originalNameInput || !nameInput || !unitInput || !qtyInput || !minQtyInput || !typeSelect) return;

    originalNameInput.value = name;
    nameInput.value = name;
    unitInput.value = unit;
    qtyInput.value = qty;
    minQtyInput.value = minQty;
    typeSelect.value = type;
}

// =========================
// DELETE PRODUCT
// =========================
function deleteProduct(productId){
    if(confirm("Do you want to delete this product permanently?")){
        window.location.href = "DeleteProductServlet?productId=" + productId;
    }
}

// =========================
// DARK MODE TOGGLE
// =========================
function toggleDarkMode() {
    document.body.classList.toggle("dark-mode");
}

// =========================
// LOW STOCK HIGHLIGHT
// =========================
function highlightLowStock() {
    const table = document.getElementById("productTable");
    if (!table) return;
    const rows = table.getElementsByTagName("tr");
    for (let i = 1; i < rows.length; i++) {
        const qty = parseInt(rows[i].getElementsByTagName("td")[2].textContent);
        const minQty = parseInt(rows[i].getElementsByTagName("td")[3].textContent);
        if (qty <= minQty) {
            rows[i].classList.add("low-stock");
        }
    }
}

// =========================
// OTP VALIDATION (CLIENT-SIDE)
// =========================
function validateOTP() {
    const otpForm = document.getElementById("otpForm");
    const otpInput = document.getElementById("otpInput");
    if (!otpForm || !otpInput) return;

    otpForm.addEventListener("submit", (e) => {
        const enteredOTP = otpInput.value.trim();
        if (enteredOTP === "") {
            e.preventDefault();
            alert("Enter OTP!");
        }
    });
}

// =========================
// PAGE LOAD
// =========================
window.onload = () => {
    filterProducts();
    highlightLowStock();
    validateOTP();
};
