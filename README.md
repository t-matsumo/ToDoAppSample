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

# auto reloadを有効にする
実行中に、別のターミナルで以下を実行する。
```sh
docker compose exec web ./gradlew -t build -x test -i
```

# 確認方法
localhost:8080をブラウザで開く。

# 試したいこと
- [x] Dockerで実行環境を作る
- [x] IntelliJ IDEA Communityを使って開発する(Dockerの中で開発しようとしなければできる)
- [ ] Kotlin + ktorを使ってWebアプリとして雑に実装する
- [ ] SwaggerでRESTのレスポンスを定義する
- [ ] RESTに対応する
- [ ] gRPCに対応する
- [ ] SPAに書き換える
- [ ] DB周りの実装を差し替えてみる
- [ ] GraphQLに対応する

