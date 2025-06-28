% Hechos

ingrediente(bastón, madera, 2).
ingrediente(espada, hierro, 3).
ingrediente(espada, bastón, 1).
ingrediente(espada, madera, 1).

elemento_basico(madera).
elemento_basico(hierro).


% Inventario

tengo(madera, 6).
tengo(hierro, 90).
tengo(bastón, 90).
tengo(pepe, 1).
% Reglas

%Consulta final:
%quePuedoHacerConMiInventarioActual(Res)
sumar_tuplas([], []).
sumar_tuplas([(Obj, Cant)|T], Resultado) :-
    sumar_tuplas(T, Parcial),
    ( select((Obj, CantPrevio), Parcial, Resto) %intenta buscar si en la lista 
    %Parcial ya existe una tupla con el mismo Obj.
    -> NuevaCant is Cant + CantPrevio,
       Resultado = [(Obj, NuevaCant)|Resto] %Si encuentra el objeto, lo pone con la nueva cantidad
    ;  Resultado = [(Obj, Cant)|Parcial] %Si no lo encuentra, lo crea
    ).
%sumar_tuplas([(madera,3), (hierro,2), (madera,5)], Resultado).
%Primera llamada:
%Cabeza: (madera,3), Parcial1 [(hierro,2), (madera,5)]
%Segunda llamada:
%Cabeza: (hierro,2) Parcial2 [(madera,5)]
%Tercera llamada:
%Cabeza: (madera,5) Parcial3 []
%Caso base de [] es []
%Ahora vuelve para atras, existe (madera,5) en []? No, lo mete
%existe (hierro,2) en [(madera,5)]? No, lo mete
%existe (madera,3) en [(hierro,2), (madera,5)]? Si, asi que hace 3+5 = 8 y pone (madera,8)
%Resultado final: [(madera, 8), (hierro, 2)]

% Si el objeto está en inventario, lo consume

descomponer_hasta_inventario_tuplas([], _, []).
descomponer_hasta_inventario_tuplas([(Obj, Cant)|T], Inventario, ResFinal) :-
    repetir_lista([Obj], Cant, ListaObjs),
    descomponer_hasta_inventario_tuplas(ListaObjs, Inventario, ResObj),
    descomponer_hasta_inventario_tuplas(T, Inventario, ResResto),
    append(ResObj, ResResto, ResFinal).


descomponer_hasta_inventario_y_contar(TuplasEntrada, InvIntermedios, ResultadoFinal) :-
    descomponer_hasta_inventario_tuplas(TuplasEntrada, InvIntermedios, ListaBasicos),
    contar_elementos(ListaBasicos, ResultadoIntermedio),
    sumar_tuplas(ResultadoIntermedio, ResultadoFinal).


% Repite una lista N veces
repetir_lista(_, 0, []) :- !.
repetir_lista(L, N, R) :-
    N >= 1,
    N1 is N - 1,
    repetir_lista(L, N1, R1),
    append(L, R1, R).

tuplaIngrediente(X, TuplaBasicos, TuplaIntermedios):-
    findall((Clave, Valor), (ingrediente(X, Clave, Valor),elemento_basico(Clave)), TuplaBasicos),
	findall((Clave, Valor), (ingrediente(X, Clave, Valor),\+elemento_basico(Clave)), TuplaIntermedios).

tuplaInventario(TuplaBasicos, TuplaIntermedios):-
    findall((Clave, Valor), (tengo(Clave, Valor),elemento_basico(Clave)), TuplaBasicos),
    findall((Clave, Valor), (tengo(Clave, Valor),\+elemento_basico(Clave)), TuplaIntermedios).     

multiplicarTupla([],_,[]).
multiplicarTupla([(Clave, Valor)|XS], N, [(Clave, Valor2)|Res]):-
    Valor2 is Valor * N,
    multiplicarTupla(XS, N, Res).

restarListas([], _, []).
restarListas([(K, V1)|T1], L2, [(K, V1)|R]) :-
    \+member((K, _), L2),
    restarListas(T1, L2, R).
restarListas([(K, V1)|T1], L2, [(K, Resta)|R]) :-
    member((K, V2), L2),
    Resta is V1 - V2,
    restarListas(T1, L2, R).

