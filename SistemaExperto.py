#Sistema experto para ayudar a los jugadores de Call of Duty Zombis a elegir mapas en función de sus necesidades.

def sistema_experto_COD():
    data = {
        "Liberty Falls": {
            "Munición Elemental": "No",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
        "Terminus": {
            "Munición Elemental": "Sí",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "No",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
        "Citadelle": {
            "Munición Elemental": "Sí",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "Sí",
            "Mejora de Campo": "No",
        },
        "The Tomb": {
            "Munición Elemental": "No",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "No",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
        "Shattered Veil": {
            "Munición Elemental": "Sí",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "No",
            "Mejora de Campo": "Sí",
        },
        "Janus Towers": {
            "Munición Elemental": "Sí",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "Sí",
            "Mejora de Campo": "Sí",
        },
        "Dark Aether": {
            "Munición Elemental": "No",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
        "Tranzit": {
            "Munición Elemental": "No",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
        "Die Maschine": {
            "Munición Elemental": "No",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
        "Alpha Omega": {
            "Munición Elemental": "No",
            "Arma Especial": "Sí",
            "Equipamiento Táctico": "Sí",
            "Ventajas": "No",
            "Mejora de Campo": "No",
        },
    }

    requerimiento = {}
    print("------------------------------")
    print("Responde solo con 'Sí' o 'No':")
    print("------------------------------")

    def getRespuesta(pregunta):
        while True:
            respuesta = input(pregunta).strip().capitalize()
            if respuesta in ["Si", "No"]:
                return respuesta
            else:
                print("Ingresa una opcion valida ('Si' o 'No').")

    requerimiento["Munición Elemental"] = getRespuesta("¿Necesitas Munición Elemental?: ")
    requerimiento["Arma Especial"] = getRespuesta("¿Necesitas Arma Especial?: ")
    requerimiento["Equipamiento Táctico"] = getRespuesta("¿Necesitas Equipamiento Táctico?: ")
    requerimiento["Ventajas"] = getRespuesta("¿Necesitas Ventajas?: ")
    requerimiento["Mejora de Campo"] = getRespuesta("¿Necesitas Mejora de Campo?: ")

    mapas_compatibles = []
    for mapa, requisitos in data.items():
        compatible = True
        for necesidad, respuestaAdd in requerimiento.items():
            requerimiento_mapa = requisitos.get(necesidad)
            if respuestaAdd == "Si":
                if requerimiento_mapa == "No":
                    compatible = False
                    break
            elif respuestaAdd == "No":
                if requerimiento_mapa == "Sí":
                    compatible = False
                    break
        if compatible:
            mapas_compatibles.append(mapa)

    if mapas_compatibles:
        print("\nMapas que cumplen con tus necesidades:")
        for mapa in mapas_compatibles:
            print(f"- {mapa}")
    else:
        return "\nNo se existen mapas que cumplan con todas tus necesidades."


resultado = sistema_experto_COD()
print(resultado)