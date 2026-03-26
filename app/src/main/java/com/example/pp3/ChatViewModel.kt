package com.example.pp3

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arm.aichat.AiChat
import com.arm.aichat.InferenceEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File

data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    // Initialize the engine using the official ARM Factory method!
    private val inferenceEngine: InferenceEngine = AiChat.getInferenceEngine(application)

    private val _messages: MutableStateFlow<List<ChatMessage>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isModelLoaded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isModelLoaded: StateFlow<Boolean> = _isModelLoaded.asStateFlow()

    init {
        addSystemMessage("Click the Folder icon at the top right to import a .gguf model from your device.")

        // Optional: Listen to the engine's internal state
        viewModelScope.launch {
            inferenceEngine.state.collect { state ->
                Log.d("ChatViewModel", "Engine State: $state")
            }
        }
    }

    fun importAndLoadModel(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _isModelLoaded.value = false
            addSystemMessage("Copying model to secure app storage...\nPlease wait. This might take a minute.")

            try {
                val destFile = File(context.filesDir, "imported_model.gguf")

                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    destFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                addSystemMessage("Copy complete. Initializing ARM Inference Engine...")

                // The new API uses a safe Kotlin suspend function
                inferenceEngine.loadModel(destFile.absolutePath)

                _isModelLoaded.value = true
                addSystemMessage("Model loaded successfully! You can now start chatting.")

            } catch (e: Exception) {
                e.printStackTrace()
                _isModelLoaded.value = false
                addSystemMessage("Error loading model: ${e.localizedMessage}")
            }
        }
    }

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return

        val userMsg = ChatMessage(content = userText, isUser = true)
        _messages.update { currentList -> currentList + userMsg }

        val aiPlaceholder = ChatMessage(content = "...", isUser = false, isLoading = true)
        _messages.update { currentList -> currentList + aiPlaceholder }

        viewModelScope.launch(Dispatchers.IO) {
            var currentResponse = ""
            var isFirstToken = true

            try {
                // The new ARM API handles ChatML templates automatically!
                inferenceEngine.sendUserPrompt(userText)
                    .catch { e ->
                        Log.e("ChatViewModel", "Generation Error", e)
                        updateLastMessage("Error: ${e.message}")
                    }
                    .collect { token ->
                        if (isFirstToken) {
                            currentResponse = ""
                            isFirstToken = false
                        }
                        currentResponse += token
                        updateLastMessage(currentResponse)
                    }
            } catch (e: Exception) {
                updateLastMessage("Error: ${e.message}")
            }
        }
    }

    private fun updateLastMessage(newContent: String) {
        _messages.update { list ->
            if (list.isEmpty()) list
            else {
                val last = list.last()
                val updated = last.copy(content = newContent, isLoading = false)
                list.dropLast(1) + updated
            }
        }
    }

    private fun addSystemMessage(text: String) {
        _messages.update { currentList -> currentList + ChatMessage(text, isUser = false) }
    }

    override fun onCleared() {
        super.onCleared()
        // Safely destroy the engine when the app closes
        try {
            inferenceEngine.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}