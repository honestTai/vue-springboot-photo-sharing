<div align="center">

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/assets/hero.zh-CN.svg">
  <img src="docs/assets/hero.zh-CN-light.svg" alt="Photo Sharing">
</picture>

**基于Vue与SpringBoot的共享图片系统，支持加密分享与以图识图。**

[开发文档](docs/thesis/README.md) · [PlantUML 图源](docs/diagrams/) · [数据库初始化说明](database/README.md)

**[快速开始](#-快速开始) · [功能](#-功能) · [文档](#-文档) · [许可](#-许可)**

[English](README.md) · **简体中文**

</div>

## ❤️ 赞助商

| 赞助商 | 简介 |
| :--- | :--- |
| [![HRouter](docs/assets/hrouter-sponsor.svg)](https://hrouter.net/home) | **[HRouter](https://hrouter.net/home)**：面向 AI 编程与应用开发的模型接入、API Key 管理与用量统计，也由作者运营。 |

## 🚀 快速开始

1. 根据项目中的依赖声明安装对应语言运行时和数据库。
2. 查看下方依赖入口及初始化说明，按实际模块分别安装依赖。
3. 搜索 `CHANGE_ME_BEFORE_RUNNING`，填入自己的本地配置；不要提交真实密码、令牌和第三方服务密钥。
4. 启动相应后端，再启动前端或在小程序开发工具中导入客户端。

以下命令依据现有文件列出，**不代表已完成全项目安装、联调或运行验证**。

### 前端 / Node.js

在 `front/` 中执行：

```bash
npm install
npm run dev
```

### Java / Maven

- `server/pom.xml`

使用对应模块的 Maven 配置构建；多模块项目需按父子模块依赖顺序构建，并按原配置启动服务。
不要把示例配置中的占位值直接用于生产环境。

## ✨ 功能

| 功能 | 现有文档中的说明 |
| :--- | :--- |
| 图片分享 | 加密分享与以图识图 |
| 用户 | 注册、登录（JWT）、图片浏览、论坛浏览、个人管理 |
| 管理 | App 用户、图片与论坛 |
| 源码 | `front/`、`server/` |

开发文档中的图片浏览包括点赞、收藏、分类查询、以图识图和排序查询；论坛浏览包括发布、评论、回复和点赞；个人管理包括信息统计、论坛管理、图片管理、图片上传、信息编辑和收藏管理。

## 📚 文档

- [开发文档](docs/thesis/README.md)：Markdown 图文格式，GitHub 上直接阅读。
- [PlantUML 图源](docs/diagrams/)：共 8 个文件（`.plantuml` / `.puml` / `.wsd`）。
- [数据库初始化说明](database/README.md)

## 📄 许可

现有第三方组件的 LICENSE、NOTICE 和作者声明保留原样；本项目不替换原有许可，也不额外声明所有代码适用同一开源许可证。前端目录中已有的许可文件是 [front/LICENSE](front/LICENSE)。

## 📈 Star 历史

<a href="https://www.star-history.com/#honestTai/vue-springboot-photo-sharing&Date">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=honestTai/vue-springboot-photo-sharing&type=Date&theme=dark">
    <img src="https://api.star-history.com/svg?repos=honestTai/vue-springboot-photo-sharing&type=Date" alt="vue-springboot-photo-sharing 的 Star 历史">
  </picture>
</a>

---

<div align="center">

Made by **[honestTai](https://github.com/honestTai)** · [HRouter](https://hrouter.net/home) · [More projects](https://github.com/honestTai)

</div>
