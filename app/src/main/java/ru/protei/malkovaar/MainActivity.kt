package ru.protei.malkovaar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.protei.malkovaar.domain.Note
import ru.protei.malkovaar.ui.notes.NotesViewModel
import ru.protei.malkovaar.ui.theme.MalkovaarTheme

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MalkovaarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ){
                   // EditNote(notesViewModel)
                    NotesScreen(notesViewModel)
                }
            }
        }
    }
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(vm: NotesViewModel) {
    val addState = remember { mutableStateOf(false) } // состояние окна, false - редактирование, true - добавление
    val selected by remember { mutableStateOf(vm.selected) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    if(selected.value != null) Icon(Icons.Filled.Done, contentDescription = "Сохранить")
                    else Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = {
                    if(selected.value != null){
                        // сохраняем
                        if(addState.value) {
                            vm.onAddNoteClicked() // новая заметка
                            addState.value = !addState.value
                        }
                        else vm.onEditComplete() // отредактированная заметка
                    }else{
                        // Добавляем
                        selected.value = Note("","")
                        addState.value = !addState.value
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
    {
        if(selected.value != null){
            // если selected - пустой, то это автоматически будет добавление заметки
            EditNote(vm)
        }else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(6.dp)
                .background(MaterialTheme.colorScheme.background)
        ){
            items(vm.notes) {note->
                Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .clickable {
                        vm.onNoteSelected(note)
                    },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ){
                Text(
                    text = note.title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 10.dp,
                            end = 10.dp,
                            top = 10.dp,
                            bottom = 0.dp)
                )
                Text(
                    text = note.text,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 10.dp,
                            end = 10.dp,
                            top = 10.dp,
                            bottom = 10.dp)
                )

                }
            }
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(vm: NotesViewModel){
    var text by remember { mutableStateOf(vm.selected.value!!.text) }
    var title by remember { mutableStateOf(vm.selected.value!!.title) }

    TextField(
        placeholder = {
            Text("Заголовок",
                color = MaterialTheme.colorScheme.onPrimaryContainer) },
        value = title,
        onValueChange = {
            title = it
            vm.onNoteChange(it,vm.selected.value!!.text)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary),
        textStyle = MaterialTheme.typography.bodyLarge
            .copy(color = MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            .padding(
                top = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .height(50.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
    )
    OutlinedTextField(
        placeholder = {
            Text("Текст заметки",
                color = MaterialTheme.colorScheme.onBackground) },
        value = text,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
            unfocusedBorderColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .padding(
                top = 70.dp,
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxSize(),
        onValueChange ={
            text = it
            vm.onNoteChange(vm.selected.value!!.title,text)
    })
}

@Preview
@Composable
fun NotesScreen(){
}}

/*
git status - проверяет проект
git commit "" - добавляет коммит
git push origin master - пушит мастер-ветку в origin (в гитхаб по-сути)
https://github.com/amalkoott/Notes-for-Android.git

*/