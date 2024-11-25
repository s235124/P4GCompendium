package com.example.p4g.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.p4g.GOLDEN_COLOR
import com.example.p4g.R
import com.example.p4g.model.Persona
import com.example.p4g.model.Resistances
import com.example.p4g.model.Skills

//Kørsel af selve skærmen
@Composable
fun KarakterScreen(persona: Persona?, onNavigateBack: () -> Unit) {

    if (persona != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp), //luft mellem elementer
        ) {
            item { Title(persona.name, persona.img) } //titel øverst
            item { KarakterBaseInfo(persona.race, persona.level, persona.inherits) } //grundlæggende karakterinfo nedenfor
            item { StatsSection(persona.stats) }
            item { ResistanceSection(personaResistanceToResistanceObject(persona.resists)) }
            item { SkillsSection(persona.skills) }
        }
    }
}

@Composable
fun Title(name : String, img : Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) //runde kanter
            .background(Color(GOLDEN_COLOR))
            .padding(16.dp), //indvendig afstand
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Align children in the center
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.Black
            )
            Image(
                painter = painterResource(img),
                contentDescription = "Persona: $name",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
fun KarakterBaseInfo(arcana : String, level : Int, primaryElement: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween, //mellemrum mellem kolonner
        verticalAlignment = Alignment.Bottom
    ) {
        //1. kolonne: Arcana
        Column(
            modifier = Modifier.weight(1f), //giver lige meget plads til hver kolonne
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9F)
                    .height(66.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Arcana",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Luft mellem bokse

            Box(Modifier
                .fillMaxWidth(0.9F) //bredde
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = arcana,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        //2. kolonne: Lvl
        Column(
            modifier = Modifier.weight(1f), // Giver lige meget plads til hver kolonne
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.9F)
                    .height(66.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Level",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Luft mellem bokse

            Box(
                Modifier
                    .fillMaxWidth(0.9F)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        //3. kolonne: Primary element
        Column(
            modifier = Modifier.weight(1f), // Giver lige meget plads til hver kolonne
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.9F)
                    .height(66.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Primary Element",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Luft mellem bokse

            Box(
                Modifier
                    .fillMaxWidth(0.9F)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = primaryElement,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Composable
fun StatsSection(stats : ArrayList<Int>) {
    var i = 0
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
                .background(Color(GOLDEN_COLOR))
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
                StatBox(label = "Strength", value = stats[i++].toString())
                StatBox(label = "Magic", value = stats[i++].toString())
                StatBox(label = "Endurance", value = stats[i++].toString())
            }

            //2. række
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatBox(label = "Agility", value = stats[i++].toString())
                StatBox(label = "Luck", value = stats[i++].toString())
            }
        }
    }
}

@Composable
fun StatBox(label: String, value: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
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
                .background(Color(GOLDEN_COLOR))
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
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Physical",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.phys),
                        contentDescription = "Physical Affinities",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Almighty",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.almighty),
                        contentDescription = "Almighty Affinities",
                        modifier = Modifier.size(30.dp)
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
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Fire",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.fire),
                        contentDescription = "Fire Affinities",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ice",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.ice),
                        contentDescription = "Ice Affinities",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Electricity",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.elec),
                        contentDescription = "Elec Affinities",
                        modifier = Modifier.size(30.dp)
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
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Wind",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.wind),
                        contentDescription = "Wind Affinities",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Light",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.bless),
                        contentDescription = "Bless Affinities",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(GOLDEN_COLOR))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dark",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )

                    Image(
                        painter = painterResource(R.drawable.curse),
                        contentDescription = "Curse Affinities",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(resistances.wind, resistances.light, resistances.dark).forEach { value ->
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

@Composable
fun SkillsSection (skills: ArrayList<Skills>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(GOLDEN_COLOR))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Skills",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Name", "Level").forEachIndexed { index, title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(GOLDEN_COLOR))
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

        skills.forEach { skill ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
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
                        text = skill.skillName,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val l = if (skill.gainedAtLevel < 1.0) "Innate" else skill.gainedAtLevel.toInt().toString()
                    Text(
                        text = l,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun personaResistanceToResistanceObject (resistStr : String): Resistances {
    val resArr = ArrayList<String>()

    for (character in resistStr) {
        when (character) {
            's' -> resArr.add("Resists")
            'S' -> resArr.add("None")
            'w' -> resArr.add("Weak")
            'n' -> resArr.add("Nullifies")
            'd' -> resArr.add("Absorbs")
            'r' -> resArr.add("Repels")
            else -> resArr.add("None")
        }
    }
    return Resistances(resArr)
}
