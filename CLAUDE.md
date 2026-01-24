# Neki Project Guidelines

## Project Overview
- Android 앱 프로젝트 (Kotlin + Jetpack Compose)
- Multi-module 아키텍처 (feature api/impl 분리)
- Hilt DI + Navigation3
- MVI 패턴 (State, Intent, Effect)

## Module Structure
```
app/                          # 앱 진입점
build-logic/                  # Convention Plugins
core/
  ├── common/                 # 공통 유틸리티
  ├── data/                   # Repository 구현체
  ├── data-api/               # Repository 인터페이스
  ├── designsystem/           # 디자인 시스템 (Theme, Component)
  ├── domain/                 # UseCase
  ├── model/                  # 도메인 모델
  ├── navigation/             # Navigator, NavKey 베이스
  └── ui/                     # 공통 UI 유틸 (MviIntentStore 등)
feature/{name}/
  ├── api/                    # NavKey, navigate 확장함수
  └── impl/                   # Screen, ViewModel, Contract, Component
```

## MVI Pattern
- **State**: UI 상태 (`XxxState`)
- **Intent**: 사용자 액션 (`XxxIntent`)
- **Effect**: 일회성 이벤트 - Toast, Navigation 등 (`XxxEffect`)
- ViewModel에서 `MviIntentStore` 사용

## Naming Convention

### Files
- `XxxScreen.kt` (Route + Screen 함수)
- `XxxViewModel.kt`
- `XxxContract.kt` (State, Intent, Effect)
- `XxxNavKey.kt`, `XxxEntryProvider.kt`

### Variables
- Dialog/BottomSheet 표시: `isShowXxx`
- 클릭 Intent: `ClickXxx`
- 상태 변경 Intent: `ChangeXxx`
- 화면 진입 Intent: `EnterXxxScreen`
- Navigation 함수: `navigateToXxx()`
- 리소스 변수: `xxxRes`

## Code Style
- Indent: 4 spaces
- Trailing comma 사용
- ImmutableList 사용 (`kotlinx.collections.immutable`)

## Commit Message
```
[type] #issue-number: 설명
```
- `[feat]`, `[fix]`, `[docs]`, `[refactor]`, `[chore]`

## Build Commands
- 빌드: `./gradlew assembleDebug`
- Detekt: `./gradlew detekt`

## Custom Rules
<!-- 여기에 추가 규칙을 작성하세요 -->
