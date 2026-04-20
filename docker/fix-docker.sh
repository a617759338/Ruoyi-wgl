#!/bin/bash
set -e

echo "=== 修复 Docker 拉取超时问题 ==="

# 配置镜像加速
echo "1. 配置镜像加速器..."
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://docker.xuanyuan.me",
    "https://hub.rat.dev",
    "https://docker.1panel.live"
  ]
}
EOF

# 重启 Docker
echo "2. 重启 Docker..."
sudo systemctl daemon-reload
sudo systemctl restart docker

# 进入项目目录
cd /home/projects/ruoyi/docker

# 清理失败状态
echo "3. 清理失败状态..."
docker-compose down 2>/dev/null || true

# 测试拉取
echo "4. 测试拉取镜像..."
docker pull redis:latest

# 启动服务
echo "5. 启动所有服务..."
docker compose up -d --build
echo "=== 修复完成 ==="
docker-compose ps