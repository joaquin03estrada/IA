import tensorflow as tf
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Flatten
from tensorflow.keras.utils import to_categorical

# Cargar el conjunto de datos MNIST, que ya está dividido en conjuntos de entrenamiento y prueba
(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

# Explora la forma de los datos
print("Forma de las imágenes de entrenamiento:", train_images.shape)
print("Número de etiquetas de entrenamiento:", len(train_labels))
print("Forma de las imágenes de prueba:", test_images.shape)
print("Número de etiquetas de prueba:", len(test_labels))

# Visualiza un ejemplo (opcional)
import matplotlib.pyplot as plt
plt.imshow(train_images[0], cmap='gray')
plt.title(f"Etiqueta: {train_labels[0]}")
plt.show()

# Normalizar las imágenes para que los valores de los píxeles estén entre 0 y 1
train_images = train_images.astype('float32') / 255.0
test_images = test_images.astype('float32') / 255.0

# Convertir las etiquetas a codificación one-hot
num_classes = 10
train_labels = to_categorical(train_labels, num_classes)
test_labels = to_categorical(test_labels, num_classes)

print("Forma de las etiquetas de entrenamiento después de one-hot encoding:", train_labels.shape)
print("Forma de las etiquetas de prueba después de one-hot encoding:", test_labels.shape)

model = Sequential([
    Flatten(input_shape=(28, 28)),  # Aplanar la imagen de 28x28 a un vector de 784
    Dense(128, activation='relu'),   # Capa densa con 128 neuronas y función de activación ReLU
    Dense(num_classes, activation='softmax') # Capa de salida con 10 neuronas (una por cada clase) y función de activación softmax
])

# Compilar el modelo
model.compile(optimizer='adam',
              loss='categorical_crossentropy',
              metrics=['accuracy'])

# Mostrar la arquitectura del modelo
model.summary()

epochs = 10  # Número de veces que el modelo recorrerá todo el conjunto de entrenamiento
batch_size = 32 # Número de muestras por lote durante el entrenamiento

history = model.fit(train_images, train_labels,
                    epochs=epochs,
                    batch_size=batch_size,
                    validation_split=0.2) # Usar el 20% de los datos de entrenamiento para la validación

loss, accuracy = model.evaluate(test_images, test_labels, verbose=0)
print(f"Pérdida en el conjunto de prueba: {loss:.4f}")
print(f"Precisión en el conjunto de prueba: {accuracy:.4f}")

import numpy as np

predictions = model.predict(test_images[:10])
predicted_labels = np.argmax(predictions, axis=1)
true_labels = np.argmax(test_labels[:10], axis=1)

print("Predicciones:", predicted_labels)
print("Etiquetas verdaderas:", true_labels)

# Visualiza las predicciones (opcional)
fig, axes = plt.subplots(2, 5, figsize=(12, 5))
axes = axes.ravel()
for i in range(10):
    axes[i].imshow(test_images[i], cmap='gray')
    axes[i].set_title(f"Pred: {predicted_labels[i]}, True: {true_labels[i]}")
    axes[i].axis('off')
plt.tight_layout()
plt.show()

