
import android.annotation.SuppressLint
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.protei.malkovaar.domain.Note
import ru.protei.malkovaar.ui.notes.NotesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(vm: NotesViewModel) {
    val addState = remember { mutableStateOf(false) } // состояние окна, false - редактирование, true - добавление
    val selected by remember { mutableStateOf(vm.selected) }
    val noteChange:() -> Unit = {vm.onNoteChange(selected.value!!.title,selected.value!!.text)}
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
        // если selected - пустой, то это автоматически будет добавление заметки
        if(selected.value != null) EditNote(noteChange, selected)
        else Notes(vm.notes, selected)
    }
}
@Composable
fun Notes(notes: List<Note>, selected: MutableState<Note?>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(6.dp)
            .background(MaterialTheme.colorScheme.background)
    ){
        items(notes) {note->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .clickable {
                        selected.value = note
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(noteChange: ()-> Unit, select: MutableState<Note?>){
    var text by remember { mutableStateOf(select.value!!.text) }
    var title by remember { mutableStateOf(select.value!!.title) }

    TextField(
        placeholder = {
            Text("Заголовок",
                color = MaterialTheme.colorScheme.onPrimaryContainer) },
        value = title,
        onValueChange = {
            title = it
            select.value!!.title = it
            noteChange()
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
            select.value!!.text = text
            noteChange()
        })
}
