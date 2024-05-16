const fecha = document.getElementById("fecha-actual"),
dias = document.querySelector(".dias"),
    horas = document.querySelector("#horas"),
    prevNext = document.querySelectorAll(".icons span");

const contenidoC = document.querySelector("#calendario div.contenido"),
    contenidoI = document.querySelector("#informacion div.contenido");


let date = new Date(),
ano = date.getFullYear(),
mes = date.getMonth();

const meses = ["Enero","Febreo","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];

function ajustarCalendario(){
    let primerDiaDelMes = new Date(ano,mes ,1).getDay(),
        ultimaFechaDelMes =new Date(ano,mes + 1,0).getDate(),
        ultimoDiaDelMes = new Date(ano,mes,ultimaFechaDelMes).getDay(),
        ultimaFechaDelUltimoMes = new Date(ano,mes,0).getDate();
    let liTag = "";
    for(let i = primerDiaDelMes; i > 0;i--){
        liTag +=  `<li class="inactivo">${ultimaFechaDelUltimoMes - i + 1}</li>`;
    }

    for(let i = 1; i <= ultimaFechaDelMes;i++){
        let esHoy = i === date.getDate() && mes === new Date().getMonth() && ano === new Date().getFullYear() ? "activo" : "";
        liTag +=  `<li class="${esHoy}" onclick="seleccionar(this)">${i}</li>`;
    }


    for(let i = ultimoDiaDelMes; i < 6 ;i++){
        liTag +=  `<li class="inactivo">${i - ultimoDiaDelMes + 1}</li>`;
    }

    fecha.innerText = meses[mes] + ', ' + ano ;
    dias.innerHTML = liTag;
}

prevNext.forEach(icon =>{
   icon.addEventListener("click",() =>{
      mes = icon.id === "anterior" ? mes - 1 : mes + 1;
      if(mes < 0 || mes > 11){
          date = new Date(ano,mes, new Date().getDate());
          ano = date.getFullYear();
          mes = date.getMonth();
      }else {
          date = new Date();
      }
      ajustarCalendario();
   });
});

function actualizarHoras(ano,mes,dia){
    while (horas.firstChild) {
        horas.removeChild(horas.firstChild);
    }
    fetch('/cotizaciones/horasDisponibles/' + ano + '/' + mes + '/' + dia).then(response => response.json())
        .then(data => {
            data.forEach(hora =>{
                var nuevoLi = document.createElement('li');
                nuevoLi.textContent = hora;
                nuevoLi.classList.add('informacion');
                horas.appendChild(nuevoLi);
            });

        });


}

function seleccionar(elemento){
    const activo = document.querySelector("li.activo");
    if(activo != null) activo.classList.remove("activo");
    elemento.classList.toggle("activo");
    const partes = fecha.innerText.split(/,\s*/);
    actualizarHoras(partes[1],partes[0],elemento.innerHTML);

}

function igualarAltura(){
    const alturaMaximaC = contenidoC.offsetHeight;
    const alturaMaximaI = contenidoI.offsetHeight;
    const alturaMaxima = Math.max(alturaMaximaC,alturaMaximaI);
    contenidoI.style.height = `${alturaMaxima}px`

}

ajustarCalendario();
igualarAltura();