elemento_basico("metal").
elemento_basico("circuitos").
elemento_basico("capacitor").
elemento_basico("cable").
elemento_basico("cristal liquido").
elemento_basico("plastico").
ingrediente("tecla","plastico",20).
ingrediente("computadora gabinete","cable",8).
ingrediente("computadora gabinete","plastico",20).
ingrediente("computadora gabinete","fuente de alimentación",1).
ingrediente("computadora gabinete","placa de video",1).
ingrediente("computadora gabinete","memoria ram 4gb",4).
ingrediente("computadora gabinete","cpu",1).
ingrediente("computadora gabinete","sistema operativo",1).
ingrediente("computadora gabinete","placa madre",1).
ingrediente("rack para servidores","plastico",150).
ingrediente("rack para servidores","metal",30).
ingrediente("chip","circuitos",5).
ingrediente("chip","metal",5).
ingrediente("pantalla","cable",2).
ingrediente("pantalla","cristal liquido",10).
ingrediente("pantalla","chip",2).
ingrediente("sistema operativo","circuitos",120).
ingrediente("cpu","circuitos",10).
ingrediente("cpu","metal",8).
ingrediente("memoria ram 4gb","circuitos",8).
ingrediente("memoria ram 4gb","plastico",4).
ingrediente("memoria ram 4gb","chip",4).
ingrediente("placa de video","circuitos",6).
ingrediente("placa de video","plastico",5).
ingrediente("placa de video","chip",8).
ingrediente("camara","plastico",5).
ingrediente("camara","cable",1).
ingrediente("camara","cristal liquido",5).
ingrediente("parlante","plastico",10).
ingrediente("parlante","cable",2).
ingrediente("fuente de alimentación","cable",10).
ingrediente("fuente de alimentación","capacitor",20).
ingrediente("computadora gabinete simple","cable",4).
ingrediente("computadora gabinete simple","plastico",10).
ingrediente("computadora gabinete simple","fuente de alimentación",1).
ingrediente("computadora gabinete simple","memoria ram 4gb",2).
ingrediente("computadora gabinete simple","cpu",1).
ingrediente("computadora gabinete simple","sistema operativo",1).
ingrediente("computadora gabinete simple","placa madre",1).
ingrediente("placa madre","plastico",15).
ingrediente("placa madre","capacitor",10).
ingrediente("placa madre","chip",5).
ingrediente("teclado","cable",1).
ingrediente("teclado","tecla",50).
ingrediente("servidor","circuitos",50).
ingrediente("servidor","cable",20).
ingrediente("servidor","memoria ram 4gb",25).
ingrediente("servidor","sistema operativo",1).
ingrediente("servidor","placa madre",1).
ingrediente("computadora de escritorio","pantalla",1).
ingrediente("computadora de escritorio","parlante",2).
ingrediente("computadora de escritorio","teclado",1).
ingrediente("computadora de escritorio","computadora gabinete",1).
ingrediente("computadora de escritorio","camara",1).
ingrediente("maquina virtual","sistema operativo",50).
ingrediente("maquina virtual","rack de 10 servidores",1).
ingrediente("rack de 10 servidores","servidor",10).
ingrediente("rack de 10 servidores","cable",20).
ingrediente("rack de 10 servidores","rack para servidores",1).
tengo("circuitos", 10000).
tengo("cable", 500).
tengo("plastico", 500).
tengo("rack para servidores", 1).
tengo("cristal liquido", 500).
tengo("memoria ram 4gb", 2).
tengo("metal", 500).
tengo("chip", 10).
tengo("servidor", 5).
tengo("sistema operativo", 5).
tengo("capacitor", 500).
receta(Ingrediente,1,0) :- elemento_basico(Ingrediente). %SI ES BÁSICO DEVUELVE 1 Y TARDA 0
receta("rack para servidores",1,15.0).
receta("camara",1,5.0).
receta("tecla",10,10.0).
receta("computadora gabinete",1,40.0).
receta("rack para servidores",1,15.0).
receta("chip",2,8.0).
receta("pantalla",2,12.0).
receta("sistema operativo",1,50.0).
receta("cpu",1,15.0).
receta("memoria ram 4gb",1,8.0).
receta("placa de video",1,10.0).
receta("camara",1,5.0).
receta("parlante",2,15.0).
receta("fuente de alimentación",1,5.0).
receta("computadora gabinete simple",1,30.0).
receta("placa madre",1,20.0).
receta("teclado",10,20.0).
receta("servidor",1,60.0).
receta("computadora de escritorio",1,30.0).
receta("maquina virtual",50,120.0).
receta("rack de 10 servidores",1,20.0).
% Calcula el mínimo cociente (lo que limita el número de crafteos)
minimo_crafteos([D/N], Cant) :- Cant is floor(D / N).
minimo_crafteos([D/N | Resto], Cant) :-
    minimo_crafteos(Resto, CantResto),
    Cant is min(floor(D / N), CantResto).


% Verifica si tengo suficientes de todos los ingredientes
puedo_craftear(Objeto, Cantidad, TiempoTotal) :-
    %objetosDisponibles(Objeto),
    receta(Objeto, CantDevuelta, Tiempo),
    (   (ingrediente(Objeto, Ing, _), %Condicion: Si hay algún ingrediente del objeto
	\+tengo(Ing, _))					%Que NO tengo
    ->	(   Cantidad is 0, TiempoTotal is 0) %Pone ambos en 0
    ;   %else
    (   findall(CantDisponible/CantNecesaria,
            (ingrediente(Objeto, Ing, CantNecesaria),
             tengo(Ing, CantDisponible)),
            Cantidades),
    Cantidades \= [], % Debe tener al menos un ingrediente
    minimo_crafteos(Cantidades, CantCrafteos),
    CantCrafteos > 0,
    Cantidad is CantCrafteos * CantDevuelta,
    TiempoTotal is CantCrafteos * Tiempo
    )).

quePuedoCraftear(Objeto, Cantidad, TiempoTotal):- puedo_craftear(Objeto, Cantidad, TiempoTotal), Cantidad>0.