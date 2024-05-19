package com.example.tareapizza.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tareapizza.ui.theme.TareaPizzaTheme

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TareaPizzaTheme {
        Contenido()
    }
}

@Composable
fun Informacion() {
    // Declaración
    val tamanio = listOf("8-10 plgs" to 5.0, "11-13 plgs" to 10.0, "18 plgs" to 15.0)
    val ingredientes = listOf("Piña" to 1.0, "Jamón" to 1.5, "Peperoni" to 2.0, "Hongos" to 1.0)

    var opTamanio by remember { mutableStateOf<String?>(null) } //se declaran
    var opIngrediente by remember { mutableStateOf<String?>(null) }
    var total by remember { mutableStateOf(0.0) }

    // Lógica
    val Total: () -> Unit = {
        val tamanioPrecio = tamanio.find { it.first == opTamanio }?.second ?: 0.0
        val ingredientePrecio = ingredientes.find { it.first == opIngrediente }?.second ?: 0.0
        total = tamanioPrecio + ingredientePrecio
    }

    // Encabezado
    Text(text = "Venta de Pizza", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
    Spacer(modifier = Modifier.padding(20.dp))

    //Muestra ambas columnas juntas
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ){
        // Columna de Tamaño
        Column(modifier = Modifier.padding(40.dp)) {
            Text(text = "Tamaño", fontSize = 16.sp,fontWeight = FontWeight.Bold)
            tamanio.forEach { (tamanio, _) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = tamanio == opTamanio,
                        onClick = {
                            opTamanio = tamanio
                            Total()
                        }
                    )
                    Text(text = tamanio)
                }
            }
        }

        // Columna de Ingredientes
        Column(modifier = Modifier.padding(30.dp)) {
            Text(text = "Ingredientes", fontSize = 16.sp,fontWeight = FontWeight.Bold)
            ingredientes.forEach { (ingrediente, _) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = ingrediente == opIngrediente,
                        onClick = {
                            opIngrediente = ingrediente
                            Total()
                        }
                    )
                    Text(text = ingrediente)
                }
            }
        }
    }

    // Total
    Text(text = "Total: $${"%.2f".format(total)}", fontSize = 24.sp)
    Spacer(modifier = Modifier.height(16.dp))

    //Botón de Pagar
    val context = LocalContext.current
    Button(onClick = {
        Toast.makeText(
            context,
            "Pizza: $opTamanio\nTotal: $${"%.2f".format(total)}",
            Toast.LENGTH_LONG
        ).show()
    }) {
        Text(text = "Pagar")
    }
}