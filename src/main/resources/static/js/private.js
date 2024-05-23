const detalles = document.querySelectorAll("div.tarjeta-producto div.detalle");
const deleteButtons = document.querySelectorAll('.borrar');
const updateButtons = document.querySelectorAll('.actualizar');
const modalMensaje = document.getElementById("modal-mensaje");
const modalAc = document.getElementById("modal-actualizar");
var opcionesCategorias = document.querySelectorAll("dialog.formulario form select.selector-cateogrias");

const respuestaTexto = document.querySelector("#modal-mensaje h2");

var nombreProducto;


detalles.forEach(tarjeta =>{
    var nuevoUl= document.createElement('ul');
    var divNombre = tarjeta.querySelector('div.categorias');
    nuevoUl.classList.add('categorias');
    const nombreProducto = tarjeta.querySelector('h3.nombre')
    fetch('/productos/categorias/' + nombreProducto.innerHTML).then(response => response.json())
        .then(data => {
            if(data.length == 0){
                console.log(data);
                console.log(nombreProducto.innerHTML);
            }
            data.forEach(categoria =>{
                var nuevloLi = document.createElement("li");
                nuevloLi.textContent = categoria;
                nuevoUl.appendChild(nuevloLi);
            });
        });
    divNombre.appendChild(nuevoUl);
});


updateButtons.forEach(button => {
    button.addEventListener("click", function() {
        nombreProducto = this.getAttribute('data-id');
        window.location.href = '/LincolnLines/privado/actualizarProducto?nombre=' + nombreProducto;
    });
});

deleteButtons.forEach(button => {
    button.addEventListener("click", function() {
        nombreProducto = this.getAttribute('data-id');
        window.location.href = '/CRUD/producto/borrar?nombre=' + nombreProducto;
    });
});

document.addEventListener("DOMContentLoaded", function() {
    if(respuestaTexto.innerHTML){
        modalMensaje.showModal();
    }
});

function cerrarModal(){
    modalMensaje.close();
    window.location.replace("/LincolnLines/privado/landPage");
}


/*
function obtenerPagina(total,pagina,maxLongitud,paginas){
    function rango(inicio,fin){
        return Array.from(Array(fin - inicio + 1),( ,i) => i + inicio);
    }

    var x = maxLongitud < paginas ? 1:2;
    var y = (maxLongitud - x *2 -3) >> 1;
    var z = (maxLongitud - x *2 -3) >> 1;

    if(total <= maxLongitud){
        return rango(1,total);
    }

    if(page <= maxLongitud - x - 1 - z){
        return rango(1,maxLongitud-x-1).concat(0,rango(total - x + 1,total));
    }

    if(pagina >= total - x - 1 - z){
        return rango(1,x).concat(0,rango(total - x - 1 - z - y,total));

    }

    return(1,x).concat(0,rango(pagina - y,pagina + z),rango(total-x+1,total));
}

$(function (){
   var numeroItems = $(".contenido .tarjeta-producto").length;
   var limetePorPagina = 3;
   var totalPaginas = Math.ceil(numeroItems/limetePorPagina);

   var paginaActual;

   function mostrarPagina(cual){
       if(cual < 1 || cual > totalPaginas) return false;
   }
});
*/
