% Hechos
ingrediente(bastón, bastón1, 1).
ingrediente(bastón1, bastón2, 1).
ingrediente(bastón2, madera, 2).
ingrediente(espada, hierro, 3).
ingrediente(espada, bastón, 1).
ingrediente(espada, madera, 1).
ingrediente(antorcha, carbon, 2).
ingrediente(antorcha, palo, 2).
ingrediente(palo, madera, 1).
ingrediente(escultura, madera, 15).
ingrediente(escudoTorre, madera, 3).
ingrediente(escudoTorre, hierro, 3).
ingrediente(abrigoLiviano, cuero, 5).
ingrediente(abrigoPolar, cuero, 10).
ingrediente(abrigoPolar, hierro, 1).

ingredienteMesa(mesaCarpinteria, escultura).
ingredienteMesa(mesaCarpinteria, escudoTorre).
ingredienteMesa(mesaPeleteria, abrigoLiviano).
ingredienteMesa(mesaPeleteria, abrigoPolar).

elemento_basico(madera).
elemento_basico(hierro).
elemento_basico(carbon).

% Inventario
tengo(madera, 6).
tengo(hierro, 6).
tengo(bastón, 2).
tengo(carbon, 4).
tengo(palo, 4).
tengo(cuero, 100).
tengo(mesaPeleteria, 1).
tengo(mesaCarpinteria, 1).
%receta(Objeto,CantDevuelta,TiempoCrafteo)
receta(Ingrediente,1,0) :- elemento_basico(Ingrediente). %SI ES BÁSICO DEVUELVE 1 Y TARDA 0
receta(espada,1,10).
receta(bastón,1,5).
receta(bastón1,1,2).
receta(bastón2,1,1).
receta(antorcha,2,2).
receta(palo,1,1).
receta(escultura, 1, 50).
receta(escudoTorre, 1, 25).
receta(abrigoPolar, 1, 50).
receta(abrigoLiviano, 1, 25).
% Calcula el mínimo cociente (lo que limita el número de crafteos)
minimo_crafteos([D/N], Cant) :- Cant is floor(D / N).
minimo_crafteos([D/N | Resto], Cant) :-
    minimo_crafteos(Resto, CantResto),
    Cant is min(floor(D / N), CantResto).

objetos_disponibles(Objeto) :- %Esto es para no poner mas de una vez cada ingrediente
    setof(O, Ing^Cant^ingrediente(O, Ing, Cant), Objetos),
    member(Objeto, Objetos).
objetosDisponibles(Objeto):-
    objetos_disponibles(Objeto), %TODOS los objetos
    \+ingredienteMesa(_,Objeto) % - los de la mesa
    ;
    %O sumo los crafteos de la mesa si tengo al menos una mesa
    (tengo(Mesa, Cant),Cant>=1, ingredienteMesa(Mesa, Objeto)).


% Verifica si tengo suficientes de todos los ingredientes
puedo_craftear(Objeto, Cantidad, TiempoTotal) :-
    objetosDisponibles(Objeto),
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