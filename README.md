项目运行与调试说明
=================

快速开始（前端）

1. 进入项目前端目录并安装依赖：

```bash
cd "community-management-web"
npm install
```
2. 启动开发服务器：
```bash
npm run dev
# Disease Management Platform

智慧社区疫情与生活综合管理系统（Disease Management Platform）。本仓库包含后端多个微服务模块和一个基于 Vite 的前端项目（`community-management-web`）。

## 项目结构（摘要）
- `community-management-system/`：后端父工程（Maven），含多个服务模块（`eureka-server`、`gateway`、`health-service`、`info-service`、`life-service`、`common` 等）。
- `community-management-web/`：前端（Vite + Vue）
- `db/`：初始化 SQL 脚本
- 项目根含需求与 UI 设计文档等 Markdown 文件

## 快速开始

- 前端（开发）
```bash
cd community-management-web
npm install
npm run dev
```

- 后端（构建）
```bash
# 在项目根执行，跳过测试以加快速度
mvn -T1C -DskipTests package
# 或单模块运行示例：
cd community-management-system
mvn -DskipTests package
```

## 部署/运行（简要）
1. 启动注册中心（`eureka-server`）
2. 启动网关（`gateway`）
3. 启动各业务服务（`health-service`、`info-service`、`life-service`）
4. 启动前端（若需访问 UI）

## 贡献
欢迎提交 Issue 和 Pull Request。请在提交前确保代码能通过基本构建并遵循既有风格。

## 许可证
本项目采用 MIT 许可证，详见 `LICENSE` 文件。

