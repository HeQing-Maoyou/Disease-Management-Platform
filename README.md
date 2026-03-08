项目运行与调试说明
=================

快速开始（前端）
-----------------

1. 进入项目前端目录并安装依赖：

```bash
cd "community-management-web"
npm install
```

2. 启动开发服务器：

```bash
npm run dev
```

快速开始（后端）
-----------------

1. 在工作区根目录运行后端打包或运行（需要安装 Maven 与 JDK 1.8+）：

打包：
```bash
cd "community-management-system"
mvn -DskipTests package
```

运行 Eureka 服务（示例）：
```bash
mvn spring-boot:run -pl eureka-server -am
```

已发现的关键问题（简要）
----------------------

- 根目录存在旧的 `package.json` 与子项目版本不一致（已替换为管理脚本）。建议使用 `community-management-web/package.json` 作为前端的唯一来源。
- 前端使用 Vue 3 + `vue-router@4` 与 Element Plus，代码结构看起来正常，但请在首次启动前在 `community-management-web` 下执行 `npm install`。
- 后端基于 Spring Boot 2.5.x，Java 1.8；如果本机为 Java 11+，请确认兼容性或升级 `java.version`。

下一步建议
-----------

1. 我可以在 `community-management-web` 运行依赖修复与格式检查，并修复任何具体的组件错误（如果你允许我修改文件）。
2. 我可以为前端添加 ESLint/Prettier 配置以规范代码风格。
3. 如果需要，我可以尝试启动前端开发服务器并报告控制台错误（需要你允许执行命令）。

如果要我继续自动修复前端问题，请确认或指定优先级。
