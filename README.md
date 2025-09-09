# StepCounterApp
[![release v1.0.0-alpha](https://img.shields.io/badge/release-v1.0.0--alpha-blue)](https://github.com/Pjhn/StepCounter/releases/tag/v1.0.0-alpha)

안드로이드 기본 센서를 활용한 걸음 수 측정 앱

시작 버튼을 누르면 걸음 수 측정이 시작되며, 앱을 종료해도 백그라운드에서 계속 기록됩니다.  
또한 홈 화면 위젯으로 실시간 걸음 수를 확인할 수 있으며, 센서 민감도를 `Low` / `High`로 자유롭게 조절하여 배터리 전력 소모를 줄일 수 있습니다.


## Screenshots
<p align="center">
  <img src="https://github.com/user-attachments/assets/89c2d951-0eca-4519-99ed-ebe4725e9c0f" width="30%" />
  <img src="https://github.com/user-attachments/assets/41c8aa59-b40a-4603-82e0-05efd57afe9c" width="30%" />
  <img src="https://github.com/user-attachments/assets/6eeefc9f-2415-43b0-8503-f9bb321b2da2" width="30%" />
</p>


## Feature
- **걸음 수 측정**: 시작 버튼 클릭 시 측정 시작, 앱 종료 후에도 백그라운드에서 계속 기록
- **위젯 지원**: 홈 화면에서 실시간 걸음 수 확인 가능
- **민감도 조절**: 센서 민감도를 `Low` / `High`로 설정 가능
- **자정 초기화**: 메인 화면 걸음 수는 매일 **0시**에 자동 초기화
- **기록 화면**:
  - 차트(걸음 수, 칼로리 소모량, 이동 시간, 이동 거리)
  - 기간 별 통계(걸음 수, 칼로리 소모량, 이동 시간, 이동 거리)
  - 일일 걸음 목표 달성 현황 확인 가능
  - 커스텀 캘린더에서 목표 달성 기록 확인 가능 


## Library
- **MPAndroidChart**   (Copyright (C) 2018 PhilJay  
  Licensed under the Apache License, Version 2.0  
  https://github.com/PhilJay/MPAndroidChart)

