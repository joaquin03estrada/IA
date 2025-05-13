import tensorflow as tf
from tensorflow.keras import layers, models
import tensorflow_datasets as tfds
import matplotlib.pyplot as plt

# Dataset Fashion MNIST de TensorFlow Datasets
(ds_train, ds_test), ds_info = tfds.load(
    'fashion_mnist',
    split=['train', 'test'],
    shuffle_files=True,
    as_supervised=True,
    with_info=True
)


class_names = ds_info.features['label'].names

# Normalizar las imágenes
def normalize_img(image, label):
  return tf.cast(image, tf.float32) / 255.0, label

# Aplicar la normalizacion a los datasets
ds_train = ds_train.map(normalize_img)
ds_test = ds_test.map(normalize_img)

# Configurar los datasets para el entrenamiento
BATCH_SIZE = 32
ds_train = ds_train.cache().shuffle(ds_info.splits['train'].num_examples).batch(BATCH_SIZE).prefetch(tf.data.AUTOTUNE)
ds_test = ds_test.cache().batch(BATCH_SIZE).prefetch(tf.data.AUTOTUNE)

# Mostrar algunas imagenes del dataset
def show(images, labels):
    plt.figure(figsize=(10,10))
    for i in range(25):
        plt.subplot(5,5,i+1)
        plt.xticks([])
        plt.yticks([])
        plt.grid(False)
        plt.imshow(images[i], cmap=plt.cm.binary)
        plt.xlabel(class_names[labels[i]])
    plt.show()

for images, labels in ds_train.take(1):
    show(images.numpy(), labels.numpy())

# Crear el modelo de la red neuronal convolucional
model = models.Sequential([
    layers.Conv2D(32, (3, 3), activation='relu', input_shape=(28, 28, 1)),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(64, (3, 3), activation='relu'),
    layers.MaxPooling2D((2, 2)),
    layers.Flatten(),
    layers.Dense(128, activation='relu'),
    layers.Dense(10) # numero de clases en Fashion MNIST
])

# Compilar el modelo
model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])

# Entrenar el modelo
history = model.fit(
    ds_train,
    epochs=10,
    validation_data=ds_test
)

# Evaluar el modelo
loss, accuracy = model.evaluate(ds_test, verbose=2)
print(f'Precisión en el conjunto de prueba: {accuracy}')

# Obtener las predicciones del modelo (las probabilidades)
probability_model = tf.keras.Sequential([model, tf.keras.layers.Softmax()])
test_images_batch, test_labels_batch = next(iter(ds_test))
predictions = probability_model.predict(test_images_batch)

def plot_image(i, predictions_array, true_label, img, class_names):
    true_label = true_label[i]
    predicted_label = tf.argmax(predictions_array[i])
    plt.grid(False)
    plt.xticks([])
    plt.yticks([])

    plt.imshow(img[i], cmap=plt.cm.binary)

    predicted_label = tf.argmax(predictions_array[i])
    if predicted_label == true_label:
        color = 'blue'
    else:
        color = 'red'

    plt.xlabel("{} {:2.0f}% ({})".format(class_names[predicted_label],
                                100*tf.reduce_max(predictions_array[i]),
                                class_names[true_label]),
                                color=color)

def plot_value_array(i, predictions_array, true_label):
    true_label = true_label[i]
    plt.grid(False)
    plt.xticks(range(10))
    plt.yticks([])
    thisplot = plt.bar(range(10), predictions_array[i], color="#777777")
    plt.ylim([0, 1])
    predicted_label = tf.argmax(predictions_array[i])

    thisplot[predicted_label].set_color('red')
    thisplot[true_label].set_color('blue')

# Mostrar algunas predicciones
num_rows = 5
num_cols = 3
num_images = num_rows*num_cols
plt.figure(figsize=(2*2*num_cols, 2*num_rows))
for i in range(num_images):
    plt.subplot(num_rows, 2*num_cols, 2*i+1)
    plot_image(i, predictions, test_labels_batch.numpy(), test_images_batch.numpy(), class_names)
    plt.subplot(num_rows, 2*num_cols, 2*i+2)
    plot_value_array(i, predictions, test_labels_batch.numpy())
plt.tight_layout()
plt.show()