package com.example.jetpackcomposedemo.db

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.db.core.data.local.db.Student
import com.example.jetpackcomposedemo.db.presenter.MainActivityViewModel
import com.example.jetpackcomposedemo.db.ui.theme.JetPackComposePlaygroundTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DashboardActivity() : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
        ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        ), 1
        )
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isSplashScreen.value
            }
        }

        setContent {
            MainView()
        }

    }

    @Composable
    fun ImagePickerDemo(selectedImageUri: String?) {
       //  selectedImageUri by remember { mutableStateOf<Uri?>(null) }
   //    val selectedImageUri by remember { mutableStateOf<Uri?>(null) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            var imgBitmap: Bitmap? = null
            var bitmap: Bitmap? = null
            val imgFile = File(selectedImageUri!!)
            val painter = rememberImagePainter(
                data = imgFile,
                builder = {
                    crossfade(true) // Enable crossfade animation if needed
                }
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    // .background(MaterialTheme.colors.primary)
                    .clip(CircleShape)
                    .size(100.dp))
                  //  .clip(shape = RoundedCornerShape(90.dp)))

        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MainView(){

        var name by remember {
            mutableStateOf("name")
        }
        var lastname by remember {
            mutableStateOf("lastname")
        }
        var editname by remember {
            mutableStateOf("name")
        }
        var editlastname by remember {
            mutableStateOf("lastname")
        }
        var editImage by remember {
            mutableStateOf("image")
        }
        var editId by remember {
            mutableStateOf(0)
        }
        var showDialog by remember { mutableStateOf(false) }
        var editdialog by remember { mutableStateOf(false) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        var path by remember {
            mutableStateOf("path")
        }
        val contentResolver = contentResolver
        var inputStream: InputStream
        val pickImage = remember {
            path = ""
            activityResultRegistry.register("pickImageKey", ActivityResultContracts.GetContent()) { uri: Uri? ->
                // Handle the selected image URI
                // For simplicity, we just print the URI here
                println("Selected image URI: $uri")
                selectedImageUri = uri

                val inputStream: InputStream =
                    contentResolver.openInputStream(uri!!)!!


                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val tempFile = File.createTempFile(
                    "JPEG_${timeStamp}_", //prefix
                    ".jpg", //suffix
                    cacheDir//directory
                )
                path = tempFile.absolutePath
                // Copy the content of the input stream to the temporary file
                try {
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream.use { input ->
                            input.copyTo(outputStream)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        var searchText by remember { mutableStateOf("") }

        // Filter the list based on the search text
        val filteredList by remember(searchText) {
            derivedStateOf {
                viewModel.listStudent.filter { student ->
                    student.name.contains(searchText, ignoreCase = true)
                }
            }
        }
        JetPackComposePlaygroundTheme {
        /*    SearchBar { searchQuery ->
                //filteredItems = FilteredList(items, searchQuery)
            }*/
            TopAppBar(
                title = {
                    Text(text = "Room Database")
                },
                /*navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },*/
                backgroundColor = colorResource(id = R.color.purple_700),
                contentColor = Color.White,
                elevation = 2.dp
            )
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search by name") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(items = filteredList, key = { it.id }) { item ->
                //    ItemRow(item = item, onItemClick = onItemClick)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 5.dp, start = 15.dp, end = 15.dp),
                        backgroundColor = colorResource(id = R.color.purple_200),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Box (Modifier.background(colorResource(id = R.color.purple_200))){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                //      horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val imgFile = File(item.image)
                                val painter = rememberImagePainter(
                                    data = imgFile,
                                    builder = {
                                        crossfade(true) // Enable crossfade animation if needed
                                    }
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colors.primary)
                                        .size(60.dp))


                                Column(modifier = Modifier.padding(start = 15.dp)) {
                                    Text(
                                        text = item.name,
                                        color = Color.DarkGray,
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight(600),
                                        modifier = Modifier.animateItemPlacement(
                                            tween(1000, easing = LinearEasing)
                                        )
                                    )
                                    Text(
                                        text = item.lastName,
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        modifier = Modifier.animateItemPlacement(
                                            tween(1000, easing = LinearEasing)
                                        )
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = {
                                        editdialog = true
                                        editname = item.name
                                        editlastname = item.lastName
                                        path = item.image
                                        editId = item.id
                                        // viewModel.updateStudentData(it)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "",
                                            tint = Color.Blue
                                        )
                                    }

                                    IconButton(onClick = {
                                        viewModel.deleteStudent(item)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "",
                                            tint = Color.Red
                                        )
                                    }
                                }

                            }
                        }
                    }
                }

            }
        }

        Column(
            modifier = Modifier
                .padding(28.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.aligned(Alignment.Bottom),
            horizontalAlignment = Alignment.End,
        ) {

            FloatingActionButton(
                onClick = {
                    name = ""
                    lastname = ""
                    path = ""
                    showDialog = true

                }, backgroundColor = colorResource(id = R.color.purple_700),
            ) {
                Icon(Icons.Filled.Add, "Floating action button.", tint = colorResource(id = R.color.white))
            }
        }
        if(editdialog){
            EditDialog(  onDismissRequest = {
                // Dismiss the dialog
                     editdialog = false
            },
                onConfirmation = {
                    // Handle confirmation action
                    // You can also dismiss the dialog if needed
                      editdialog = false
                },
                onTextFieldValueChanged = {
                    // Handle TextField value change
                    editname = it
                },
                onSecondTextFieldValueChanged = {
                    editlastname = it
                },
                textFieldValue = editname,
                secondTextFieldValue = editlastname,
                path,
                pickImage,
                editId
            )
        }

        if (showDialog) {
            MyDialog(
                onDismissRequest = {
                    // Dismiss the dialog
                    showDialog = false
                },
                onConfirmation = {
                    // Handle confirmation action
                    // You can also dismiss the dialog if needed
                    showDialog = false
                },
                onTextFieldValueChanged = {
                    // Handle TextField value change
                    name = it
                },
                onSecondTextFieldValueChanged = {
                    lastname = it
                },
                textFieldValue = name,
                secondTextFieldValue = lastname,
                path,
                pickImage

            )
        }

    }

    @Composable
    fun EditDialog(
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
        onTextFieldValueChanged: (String) -> Unit,
        onSecondTextFieldValueChanged: (String) -> Unit,
        textFieldValue: String,
        secondTextFieldValue: String,
        selectedImageUri: String?,
        pickImage: ActivityResultLauncher<String>,
        editId: Int,
    ) {

        Dialog(
            onDismissRequest = onDismissRequest
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            )  {
                // Your other dialog content

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    ImagePickerDemo(selectedImageUri)
                    Button(
                        //  onClick = { this@MainActivity.pickImage.launch("image/*")},
                        onClick = {pickImage.launch("image/*")},
                        modifier = Modifier
                            .height(50.dp)
                            .padding(8.dp)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Pick Image")
                        }
                    }
                    TextField(value = textFieldValue,
                        textStyle = TextStyle(color = Color.Blue),
                        onValueChange = {
                            onTextFieldValueChanged.invoke(it)
                        })
                    TextField(value = secondTextFieldValue,
                        textStyle = TextStyle(color = Color.Blue),
                        onValueChange = {
                            onSecondTextFieldValueChanged.invoke(it)
                        })

                    Button(onClick = {
                        //viewModel.addStudent(textFieldValue, secondTextFieldValue,selectedImageUri.toString())
                        //  onConfirmation.invoke(firstTextFieldValue, secondTextFieldValue)
                        var stud = Student(editId,textFieldValue,secondTextFieldValue,selectedImageUri.toString())
                        viewModel.updateStudentData(stud)
                        onDismissRequest.invoke()
                    }) {
                        Text(text = "Submit")
                    }


                    //  ImagePickerDemo(pickImage = {pickImage.launch("image/*")})

                }
            }
        }
    }

    @Composable
    fun SearchBar(onSearch: (String) -> Unit) {
        var searchText by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
              //  .padding(28.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.aligned(Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearch(it)
                },
                label = { Text("Search") }
            )
        }
    }

    @Composable
    fun MyDialog(
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
        onTextFieldValueChanged: (String) -> Unit,
        onSecondTextFieldValueChanged: (String) -> Unit,
        textFieldValue: String,
        secondTextFieldValue: String,
        selectedImageUri: String?,
        pickImage: ActivityResultLauncher<String>,
    ) {
        var textInput by remember { mutableStateOf("") }
        var textInputSecond by remember { mutableStateOf("") }
        Dialog(
            onDismissRequest = onDismissRequest
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            )  {
                // Your other dialog content

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        ImagePickerDemo(selectedImageUri)
                        Button(
                            //  onClick = { this@MainActivity.pickImage.launch("image/*")},
                            onClick = {pickImage.launch("image/*")},
                            modifier = Modifier
                                .height(50.dp)
                                .padding(8.dp)

                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Pick Image")
                            }
                        }
                        TextField(value = textFieldValue,
                            textStyle = TextStyle(color = Color.Blue),
                            onValueChange = {
                                textInput = it
                                onTextFieldValueChanged.invoke(it)
                            },
                            label = { Text("Enter Text") },
                            isError = textInput.isNotEmpty() && !isValidText(textInput)

                        )
                        TextField(value = secondTextFieldValue,
                            textStyle = TextStyle(color = Color.Blue),
                            onValueChange = {
                                textInputSecond = it
                                onSecondTextFieldValueChanged.invoke(it)
                            },
                            label = { Text("Enter Text") },
                            isError = textInputSecond.isNotEmpty() && !isValidText(textInputSecond)
                        )

                        Button(onClick = {
                            if(!isValidText(textInput)){
                                Toast.makeText(this@DashboardActivity,"fill all fields",Toast.LENGTH_SHORT).show()
                               return@Button
                            }else if(!isValidText(textInputSecond)){
                                Toast.makeText(this@DashboardActivity,"fill all fields",Toast.LENGTH_SHORT).show()
                                return@Button
                            }else if(selectedImageUri!!.isEmpty()){
                                Toast.makeText(this@DashboardActivity,"fill all fields",Toast.LENGTH_SHORT).show()
                                return@Button
                            }else{
                                viewModel.addStudent(textFieldValue, secondTextFieldValue,selectedImageUri.toString())
                                onDismissRequest.invoke()

                            }
                          //  onConfirmation.invoke(firstTextFieldValue, secondTextFieldValue)

                        }) {
                            Text(text = "Submit")
                        }


                      //  ImagePickerDemo(pickImage = {pickImage.launch("image/*")})

                    }
            }
        }
    }
    fun isValidText(text: String): Boolean {
        // Add your custom validation rules here
        return text.matches(Regex("[a-zA-Z]+"))
    }

}