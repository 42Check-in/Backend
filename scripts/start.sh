#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/github_action"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> "$DEPLOY_LOG"
cp "$PROJECT_ROOT/build/libs/"*.jar "$JAR_FILE"

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> "$DEPLOY_LOG"
nohup java -jar -Dspring.profiles.active=deploy "$JAR_FILE" > "$APP_LOG" 2> >(tee -a "$ERROR_LOG" >&2) &

# 프로세스 PID 가져오기
CURRENT_PID=$(pgrep -f "$JAR_FILE")
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> "$DEPLOY_LOG"

# 에러 로그 추출하여 error.log에 저장
echo "$TIME_NOW > 에러 로그 추출 중..." >> "$DEPLOY_LOG"
sed -n '/ERROR/p' "$APP_LOG" >> "$ERROR_LOG"
echo "$TIME_NOW > 에러 로그 추출 완료." >> "$DEPLOY_LOG"
