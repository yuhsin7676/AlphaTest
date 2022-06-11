// Объявляем переменные, которые ссылаются на DOM-элементы
var resultMessage = document.getElementById("resultMessage");
var selectCode = document.getElementById("selectCode");
var gifImg = document.getElementById("gifImg");

// Получим коды валют и заполним им selectCode (select-меню)
getCodes();

/******************************
*      Описание функций:
******************************/
function getCodes(){
    
    $.ajax({
        url: 'http://localhost:8080/demo/getRates',
        method: 'GET',
        dataType: "text/html",
        complete: function (data) {
            
            // Получим ответ response и выделим список с ключами
            var response = data.responseText;
            response = JSON.parse(response);
            
            // из списка выделим коды валют и заполним ими selectCode (select-меню)
            for(key in response)
                selectCode.innerHTML += "<option>" + key + "</option>";

        },
        failed: function(data) {
            
            // Получим ответ response и выделим статус ошибки
            var response = data.responseText;
            response = JSON.parse(response);
            
            // Выведем сообщение об ошибке
            resultMessage.style.display = "block";
            resultMessage.className = "alert alert-danger";
            resultMessage.innerHTML = "get-запрос к http://localhost:8080/demo/getCodes завершился с ошибкой " + response.status;
            
        }
    });
}

function getGif(){
    
    // Просмотрим код, выбранный в selectCode (select-меню)
    var code = selectCode.value;
    
    $.ajax({
        url: 'http://localhost:8080/demo/getGif/' + code,
        method: 'GET',
        dataType: "text/html",
        complete: function (data) {
            
            var response = data.responseText;
            response = JSON.parse(response);
            
            // Выделим из response-ответа объекты gifResponse и key (см. модель responseGifModel в пакете AlphaTestTomcat.model)
            var gifResponse = JSON.parse(response.gifResponse);
            var key = response.key;
            
            // Выведем сообщение об изменении курса доллар к выбранной валюте
            resultMessage.style.display = "block";
            
            // key равен 1 или -1.
            if(key == 1){ 
                resultMessage.className = "alert alert-success";
                resultMessage.innerHTML = "Курс доллара по отношению к " + code + " вырос";
            }
            else if(key == -1){
                resultMessage.className = "alert alert-danger";
                resultMessage.innerHTML = "Курс доллара по отношению к " + code + " упал";
            }
            
            // Вставим гифку
            gifImg.src = gifResponse.data.images.original.url;

        }
    });
    
}

