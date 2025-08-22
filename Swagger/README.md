# Swagger UI 로컬 실행

Python 내장 서버로 Swagger UI(`index_final.html`)를 실행, 별도 설치가 필요 없고, Python만 있으면 바로 실행 가능

http://127.0.0.1:5500/index.html

* Python 3.x 이상 설치 필요
  (확인 방법: `python --version` 또는 `python3 --version`)

---

## 실행 방법

### macOS

```bash
./Mac.command
```

### Windows (CMD / PowerShell)

```bat
Window.bat
```

---

# IntelliJ + Spring Boot에서 Swagger UI 실행

1. 의존성 추가  
   - Gradle: `implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'`  

2. IntelliJ에서 서버 실행 (`▶ Run` 버튼 또는 `./gradlew bootRun`)

3. Swagger UI 접속  
   - 브라우저에서 `http://localhost:8080/swagger-ui.html`
