# AGENTS.md - GuessingGame Codebase Guide

## Project Overview
**GuessingGame** is an Android app illustrating a binary tree-based animal-guessing game using **MVVM architecture**. The app teaches a binary search decision tree through iterative gameplay where the AI learns new animals from user feedback.

---

## Architecture & Data Flow

### MVVM Pattern
- **View Layer**: `MainActivity` (container) + `MainActivityFragment` (UI logic)
- **ViewModel Layer**: `GameViewModel` (state management, business logic)
- **Model Layer**: `GameBoard` (game tree logic) + `Question` hierarchy (tree nodes)

**Critical Pattern**: The ViewModel persists across device rotations; use `activityViewModels()` delegate in fragments to access it.

### Binary Tree Structure (Core Algorithm)
The game stores decisions as a binary tree using an **interface hierarchy**:

```kotlin
// Node types in tree
Question (interface)
  ├─ Parent (has yes/no children)
  │  ├─ GameRoot (tree root)
  │  └─ GameQuestion (internal nodes - both parent and child)
  └─ Child (has parent reference)
     └─ GameGuess (leaf nodes - animal guesses)
```

**Key Behavior**: Tree grows when learning new animals. `addNewAnimal()` restructures the tree by inserting a new `GameQuestion` node between parent and old guess.

### Game State Machine
`GameViewModel` has 6 states (enum `GameState`):
1. `NOT_STARTED` → initial state
2. `SHOW_WELCOME_MESSAGE` → user preparation
3. `PLAY` → question loop
4. `FINISHED` → victory/loss
5. `SHOW_ADD_NEW_ANIMAL` → user inputs failed guess
6. `SHOW_ADD_NEW_ANIMAL_QUESTION` → user creates distinguishing question

**Navigation Pattern**: `MainActivityFragment.navigate()` acts as state-driven router—each state maps to a UI display method.

---

## UI Patterns & ViewBinding

### ViewBinding Setup
- **MainActivity**: Lazy-initialized `ActivityMainBinding`
- **MainActivityFragment**: Null-safe binding pattern (set `_binding = null` in `onDestroyView()`)

### Dialog Fragment Pattern
Custom factory function `newAlertDialogFragment()` creates reusable dialogs:
```kotlin
newAlertDialogFragment(
  title = String,
  showEditText = Boolean,  // toggles input field visibility
  onClickListener = DialogInterface.OnClickListener
)
```

Dialogs auto-dismiss on pause (`dismissAllowingStateLoss()` in `onPause()`).

---

## Testing Approach

### Unit Test Structure
File: `GameBoardUnitTest.java` uses **JUnit** with:
- **Mock setup**: HashMap-based answer injection
- **Test pattern**: Initialize GameBoard → newGame() → loop playA_Game() → assert final state

**Key Methods to Test**:
- `board.newGame()` - resets tree traversal to root
- `board.play(answer)` - navigates tree and detects victory
- `board.addNewAnimal()` - restructures tree with new branch

### Utility for Debugging
`PrintUtil.print(Question)` recursively prints the binary tree structure—use this when adding animals to verify tree integrity.

---

## Build & Development Commands

### Gradle Build
```bash
# Build debug variant
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run all tests with reports
./gradlew testDebugUnitTest
```

### Key Configuration
- **compileSdk/targetSdk**: 37 (latest in manifest)
- **minSdk**: 29
- **Kotlin Version**: 2.2.10
- **AGP Version**: 9.2.1
- **ViewBinding**: Enabled in build.gradle

### Dependencies
Minimal core set:
- `androidx.appcompat:appcompat:1.3.1`
- `com.google.android.material:material:1.4.0`
- `androidx.core:core-ktx:1.6.0`
- `androidx.fragment:fragment-ktx:1.3.6`

Testing: JUnit, Mockito, PowerMock (legacy—consider replacing with more modern mocking frameworks for new tests).

---

## Code Organization

```
app/src/main/java/com/example/rodrigo/guessinggame/
├── MainActivity.kt              # Activity container + ViewBinding
├── MainActivityFragment.kt      # Fragment with state-driven UI logic
├── GameViewModel.kt             # State enum + business logic
├── AlertDialogFragment.kt       # Custom DialogFragment + factory
└── model/
    ├── GameBoard.kt             # Tree traversal & learning logic
    └── Question.kt              # Node interface hierarchy
```

---

## Development Guidelines

### Adding New Features
1. **Game Logic**: Modify `GameBoard.kt` (tree manipulation)
2. **State Handling**: Add to `GameState` enum and handle in `navigate()`
3. **UI Display**: Create new method in `MainActivityFragment` following the `show*()` pattern

### Common Patterns

**Safe ViewModel Access**: Always use `activityViewModels()` in fragments—never pass ViewModel through bundle.

**State Persistence**: ViewModel survives rotation automatically; avoid storing mutable state in fragment fields.

**Tree Debugging**: Call `PrintUtil.print()` during tests to visualize tree after learning new animals—critical for validating parentage/hierarchy.

### Kotlin Conventions
- Use **val** by default; fragment bindings use backing field pattern (`_binding`)
- String templates for question formatting: `String.format(template, value)`
- Companion object for static log tags: `LOG_TAG = ClassName::class.java.simpleName`

---

## Key Files for Understanding Patterns

| File | Purpose |
|------|---------|
| `GameViewModel.kt` | State machine and game state enum—study `answer()` for state transitions |
| `GameBoard.kt` | Binary tree algorithm—understand `play()` and `addNewAnimal()` |
| `MainActivityFragment.kt` | UI-to-state mapping—each state method shows the ViewModel-driven pattern |
| `GameBoardUnitTest.java` | Test pattern—HashMap-driven playthrough validates tree logic |
| `Question.kt` | Node hierarchy interfaces—core data structure design |

---

## Tips for Agents

- **Before modifying tree logic**: Run `GameBoardUnitTest` to ensure consistency
- **State transitions**: Always verify the next state in `GameState` enum—navigate() handles all cases
- **Fragment lifecycle**: Remember `onResume()` in MainActivityFragment checks for pending questionText
- **Testing dialogs**: AlertDialogFragment intentionally dismisses on pause—test via MainActivity scenario tests if needed

