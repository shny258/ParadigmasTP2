tengo(palo, 2).
tengo(madera, 30).
tengo(carbon, 1).
elemento_basico(madera).
elemento_basico(carbon).
ingrediente(palo, madera, 3).
ingrediente(antorcha, palo, 5).
ingrediente(antorcha, carbon, 1).
ingrediente(tornillo, madera, 2).
ingrediente(tablon, palo, 5).
ingrediente(tablon, tornillo, 1).
ingrediente(marco, tablon, 2).
ingrediente(marco, tornillo, 4).
ingrediente(mesa, tablon, 1).
ingrediente(mesa, palo, 2).
ingrediente(mesa, marco, 1).
puedo_craftear(Objeto) :- ingrediente(Objeto, Ingrediente, Cantidad), tengo(Ingrediente, CantDisponible), CantDisponible >= Cantidad, (elemento_basico(Ingrediente) ; puedo_craftear(Ingrediente)).
