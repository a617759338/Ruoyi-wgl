FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 安装所有必须的字体/图形库
RUN apt-get update && apt-get install -y --no-install-recommends \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libfreetype6 \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

COPY ruoyi-gateway.jar app.jar

# 强制 headless 模式
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
