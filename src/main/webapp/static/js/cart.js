

function init() {
    const cartContainer = document.querySelector(`.cart-list-table`);
    const removeButtons = document.querySelectorAll(`.btn-danger`);
    const addButtons = document.querySelectorAll(`.btn-success`);

    addButtons.forEach((button) => {
        console.log("adding..")
        button.addEventListener("click", function () {
            apiPost("/api/shoppingCart/add" + `?id=${button.id}` , {"id": button.id})
                .then(response => {
                    updateCart(cartContainer, response)
                    init();
                })
        })
    });

    removeButtons.forEach((button) => {
        button.addEventListener("click", function () {
            console.log("clicked remove")
            apiPost("/api/shoppingCart/remove" + `?id=${button.id}` , {"id": button.id})
                .then(response => {
                    updateCart(cartContainer, response)
                    init();
                })

        })
    });

}

async function apiGet(url)
{
    // set the path; the method is GET by default, but can be modified with a second parameter

    const response = await fetch(url);
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
    let totalPrice = 0;
    responseData.forEach((cartProd) => {
        totalPrice += cartProd["actualPrice"];
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
    const price = document.querySelector(`.total-price`);
    totalPrice = Math.floor(totalPrice * 100) / 100
    price.innerHTML = totalPrice.toString();

}

init();
 