%Chequea que todas las claves de la tupla sean positivas o 0
chequearLista([]).
chequearLista([(_, V1)|T1]):- V1>(-1), chequearLista(T1).


purga_inventario_positivo([], []).
purga_inventario_positivo([(Obj, Cant)|T], [(Obj, Cant)|Resto]) :-
    Cant >= 1,
    purga_inventario_positivo(T, Resto).
purga_inventario_positivo([(_, Cant)|T], Resto) :-
    Cant < 1,
    purga_inventario_positivo(T, Resto).

existe(X):-
    ingrediente(X,_,_),
    \+elemento_basico(X).
%Prueba si se puede craftear un objeto una N cantidad de veces. Devuelve true o false
puedeCraftearN(Objeto, N):-
    existe(Objeto), %El objeto existe?
    tuplaInventario(TBI, TII),  %Devuelve una lista de tuplas de objetos basicos 
    %e intermedios del inventario
    tuplaIngrediente(Objeto, ParcialTBO, ParcialTIO), %Idem con el objeto solicitado
    multiplicarTupla(ParcialTBO, N, TBO),
    multiplicarTupla(ParcialTIO, N, TIO), %Multiplica todas las claves por N
    restarListas(TBI, TBO, BasicosActualizado), % Resto los elementos básicos del inventario y tupla
    chequearLista(BasicosActualizado), %Si alguno tiene negativo, ya tira false
    %Resto ambas listas entre si para eliminar si es posible a los objetos intermedios
    restarListas(TIO, TII, IntermediosObjActualizado),
    restarListas(TII, TIO, IntermediosInvActualizado),
	%Transforma (elem, cant) en [elem, elem, elem...] cant veces
    %Si los elementos son 0 o vacios los quita de la lista
    %expandir_tuplas(IntermediosObjActualizado, ListaIntermedios),
    %Si el inventario tiene algun elemento en 0 o negativo, es decir ese elemento
    %ya no forma parte del inventario, lo elimino de la lista
    purga_inventario_positivo(IntermediosInvActualizado, InvFinal),
    purga_inventario_positivo(IntermediosObjActualizado, ObjFinal),
    %Todo lo que se puede restar de acá se resta de lo que quede del inventario
    %Devuelve una lista de todos los elementos básicos faltantes
    descomponer_hasta_inventario_tuplas(ObjFinal, InvFinal, BasicosFaltantes),
	%finalmente, resta de nuevo las listas. Si la lista del inventario es 0 o positiva, es
    %que lo pudo craftear
    restarListas(BasicosActualizado, BasicosFaltantes, Final),
    chequearLista(Final).

%Retorna cuantas veces se puede craftear un objeto
cuantosPuedeCraftear(Objeto, Cant):- puedeHacer(Objeto, 1, Cant).
puedeHacer(Objeto, Actuales, Res):-
   	\+puedeCraftearN(Objeto, Actuales),
    Res is Actuales - 1.
puedeHacer(Objeto, Actuales, Res):-
   	puedeCraftearN(Objeto, Actuales),
    Actualizar is Actuales + 1,
    puedeHacer(Objeto, Actualizar, Res).

%Lista todos los objetos que tengan alguna receta
lista_objetos_compuestos(Lista) :-
    findall(Objeto, ingrediente(Objeto, _, _), Objetos),
    sort(Objetos, Lista). % sort elimina duplicados y ordena
%Se fija cuantos puede craftear de un determinado objeto. Si no puede craftear ninguno no
%lo pone en la lista
iterar([],[]).
iterar([Objeto|Cola],Res):-
	cuantosPuedeCraftear(Objeto, Parcial),
    Parcial<1,
    iterar(Cola, Res).
iterar([Objeto|Cola],[(Objeto, Num)|Res]):-
	cuantosPuedeCraftear(Objeto, Parcial),
    Parcial>=1,
    Num is Parcial,
    iterar(Cola, Res).

quePuedoHacerConMiInventarioActual(Res):-
    lista_objetos_compuestos(ListaCompuestos),
    iterar(ListaCompuestos,Res).




