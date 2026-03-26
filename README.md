# PP3

=============================================================================
LLAMA.CPP ANDROID CHAT APP - SETUP & USAGE GUIDE
=============================================================================

Welcome to the local AI chat app for Android! This project allows you to run local .gguf AI models completely offline on your phone using the official llama.cpp C++ engine with ARM KleidiAI optimizations.

Because the llama.cpp C++ library is massive and constantly updating, it has been excluded from this repository. You must download it yourself before the app will build.

Follow these exact steps to get the app running on your machine:


PHASE 1: THE PREREQUISITES
-----------------------------------------------------------------------------
1. Android Studio installed on your computer.
2. CMake version 3.31.6 installed. 
   To get this: Open Android Studio -> Tools -> SDK Manager -> SDK Tools tab -> check "Show Package Details" at the bottom right -> expand CMake -> check "3.31.6" -> click Apply.


PHASE 2: CLONE AND ADD LLAMA.CPP
-----------------------------------------------------------------------------
1. Clone this repository to your local machine.
2. Open your computer's terminal or command prompt.
3. Navigate INSIDE the root folder of this project that you just cloned.
4. Run this exact command to download the official llama.cpp library directly into the project:
   git clone https://github.com/ggerganov/llama.cpp.git
5. Make sure the downloaded folder is named exactly "llama.cpp" and is sitting directly inside the root directory of this project.


PHASE 3: BUILD THE APP
-----------------------------------------------------------------------------
1. Open the project in Android Studio.
2. Wait for the initial indexing, then click File > Sync Project with Gradle Files.
3. Once the sync finishes successfully, hit the green Play button to build and install the app on your Android phone.

IMPORTANT NOTE: The very first time you click Play, your computer's fans will spin up and it will take several minutes to build. Android Studio is compiling the raw C++ math engine from scratch. This massive CPU usage is completely normal and will only happen once.


PHASE 4: HOW TO USE THE APP
-----------------------------------------------------------------------------

1. Download a compatible .gguf model to your phone's internal storage.
   Highly recommended test models (search for these on Hugging Face):
   - Qwen2.5-0.5B-Instruct-Q4_K_M.gguf (Very fast, ~350MB)
   - Llama-3.2-1B-Instruct-Q4_K_M.gguf (Standard, ~800MB)


2. Open the app on your phone.
3. Tap the Folder icon in the top right corner.
4. Select the .gguf file you downloaded.
5. Wait a moment for the app to securely copy the file and initialize the ARM Inference Engine. 
6. Once the chat bubble says "Model loaded successfully", type your message and start chatting!

=============================================================================
