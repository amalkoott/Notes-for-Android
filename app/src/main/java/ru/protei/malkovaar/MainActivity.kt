package ru.protei.malkovaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.protei.malkovaar.domain.Note
import ru.protei.malkovaar.ui.theme.MalkovaarTheme

class MainActivity : ComponentActivity() {
    private var notes = listOf(
        Note("Grimace","Пост в твиттер про памп sundae и grimace (с хештегом #Grimace100x!!!)"),
        Note("Изоляция транзакций","есть уровни - то, насколько сильно влияют друг на друга параллельно выполняющиеся транзакции"),
        Note("Что покушать","Лазанья, сырники, тбилиси + мороженка, несколько фруктов"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MalkovaarTheme {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                ) {
                    PrintNotes(notes)
                }
            }
        }
    }


@Composable
fun PrintNotes(notes: List<Note>, modifier: Modifier = Modifier) {
    notes.forEach {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = it.title,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
            )
            Text(
                text = it.text,
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        MalkovaarTheme {
            Column (modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,){
                PrintNotes(notes)
            }
        }
    }
}