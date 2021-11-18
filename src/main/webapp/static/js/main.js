function init() {
    alert("pula");
    const categorySelector = document.querySelector(`#categorySorter`);
    const supplierSelector = document.querySelector(`#supplierSorter`);

    supplierSelector.addEventListener(`change`, function () {
        fetchData(categorySelector.value, supplierSelector.value, "/api/get/products").then((responseData) => {
            // show responseData in html
            showProducts(responseData);
        });

    });

    categorySelector.addEventListener(`change`, function () {
        fetchData(categorySelector.value, supplierSelector.value, "/api/get/products").then((responseData) => {
            // show responseData in html
            showProducts(responseData);
        });

    });
}

async function fetchData(category, supplier, url)
{
    // set the path; the method is GET by default, but can be modified with a second parameter

    const response = await fetch(url + `?category=${category}&supplier=${supplier}`);
    let json = response.json();
    console.log("json" + json);
    return await json;
}

function showProducts(responseData) {
    const contentDiv = document.querySelector(`.row`);
    contentDiv.innerHTML = "";
    console.log("cleared");
    responseData.forEach((prod) => {
        contentDiv.insertAdjacentHTML("beforeend",
            `<div class="card">
                <img class="" src="../static/img/product_${prod["id"]}.jpg" alt="" />
                <div class="card-header">
                    <h4 class="card-title">${prod["name"]}</h4>
                    <p class="card-text">${prod["description"]}</p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead" >${prod["defaultPrice"]} ${prod["defaultCurrency"]}</p>
                    </div>
                    <div class="card-text">
                        <a class="btn btn-success" href="#">Add to cart</a>
                    </div>
                </div>
            </div>`
            )
    })
}

init();