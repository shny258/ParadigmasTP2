%¿Cuáles son todos los productos que podría generar con el inventario actual?
%El sistema debe traducir el inventario actual a hechos en Prolog (tengo/2) y 
%definir las reglas y recetas (ingrediente/3). Prolog responderá con la lista de objetos 
%posibles, deduciendo automáticamente a partir de las recetas y cantidades disponibles.

% Hechos
ingrediente(bastón, bastón1, 1).
ingrediente(bastón1, bastón2, 1).
ingrediente(bastón2, madera, 2).

ingrediente(espada, hierro, 3).
ingrediente(espada, bastón, 1).
ingrediente(espada, madera, 1).
elemento_basico(madera).
elemento_basico(hierro).


% Inventario

tengo(madera, 6).
tengo(hierro, 5).
tengo(bastón, 1).

% Reglas
    

puedo_craftear(Objeto) :-
    ingrediente(Objeto, Ing, Cant),
    tengo(Ing, CantDisponible),
    CantDisponible >= Cant.

%Descomponer
% Caso base: si es básico, devuelve una lista con N repeticiones
descomponer(Objeto, [Objeto]) :- 
    elemento_basico(Objeto).

% Caso recursivo: buscar ingredientes y descomponer
descomponer(Objeto, ListaFinal) :-
    %Si por ejemplo descompones baston, con esta funcion te sale [madera, madera]
    findall(IngLista,
        (ingrediente(Objeto, Ingr, Cant),
         descomponer(Ingr, DescompIngr),
         repetir_lista(DescompIngr, Cant, IngLista)),
        Listas),
    flatten(Listas, ListaFinal). %Aplana la lista de listas en una sola

%Repite una lista N veces
repetir_lista(_, 0, []) :- !.
repetir_lista(L, N, R) :-
    N >= 1,
    N1 is N - 1,
    repetir_lista(L, N1, R1),
    append(L, R1, R).

contar_basicos([], []).
contar_basicos([X|XS], Resultado) :-
    contar_basicos(XS, Parcial),
    actualizar_conteo(X, Parcial, Resultado).

% Si X ya está en la lista, incrementa el contador
actualizar_conteo(X, [], [(X,1)]). %Lista vacia? Mete el primer elemento con 1
actualizar_conteo(X, [(X,N)|XS], [(X,N1)|XS]) :-
    N1 is N + 1. %Si lo encuentra (mira las 2 primeras X) le suma 1
actualizar_conteo(X, [Y|XS], [Y|Resto]) :-
    X \= Y, %Si no lo encuentra sigue
    actualizar_conteo(X, XS, Resto).


materiales_necesarios(Objeto, Resumen) :-
    descomponer(Objeto, Basicos), %Te da una lista de objetos basicos
    contar_basicos(Basicos, Resumen). %La transforma en tuplas clave,valor

%Por ahora no se usa
sumar_listas_por_clave([], _, []).
sumar_listas_por_clave([(K, V1)|T1], L2, [(K, Suma)|R]) :-
    member((K, V2), L2),
    Suma is V1 + V2,
    sumar_listas_por_clave(T1, L2, R).

%Te dice cuantas veces un determinado elemento se puede usar para craftear
%Por ejemplo si tenemos 5 de madera y un crafteo requiere 2, este te devuelve 2
cantidad_posible((Elem, Necesario), Inventario, CantPosible) :-
    member((Elem, Disponible), Inventario),
    CantPosible is Disponible // Necesario. %Division entera, descarta lo decimal
cantidades_posibles([], _, []).

cantidades_posibles([H|T], Inventario, [CP|R]) :- %Aplica el predicado anterior a toda la lista
    cantidad_posible(H, Inventario, CP),
    cantidades_posibles(T, Inventario, R).

%Devuelve el minimo de una lista. Este minimo sera la cantidad de crafteos que 
%puede hacerse de un item X con el inventario actual
minimo_lista([X], X).
minimo_lista([H|T], Min) :-
    minimo_lista(T, MinResto),
    Min is min(H, MinResto).



cuantosPuedoCraftear(Objeto, CantMax) :-
    materiales_necesarios(Objeto, Tupla),
    findall((Elemento, Valor), tengo(Elemento, Valor), MiInventario),
    cantidades_posibles(Tupla, MiInventario, Cantidades),
    minimo_lista(Cantidades, CantMax).
