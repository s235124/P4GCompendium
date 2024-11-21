package com.example.p4g

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Resistances(
    val physical: String,
    val almighty: String,
    val fire: String,
    val ice: String,
    val electricity: String,
    val wind: String,
    val dark: String,
    val light: String
)

//objekt af Resistance for test
val resistanceValues = Resistances(
    physical = "Yes",
    almighty = "No",
    fire = "Yes",
    ice = "Yes",
    electricity = "Weak",
    wind = "Resistant",
    dark = "Resistant",
    light = "Yes"
)

//Kørsel af selve skærmen
@Composable
fun KarakterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) //luft mellem elementer
    ) {
        Title() //titel øverst
        KarakterBaseInfo() //grundlæggende karakterinfo nedenfor
        StatsSection()
        ResistanceSection(resistanceValues)
    }
}

//Preview af selve skærmen
@Preview(showBackground = true)
@Composable
fun PreviewKarakterScreen() {
    KarakterScreen()
}



@Composable
fun Title() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) //runde kanter
            .background(Color.Yellow)
            .padding(16.dp)//indvendig afstand
            ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sandman",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.Black
        )
    }
}



@Composable
fun KarakterBaseInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween //mellemrum mellem kolonner
    ) {
        //1. kolonne: Arcana
        Column(
            modifier = Modifier.weight(1f) //giver lige meget plads til hver kolonne
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp) //fast bredde
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Yellow)
                    .padding(8.dp)
                , contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Arcana",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Luft mellem bokse

            Box(Modifier
                .width(100.dp) //bredde
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Strength",
                    color = Color.Black
                )
            }
        }

        //2. kolonne: Lvl
        Column(
            modifier = Modifier.weight(1f) // Giver lige meget plads til hver kolonne
        ) {
            Box(
                Modifier
                    .width(60.dp) //bredde
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Yellow)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lvl",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Luft mellem bokse

            Box(
                Modifier
                    .width(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "5",
                    color = Color.Black
                )
            }
        }

        //3. kolonne: Price
        Column(
            modifier = Modifier.weight(1f) //giver lige meget plads til hver kolonne
        ) {
            Box(
                Modifier
                    .width(150.dp) //fast bredde
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Yellow)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Price",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) //luft mellem bokse

            Box(
                modifier = Modifier
                    .width(150.dp) //fast bredde
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2484",
                    color = Color.Black
                )
            }
        }
    }
}



@Composable
fun StatsSection() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .padding(5.dp)
        ) {
            //stats titel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Yellow)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Stats",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) //luft mellem header og indhold

            //stats Row (2 rækker med stats)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //1. række
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatBox(label = "Strength", value = "4")
                    StatBox(label = "Magic", value = "5")
                    StatBox(label = "Endurance", value = "6")
                }

                //2. række
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatBox(label = "Agility", value = "4")
                    StatBox(label = "Luck", value = "3")
                }
            }
        }
    }

    @Composable
    fun StatBox(label: String, value: String) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally //centrerer teksten horisontalt
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = value,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }



@Composable
fun ResistanceSection(resistances: Resistances) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .padding(8.dp)
    ) {
        //Overskrift for hele sektionen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Yellow)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Resistances",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) //Luft mellem overskrift og første række

        //Første række: Physical og Almighty
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Physical", "Almighty").forEachIndexed { index, title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Yellow)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) //Luft mellem titler og grå værdier

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(resistances.physical, resistances.almighty).forEach { value ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Luft mellem rækker

        //Anden række: Fire, Ice, Electricity
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Fire", "Ice", "Electricity").forEach { title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Yellow)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(resistances.fire, resistances.ice, resistances.electricity).forEach { value ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Tredje række: Wind, Dark, Light
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Wind", "Dark", "Light").forEach { title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Yellow)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(resistances.wind, resistances.dark, resistances.light).forEach { value ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value,
                        color = Color.Black
                    )
                }
            }
        }
    }
}


