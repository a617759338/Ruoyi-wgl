#!/bin/bash

# ===================== 自动计算路径（无需修改！）=====================
# 脚本所在目录：当前就是 Ruoyi-wgl 项目根目录
PROJECT_ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd)

# Docker 部署目录：Ruoyi-wgl/docker
DOCKER_DIR="${PROJECT_ROOT}/docker"
# ===================================================================

echo "正在复制所有模块 jar 包到 docker 目录..."
echo "📂 项目路径：${PROJECT_ROOT}"
echo "📂 目标路径：${DOCKER_DIR}"

# 复制模块 JAR
cp "${PROJECT_ROOT}/ruoyi-auth/target/"*.jar                  "${DOCKER_DIR}/auth/ruoyi-auth.jar"
cp "${PROJECT_ROOT}/ruoyi-gateway/target/"*.jar               "${DOCKER_DIR}/gateway/ruoyi-gateway.jar"
cp "${PROJECT_ROOT}/ruoyi-modules/ruoyi-system/target/"*.jar  "${DOCKER_DIR}/system/ruoyi-system.jar"
cp "${PROJECT_ROOT}/ruoyi-modules/ruoyi-gen/target/"*.jar     "${DOCKER_DIR}/gen/ruoyi-gen.jar"
cp "${PROJECT_ROOT}/ruoyi-modules/ruoyi-file/target/"*.jar    "${DOCKER_DIR}/file/ruoyi-file.jar"
cp "${PROJECT_ROOT}/ruoyi-modules/ruoyi-job/target/"*.jar     "${DOCKER_DIR}/job/ruoyi-job.jar"

# ===================== 先删除旧 SQL 文件 =====================
echo "🗑️  清空 mysql/init 旧 SQL 文件..."
rm -f "${DOCKER_DIR}/mysql/init/"*.sql

# 复制新 SQL 初始化文件
echo "📥 复制新 SQL 文件..."
cp "${PROJECT_ROOT}/sql/ry-cloud"*.sql    "${DOCKER_DIR}/mysql/init/"
cp "${PROJECT_ROOT}/sql/ry-config.sql"    "${DOCKER_DIR}/mysql/init/"

echo -e "\n✅ 所有 jar 包和 SQL 文件复制完成！"