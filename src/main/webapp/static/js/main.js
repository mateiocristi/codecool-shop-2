function init() {
    const categorySelector = document.querySelector(`#categorySorter`);
    const supplierSelector = document.querySelector(`#supplierSorter`);
    const addButtons = document.querySelectorAll(`.btn-success`);
    // const removeButtons = document.querySelectorAll(`.btn-danger`);
    // console.log(cartContainer);

    addButtons.forEach((button) => {
        button.addEventListener("click", function () {
            apiPost("/api/shoppingCart/add" + `?id=${button.id}` , {"id": button.id})
        })
    });

    supplierSelector.addEventListener("change", function () {
        apiGet(categorySelector.value, supplierSelector.value, "/api/get/products").then((responseData) => {
            // show responseData in html
            showProducts(responseData);
        });

    });

    categorySelector.addEventListener("change", function () {
        apiGet(categorySelector.value, supplierSelector.value, "/api/get/products").then((responseData) => {
            // show responseData in html
            showProducts(responseData);
        });

    });
}

async function apiGet(category, supplier, url)
{
    // set the path; the method is GET by default, but can be modified with a second parameter

    const response = await fetch(url + `?category=${category}&supplier=${supplier}`);
    let json = response.json();
    console.log("json" + json);
    return await json;
}

async function apiPost(url, payload) {
    const response = await fetch(url,
        {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })
    if (response.ok) {
        let data = response.json()
        return data;
    }

}

function updateCart(container, responseData) {
    container.innerHTML = "";
    container.insertAdjacentHTML("beforeend",
        `<tr class="card-title">
                  <th>Name</th>
                  <th>Description</th>
                  <th>Price</th>
                  <th></th>
              </tr>`)
    responseData.forEach((cartProd) => {
        container.insertAdjacentHTML("beforeend",
            `
                <tr class="card-title">
                    <td >${cartProd["name"]}</td>
                    <td >${cartProd["defaultPrice"] + ' ' +cartProd.defaultCurrency}</td>
                    <td >${cartProd["description"]}</td>
                    <td> <div class="card-text">
                            <button type="button" class="btn btn-danger" id="${cartProd["id"]}">Remove</button>
                        </div>
                    </td>
                </tr>
                  `)
    })
}

function showProducts(responseData) {
    const contentDiv = document.querySelector(`.row`);
    contentDiv.innerHTML = "";
    responseData.forEach((prod) => {
        console.log(prod.id)
        contentDiv.insertAdjacentHTML("beforeend",
            `<div class="col col-sm-12 col-md-6 col-lg-4" id="${prod["id"]}">
                      <img class="img" src="../static/img/product_${prod["id"]}.jpg" alt="" />
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
                   </div>`)
    })
}

init();