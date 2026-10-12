package com.example.inventarioindustrial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.example.inventarioindustrial.ui.theme.InventarioIndustrialTheme

data class Material(
    val codigo: String,
    val descripcion: String,
    val unidad: String,
    val precio: String,
    val imagen: Int
)

val materiales = listOf(
    Material("0023NE", "Cable 1/0 AWG centeflex", "m", "$45.522", R.drawable.cable_1),
    Material("0024NE", "Cable 2 AWG centeflex", "m", "$30.659", R.drawable.cable_2),
    Material("0025NE", "Cable 2/0 AWG centeflex", "m", "$54.536", R.drawable.cable_3),
    Material("0026NE", "Cable 4 AWG centeflex", "m", "$16.479", R.drawable.cable_4),
    Material("0027NE", "Cable 4/0 AWG centeflex", "m", "$84.994", R.drawable.cable_5)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventarioIndustrialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    InventarioIndustrialApp()
                }
            }
        }
    }
}

@Composable
fun InventarioIndustrialApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Maestro de Materiales",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Listado general de materiales",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text("Buscar materiales")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Materiales",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        for (material in materiales) {
            MaterialCard(material)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun MaterialCard(material: Material) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = material.imagen),
                contentDescription = material.descripcion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Text(
                text = material.descripcion,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Código: ${material.codigo}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${material.precio} / ${material.unidad}",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE65100),
                        contentColor = Color.White)
                ) {
                    Text("Ver detalles")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventarioIndustrialAppPreview() {
    InventarioIndustrialTheme {
        InventarioIndustrialApp()
    }
}
