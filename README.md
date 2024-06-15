# Game Documentation: Pokemon Game

## Introduction
這是一個寶可夢對戰遊戲，你可以和朋友一同遊玩，雙方選好自己喜歡的角色就可以直接開戰，享受對戰的快樂吧


## Game Design Document

### Gameplay
- **Pokemon:** 每隻寶可夢有血量，屬性和技能，不同屬性之間會產生相剋，使受到傷害變高或變低
- **Combat:** 在一個回合，玩家可以使用寶可夢進行攻擊，使用技能或使用道具，先讓對方所有寶可夢的血量歸零的一方獲勝
- **Tools:** 使用道具來補血

### User Interface
- **Main Menu:** 初始的登入介面，會詢問你是否滿18歲，但不論按哪個都可以進入
- **Pokemon Selection:** 寶可夢選擇介面，左邊為16種寶可夢，雙方玩家可以各自挑選6隻，可以重複選同一隻，最下面的按鈕即是開戰
- **Pokemon Fight Screen:** 戰鬥的介面，


## Technical Documentation

### Programming Languages and Tools
- **Languages:** 由Java語言撰寫
- **UI** 使用javax.swing製作
- **Graphics:** 圖像參考網路上，連結提供在檔案最後面

### Architecture
- **GameLogin.java:** 登入介面
- **ChoosePokemon.java:** 選擇寶可夢介面
- **FightPokemon.java:** 寶可夢戰鬥介面
- **Main.java:** 整個程式的entry
- **SerializePokemonData.java** 將寶可夢的資料序列化
- **DeserializePokemonData.java** 將寶可夢的資料反序列化


## User Guide

### Controls
1. 先透過登入畫面進到選擇介面當中
2. 兩個人分別選1至6隻寶可夢，按下方按鈕開戰
3. 一方選擇使用攻擊，技能或道具，接著換下一方選擇
4. 雙方攻擊，若一方血量歸零則換下一隻寶可夢上場，否則返回步驟3
5. 最先所有寶可夢戰敗的一方輸


## Developer Notes

### Bugs
- Occasional frame rate drops in high-density areas.
- Minor graphical glitches on certain hardware configurations.


### Contributions
- **徐皓揚**
- **謝卓熹**
- **楊承曄**
- **育成**


### Reference
- https://www.kaggle.com/datasets/kvpratama/pokemon-images-dataset
寶可夢的圖片
- https://www.gettyimages.hk/%E5%9C%96%E7%89%87/%E8%94%A1%E8%8B%B1%E6%96%87
蔡英文的照片
- https://www.gettyimages.hk/search/2/image?family=editorial&sort=mostpopular&phrase=%E9%9F%93%E5%9C%8B%E7%91%9C
韓國瑜的照片
