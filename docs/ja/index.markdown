# ReverseCraft Reimagined

`ReverseCraft Reimagined` はMinecraftに逆クラフトを行うブロックを追加するMODです。

## 機能

- アイテムを素材に分解する

### ロードマップ

[milestone](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/milestone) ページにて公開しています。

あなたのアイディアはいつでも大歓迎です！
特にすばらしい、MODに実装したいアイディアはロードマップに追加します！

## 導入

### CurseForgeを使用する

CurseForgeで「ReverseCraft Reimagined」を検索し、そこから直接インストールできます。

詳しいCurseForgeの使い方に関しては、CurseForgeのドキュメントやヘルプをご参照ください。
こちらでは詳しい説明はいたしかねます。

### 手動インストール

前提MODとして[Forge](https://files.minecraftforge.net/)が必要です。

1. [Forge](https://files.minecraftforge.net/)をインストール。
2. [最新MODファイル](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/releases)をダウンロード。
    - 最新の安定版を推奨します。 いかなる種類のMODファイルでも、
      適切なバージョンを選択しなかった場合や適切にバックアップを取らなかった場合に生じた損害や損失については、
      こちらでは補償できません。
3. ダウンロードした `.jar` ファイルを `mods` フォルダにコピーします。
    - バージョンごとにフォルダが分かれている場合があります。導入間違えに注意してください。
4. Minecraftを起動し、MODが正しく読み込まれていることを確認します。

## 使い方

このMODは「ReverseWorkbench」というブロックを追加します。
このブロックを使用することで、クラフトしたアイテムを材料アイテムへと分解することができます。

1. ワールドにブロックを設置します。
2. ブロックを右クリックしてGUIを開きます。
3. 分解したいアイテムを右側のスロットに入れます。
4. 「リバース」ボタンをクリックすると、左側の3x3グリッドに表示されているアイテムに分解されます。

### `Snapshot` バージョンについて

`Snapshot` バージョンは`main`ブランチの最新コミットを元にビルドされたものです。
このバージョンには以下の特徴があります：

- 正式にリリースしていない新機能
- 最新のバグ修正
- 安定さを欠く機能や相互作用がよく検証されていないもの

[Actions](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/actions/workflows/snapshot.yaml)
タブに移動し、最新のワークフローを選択してください。
最新の`Snapshot`バージョンは、ワークフローの要約ページの`Artifacts`セクションからダウンロードできます。

> [!NOTE]
> 最新コミットのプッシュ後、ワークフローの完了までにしばらく時間がかかる場合があります。

## コントリビューション

**大歓迎です！**
以下のステップはより簡略化したものです。

1. このリポジトリをフォークします。
2. 新しいブランチを作成します。
    ```
    git checkout -b feature/your-feature-name
    ```
3. 変更をコミットします。
    ```
    git commit -m "Add new feature"
    ```
4. リポジトリにプッシュします。
    ```
    git push origin feature/your-feature-name
    ```
5. プルリクエストを作成します。

より詳細なガイドラインは[CONTRIBUTING.md](./CONTRIBUTING.md)をご覧ください。

## ライセンス

このプロジェクトは GNU AGPL-3.0 ライセンスの下でライセンスされています。
詳細は [LICENSE](../../LICENSE) ファイルをご覧ください。

## サポート

プレイ中に問題や不具合と思われる事象が起きた場合は [Issues](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/issues)
ページにて報告してください。

問題点として報告するまでもない、簡単な相談や質問は [Discussions](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/discussions)
をご利用することもできます。

## クレジット

偉大なるMinecraftコミュニティ、ならびにModdingを支えている全ての人々に感謝します。

また、このMODを開発するに至った、旧来の [ReverseCraft MOD](https://github.com/Unyuho/RevereseCraft)
とその作者に最大限のリスペクトと感謝を捧げます。
私たちはいずれも、オリジナルのMODそれ自体、開発者、その他のプロジェクトとは無関係で、完全なるファンボーイに過ぎません。

そのため、事前の予告なしにMODの開発を中止、プロジェクトページを削除する場合があります。

私たちはサードパーティーのコミュニティ「Cheered Adventure」
に所属するプロジェクトであり、私たちはより良い体験を全てのプレイヤーに提供するために活動しています。


<details>
<summary>以下は余談です。</summary>
私 @hizumiaoba は100%純粋な日本人です。リポジトリのトップページでは英語のみで書いていますが、特にサポートを英語だけに縛るようなことはありません。むしろウェルカムです。

また、MinecraftのModdingについては過去に 1.7.10 で少し経験があるのみで、本格的にこうしてプロジェクトを作るのは初めてです。
そのため、コミュニティの一員としてまだまだ未熟な部分が多々あると思います。先にお詫びを申し上げるとともに、他の皆さまからのご指導ご鞭撻は大歓迎です。
</details>
