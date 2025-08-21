# 전제

* 앱 실행: `SPRING_DATASOURCE_URL/USERNAME/PASSWORD` 환경변수로 기동
* 베이스 URL: `http://localhost:8080`
* 세션 저장: `--session=me`

# 0) 기동 확인

```
http :8080/actuator/health
```

# 1) 회원가입 → 로그인(세션 획득)

```
http --session=me POST :8080/auth/signup \
  name=alice email=alice@example.com password='Aa1!aa!1'

http --session=me POST :8080/auth/login \
  email=alice@example.com password='Aa1!aa!1'
```

# 2) 내 정보 조회

```
http --session=me GET :8080/users/me
```

# 3) 공개 프로필 조회

```
http --session=me GET :8080/users/1
```

# 4) 내 정보 수정(PATCH /users/me)

```
http --session=me PATCH :8080/users/me \
  username=alice_new
```

# 5) 비밀번호 변경(PATCH /users/me/password)

## 실패 케이스

```
# 현재 비밀번호 불일치
http --session=me PATCH :8080/users/me/password \
  currentPassword='wrongpassword' newPassword='n3wp@ss5'

# 새 비밀번호가 현재와 동일
http --session=me PATCH :8080/users/me/password \
  currentPassword='Aa1!aa!1' newPassword='Aa1!aa!1'

# 형식 위반(길이 < 6)
http --session=me PATCH :8080/users/me/password \
  currentPassword='pass' newPassword='pass'
```

## 정상

```
http --session=me PATCH :8080/users/me/password \
  currentPassword='Aa1!aa!1' newPassword='n3wp@ss5'
```

# 6) 프로필 이미지 변경(PATCH /users/me/profile/image)

## 실패 케이스

```
# 공백( @NotBlank )
http --session=me PATCH :8080/users/me/profile/image \
  profileImage=''
```

## 정상

```
http --session=me PATCH :8080/users/me/profile/image \
  profileImage='https://img.example.com/u/alice.png'
```

# 7) 회원탈퇴(세션 필요)

```
@RequestBody
http --session=me DELETE :8080/auth/me \
  password='n3wp@ss'
  
---

@RequestParam
쿼리 스트링
http --session=me DELETE ':8080/auth/me?password=n3wp@ss5'

폼 파라미터
http --session=me --form DELETE :8080/auth/me password='n3wp@ss5'
```

# 예상 상태코드 요약

* `GET /users/me` → 200
* `GET /users/{id}` → 200
* `PATCH /users/me` → 200
* `PATCH /users/me/password` → 204 | 400
* `PATCH /users/me/profile/image` → 200 | 400
* `POST /auth/signup` → 200/201
* `POST /auth/login` → 200
* `DELETE /auth/me` → 200

# 체크리스트

* 세션 쿠키 사용 여부(`--session=me`)
* DTO 검증 실패 시 400 발생 여부
* `BadRequestException` 발생 시 일관 응답 포맷
* DB 연결 및 트랜잭션 롤백 로그 확인

