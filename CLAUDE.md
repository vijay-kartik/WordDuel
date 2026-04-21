# WordDuel — Android Project

Multiplayer Wordle-like mobile game. Players create rooms, challenge friends with curated or random words, and compete on leaderboards. Full product spec in `/design/WordDuel-PRD.docx`; architecture spec in `/design/WordDuel-TDD.docx`.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose (Android-only for now; CMP migration planned for V3) |
| Architecture | MVI (Model-View-Intent) — unidirectional data flow |
| DI | Hilt |
| Navigation | Compose Navigation |
| Backend | Supabase (PostgreSQL + Auth + Realtime + Edge Functions) |
| Network | Ktor client |
| Local DB | Room |
| Push | Firebase Cloud Messaging (FCM) |
| State | Kotlin Flows + ViewModel |
| Testing | JUnit4, MockK, Turbine |

---

## Package Name

`com.vkartik.wordduel`

---

## Project Structure

Follows the feature-first layout defined in the TDD:

```
app/src/main/java/com/vkartik/wordduel/
├── ui/
│   ├── auth/           # Sign-up / Login screens
│   ├── home/           # Home Dashboard
│   ├── room/           # Create/Join Room, Room View & Leaderboard
│   ├── challenge/      # Create Challenge screen
│   ├── gameplay/       # Gameplay screen (guess tiles, keyboard)
│   ├── result/         # Result Win / Result Lose screens
│   ├── repository/     # Word Repository screen
│   └── components/     # Shared Compose components
├── domain/
│   ├── model/          # Pure data classes (no Android/Supabase deps)
│   └── usecase/        # One class per use case (e.g. CreateChallengeUseCase)
├── data/
│   ├── remote/         # Supabase API clients (Ktor)
│   ├── local/          # Room database, DAOs, entities
│   ├── repository/     # Repository implementations
│   └── sync/           # Offline sync queue
├── di/                 # Hilt modules
└── util/               # Dictionary loader, constants, date helpers
```

Each feature folder contains: `Screen.kt`, `ViewModel.kt`, `State.kt`, `Intent.kt`, `Effect.kt`.

---

## MVI Pattern

Every screen follows strict MVI. No exceptions.

```
┌─────────┐  Intent  ┌───────────┐  State   ┌────────┐
│  Screen │ ──────► │ ViewModel │ ──────►  │  UI   │
└─────────┘          └───────────┘          └────────┘
                           │ Effect (one-shot: navigation, toast)
```

- **State** — immutable data class rendered by the Compose screen. Only one `UiState` per screen.
- **Intent** — sealed class of all user actions / events the screen can emit.
- **Effect** — `Channel`-backed `SharedFlow` for one-shot events (navigate, show snackbar). Never put navigation in State.
- **ViewModel** — receives Intents, calls use cases, emits State and Effects via `StateFlow` / `SharedFlow`.

Example skeleton:

```kotlin
// State.kt
data class HomeUiState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

// Intent.kt
sealed interface HomeIntent {
    data object LoadRooms : HomeIntent
    data class OpenRoom(val roomId: String) : HomeIntent
}

// Effect.kt
sealed interface HomeEffect {
    data class NavigateToRoom(val roomId: String) : HomeEffect
    data class ShowError(val message: String) : HomeEffect
}

// ViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _effects = Channel<HomeEffect>(Channel.BUFFERED)
    val effects: Flow<HomeEffect> = _effects.receiveAsFlow()

    fun handleIntent(intent: HomeIntent) { ... }
}
```

---

## Architecture Rules

- **Domain layer has zero Android/Supabase/Room imports.** Use cases and models are pure Kotlin.
- **Repositories** are interfaces in `domain/`, implemented in `data/`. ViewModels depend on the interface, never the implementation.
- **Use cases** are single-responsibility. One public `operator fun invoke(...)` returning a `Flow<Result<T>>` or `Result<T>`.
- **Scoring and burn validation always happen server-side** (Supabase Edge Functions). Never compute points or validate burns in the client.
- **Challenge words are never stored in client state** until the attempt begins — do not pre-fetch or cache the word on the guesser's device.

---

## Coding Conventions

