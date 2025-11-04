<div align="center">

## 🚀 Rick & Morty KMP Workshop

### _Learn Kotlin Multiplatform by building a real app_

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7.1-brightgreen)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>

---

## 📖 About This Project

This is a **hands-on workshop project** to learn **Kotlin Multiplatform (KMP)** by building a mobile app that displays characters from the [Rick & Morty API](https://rickandmortyapi.com/).

The app demonstrates modern Android/iOS development with:
- **Shared business logic** between platforms
- **MVVM architecture** with clean separation of concerns
- **Reactive state management** with StateFlow
- **RESTful API consumption** using Ktor
- **Modern UI** with Compose Multiplatform

---

## 🌿 Project Branches

This repository has **two main branches** for different purposes:

### 🟢 `main` Branch - Complete Application

The `main` branch contains the **fully implemented application** with all features working:

- ✅ Complete API integration with Rick & Morty API
- ✅ Full MVVM architecture implementation
- ✅ Repository pattern with error handling
- ✅ ViewModels with StateFlow
- ✅ Beautiful UI with Compose Multiplatform
- ✅ Navigation between screens
- ✅ Image loading with Coil
- ✅ Dependency injection with Koin
- ✅ Comprehensive English comments explaining every step

**Use this branch to:**
- 🔍 See the final result
- 📚 Learn from the complete implementation
- 🎓 Understand how all pieces work together
- 🐛 Debug and compare with your implementation

### 🔴 `unsolved` Branch - Workshop Base

The `unsolved` branch contains the **starting point for the workshop** with some parts removed:

- ⚠️ API methods to implement
- ⚠️ Repository logic to complete
- ⚠️ ViewModel functions to fill in
- ✅ Complete UI already built
- ✅ Project structure ready
- ✅ All dependencies configured
- ✅ TODOs marking where to work

**Use this branch to:**
- 🛠️ Practice implementing the missing parts
- 👨‍💻 Follow along during the workshop
- 🎯 Focus on core KMP concepts
- 💪 Build confidence with hands-on coding

---

## 🎯 What You'll Learn

By completing this workshop, you'll understand:

### Architecture & Patterns
- 🏗️ **MVVM Architecture** - Separation between UI and business logic
- 📦 **Repository Pattern** - Abstracting data sources
- 💉 **Dependency Injection** - Using Koin for DI
- 🔄 **Unidirectional Data Flow** - Reactive state management

### Kotlin Multiplatform
- 🔀 **Code Sharing** - Write once, run on Android & iOS
- 🧩 **Common & Platform-Specific** code structure
- 📱 **Compose Multiplatform** - Declarative UI across platforms

### Networking & Async
- 🌐 **HTTP Requests** - Using Ktor client
- ⚡ **Coroutines** - Asynchronous programming in Kotlin
- 🔄 **StateFlow** - Reactive state management
- 🛡️ **Error Handling** - Graceful failure management

### Modern Android/iOS Development
- 🎨 **Jetpack Compose** - Declarative UI
- 🖼️ **Image Loading** - Coil for async images
- 🧭 **Navigation** - Type-safe navigation
- 📐 **Material Design 3** - Modern UI components

---

## 🏗️ Project Structure

```
tutorial-kmp/
│
├── composeApp/                    # Main application module
│   ├── src/
│   │   ├── commonMain/            # Shared code for all platforms
│   │   │   └── kotlin/
│   │   │       └── com.jetbrains.kmpapp/
│   │   │           ├── data/      # Data layer (API, models, repository)
│   │   │           ├── screens/   # UI screens (list, detail)
│   │   │           ├── di/        # Dependency injection (Koin)
│   │   │           └── App.kt     # Main app & navigation
│   │   │
│   │   ├── androidMain/           # Android-specific code
│   │   └── iosMain/               # iOS-specific code
│   │
│   └── build.gradle.kts           # App dependencies
│
├── iosApp/                        # iOS app wrapper (Xcode project)
├── gradle/                        # Gradle configuration
└── README.md                      # You are here! 📍
```

---

## 🚀 Getting Started

### Prerequisites

**For Android:**
- ✅ Android Studio Hedgehog (2023.1.1) or later
- ✅ JDK 11 or higher
- ✅ Android SDK (API 24+)

**For iOS (optional, macOS only):**
- ✅ Xcode 15+
- ✅ CocoaPods installed
- ✅ macOS 12+

### Clone the Repository

```bash
# Clone the repository
git clone <repository-url>
cd tutorial-kmp

# For complete application (see the final result)
git checkout main

# For workshop base (hands-on practice)
git checkout unsolved
```

### Run on Android

```bash
# Using command line
./gradlew :composeApp:installDebug

# Or in Android Studio
# Open project > Select 'composeApp' configuration > Run
```

### Run on iOS (macOS only)

```bash
# Open iOS project in Xcode
open iosApp/iosApp.xcodeproj

# Select a simulator and press Run (⌘R)
```

---

## 📱 Features

### Character List Screen
- 📋 Displays 15 Rick & Morty characters
- 🖼️ Circular character images
- 🎨 Color-coded status (Alive/Dead/Unknown)
- 📍 Shows last known location
- ⚡ Loading indicator while fetching data
- 🔄 Pull to refresh (TODO)

### Character Detail Screen
- 🎭 Large character image
- 📊 Detailed information cards:
  - ✅ Status (with color coding)
  - 👤 Species and Gender
  - 🏷️ Type (if available)
  - 🌍 Origin location
  - 📍 Last known location
  - 📺 Number of episode appearances
- ◀️ Back navigation
- 📜 Scrollable content

---

## 🛠️ Tech Stack

### Core Technologies
| Technology                | Purpose                       | Version  |
|---------------------------|-------------------------------|----------|
| **Kotlin Multiplatform**  | Code sharing across platforms | 2.0.21   |
| **Compose Multiplatform** | Declarative UI framework      | 1.7.1    |
| **Ktor**                  | HTTP client for API calls     | 3.0.1    |
| **Kotlinx Serialization** | JSON parsing                  | 1.7.3    |
| **Coroutines**            | Asynchronous programming      | 1.9.0    |
| **StateFlow**             | Reactive state management     | Built-in |

### Libraries
| Library                | Purpose              |
|------------------------|----------------------|
| **Koin**               | Dependency injection |
| **Coil**               | Image loading        |
| **Navigation Compose** | Screen navigation    |
| **Material 3**         | UI components        |

---

## 🎓 Workshop Flow

### Step 1: Explore the Complete App
```bash
git checkout main
```
- Run the app and explore all features
- Read through the code and comments
- Understand the architecture and data flow

### Step 2: Start the Workshop
```bash
git checkout unsolved
```
- Follow the instructor's guidance
- Complete TODOs one by one
- Test your implementation after each step

### Step 3: Implement Core Features
1. **API Integration** - Connect to Rick & Morty API
2. **Repository** - Implement data fetching logic
3. **ViewModels** - Add state management
4. **Test** - Run and verify your implementation

### Step 4: Compare and Learn
- Compare your solution with `main` branch
- Understand different approaches
- Ask questions and discuss

---

## 🌐 API Reference

This project uses the **Rick & Morty API** - a free and open API.

**Base URL:** `https://rickandmortyapi.com/api`

**Endpoints used:**
- `GET /character/?page=1` - Get list of characters
- `GET /character/{id}` - Get single character by ID

**Documentation:** https://rickandmortyapi.com/documentation

---

## 📚 Learning Resources

### Official Documentation
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor Client](https://ktor.io/docs/client.html)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

### Tutorials & Guides
- [KMP Getting Started](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html)
- [Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)
- [StateFlow Guide](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

### Community
- [Kotlin Slack](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/kotlin-multiplatform)
- [Reddit r/Kotlin](https://www.reddit.com/r/Kotlin/)

---

## 🤝 Contributing

This is a workshop project, but contributions are welcome!

- 🐛 Found a bug? Open an issue
- 💡 Have an idea? Submit a pull request
- 📝 Improve documentation? Edit the README
- ⭐ Like the project? Give it a star!

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Authors

Created with ❤️ for the Kotlin Multiplatform community.

---

## 🙏 Acknowledgments

- **Rick & Morty API** - For the free and awesome API
- **JetBrains** - For Kotlin and Compose Multiplatform
- **The Kotlin Community** - For continuous support and resources

---

<div align="center">

### 🎉 Happy Coding! 🎉

_Learn by doing, build something awesome!_

**[⬆ Back to Top](#-rick--morty-kmp-workshop)**

</div>
