# Importa las librerías 
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt

# Centimetros y su equivalente en pulgadas
centimetros = np.array([1, 10, 50, 100, 500, 1000], dtype=float)  # centimetros
pulgadas = np.array([0.393701, 3.93701, 19.685, 39.3701, 196.85, 393.701], dtype=float)  # pulgadas (tasa ficticia: 1 cm = 0.393701 pulgadas)

# Imprimimos los datos para verificar
print("centimetros:", centimetros)
print("pulgadas:", pulgadas)

# Define el modelo de red neuronal
capa = tf.keras.layers.Dense(units=1, input_shape=[1])  # Una capa con una neurona
modelo = tf.keras.Sequential([capa])  # Modelo secuencial con una sola capa

# Compila el modelo
modelo.compile(
    optimizer=tf.keras.optimizers.Adam(0.1),  # Tasa de aprendizaje
    loss='mean_squared_error'  # Función de pérdida
)

# Entrenamos el modelo
print("Comenzando entrenamiento...")
historial = modelo.fit(centimetros, pulgadas, epochs=1000, verbose=False)
print("Modelo entrenado!")

# Graficamos la función de pérdida
plt.xlabel("# Época")
plt.ylabel("Magnitud de pérdida")
plt.plot(historial.history["loss"])
plt.show()

# Realizamos una predicción
print("Hagamos una predicción!")
resultado = modelo.predict(np.array([6000.0]))  # 6000 cm
print(f"El resultado es {resultado[0][0]:.2f} pulgadas")

# Mostramos las variables internas del modelo
print("Variables internas del modelo:")
print(capa.get_weights())