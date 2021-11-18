function init() {
    alert("xxx");
    const selector = document.querySelector(`#sorter`);
    selector.addEventListener(`change`, function () {
        // todo : take id from option
        fetchData(selector.value, "/api/get").then((responseData) => {
            // show responseData in html
            showProducts(responseData);
        });

    });
}

async function fetchData(sortBy, url)
{
    // set the path; the method is GET by default, but can be modified with a second parameter

    const response = await fetch(url + `?sorted=${sortBy}`);
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
                <img class="" src="../img/product_${prod["id"]}.jpg" alt="" />
                <div class="card-header">
                    <h4 class="card-title" th:text="${prod["name"]}">Product name</h4>
                    <p class="card-text" th:text="${prod["description"]}">Product description... </p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead" th:text="${prod["defaultPrice"]}">100 USD</p>
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