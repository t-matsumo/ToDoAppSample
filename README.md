シンプルなTODOリストアプリ

# 必要なもの
Docker

# docker imageを作成する方法
Dockerfileの内容をもとに、「to-do-app-sample」という名前のイメージを作成する。
```sh
docker build -t to-do-app-sample .
```

# 実行する方法
docker-compose.ymlの内容をもとに、webを実行する。
8080ポートで公開する。
```sh
docker compose run -p 8080:8080 --rm web
or
docker compose up web
```

# 確認方法
localhost:8080をブラウザで開く。

# 試したいこと
- [x] Dockerで実行環境を作る
- [ ] ~IntelliJ IDEA Community~ Visual Studio Codeを使って開発する
- [ ] Kotlin + ~Spring boot + thymeleaf~ ktorを使ってWebアプリとして雑に実装する
- [ ] SwaggerでRESTのレスポンスを定義する
- [ ] RESTに対応する
- [ ] gRPCに対応する
- [ ] SPAに書き換える
- [ ] DB周りの実装を差し替えてみる
- [ ] GraphQLに対応する

