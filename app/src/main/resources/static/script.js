// ��������� ����������, ������� ��������� �� DOM-��������
var resultMessage = document.getElementById("resultMessage");
var selectCode = document.getElementById("selectCode");
var gifImg = document.getElementById("gifImg");

// ������� ���� ����� � �������� �� selectCode (select-����)
getCodes();

/******************************
*      �������� �������:
******************************/
function getCodes(){
    
    $.ajax({
        url: 'http://localhost:8080/demo/getRates',
        method: 'GET',
        dataType: "text/html",
        complete: function (data) {
            
            // ������� ����� response � ������� ������ � �������
            var response = data.responseText;
            response = JSON.parse(response);
            
            // �� ������ ������� ���� ����� � �������� ��� selectCode (select-����)
            for(key in response)
                selectCode.innerHTML += "<option>" + key + "</option>";

        },
        failed: function(data) {
            
            // ������� ����� response � ������� ������ ������
            var response = data.responseText;
            response = JSON.parse(response);
            
            // ������� ��������� �� ������
            resultMessage.style.display = "block";
            resultMessage.className = "alert alert-danger";
            resultMessage.innerHTML = "get-������ � http://localhost:8080/demo/getCodes ���������� � ������� " + response.status;
            
        }
    });
}

function getGif(){
    
    // ���������� ���, ��������� � selectCode (select-����)
    var code = selectCode.value;
    
    $.ajax({
        url: 'http://localhost:8080/demo/getGif/' + code,
        method: 'GET',
        dataType: "text/html",
        complete: function (data) {
            
            var response = data.responseText;
            response = JSON.parse(response);
            
            // ������� �� response-������ ������� gifResponse � key (��. ������ responseGifModel � ������ AlphaTestTomcat.model)
            var gifResponse = JSON.parse(response.gifResponse);
            var key = response.key;
            
            // ������� ��������� �� ��������� ����� ������ � ��������� ������
            resultMessage.style.display = "block";
            
            // key ����� 1 ��� -1.
            if(key == 1){ 
                resultMessage.className = "alert alert-success";
                resultMessage.innerHTML = "���� ������� �� ��������� � " + code + " �����";
            }
            else if(key == -1){
                resultMessage.className = "alert alert-danger";
                resultMessage.innerHTML = "���� ������� �� ��������� � " + code + " ����";
            }
            
            // ������� �����
            gifImg.src = gifResponse.data.images.original.url;

        }
    });
    
}

