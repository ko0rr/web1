"use strict";

function getX(){
    const xInput = document.querySelector('input[name="x"]:checked');
    return xInput ? parseFloat(xInput.value) : null;
}

function getY(){
    const yInput = document.getElementById("y").value.trim().replace(',', '.');

    if (yInput === "" || yInput === "-" || yInput === " ") {
        return null;
    }

    if (!/^-?\d*[,.]?\d*$/.test(yInput)) {
        return null;
    }

    const numY = parseFloat(yInput.replace(',', '.'));

    if (Number.isNaN(numY) || !isFinite(numY)) {
        return null;
    }

    return numY;
}

function getR(){
    const rInput = document.getElementById("r").value.trim().replace(',', '.');

    if (rInput === "" || rInput === "-" || rInput === " ") {
        return null;
    }

    if (!/^-?\d*[,.]?\d*$/.test(rInput)) {
        return null;
    }

    const numR = parseFloat(rInput.replace(',', '.'));


    if (Number.isNaN(numR) || !isFinite(numR)) {
        return null;
    }

    return numR;
}

function validateInput(x, y, r){
    return (x != null) && (y != null) && (r != null) && (y < 3) && (y > -3) && (r > 2) && (r < 5);
}

function formatNumber(num) {
    return parseFloat(num.toFixed(5)).toString();
}

function addTableRow(x, y, r, hit, date, execTime) {

    const table = document.querySelector('#results-section');
    const tbody = table.querySelector("tbody");
    const row = tbody.insertRow(-1);

    row.insertCell(0).textContent = formatNumber(x);
    row.insertCell(1).textContent = formatNumber(y);
    row.insertCell(2).textContent = formatNumber(r);
    row.insertCell(3).textContent = hit ? "Попала" : "Не попала";
    row.insertCell(4).textContent = date;
    row.insertCell(5).textContent = execTime + " мс";

}

async function sendToServer(){
    const x = getX();
    const y = getY();
    const r = getR();

    if (!validateInput(x, y, r)) {
        alert("Введите корректные значения. Обратите внимание на place-holder\n (0_0)");
        return;
    }

    const data = {x, y, r};
    console.log("Отправляемые на сервер данные -- ", data);

    try {
        const response = await fetch("/fcgi/", {

            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)

        });

        if (!response.ok) throw new Error(`Ошибка сервера: ${response.status}`);

        const raw = await response.text();
        console.log("Ответ сервера -- ", raw);
        const parsed = JSON.parse(raw);
        const body = parsed.body;
        const execTime = parsed.executionTime;

        addTableRow(
            body.x,
            body.y,
            body.r,
            body.hit,
            body.date,
            execTime

        );

    } catch (err) {
        console.error("Запрос не выполнен:", err);
        alert("Произошла ошибка при отправке данных на сервер");
    }

}