- Follow [Kotlin official coding conventions](https://kotlinlang.org/docs/coding-conventions.html).
- `data class` for all state and domain models. No mutable fields.
- Prefer `sealed interface` over `sealed class` for Intent and Effect.
- Use `Result<T>` for all repository return types to propagate errors explicitly.
- No coroutine scope leaks — always launch in `viewModelScope` or a Hilt-scoped coroutine scope.
- No hardcoded strings in Compose screens — use `stringResource()`.
- No raw SQL in app code — use Room DAOs.
- Use `object` for singleton use cases with no state; `class` when injected dependencies exist.

---

## Dependency Injection (Hilt)

- Every ViewModel is annotated `@HiltViewModel` with `@Inject constructor`.
- Modules live in `di/`. One module file per layer: `DatabaseModule`, `NetworkModule`, `RepositoryModule`.
- Use `@Singleton` for repositories and network clients. Use `@ViewModelScoped` sparingly.
- Never inject `Context` directly into ViewModels — use `@ApplicationContext` at the repository/module level.

---

## Navigation

- Single `NavHost` in `MainActivity`. All routes defined as a `sealed class` or `object` in a top-level `navigation/AppDestinations.kt`.
- Navigate via Effects emitted from the ViewModel — never call `navController` directly inside a ViewModel.
- Pass only IDs between screens (e.g. `roomId: String`), not full objects.

---

## Testing Requirements

### Unit Tests (required for all)
- Every **ViewModel** — test each Intent, verify State emissions and Effects using `Turbine`.
- Every **use case** — test success, failure, and edge cases. No fakes of fakes; mock at the repository boundary using MockK.
- Every **utility function** (dictionary loader, scoring, burn checks).

### Integration Tests (required for repositories)
- Every **repository implementation** in `data/repository/` must have integration tests against a real local database (Room in-memory) or a Supabase test project.
- Use `@RunWith(AndroidJUnit4::class)` for Room integration tests.

### Testing Conventions
```kotlin
// ViewModel test structure
@Test
fun `given rooms loaded, when LoadRooms intent, then state contains rooms`() = runTest {
    // Given
    val rooms = listOf(fakeRoom())
    every { getRoomsUseCase() } returns flowOf(Result.success(rooms))

    // When
    viewModel.handleIntent(HomeIntent.LoadRooms)

    // Then
    viewModel.state.test {
        assertEquals(rooms, awaitItem().rooms)
    }
}
```
- Test function names use backtick `given/when/then` format.
- No `Thread.sleep()` — use `runTest` and `Turbine`.
- MockK over Mockito everywhere.

---

## Git Workflow

**Default branch:** `main`

**Branch naming:**
```
feature/<short-description>   # new feature development
fix/<short-description>       # bug fix or patch
refactor/<short-description>  # code refactoring
```

**Commit rules:**
- Build must pass (`./gradlew assembleDebug`) before committing.
- Relevant unit and integration tests must pass before committing.
- Use conventional commit messages:
  ```
  feat: add room creation screen
  fix: resolve crash on empty word repository
  refactor: extract burn validation to use case
  test: add unit tests for CreateChallengeUseCase
  ```
- Never commit directly to `master`. All changes go through a branch and are merged via PR.

---

## MVP Scope (Build This First)

The 9 screens to build for MVP, in dependency order:

1. **Sign Up / Login** (`ui/auth/`)
2. **Home Dashboard** (`ui/home/`)
3. **Create / Join Room** (`ui/room/`)
4. **Room View & Leaderboard** (`ui/room/`)
5. **Word Repository** (`ui/repository/`)
6. **Create Challenge** (`ui/challenge/`)
7. **Gameplay** (`ui/gameplay/`)
8. **Result — Win** (`ui/result/`)
9. **Result — Lose** (`ui/result/`)

Screens explicitly **out of scope for MVP**: Splash, Onboarding, Profile & Stats, Settings.

---

## Key Product Rules (Enforce in Code)

- A user can have **at most 1 active challenge** at a time. Multiple queued challenges are allowed.
- Challenges expire after **24 hours**. Expiry is computed server-side via a cron Edge Function.
- A word burned per-recipient (globally) or per-room can **never** be reused. Validated server-side.
- Room capacity: **max 8 members**.
- Rooms with no activity for **7 days** are auto-archived. Archived rooms are invisible to all members.
- All word validation (guesses + add-word) uses the **bundled dictionary** (~50KB text file). No external API.
- Points are **always computed server-side**. Never trust client-reported scores.
- Challenge words are never sent to the guesser's device before the attempt begins.

---

## Design System

| Token | Value |
|---|---|
| Primary colour | `#7C3AED` (Purple) |
| Secondary colour | `#F97316` (Orange) |
| Accent colour | `#FACC15` (Yellow) |
| Typography | Inter (Bold 700, SemiBold 600, Medium 500, Regular 400) |
| Corner radius | Rounded (use `RoundedCornerShape`) |
| Platform target | Android (iPhone 17 / 393×852pt reference for design; adapt to Android dp) |

Material 3 theming is set up. Map brand colours to M3 colour roles (`primary`, `secondary`, `tertiary`).

---

## References

| Document | Path |
|---|---|
| PRD v1.9 | `/design/WordDuel-PRD.docx` |
| TDD v1.0 | `/design/WordDuel-TDD.docx` |
| Design System | `/design/WordDuel-Design-System.docx` |
| Figma Wireframes | https://www.figma.com/design/yzDJxpLlUDnMfb6RF8PF7k/Untitled |
